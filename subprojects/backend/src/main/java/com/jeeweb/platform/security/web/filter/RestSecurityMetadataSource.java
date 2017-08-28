/**
 * 
 */
package com.jeeweb.platform.security.web.filter;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.jeeweb.platform.security.service.SecurityCacheService;

/**
 * @author 袁进勇
 *
 */
@Service
public class RestSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private static final Logger LOG = LoggerFactory.getLogger(RestSecurityMetadataSource.class);

    @Autowired
    private SecurityCacheService securityCacheService;

    // 根据request，找到相关的权限配置。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        Collection<ConfigAttribute> authorityList = securityCacheService.getRequestAuthorities(fi.getRequest());
        LOG.debug("需要权限[{}]。", authorityList);
        return authorityList;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}
