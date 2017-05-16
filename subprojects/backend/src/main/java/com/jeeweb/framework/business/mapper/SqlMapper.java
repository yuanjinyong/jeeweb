/**
 * 
 */
package com.jeeweb.framework.business.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;

import com.jeeweb.framework.core.model.ParameterMap;

/**
 * MyBatis执行sql工具
 * 
 * @author 袁进勇
 *
 */
public abstract class SqlMapper implements InitializingBean {

    protected abstract SqlSessionFactory getSqlSessionFactory();

    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param sql
     *            执行的sql
     * @return
     */
    public Map<String, Object> selectOne(String sql) {
        List<Map<String, Object>> list = selectListPage(sql);
        return getOne(list);
    }

    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param sql
     *            执行的sql
     * @param parameter
     *            参数
     * @return
     */
    public Map<String, Object> selectOne(String sql, ParameterMap parameter) {
        return getOne(selectListPage(sql, parameter));
    }

    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param sql
     *            执行的sql
     * @param resultType
     *            返回的结果类型
     * @param <T>
     *            泛型类型
     * @return
     */
    public <T> T selectOne(String sql, Class<T> resultType) {
        return getOne(selectListPage(sql, resultType));
    }

    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param sql
     *            执行的sql
     * @param parameter
     *            参数
     * @param resultType
     *            返回的结果类型
     * @param <T>
     *            泛型类型
     * @return
     */
    public <T> T selectOne(String sql, ParameterMap parameter, Class<T> resultType) {
        return getOne(selectListPage(sql, parameter, resultType));
    }

    /**
     * 查询返回List<Map<String, Object>>
     *
     * @param sql
     *            执行的sql
     * @return
     */
    public List<Map<String, Object>> selectListPage(String sql) {
        return selectListPage(sql, (ParameterMap) null);
    }

    /**
     * 查询返回List<Map<String, Object>>
     *
     * @param sql
     *            执行的sql
     * @param parameter
     *            参数
     * @return
     */
    public List<Map<String, Object>> selectListPage(String sql, ParameterMap parameter) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.SELECT, sqlSessionFactory.getConfiguration(), sql, parameter, Map.class,
                ".ListPage");
        if (parameter == null) {
            return sqlSessionFactory.openSession().selectList(id);
        } else {
            return sqlSessionFactory.openSession().selectList(id, parameter);
        }
    }

    /**
     * 查询返回指定的结果类型
     *
     * @param sql
     *            执行的sql
     * @param resultType
     *            返回的结果类型
     * @param <T>
     *            泛型类型
     * @return
     */
    public <T> List<T> selectListPage(String sql, Class<T> resultType) {
        return selectListPage(sql, null, resultType);
    }

    /**
     * 查询返回指定的结果类型
     *
     * @param sql
     *            执行的sql
     * @param parameter
     *            参数
     * @param resultType
     *            返回的结果类型
     * @param <T>
     *            泛型类型
     * @return
     */
    public <T> List<T> selectListPage(String sql, Object parameter, Class<T> resultType) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.SELECT, sqlSessionFactory.getConfiguration(), sql, parameter, resultType,
                ".ListPage");
        if (parameter == null) {
            return sqlSessionFactory.openSession().selectList(id);
        } else {
            return sqlSessionFactory.openSession().selectList(id, parameter);
        }
    }

    /**
     * 插入数据
     *
     * @param sql
     *            执行的sql
     * @return
     */
    public int insert(String sql) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.INSERT, sqlSessionFactory.getConfiguration(), sql, null, int.class);
        return sqlSessionFactory.openSession().insert(id);
    }

    /**
     * 插入数据
     *
     * @param sql
     *            执行的sql
     * @param parameter
     *            参数
     * @return
     */
    public int insert(String sql, Object parameter) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.INSERT, sqlSessionFactory.getConfiguration(), sql, parameter, int.class);
        return sqlSessionFactory.openSession().insert(id, parameter);
    }

    /**
     * 更新数据
     *
     * @param sql
     *            执行的sql
     * @return
     */
    public int update(String sql) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.UPDATE, sqlSessionFactory.getConfiguration(), sql, null, int.class);
        return sqlSessionFactory.openSession().update(id);
    }

    /**
     * 更新数据
     *
     * @param sql
     *            执行的sql
     * @param parameter
     *            参数
     * @return
     */
    public int update(String sql, Object parameter) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.UPDATE, sqlSessionFactory.getConfiguration(), sql, parameter, int.class);
        return sqlSessionFactory.openSession().update(id, parameter);
    }

    /**
     * 删除数据
     *
     * @param sql
     *            执行的sql
     * @return
     */
    public int delete(String sql) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.DELETE, sqlSessionFactory.getConfiguration(), sql, null, int.class);
        return sqlSessionFactory.openSession().delete(id);
    }

    /**
     * 删除数据
     *
     * @param sql
     *            执行的sql
     * @param value
     *            参数
     * @return
     */
    public int delete(String sql, Object parameter) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.DELETE, sqlSessionFactory.getConfiguration(), sql, parameter, int.class);
        return sqlSessionFactory.openSession().delete(id, parameter);
    }

    private String getSqlId(SqlCommandType sqlCommandType, final Configuration configuration, String sql,
            Object parameter, final Class<?> resultType) {
        return getSqlId(sqlCommandType, configuration, sql, parameter, resultType, "");
    }

    private String getSqlId(SqlCommandType sqlCommandType, final Configuration configuration, String sql,
            Object parameter, final Class<?> resultType, String sufix) {
        String hashCode = resultType + sql + (parameter == null ? "null" : parameter.getClass());
        String statementId = new StringBuilder(sqlCommandType.toString()).append('.').append(hashCode.hashCode())
                .append(sufix).toString();
        if (configuration.hasStatement(statementId, false)) {
            return statementId;
        }

        SqlSource sqlSource = parameter == null ? new StaticSqlSource(configuration, sql)
                : configuration.getDefaultScriptingLanuageInstance().createSqlSource(configuration, sql,
                        parameter.getClass());
        ResultMap defaultResultMap = new ResultMap.Builder(configuration, "defaultResultMap", resultType,
                new ArrayList<ResultMapping>(0)).build();
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        resultMaps.add(defaultResultMap);
        MappedStatement mappedStatement = new MappedStatement.Builder(configuration, statementId, sqlSource,
                sqlCommandType).resultMaps(resultMaps).build();
        configuration.addMappedStatement(mappedStatement);
        return statementId;
    }

    /**
     * 获取List中最多只有一个的数据
     *
     * @param list
     *            List结果
     * @param <T>
     *            泛型类型
     * @return
     */
    private <T> T getOne(List<T> list) {
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new TooManyResultsException(
                    "Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }
}
