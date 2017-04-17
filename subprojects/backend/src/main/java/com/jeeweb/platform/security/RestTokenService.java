/**
 * 
 */
package com.jeeweb.platform.security;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author 袁进勇
 *
 */
@Component
public class RestTokenService {
    public static final String REST_TOKEN = "X-REST-TOKEN";
    private static Map<String, Authentication> tokenCache = new HashMap<String, Authentication>();

    public String generateToken(Authentication authentication) {
        // TODO 后续需要改为Redis，添加过期时间
        String token = UUID.randomUUID().toString();
        tokenCache.put(token, authentication);
        return token;
    }

    public void removeToken(String token) {
        // TODO 后续需要改为Redis，添加过期时间
        tokenCache.remove(token);
    }

    public boolean validate(String token) {
        // TODO 后续需要改为Redis，添加过期时间
        return tokenCache.containsKey(token);
    }

    public Authentication getAuthentication(String token) {
        return tokenCache.get(token);
    }

    public String getTokenName() {
        return REST_TOKEN;
    }
}
