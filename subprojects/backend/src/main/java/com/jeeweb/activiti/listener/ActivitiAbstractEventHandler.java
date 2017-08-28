/**
 * 
 */
package com.jeeweb.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 袁进勇
 *
 */
public abstract class ActivitiAbstractEventHandler implements ActivitiEventHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ActivitiAbstractEventHandler.class);

    @Override
    public void handleEvent(ActivitiEvent event) {
        LOG.warn("处理器{}未能识别事件{}，已忽略。", this.getClass().getSimpleName(), event.getType());
    }
}
