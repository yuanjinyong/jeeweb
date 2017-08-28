package com.jeeweb.platform.security.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeeweb.framework.business.web.controller.SuperController;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.platform.security.context.RestContext;
import com.jeeweb.platform.security.service.RestTokenCacheService;

@RestController
@RequestMapping(value = "/api/platform/security/token")
public class RestTokenApi extends SuperController {
    @Autowired
    private RestTokenCacheService restTokenCacheService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseResult getToken() {
        RestContext.setToken(restTokenCacheService.generateToken(SecurityContextHolder.getContext().getAuthentication()));

        HttpHeaders headers = new HttpHeaders();
        headers.add(RestTokenCacheService.REST_TOKEN, RestContext.getToken());
        return new ResponseResult(new Result(), headers, HttpStatus.OK);
    }
}
