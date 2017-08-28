/**
 * 
 */
package com.jeeweb.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiActivityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 袁进勇
 *
 */
@Component
public class ActivitiActivityEventHandler extends ActivitiAbstractEventHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ActivitiActivityEventHandler.class);

    @Override
    public void handleEvent(ActivitiEvent event) {
        ActivitiActivityEvent e = (ActivitiActivityEvent) event;
        switch (event.getType()) {
        case ACTIVITY_STARTED:
            LOG.debug("流程活动{}({} - {})已启动，流程定义：{}，流程实例：{}，执行实例：{}。", e.getActivityId(), e.getActivityType(),
                    e.getActivityName(), event.getProcessDefinitionId(), event.getProcessInstanceId(),
                    event.getExecutionId());
            break;
        case ACTIVITY_SIGNALED:
            LOG.debug("流程活动{}({} - {})收到信号事件，流程定义：{}，流程实例：{}，执行实例：{}。", e.getActivityId(), e.getActivityType(),
                    e.getActivityName(), event.getProcessDefinitionId(), event.getProcessInstanceId(),
                    event.getExecutionId());
            break;
        case ACTIVITY_MESSAGE_RECEIVED:
            LOG.debug("流程活动{}({} - {})收到消息事件，流程定义：{}，流程实例：{}，执行实例：{}。", e.getActivityId(), e.getActivityType(),
                    e.getActivityName(), event.getProcessDefinitionId(), event.getProcessInstanceId(),
                    event.getExecutionId());
            break;
        case ACTIVITY_ERROR_RECEIVED:
            LOG.debug("流程活动{}({} - {})收到错误事件，流程定义：{}，流程实例：{}，执行实例：{}。", e.getActivityId(), e.getActivityType(),
                    e.getActivityName(), event.getProcessDefinitionId(), event.getProcessInstanceId(),
                    event.getExecutionId());
            break;
        case ACTIVITY_COMPENSATE:
            LOG.debug("流程活动{}({} - {})收到补偿事件，流程定义：{}，流程实例：{}，执行实例：{}。", e.getActivityId(), e.getActivityType(),
                    e.getActivityName(), event.getProcessDefinitionId(), event.getProcessInstanceId(),
                    event.getExecutionId());
            break;
        case ACTIVITY_CANCELLED:
            LOG.debug("流程活动{}({} - {})已取消，流程定义：{}，流程实例：{}，执行实例：{}。", e.getActivityId(), e.getActivityType(),
                    e.getActivityName(), event.getProcessDefinitionId(), event.getProcessInstanceId(),
                    event.getExecutionId());
            break;
        case ACTIVITY_COMPLETED:
            LOG.debug("流程活动{}({} - {})已完成，流程定义：{}，流程实例：{}，执行实例：{}。", e.getActivityId(), e.getActivityType(),
                    e.getActivityName(), event.getProcessDefinitionId(), event.getProcessInstanceId(),
                    event.getExecutionId());
            break;
        default:
            super.handleEvent(event);
            break;
        }
    }

}
