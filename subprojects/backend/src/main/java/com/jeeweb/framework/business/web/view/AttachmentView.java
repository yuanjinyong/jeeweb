/**
 * 
 */
package com.jeeweb.framework.business.web.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @author 袁进勇
 *
 */
public class AttachmentView extends AbstractView {
    private static final Logger LOG = LoggerFactory.getLogger(AttachmentView.class);

    private File file;
    private Long size;
    private String type;
    private String attachmentName;

    public AttachmentView(File file, Long size, String type, String attachmentName) {
        this.file = file;
        this.size = size;
        this.type = type;
        this.attachmentName = attachmentName;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String fileName = URLEncoder.encode(attachmentName, "UTF-8"); // 中文文件名需要编码
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType(type); // 设置文件类型
        response.setContentLengthLong(size);

        InputStream in = null;
        try {
            in = new FileInputStream(file);
            IOUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            LOG.error("附件{}下载失败！", file.getAbsolutePath(), e);
            throw new Exception("附件下载失败！", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.error("关闭文件输入流失败}", e);
                }
            }
        }
    }
}
