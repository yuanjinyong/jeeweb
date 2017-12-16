package com.jeeweb.admin.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeeweb.framework.business.enums.Enums;
import com.jeeweb.framework.business.web.controller.SuperController;
import com.jeeweb.framework.core.model.Page;
import com.jeeweb.framework.core.model.ParamsMap;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.framework.core.utils.TreeUtil;
import com.jeeweb.platform.security.utils.SecurityUtil;
import com.jeeweb.platform.sys.entity.MenuEntity;
import com.jeeweb.platform.sys.entity.UserEntity;
import com.jeeweb.platform.sys.enums.MenuType;
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
    @Autowired(required = false)
    private List<IndexHandler> indexHandlers;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseResult index() {
        Map<String, Object> data = new HashMap<>();
        data.put("user", SecurityUtil.getCurUser());

        if (!HelpUtil.isEmpty(indexHandlers)) {
            for (IndexHandler handler : indexHandlers) {
                handler.onIndex(data);
            }
        }

        if (!data.containsKey("menuList")) {
            data.put("menuList", getMenuList());
        }

        return new ResponseResult(new Result(data), HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseResult getUser() {
        UserEntity user = SecurityUtil.getCurUser(null);
        if (user != null) {
            return new ResponseResult(new Result(userService.selectEntity(user.getF_id())), HttpStatus.OK);
        } else {
            return new ResponseResult(new Result("未找到用户信息，请先登录！"), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/change/password", method = RequestMethod.POST)
    public ResponseResult changePassword() {
        userService.changePassword($("oldPassword"), $("newPassword"));
        return new ResponseResult(new Result(), HttpStatus.OK);
    }

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public ResponseResult getMenus() {
        // 获取当前用户可以访问的菜单和按钮
        Page<MenuEntity> page = new Page<>();
        page.setItems(getMenuList());
        return new ResponseResult(new Result(page), HttpStatus.OK);
    }

    private List<MenuEntity> getMenuList() {
        ParamsMap params = new ParamsMap("f_status", Enums.ENABLE);
        // params.put("f_is_web", 1); // TODO 等移动端开始开发时，再来完善代码，暂时直接写死只查询Web端的功能列表。
        params.put("f_type_in",
                HelpUtil.joinToInString(MenuType.FOLDER, MenuType.PAGE, MenuType.BUTTON, MenuType.TOKEN));
        params.put("orderBy", "f_parent_path,f_order");
        SysUtil.appendCurUserAndRoles(params);
        List<MenuEntity> menuList = menuService.selectEntityListPage(params);
        return TreeUtil.listToTree(menuList);
    }
}
