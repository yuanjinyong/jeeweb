/**
 * 
 */
package com.jeeweb.platform.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.jeeweb.platform.security.exception.RestAnonymousAuthenticationException;

/**
 * @author 袁进勇
 *
 */
public class RestSecurityFilter extends AbstractSecurityInterceptor implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(RestSecurityFilter.class);
    private static final String FILTER_APPLIED = "__spring_security_rest_authorities_applied";

    private AuthenticationEntryPoint authenticationEntryPoint;
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    private List<RequestMatcher> ignoringMatchers = new ArrayList<>();
    private List<RequestMatcher> permitAllMatchers = new ArrayList<>();
    private List<RequestMatcher> authenticatedMatchers = new ArrayList<>();

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (isMatcher((HttpServletRequest) request, ignoringMatchers)) {
            chain.doFilter(request, response);
            return;
        }

        FilterInvocation fi = new FilterInvocation(request, response, chain);
        if (fi.getRequest().getAttribute(FILTER_APPLIED) != null) {
            // ensure that filter is only applied once per request
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        } else {
            fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
        }

        doFilterInternal(fi);
    }

    private void doFilterInternal(FilterInvocation fi) throws IOException, ServletException {
        // 如果为不登录也可访问的URL
        if (isMatcher(fi.getRequest(), permitAllMatchers)) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }

        // 如果为匿名用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            String errorMsg = String.format("匿名用户无权访问[%s][%s]，请先登录验证！", fi.getRequest().getMethod(),
                    fi.getRequest().getRequestURI().substring(fi.getRequest().getContextPath().length()));

            LOG.warn(errorMsg);
            authenticationEntryPoint.commence(fi.getRequest(), fi.getResponse(),
                    new RestAnonymousAuthenticationException(errorMsg));
            return;
        }

        // 如果为登录即可访问的URL
        if (isMatcher(fi.getRequest(), authenticatedMatchers)) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }

        // 将调用RestSecurityMetadataSource的getAttributes获取用户的权限，然后调用RestAccessDecisionManager的decide方法进行权限判断
        InterceptorStatusToken token = super.beforeInvocation(fi);

        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.finallyInvocation(token);
        }

        super.afterInvocation(token, null);
    }

    private boolean isMatcher(HttpServletRequest reques, List<RequestMatcher> matchers) {
        for (RequestMatcher matcher : matchers) {
            if (matcher.matches(reques)) {
                return true;
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.access.intercept.AbstractSecurityInterceptor#getSecureObjectClass()
     */
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.access.intercept.AbstractSecurityInterceptor#obtainSecurityMetadataSource()
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSource;
    }

    /**
     * @param authenticationEntryPoint
     */
    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * @param securityMetadataSource
     */
    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

    /**
     * @param antPatterns
     */
    public void addIgnoringMatchers(String... antPatterns) {
        for (String pattern : antPatterns) {
            ignoringMatchers.add(new AntPathRequestMatcher(pattern));
        }
    }

    /**
     * @param antPatterns
     */
    public void addPermitAllMatchers(String... antPatterns) {
        for (String pattern : antPatterns) {
            permitAllMatchers.add(new AntPathRequestMatcher(pattern));
        }
    }

    /**
     * @param antPatterns
     */
    public void addAuthenticatedMatchers(String... antPatterns) {
        for (String pattern : antPatterns) {
            authenticatedMatchers.add(new AntPathRequestMatcher(pattern));
        }
    }
}
