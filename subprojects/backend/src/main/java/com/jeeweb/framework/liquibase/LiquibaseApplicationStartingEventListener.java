/**
 * 
 */
package com.jeeweb.framework.liquibase;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import liquibase.servicelocator.ServiceLocator;

/**
 * @author 袁进勇
 *
 */
public class LiquibaseApplicationStartingEventListener
        implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        ServiceLocator.getInstance().addPackageToScan(AbstractSqlChangeLogParser.class.getPackage().getName());
    }
}
