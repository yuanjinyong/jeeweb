package com.jeeweb.platform.security.utils;

import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.platform.security.context.TokenContextHolder;
import com.jeeweb.platform.security.context.UserContextHolder;
import com.jeeweb.platform.sys.entity.UserEntity;

public class SecurityUtil {
    public static String getToken() {
        return TokenContextHolder.getContext();
    }

    public static Integer getCurUserId() {
        return getCurUser().getF_id();
    }

    public static UserEntity getCurUser() {
        UserEntity userEntity = getCurUser(null);
        if (userEntity == null) {
            throw new BusinessException("获取用户信息失败，请先登录！");
        }

        return userEntity;
    }

    public static UserEntity getCurUser(UserEntity defaultValue) {
        UserEntity userEntity = UserContextHolder.getContext();
        if (userEntity == null) {
            return defaultValue;
        }
        return userEntity;
    }
}
