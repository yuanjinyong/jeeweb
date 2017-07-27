/**
 * 
 */
package com.jeeweb.framework.liquibase;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.jeeweb.framework.core.utils.HelpUtil;

import liquibase.change.core.RawSQLChange;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.ChangeLogParseException;
import liquibase.parser.core.sql.SqlChangeLogParser;
import liquibase.resource.ResourceAccessor;
import liquibase.util.StreamUtil;

/**
 * @author 袁进勇
 *
 */
public class MultiSqlChangeLogParser extends SqlChangeLogParser {

    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT + 1;
    }

    @Override
    public DatabaseChangeLog parse(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters,
            ResourceAccessor resourceAccessor) throws ChangeLogParseException {

        DatabaseChangeLog changeLog = new DatabaseChangeLog();
        changeLog.setPhysicalFilePath(physicalChangeLogLocation);

        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(physicalChangeLogLocation);
            if (!HelpUtil.isEmpty(resources)) {
                for (Resource resource : resources) {
                    changeLog.addChangeSet(parseChangeSet(resource.getURL().toString(), changeLog, resourceAccessor));
                }
            }
        } catch (IOException e) {
            throw new ChangeLogParseException("Resource does not exist: " + physicalChangeLogLocation);
        }

        return changeLog;
    }

    private ChangeSet parseChangeSet(String physicalChangeLogLocation, DatabaseChangeLog changeLog,
            ResourceAccessor resourceAccessor) throws ChangeLogParseException {
        RawSQLChange change = new RawSQLChange();

        try {
            InputStream sqlStream = StreamUtil.singleInputStream(physicalChangeLogLocation, resourceAccessor);
            if (sqlStream == null) {
                throw new ChangeLogParseException("File does not exist: " + physicalChangeLogLocation);
            }
            String sql = StreamUtil.getStreamContents(sqlStream, null);
            change.setSql(sql);
        } catch (IOException e) {
            throw new ChangeLogParseException(e);
        }
        change.setResourceAccessor(resourceAccessor);
        change.setSplitStatements(false);
        change.setStripComments(false);

        ChangeSet changeSet = new ChangeSet("raw", "includeAll", false, false, physicalChangeLogLocation, null, null,
                true, ObjectQuotingStrategy.LEGACY, changeLog);
        changeSet.addChange(change);

        return changeSet;
    }
}
