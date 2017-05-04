/**
 * 
 */
package com.jeeweb.platform.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.jeeweb.framework.core.model.Result;
import com.jeeweb.platform.security.exception.RestAnonymousAuthenticationException;
import com.jeeweb.platform.security.exception.RestInvalidTokenException;
import com.jeeweb.platform.security.utils.ResponseUtil;

/**
 * @author 袁进勇
 *
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOG = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

    /*
     * (non-Javadoc)
     * @see org.springframework.security.web.AuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof RestInvalidTokenException) {
            ResponseUtil.failure(response, HttpServletResponse.SC_UNAUTHORIZED, new Result(authException.getMessage()));
        } else if (authException instanceof RestAnonymousAuthenticationException) {
            ResponseUtil.failure(response, HttpServletResponse.SC_UNAUTHORIZED, new Result(authException.getMessage()));
        } else if (authException instanceof BadCredentialsException) {
            LOG.warn("账号密码错误！");
            ResponseUtil.failure(response, HttpServletResponse.SC_UNAUTHORIZED, new Result("账号密码错误！"));
        } else {
            LOG.warn("访问[{}][{}]需要先登录验证！", request.getMethod(),
                    request.getRequestURI().substring(request.getContextPath().length()));
            ResponseUtil.failure(response, HttpServletResponse.SC_UNAUTHORIZED, new Result("请先登录验证！"));
        }
    }
}
