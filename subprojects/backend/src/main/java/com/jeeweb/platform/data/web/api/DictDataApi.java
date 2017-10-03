package com.jeeweb.platform.data.web.api;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jeeweb.framework.business.web.controller.SuperController;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.platform.sys.service.DictService;

@RestController
@RequestMapping(value = "/api/platform/data/dicts")
public class DictDataApi extends SuperController {
    @Resource
    private DictService dictService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseResult getDicts() {
        return new ResponseResult(new Result(dictService.getDicts()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{dictCode}", method = RequestMethod.GET)
    public ResponseResult getDict(@PathVariable("dictCode") String dictCode) {
        return new ResponseResult(new Result(dictService.getDictItemList(dictCode)), HttpStatus.OK);
    }
}
