package com.jeeweb.platform.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
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

    @Override
    protected BaseMapper<String, MenuEntity> getMapper() {
        return menuMapper;
    }

    public List<MenuEntity> getMenuList(ParameterMap params) {
        List<MenuEntity> menuList = getMapper().selectEntityListPage(params);
        Map<String, MenuEntity> menuMap = new HashMap<>();
        for (MenuEntity menu : menuList) {
            menu.setChildren(new ArrayList<MenuEntity>());
            menuMap.put(menu.getF_id(), menu);

            MenuEntity parentMenu = menuMap.get(menu.getF_parent_id());
            if (parentMenu != null) {
                parentMenu.getChildren().add(menu);
            }
        }

        List<MenuEntity> childrenList = new ArrayList<>();
        for (MenuEntity menu : menuList) {
            if (!menuMap.containsKey(menu.getF_parent_id())) {
                childrenList.add(menu);
            }
        }

        return childrenList;
    }

    @Override
    public MenuEntity selectEntity(String primaryKey) {
        MenuEntity menu = super.selectEntity(primaryKey);
        menu.setUrlList(
                selectMenuUrlList(new ParameterMap("f_menu_id", menu.getF_id()).put("orderBy", "f_url,f_methods")));
        return menu;
    }

    @Override
    public void deleteEntity(String primaryKey) {
        MenuEntity menu = super.selectEntity(primaryKey);
        if (menu.getF_parent_id() == null) {
            throw new BusinessException("顶级根菜单不能删除！");
        }

        // 查出所有的子菜单，先删除子菜单
        ParameterMap params = new ParameterMap("f_parent_path_like", menu.getF_parent_path() + menu.getF_id() + "/");
        menuUrlMapper.deleteEntities(params);
        super.deleteEntities(params);

        // 最后在删除自己
        deleteMenuUrl(menu.getF_id());
        super.deleteEntity(primaryKey);
    }

    public List<RowMap> selectMenuUrlList(ParameterMap params) {
        return menuUrlMapper.selectEntityListPage(params);
    }

    public void updateMenuUrlList(String f_menu_id, List<String> f_url_ids) {
        // 先删除角色下关联的菜单
        deleteMenuUrl(f_menu_id);

        // 在重新插入角色下关联的菜单
        insertMenuUrl(f_menu_id, f_url_ids);
    }

    private void insertMenuUrl(String f_menu_id, List<String> f_url_ids) {
        if (!HelpUtil.isEmpty(f_url_ids)) {
            List<RowMap> menuUrlList = new ArrayList<>();
            for (String f_url_id : f_url_ids) {
                RowMap menuUrl = new RowMap();
                menuUrlList.add(menuUrl);

                menuUrl.put("f_menu_id", f_menu_id);
                menuUrl.put("f_url_id", f_url_id);
            }
            menuUrlMapper.insertEntities(menuUrlList);
        }
    }

    private void deleteMenuUrl(String f_menu_id) {
        menuUrlMapper.deleteEntities(new ParameterMap("f_menu_id", f_menu_id));
    }
}
