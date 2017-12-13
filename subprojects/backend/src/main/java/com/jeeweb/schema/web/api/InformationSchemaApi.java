package com.jeeweb.schema.web.api;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeeweb.framework.business.web.controller.SuperController;
import com.jeeweb.framework.core.model.ParamsMap;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.schema.service.InformationSchemaService;

@RestController
@RequestMapping(value = "/api/schema/information")
public class InformationSchemaApi extends SuperController {
    @Resource
    private InformationSchemaService informationSchemaService;

    @RequestMapping(value = "/schematas", method = RequestMethod.GET)
    public ResponseResult listSchemata() {
        ParamsMap params = $params();
        return new ResponseResult(new Result(params.page(informationSchemaService.selectSchemataListPage(params))),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public ResponseResult listTable() {
        ParamsMap params = $params();
        return new ResponseResult(new Result(params.page(informationSchemaService.selectTableListPage(params))),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/tables/{tableSchema}/{tableName}", method = RequestMethod.GET)
    public ResponseResult getTable(@PathVariable("tableSchema") String tableSchema,
            @PathVariable("tableName") String tableName) {
        return new ResponseResult(new Result(informationSchemaService.selectTable(tableSchema, tableName)),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/columns", method = RequestMethod.GET)
    public ResponseResult listColumn() {
        ParamsMap params = $params();
        return new ResponseResult(new Result(params.page(informationSchemaService.selectColumnListPage(params))),
                HttpStatus.OK);
    }
}
