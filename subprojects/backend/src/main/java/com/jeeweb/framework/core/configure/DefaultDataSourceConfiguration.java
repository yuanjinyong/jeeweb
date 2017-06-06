/**
 * 
 */
package com.jeeweb.framework.core.configure;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * @author 袁进勇
 *
 */
@Configuration
public class DefaultDataSourceConfiguration {
    @Value("${spring.datasource.default.uniqueResourceName:defaultDataSource}")
    private String uniqueResourceName;
    @Value("${spring.datasource.default.url:#{null}}")
    private String url;
    @Value("${spring.datasource.default.username:#{null}}")
    private String user;
    @Value("${spring.datasource.default.password:#{null}}")
    private String password;

    @Value("${spring.datasource.default.minPoolSize:5}")
    private Integer minPoolSize;
    @Value("${spring.datasource.default.maxPoolSize:25}")
    private Integer maxPoolSize;
    @Value("${spring.datasource.default.maxLifetime:20000}")
    private Integer maxLifetime;
    @Value("${spring.datasource.default.borrowConnectionTimeout:30}")
    private Integer borrowConnectionTimeout;
    @Value("${spring.datasource.default.loginTimeout:30}")
    private Integer loginTimeout;
    @Value("${spring.datasource.default.maintenanceInterval:60}")
    private Integer maintenanceInterval;
    @Value("${spring.datasource.default.maxIdleTime:60}")
    private Integer maxIdleTime;
    @Value("${spring.datasource.default.testQuery:SELECT 1}")
    private String testQuery;

    @Primary
    @Bean(name = "defaultDataSource")
    public DataSource defaultDataSource() throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setUrl(url);
        mysqlXaDataSource.setPassword(password);
        mysqlXaDataSource.setUser(user);

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
        xaDataSource.setTestQuery(testQuery);

        return xaDataSource;
    }
}
