/**
 * 
 */
package com.jeeweb.platform.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 袁进勇
 *
 */
public class RestException extends AuthenticationException {
    private static final long serialVersionUID = -632338236560850565L;

    public RestException(String msg) {
        super(msg);
    }

    public RestException(String msg, Throwable t) {
        super(msg, t);
    }
}
