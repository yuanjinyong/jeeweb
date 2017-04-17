package com.jeeweb.platform.sys.mapper;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.platform.sys.entity.UserEntity;

public interface UserMapper extends BaseMapper<Integer, UserEntity> {
    UserEntity selectUserByAccount(String f_account);
}
