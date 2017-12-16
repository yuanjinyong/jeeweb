package com.jeeweb.framework.business.mapper;

import java.util.List;

import com.jeeweb.framework.core.mapper.SuperMapper;
import com.jeeweb.framework.core.model.ParamsMap;
import com.jeeweb.framework.core.model.RowMap;



public interface BaseMapper<P, E> extends SuperMapper {
    E selectEntity(P primaryKey);

    List<E> selectEntityListPage(ParamsMap params);

    List<RowMap> selectRowMapListPage(ParamsMap params);

    int insertEntity(E entity);

    void insertEntities(List<E> entityList);

    int updateEntity(E entity);

    int updateEntities(List<E> entityList);

    int deleteEntity(P primaryKey);

    int deleteEntities(ParamsMap params);

    Long isCanDeleteEntity(P primaryKey);
}
