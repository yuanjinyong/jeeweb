/**
 * 
 */
package com.jeeweb.platform.schema.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jeeweb.framework.business.mapper.SqlMapper;

/**
 * MyBatis执行sql工具
 * 
 * @author 袁进勇
 *
 */
@Component
public class InformationSchemaMapper extends SqlMapper {
    @Autowired
    @Qualifier("schemabSqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (sqlSessionFactory == null) {
            throw new Exception("请先定义Bean schemabSqlSessionFactory！");
        }
    }

    @Override
    protected SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
