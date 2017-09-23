/**
 * 
 */
package com.jeeweb.framework.liquibase;

import java.io.IOException;

import org.springframework.core.io.Resource;

import com.jeeweb.framework.core.utils.HelpUtil;

import liquibase.change.core.RawSQLChange;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.ChangeLogParseException;
import liquibase.resource.ResourceAccessor;
import liquibase.util.StreamUtil;

/**
 * @author 袁进勇
 *
 */
public class MultiSqlChangeLogParser extends AbstractSqlChangeLogParser {

    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT + 1;
    }

    @Override
    public DatabaseChangeLog parse(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters,
            ResourceAccessor resourceAccessor) throws ChangeLogParseException {
        DatabaseChangeLog changeLog = new DatabaseChangeLog();
        changeLog.setPhysicalFilePath(physicalChangeLogLocation);

        Resource[] resources = getResources(physicalChangeLogLocation);
        if (!HelpUtil.isEmpty(resources)) {
            for (Resource resource : resources) {
                try {
                    String logicalFilePath = resource.getURL().toString();
                    logicalFilePath = logicalFilePath.substring(logicalFilePath.indexOf("/com/"));
                    changeLog.addChangeSet(parseChangeSet(logicalFilePath, changeLog, resourceAccessor));
                } catch (IOException e) {
                    throw new ChangeLogParseException("Resource does not exist: " + resource, e);
                }
            }
        }

        return changeLog;
    }

    private ChangeSet parseChangeSet(String fileLocation, DatabaseChangeLog changeLog,
            ResourceAccessor resourceAccessor) throws ChangeLogParseException {
        RawSQLChange change = new RawSQLChange();
        change.setResourceAccessor(resourceAccessor);
        change.setSplitStatements(false);
        change.setStripComments(false);

        try {
            change.setSql(StreamUtil.getStreamContents(openFile(resourceAccessor, fileLocation), null));
        } catch (IOException e) {
            throw new ChangeLogParseException(e);
        }

        ChangeSet changeSet = new ChangeSet("raw", "includeAll", false, false, fileLocation, null, null, true,
                ObjectQuotingStrategy.LEGACY, changeLog);
        changeSet.addChange(change);

        return changeSet;
    }
}
