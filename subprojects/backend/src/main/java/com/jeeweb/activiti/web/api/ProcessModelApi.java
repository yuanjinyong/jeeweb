package com.jeeweb.activiti.web.api;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.jeeweb.activiti.entity.ProcessModelEntity;
import com.jeeweb.activiti.service.ProcessModelService;
import com.jeeweb.framework.business.service.BaseService;
import com.jeeweb.framework.business.web.api.BaseApi;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;

@Controller
@RequestMapping(value = "/api/activiti/process/models")
public class ProcessModelApi extends BaseApi<String, ProcessModelEntity> {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessModelApi.class);

    @Resource
    private ProcessModelService processModelService;

    @Override
    protected BaseService<String, ProcessModelEntity> getService() {
        return processModelService;
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

    /**
     * 新建一个空模型
     * 
     * @param entity
     * @param ucBuilder
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult create(@RequestBody ProcessModelEntity entity, UriComponentsBuilder ucBuilder) {
        return super.createEntity(entity, ucBuilder);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseResult update(@PathVariable("id") String primaryKey, @RequestBody ProcessModelEntity entity) {
        return super.updateEntity(primaryKey, entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseResult delete(@PathVariable("id") String primaryKey) {
        return super.deleteEntity(primaryKey);
    }

    @RequestMapping(value = "/{id}/increase/version", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult increaseVersion(@PathVariable("id") String primaryKey) {
        processModelService.increaseModelVersion(primaryKey);
        return new ResponseResult(new Result(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/deploy", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deploy(@PathVariable("id") String primaryKey) {
        processModelService.deployModel(primaryKey);
        return new ResponseResult(new Result(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/export", method = RequestMethod.GET)
    public void export(@PathVariable("id") String primaryKey, HttpServletResponse response) {
        ProcessModelEntity modelEntity = processModelService.selectEntity(primaryKey);
        BpmnModel bpmnModel = processModelService.getBpmnModel(primaryKey);
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        String fileName = modelEntity.getModel().getName() + "-" + modelEntity.getModel().getVersion() + ".bpmn20.xml";

        // 重置输出流
        response.reset();
        response.setHeader("Pragma", "private");
        response.setHeader("Cache-Control", "private, must-revalidate");
        response.setContentType("text/xml;charset=UTF-8"); // 设置文件类型
        response.setIntHeader("Content-Length", bpmnBytes.length);// 设置下载文件大小
        try {
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO_8859_1")); // 中文文件名需要转为GB2312编码

            response.getOutputStream().write(bpmnBytes);
            response.flushBuffer();
        } catch (IOException e) {
            LOG.error("导出流程模型失败！mainProcessId={}", bpmnModel.getMainProcess().getId(), e);
            throw new BusinessException("导出流程模型失败！", e);
        }
    }
}
