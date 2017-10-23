package com.jeeweb.platform.sys.mapper;

import java.util.List;

import com.jeeweb.framework.core.mapper.SuperMapper;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.platform.sys.entity.MenuEntity;

public interface UserMenuMapper extends SuperMapper {
    public List<RowMap> selectDistMenuListPage(ParameterMap params);

    public List<RowMap> selectAuthMenuListPage(ParameterMap params);

    void insertDistMenus(List<RowMap> entityList);

    void insertAuthMenus(List<RowMap> entityList);

    int deleteDistMenus(ParameterMap params);

    int deleteAuthMenus(ParameterMap params);

    /**
     * 查询用户（操作员）及其角色已授权的菜单（权限）列表
     *
     * @param params
     * @return
     */
    public List<MenuEntity> selectUserAndRoleMenuListPage(ParameterMap params);
}
