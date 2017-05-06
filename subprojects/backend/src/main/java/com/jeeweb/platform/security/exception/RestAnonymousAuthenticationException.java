/**
 * 
 */
package com.jeeweb.platform.security.exception;

/**
 * @author 袁进勇
 *
 */
public class RestAnonymousAuthenticationException extends RestException {
    private static final long serialVersionUID = 672797022473184206L;

    public RestAnonymousAuthenticationException(String msg) {
        super(msg);
    }
}
