/**
 * 
 */
package com.jeeweb.platform.schema.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SqlSessionFactory informationSchemaSqlSessionFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (informationSchemaSqlSessionFactory == null) {
            throw new Exception("请先定义Bean informationSchemaSqlSessionFactory！");
        }
    }

    @Override
    protected SqlSessionFactory getSqlSessionFactory() {
        return informationSchemaSqlSessionFactory;
    }
}
