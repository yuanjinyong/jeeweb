package com.jeeweb.activiti.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jeeweb.activiti.entity.ProcessModelEntity;
import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.utils.HelpUtil;

@Service
@Transactional
public class ProcessModelService extends ActivitiService<String, ProcessModelEntity> {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessModelService.class);

    @Override
    protected BaseMapper<String, ProcessModelEntity> getMapper() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ProcessModelEntity selectEntity(String primaryKey) {
        Model model = repositoryService.getModel(primaryKey);
        return buildProcessModelEntity(model);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessModelEntity> selectEntityListPage(ParameterMap params) {
        ModelQuery query = repositoryService.createModelQuery();
        if (!HelpUtil.isEmpty(params.getString("f_tenant_id", ""))) {
            query.modelTenantId(params.getString("f_tenant_id"));
        }
        if (!HelpUtil.isEmpty(params.getString("f_tenant_id_in", ""))) {
            query.modelTenantId(params.getString("f_tenant_id_in"));
        }
        if (!HelpUtil.isEmpty(params.getString("f_name_like", ""))) {
            query.modelNameLike(params.getString("f_name_like"));
        }
        query.orderByModelKey().asc().orderByModelVersion().desc();
        params.setTotalCount((int) query.count());

        List<Model> modelList = params.getPageSize() == null ? query.list()
                : query.listPage(params.getBeginRowNo(), params.getPageSize());
        List<ProcessModelEntity> entityList = new ArrayList<>();
        for (Model model : modelList) {
            entityList.add(buildProcessModelEntity(model));
        }

        return entityList;
    }

    @Override
    public void insertEntity(ProcessModelEntity entity) {
        Model model = buildModel(entity);
        repositoryService.saveModel(model);

        try {
            repositoryService.addModelEditorSource(model.getId(),
                    buildModelEditorSource().toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException("创建流程模型失败！", e);
        }
    }

    @Override
    public void updateEntity(String primaryKey, ProcessModelEntity entity) {
        Model model = buildModel(entity);
        repositoryService.saveModel(model);
    }

    @Override
    public void deleteEntity(String primaryKey) {
        repositoryService.deleteModel(primaryKey);
    }

    @Override
    public void deleteEntities(ParameterMap params) {
        List<String> modelIdList = HelpUtil.splitToList(params.getString("f_id_in"));
        for (String modelId : modelIdList) {
            deleteEntity(modelId);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void deployModel(String modelId) {
        Model model = repositoryService.getModel(modelId);
        String resourceName = model.getName() + "-" + model.getVersion() + ".bpmn20.xml";
        String bpmn = new String(new BpmnXMLConverter().convertToXML(getBpmnModel(model.getId())));
        Deployment deployment = repositoryService.createDeployment().name(model.getName()).category(model.getCategory())
                .tenantId(model.getTenantId()).addString(resourceName, bpmn).deploy();

        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);
    }

    public BpmnModel getBpmnModel(String modelId) {
        try {
            JsonNode editorNode = objectMapper.readTree(repositoryService.getModelEditorSource(modelId));
            return new BpmnJsonConverter().convertToBpmnModel(editorNode);
        } catch (IOException e) {
            LOG.error("获取BPMN模型失败！modelId={}", modelId);
            throw new BusinessException("获取BPMN模型失败！", e);
        }
    }

    public String increaseModelVersion(String modelId) {
        Model model = repositoryService.getModel(modelId);
        Model newModel = repositoryService.newModel();
        newModel.setCategory(model.getCategory());
        newModel.setDeploymentId(null);
        newModel.setKey(model.getKey());
        newModel.setMetaInfo(model.getMetaInfo());
        newModel.setName(model.getName());
        newModel.setTenantId(model.getTenantId());
        newModel.setVersion(model.getVersion() + 1);
        repositoryService.saveModel(newModel);

        byte[] editorSource = repositoryService.getModelEditorSource(model.getId());
        repositoryService.addModelEditorSource(newModel.getId(), editorSource);
        byte[] editorSourceExtra = repositoryService.getModelEditorSourceExtra(model.getId());
        repositoryService.addModelEditorSourceExtra(newModel.getId(), editorSourceExtra);

        return newModel.getId();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private ProcessModelEntity buildProcessModelEntity(Model model) {
        return new ProcessModelEntity((org.activiti.engine.impl.persistence.entity.ModelEntity) model);
    }

    private Model buildModel(ProcessModelEntity entity) {
        org.activiti.engine.impl.persistence.entity.ModelEntity newModel = entity.getModel();
        Model model = HelpUtil.isEmpty(entity.getF_id()) ? repositoryService.newModel()
                : repositoryService.getModel(entity.getF_id());
        model.setKey(newModel.getKey());
        model.setName(newModel.getName());
        model.setVersion(newModel.getVersion());
        model.setTenantId(ProcessEngineConfiguration.NO_TENANT_ID);

        ObjectNode metaInfoNode = objectMapper.createObjectNode();
        metaInfoNode.put(ModelDataJsonConstants.MODEL_NAME, model.getName());
        metaInfoNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, entity.getDescription());
        metaInfoNode.put(ModelDataJsonConstants.MODEL_REVISION, newModel.getRevision());
        model.setMetaInfo(metaInfoNode.toString());

        return model;
    }

    private ObjectNode buildModelEditorSource() {
        ObjectNode editorSource = objectMapper.createObjectNode();
        editorSource.put("id", "canvas");
        editorSource.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorSource.set("stencilset", stencilSetNode);
        return editorSource;
    }
}
