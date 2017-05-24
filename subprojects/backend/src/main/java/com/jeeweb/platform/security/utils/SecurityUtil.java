package com.jeeweb.platform.security.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jeeweb.platform.security.model.SecurityUser;
import com.jeeweb.platform.sys.entity.UserEntity;

public class SecurityUtil {
    public static SecurityUser getSecurityUser() {
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        if (token instanceof UsernamePasswordAuthenticationToken && token.getPrincipal() instanceof SecurityUser) {
            return (SecurityUser) token.getPrincipal();
        }

        return null;
    }

    public static String getToken() {
        SecurityUser user = getSecurityUser();
        if (user != null) {
            return user.getToken();
        }

        return null;
    }

    public static UserEntity getCurUser() {
        SecurityUser user = getSecurityUser();
        if (user != null) {
            return user.getUser();
        }

        return null;
    }
}
