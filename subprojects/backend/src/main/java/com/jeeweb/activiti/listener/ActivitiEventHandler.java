/**
 * 
 */
package com.jeeweb.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;

/**
 * 事件的处理器接口
 * 
 * @author 袁进勇
 *
 */
public interface ActivitiEventHandler {
    void handleEvent(ActivitiEvent event);
}
