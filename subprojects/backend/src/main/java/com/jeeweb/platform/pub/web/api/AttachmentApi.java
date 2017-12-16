package com.jeeweb.platform.pub.web.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.business.web.api.BaseApi;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.platform.pub.entity.AttachmentEntity;
import com.jeeweb.platform.pub.service.AttachmentService;

@RestController
@RequestMapping(value = "/api/platform/pub/attachments")
public class AttachmentApi extends BaseApi<Long, AttachmentEntity> {
    // private static final Logger LOG = LoggerFactory.getLogger(AttachmentApi.class);

    @Resource
    private AttachmentService attachmentService;

    @Override
    protected BaseService<Long, AttachmentEntity> getService() {
        return attachmentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseResult list() {
        return super.listEntities();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long primaryKey) {
        return super.getEntity(primaryKey);
    }
}
