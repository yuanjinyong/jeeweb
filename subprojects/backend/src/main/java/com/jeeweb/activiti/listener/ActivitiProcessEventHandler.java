/**
 * 
 */
package com.jeeweb.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiProcessStartedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 袁进勇
 *
 */
@Component
public class ActivitiProcessEventHandler extends ActivitiAbstractEventHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ActivitiProcessEventHandler.class);

    @Override
    public void handleEvent(ActivitiEvent event) {
        switch (event.getType()) {
        case PROCESS_STARTED:
            ActivitiProcessStartedEvent e = (ActivitiProcessStartedEvent) event;
            LOG.debug("流程实例{}({})已启动，流程定义：{}，变量：{}。", event.getProcessInstanceId(), event.getProcessDefinitionId(),
                    e.getVariables());
            break;
        case PROCESS_CANCELLED:
            LOG.debug("流程实例{}({})已取消，流程定义：{}。", event.getProcessInstanceId(), event.getProcessDefinitionId());
            break;
        case PROCESS_COMPLETED:
            LOG.debug("流程实例{}({})已完成，流程定义：{}。", event.getProcessInstanceId(), event.getProcessDefinitionId());
            break;
        case PROCESS_COMPLETED_WITH_ERROR_END_EVENT:
            LOG.debug("流程实例{}({})出错结束，流程定义：{}。", event.getProcessInstanceId(), event.getProcessDefinitionId());
            break;
        default:
            super.handleEvent(event);
            break;
        }
    }

}
