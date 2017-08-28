/**
 * 
 */
package com.jeeweb.platform.security.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.framework.jdbc.DataSourceHolder;
import com.jeeweb.platform.security.context.RestContext;
import com.jeeweb.platform.security.exception.RestException;
import com.jeeweb.platform.security.exception.RestInvalidTokenException;
import com.jeeweb.platform.security.service.RestTokenCacheService;
import com.jeeweb.platform.security.web.handler.RestAuthenticationEntryPoint;

/**
 * @author 袁进勇
 *
 */
@Component
public class RestTokenProcessingFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(RestTokenProcessingFilter.class);

    @Autowired
    private RestTokenCacheService restTokenCacheService;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /*
     * (non-Javadoc)
     * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        DataSourceHolder.clearDataSourceId();

        String token = getToken(request);
        if (HelpUtil.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 验证Token是否有效
            if (!restTokenCacheService.validate(token)) {
                LOG.warn("无效的" + RestTokenCacheService.REST_TOKEN + "：" + token);
                new RestInvalidTokenException("无效的" + RestTokenCacheService.REST_TOKEN + "：" + token);
            }

            Authentication authentication = restTokenCacheService.getAuthentication(token);
            if (authentication == null) {
                LOG.warn("无效的" + RestTokenCacheService.REST_TOKEN + "：" + token);
                new RestInvalidTokenException("无效的" + RestTokenCacheService.REST_TOKEN + "：" + token);
            }

            // Set the authentication into the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            RestContext.setToken(token);
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                restAuthenticationEntryPoint.commence(request, response, (AuthenticationException) e);
            } else {
                restAuthenticationEntryPoint.commence(request, response, new RestException(e.getMessage(), e));
            }

            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(RestTokenCacheService.REST_TOKEN);
        if (!HelpUtil.isEmpty(token)) {
            return token;
        }

        Cookie[] cookies = request.getCookies();
        if (!HelpUtil.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(RestTokenCacheService.REST_TOKEN)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
