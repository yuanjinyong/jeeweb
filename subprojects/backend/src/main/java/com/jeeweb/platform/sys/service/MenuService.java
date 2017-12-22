package com.jeeweb.platform.sys.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.jeeweb.framework.core.model.ParamsMap;
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
    @Qualifier("defaultSqlMapper")
    private SqlMapper sqlMapper;

    @Override
    protected BaseMapper<String, MenuEntity> getMapper() {
        return menuMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public MenuEntity selectEntity(String primaryKey) {
        MenuEntity menu = super.selectEntity(primaryKey);
        menu.setUrlList(selectMenuUrlList(new ParamsMap("f_menu_id", menu.getF_id()).setOrderBy("f_url,f_methods")));
        return menu;
    }

    @Override
    public void insertEntity(MenuEntity entity) {
        insertMenuUrl(entity);

        super.insertEntity(entity);
    }

    @Override
    public void updateEntity(String primaryKey, MenuEntity entity) {
        deleteMenuUrl(entity);
        insertMenuUrl(entity);

        super.updateEntity(primaryKey, entity);
    }

    @Override
    protected void beforeDeleteEntity(MenuEntity entity) {
        // 查出所有的子菜单，先删除子菜单及其URL
        ParamsMap params = new ParamsMap("f_parent_path_like", entity.getF_full_path());
        menuUrlMapper.deleteEntities(params);

        // 再删除自己的URL
        deleteMenuUrl(entity);
    }

    @Override
    public void deleteEntities(ParamsMap params) {
        throw new BusinessException("不支持一次删除多条菜单！");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Transactional(readOnly = true)
    public RowMap getSqlData(String f_menu_id) {
        MenuEntity menu = menuMapper.selectEntity(f_menu_id);

        List<MenuEntity> menuList = new ArrayList<>();
        menuList.add(menu);
        menuList.addAll(menuMapper.selectEntityListPage(
                new ParamsMap("f_parent_path_like", menu.getF_full_path()).setOrderBy("f_parent_path,f_order")));

        List<Map<String, Object>> menuUrlList = new ArrayList<>();
        menuUrlList.addAll(sqlMapper.selectListPage("SELECT * FROM `t_sys_menu_url` WHERE f_menu_id = #{f_menu_id}",
                new ParamsMap("f_menu_id", menu.getF_id()).setOrderBy("f_menu_id,f_url_id")));
        menuUrlList.addAll(sqlMapper.selectListPage(
                "SELECT * FROM `t_sys_menu_url` WHERE f_menu_id IN (SELECT f_id FROM `t_sys_menu` WHERE f_parent_path LIKE CONCAT('%', #{f_parent_path_like}, '%'))",
                new ParamsMap("f_parent_path_like", menu.getF_full_path()).setOrderBy("f_menu_id,f_url_id")));

        List<Map<String, Object>> roleDistMenuList = new ArrayList<>();
        roleDistMenuList.addAll(sqlMapper.selectListPage(
                "SELECT * FROM `t_sys_role_menu_distribution` WHERE f_role_id < 1000 AND f_menu_id = #{f_menu_id}",
                new ParamsMap("f_menu_id", menu.getF_id()).setOrderBy("f_role_id,f_menu_id")));
        roleDistMenuList.addAll(sqlMapper.selectListPage(
                "SELECT * FROM `t_sys_role_menu_distribution` WHERE  f_role_id < 1000 AND f_menu_id IN (SELECT f_id FROM `t_sys_menu` WHERE f_parent_path LIKE CONCAT('%', #{f_parent_path_like}, '%'))",
                new ParamsMap("f_parent_path_like", menu.getF_full_path()).setOrderBy("f_role_id,f_menu_id")));
        Collections.sort(roleDistMenuList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
            	Long r1 = (Long) o1.get("f_role_id");
            	Long r2 = (Long) o2.get("f_role_id");
                if (r1 == r2) {
                    return ((String) o1.get("f_menu_id")).compareTo((String) o2.get("f_menu_id"));
                }

                return r1.compareTo(r2);
            }
        });

        List<Map<String, Object>> roleAuthMenuList = new ArrayList<>();
        roleAuthMenuList.addAll(sqlMapper.selectListPage(
                "SELECT * FROM `t_sys_role_menu_authorization` WHERE f_role_id < 1000 AND f_menu_id = #{f_menu_id}",
                new ParamsMap("f_menu_id", menu.getF_id()).setOrderBy("f_role_id,f_menu_id")));
        roleAuthMenuList.addAll(sqlMapper.selectListPage(
                "SELECT * FROM `t_sys_role_menu_authorization` WHERE  f_role_id < 1000 AND f_menu_id IN (SELECT f_id FROM `t_sys_menu` WHERE f_parent_path LIKE CONCAT('%', #{f_parent_path_like}, '%'))",
                new ParamsMap("f_parent_path_like", menu.getF_full_path()).setOrderBy("f_role_id,f_menu_id")));
        Collections.sort(roleAuthMenuList, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
            	Long r1 = (Long) o1.get("f_role_id");
            	Long r2 = (Long) o2.get("f_role_id");
                if (r1 == r2) {
                    return ((String) o1.get("f_menu_id")).compareTo((String) o2.get("f_menu_id"));
                }

                return r1.compareTo(r2);
            }
        });

        RowMap data = new RowMap();
        data.put("menuList", TreeUtil.listToTree(menuList));
        data.put("menuUrlList", menuUrlList);
        data.put("roleDistMenuList", roleDistMenuList);
        data.put("roleAuthMenuList", roleAuthMenuList);
        return data;
    }

    private List<RowMap> selectMenuUrlList(ParamsMap params) {
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
        menuUrlMapper.deleteEntities(new ParamsMap("f_menu_id", menu.getF_id()));
    }
}
