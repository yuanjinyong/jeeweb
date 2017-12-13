package com.jeeweb.platform.sys.mapper;

import java.util.List;

import com.jeeweb.framework.core.mapper.SuperMapper;
import com.jeeweb.framework.core.model.ParamsMap;
import com.jeeweb.framework.core.model.RowMap;

public interface RoleMenuMapper extends SuperMapper {
    public List<RowMap> selectDistMenuListPage(ParamsMap params);

    public List<RowMap> selectAuthMenuListPage(ParamsMap params);

    void insertDistMenus(List<RowMap> entityList);

    void insertAuthMenus(List<RowMap> entityList);

    int deleteDistMenus(ParamsMap params);

    int deleteAuthMenus(ParamsMap params);
}
