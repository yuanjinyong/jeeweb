/**
 * 
 */
package com.jeeweb.diy.configure;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import liquibase.integration.spring.SpringLiquibase;

/**
 * @author 袁进勇
 *
 */
@Configuration
public class DiyDataSourceConfiguration {
    @Value("${spring.datasource.diy.uniqueResourceName:diyDataSource}")
    private String uniqueResourceName;
    @Value("${spring.datasource.diy.url:#{null}}")
    private String url;
    @Value("${spring.datasource.diy.username:#{null}}")
    private String username;
    @Value("${spring.datasource.diy.password:#{null}}")
    private String password;

    @Value("${spring.datasource.diy.minPoolSize:3}")
    private Integer minPoolSize;
    @Value("${spring.datasource.diy.maxPoolSize:15}")
    private Integer maxPoolSize;
    @Value("${spring.datasource.diy.maxLifetime:120}")
    private Integer maxLifetime;
    @Value("${spring.datasource.diy.borrowConnectionTimeout:30}")
    private Integer borrowConnectionTimeout;
    @Value("${spring.datasource.diy.loginTimeout:30}")
    private Integer loginTimeout;
    @Value("${spring.datasource.diy.maintenanceInterval:60}")
    private Integer maintenanceInterval;
    @Value("${spring.datasource.schema.maxIdleTime:60}")
    private Integer maxIdleTime;
    @Value("${spring.datasource.schema.reapTimeout:120}")
    private Integer reapTimeout;
    @Value("${spring.datasource.schema.testQuery:SELECT 1}")
    private String testQuery;

    @Bean(name = "diyDataSource")
    public DataSource diyDataSource() throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setUrl(url);
        mysqlXaDataSource.setUser(username);
        mysqlXaDataSource.setPassword(password);

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName(uniqueResourceName);
        xaDataSource.setMinPoolSize(minPoolSize);
        xaDataSource.setMaxPoolSize(maxPoolSize);
        xaDataSource.setMaxLifetime(maxLifetime);
        xaDataSource.setBorrowConnectionTimeout(borrowConnectionTimeout);
        xaDataSource.setLoginTimeout(loginTimeout);
        xaDataSource.setMaintenanceInterval(maintenanceInterval);
        xaDataSource.setMaxIdleTime(maxIdleTime);
        xaDataSource.setReapTimeout(reapTimeout);
        xaDataSource.setTestQuery(testQuery);

        return xaDataSource;
    }

    @Bean(name = "diyLiquibase")
    public SpringLiquibase defaultLiquibase(@Qualifier("diyDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:/com/jeeweb/diy/**/mapper/*Liquibase.diy.sql");
        liquibase.setContexts("dev, verify, test, mirror, production");
        return liquibase;
    }
}
