package com.jeeweb.framework.core.aware;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author 袁进勇
 *
 */
@Component
public class SpringContextAware implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /*
     * (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.
     * ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextAware.applicationContext = applicationContext;
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入，请先配置SpringContextAware为Spring Bean。");
        }
    }

    /**
     * 取得ApplicationContext
     * 
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return applicationContext.getBean(beanName, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }
}