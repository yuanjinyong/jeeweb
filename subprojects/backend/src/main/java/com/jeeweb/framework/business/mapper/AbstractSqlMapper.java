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

import com.jeeweb.framework.core.model.ParameterMap;

/**
 * @author 袁进勇
 *
 */
public abstract class AbstractSqlMapper implements SqlMapper {
    @Override
    public Map<String, Object> selectOne(String sql) {
        List<Map<String, Object>> list = selectListPage(sql);
        return getOne(list);
    }

    @Override
    public Map<String, Object> selectOne(String sql, ParameterMap parameter) {
        return getOne(selectListPage(sql, parameter));
    }

    @Override
    public <T> T selectOne(String sql, Class<T> resultType) {
        return getOne(selectListPage(sql, resultType));
    }

    @Override
    public <T> T selectOne(String sql, ParameterMap parameter, Class<T> resultType) {
        return getOne(selectListPage(sql, parameter, resultType));
    }

    @Override
    public List<Map<String, Object>> selectListPage(String sql) {
        return selectListPage(sql, (ParameterMap) null);
    }

    @Override
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

    @Override
    public <T> List<T> selectListPage(String sql, Class<T> resultType) {
        return selectListPage(sql, null, resultType);
    }

    @Override
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

    @Override
    public int insert(String sql) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.INSERT, sqlSessionFactory.getConfiguration(), sql, null, int.class);
        return sqlSessionFactory.openSession().insert(id);
    }

    @Override
    public int insert(String sql, Object parameter) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.INSERT, sqlSessionFactory.getConfiguration(), sql, parameter, int.class);
        return sqlSessionFactory.openSession().insert(id, parameter);
    }

    @Override
    public int update(String sql) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.UPDATE, sqlSessionFactory.getConfiguration(), sql, null, int.class);
        return sqlSessionFactory.openSession().update(id);
    }

    @Override
    public int update(String sql, Object parameter) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.UPDATE, sqlSessionFactory.getConfiguration(), sql, parameter, int.class);
        return sqlSessionFactory.openSession().update(id, parameter);
    }

    @Override
    public int delete(String sql) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        String id = getSqlId(SqlCommandType.DELETE, sqlSessionFactory.getConfiguration(), sql, null, int.class);
        return sqlSessionFactory.openSession().delete(id);
    }

    @Override
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
        List<ResultMap> resultMaps = new ArrayList<>();
        resultMaps.add(defaultResultMap);
        MappedStatement mappedStatement = new MappedStatement.Builder(configuration, statementId, sqlSource,
                sqlCommandType).resultMaps(resultMaps).build();
        configuration.addMappedStatement(mappedStatement);
        return statementId;
    }

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
