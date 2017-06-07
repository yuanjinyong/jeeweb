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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.jeeweb.framework.core.configure.MybatisPageProperties;
import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.utils.HelpUtil;
import com.jeeweb.framework.core.utils.MetaUtil;

/**
 * 通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，重写sql语句实现物理分页。
 * 
 * @author 袁进勇
 */
@Component
@EnableConfigurationProperties(MybatisPageProperties.class)
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class PageInterceptor implements Interceptor {
    private static final Logger LOG = LoggerFactory.getLogger(PageInterceptor.class);

    @Autowired
    private MybatisPageProperties properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof RoutingStatementHandler) {
            MetaObject metaObject = MetaUtil.getMetaObject(invocation.getTarget());
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
            LOG.debug("\n执行SQL：{}\n对应XML：{}\n原始的SQL如下：\n    {}", mappedStatement.getId(), mappedStatement.getResource(),
                    boundSql.getSql());

            doPageIntercept(metaObject, invocation.getArgs());
        }

        return invocation.proceed();
    }

    private void doPageIntercept(MetaObject metaObject, Object[] args) throws SQLException, Exception {
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        // 如果没有传入任何参数或者入参不是统一的MapEntity，则不进行翻页数据的处理
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject != null && parameterObject instanceof ParameterMap) {
            ParameterMap params = (ParameterMap) parameterObject;
            // 要传入了pageSize或orderBy且SqlId以“ListPage”结尾，才进行翻页数据的处理
            if (params.hasPagenation() || !HelpUtil.isEmpty(params.getOrderBy())) {
                if (mappedStatement.getId().matches(properties.getSqlIdRegex())) {
                    processTotalCount((Connection) args[0], mappedStatement, boundSql);

                    String pageSql = generatePageSql(boundSql.getSql(), params);
                    metaObject.setValue("delegate.boundSql.sql", pageSql);

                    LOG.info("\n重写分页和排序后的SQL如下：\n{}", pageSql);
                }
            }
        }
    }

    private void processTotalCount(Connection connection, MappedStatement mappedStatement, BoundSql boundSql)
            throws Exception, SQLException {
        ParameterMap params = (ParameterMap) boundSql.getParameterObject();
        // 如果入参中已经传入了totalCount，则不需要再去查询出总数了
        if (params.getPageSize() == null || (params.getTotalCount() != null && params.getTotalCount() > 0)) {
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
                LOG.info("\n重写分页参数里的总页数：totalCount=" + params.getTotalCount() + "\n" + countSql);
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
        String countSqlId = mappedStatement.getId().replaceAll(properties.getSqlIdRegex(), "ListCount");
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
     * @param sql
     * @param page
     * @return
     */
    private String generatePageSql(String sql, ParameterMap params) {
        StringBuffer pageSql = new StringBuffer();
        if ("mysql".equals(properties.getDialect())) {
            pageSql.append(sql);
            String orderBy = params.getOrderBy();
            if (!HelpUtil.isEmpty(orderBy)) {
                pageSql.append(" \nORDER BY ").append(orderBy);
            }
            if (params.hasPagenation()) {
                pageSql.append(" \nLIMIT " + params.getBeginRowNo() + "," + params.getPageSize());
            }
        } else if ("oracle".equals(properties.getDialect())) {
            pageSql.append("SELECT * FROM (SELECT TMP_TB.*, ROWNUM ROW_ID FROM (\n");
            pageSql.append(sql);
            String orderBy = params.getOrderBy();
            if (!HelpUtil.isEmpty(orderBy)) {
                pageSql.append(" \nORDER BY ").append(orderBy);
            }
            if (params.hasPagenation()) {
                Integer beginRow = params.getBeginRowNo();
                pageSql.append("\n) AS TMP_TB WHERE ROWNUM <= ").append(beginRow + params.getPageSize())
                        .append(") WHERE ROW_ID > ").append(beginRow);
            }
        }

        return pageSql.toString();
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
