/**
 *
 */
package com.jeeweb.platform.tools.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.codehaus.groovy.control.CompilationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import groovy.lang.GroovyRuntimeException;
import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;

/**
 * @author 袁进勇
 *
 */
@Component
public class ArchetypeService {
    private static final Logger LOG = LoggerFactory.getLogger(ArchetypeService.class);
    private SimpleTemplateEngine engine = new SimpleTemplateEngine();

    /**
     * @param archetypeDir
     * @param destDir
     * @param feature
     * @throws Exception
     */
    public void generateArchetype(File archetypeDir, File destDir, Map<String, Object> templateParams)
            throws Exception {
        if (!destDir.exists()) {
            FileUtils.forceMkdir(destDir);
        }

        templateParams.put("java", "java");
        templateParams.put("xml", "xml");
        templateParams.put("sql", "sql");
        templateParams.put("jsp", "jsp");
        LOG.info("构造模板需要使用到的参数：" + templateParams);

        File tempDir = new File(destDir.getParentFile(), "temp_" + UUID.randomUUID());
        LOG.info("创建临时目录：" + tempDir.getCanonicalPath());
        FileDeleteStrategy.FORCE.deleteQuietly(tempDir);
        FileUtils.forceMkdir(tempDir);

        try {
            LOG.info("提取模板文件{}到临时目录。", archetypeDir.getCanonicalPath());
            FileUtils.copyDirectory(archetypeDir, tempDir);

            LOG.info("开始渲染临时目录 下的模板文件。");
            renderFiles(tempDir, templateParams);

            LOG.info("复制渲染后的文件到目标目录下：" + destDir.getCanonicalPath());
            FileUtils.copyDirectory(tempDir, destDir);
        } finally {
            LOG.info("删除临时目录：" + tempDir);
            FileDeleteStrategy.FORCE.deleteQuietly(tempDir);
        }
    }

    private void renderFiles(File projectDir, Map<String, Object> params)
            throws CompilationFailedException, ClassNotFoundException, IOException {
        for (File file : projectDir.listFiles()) {
            // 先渲染文件名
            String newFileName = engine.createTemplate(file.getAbsolutePath().replaceAll("\\\\", "/")).make(params)
                    .toString();

            // 重命名
            File newFile = new File(newFileName);
            if (!file.getAbsolutePath().equals(newFile.getAbsolutePath())) {
                LOG.debug("重命名文件：" + file.getCanonicalPath() + " -> " + newFile.getCanonicalPath());
                // 如果只是文件名变更，目录结构没有变更，则改名
                if (file.getParentFile().equals(newFile.getParentFile())) {
                    file.renameTo(newFile);
                }
                // 如果目录结构也变更了，则移动新目录下，并删除老目录
                else {
                    newFile.mkdirs();
                    FileUtils.copyDirectory(file, newFile, false); // 不保留源文件的时间
                    FileUtils.deleteDirectory(file);
                }
            }

            // 如果是目录，递归处理
            if (newFile.isDirectory()) {
                renderFiles(newFile, params);
            }
            // 如果是文件，渲染文件内容
            else {
                InputStreamReader reader = null;
                OutputStreamWriter writer = null;
                try {
                    reader = new InputStreamReader(new FileInputStream(newFile), "UTF-8");
                    Writable result = engine.createTemplate(reader).make(params);
                    writer = new OutputStreamWriter(new FileOutputStream(newFile), "UTF-8");
                    result.writeTo(writer);
                    writer.flush();
                } catch (GroovyRuntimeException e) {
                    LOG.error("渲染文件：{}失败！", newFile);
                    throw e;
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                    if (writer != null) {
                        writer.close();
                    }
                }
            }
        }
    }
}
