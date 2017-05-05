package com.jeeweb.platform.sys.web.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.business.web.controller.BaseController;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.platform.sys.entity.UrlEntity;
import com.jeeweb.platform.sys.service.UrlService;

@RestController
@RequestMapping(value = "/api/platform/sys/urls")
public class UrlApi extends BaseController<String, UrlEntity> {
    @Resource
    private UrlService urlService;

    @Override
    protected BaseService<String, UrlEntity> getService() {
        return urlService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseResult list() {
        return super.listEntities();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseResult create(@RequestBody UrlEntity entity, UriComponentsBuilder ucBuilder) {
        return super.createEntity(entity, ucBuilder);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") String primaryKey) {
        return super.getEntity(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseResult update(@PathVariable("id") String primaryKey, @RequestBody UrlEntity entity) {
        return super.updateEntity(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") String primaryKey) {
        return super.deleteEntity(primaryKey);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseResult delete() {
        return super.deleteEntities();
    }
}
