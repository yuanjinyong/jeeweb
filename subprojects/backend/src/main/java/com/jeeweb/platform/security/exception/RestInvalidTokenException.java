/**
 * 
 */
package com.jeeweb.platform.security.exception;

/**
 * @author 袁进勇
 *
 */
public class RestInvalidTokenException extends RestException {
    private static final long serialVersionUID = -8570945003418057601L;

    public RestInvalidTokenException(String msg) {
        super(msg);
    }
}
