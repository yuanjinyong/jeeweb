package com.jeeweb.platform.tools.web.api;

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
import com.jeeweb.platform.tools.entity.GenerateRuleEntity;
import com.jeeweb.platform.tools.service.CodeGenerateService;

@RestController
@RequestMapping(value = "/api/platform/tools/code/generate")
public class CodeGeneratApi extends BaseController<Integer, GenerateRuleEntity> {
    @Resource
    private CodeGenerateService codeGenerateService;

    @Override
    protected BaseService<Integer, GenerateRuleEntity> getService() {
        return codeGenerateService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult generate(@PathVariable("id") Integer primaryKey) {
        codeGenerateService.generateCode(primaryKey);
        return new ResponseResult(new Result(), HttpStatus.OK);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/rules", method = RequestMethod.GET)
    public ResponseResult list() {
        return super.listEntities();
    }

    @RequestMapping(value = "/rules", method = RequestMethod.POST)
    public ResponseResult create(@RequestBody GenerateRuleEntity entity, UriComponentsBuilder ucBuilder) {
        return super.createEntity(entity, ucBuilder);
    }

    @RequestMapping(value = "/rules/{id}", method = RequestMethod.GET)
    public ResponseResult get(@PathVariable("id") Integer primaryKey) {
        return super.getEntity(primaryKey);
    }

    @RequestMapping(value = "/rules/{id}", method = RequestMethod.PUT)
    public ResponseResult update(@PathVariable("id") Integer primaryKey, @RequestBody GenerateRuleEntity entity) {
        return super.updateEntity(primaryKey, entity);
    }

    @RequestMapping(value = "/rules/{id}", method = RequestMethod.DELETE)
    public ResponseResult delete(@PathVariable("id") Integer primaryKey) {
        return super.deleteEntity(primaryKey);
    }
}