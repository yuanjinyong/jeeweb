package com.jeeweb.framework.core.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jeeweb.framework.core.model.ParamsMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.framework.core.utils.MetaUtil;

/**
 * 通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，重写sql语句实现物理分页。
 * 
 * @author 袁进勇
 */
@Component
@Intercepts({ //
        @Signature(type = StatementHandler.class, method = "prepare", //
                args = { Connection.class, Integer.class }), //
        @Signature(type = StatementHandler.class, method = "query", //
                args = { Statement.class, ResultHandler.class }) //
})
public class PageInterceptor implements Interceptor {
    private static final String DB_PRODUCT_NAME_MYSQL = "MySQL";
    private static final String DB_PRODUCT_NAME_SQLSERVER = "Microsoft SQL Server";

    @Value("${mybatis.page.sqlIdRegex:.*ListPage$}")
    private String sqlIdRegex;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (!(target instanceof RoutingStatementHandler)) {
            return invocation.proceed();
        }

        MetaObject metaObject = MetaUtil.getMetaObject(invocation.getTarget());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!mappedStatement.getId().matches(sqlIdRegex)) {
            return invocation.proceed();
        }

        // 如果没有传入任何参数或者入参不是统一的ParamsMap，则不进行处理
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject == null || !(parameterObject instanceof ParamsMap)) {
            return invocation.proceed();
        }

        if ("prepare".equals(invocation.getMethod().getName())) {
            Log log = mappedStatement.getStatementLog();
            if (log.isTraceEnabled()) {
                log.trace(new StringBuffer("执行SQL：").append(mappedStatement.getId()).append("\n对应XML：")
                        .append(mappedStatement.getResource()).append("\n原始的SQL如下：\n    ").append(boundSql.getSql())
                        .toString());
            }

            return prepare(invocation, mappedStatement, boundSql);
        }

        return query(invocation, mappedStatement, boundSql);
    }

    private Object query(Invocation invocation, MappedStatement mappedStatement, BoundSql boundSql) throws Throwable {
        // 如果入参中已经有了为0的totalCount，则不需要再去查询数据了
        ParamsMap params = (ParamsMap) boundSql.getParameterObject();
        Integer totalCount = params.getTotalCount();
        if (totalCount != null && totalCount == 0) {
            Log log = mappedStatement.getStatementLog();
            if (log.isDebugEnabled()) {
                log.debug(new StringBuffer("检测到").append(ParamsMap.TOTAL_COUNT).append('=')
                        .append("0，不再执行查询SQL语句，直接返回空列表。").toString());
            }
            return new ArrayList<>();
        }

        return invocation.proceed();
    }

    private Object prepare(Invocation invocation, MappedStatement mappedStatement, BoundSql boundSql) throws Throwable {
        MetaObject metaObject = MetaUtil.getMetaObject(invocation.getTarget());
        Connection connection = (Connection) invocation.getArgs()[0];
        try {
            processTotalCount(connection, mappedStatement, boundSql);
            processPagenation(connection, mappedStatement, boundSql, metaObject);
            Object result = invocation.proceed();
            return result;
        } catch (Exception e) {
            if (e instanceof SQLException) {
                Log log = mappedStatement.getStatementLog();
                log.error(new StringBuffer("执行SQL：").append(mappedStatement.getId()).append("\n对应XML：")
                        .append(mappedStatement.getResource()).append("\n原始的SQL如下：\n    ").append(boundSql.getSql())
                        .toString());
            }

            throw e;
        }
    }

    private void processTotalCount(Connection connection, MappedStatement mappedStatement, BoundSql boundSql)
            throws Exception, SQLException {
        Log log = mappedStatement.getStatementLog();
        ParamsMap params = (ParamsMap) boundSql.getParameterObject();
        // 如果入参中已经传入了有值的totalCount，则不需要再去查询出总数了
        if (!params.hasTotalCount() || params.getTotalCount() != null) {
            log.trace("无需查询总记录数。");
            return;
        }

        PreparedStatement countStmt = null;
        ResultSet resultSet = null;
        try {
            String countSql = generatCountSql(mappedStatement, boundSql);
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), params);

            setParameters(countStmt, mappedStatement, countBoundSql, params);

            resultSet = countStmt.executeQuery();
            if (resultSet.next()) {
                params.setTotalCount(resultSet.getInt(1));
                if (log.isDebugEnabled()) {
                    log.debug(new StringBuffer("设置总记录数：").append(ParamsMap.TOTAL_COUNT).append('=')
                            .append(params.getTotalCount()).append("\n").append(countSql).toString());
                }
            }
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } finally {
                if (countStmt != null) {
                    countStmt.close();
                }
            }
        }
    }

    private String generatCountSql(MappedStatement mappedStatement, BoundSql boundSql) {
        String countSqlId = mappedStatement.getId().replaceAll(sqlIdRegex, "ListCount");
        if (mappedStatement.getConfiguration().hasStatement(countSqlId)) {
            MappedStatement countMappedStatement = mappedStatement.getConfiguration().getMappedStatement(countSqlId);

            return countMappedStatement.getBoundSql(boundSql.getParameterObject()).getSql();
        } else {
            return "SELECT COUNT(1) TOTAL_COUNT FROM (" + boundSql.getSql() + ") _TEMP_C ";
        }
    }

    private void processPagenation(Connection connection, MappedStatement mappedStatement, BoundSql boundSql,
            MetaObject metaObject) throws SQLException, Exception {
        Log log = mappedStatement.getStatementLog();
        ParamsMap params = (ParamsMap) boundSql.getParameterObject();
        if (!(params.hasPageSize() || params.hasOrderBy())) {
            log.trace("无需分页或排序。");
            return;
        }

        if (params.getPageNo() == null) {
            params.defaultPageNo();
            if (log.isDebugEnabled()) {
                log.debug(new StringBuffer("设置当前页号：").append(ParamsMap.PAGE_NO).append("=").append(params.getPageNo())
                        .toString());
            }
        }

        String pageSql = generatePageSql(connection.getMetaData().getDatabaseProductName(), boundSql.getSql(), params);
        metaObject.setValue("delegate.boundSql.sql", pageSql);
        if (log.isDebugEnabled()) {
            log.debug(new StringBuffer("重写分页和排序后的SQL如下：\n").append(pageSql).toString());
        }
    }

    private String generatePageSql(String databaseProductName, String sql, ParamsMap params) {
        StringBuffer pageSql = new StringBuffer();
        if (DB_PRODUCT_NAME_MYSQL.equals(databaseProductName)) {
            if (params.hasOrderBy()) {
                pageSql.append("SELECT * FROM (\n" + sql + "\n) _TEMP_O\nORDER BY " + params.getOrderBy());
            } else {
                pageSql.append(sql);
            }

            if (params.hasPageSize()) {
                pageSql.append(" \nLIMIT " + params.getBeginRowNo() + "," + params.getPageSize());
            }
        } else if (DB_PRODUCT_NAME_SQLSERVER.equals(databaseProductName)) {
            pageSql.append("SELECT * FROM (\n");
            pageSql.append("    SELECT *, ROW_NUMBER() OVER(ORDER BY ")
                    .append(HelpUtil.isEmpty(params.getOrderBy()) ? "(SELECT 0)" : params.getOrderBy())
                    .append(") RowNumber FROM (\n");
            pageSql.append(sql);
            pageSql.append("\n    ) _TEMP_O\n");
            pageSql.append(") _TEMP_P ");
            if (params.hasPageSize()) {
                Integer beginRow = params.getBeginRowNo();
                pageSql.append(" WHERE _TEMP_P.RowNumber > ").append(beginRow).append(" AND _TEMP_P.RowNumber <= ")
                        .append(beginRow + params.getPageSize());
            }
        } else if ("oracle".equals(databaseProductName)) {
            // pageSql.append("SELECT * FROM (SELECT TMP_TB.*, ROWNUM ROW_ID FROM (\n");
            // pageSql.append(buildOrderBySql(sql, params.getOrderBy()));
            // if (params.hasPagenation()) {
            // Integer beginRow = params.getBeginRowNo();
            // pageSql.append("\n) AS TMP_TB WHERE ROWNUM <= ").append(beginRow + params.getPageSize())
            // .append(") WHERE ROW_ID > ").append(beginRow);
            // }
        }

        return pageSql.toString();
    }

    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
     * 
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value)
                                    .getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName
                                + " of statement " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof RoutingStatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
