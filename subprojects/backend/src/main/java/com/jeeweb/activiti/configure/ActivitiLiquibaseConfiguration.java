/**
 * 
 */
package com.jeeweb.activiti.configure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.integration.spring.SpringLiquibase;

/**
 * @author 袁进勇
 *
 */
@Configuration
@AutoConfigureAfter(ActivitiDataSourceConfiguration.class)
public class ActivitiLiquibaseConfiguration {
    @Bean(name = "activitiLiquibase")
    public SpringLiquibase activitiLiquibase(@Qualifier("activitiDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:/com/jeeweb/activiti/**/mapper/*Liquibase.sql");
        liquibase.setContexts("test, production");
        return liquibase;
    }
}
