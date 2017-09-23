/**
 * 
 */
package com.jeeweb.framework.liquibase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import liquibase.exception.ChangeLogParseException;
import liquibase.parser.core.sql.SqlChangeLogParser;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.util.StreamUtil;

/**
 * @author 袁进勇
 *
 */
public abstract class AbstractSqlChangeLogParser extends SqlChangeLogParser {
    protected PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    protected Resource[] getResources(String physicalChangeLogLocation) throws ChangeLogParseException {
        try {
            File changeLogFile = new File(physicalChangeLogLocation);
            if (changeLogFile.exists()) {
                physicalChangeLogLocation = changeLogFile.toURI().toURL().toString();
            }

            return resolver.getResources(physicalChangeLogLocation);
        } catch (IOException e) {
            throw new ChangeLogParseException("Resource does not exist: " + physicalChangeLogLocation, e);
        }
    }

    protected String getFileLocation(ResourceAccessor resourceAccessor, Resource resource) throws IOException {
        if (resourceAccessor instanceof FileSystemResourceAccessor) {
            return resource.getFile().getAbsolutePath();
        } else {
            return resource.getURL().toString();
        }
    }

    protected InputStream openFile(ResourceAccessor resourceAccessor, Resource resource)
            throws ChangeLogParseException, IOException {
        return openFile(resourceAccessor, getFileLocation(resourceAccessor, resource));
    }

    protected InputStream openFile(ResourceAccessor resourceAccessor, String fileLocation)
            throws ChangeLogParseException {
        try {
            InputStream resourceAsStream = StreamUtil.singleInputStream(fileLocation, resourceAccessor);
            if (resourceAsStream != null) {
                return resourceAsStream;
            }

            throw new ChangeLogParseException("File does not exist: " + fileLocation);
        } catch (IOException e) {
            throw new ChangeLogParseException("File does not exist: " + fileLocation, e);
        }
    }
}
