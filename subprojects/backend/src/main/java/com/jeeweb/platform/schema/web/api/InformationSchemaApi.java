package com.jeeweb.platform.schema.web.api;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeeweb.framework.business.web.controller.SuperController;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.platform.schema.service.InformationSchemaService;

@RestController
@RequestMapping(value = "/api/platform/schema/information")
public class InformationSchemaApi extends SuperController {
    @Resource
    private InformationSchemaService informationSchemaService;

    @RequestMapping(value = "/schematas", method = RequestMethod.GET)
    public ResponseResult listSchemata() {
        ParameterMap params = $params();
        return new ResponseResult(new Result(params.page(informationSchemaService.selectSchemataListPage(params))),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public ResponseResult listTable() {
        ParameterMap params = $params();
        return new ResponseResult(new Result(params.page(informationSchemaService.selectTableListPage(params))),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/columns", method = RequestMethod.GET)
    public ResponseResult listColumn() {
        ParameterMap params = $params();
        return new ResponseResult(new Result(params.page(informationSchemaService.selectColumnListPage(params))),
                HttpStatus.OK);
    }
}
