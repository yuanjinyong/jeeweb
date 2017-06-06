/**
 * 
 */
package com.jeeweb.framework.business.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author 袁进勇
 * 
 */
@Component
public class DefaultSqlMapper extends AbstractSqlMapper {
    @Autowired
    @Qualifier("defaultSqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sqlSessionFactory == null) {
            throw new Exception("请先定义Bean defaultSqlSessionFactory！");
        }
    }

    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
