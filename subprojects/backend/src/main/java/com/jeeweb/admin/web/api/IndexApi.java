package com.jeeweb.admin.web.api;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeeweb.framework.business.web.controller.SuperController;
import com.jeeweb.framework.core.model.Page;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.framework.core.utils.TreeUtil;
import com.jeeweb.platform.sys.entity.MenuEntity;
import com.jeeweb.platform.sys.entity.UserEntity;
import com.jeeweb.platform.sys.service.MenuService;
import com.jeeweb.platform.sys.service.UserService;
import com.jeeweb.platform.sys.utils.SysUtil;

@RestController
@RequestMapping(value = "/api/admin/index")
public class IndexApi extends SuperController {
    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseResult getUser() {
        UserEntity user = SysUtil.getCurUser();
        if (user != null) {
            return new ResponseResult(new Result(userService.selectEntity(user.getF_id())), HttpStatus.OK);
        } else {
            return new ResponseResult(new Result("未找到用户信息，请先登录！"), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public ResponseResult getMenus() {
        // 获取当前用户可以访问的菜单和按钮
        ParameterMap params = new ParameterMap();
        params.put("f_status", 1);
        // params.put("f_is_web", 1); // TODO 等移动端开始开发时，再来完善代码，暂时直接写死只查询Web端的功能列表。
        params.put("f_type_in", "1,2,3,4");
        params.put("f_parent_path_like", MenuEntity.ROOT_PATH);
        params.put("orderBy", "f_parent_path,f_order");
        SysUtil.appendCurUserAndRoles(params);
        List<MenuEntity> menuList = menuService.selectEntityListPage(params);
        Page<MenuEntity> page = new Page<MenuEntity>();
        page.setItems(TreeUtil.listToTree(menuList));
        return new ResponseResult(new Result(page), HttpStatus.OK);
    }
}