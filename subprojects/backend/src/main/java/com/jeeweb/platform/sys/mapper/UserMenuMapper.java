package com.jeeweb.platform.sys.mapper;

import java.util.List;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.platform.sys.entity.MenuEntity;


public interface UserMenuMapper extends BaseMapper<Integer, RowMap> {
    /**
     * 查询用户（操作员）可赋&已赋的菜单（权限）列表
     * 
     * @param params
     * @return
     */
    public List<RowMap> selectUserMenuListPage(ParameterMap params);

    /**
     * 查询用户（操作员）及其角色已赋的菜单（权限）列表
     * 
     * @param params
     * @return
     */
    public List<MenuEntity> selectUserAndRoleMenuListPage(ParameterMap params);
}
