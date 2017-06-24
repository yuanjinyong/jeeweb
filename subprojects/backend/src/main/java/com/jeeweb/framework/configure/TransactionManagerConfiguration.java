/**
 * 
 */
package com.jeeweb.framework.configure;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

/**
 * @author 袁进勇
 *
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class TransactionManagerConfiguration {
    @Value("${mybatis.transaction.timeout:30}")
    private Integer timeout; // 默认30秒超时
    @Value("${mybatis.transaction.enableLogging:true}")
    private String enableLogging;
    @Value("${mybatis.transaction.logBaseDir:${catalina.base}/logs/}")
    private String logBaseDir;
    @Value("${mybatis.transaction.logBaseName:tm_atomikos}")
    private String logBaseName; // 默认30秒超时

    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() throws SystemException {
        // ConfigProperties configProperties = com.atomikos.icatch.config.Configuration.getConfigProperties();
        // configProperties.setProperty(ConfigProperties.ENABLE_LOGGING_PROPERTY_NAME, enableLogging);
        // configProperties.setProperty(ConfigProperties.LOG_BASE_DIR_PROPERTY_NAME, logBaseDir);
        // configProperties.setProperty(ConfigProperties.LOG_BASE_NAME_PROPERTY_NAME,
        // logBaseName + HelpUtil.getNowTime("_yyyyMMddHHmmss"));
        // com.atomikos.icatch.config.Configuration.init();

        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(timeout);
        return userTransactionImp;
    }

    @Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
    public TransactionManager atomikosTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
    }

    @Bean(name = "transactionManager")
    @DependsOn({ "userTransaction", "atomikosTransactionManager" })
    public PlatformTransactionManager transactionManager() throws SystemException {
        UserTransaction userTransaction = userTransaction();
        JtaTransactionManager manager = new JtaTransactionManager(userTransaction, atomikosTransactionManager());
        return manager;
    }
}
