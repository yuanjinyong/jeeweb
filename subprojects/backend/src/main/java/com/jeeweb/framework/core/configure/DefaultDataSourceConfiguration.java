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
    @Value("${spring.datasource.default.maxLifetime:120}")
    private Integer maxLifetime;
    @Value("${spring.datasource.default.borrowConnectionTimeout:30}")
    private Integer borrowConnectionTimeout;
    @Value("${spring.datasource.default.loginTimeout:30}")
    private Integer loginTimeout;
    @Value("${spring.datasource.default.maintenanceInterval:60}")
    private Integer maintenanceInterval;
    @Value("${spring.datasource.default.maxIdleTime:60}")
    private Integer maxIdleTime;
    @Value("${spring.datasource.default.reapTimeout:120}")
    private Integer reapTimeout;
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
        // 连接最大存活时间，超过这个且没有正在使用的连接将自动销毁，0无限制，1000=1000s。
        // 对于一些会自动中断连接的数据库如MySQL，可以设置这个参数，在达到这个时间的时候会自动关闭连接，下次数据库调用的时候就会新建
        xaDataSource.setMaxLifetime(maxLifetime);
        // 获取连接失败重新获等待最大时间，在这个时间内如果有可用连接，将返回
        xaDataSource.setBorrowConnectionTimeout(borrowConnectionTimeout);
        // Java数据库连接池，最大可等待获取datasouce的时间
        xaDataSource.setLoginTimeout(loginTimeout);
        // 连接回收时间
        xaDataSource.setMaintenanceInterval(maintenanceInterval);
        // 最大闲置时间，超过最小连接池连接的连接将将关闭
        xaDataSource.setMaxIdleTime(maxIdleTime);
        // 最大获取数据时间，如果不设置这个值，Atomikos使用默认的5分钟。
        // 在处理大批量数据读取的时候，一旦超过这个值，就会抛出类似 Resultset is close的错误
        xaDataSource.setReapTimeout(reapTimeout);
        xaDataSource.setTestQuery(testQuery);

        return xaDataSource;
    }
}
