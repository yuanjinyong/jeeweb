/**
 * 
 */
package com.jeeweb.platform.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.jeeweb.framework.core.listener.Startup;
import com.jeeweb.framework.core.listener.StartupItem;
import com.jeeweb.platform.security.service.SecurityCacheService;

/**
 * @author 袁进勇
 *
 */
@Component
@Startup(order = 11, desc = "加载权限配置")
public class LoadSecurityStartupItem implements StartupItem {
    // private static final Logger LOG = LoggerFactory.getLogger(LoadSecurityStartupItem.class);

    @Autowired
    private SecurityCacheService securityCacheService;

    @Override
    public void invoke(ApplicationContext ac) {
        securityCacheService.setRequestMappingHandlerMapping(ac.getBean(RequestMappingHandlerMapping.class));
        securityCacheService.setEndpointHandlerMapping(ac.getBean(EndpointHandlerMapping.class));
        securityCacheService.loadUrlAuthoritiesCache();
    }
}
