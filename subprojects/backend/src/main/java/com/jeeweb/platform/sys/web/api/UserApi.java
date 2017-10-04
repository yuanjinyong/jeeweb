package com.jeeweb.platform.sys.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.business.web.api.BaseApi;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.export.excel.ExcelColumn;
import com.jeeweb.framework.core.export.excel.ExcelSheet;
import com.jeeweb.framework.core.export.excel.ExcelWorkbook;
import com.jeeweb.framework.core.export.excel.ExcelXlsView;
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

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ModelAndView export() {
        ParameterMap params = $params();
        List<RowMap> entities = getService().selectMapEntityListPage(params);

        ExcelSheet sheet = new ExcelSheet("用户", "用户", entities);
        sheet.addColumn(new ExcelColumn("f_account", "账号"));
        sheet.addColumn(new ExcelColumn("f_name", "姓名"));
        sheet.addColumn(new ExcelColumn("f_telephone", "绑定手机"));
        sheet.addColumn(new ExcelColumn("f_creator_name", "创建人"));
        sheet.addColumn(new ExcelColumn("f_created_time", "创建时间", Cell.CELL_TYPE_NUMERIC, CellStyle.ALIGN_CENTER,
                "yyyy-MM-dd hh:mm:ss", 19));
        sheet.addColumn(new ExcelColumn("f_last_login_time", "最后登录时间", Cell.CELL_TYPE_NUMERIC, CellStyle.ALIGN_CENTER,
                "yyyy-MM-dd hh:mm:ss", 19));
        sheet.addColumn(new ExcelColumn("f_locked_time", "锁定时间", Cell.CELL_TYPE_NUMERIC, CellStyle.ALIGN_CENTER,
                "yyyy-MM-dd hh:mm:ss", 19));
        sheet.addColumn(new ExcelColumn("f_unregister_time", "注销时间", Cell.CELL_TYPE_NUMERIC, CellStyle.ALIGN_CENTER,
                "yyyy-MM-dd hh:mm:ss", 19));
        sheet.addColumn(new ExcelColumn("f_is_can_login", "是否允许登录"));
        sheet.addColumn(new ExcelColumn("f_is_preset", "是否系统预置"));
        sheet.addColumn(new ExcelColumn("f_status", "状态"));
        sheet.addColumn(new ExcelColumn("f_remark", "备注", Cell.CELL_TYPE_STRING, CellStyle.ALIGN_LEFT, "@", 50));

        ExcelWorkbook excel = new ExcelWorkbook("用户-" + HelpUtil.getNowTime("yyyyMMddhhmmss") + ".xls");
        excel.addSheet(sheet);
        Map<String, Object> model = new HashMap<>();
        model.put(ExcelWorkbook.class.getName(), excel);

        return new ModelAndView(new ExcelXlsView(), model);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/{id}/menus", method = RequestMethod.GET)
    public ResponseResult listMenu(@PathVariable("id") Integer primaryKey) {
        List<RowMap> menuList = userService.selectUserMenuListPage(primaryKey);
        return new ResponseResult(new Result(TreeUtil.listToTree(menuList, "f_id")), HttpStatus.OK);
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
