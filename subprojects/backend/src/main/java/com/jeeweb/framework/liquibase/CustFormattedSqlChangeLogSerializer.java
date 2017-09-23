/**
 * 
 */
package com.jeeweb.framework.liquibase;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.jeeweb.framework.core.utils.HelpUtil;

import liquibase.change.Change;
import liquibase.changelog.ChangeLogChild;
import liquibase.changelog.ChangeSet;
import liquibase.configuration.GlobalConfiguration;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.database.Database;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.serializer.core.formattedsql.FormattedSqlChangeLogSerializer;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;

/**
 * @author 袁进勇
 *
 */
public class CustFormattedSqlChangeLogSerializer extends FormattedSqlChangeLogSerializer {
    @Override
    public <T extends ChangeLogChild> void write(List<T> children, OutputStream out) throws IOException {
        if (HelpUtil.isEmpty(children)) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (T child : children) {
            String changeSetSql = serialize(child, true);
            if (!HelpUtil.isEmpty(changeSetSql)) {
                builder.append(changeSetSql).append("\n");
            }
        }

        if (builder.length() > 0) {
            out.write(new StringBuilder("-- liquibase formatted sql\n\n").append(builder).toString()
                    .getBytes(LiquibaseConfiguration.getInstance().getConfiguration(GlobalConfiguration.class)
                            .getOutputEncoding()));
        }
    }

    @Override
    public String serialize(LiquibaseSerializable object, boolean pretty) {
        if (object instanceof ChangeSet) {
            StringBuilder builder = new StringBuilder();

            ChangeSet changeSet = (ChangeSet) object;
            Database database = getTargetDatabase(changeSet);

            String author = (changeSet.getAuthor()).replaceAll("\\s+", "_");
            author = author.replace("_(generated)", "");

            for (Change change : changeSet.getChanges()) {
                Sql[] sqls = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database),
                        database);
                if (sqls != null) {
                    for (Sql sql : sqls) {
                        builder.append(sql.toSql()).append(sql.getEndDelimiter()).append("\n");
                    }
                }
            }

            if (builder.length() > 0) {
                String id = changeSet.getId();
                String[] ids = id.split("-");
                if (ids.length > 1) {
                    id = String.format("%s-%08d", ids[0], Integer.valueOf(ids[1]));
                }
                return new StringBuilder().append("-- changeset ").append(author).append(":").append(id).append("\n")
                        .append(builder).toString();
            }

            return null;
        } else {
            throw new UnexpectedLiquibaseException("Cannot serialize object type: " + object.getClass().getName());
        }
    }
}
