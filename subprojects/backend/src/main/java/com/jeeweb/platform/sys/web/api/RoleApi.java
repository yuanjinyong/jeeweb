package com.jeeweb.platform.sys.web.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.business.web.controller.BaseController;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.sys.entity.RoleEntity;
import com.jeeweb.platform.sys.service.RoleService;

@RestController
@RequestMapping(value = "/api/platform/sys/roles")
public class RoleApi extends BaseController<Integer, RoleEntity> {

    @Resource
    private RoleService roleService;

    @Override
    protected BaseService<Integer, RoleEntity> getService() {
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
    public ResponseResult get(@PathVariable("id") Integer primaryKey) {
        return super.getEntity(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseResult update(@PathVariable("id") Integer primaryKey, @RequestBody RoleEntity entity) {
        return super.updateEntity(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") Integer primaryKey) {
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
    public ResponseResult listMenu(@PathVariable("id") Integer primaryKey) {
        List<RowMap> menuList = null;
        if (primaryKey >= 0) {
            menuList = roleService.selectRoleMenuListPage(primaryKey);
        } else {
            menuList = new ArrayList<RowMap>();
        }

        return new ResponseResult(new Result(menuList), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/menus", method = RequestMethod.POST)
    public ResponseResult saveMenu(@PathVariable("id") Integer primaryKey) {
        roleService.updateRoleMenuList(primaryKey, HelpUtil.splitToList($("f_menu_ids")));
        return new ResponseResult(new Result(), HttpStatus.OK);
    }
}
