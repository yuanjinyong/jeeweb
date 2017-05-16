/**
 * 
 */
package com.jeeweb.framework.core.configure;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ObjectUtils;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author 袁进勇
 *
 */
@Configuration
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
@MapperScan(basePackages = { "com.jeeweb.**.mapper.**" }, sqlSessionFactoryRef = "jeewebSqlSessionFactory")
public class JeewebDataSourceConfiguration {
    @Autowired(required = false)
    private Interceptor[] interceptors;
    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    @Value("${jeeweb.datasource.driverClassName:com.mysql.jdbc.Driver}")
    private String driverClass;
    @Value("${jeeweb.datasource.url:#{null}}")
    private String url;
    @Value("${jeeweb.datasource.username:#{null}}")
    private String user;
    @Value("${jeeweb.datasource.password:#{null}}")
    private String password;
    @Value("${jeeweb.mybatis.typeAliasesPackage:com.jeeweb.**.entity}")
    private String typeAliasesPackage;
    @Value("${jeeweb.mybatis.mapperLocations:classpath:/com/jeeweb/**/mapper/*Mapper.xml}")
    private String mapperLocations;

    @Bean(name = "jeewebDataSource")
    @Primary
    public DataSource jeewebDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "jeewebTransactionManager")
    @Primary
    public DataSourceTransactionManager jeewebTransactionManager(
            @Qualifier("jeewebDataSource") DataSource jeewebDataSource) {
        return new DataSourceTransactionManager(jeewebDataSource);
    }

    @Bean(name = "jeewebSqlSessionFactory")
    @Primary
    public SqlSessionFactory jeewebSqlSessionFactory(@Qualifier("jeewebDataSource") DataSource jeewebDataSource)
            throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(jeewebDataSource);
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
}
