/**
 * 
 */
package com.jeeweb.framework.liquibase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liquibase.database.Database;
import liquibase.database.core.MySQLDatabase;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.SetColumnRemarksGenerator;
import liquibase.statement.core.SetColumnRemarksStatement;
import liquibase.util.StringUtils;

/**
 * @author 袁进勇
 *
 */
public class MySQLSetColumnRemarksGenerator extends SetColumnRemarksGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(MySQLSetColumnRemarksGenerator.class);

    @Override
    public boolean supports(SetColumnRemarksStatement statement, Database database) {
        return database instanceof MySQLDatabase;
    }

    @Override
    public Sql[] generateSql(SetColumnRemarksStatement statement, Database database,
            SqlGeneratorChain sqlGeneratorChain) {
        String remarksEscaped = database.escapeStringForDatabase(StringUtils.trimToEmpty(statement.getRemarks()));
        LOG.warn("MySQL数据库不支持修改列描述，已忽略“ALTER TABLE `" + statement.getTableName() + "` MODIFY COLUMN `"
                + statement.getColumnName() + "` COMMENT '" + remarksEscaped + "';”！");
        return null;
    }
}
