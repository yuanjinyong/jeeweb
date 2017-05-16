/**
 * 
 */
package com.jeeweb.platform.schema.configure;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author 袁进勇
 *
 */
@Configuration
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
public class InformationSchemaDataSourceConfiguration {
    @Autowired(required = false)
    private Interceptor[] interceptors;
    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    @Value("${schema.datasource.driverClassName:com.mysql.jdbc.Driver}")
    private String driverClass;
    @Value("${schema.datasource.url:#{null}}")
    private String url;
    @Value("${schema.datasource.username:#{null}}")
    private String user;
    @Value("${schema.datasource.password:#{null}}")
    private String password;

    @Bean(name = "schemabDataSource")
    public DataSource schemabDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "schemabSqlSessionFactory")
    public SqlSessionFactory schemabSqlSessionFactory(@Qualifier("schemabDataSource") DataSource schemabDataSource)
            throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(schemabDataSource);
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            factory.setPlugins(this.interceptors);
        }
        if (this.databaseIdProvider != null) {
            factory.setDatabaseIdProvider(this.databaseIdProvider);
        }
        return factory.getObject();
    }
}
