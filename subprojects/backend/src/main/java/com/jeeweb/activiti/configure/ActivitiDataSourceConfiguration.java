/**
 * 
 */
package com.jeeweb.activiti.configure;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * @author 袁进勇
 *
 */
@Configuration
public class ActivitiDataSourceConfiguration {
    @Value("${spring.datasource.activiti.uniqueResourceName:activitiDataSource}")
    private String uniqueResourceName;
    @Value("${spring.datasource.activiti.url:#{null}}")
    private String url;
    @Value("${spring.datasource.activiti.username:#{null}}")
    private String username;
    @Value("${spring.datasource.activiti.password:#{null}}")
    private String password;

    @Value("${spring.datasource.activiti.minPoolSize:1}")
    private Integer minPoolSize;
    @Value("${spring.datasource.activiti.maxPoolSize:15}")
    private Integer maxPoolSize;
    @Value("${spring.datasource.activiti.maxLifetime:120}")
    private Integer maxLifetime;
    @Value("${spring.datasource.activiti.borrowConnectionTimeout:30}")
    private Integer borrowConnectionTimeout;
    @Value("${spring.datasource.activiti.loginTimeout:30}")
    private Integer loginTimeout;
    @Value("${spring.datasource.activiti.maintenanceInterval:60}")
    private Integer maintenanceInterval;
    @Value("${spring.datasource.activiti.maxIdleTime:60}")
    private Integer maxIdleTime;
    @Value("${spring.datasource.activiti.reapTimeout:120}")
    private Integer reapTimeout;
    @Value("${spring.datasource.activiti.testQuery:SELECT 1}")
    private String testQuery;

    @Bean(name = "activitiDataSource")
    public DataSource activitiDataSource() throws SQLException {
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
}
