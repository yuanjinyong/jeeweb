/**
 * 
 */
package com.jeeweb.platform.configure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jeeweb.framework.configure.DefaultDataSourceConfiguration;

import liquibase.integration.spring.SpringLiquibase;

/**
 * @author 袁进勇
 *
 */
@Configuration
@AutoConfigureAfter(DefaultDataSourceConfiguration.class)
public class PlatformLiquibaseConfiguration {
    @Bean(name = "platformLiquibase")
    public SpringLiquibase platformLiquibase(@Qualifier("defaultDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:/com/jeeweb/platform/**/mapper/*Liquibase.sql");
        liquibase.setContexts("test, production");
        return liquibase;
    }
}
