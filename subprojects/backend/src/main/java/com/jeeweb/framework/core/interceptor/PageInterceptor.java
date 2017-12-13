package com.jeeweb.framework.core.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
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
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class PageInterceptor implements Interceptor {
    // private static final Logger LOG = LoggerFactory.getLogger(PageInterceptor.class);
    private static final String DB_PRODUCT_NAME_MYSQL = "MySQL";
    private static final String DB_PRODUCT_NAME_SQLSERVER = "Microsoft SQL Server";

    @Value("${mybatis.page.sqlIdRegex:.*ListPage$}")
    private String sqlIdRegex;
    private String databaseProductName;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (!(target instanceof RoutingStatementHandler)) {
            return invocation.proceed();
        }

        MetaObject metaObject = MetaUtil.getMetaObject(invocation.getTarget());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        mappedStatement.getStatementLog()
                .trace(new StringBuffer("\n执行SQL：").append(mappedStatement.getId()).append("\n对应XML：")
                        .append(mappedStatement.getResource()).append("\n原始的SQL如下：\n    ").append(boundSql.getSql())
                        .toString());
        try {
            doPageIntercept(metaObject, invocation.getArgs());
            return invocation.proceed();
        } catch (Exception e) {
            if (e instanceof SQLException) {
                mappedStatement.getStatementLog()
                        .error(new StringBuffer("\n执行SQL：").append(mappedStatement.getId()).append("\n对应XML：")
                                .append(mappedStatement.getResource()).append("\n原始的SQL如下：\n    ")
                                .append(boundSql.getSql()).toString());
            }

            throw e;
        }
    }

    private void doPageIntercept(MetaObject metaObject, Object[] args) throws SQLException, Exception {
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        // 如果没有传入任何参数或者入参不是统一的MapEntity，则不进行翻页数据的处理
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject != null && parameterObject instanceof ParamsMap) {
            ParamsMap params = (ParamsMap) parameterObject;
            // 要传入了pageSize或orderBy且SqlId以“ListPage”结尾，才进行翻页数据的处理
            if (params.hasPagenation() || !HelpUtil.isEmpty(params.getOrderBy())) {
                if (mappedStatement.getId().matches(sqlIdRegex)) {
                    Connection connection = (Connection) args[0];
                    databaseProductName = connection.getMetaData().getDatabaseProductName();
                    // 如果入参中已经传入了totalCount，则不需要再去查询出总数了
                    if (params.hasPagenation() && (params.getTotalCount() == null || params.getTotalCount() == 0)) {
                        processTotalCount(connection, mappedStatement, boundSql);
                    }

                    String pageSql = generatePageSql(boundSql);
                    metaObject.setValue("delegate.boundSql.sql", pageSql);
                    mappedStatement.getStatementLog()
                            .trace(new StringBuffer("\n重写分页和排序后的SQL如下：\n").append(pageSql).toString());
                }
            }
        }
    }

    private void processTotalCount(Connection connection, MappedStatement mappedStatement, BoundSql boundSql)
            throws Exception, SQLException {
        PreparedStatement countStmt = null;
        ResultSet resultSet = null;
        try {
            ParamsMap params = (ParamsMap) boundSql.getParameterObject();
            String countSql = generatCountSql(mappedStatement, boundSql);
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), params);

            setParameters(countStmt, mappedStatement, countBoundSql, params);

            resultSet = countStmt.executeQuery();
            if (resultSet.next()) {
                params.setTotalCount(resultSet.getInt(1));
                mappedStatement.getStatementLog().trace(new StringBuffer("\n重写分页参数里的总记录数：totalCount=")
                        .append(params.getTotalCount()).append("\n").append(countSql).toString());
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
            return "SELECT COUNT(1) TOTAL_COUNT FROM (" + boundSql.getSql() + ") temp ";
        }
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

    /**
     * 根据数据库方言，生成特定的分页sql
     * 
     * @param boundSql
     * @return
     */
    private String generatePageSql(BoundSql boundSql) {
        String sql = boundSql.getSql();
        ParamsMap params = (ParamsMap) boundSql.getParameterObject();

        StringBuffer pageSql = new StringBuffer();
        if (DB_PRODUCT_NAME_MYSQL.equals(databaseProductName)) {
            pageSql.append(HelpUtil.isEmpty(params.getOrderBy()) ? sql
                    : ("SELECT * FROM (\n" + sql + "\n) TEMP_TB\nORDER BY " + params.getOrderBy()));
            if (params.hasPagenation()) {
                pageSql.append(" \nLIMIT " + params.getBeginRowNo() + "," + params.getPageSize());
            }
        } else if (DB_PRODUCT_NAME_SQLSERVER.equals(databaseProductName)) {
            pageSql.append("SELECT * FROM (\n");
            pageSql.append("    SELECT *, ROW_NUMBER() OVER(order by ")
                    .append(HelpUtil.isEmpty(params.getOrderBy()) ? "(select 0)" : params.getOrderBy())
                    .append(") RowNumber FROM (\n");
            pageSql.append(sql);
            pageSql.append("\n    ) TMP_TB\n");
            pageSql.append(") TEMP_TB ");
            if (params.hasPagenation()) {
                Integer beginRow = params.getBeginRowNo();
                pageSql.append(" WHERE TEMP_TB.RowNumber > ").append(beginRow).append(" AND TEMP_TB.RowNumber <= ")
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

    // private String buildOrderBySql(String sql, String orderBy) {
    // if (HelpUtil.isEmpty(orderBy)) {
    // return sql;
    // }
    //
    // return "SELECT * FROM (\n" + sql + "\n) temp\nORDER BY " + orderBy;
    // }

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
