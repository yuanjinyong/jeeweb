/**
 * 
 */
package com.jeeweb.platform.security.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.jeeweb.platform.redis.ByteRedisTemplate;

/**
 * @author 袁进勇
 *
 */
@Component
public class RestTokenService {
    public static final String REST_TOKEN = "X-REST-TOKEN";

    @Autowired
    private ByteRedisTemplate<Authentication> byteRedisTemplate;

    public String generateToken(Authentication authentication) {
        String token = UUID.randomUUID().toString();
        byteRedisTemplate.opsForValue().set("token:" + token, authentication, 1, TimeUnit.DAYS);
        return token;
    }

    public void removeToken(String token) {
        byteRedisTemplate.delete("token:" + token);
    }

    public boolean validate(String token) {
        // TODO 后续需要改为Redis，添加过期时间
        return true;
    }

    public Authentication getAuthentication(String token) {
        return byteRedisTemplate.opsForValue().get("token:" + token);
    }
}
