/**
 * 
 */
package com.jeeweb.framework.business.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * MyBatis执行sql工具
 * 
 * @author 袁进勇
 *
 */
@Component
public class JeewebMapper extends SqlMapper {
    @Autowired
    @Qualifier("jeewebSqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sqlSessionFactory == null) {
            throw new Exception("请先定义Bean jeewebSqlSessionFactory！");
        }
    }

    @Override
    protected SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
