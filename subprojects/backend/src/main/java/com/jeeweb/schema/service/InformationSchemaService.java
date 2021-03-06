package com.jeeweb.schema.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeweb.framework.business.mapper.SqlBuilder;
import com.jeeweb.framework.business.mapper.SqlMapper;
import com.jeeweb.framework.core.model.ParamsMap;

@Service
@Transactional(readOnly = true) // 这个Service只读取数据库元数据，不做任何修改的操作
public class InformationSchemaService {
    @Autowired
    @Qualifier("schemaSqlMapper")
    private SqlMapper sqlMapper;

    public List<Map<String, Object>> selectSchemataListPage(ParamsMap params) {
        SqlBuilder sql = new SqlBuilder(params, "SELECT * FROM SCHEMATA WHERE 1=1 \n");
        sql.and("SCHEMA_NAME_like").and("SCHEMA_NAME_notIn");

        return sqlMapper.selectListPage(sql.toString(), params);
    }

    public Map<String, Object> selectTable(String tableSchema, String tableName) {
        ParamsMap params = new ParamsMap("TABLE_SCHEMA", tableSchema).put("TABLE_NAME", tableName);
        SqlBuilder sql = new SqlBuilder(params, "SELECT * FROM TABLES WHERE 1=1 \n");
        sql.and("TABLE_SCHEMA").and("TABLE_NAME");

        Map<String, Object> table = sqlMapper.selectOne(sql.toString(), params);
        table.put("columnList", selectColumnListPage(params));
        return table;
    }

    public List<Map<String, Object>> selectTableListPage(ParamsMap params) {
        SqlBuilder sql = new SqlBuilder(params, "SELECT * FROM TABLES WHERE 1=1 \n");
        sql.and("TABLE_SCHEMA").and("TABLE_TYPE").and("TABLE_NAME_like").and("TABLE_COMMENT_like");

        return sqlMapper.selectListPage(sql.toString(), params);
    }

    public List<Map<String, Object>> selectColumnListPage(ParamsMap params) {
        SqlBuilder sql = new SqlBuilder(params, "SELECT * FROM COLUMNS WHERE 1=1 \n");
        sql.and("TABLE_SCHEMA").and("TABLE_NAME").and("TABLE_NAME_like").and("COLUMN_NAME_like")
                .and("TABLE_COMMENT_like");

        return sqlMapper.selectListPage(sql.toString(), params);
    }
}
