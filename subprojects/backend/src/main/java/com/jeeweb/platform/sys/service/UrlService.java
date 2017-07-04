package com.jeeweb.platform.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.platform.sys.entity.UrlEntity;
import com.jeeweb.platform.sys.mapper.UrlMapper;

@Service
@Transactional
public class UrlService extends BaseService<String, UrlEntity> {
    @Autowired
    private UrlMapper urlMapper;

    @Override
    protected BaseMapper<String, UrlEntity> getMapper() {
        return urlMapper;
    }

    @Override
    public void insertEntity(UrlEntity entity) {
        entity.generateF_id(); // 用MD5算法生成f_id字段的值
        super.insertEntity(entity);
    }

    @Override
    public void updateEntity(String primaryKey, UrlEntity entity) {
        super.deleteEntity(entity.getF_id());

        this.insertEntity(entity);
    }
}
