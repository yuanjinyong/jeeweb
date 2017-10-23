package com.jeeweb.platform.sys.mapper;

import java.util.List;

import com.jeeweb.framework.core.mapper.SuperMapper;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;

public interface RoleMenuMapper extends SuperMapper {
    public List<RowMap> selectDistMenuListPage(ParameterMap params);

    public List<RowMap> selectAuthMenuListPage(ParameterMap params);

    void insertDistMenus(List<RowMap> entityList);

    void insertAuthMenus(List<RowMap> entityList);

    int deleteDistMenus(ParameterMap params);

    int deleteAuthMenus(ParameterMap params);
}
