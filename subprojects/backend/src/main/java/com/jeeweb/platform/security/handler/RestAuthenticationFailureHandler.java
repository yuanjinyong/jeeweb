/**
 * 
 */
package com.jeeweb.platform.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.jeeweb.framework.core.model.Result;
import com.jeeweb.platform.security.utils.ResponseUtil;

/**
 * @author 袁進勇
 *
 */
@Component
public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RestAuthenticationFailureHandler.class);

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler#onAuthenticationFailure(
     * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadCredentialsException) {
            LOG.warn("账号密码错误！");
            ResponseUtil.failure(response, HttpServletResponse.SC_UNAUTHORIZED, new Result("账号密码错误！"));
        } else {
            LOG.warn("登录失败！");
            ResponseUtil.failure(response, HttpServletResponse.SC_UNAUTHORIZED, new Result("登录失败!"));
        }
    }
}
