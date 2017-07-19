/**
 * 
 */
package com.jeeweb.framework.flowable.configure;

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
public class FlowableDataSourceConfiguration {
    @Value("${spring.datasource.flowable.uniqueResourceName:flowableDataSource}")
    private String uniqueResourceName;
    @Value("${spring.datasource.flowable.url:#{null}}")
    private String url;
    @Value("${spring.datasource.flowable.username:#{null}}")
    private String username;
    @Value("${spring.datasource.flowable.password:#{null}}")
    private String password;

    @Value("${spring.datasource.flowable.minPoolSize:5}")
    private Integer minPoolSize;
    @Value("${spring.datasource.flowable.maxPoolSize:25}")
    private Integer maxPoolSize;
    @Value("${spring.datasource.flowable.maxLifetime:120}")
    private Integer maxLifetime;
    @Value("${spring.datasource.flowable.borrowConnectionTimeout:30}")
    private Integer borrowConnectionTimeout;
    @Value("${spring.datasource.flowable.loginTimeout:30}")
    private Integer loginTimeout;
    @Value("${spring.datasource.flowable.maintenanceInterval:60}")
    private Integer maintenanceInterval;
    @Value("${spring.datasource.flowable.maxIdleTime:60}")
    private Integer maxIdleTime;
    @Value("${spring.datasource.flowable.reapTimeout:120}")
    private Integer reapTimeout;
    @Value("${spring.datasource.flowable.testQuery:SELECT 1}")
    private String testQuery;

    @Bean(name = "flowableDataSource")
    public DataSource flowableDataSource() throws SQLException {
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
