/**
 * 
 */
package com.jeeweb.platform.security.web.handler;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import com.jeeweb.platform.security.context.RestContext;
import com.jeeweb.platform.security.model.SecurityUser;

/**
 * @author 袁进勇
 *
 */
@Component
public class RestAuthorizationSuccessHandler implements AuthorizationSuccessHandler {

    /*
     * (non-Javadoc)
     * 
     * @see com.zhuku.platform.security.handler.AuthorizationSuccessHandler#
     * onAuthorizationSuccess(org.springframework.
     * security.web.FilterInvocation)
     */
    @Override
    public void onAuthorizationSuccess(FilterInvocation fi) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof SecurityUser) {
                SecurityUser securityUser = (SecurityUser) principal;
                RestContext.setCurUser(securityUser.getUser());
            }
        }
    }
}
