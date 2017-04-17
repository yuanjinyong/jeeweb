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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.jeeweb.framework.core.model.Result;
import com.jeeweb.framework.core.utils.ResponseUtil;

/**
 * @author 袁进勇
 *
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RestAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        LOG.warn("没有[{}][{}]的访问权限。", request.getMethod(),
                request.getRequestURI().substring(request.getContextPath().length()));
        ResponseUtil.failure(response, HttpServletResponse.SC_FORBIDDEN,
                new Result(accessDeniedException.getMessage()));
    }
}
