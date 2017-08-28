/**
 * 
 */
package com.jeeweb.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 袁进勇
 *
 */
@Component
public class ActivitiEngineEventHandler extends ActivitiAbstractEventHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ActivitiEngineEventHandler.class);

    @Override
    public void handleEvent(ActivitiEvent event) {
        switch (event.getType()) {
        case ENGINE_CREATED:
            LOG.info("流程引擎已创建。");
            break;
        case ENGINE_CLOSED:
            LOG.info("流程引擎已关闭。");
            break;
        default:
            super.handleEvent(event);
            break;
        }
    }

}
