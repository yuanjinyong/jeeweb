/**
 * 
 */
package com.jeeweb.platform.security.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.jeeweb.platform.redis.RedisService;


/**
 * @author 袁进勇
 *
 */
@Component
public class RestTokenService {
    public static final String REST_TOKEN = "X-REST-TOKEN";

    @Autowired
    private RedisService redisService;

    public String generateToken(Authentication authentication) {
        String token = UUID.randomUUID().toString();
        redisService.set(token, authentication, 24 * 60 * 60);
        return token;
    }

    public void removeToken(String token) {
        redisService.del(token);
    }

    public boolean validate(String token) {
        // TODO 后续需要改为Redis，添加过期时间
        return true;
    }

    public Authentication getAuthentication(String token) {
        return redisService.get(token, UsernamePasswordAuthenticationToken.class);
    }

    public String getTokenName() {
        return REST_TOKEN;
    }
}