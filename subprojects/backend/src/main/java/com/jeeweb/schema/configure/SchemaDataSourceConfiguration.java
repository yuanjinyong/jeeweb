/**
 * 
 */
package com.jeeweb.schema.configure;

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
public class SchemaDataSourceConfiguration {
    @Value("${spring.datasource.schema.uniqueResourceName:schemaDataSource}")
    private String uniqueResourceName;
    @Value("${spring.datasource.schema.url:#{null}}")
    private String url;
    @Value("${spring.datasource.schema.username:#{null}}")
    private String user;
    @Value("${spring.datasource.schema.password:#{null}}")
    private String password;

    @Value("${spring.datasource.schema.minPoolSize:1}")
    private Integer minPoolSize;
    @Value("${spring.datasource.schema.maxPoolSize:25}")
    private Integer maxPoolSize;
    @Value("${spring.datasource.schema.maxLifetime:20000}")
    private Integer maxLifetime;
    @Value("${spring.datasource.schema.borrowConnectionTimeout:30}")
    private Integer borrowConnectionTimeout;
    @Value("${spring.datasource.schema.loginTimeout:30}")
    private Integer loginTimeout;
    @Value("${spring.datasource.schema.maintenanceInterval:60}")
    private Integer maintenanceInterval;
    @Value("${spring.datasource.schema.maxIdleTime:60}")
    private Integer maxIdleTime;
    @Value("${spring.datasource.schema.testQuery:SELECT 1}")
    private String testQuery;

    @Bean(name = "schemaDataSource")
    public DataSource schemaDataSource() throws SQLException {
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
