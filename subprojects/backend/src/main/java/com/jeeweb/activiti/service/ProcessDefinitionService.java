package com.jeeweb.activiti.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.activiti.entity.ProcessDefinitionEntity;
import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParamsMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.security.utils.SecurityUtil;
import com.jeeweb.platform.sys.entity.UserEntity;

@Service
@Transactional
public class ProcessDefinitionService extends ActivitiService<String, ProcessDefinitionEntity> {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessDefinitionService.class);

    @Override
    protected BaseMapper<String, ProcessDefinitionEntity> getMapper() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ProcessDefinitionEntity selectEntity(String primaryKey) {
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(primaryKey);
        return buildProcessDefinitionEntity(processDefinition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessDefinitionEntity> selectEntityListPage(ParamsMap params) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        if (!HelpUtil.isEmpty(params.$("f_tenant_id", ""))) {
            query.processDefinitionTenantId(params.$("f_tenant_id"));
        }
        if (!HelpUtil.isEmpty(params.$("f_tenant_id_in", ""))) {
            query.processDefinitionTenantId(params.$("f_tenant_id_in"));
        }
        if (!HelpUtil.isEmpty(params.$("f_name_like", ""))) {
            query.processDefinitionNameLike("%" + params.$("f_name_like") + "%");
        }
        query.orderByProcessDefinitionKey().asc().orderByProcessDefinitionVersion().desc();
        params.setTotalCount((int) query.count());

        List<ProcessDefinition> processDefinitionList = params.getPageSize() == null ? query.list()
                : query.listPage(params.getBeginRowNo(), params.getPageSize());
        List<ProcessDefinitionEntity> entityList = new ArrayList<>();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            entityList.add(buildProcessDefinitionEntity(processDefinition));
        }

        return entityList;
    }

    @Override
    public void insertEntity(ProcessDefinitionEntity entity) {
    }

    @Override
    public void updateEntity(String primaryKey, ProcessDefinitionEntity entity) {
    }

    @Override
    public void deleteEntity(String primaryKey) {
    }

    @Override
    public void deleteEntities(ParamsMap params) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public InputStream getProcessDiagram(String processDefinitionId) {
        return repositoryService.getProcessDiagram(processDefinitionId);
    }

    public ProcessInstance startProcess(String processDefinitionId, Map<String, String> varables) {
        LOG.info("启动[{}]流程：varables={}。", processDefinitionId, varables);

        // 设置流程发起人
        UserEntity user = SecurityUtil.getCurUser();
        identityService.setAuthenticatedUserId(user.getF_id().toString());

        ProcessInstance processInstance = formService.submitStartFormData(processDefinitionId, varables);
        ProcessDefinition processDefinition = repositoryService
                .getProcessDefinition(processInstance.getProcessDefinitionId());
        runtimeService.setProcessInstanceName(processInstance.getId(), processDefinition.getName());
        LOG.info("启动[{}]流程成功：processInstanceId={}，activityId={}。", processDefinitionId, processInstance.getId(),
                processInstance.getActivityId());
        return processInstance;
    }

    /**
     * 通过流程定义的key启动流程，会启动版本最高的流程
     * 
     * @param processDefinitionKey
     * @param tenantId
     * @param varables
     * @return
     */
    public ProcessInstance startProcess(String processDefinitionKey, Integer tenantId, Map<String, Object> varables) {
        LOG.info("启动[{}]流程：varables={}。", processDefinitionKey, varables);

        // 设置流程发起人
        UserEntity user = SecurityUtil.getCurUser();
        identityService.setAuthenticatedUserId(user.getF_id().toString());

        // 找出租户部署的最高版本的流程定义
        ProcessDefinition processDefinition = getProcessDefinition(processDefinitionKey, tenantId);

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varables);
        runtimeService.setProcessInstanceName(processInstance.getId(), processDefinition.getName());
        LOG.info("启动[{}]流程成功：processInstanceId={}，activityId={}。", processDefinitionKey, processInstance.getId(),
                processInstance.getActivityId());

        return processInstance;
    }

    /**
     * 通过流程定义的key，获取租户部署的最高版本的流程定义。指定租户下没有时获取父级租户下的。
     *
     * @param processDefinitionKey
     * @param tenantId
     * @return
     */
    public ProcessDefinition getProcessDefinition(String processDefinitionKey, Integer tenantId) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        query.processDefinitionKey(processDefinitionKey).orderByProcessDefinitionVersion().desc();
        // TODO 以后再说
        // List<Integer> tenantIdList = SysUtil.getTenantIdList(tenantId);
        // for (int i = tenantIdList.size() - 1; i >= 0; i--) {
        // List<ProcessDefinition> processDefinitionList = query
        // .processDefinitionTenantId(tenantIdList.get(i).toString()).list();
        // if (!HelpUtil.isEmpty(processDefinitionList)) {
        // return processDefinitionList.get(0);
        // }
        // }
        // TODO 以后再说
        List<ProcessDefinition> processDefinitionList = query.processDefinitionTenantId("").list();
        if (!HelpUtil.isEmpty(processDefinitionList)) {
            return processDefinitionList.get(0);
        }

        // throw new ActivitiObjectNotFoundException("no processes deployed with key '" + processDefinitionKey
        // + "' for tenant identifier '" + tenantId + "'");
        throw new BusinessException("根据[" + processDefinitionKey + "]未获取到租户 [" + tenantId + "]下的流程定义！");
    }

    /**
     * 查询流程节点
     * 
     * @param processDefinitionKey
     * @return
     */
    public Iterator<FlowElement> findFlowElement(String processDefinitionKey, Integer tenantId) {
        ProcessDefinition processDefinition = getProcessDefinition(processDefinitionKey, tenantId);
        BpmnModel bpmnModel = getBpmnModel(processDefinition);
        Collection<FlowElement> elements = bpmnModel.getMainProcess().getFlowElements();
        return elements.iterator();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ProcessDefinitionEntity buildProcessDefinitionEntity(ProcessDefinition processDefinition) {
        ProcessDefinitionEntity entity = new ProcessDefinitionEntity();
        entity.setF_id(processDefinition.getId());
        entity.setF_tenant_id(processDefinition.getTenantId());
        entity.setF_key(processDefinition.getKey());
        entity.setF_name(processDefinition.getName());
        entity.setF_category(processDefinition.getCategory());
        entity.setF_version(processDefinition.getVersion());
        entity.setF_description(processDefinition.getDescription());
        entity.setF_deployment_id(processDefinition.getDeploymentId());
        entity.setF_resource_name(processDefinition.getResourceName());
        entity.setF_diagram_resource_name(processDefinition.getDiagramResourceName());
        entity.setHasStartFormKey(processDefinition.hasStartFormKey());
        entity.setHasGraphicalNotation(processDefinition.hasGraphicalNotation());
        entity.setSuspended(processDefinition.isSuspended());

        return entity;
    }
}
