package com.jeeweb.diy.partner.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.diy.partner.entity.PartnerEntity;
import com.jeeweb.diy.partner.mapper.PartnerMapper;


@Service
@Transactional
public class PartnerService extends BaseService<Long, PartnerEntity> {
    // private static final Logger LOG = LoggerFactory.getLogger(PartnerService.class);
    @Autowired
    private PartnerMapper partnerMapper;

    @Override
    protected BaseMapper<Long, PartnerEntity> getMapper() {
        return partnerMapper;
    }
}
