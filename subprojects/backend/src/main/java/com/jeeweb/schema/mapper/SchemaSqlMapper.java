/**
 * 
 */
package com.jeeweb.schema.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jeeweb.framework.business.mapper.AbstractSqlMapper;

/**
 * @author 袁进勇
 *
 */
@Component
public class SchemaSqlMapper extends AbstractSqlMapper {
    @Autowired
    @Qualifier("schemaSqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sqlSessionFactory == null) {
            throw new Exception("请先定义Bean schemaSqlSessionFactory！");
        }
    }

    @Override
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
