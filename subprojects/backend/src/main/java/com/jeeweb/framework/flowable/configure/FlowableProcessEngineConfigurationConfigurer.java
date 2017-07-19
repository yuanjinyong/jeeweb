/**
 * 
 */
package com.jeeweb.framework.flowable.configure;

import javax.sql.DataSource;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 袁进勇
 *
 */
@Component
public class FlowableProcessEngineConfigurationConfigurer implements ProcessEngineConfigurationConfigurer {
    @Value("${spring.flowable.asyncExecutorActivate:true}")
    private Boolean asyncExecutorActivate;

    @Autowired
    @Qualifier("flowableDataSource")
    private DataSource dataSource;

    /*
     * (non-Javadoc)
     * 
     * @see org.flowable.spring.boot.ProcessEngineConfigurationConfigurer#configure(org.flowable.spring.
     * SpringProcessEngineConfiguration)
     */
    @Override
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
        processEngineConfiguration.setDataSource(dataSource);
        // processEngineConfiguration.setDatabaseSchemaUpdate("true");
        processEngineConfiguration.setAsyncExecutorActivate(asyncExecutorActivate);

        // 设置字体解决中文乱码
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");

    }

}
