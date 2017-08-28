/**
 * 
 */
package com.jeeweb.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 袁进勇
 *
 */
@Component
public class ActivitiTaskEventHandler extends ActivitiAbstractEventHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ActivitiTaskEventHandler.class);

    @Override
    public void handleEvent(ActivitiEvent event) {
        ActivitiEntityEvent e = (ActivitiEntityEvent) event;
        switch (event.getType()) {
        case TASK_CREATED:
            LOG.debug("流程任务{}已创建，流程定义：{}，流程实例：{}，执行实例：{}。", e.getEntity(), event.getProcessDefinitionId(),
                    event.getProcessInstanceId(), event.getExecutionId());
            break;
        case TASK_ASSIGNED:
            LOG.debug("流程任务{}({})已指派，流程定义：{}，流程实例：{}，执行实例：{}。", e.getEntity(), event.getProcessDefinitionId(),
                    event.getProcessInstanceId(), event.getExecutionId());
            break;
        case TASK_COMPLETED:
            LOG.debug("流程任务{}({})已完成，流程定义：{}，流程实例：{}，执行实例：{}。", e.getEntity(), event.getProcessDefinitionId(),
                    event.getProcessInstanceId(), event.getExecutionId());
            break;
        default:
            super.handleEvent(event);
            break;
        }
    }

}
