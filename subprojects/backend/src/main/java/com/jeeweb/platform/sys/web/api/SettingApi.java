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
import com.jeeweb.platform.sys.entity.SettingEntity;
import com.jeeweb.platform.sys.service.SettingService;

@RestController
@RequestMapping(value = "/api/platform/sys/settings")
public class SettingApi extends BaseApi<Integer, SettingEntity> {
    @Resource
    private SettingService settingService;

    @Override
    protected BaseService<Integer, SettingEntity> getService() {
        return settingService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseResult list() {
        return super.listEntities();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseResult create(@RequestBody SettingEntity entity, UriComponentsBuilder ucBuilder) {
        return super.createEntity(entity, ucBuilder);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Integer primaryKey) {
        return super.getEntity(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseResult update(@PathVariable("id") Integer primaryKey, @RequestBody SettingEntity entity) {
        return super.updateEntity(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}/value", method = RequestMethod.PUT)
    public ResponseResult updateValue(@PathVariable("id") Integer primaryKey, @RequestBody SettingEntity entity) {
        SettingEntity newEntity = new SettingEntity();
        newEntity.setF_id(entity.getF_id());
        newEntity.setF_value(entity.getF_value());
        return super.updateEntity(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") Integer primaryKey) {
        return super.deleteEntity(primaryKey);
    }
}
