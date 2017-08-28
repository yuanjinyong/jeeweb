package com.jeeweb.framework.liquibase;

import org.springframework.context.annotation.Configuration;

import liquibase.servicelocator.ServiceLocator;

/**
 * @author 袁进勇
 * Liquibase的配置是采用static方式完成初始化，故按spring的加载顺序，增加的两个parser可以直接采用@Configuration配置即可
 */
@Configuration
public class LiquibaseParserConfiguration {
    LiquibaseParserConfiguration() {
        ServiceLocator.getInstance().addPackageToScan(LiquibaseParserConfiguration.class.getPackage().getName());
    }
}
