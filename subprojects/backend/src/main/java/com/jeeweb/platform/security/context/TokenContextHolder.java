package com.jeeweb.platform.security.context;

/**
 * 
 * @author 袁进勇
 * 
 */
public class TokenContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setContext(String context) {
        contextHolder.set(context);
    }

    public static String getContext() {
        return contextHolder.get();
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
