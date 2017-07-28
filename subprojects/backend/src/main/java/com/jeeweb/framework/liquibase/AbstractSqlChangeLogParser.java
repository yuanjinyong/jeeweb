/**
 * 
 */
package com.jeeweb.framework.liquibase;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import liquibase.exception.ChangeLogParseException;
import liquibase.parser.core.sql.SqlChangeLogParser;
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
            return resolver.getResources(physicalChangeLogLocation);
        } catch (IOException e) {
            throw new ChangeLogParseException("Resource does not exist: " + physicalChangeLogLocation, e);
        }
    }

    protected InputStream openChangeLogFile(String fileLocation, ResourceAccessor resourceAccessor)
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
