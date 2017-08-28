/**
 * 
 */
package com.jeeweb.platform.security.web.handler;

import org.springframework.security.web.FilterInvocation;

/**
 * @author 授权验证通过后的回调处理
 *
 */
public interface AuthorizationSuccessHandler {
    void onAuthorizationSuccess(FilterInvocation fi);
}
