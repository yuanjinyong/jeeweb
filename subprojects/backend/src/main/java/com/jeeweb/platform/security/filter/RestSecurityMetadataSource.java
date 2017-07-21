/**
 * 
 */
package com.jeeweb.platform.security.filter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.jeeweb.platform.security.SecurityCacheManager;

/**
 * @author 袁进勇
 *
 */
@Service
public class RestSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private SecurityCacheManager securityCacheManager;

    // 根据request，找到相关的权限配置。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        Collection<ConfigAttribute> authorityList = securityCacheManager.getRequestAuthorities(fi.getRequest());
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
