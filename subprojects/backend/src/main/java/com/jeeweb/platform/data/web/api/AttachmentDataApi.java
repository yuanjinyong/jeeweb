package com.jeeweb.platform.data.web.api;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jeeweb.framework.business.web.controller.SuperController;
import com.jeeweb.framework.business.web.view.AttachmentView;
import com.jeeweb.framework.core.exception.BusinessException;
import com.jeeweb.framework.core.model.ParamsMap;
import com.jeeweb.framework.core.model.ResponseResult;
import com.jeeweb.framework.core.model.Result;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.platform.pub.entity.AttachmentEntity;
import com.jeeweb.platform.pub.enums.AttachmentStatus;
import com.jeeweb.platform.pub.service.AttachmentService;
import com.jeeweb.platform.security.utils.SecurityUtil;
import com.jeeweb.platform.sys.utils.SysUtil;

@RestController
@RequestMapping(value = "/api/platform/data/attachments")
public class AttachmentDataApi extends SuperController {
    private static final Logger LOG = LoggerFactory.getLogger(AttachmentDataApi.class);
    @Resource
    private AttachmentService attachmentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseResult list() {
        ParamsMap params = $params();
        params.put("f_tenant_id", 0);

        List<AttachmentEntity> entities = attachmentService.selectEntityListPage(params);
        for (AttachmentEntity entity : entities) {
            fillFileInfo(entity);
        }

        return new ResponseResult(new Result(params.page(entities)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseResult uplolad(HttpServletRequest request) {
        List<AttachmentEntity> entityList = saveAttachmentList(request);
        try {
            attachmentService.insertEntities(entityList);
            for (AttachmentEntity entity : entityList) {
                fillFileInfo(entity);
            }
            return new ResponseResult(new Result(entityList), HttpStatus.OK);
        } catch (BusinessException e) {
            deleteTempFile(entityList);
            throw e;
        } catch (Exception e) {
            deleteTempFile(entityList);
            throw new BusinessException("文件上传发生错误！", e);
        }
    }

    private List<AttachmentEntity> saveAttachmentList(HttpServletRequest request) {
        File tempDir = getAttachmentDir();
        ServletFileUpload upload = buildServletFileUpload(tempDir);

        List<AttachmentEntity> entityList = new ArrayList<>();

        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            for (int i = 0; i < fileItems.size(); i++) {
                FileItem fileItem = fileItems.get(i);
                // 判断该表单项是否是普通类型
                if (fileItem.isFormField()) {
                    continue;
                }

                AttachmentEntity entity = buildAttachmentEntity(i, fileItem);
                entityList.add(entity);

                File attachmentFile = new File(tempDir, entity.getF_local_path());
                FileUtils.forceMkdir(attachmentFile.getParentFile());
                fileItem.write(attachmentFile);
            }

            if (HelpUtil.isEmpty(entityList)) {
                throw new BusinessException("未获取到文件，请先选择文件！");
            }

            return entityList;
        } catch (BusinessException e) {
            deleteTempFile(entityList);
            throw e;
        } catch (SizeLimitExceededException e) {
            deleteTempFile(entityList);
            throw new BusinessException("请上传大小为2GB以内的文件！");
        } catch (Exception e) {
            deleteTempFile(entityList);
            throw new BusinessException("文件上传发生错误！", e);
        }
    }

    private void deleteTempFile(List<AttachmentEntity> entityList) {
        if (!HelpUtil.isEmpty(entityList)) {
            for (AttachmentEntity entity : entityList) {
                try {
                    FileUtils.forceDelete(new File(entity.getF_local_path()));
                } catch (IOException e) {
                    LOG.error("删除文件{}失败！", entity.getF_local_path());
                }
            }
        }
    }

    private ServletFileUpload buildServletFileUpload(File tempDir) {
        DiskFileItemFactory factory = new DiskFileItemFactory(); // 创建该对象
        factory.setRepository(tempDir); // 指定上传文件的临时目录
        factory.setSizeThreshold(1024 * 1024); // 指定在内存中缓存数据大小,单位为byte
        ServletFileUpload upload = new ServletFileUpload(factory); // 创建该对象
        upload.setHeaderEncoding("utf-8");
        upload.setFileSizeMax(2 * 1024L * 1024L * 1024L); // 指定单个上传文件的最大尺寸
        upload.setSizeMax(-1L); // 指定一次上传多个文件的总尺寸

        return upload;
    }

    private File getAttachmentDir() {
        File tempDir = new File(
                SysUtil.getSetting("ZKPMS.MASTER.ATTACHMENT.DIRECTORY", System.getProperty("java.io.tmpdir")));
        if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
            tempDir = new File("C:/attachment"); // 当为Windows的本地开发环境时，强制设置为固定值
        }

        try {
            FileUtils.forceMkdir(tempDir);
        } catch (IOException e) {
            throw new BusinessException("创建临时目录失败！", e);
        }
        return tempDir;
    }

    private AttachmentEntity buildAttachmentEntity(int index, FileItem fileItem) {
        String originalFileName = fileItem.getName();
        Long f_creator_id = SecurityUtil.getCurUserId();
        Timestamp f_create_time = HelpUtil.getNowTime();

        StringBuffer tempFileName = new StringBuffer(HelpUtil.getNowTime("yyyyMM")).append('/')
                .append(f_create_time.getTime()).append('.').append(f_creator_id).append('.').append(index);
        int idx = originalFileName.lastIndexOf('.');
        if (idx >= 0) {
            tempFileName.append(originalFileName.substring(idx));
        }

        AttachmentEntity entity = new AttachmentEntity();
        entity.setF_tenant_id(0L); // 这里写入0
                                   // 由具体业务保存时保存对应的租户
        // entity.setF_entity_name(""); 由具体业务保存时保存对应的实体名称
        // entity.setF_entity_id(0);由具体业务保存时保存对应的主键ID
        entity.setF_name(originalFileName);
        entity.setF_type(fileItem.getContentType());
        entity.setF_local_path(tempFileName.toString());
        entity.setF_size(fileItem.getSize()); // 文件大小，单位字节
        entity.setF_status(AttachmentStatus.INIT); // 这里状态永远设置为待归档，当表单提交时，由具体的业务侧在表单保存时修改为已归档状态。
        entity.setF_creator_id(f_creator_id);
        entity.setF_created_time(f_create_time);

        return entity;
    }

    @RequestMapping(value = "/{id}/preview", method = RequestMethod.GET)
    public void preview(@PathVariable("id") Integer primaryKey, HttpServletResponse response) {

    }

    @RequestMapping(value = "/{id}/url", method = RequestMethod.GET)
    public ResponseResult url(@PathVariable("id") Long primaryKey) {
        AttachmentEntity entity = attachmentService.selectEntity(primaryKey);
        // TODO
        // if (CompanyContext.getCompanyId(0) != entity.getF_company_id()) {
        // throw new BusinessException("附件不存在！");
        // }

        fillFileInfo(entity);

        return new ResponseResult(new Result(entity), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/download", method = RequestMethod.GET)
    public ModelAndView download(@PathVariable("id") Long primaryKey, HttpServletResponse response) {
        AttachmentEntity entity = attachmentService.selectEntity(primaryKey);
        // TODO
        // if (CompanyContext.getCompanyId(0) != entity.getF_company_id()) {
        // throw new BusinessException("附件不存在！");
        // }

        if (AttachmentStatus.INIT.equals(entity.getF_status())
                || AttachmentStatus.ARCHIVED.equals(entity.getF_status())) {
            return new ModelAndView(new AttachmentView(new File(getAttachmentDir(), entity.getF_local_path()),
                    entity.getF_size(), entity.getF_type(), entity.getF_name()), new HashMap<>());
        } else {
            LOG.error("下载附件失败，文件状态不为已归档！id={}", primaryKey);
            throw new BusinessException("下载附件失败，文件状态不为已归档！");
        }
    }

    private void fillFileInfo(AttachmentEntity entity) {
        entity.setName(entity.getF_name());
        entity.setUrl(String.format(AttachmentEntity.DOWNLOAD_URL, entity.getF_id()));
    }
}