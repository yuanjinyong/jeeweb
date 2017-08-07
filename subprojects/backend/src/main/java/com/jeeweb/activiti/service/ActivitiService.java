/**
 * 
 */
package com.jeeweb.activiti.service;

import java.io.InputStream;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.core.exception.BusinessException;

/**
 * @author 袁进勇
 *
 */
public abstract class ActivitiService<P, E> extends BaseService<P, E> {
    private static final Logger LOG = LoggerFactory.getLogger(ActivitiService.class);
    @Autowired
    protected ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    protected ProcessEngine processEngine;
    // @Autowired
    // protected DynamicBpmnService dynamicBpmnService;
    @Autowired
    protected FormService formService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected IdentityService identityService;
    @Autowired
    protected ManagementService managementService;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected ObjectMapper objectMapper;

    protected BpmnModel getBpmnModel(ProcessDefinition processDefinition) {
        InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());

        try {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream);
            return new BpmnXMLConverter().convertToBpmnModel(reader);
        } catch (XMLStreamException | FactoryConfigurationError e) {
            LOG.error("获取BPMN模型失败！processDefinitionId={}，version={}。", processDefinition.getId(),
                    processDefinition.getVersion());
            throw new BusinessException("获取BPMN模型失败！", e);
        }
    }

    protected InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities,
            List<String> highLightedFlows) {
        return processEngineConfiguration.getProcessDiagramGenerator().generateDiagram(bpmnModel, imageType,
                highLightedActivities, highLightedFlows, processEngineConfiguration.getActivityFontName(),
                processEngineConfiguration.getLabelFontName(), processEngineConfiguration.getAnnotationFontName(), null,
                1.0);
    }
}
