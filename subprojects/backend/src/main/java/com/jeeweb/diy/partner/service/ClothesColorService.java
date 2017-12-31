package com.jeeweb.diy.partner.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.diy.partner.entity.ClothesColorEntity;
import com.jeeweb.diy.partner.mapper.ClothesColorMapper;


@Service
@Transactional
public class ClothesColorService extends BaseService<Long, ClothesColorEntity> {
    // private static final Logger LOG = LoggerFactory.getLogger(ClothesColorService.class);
    @Autowired
    private ClothesColorMapper clothesColorMapper;

    @Override
    protected BaseMapper<Long, ClothesColorEntity> getMapper() {
        return clothesColorMapper;
    }
}
