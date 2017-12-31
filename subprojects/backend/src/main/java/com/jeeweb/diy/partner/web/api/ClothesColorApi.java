package com.jeeweb.diy.partner.web.api;


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
import com.jeeweb.diy.partner.entity.ClothesColorEntity;
import com.jeeweb.diy.partner.service.ClothesColorService;


@RestController
@RequestMapping(value = "/api/diy/partner/clothes/colors")
public class ClothesColorApi extends BaseApi<Long, ClothesColorEntity> {
    @Resource
    private ClothesColorService clothesColorService;

    @Override
    protected BaseService<Long, ClothesColorEntity> getService() {
        return clothesColorService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseResult list() {
        return super.listEntities();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseResult create(@RequestBody ClothesColorEntity entity, UriComponentsBuilder ucBuilder) {
        return super.createEntity(entity, ucBuilder);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long primaryKey) {
        return super.getEntity(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseResult update(@PathVariable("id") Long primaryKey, @RequestBody ClothesColorEntity entity) {
        return super.updateEntity(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") Long primaryKey) {
        return super.deleteEntity(primaryKey);
    }

    // @RequestMapping(method = RequestMethod.DELETE)
    // public ResponseResult delete() {
    //     return super.deleteEntities();
    // }
}