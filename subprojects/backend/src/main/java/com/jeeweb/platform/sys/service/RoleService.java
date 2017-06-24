package com.jeeweb.platform.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.sys.entity.RoleEntity;
import com.jeeweb.platform.sys.mapper.RoleMapper;
import com.jeeweb.platform.sys.mapper.RoleMenuMapper;
import com.jeeweb.platform.sys.utils.SysUtil;

@Service
@Transactional
public class RoleService extends BaseService<Integer, RoleEntity> {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    protected BaseMapper<Integer, RoleEntity> getMapper() {
        return roleMapper;
    }

    @Override
    public void deleteEntity(Integer primaryKey) {
        // 先删除角色下关联的菜单
        deleteRoleMenu(primaryKey);

        // 再删除角色
        super.deleteEntity(primaryKey);
    }

    @Override
    public void deleteEntities(ParameterMap params) {
        // 删除角色下关联的菜单
        roleMenuMapper.deleteEntities(new ParameterMap("f_role_id_in", params.getString("f_id_in")));

        getMapper().deleteEntities(params);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional(readOnly = true)
    public List<RowMap> selectRoleMenuListPage(Integer roleId) {
        ParameterMap params = new ParameterMap();
        params.put("f_role_id", roleId);
        params.put("f_status", 1);
        params.put("orderBy", "f_parent_path,f_order");
        SysUtil.appendCurUserAndRoles(params);
        return roleMenuMapper.selectRoleMenuListPage(params);
    }

    public void updateRoleMenuList(Integer f_role_id, List<String> f_menu_ids) {
        // 先删除角色下关联的菜单
        deleteRoleMenu(f_role_id);

        // 在重新插入角色下关联的菜单
        insertRoleMenu(f_role_id, f_menu_ids);
    }

    private void insertRoleMenu(Integer f_role_id, List<String> f_menu_ids) {
        if (!HelpUtil.isEmpty(f_menu_ids)) {
            List<RowMap> roleMenuList = new ArrayList<>();
            for (String f_menu_id : f_menu_ids) {
                RowMap roleMenu = new RowMap();
                roleMenuList.add(roleMenu);

                roleMenu.put("f_role_id", f_role_id);
                roleMenu.put("f_menu_id", f_menu_id);
            }
            roleMenuMapper.insertEntities(roleMenuList);
        }
    }

    private void deleteRoleMenu(Integer f_role_id) {
        // 删除角色下关联的菜单
        roleMenuMapper.deleteEntities(new ParameterMap("f_role_id", f_role_id));
    }
}
