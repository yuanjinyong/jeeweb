package com.jeeweb.platform.pub.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.BaseMapper;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.platform.pub.entity.AttachmentEntity;
import com.jeeweb.platform.pub.mapper.AttachmentMapper;


@Service
@Transactional
public class AttachmentService extends BaseService<Integer, AttachmentEntity> {
    // private static final Logger LOG = LoggerFactory.getLogger(AttachmentService.class);
    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    protected BaseMapper<Integer, AttachmentEntity> getMapper() {
        
        return attachmentMapper;
    }
}
