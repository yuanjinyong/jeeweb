package com.jeeweb.platform.security.context;

import com.jeeweb.platform.sys.entity.UserEntity;

/**
 * 
 * @author 袁进勇
 * 
 */
public class UserContextHolder {
    private static final ThreadLocal<UserEntity> contextHolder = new ThreadLocal<>();

    public static void setContext(UserEntity context) {
        contextHolder.set(context);
    }

    public static UserEntity getContext() {
        return contextHolder.get();
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
