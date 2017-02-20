package com.jeeweb.platform.sys.web.controller;

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
import com.jeeweb.platform.security.SecurityCacheManager;
import com.jeeweb.platform.sys.entity.MenuEntity;
import com.jeeweb.platform.sys.service.MenuService;

@RestController
@RequestMapping(value = "/api/sys/menus")
public class MenuController extends BaseController<String, MenuEntity> {

    @Resource
    private MenuService menuService;
    @Resource
    private SecurityCacheManager securityCacheManager;

    @Override
    protected BaseService<String, MenuEntity> getService() {
        return menuService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseResult list() {
        return super.listEntities();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseResult create(UriComponentsBuilder ucBuilder, @RequestBody MenuEntity entity) {
        ResponseResult result = super.createEntity(entity, ucBuilder);
        // 刷新权限控制的缓存
        securityCacheManager.loadUrlAuthoritiesCache();
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") String primaryKey) {
        return super.getEntity(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseResult update(@PathVariable("id") String primaryKey, @RequestBody MenuEntity entity) {
        ResponseResult result = super.updateEntity(primaryKey, entity);
        // 刷新权限控制的缓存
        securityCacheManager.loadUrlAuthoritiesCache();
        return result;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") String primaryKey) {
        return super.deleteEntity(primaryKey);
    }
}
