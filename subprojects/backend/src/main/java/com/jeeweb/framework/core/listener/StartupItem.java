/**
 * 
 */
package com.jeeweb.framework.core.listener;

import org.springframework.context.ApplicationContext;

/**
 * @author 袁进勇
 *
 */
public interface StartupItem {
    void invoke(ApplicationContext ac);
}
