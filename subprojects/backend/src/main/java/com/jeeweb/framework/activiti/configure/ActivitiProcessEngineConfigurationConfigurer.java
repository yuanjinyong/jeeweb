/**
 * 
 */
package com.jeeweb.framework.activiti.configure;

import javax.sql.DataSource;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 袁进勇
 *
 */
@Component
public class ActivitiProcessEngineConfigurationConfigurer implements ProcessEngineConfigurationConfigurer {
    @Value("${spring.activiti.jobExecutorActivate:true}")
    private Boolean jobExecutorActivate;
    @Value("${spring.activiti.asyncExecutorActivate:true}")
    private Boolean asyncExecutorActivate;

    @Autowired
    @Qualifier("activitiDataSource")
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
        processEngineConfiguration.setJobExecutorActivate(jobExecutorActivate);
        processEngineConfiguration.setAsyncExecutorActivate(asyncExecutorActivate);

        // 设置字体解决中文乱码
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setAnnotationFontName("宋体");

    }

}
