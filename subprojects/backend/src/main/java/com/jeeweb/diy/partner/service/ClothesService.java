package com.jeeweb.diy.partner.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.diy.partner.entity.ClothesEntity;
import com.jeeweb.diy.partner.mapper.ClothesMapper;


@Service
@Transactional
public class ClothesService extends BaseService<Long, ClothesEntity> {
    // private static final Logger LOG = LoggerFactory.getLogger(ClothesService.class);
    @Autowired
    private ClothesMapper clothesMapper;

    @Override
    protected BaseMapper<Long, ClothesEntity> getMapper() {
        return clothesMapper;
    }
}
