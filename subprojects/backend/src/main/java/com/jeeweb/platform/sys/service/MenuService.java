package com.jeeweb.platform.sys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.mapper.SqlMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.framework.core.utils.TreeUtil;
import com.jeeweb.platform.sys.entity.MenuEntity;
import com.jeeweb.platform.sys.mapper.MenuMapper;
import com.jeeweb.platform.sys.mapper.MenuUrlMapper;

@Service
@Transactional
public class MenuService extends BaseService<String, MenuEntity> {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuUrlMapper menuUrlMapper;

    @Autowired
    @Qualifier("jeewebMapper")
    private SqlMapper sqlMapper;

    @Override
    protected BaseMapper<String, MenuEntity> getMapper() {
        return menuMapper;
    }

    @Override
    public MenuEntity selectEntity(String primaryKey) {
        MenuEntity menu = super.selectEntity(primaryKey);
        menu.setUrlList(selectMenuUrlList(new ParameterMap("f_menu_id", menu.getF_id()).setOrderBy("f_url,f_methods")));
        return menu;
    }

    @Override
    public void insertEntity(MenuEntity entity) {
        insertMenuUrl(entity);

        super.insertEntity(entity);
    }

    @Override
    public void updateEntity(MenuEntity entity) {
        deleteMenuUrl(entity);
        insertMenuUrl(entity);

        super.updateEntity(entity);
    }

    @Override
    public void deleteEntity(String primaryKey) {
        MenuEntity menu = super.selectEntity(primaryKey);
        if (menu.getF_parent_id() == null) {
            throw new BusinessException("顶级根菜单不能删除！");
        }

        // 查出所有的子菜单，先删除子菜单
        ParameterMap params = new ParameterMap("f_parent_path_like", menu.getF_full_path());
        menuUrlMapper.deleteEntities(params);
        super.deleteEntities(params);

        // 最后再删除自己
        deleteMenuUrl(menu);
        super.deleteEntity(primaryKey);
    }

    @Override
    public void deleteEntities(ParameterMap params) {
        throw new BusinessException("菜单不支持一次删除多条！");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public RowMap getSqlData(String f_menu_id) {
        MenuEntity menu = menuMapper.selectEntity(f_menu_id);

        List<MenuEntity> menuList = new ArrayList<>();
        menuList.add(menu);
        menuList.addAll(menuMapper.selectEntityListPage(
                new ParameterMap("f_parent_path_like", menu.getF_full_path()).setOrderBy("f_parent_path,f_order")));

        List<Map<String, Object>> menuUrlList = new ArrayList<>();
        menuUrlList.addAll(sqlMapper.selectListPage("SELECT * FROM `t_sys_menu_url` WHERE f_menu_id = #{f_menu_id}",
                new ParameterMap("f_menu_id", menu.getF_id()).setOrderBy("f_menu_id,f_url_id")));
        menuUrlList.addAll(sqlMapper.selectListPage(
                "SELECT * FROM `t_sys_menu_url` WHERE f_menu_id IN (SELECT f_id FROM `t_sys_menu` WHERE f_parent_path LIKE CONCAT('%', #{f_parent_path_like}, '%'))",
                new ParameterMap("f_parent_path_like", menu.getF_full_path()).setOrderBy("f_menu_id,f_url_id")));

        List<Map<String, Object>> roleMenuList = new ArrayList<>();
        roleMenuList.addAll(sqlMapper.selectListPage(
                "SELECT * FROM `t_sys_role_menu` WHERE f_role_id = 0 AND f_menu_id = #{f_menu_id}",
                new ParameterMap("f_menu_id", menu.getF_id()).setOrderBy("f_role_id,f_menu_id")));
        roleMenuList.addAll(sqlMapper.selectListPage(
                "SELECT * FROM `t_sys_role_menu` WHERE  f_role_id = 0 AND f_menu_id IN (SELECT f_id FROM `t_sys_menu` WHERE f_parent_path LIKE CONCAT('%', #{f_parent_path_like}, '%'))",
                new ParameterMap("f_parent_path_like", menu.getF_full_path()).setOrderBy("f_role_id,f_menu_id")));

        RowMap data = new RowMap();
        data.put("menuList", TreeUtil.listToTree(menuList));
        data.put("menuUrlList", menuUrlList);
        data.put("roleMenuList", roleMenuList);
        return data;
    }

    private List<RowMap> selectMenuUrlList(ParameterMap params) {
        return menuUrlMapper.selectEntityListPage(params);
    }

    private void insertMenuUrl(MenuEntity menu) {
        Integer f_type = menu.getF_type();
        List<RowMap> urlList = menu.getUrlList();
        // 只有页面和按钮下面才能配置可以访问的URL地址
        if (!(f_type == 2 || f_type == 3) || HelpUtil.isEmpty(urlList)) {
            return;
        }

        for (RowMap url : urlList) {
            url.put("f_menu_id", menu.getF_id());
        }

        menuUrlMapper.insertEntities(urlList);
    }

    private void deleteMenuUrl(MenuEntity menu) {
        menuUrlMapper.deleteEntities(new ParameterMap("f_menu_id", menu.getF_id()));
    }
}
