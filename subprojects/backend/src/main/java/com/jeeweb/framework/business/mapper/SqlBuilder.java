/**
 * 
 */
package com.jeeweb.framework.business.mapper;

import com.jeeweb.framework.core.model.ParameterMap;
import com.jeeweb.framework.core.utils.HelpUtil;

/**
 * @author 袁进勇
 *
 */
public class SqlBuilder {
    private ParameterMap params;
    private StringBuffer sql;

    public SqlBuilder() {
        this(null, new StringBuffer());
    }

    public SqlBuilder(ParameterMap params) {
        this(params, new StringBuffer());
    }

    public SqlBuilder(String sql) {
        this(null, new StringBuffer(sql));
    }

    public SqlBuilder(ParameterMap params, String sql) {
        this(params, new StringBuffer(sql));
    }

    public SqlBuilder(ParameterMap params, StringBuffer sql) {
        this.params = params;
        this.sql = sql;
    }

    public SqlBuilder and(String paramKey) {
        return appendWhere("AND", paramKey);
    }

    public SqlBuilder or(String paramKey) {
        return appendWhere("OR", paramKey);
    }

    public SqlBuilder appendWhere(String type, String paramId) {
        if (params == null) {
            sql.append("    ").append(type).append(' ').append(paramId).append(" \n");
            return this;
        }

        if (HelpUtil.isEmpty(params.getString(paramId))) {
            return this;
        }

        sql.append("    ").append(type).append(' ');
        String column = paramId;
        if (paramId.endsWith("_like")) {
            column = paramId.substring(0, paramId.indexOf("_like"));
            sql.append(column).append(" LIKE CONCAT('%', #{").append(paramId).append("}, '%') \n");
        } else if (paramId.endsWith("_notIn")) {
            column = paramId.substring(0, paramId.indexOf("_notIn"));
            sql.append(column).append(" NOT IN (${").append(paramId).append("}) \n");
        } else {
            sql.append(column).append(" = #{").append(paramId).append("} \n");
        }

        return this;
    }

    public SqlBuilder orderBy() {
        return orderBy(params.getOrderBy());
    }

    public SqlBuilder orderBy(String orderBy) {
        if (!HelpUtil.isEmpty(orderBy)) {
            sql.append("ORDER BY ").append(orderBy).append(" \n");
        }
        return this;
    }

    public String sql() {
        return sql.toString();
    }

    @Override
    public String toString() {
        return sql.toString();
    }
}
