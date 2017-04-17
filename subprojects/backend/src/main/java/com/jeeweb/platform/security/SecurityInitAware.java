/**
 * 
 */
package com.jeeweb.platform.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.platform.security.utils.RequestMappingInfoUtil;
import com.jeeweb.platform.sys.entity.UrlEntity;
import com.jeeweb.platform.sys.mapper.UrlMapper;

/**
 * @author 袁进勇
 *
 */
@Component
public class SecurityInitAware implements InitializingBean {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityInitAware.class);

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private SecurityCacheManager securityCacheManager;

    @Autowired
    private UrlMapper urlMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    /**
     * 启动时调用该方法
     */
    public void init() {
        long startTime = System.currentTimeMillis();
        LOG.info("====加载URL和权限控制信息。");
        updateUrls();
        securityCacheManager.loadCache(requestMappingHandlerMapping);
        LOG.info("====URL和权限控制信息加载完成，耗时：{}ms。", (System.currentTimeMillis() - startTime));
    }

    private void updateUrls() {
        LOG.info("========更新t_sys_url开始========");

        long startTime = System.currentTimeMillis();
        List<UrlEntity> entityList = new ArrayList<UrlEntity>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            entityList.add(RequestMappingInfoUtil.buildUrlEntity(entry.getKey(), entry.getValue()));
        }

        urlMapper.deleteEntities(new ParameterMap("f_auto", true));
        urlMapper.insertEntities(entityList);

        LOG.info("========更新t_sys_url完成，耗时：{}ms========", (System.currentTimeMillis() - startTime));
    }
}
