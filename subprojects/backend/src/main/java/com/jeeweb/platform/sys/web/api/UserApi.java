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
import com.jeeweb.framework.business.web.api.BaseApi;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.framework.core.model.RowMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.framework.core.utils.TreeUtil;
import com.jeeweb.platform.sys.entity.UserEntity;
import com.jeeweb.platform.sys.service.UserService;

@RestController
@RequestMapping(value = "/api/platform/sys/users")
public class UserApi extends BaseApi<Integer, UserEntity> {
    @Resource
    private UserService userService;

    @Override
    protected BaseService<Integer, UserEntity> getService() {
        return userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseResult list() {
        ParameterMap params = $params();
        params.put("f_account_notIn", "'" + UserEntity.SUPERADMIN + "'");
        List<UserEntity> entities = getService().selectEntityListPage(params);
        return new ResponseResult(new Result(params.page(entities)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseResult create(@RequestBody UserEntity entity, UriComponentsBuilder ucBuilder) {
        if (entity.getF_account().length() > 30) {
            throw new BusinessException("用户名长度必须小于30个字符！");
        }

        return super.createEntity(entity, ucBuilder);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Integer primaryKey) {
        return super.getEntity(primaryKey);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseResult update(@PathVariable("id") Integer primaryKey, @RequestBody UserEntity entity) {
        return super.updateEntity(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") Integer primaryKey) {
        // return super.deleteEntity(primaryKey);
        // 这里改为注销，不是普通的物理删除。
        userService.deregisterUser(primaryKey);
        return new ResponseResult(new Result(), HttpStatus.OK);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/{id}/menus", method = RequestMethod.GET)
    public ResponseResult listMenu(@PathVariable("id") Integer primaryKey) {
        List<RowMap> menuList = null;
        if (primaryKey >= 0) {
            menuList = userService.selectUserMenuListPage(primaryKey);
        } else {
            menuList = new ArrayList<>();
        }

        if ($bool("treeData", true)) {
            return new ResponseResult(new Result(TreeUtil.listToTree(menuList, "f_id")), HttpStatus.OK);
        } else {
            return new ResponseResult(new Result(menuList), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}/menus", method = RequestMethod.POST)
    public ResponseResult saveMenu(@PathVariable("id") Integer primaryKey) {
        userService.updateUserMenuList(primaryKey, HelpUtil.splitToList($("f_menu_ids")));
        return new ResponseResult(new Result(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/unlock", method = RequestMethod.POST)
    public ResponseResult unlock(@PathVariable("id") Integer primaryKey) {
        userService.unlockUser(primaryKey);
        return new ResponseResult(new Result(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/reset/password", method = RequestMethod.POST)
    public ResponseResult resetPassword(@PathVariable("id") Integer primaryKey) {
        userService.resetPassword(primaryKey);
        return new ResponseResult(new Result(), HttpStatus.OK);
    }
}
