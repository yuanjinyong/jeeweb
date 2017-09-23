package com.jeeweb.platform.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.model.IPreset;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.security.service.PasswordService;
import com.jeeweb.platform.security.utils.SecurityUtil;
import com.jeeweb.platform.sys.entity.UserEntity;
import com.jeeweb.platform.sys.mapper.UserMapper;
import com.jeeweb.platform.sys.mapper.UserMenuMapper;
import com.jeeweb.platform.sys.mapper.UserRoleMapper;
import com.jeeweb.platform.sys.utils.SysUtil;

@Service
@Transactional
public class UserService extends BaseService<Integer, UserEntity> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMenuMapper userMenuMapper;

    @Autowired
    private PasswordService passwordService;

    @Override
    protected BaseMapper<Integer, UserEntity> getMapper() {
        return userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity selectEntity(Integer primaryKey) {
        UserEntity entity = super.selectEntity(primaryKey);
        entity.setRoleIdList(selectRoleIdList(primaryKey));
        return entity;
    }

    @Override
    public void insertEntity(UserEntity entity) {
        fillUserEntity(entity);

        super.insertEntity(entity);

        insertRoleList(entity);
    }

    @Override
    public void updateEntity(Integer primaryKey, UserEntity entity) {
        UserEntity oldEntity = super.selectEntity(entity.getF_id());
        if (oldEntity.getF_status() == UserEntity.STATUS_DEREGISTER) {
            throw new BusinessException("已注销用户不能进行此操作！");
        }

        deleteRoleList(entity.getF_id());
        insertRoleList(entity);

        super.updateEntity(primaryKey, entity);
    }

    @Override
    public void deleteEntity(Integer primaryKey) {
        deleteRoleList(primaryKey);

        // 最后在删除自己
        super.deleteEntity(primaryKey);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional(readOnly = true)
    public List<RowMap> selectUserMenuListPage(Integer f_user_id) {
        ParameterMap params = new ParameterMap();
        params.put("f_user_id", f_user_id);
        params.put("f_status", 1);
        params.put("orderBy", "f_parent_path,f_order");
        SysUtil.appendCurUserAndRoles(params);
        return userMenuMapper.selectUserMenuListPage(params);
    }

    public void updateUserMenuList(Integer f_user_id, List<String> f_menu_ids) {
        // 先删除角色下关联的菜单
        deleteUserMenu(f_user_id);

        // 在重新插入角色下关联的菜单
        insertUserMenu(f_user_id, f_menu_ids);
    }

    private void insertUserMenu(Integer f_user_id, List<String> f_menu_ids) {
        if (!HelpUtil.isEmpty(f_menu_ids)) {
            List<RowMap> userMenuList = new ArrayList<>();
            for (String f_menu_id : f_menu_ids) {
                RowMap userMenu = new RowMap();
                userMenuList.add(userMenu);

                userMenu.put("f_user_id", f_user_id);
                userMenu.put("f_menu_id", f_menu_id);
            }
            userMenuMapper.insertEntities(userMenuList);
        }
    }

    private void deleteUserMenu(Integer f_user_id) {
        // 删除角色下关联的菜单
        userMenuMapper.deleteEntities(new ParameterMap("f_user_id", f_user_id));
    }

    private void fillUserEntity(UserEntity user) {
        user.setF_tenant_id(0);
        user.setF_department_id(0);
        user.setF_password(user.getF_password() == null ? passwordService.generatePassword()
                : passwordService.encodePassword(user.getF_password()));
        user.setF_is_can_login(1); // 一般只有预置的用户才设置为不能登录。
        user.setF_status(UserEntity.STATUS_NORMAL);
    }

    private List<Integer> selectRoleIdList(Integer f_user_id) {
        List<RowMap> roleList = userRoleMapper.selectEntityListPage(new ParameterMap("f_user_id", f_user_id));
        List<Integer> roleIdList = new ArrayList<>();
        for (RowMap role : roleList) {
            roleIdList.add(role.getInteger("f_role_id", null));
        }

        return roleIdList;
    }

    private void insertRoleList(UserEntity user) {
        List<Integer> roleIdList = user.getRoleIdList();
        if (!HelpUtil.isEmpty(roleIdList)) {

            List<RowMap> roleList = new ArrayList<>();
            for (Integer roleId : roleIdList) {
                RowMap role = new RowMap("f_user_id", user.getF_id());
                role.put("f_role_id", roleId);
                roleList.add(role);
            }

            userRoleMapper.insertEntities(roleList);
        }
    }

    private void deleteRoleList(Integer f_user_id) {
        userRoleMapper.deleteEntities(new ParameterMap("f_user_id", f_user_id));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void deregisterUser(Integer primaryKey) {
        UserEntity entity = super.selectEntity(primaryKey);
        if (entity.getF_status() == UserEntity.STATUS_DEREGISTER) {
            throw new BusinessException("已注销用户不能进行此操作！");
        }
        if (entity.getF_is_preset() == IPreset.YES) {
            throw new BusinessException("系统预置用户不能进行此操作！");
        }

        entity.setF_unregister_time(HelpUtil.getNowTime());
        entity.setF_status(UserEntity.STATUS_DEREGISTER);
        entity.setF_remark("被操作员【" + SecurityUtil.getCurUser().getF_name() + "】注销。");
        super.updateEntity(primaryKey, entity);
    }

    public void unlockUser(Integer primaryKey) {
        UserEntity entity = super.selectEntity(primaryKey);
        if (entity.getF_status() != UserEntity.STATUS_LOCKED) {
            throw new BusinessException("未锁定用户不能进行此操作！");
        }

        userMapper.unlockUser(primaryKey);
    }

    public void resetPassword(Integer primaryKey) {
        UserEntity entity = super.selectEntity(primaryKey);
        if (entity.getF_status() == UserEntity.STATUS_DEREGISTER) {
            throw new BusinessException("已注销用户不能进行此操作！");
        }

        entity.setF_password(passwordService.generatePassword());
        super.updateEntity(primaryKey, entity);
    }

    public void changePassword(String oldPassword, String newPassword) {
        UserEntity entity = userMapper.selectEntity(SecurityUtil.getCurUserId());
        if (!passwordService.validatePassword(oldPassword, entity.getF_password())) {
            throw new BusinessException("旧密码不正确！");
        }

        entity.setF_password(passwordService.encodePassword(newPassword));
        super.updateEntity(entity.getF_id(), entity);
    }
}
