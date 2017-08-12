/**
 * 
 */
package com.jeeweb.framework.liquibase;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.jeeweb.framework.core.utils.HelpUtil;

import liquibase.Labels;
import liquibase.change.core.EmptyChange;
import liquibase.change.core.RawSQLChange;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.ChangeLogParseException;
import liquibase.precondition.core.PreconditionContainer;
import liquibase.precondition.core.SqlPrecondition;
import liquibase.resource.ResourceAccessor;
import liquibase.resource.UtfBomAwareReader;
import liquibase.util.StringUtils;
import liquibase.util.SystemUtils;

/**
 * @author 袁进勇
 *
 */
public class MultiFormattedSqlChangeLogParser extends AbstractSqlChangeLogParser {
    private static final Logger LOG = LoggerFactory.getLogger(MultiFormattedSqlChangeLogParser.class);
    private Pattern changeLogPattern = buildPattern("\\-\\-\\s*liquibase formatted.*");
    private Pattern changeSetPattern = buildPattern("\\-\\-[\\s]*changeset\\s+([^:]+):(\\S+).*");
    private Pattern rollbackPattern = buildPattern("\\s*\\-\\-[\\s]*rollback (.*)");
    private Pattern preconditionsPattern = buildPattern("\\s*\\-\\-[\\s]*preconditions(.*)");
    private Pattern preconditionPattern = buildPattern("\\s*\\-\\-[\\s]*precondition\\-([a-zA-Z0-9-]+) (.*)");
    private Pattern stripCommentsPattern = buildPattern(".*stripComments:(\\w+).*");
    private Pattern splitStatementsPattern = buildPattern(".*splitStatements:(\\w+).*");
    private Pattern rollbackSplitStatementsPattern = buildPattern(".*rollbackSplitStatements:(\\w+).*");
    private Pattern endDelimiterPattern = buildPattern(".*endDelimiter:(\\S*).*");
    private Pattern rollbackEndDelimiterPattern = buildPattern(".*rollbackEndDelimiter:(\\S*).*");
    private Pattern commentPattern = buildPattern("\\-\\-[\\s]*comment:? (.*)");
    private Pattern validCheckSumPattern = buildPattern("\\-\\-[\\s]*validCheckSum:? (.*)");
    private Pattern runOnChangePattern = buildPattern(".*runOnChange:(\\w+).*");
    private Pattern runAlwaysPattern = buildPattern(".*runAlways:(\\w+).*");
    private Pattern contextPattern = buildPattern(".*context:(\".*\"|\\S*).*");
    private Pattern logicalFilePathPattern = buildPattern(".*logicalFilePath:(\\S*).*");
    private Pattern labelsPattern = buildPattern(".*labels:(\\S*).*");
    private Pattern runInTransactionPattern = buildPattern(".*runInTransaction:(\\w+).*");
    private Pattern dbmsPattern = buildPattern(".*dbms:([^,][\\w!,]+).*");
    private Pattern failOnErrorPattern = buildPattern(".*failOnError:(\\w+).*");
    private Pattern onFailPattern = buildPattern(".*onFail:(\\w+).*");
    private Pattern onErrorPattern = buildPattern(".*onError:(\\w+).*");
    private Pattern onUpdateSqlPattern = buildPattern(".*onUpdateSQL:(\\w+).*");

    @Override
    public boolean supports(String physicalChangeLogLocation, ResourceAccessor resourceAccessor) {
        BufferedReader reader = null;
        try {
            Resource[] resources = getResources(physicalChangeLogLocation);
            if (!HelpUtil.isEmpty(resources)) {
                String fileLocation = resources[0].getURL().toString();
                if (fileLocation.endsWith(".sql")) {
                    reader = new BufferedReader(
                            new UtfBomAwareReader(openChangeLogFile(fileLocation, resourceAccessor)));
                    String line = reader.readLine();
                    return line != null && line.matches("\\-\\-\\s*liquibase formatted.*");
                }
            }
        } catch (ChangeLogParseException e) {
            LOG.debug("Exception reading " + physicalChangeLogLocation, e);
        } catch (IOException e) {
            LOG.debug("Exception reading " + physicalChangeLogLocation, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.debug("Exception closing " + physicalChangeLogLocation, e);
                }
            }
        }

        return false;
    }

    @Override
    public int getPriority() {
        return PRIORITY_DEFAULT + 6;
    }

    @Override
    public DatabaseChangeLog parse(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters,
            ResourceAccessor resourceAccessor) throws ChangeLogParseException {
        DatabaseChangeLog changeLog = new DatabaseChangeLog();
        changeLog.setChangeLogParameters(changeLogParameters);
        changeLog.setPhysicalFilePath(physicalChangeLogLocation);

        Resource[] resources = getResources(physicalChangeLogLocation);
        if (HelpUtil.isEmpty(resources)) {
            return changeLog;
        }

        for (Resource resource : resources) {
            parseResource(resource, changeLog, resourceAccessor);
        }

        // 返回之前，根据ChangeSet的Id进行排序
        List<ChangeSet> changeSetList = changeLog.getChangeSets();
        Collections.sort(changeSetList, new Comparator<ChangeSet>() {
            @Override
            public int compare(ChangeSet o1, ChangeSet o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });

        return changeLog;
    }

    private void parseResource(Resource resource, DatabaseChangeLog changeLog, ResourceAccessor resourceAccessor)
            throws ChangeLogParseException {
        ChangeLogParameters changeLogParameters = changeLog.getChangeLogParameters();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new UtfBomAwareReader(openChangeLogFile(resource.getURL().toString(), resourceAccessor)));
            StringBuffer currentSql = new StringBuffer();
            StringBuffer currentRollbackSql = new StringBuffer();

            ChangeSet changeSet = null;
            RawSQLChange change = null;

            boolean rollbackSplitStatements = true;
            String rollbackEndDelimiter = null;

            String line;
            while ((line = reader.readLine()) != null) {

                Matcher changeLogPatterMatcher = changeLogPattern.matcher(line);
                if (changeLogPatterMatcher.matches()) {
                    Matcher logicalFilePathMatcher = logicalFilePathPattern.matcher(line);
                    String logicalFilePath = parseString(logicalFilePathMatcher);
                    if (HelpUtil.isEmpty(logicalFilePath)) {
                        logicalFilePath = resource.getURL().toString();
                        logicalFilePath = logicalFilePath.substring(logicalFilePath.indexOf("/com/"));
                    }
                    changeLog.setLogicalFilePath(logicalFilePath);
                }

                Matcher changeSetPatternMatcher = changeSetPattern.matcher(line);
                if (changeSetPatternMatcher.matches()) {
                    String finalCurrentSql = changeLogParameters
                            .expandExpressions(StringUtils.trimToNull(currentSql.toString()), changeLog);
                    if (changeSet != null) {

                        if (finalCurrentSql == null) {
                            throw new ChangeLogParseException("No SQL for changeset " + changeSet.toString(false));
                        }

                        change.setSql(finalCurrentSql);

                        if (StringUtils.trimToNull(currentRollbackSql.toString()) != null) {
                            if (currentRollbackSql.toString().trim().toLowerCase().matches("^not required.*")) {
                                changeSet.addRollbackChange(new EmptyChange());
                            } else {
                                RawSQLChange rollbackChange = new RawSQLChange();
                                rollbackChange.setSql(changeLogParameters
                                        .expandExpressions(currentRollbackSql.toString(), changeLog));
                                changeSet.addRollbackChange(rollbackChange);
                            }
                        }
                    }

                    Matcher stripCommentsPatternMatcher = stripCommentsPattern.matcher(line);
                    Matcher splitStatementsPatternMatcher = splitStatementsPattern.matcher(line);
                    Matcher rollbackSplitStatementsPatternMatcher = rollbackSplitStatementsPattern.matcher(line);
                    Matcher endDelimiterPatternMatcher = endDelimiterPattern.matcher(line);
                    Matcher rollbackEndDelimiterPatternMatcher = rollbackEndDelimiterPattern.matcher(line);

                    Matcher logicalFilePathMatcher = logicalFilePathPattern.matcher(line);
                    Matcher runOnChangePatternMatcher = runOnChangePattern.matcher(line);
                    Matcher runAlwaysPatternMatcher = runAlwaysPattern.matcher(line);
                    Matcher contextPatternMatcher = contextPattern.matcher(line);
                    Matcher labelsPatternMatcher = labelsPattern.matcher(line);
                    Matcher runInTransactionPatternMatcher = runInTransactionPattern.matcher(line);
                    Matcher dbmsPatternMatcher = dbmsPattern.matcher(line);
                    Matcher failOnErrorPatternMatcher = failOnErrorPattern.matcher(line);

                    boolean stripComments = parseBoolean(stripCommentsPatternMatcher, changeSet, true);
                    boolean splitStatements = parseBoolean(splitStatementsPatternMatcher, changeSet, true);
                    rollbackSplitStatements = parseBoolean(rollbackSplitStatementsPatternMatcher, changeSet, true);
                    boolean runOnChange = parseBoolean(runOnChangePatternMatcher, changeSet, false);
                    boolean runAlways = parseBoolean(runAlwaysPatternMatcher, changeSet, false);
                    boolean runInTransaction = parseBoolean(runInTransactionPatternMatcher, changeSet, true);
                    boolean failOnError = parseBoolean(failOnErrorPatternMatcher, changeSet, true);

                    String endDelimiter = parseString(endDelimiterPatternMatcher);
                    rollbackEndDelimiter = parseString(rollbackEndDelimiterPatternMatcher);
                    String context = StringUtils.trimToNull(StringUtils.trimToEmpty(parseString(contextPatternMatcher))
                            .replaceFirst("^\"", "").replaceFirst("\"$", "") // remove surrounding
                                                                             // quotes if they're in
                                                                             // there
                    );
                    String labels = parseString(labelsPatternMatcher);
                    String logicalFilePath = parseString(logicalFilePathMatcher);
                    if (logicalFilePath == null || "".equals(logicalFilePath)) {
                        logicalFilePath = changeLog.getLogicalFilePath();
                    }
                    String dbms = parseString(dbmsPatternMatcher);

                    changeSet = new ChangeSet(changeSetPatternMatcher.group(2), changeSetPatternMatcher.group(1),
                            runAlways, runOnChange, logicalFilePath, context, dbms, runInTransaction,
                            changeLog.getObjectQuotingStrategy(), changeLog);
                    changeSet.setLabels(new Labels(labels));
                    changeSet.setFailOnError(failOnError);
                    changeLog.addChangeSet(changeSet);

                    change = new RawSQLChange();
                    change.setSql(finalCurrentSql);
                    change.setResourceAccessor(resourceAccessor);
                    change.setSplitStatements(splitStatements);
                    change.setStripComments(stripComments);
                    change.setEndDelimiter(endDelimiter);
                    changeSet.addChange(change);

                    currentSql = new StringBuffer();
                    currentRollbackSql = new StringBuffer();
                } else {
                    if (changeSet != null) {
                        Matcher commentMatcher = commentPattern.matcher(line);
                        Matcher rollbackMatcher = rollbackPattern.matcher(line);
                        Matcher preconditionsMatcher = preconditionsPattern.matcher(line);
                        Matcher preconditionMatcher = preconditionPattern.matcher(line);
                        Matcher validCheckSumMatcher = validCheckSumPattern.matcher(line);

                        if (commentMatcher.matches()) {
                            if (commentMatcher.groupCount() == 1) {
                                changeSet.setComments(commentMatcher.group(1));
                            }
                        } else if (validCheckSumMatcher.matches()) {
                            if (validCheckSumMatcher.groupCount() == 1) {
                                changeSet.addValidCheckSum(validCheckSumMatcher.group(1));
                            }
                        } else if (rollbackMatcher.matches()) {
                            if (rollbackMatcher.groupCount() == 1) {
                                currentRollbackSql.append(rollbackMatcher.group(1)).append(SystemUtils.LINE_SEPARATOR);
                            }
                        } else if (preconditionsMatcher.matches()) {
                            if (preconditionsMatcher.groupCount() == 1) {
                                String body = preconditionsMatcher.group(1);
                                Matcher onFailMatcher = onFailPattern.matcher(body);
                                Matcher onErrorMatcher = onErrorPattern.matcher(body);
                                Matcher onUpdateSqlMatcher = onUpdateSqlPattern.matcher(body);

                                PreconditionContainer pc = new PreconditionContainer();
                                pc.setOnFail(StringUtils.trimToNull(parseString(onFailMatcher)));
                                pc.setOnError(StringUtils.trimToNull(parseString(onErrorMatcher)));
                                pc.setOnSqlOutput(StringUtils.trimToNull(parseString(onUpdateSqlMatcher)));
                                changeSet.setPreconditions(pc);
                            }
                        } else if (preconditionMatcher.matches()) {
                            if (changeSet.getPreconditions() == null) {
                                // create the defaults
                                changeSet.setPreconditions(new PreconditionContainer());
                            }
                            if (preconditionMatcher.groupCount() == 2) {
                                String name = StringUtils.trimToNull(preconditionMatcher.group(1));
                                if (name != null) {
                                    String body = preconditionMatcher.group(2).trim();
                                    if ("sql-check".equals(name)) {
                                        changeSet.getPreconditions()
                                                .addNestedPrecondition(parseSqlCheckCondition(changeLogParameters
                                                        .expandExpressions(StringUtils.trimToNull(body),
                                                                changeSet.getChangeLog())));
                                    } else {
                                        throw new ChangeLogParseException(
                                                "The '" + name + "' precondition type is not supported.");
                                    }
                                }
                            }
                        } else {
                            currentSql.append(line).append(SystemUtils.LINE_SEPARATOR);
                        }
                    }
                }
            }

            if (changeSet != null) {
                change.setSql(changeLogParameters.expandExpressions(StringUtils.trimToNull(currentSql.toString()),
                        changeSet.getChangeLog()));

                if (change.getEndDelimiter() == null && StringUtils.trimToEmpty(change.getSql()).endsWith("\n/")) {
                    change.setEndDelimiter("\n/$");
                }

                if (StringUtils.trimToNull(currentRollbackSql.toString()) != null) {
                    if (currentRollbackSql.toString().trim().toLowerCase().matches("^not required.*")) {
                        changeSet.addRollbackChange(new EmptyChange());
                    } else {
                        RawSQLChange rollbackChange = new RawSQLChange();
                        rollbackChange.setSql(changeLogParameters.expandExpressions(currentRollbackSql.toString(),
                                changeSet.getChangeLog()));
                        rollbackChange.setSplitStatements(rollbackSplitStatements);
                        if (rollbackEndDelimiter != null) {
                            rollbackChange.setEndDelimiter(rollbackEndDelimiter);
                        }
                        changeSet.addRollbackChange(rollbackChange);
                    }
                }
            }

        } catch (IOException e) {
            throw new ChangeLogParseException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    private SqlPrecondition parseSqlCheckCondition(String body) throws ChangeLogParseException {
        Pattern[] patterns = new Pattern[] {
                Pattern.compile("^(?:expectedResult:)?(\\w+) (.*)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("^(?:expectedResult:)?'([^']+)' (.*)", Pattern.CASE_INSENSITIVE),
                Pattern.compile("^(?:expectedResult:)?\"([^\"]+)\" (.*)", Pattern.CASE_INSENSITIVE) };
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(body);
            if (matcher.matches() && matcher.groupCount() == 2) {
                SqlPrecondition p = new SqlPrecondition();
                p.setExpectedResult(matcher.group(1));
                p.setSql(matcher.group(2));
                return p;
            }
        }
        throw new ChangeLogParseException("Could not parse a SqlCheck precondition from '" + body + "'.");
    }

    private String parseString(Matcher matcher) {
        String endDelimiter = null;
        if (matcher.matches()) {
            endDelimiter = matcher.group(1);
        }
        return endDelimiter;
    }

    private boolean parseBoolean(Matcher matcher, ChangeSet changeSet, boolean defaultValue)
            throws ChangeLogParseException {
        boolean stripComments = defaultValue;
        if (matcher.matches()) {
            try {
                stripComments = Boolean.parseBoolean(matcher.group(1));
            } catch (Exception e) {
                throw new ChangeLogParseException("Cannot parse " + changeSet + " "
                        + matcher.toString().replaceAll("\\.*", "") + " as a boolean");
            }
        }
        return stripComments;
    }

    private static Pattern buildPattern(String pattern) {
        return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }
}
