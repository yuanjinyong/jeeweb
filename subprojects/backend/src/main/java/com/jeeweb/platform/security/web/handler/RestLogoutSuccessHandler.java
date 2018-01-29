/**
 * 
 */
package com.jeeweb.platform.security.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.jeeweb.framework.core.model.Result;
import com.jeeweb.platform.security.service.RestTokenCacheService;
import com.jeeweb.platform.security.utils.ResponseUtil;

/**
 * @author 袁进勇
 *
 */
@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RestLogoutSuccessHandler.class);

    @Autowired
    private RestTokenCacheService restTokenCacheService;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler#onLogoutSuccess(javax.
     * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LOG.debug("登出成功。");

        // 登出成功后，清理token
        restTokenCacheService.removeToken(request.getHeader(RestTokenCacheService.REST_TOKEN));

        ResponseUtil.success(response, new Result(true, authentication == null ? "未检测到认证信息。" : Result.SUCCESS_MESSAGE));
    }
}