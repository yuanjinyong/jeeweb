package com.jeeweb.framework.business.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;



public abstract class BaseService<P, E> {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    protected abstract BaseMapper<P, E> getMapper();

    public E selectEntity(P primaryKey) {
        return getMapper().selectEntity(primaryKey);
    }

    public List<E> selectEntityListPage(ParameterMap params) {
        return getMapper().selectEntityListPage(params);
    }

    public List<RowMap> selectMapEntityListPage(ParameterMap params) {
        return getMapper().selectMapEntityListPage(params);
    }

    public void insertEntity(E entity) {
        // if (entity instanceof ICreatorEntity) {
        // UserEntity user = SysUtil.getCurUser();
        // ICreatorEntity creator = (ICreatorEntity) entity;
        // creator.setF_creator_id(user.getF_id());
        // creator.setF_creator_name(user.getF_name());
        // creator.setF_created_time(HelpUtil.getNowTime());
        // }

        getMapper().insertEntity(entity);
    }

    public void insertEntities(List<E> entityList) {
        getMapper().insertEntities(entityList);
    }

    public void updateEntity(E entity) {
        getMapper().updateEntity(entity);
    }

    public void deleteEntity(P primaryKey) {
        if (getMapper().isCanDeleteEntity(primaryKey) > 0) {
            throw new BusinessException("存在关联数据，不能删除！");
        }

        getMapper().deleteEntity(primaryKey);
    }

    public void deleteEntities(ParameterMap params) {
        getMapper().deleteEntities(params);
    }
}
