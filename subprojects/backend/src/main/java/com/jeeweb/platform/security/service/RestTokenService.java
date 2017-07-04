/**
 * 
 */
package com.jeeweb.platform.security.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.framework.redis.ByteRedisTemplate;
import com.jeeweb.platform.security.model.SecurityUser;
import com.jeeweb.platform.sys.entity.UserEntity;
import com.jeeweb.platform.sys.mapper.UserMapper;

/**
 * @author 袁进勇
 *
 */
@Component
public class RestTokenService {
    public static final String REST_TOKEN = "X-REST-TOKEN";

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ByteRedisTemplate<Authentication> byteRedisTemplate;

    public String generateToken(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        UserEntity entity = securityUser.getUser();
        entity.setF_last_login_time(HelpUtil.getNowTime()); // TODO 还需要清空错误次数
        userMapper.updateEntity(entity);

        String token = UUID.randomUUID().toString();
        securityUser.setToken(token);
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

    public void updateAuthorities(List<GrantedAuthority> authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        String token = securityUser.getToken();

        SecurityUser newSecurityUser = new SecurityUser(securityUser.getUser(), authorities);
        newSecurityUser.setToken(token);

        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(newSecurityUser,
                authentication.getCredentials(), authorities);
        newAuthentication.setDetails(authentication.getDetails());
        byteRedisTemplate.opsForValue().set("token:" + token, newAuthentication, 1, TimeUnit.DAYS);
    }
}
