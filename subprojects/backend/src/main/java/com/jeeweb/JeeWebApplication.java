/**
 * 
 */
package com.jeeweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.jeeweb.framework.core.listener.ApplicationStartupListener;

/**
 * @author 袁进勇
 *
 */
// @SpringBootApplication(scanBasePackages = { "com.jeeweb.**.web.api.**", "com.jeeweb.**.service.**",
// "com.jeeweb.**.mapper.**", "com.jeeweb.framework.**", "com.jeeweb.platform.**", "com.jeeweb.schema.**" })
@SpringBootApplication
public class JeeWebApplication extends SpringBootServletInitializer {
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.boot.web.support.SpringBootServletInitializer#configure(org.springframework.boot.builder.
     * SpringApplicationBuilder)
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.application().addListeners(new ApplicationStartupListener());
        return builder.sources(JeeWebApplication.class);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(JeeWebApplication.class);
        springApplication.addListeners(new ApplicationStartupListener());
        springApplication.run(args);
    }
}
