package com.jeeweb.platform.sys.mapper;

import java.util.List;
import java.util.Map;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.core.model.ParamsMap;
import com.jeeweb.platform.sys.entity.MenuEntity;

public interface MenuMapper extends BaseMapper<String, MenuEntity> {
    void createTempMenuIdTable();

    void cleanTempMenuIdTable();

    void insertTempMenuIds(List<Map<String, Object>> entityList);

    List<MenuEntity> selectTempMenuListPage(ParamsMap params);
}
