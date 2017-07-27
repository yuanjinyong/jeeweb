/**
 * 
 */
package com.jeeweb.framework.liquibase;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import liquibase.servicelocator.ServiceLocator;

/**
 * @author 袁进勇
 *
 */
@Configuration
@AutoConfigureAfter(LiquibaseAutoConfiguration.class)
public class LiquibaseParserConfiguration {
    LiquibaseParserConfiguration() {
        ServiceLocator.getInstance().addPackageToScan(LiquibaseParserConfiguration.class.getPackage().getName());
    }
}
