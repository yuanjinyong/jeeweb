package com.jeeweb.platform.sys.mapper;

import java.util.List;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.platform.sys.entity.MenuEntity;



public interface MenuMapper extends BaseMapper<String, MenuEntity> {
    public List<MenuEntity> selectUserMenuListPage(ParameterMap param);
}
