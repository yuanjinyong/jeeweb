package com.jeeweb.framework.business.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.model.ICreator;
import com.jeeweb.framework.business.model.IPreset;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.security.utils.SecurityUtil;
import com.jeeweb.platform.sys.entity.UserEntity;



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
        fillCreator(entity);
        fillPreset(entity);

        getMapper().insertEntity(entity);
    }

    public void insertEntities(List<E> entityList) {
        getMapper().insertEntities(entityList);
    }

    public void updateEntity(E entity) {
        getMapper().updateEntity(entity);
    }

    public void deleteEntity(P primaryKey) {
        E entity = getMapper().selectEntity(primaryKey);
        validateDeleteEntity(entity);

        if (getMapper().isCanDeleteEntity(primaryKey) > 0) {
            throw new BusinessException("存在关联数据，不能删除！");
        }

        getMapper().deleteEntity(primaryKey);
    }

    public void deleteEntities(ParameterMap params) {
        getMapper().deleteEntities(params);
    }

    protected void validateDeleteEntity(E entity) {
        checkPreset(entity);
    }

    protected void fillCreator(E entity) {
        if (entity instanceof ICreator) {
            UserEntity user = SecurityUtil.getCurUser();
            ICreator creator = (ICreator) entity;
            creator.setF_creator_id(user.getF_id());
            creator.setF_creator_name(user.getF_name());
            creator.setF_created_time(HelpUtil.getNowTime());
        }
    }

    protected void fillPreset(E entity) {
        if (entity instanceof IPreset) {
            IPreset preset = (IPreset) entity;
            preset.setF_is_preset(IPreset.NO); // 系统预置数据是不能通过程序来修改的，这里能够写入的数据都不是系统预置的。
        }
    }

    protected void checkPreset(E entity) {
        if (entity instanceof IPreset) {
            IPreset preset = (IPreset) entity;
            if (preset.isPreset()) {
                throw new BusinessException("系统预置数据，不能删除！");
            }
        }
    }
}
