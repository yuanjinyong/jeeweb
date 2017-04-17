/**
 * 
 */
package com.jeeweb.platform.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 袁进勇
 *
 */
public class RestAnonymousAuthenticationException extends AuthenticationException {
    private static final long serialVersionUID = 672797022473184206L;

    public RestAnonymousAuthenticationException(String paramString) {
        super(paramString);
    }
}
