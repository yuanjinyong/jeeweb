package com.jeeweb.platform.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.sys.entity.UserEntity;
import com.jeeweb.platform.sys.mapper.UserMapper;
import com.jeeweb.platform.sys.utils.SysUtil;

@Service
@Transactional
public class UserService extends BaseService<Integer, UserEntity> {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected BaseMapper<Integer, UserEntity> getMapper() {
        return userMapper;
    }

    public UserEntity selectUserByAccount(String f_account) {
        return userMapper.selectUserByAccount(f_account);
    }

    @Override
    public void insertEntity(UserEntity entity) {
        entity.setF_tenant_id(0);
        entity.setF_department_id(0);
        entity.setF_creator_id(SysUtil.getCurUser().getF_id());
        entity.setF_created_time(HelpUtil.getNowTime());
        entity.setF_password(passwordEncoder.encode("12345678")); // TODO
        entity.setF_status(UserEntity.STATUS_NORMAL);

        super.insertEntity(entity);
    }
}
