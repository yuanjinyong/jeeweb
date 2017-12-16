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
import com.jeeweb.platform.security.utils.SecurityUtil;
import com.jeeweb.platform.sys.entity.RoleEntity;
import com.jeeweb.platform.sys.mapper.RoleMapper;
import com.jeeweb.platform.sys.mapper.RoleMenuMapper;
import com.jeeweb.platform.sys.utils.SysUtil;

@Service
@Transactional
public class RoleService extends BaseService<Long, RoleEntity> {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    protected BaseMapper<Long, RoleEntity> getMapper() {
        return roleMapper;
    }

    @Override
    public void deleteEntity(Long primaryKey) {
        // 先删除角色下关联的菜单
        deleteRoleMenu(primaryKey);

        // 再删除角色
        super.deleteEntity(primaryKey);
    }

    @Override
    public void deleteEntities(ParamsMap params) {
        // 删除角色下关联的菜单
        roleMenuMapper.deleteDistMenus(new ParamsMap("f_role_id_in", params.$("f_id_in")));
        roleMenuMapper.deleteAuthMenus(new ParamsMap("f_role_id_in", params.$("f_id_in")));

        getMapper().deleteEntities(params);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional(readOnly = true)
    public RowMap selectRoleMenuList(Long roleId) {
        ParamsMap params = new ParamsMap();
        params.put("f_role_id", roleId);
        params.put("f_status", Enums.ENABLE);
        params.put("orderBy", "f_parent_path,f_order");
        SysUtil.appendCurUserAndRoles(params);
        List<RowMap> distMenus = TreeUtil.listToTree(roleMenuMapper.selectDistMenuListPage(params), "f_id");
        List<RowMap> authMenus = TreeUtil.listToTree(roleMenuMapper.selectAuthMenuListPage(params), "f_id");
        return new RowMap("distMenus", distMenus).put("authMenus", authMenus);
    }

    public void updateRoleMenuList(Long f_role_id, List<String> distMenuIds, List<String> authMenuIds) {
        // 只有超级管理员账号才能给系统管理员角色授权
        if (f_role_id == RoleEntity.ID_ADMIN_SYS && !SecurityUtil.getCurUser().isSuperAdmin()) {
            throw new BusinessException("系统管理员角色的授权不能被修改！");
        }

        // 先删除角色下关联的菜单
        deleteRoleMenu(f_role_id);

        // 在重新插入角色下关联的菜单
        insertRoleMenu(f_role_id, distMenuIds, authMenuIds);
    }

    private void insertRoleMenu(Long f_role_id, List<String> distMenuIds, List<String> authMenuIds) {
        if (!HelpUtil.isEmpty(distMenuIds)) {
            List<RowMap> roleMenuList = new ArrayList<>();
            for (String f_menu_id : distMenuIds) {
                RowMap roleMenu = new RowMap();
                roleMenuList.add(roleMenu);

                roleMenu.put("f_role_id", f_role_id);
                roleMenu.put("f_menu_id", f_menu_id);
            }
            roleMenuMapper.insertDistMenus(roleMenuList);
        }

        if (!HelpUtil.isEmpty(authMenuIds)) {
            List<RowMap> roleMenuList = new ArrayList<>();
            for (String f_menu_id : authMenuIds) {
                RowMap roleMenu = new RowMap();
                roleMenuList.add(roleMenu);

                roleMenu.put("f_role_id", f_role_id);
                roleMenu.put("f_menu_id", f_menu_id);
            }
            roleMenuMapper.insertAuthMenus(roleMenuList);
        }
    }

    private void deleteRoleMenu(Long f_role_id) {
        // 删除角色下关联的菜单
        roleMenuMapper.deleteDistMenus(new ParamsMap("f_role_id", f_role_id));
        roleMenuMapper.deleteAuthMenus(new ParamsMap("f_role_id", f_role_id));
    }
}
