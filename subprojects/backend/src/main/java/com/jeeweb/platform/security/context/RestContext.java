package com.jeeweb.platform.security.context;

import com.jeeweb.platform.sys.entity.UserEntity;

public final class RestContext {
    private static final ThreadLocal<String> TOKEN_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<UserEntity> USER_HOLDER = new ThreadLocal<>();

    public static void setToken(String token) {
        TOKEN_HOLDER.set(token);
    }

    public static String getToken() {
        return TOKEN_HOLDER.get();
    }

    public static void setCurUser(UserEntity user) {
        USER_HOLDER.set(user);
    }

    public static UserEntity getCurUser() {
        return USER_HOLDER.get();
    }
}
