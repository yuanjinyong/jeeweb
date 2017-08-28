package com.jeeweb.activiti.web.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeweb.activiti.entity.ProcessDefinitionEntity;
import com.jeeweb.activiti.service.ProcessDefinitionService;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.business.web.api.BaseApi;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;

@Controller
@RequestMapping(value = "/api/activiti/process/definitions")
public class ProcessDefinitionApi extends BaseApi<String, ProcessDefinitionEntity> {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessDefinitionApi.class);

    @Resource
    private ProcessDefinitionService processDefinitionService;

    @Override
    protected BaseService<String, ProcessDefinitionEntity> getService() {
        return processDefinitionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult list() {
        return super.listEntities();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult get(@PathVariable("id") String primaryKey) {
        return super.getEntity(primaryKey);
    }

    @RequestMapping(value = "/{id}/start", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deploy(@PathVariable("id") String primaryKey) {
        processDefinitionService.startProcess(primaryKey, new HashMap<String, String>());
        return new ResponseResult(new Result(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/diagram", method = RequestMethod.GET)
    public void diagram(@PathVariable("id") String primaryKey, HttpServletResponse response) {
        InputStream in = processDefinitionService.getProcessDiagram(primaryKey);

        // 重置输出流
        response.reset();
        response.setContentType("image/png"); // 设置文件类型

        try {
            IOUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            LOG.error("获取流程图片失败！processDefinitionId={}", primaryKey, e);
            throw new BusinessException("获取流程图片失败！", e);
        }
    }
}
