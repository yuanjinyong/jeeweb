/**
 * 
 */
package com.jeeweb.framework.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.InitializingBean;

import com.jeeweb.framework.core.model.ParameterMap;

/**
 * MyBatis执行sql语句的工具
 * 
 * @author 袁进勇
 *
 */
public interface SqlMapper extends InitializingBean {

    SqlSessionFactory getSqlSessionFactory();

    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param sql
     *            执行的sql
     * @return
     */
    Map<String, Object> selectOne(String sql);

    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param sql
     *            执行的sql
     * @param parameter
     *            参数
     * @return
     */
    Map<String, Object> selectOne(String sql, ParameterMap parameter);

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
    <T> T selectOne(String sql, Class<T> resultType);

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
    <T> T selectOne(String sql, ParameterMap parameter, Class<T> resultType);

    /**
     * 查询返回List<Map<String, Object>>
     *
     * @param sql
     *            执行的sql
     * @return
     */
    List<Map<String, Object>> selectListPage(String sql);

    /**
     * 查询返回List<Map<String, Object>>
     *
     * @param sql
     *            执行的sql
     * @param parameter
     *            参数
     * @return
     */
    List<Map<String, Object>> selectListPage(String sql, ParameterMap parameter);

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
    <T> List<T> selectListPage(String sql, Class<T> resultType);

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
    <T> List<T> selectListPage(String sql, Object parameter, Class<T> resultType);

    /**
     * 插入数据
     *
     * @param sql
     *            执行的sql
     * @return
     */
    int insert(String sql);

    /**
     * 插入数据
     *
     * @param sql
     *            执行的sql
     * @param parameter
     *            参数
     * @return
     */
    int insert(String sql, Object parameter);

    /**
     * 更新数据
     *
     * @param sql
     *            执行的sql
     * @return
     */
    int update(String sql);

    /**
     * 更新数据
     *
     * @param sql
     *            执行的sql
     * @param parameter
     *            参数
     * @return
     */
    int update(String sql, Object parameter);

    /**
     * 删除数据
     *
     * @param sql
     *            执行的sql
     * @return
     */
    int delete(String sql);

    /**
     * 删除数据
     *
     * @param sql
     *            执行的sql
     * @param value
     *            参数
     * @return
     */
    int delete(String sql, Object parameter);
}
