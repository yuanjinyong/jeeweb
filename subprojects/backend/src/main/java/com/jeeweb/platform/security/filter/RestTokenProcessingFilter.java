/**
 * 
 */
package com.jeeweb.platform.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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
import com.jeeweb.platform.security.exception.RestException;
import com.jeeweb.platform.security.exception.RestInvalidTokenException;
import com.jeeweb.platform.security.handler.RestAuthenticationEntryPoint;
import com.jeeweb.platform.security.service.RestTokenService;

/**
 * @author 袁进勇
 *
 */
@Component
public class RestTokenProcessingFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(RestTokenProcessingFilter.class);

    @Autowired
    private RestTokenService restTokenService;
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
        String token = request.getHeader(RestTokenService.REST_TOKEN);
        if (HelpUtil.isEmpty(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 验证Token是否有效
            if (!restTokenService.validate(token)) {
                LOG.warn("无效的" + RestTokenService.REST_TOKEN + "：" + token);
                new RestInvalidTokenException("无效的" + RestTokenService.REST_TOKEN + "：" + token);
            }

            Authentication authentication = restTokenService.getAuthentication(token);
            if (authentication == null) {
                LOG.warn("无效的" + RestTokenService.REST_TOKEN + "：" + token);
                new RestInvalidTokenException("无效的" + RestTokenService.REST_TOKEN + "：" + token);
            }

            // Set the authentication into the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
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
}
