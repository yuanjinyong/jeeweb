package com.jeeweb.platform.sys.mapper;

import java.util.List;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;


public interface UserRoleMapper extends BaseMapper<Integer, RowMap> {
    /**
     * 查询用户（操作员）可赋&已赋的角色列表
     * 
     * @param params
     * @return
     */
    public List<RowMap> selectRoleListPage(ParameterMap params);
}
