/**
 * 
 */
package com.jeeweb.activiti.listener;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全局事件监听器，所有事件均需要在这里统一分发处理
 * 接收标准事件类型并处理和异常处理，其中isFailOnException()决定了onEvent(..)是否抛出异常，如果返回false,异常将会被忽略。如果返回true,异常将会逐级向上提交，
 * 造成当前运行的命令的失败。如果当前执行具有事务，事务将会回滚。activiti官方建议如果事件的业务不是非常重要，还是返回false。
 * 
 * @author 袁进勇
 *
 */
public class GlobalActivitiEventListener implements ActivitiEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalActivitiEventListener.class);

    // 事件及事件的处理器
    private Map<ActivitiEventType, ActivitiEventHandler> handlers = new HashMap<>();
    // 更换为以下模式，可以防止Spring容器启动时，ProcessEngine尚未创建，而业务类中又使用了这个引用
    // private Map<String, String> handlers = new HashMap<String, String>();

    /*
     * (non-Javadoc)
     * 
     * @see org.activiti.engine.delegate.event.ActivitiEventListener#onEvent(org.activiti.engine.delegate.event.
     * ActivitiEvent)
     */
    @Override
    public void onEvent(ActivitiEvent event) {
        ActivitiEventType eventType = event.getType();
        LOG.trace("事件类型：{}({})，流程定义：{}，流程实例：{}，执行实例：{}。", eventType.name(), event.getClass().getName(),
                event.getProcessDefinitionId(), event.getProcessInstanceId(), event.getExecutionId());

        // 根据事件的类型ID,找到对应的事件处理器
        if (!handlers.containsKey(eventType)) {
            LOG.info("事件{}未配置全局的处理器，已忽略。", eventType.name());
            return;
        }

        ActivitiEventHandler handler = handlers.get(eventType);
        handler.handleEvent(event);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.activiti.engine.delegate.event.ActivitiEventListener#isFailOnException()
     */
    @Override
    public boolean isFailOnException() {
        return false;
    }

    public Map<ActivitiEventType, ActivitiEventHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<ActivitiEventType, ActivitiEventHandler> handlers) {
        this.handlers = handlers;
    }

}
