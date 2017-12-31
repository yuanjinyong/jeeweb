package com.jeeweb.diy.partner.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.diy.partner.entity.ClothesStyleEntity;
import com.jeeweb.diy.partner.mapper.ClothesStyleMapper;


@Service
@Transactional
public class ClothesStyleService extends BaseService<Long, ClothesStyleEntity> {
    // private static final Logger LOG = LoggerFactory.getLogger(ClothesStyleService.class);
    @Autowired
    private ClothesStyleMapper clothesStyleMapper;

    @Override
    protected BaseMapper<Long, ClothesStyleEntity> getMapper() {
        return clothesStyleMapper;
    }
}
