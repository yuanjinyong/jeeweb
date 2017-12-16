/**
 * 
 */
package com.jeeweb.platform.startup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.jeeweb.framework.business.enums.Enums;
import com.jeeweb.framework.core.listener.Startup;
import com.jeeweb.framework.core.listener.StartupItem;
import com.jeeweb.framework.core.model.ParamsMap;
import com.jeeweb.platform.security.utils.RequestUtil;
import com.jeeweb.platform.sys.entity.UrlEntity;
import com.jeeweb.platform.sys.service.UrlService;

/**
 * @author 袁进勇
 *
 */
@Component
@Startup(order = 10, desc = "扫描更新URL")
public class LoadUrlStartupItem implements StartupItem {
    // private static final Logger LOG = LoggerFactory.getLogger(LoadUrlStartupItem.class);

    @Autowired
    private UrlService urlService;

    @Override
    public void invoke(ApplicationContext ac) {
        List<UrlEntity> entityList = new ArrayList<>();
        entityList.addAll(buildUrlEntityList(ac.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()));
        entityList.addAll(buildUrlEntityList(ac.getBean(EndpointHandlerMapping.class).getHandlerMethods()));
        urlService.deleteEntities(new ParamsMap("f_auto", Enums.TRUE));
        urlService.insertEntities(entityList);
    }

    private List<UrlEntity> buildUrlEntityList(Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
        List<UrlEntity> entityList = new ArrayList<>();
        for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            entityList.add(RequestUtil.buildUrlEntity(entry.getKey(), entry.getValue()));
        }
        return entityList;
    }
}
