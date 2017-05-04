package com.jeeweb.platform.sys.mapper;

import java.util.List;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;

public interface RoleMenuMapper extends BaseMapper<Integer, RowMap> {
    public List<RowMap> selectRoleMenuListPage(ParameterMap params);
}
