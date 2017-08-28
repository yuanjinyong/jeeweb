package com.jeeweb.activiti.service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.activiti.entity.ProcessInstanceEntity;
import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.utils.HelpUtil;

@Service
@Transactional
public class ProcessInstanceService extends ActivitiService<String, ProcessInstanceEntity> {
    @Override
    protected BaseMapper<String, ProcessInstanceEntity> getMapper() {
        return null;
    }

    @Override
    public ProcessInstanceEntity selectEntity(String primaryKey) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        HistoricProcessInstance processInstance = query.processInstanceId(primaryKey).singleResult();
        return buildProcessInstanceEntity((HistoricProcessInstanceEntity) processInstance);
    }

    @Override
    public List<ProcessInstanceEntity> selectEntityListPage(ParameterMap params) {
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        if (!HelpUtil.isEmpty(params.getString("f_tenant_id", ""))) {
            query.processInstanceTenantId(params.getString("f_tenant_id"));
        }
        if (!HelpUtil.isEmpty(params.getString("f_tenant_id_in", ""))) {
            query.processInstanceTenantId(params.getString("f_tenant_id_in"));
        }
        if (!HelpUtil.isEmpty(params.getString("f_name_like", ""))) {
            query.processInstanceNameLike("%" + params.getString("f_name_like") + "%");
        }
        if (!HelpUtil.isEmpty(params.getString("f_start_time_begin", ""))) {
            query.startedAfter(HelpUtil.parseDatetime(params.getString("f_start_time_begin")));
        }
        if (!HelpUtil.isEmpty(params.getString("f_start_time_end", ""))) {
            query.startedBefore(HelpUtil.parseDatetime(params.getString("f_start_time_end")));
        }
        if (!HelpUtil.isEmpty(params.getString("f_status", ""))) {
            if ("finished".equals(params.getString("f_status", ""))) {
                query.finished();
            } else if ("unfinished".equals(params.getString("f_status", ""))) {
                query.unfinished();
            }
        }
        query.orderByProcessInstanceStartTime().desc();
        params.setTotalCount((int) query.count());

        List<HistoricProcessInstance> processInstancelList = params.getPageSize() == null ? query.list()
                : query.listPage(params.getBeginRowNo(), params.getPageSize());
        List<ProcessInstanceEntity> entityList = new ArrayList<>();
        for (HistoricProcessInstance processInstance : processInstancelList) {
            entityList.add(buildProcessInstanceEntity((HistoricProcessInstanceEntity) processInstance));
        }

        return entityList;
    }

    @Override
    public void insertEntity(ProcessInstanceEntity entity) {
    }

    @Override
    public void updateEntity(String primaryKey, ProcessInstanceEntity entity) {
    }

    @Override
    public void deleteEntity(String primaryKey) {
    }

    @Override
    public void deleteEntities(ParameterMap params) {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<HistoricActivityInstance> selectActivityInstances(String processInstanceId) {
        HistoricActivityInstanceQuery query = historyService.createHistoricActivityInstanceQuery();
        List<HistoricActivityInstance> activityList = query.processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceId().asc().list();

        return activityList;
    }

    public InputStream getProcessDiagram(String processInstanceId) {
        // 获取历史流程实例
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();

        // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
        List<HistoricActivityInstance> historicActivityInstanceList = historyService
                .createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceId().asc().list();

        // 已执行的节点ID集合
        List<String> executedActivityIdList = new ArrayList<>();
        for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
            executedActivityIdList.add(activityInstance.getActivityId());
        }

        // 获取流程定义
        ProcessDefinition processDefinition = repositoryService
                .getProcessDefinition(processInstance.getProcessDefinitionId());
        // 获取流程图图像字符流
        InputStream imageStream = generateDiagram(getBpmnModel(processDefinition), "png", executedActivityIdList,
                Collections.<String> emptyList());
        return imageStream;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ProcessInstanceEntity buildProcessInstanceEntity(HistoricProcessInstanceEntity processInstance) {
        ProcessInstanceEntity entity = new ProcessInstanceEntity();
        entity.setF_id(processInstance.getId());
        entity.setF_tenant_id(processInstance.getTenantId());
        entity.setF_name(processInstance.getName());
        entity.setF_description(processInstance.getDescription());
        entity.setF_start_user_id(processInstance.getStartUserId());
        entity.setF_start_time(new Timestamp(processInstance.getStartTime().getTime()));
        if (processInstance.getEndTime() != null) {
            entity.setF_end_time(new Timestamp(processInstance.getEndTime().getTime()));
        }
        entity.setF_duration(processInstance.getDurationInMillis());
        // entity.setF_activity_id(processInstance.getActivityId());
        entity.setF_proc_inst_id(processInstance.getProcessInstanceId());
        // entity.setF_parent_id(processInstance.getParentId());
        // entity.setSuspended(processInstance.isSuspended());
        // entity.setEnded(processInstance.isEnded());
        // entity.setF_localized_name(processInstance.getLocalizedName());
        // entity.setF_localized_description(processInstance.getLocalizedDescription());
        entity.setF_proc_def_id(processInstance.getProcessDefinitionId());
        entity.setF_proc_def_key(processInstance.getProcessDefinitionKey());
        entity.setF_proc_def_name(processInstance.getProcessDefinitionName());
        entity.setF_proc_def_version(processInstance.getProcessDefinitionVersion());
        entity.setF_deployment_id(processInstance.getDeploymentId());
        entity.setF_business_key(processInstance.getBusinessKey());
        entity.setF_delete_reason(processInstance.getDeleteReason());
        entity.setVariables(processInstance.getProcessVariables());

        return entity;
    }
}
