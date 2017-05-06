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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.jeeweb.platform.security.service.RestTokenService;
import com.jeeweb.platform.security.utils.ResponseUtil;


/**
 * @author 袁进勇
 *
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RestAuthenticationSuccessHandler.class);

    @Autowired
    private RestTokenService restTokenService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler#onAuthenticationSuccess(
     * javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        LOG.debug("登录成功。");

        clearAuthenticationAttributes(request);

        // 登录成功后，生成新的Token设置到响应头中
        response.setHeader(restTokenService.getTokenName(), restTokenService.generateToken(authentication));

        ResponseUtil.success(response);
    }
}
