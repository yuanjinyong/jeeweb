package com.jeeweb.diy.partner.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.diy.partner.entity.ClothesSizeEntity;
import com.jeeweb.diy.partner.mapper.ClothesSizeMapper;


@Service
@Transactional
public class ClothesSizeService extends BaseService<Long, ClothesSizeEntity> {
    // private static final Logger LOG = LoggerFactory.getLogger(ClothesSizeService.class);
    @Autowired
    private ClothesSizeMapper clothesSizeMapper;

    @Override
    protected BaseMapper<Long, ClothesSizeEntity> getMapper() {
        return clothesSizeMapper;
    }
}
