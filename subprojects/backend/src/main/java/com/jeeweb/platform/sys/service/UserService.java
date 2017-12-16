package com.jeeweb.platform.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.enums.Enums;
import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParamsMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.framework.core.utils.TreeUtil;
import com.jeeweb.platform.security.service.PasswordService;
import com.jeeweb.platform.security.utils.SecurityUtil;
import com.jeeweb.platform.sys.entity.UserEntity;
import com.jeeweb.platform.sys.enums.UserStatus;
import com.jeeweb.platform.sys.mapper.UserMapper;
import com.jeeweb.platform.sys.mapper.UserMenuMapper;
import com.jeeweb.platform.sys.mapper.UserRoleMapper;
import com.jeeweb.platform.sys.utils.SysUtil;

@Service
@Transactional
public class UserService extends BaseService<Long, UserEntity> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMenuMapper userMenuMapper;

    @Autowired
    private PasswordService passwordService;

    @Override
    protected BaseMapper<Long, UserEntity> getMapper() {
        return userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity selectEntity(Long primaryKey) {
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
    public void updateEntity(Long primaryKey, UserEntity entity) {
        UserEntity oldEntity = super.selectEntity(entity.getF_id());
        if (UserStatus.DEREGISTER.equals(oldEntity.getF_status())) {
            throw new BusinessException("已注销用户不能进行此操作！");
        }

        deleteRoleList(entity.getF_id());
        insertRoleList(entity);

        super.updateEntity(primaryKey, entity);
    }

    @Override
    public void deleteEntity(Long primaryKey) {
        deleteUserMenu(primaryKey);
        deleteRoleList(primaryKey);

        // 最后在删除自己
        super.deleteEntity(primaryKey);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional(readOnly = true)
    public RowMap selectUserMenuList(Long f_user_id) {
        ParamsMap params = new ParamsMap();
        params.put("f_user_id", f_user_id);
        params.put("f_status", Enums.ENABLE);
        params.put("orderBy", "f_parent_path,f_order");
        SysUtil.appendCurUserAndRoles(params);
        List<RowMap> distMenus = TreeUtil.listToTree(userMenuMapper.selectDistMenuListPage(params), "f_id");
        List<RowMap> authMenus = TreeUtil.listToTree(userMenuMapper.selectAuthMenuListPage(params), "f_id");
        return new RowMap("distMenus", distMenus).put("authMenus", authMenus);
    }

    public void updateUserMenuList(Long f_user_id, List<String> distMenuIds, List<String> authMenuIds) {
        // 先删除用户下关联的菜单
        deleteUserMenu(f_user_id);

        // 在重新插入用户下关联的菜单
        insertUserMenu(f_user_id, distMenuIds, authMenuIds);
    }

    private void insertUserMenu(Long f_user_id, List<String> distMenuIds, List<String> authMenuIds) {
        if (!HelpUtil.isEmpty(distMenuIds)) {
            List<RowMap> userMenuList = new ArrayList<>();
            for (String f_menu_id : distMenuIds) {
                RowMap userMenu = new RowMap();
                userMenuList.add(userMenu);

                userMenu.put("f_user_id", f_user_id);
                userMenu.put("f_menu_id", f_menu_id);
            }
            userMenuMapper.insertDistMenus(userMenuList);
        }

        if (!HelpUtil.isEmpty(authMenuIds)) {
            List<RowMap> userMenuList = new ArrayList<>();
            for (String f_menu_id : authMenuIds) {
                RowMap userMenu = new RowMap();
                userMenuList.add(userMenu);

                userMenu.put("f_user_id", f_user_id);
                userMenu.put("f_menu_id", f_menu_id);
            }
            userMenuMapper.insertAuthMenus(userMenuList);
        }
    }

    private void deleteUserMenu(Long f_user_id) {
        // 删除用户下关联的菜单
        userMenuMapper.deleteDistMenus(new ParamsMap("f_user_id", f_user_id));
        userMenuMapper.deleteAuthMenus(new ParamsMap("f_user_id", f_user_id));
    }

    private void fillUserEntity(UserEntity user) {
        user.setF_tenant_id(0L);
        user.setF_department_id(0L);
        user.setF_password(user.getF_password() == null ? passwordService.generatePassword()
                : passwordService.encodePassword(user.getF_password()));
        user.setF_is_can_login(Enums.TRUE); // 一般只有预置的用户才设置为不能登录。
        user.setF_status(UserStatus.NORMAL);
    }

    private List<Long> selectRoleIdList(Long f_user_id) {
        List<RowMap> roleList = userRoleMapper.selectEntityListPage(new ParamsMap("f_user_id", f_user_id));
        List<Long> roleIdList = new ArrayList<>();
        for (RowMap role : roleList) {
            roleIdList.add(role.$long("f_role_id", null));
        }

        return roleIdList;
    }

    private void insertRoleList(UserEntity user) {
        List<Long> roleIdList = user.getRoleIdList();
        if (!HelpUtil.isEmpty(roleIdList)) {

            List<RowMap> roleList = new ArrayList<>();
            for (Long roleId : roleIdList) {
                RowMap role = new RowMap("f_user_id", user.getF_id());
                role.put("f_role_id", roleId);
                roleList.add(role);
            }

            userRoleMapper.insertEntities(roleList);
        }
    }

    private void deleteRoleList(Long f_user_id) {
        userRoleMapper.deleteEntities(new ParamsMap("f_user_id", f_user_id));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void deregisterUser(Long primaryKey) {
        UserEntity entity = super.selectEntity(primaryKey);
        if (UserStatus.DEREGISTER.equals(entity.getF_status())) {
            throw new BusinessException("已注销用户不能进行此操作！");
        }
        if (Enums.$true(entity.getF_is_preset())) {
            throw new BusinessException("系统预置用户不能进行此操作！");
        }

        entity.setF_unregister_time(HelpUtil.getNowTime());
        entity.setF_status(UserStatus.DEREGISTER);
        entity.setF_remark("被操作员【" + SecurityUtil.getCurUser().getF_name() + "】注销。");
        super.updateEntity(primaryKey, entity);
    }

    public void unlockUser(Long primaryKey) {
        UserEntity entity = super.selectEntity(primaryKey);
        if (!UserStatus.LOCKED.equals(entity.getF_status())) {
            throw new BusinessException("未锁定用户不能进行此操作！");
        }

        userMapper.unlockUser(primaryKey);
    }

    public void resetPassword(Long primaryKey) {
        UserEntity entity = super.selectEntity(primaryKey);
        if (UserStatus.DEREGISTER.equals(entity.getF_status())) {
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
