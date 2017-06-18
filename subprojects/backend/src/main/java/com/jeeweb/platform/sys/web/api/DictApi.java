package com.jeeweb.platform.sys.web.api;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.business.web.api.BaseApi;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.platform.sys.entity.DictEntity;
import com.jeeweb.platform.sys.service.DictService;


@RestController
@RequestMapping(value = "/api/platform/sys/dicts")
public class DictApi extends BaseApi<Integer, DictEntity> {
    @Resource
    private DictService dictService;

    @Override
    protected BaseService<Integer, DictEntity> getService() {
        return dictService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseResult list() {
        return super.listEntities();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseResult create(@RequestBody DictEntity entity, UriComponentsBuilder ucBuilder) {
        return super.createEntity(entity, ucBuilder);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Integer primaryKey) {
        return super.getEntity(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseResult update(@PathVariable("id") Integer primaryKey, @RequestBody DictEntity entity) {
        return super.updateEntity(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") Integer primaryKey) {
        return super.deleteEntity(primaryKey);
    }

    // @RequestMapping(method = RequestMethod.DELETE)
    // public ResponseResult delete() {
    //     return super.deleteEntities();
    // }
}