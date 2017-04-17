/**
 * 
 */
package com.jeeweb.platform.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 袁进勇
 *
 */
public class RestInvalidTokenException extends AuthenticationException {
    private static final long serialVersionUID = -8570945003418057601L;

    public RestInvalidTokenException(String paramString) {
        super(paramString);
    }
}
