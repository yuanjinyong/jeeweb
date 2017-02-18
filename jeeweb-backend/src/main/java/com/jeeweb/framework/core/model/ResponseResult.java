/**
 * 
 */
package com.jeeweb.framework.core.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;


/**
 * @author 袁进勇
 *
 */
public class ResponseResult extends ResponseEntity<Result> {
    public ResponseResult(Result body, HttpStatus status) {
        super(body, status);
    }

    public ResponseResult(Result body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }
}
