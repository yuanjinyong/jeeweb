package com.jeeweb.platform.sys.web.api;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.business.web.api.BaseApi;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.sys.entity.RoleEntity;
import com.jeeweb.platform.sys.service.RoleService;

@RestController
@RequestMapping(value = "/api/platform/sys/roles")
public class RoleApi extends BaseApi<Long, RoleEntity> {
    @Resource
    private RoleService roleService;

    @Override
    protected BaseService<Long, RoleEntity> getService() {
        return roleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseResult list() {
        return super.listEntities();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseResult create(@RequestBody RoleEntity entity, UriComponentsBuilder ucBuilder) {
        return super.createEntity(entity, ucBuilder);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Long primaryKey) {
        return super.getEntity(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseResult update(@PathVariable("id") Long primaryKey, @RequestBody RoleEntity entity) {
        return super.updateEntity(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") Long primaryKey) {
        return super.deleteEntity(primaryKey);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseResult delete() {
        return super.deleteEntities();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/{id}/menus", method = RequestMethod.GET)
    public ResponseResult listMenu(@PathVariable("id") Long primaryKey) {
        RowMap menuMap = roleService.selectRoleMenuList(primaryKey);
        return new ResponseResult(new Result(menuMap), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/menus", method = RequestMethod.POST)
    public ResponseResult saveMenu(@PathVariable("id") Long primaryKey) {
        roleService.updateRoleMenuList(primaryKey, HelpUtil.splitToList($("distMenuIds")),
                HelpUtil.splitToList($("authMenuIds")));
        return new ResponseResult(new Result(), HttpStatus.OK);
    }
}
