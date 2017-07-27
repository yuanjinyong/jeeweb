/**
 * 
 */
package com.jeeweb.platform.configure;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ObjectUtils;

import liquibase.integration.spring.SpringLiquibase;

/**
 * @author 袁进勇
 *
 */
@Configuration
@MapperScan(basePackages = { "com.jeeweb.platform.**.mapper.**" }, sqlSessionTemplateRef = "platformSqlSessionTemplate")
public class PlatformMybatisConfiguration {
    @Autowired(required = false)
    private Interceptor[] interceptors;
    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    @Value("${mybatis.mapper.platform.typeAliasesPackage:com.jeeweb.platform.**.entity}")
    private String typeAliasesPackage;
    @Value("${mybatis.mapper.platform.mapperLocations:classpath:/com/jeeweb/platform/**/mapper/*Mapper.xml}")
    private String mapperLocations;

    @Bean(name = "platformSqlSessionFactory")
    public SqlSessionFactory platformSqlSessionFactory(@Qualifier("defaultDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            factory.setPlugins(this.interceptors);
        }
        if (this.databaseIdProvider != null) {
            factory.setDatabaseIdProvider(this.databaseIdProvider);
        }
        factory.setTypeAliasesPackage(typeAliasesPackage);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return factory.getObject();
    }

    @Bean(name = "platformSqlSessionTemplate")
    public SqlSessionTemplate platformSqlSessionTemplate(
            @Qualifier("platformSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "platformLiquibase")
    public SpringLiquibase platformLiquibase(@Qualifier("defaultDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:/com/jeeweb/platform/**/mapper/*Liquibase.sql");
        liquibase.setContexts("test, production");
        return liquibase;
    }
}
