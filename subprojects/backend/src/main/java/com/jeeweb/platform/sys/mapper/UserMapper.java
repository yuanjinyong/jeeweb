package com.jeeweb.platform.sys.mapper;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.platform.sys.entity.UserEntity;

public interface UserMapper extends BaseMapper<Long, UserEntity> {
    UserEntity selectUserByAccount(String f_account);

    UserEntity selectUserByTelephone(String f_telephone);

    void unlockUser(Long primaryKey);
}
