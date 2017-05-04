package com.jeeweb.platform.sys.service;

import java.util.List;

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
