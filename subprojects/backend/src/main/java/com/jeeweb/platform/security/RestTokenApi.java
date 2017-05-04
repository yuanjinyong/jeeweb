package com.jeeweb.platform.security;

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

@RestController
@RequestMapping(value = "/api/token")
public class RestTokenApi extends SuperController {
    @Autowired
    private RestTokenService restTokenService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseResult getToken() {
        String token = restTokenService.generateToken(SecurityContextHolder.getContext().getAuthentication());
        HttpHeaders headers = new HttpHeaders();
        headers.add(restTokenService.getTokenName(), token);
        return new ResponseResult(new Result(), headers, HttpStatus.OK);
    }
}