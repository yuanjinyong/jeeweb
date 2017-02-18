/**
 * 
 */
package com.jeeweb.framework.core.configure;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 袁进勇
 *
 */
@ConfigurationProperties(prefix = MybatisPageProperties.PREFIX)
public class MybatisPageProperties {
    public static final String PREFIX = "mybatis.page";

    private static final Logger LOG = LoggerFactory.getLogger(MybatisPageProperties.class);
    private static final String DEFAULT_DIALECT = "mysql";
    private static final String DEFAULT_SQLID_REGEX = ".*ListPage$";

    /**
     * 数据库方言（类型）
     */
    private String dialect;

    /**
     * 应用分页插件的SQL语句的ID（正则表达式匹配）
     */
    private String sqlIdRegex;

    @PostConstruct
    public void init() {
        if (dialect == null) {
            dialect = DEFAULT_DIALECT;
            LOG.warn("dialect属性未配置，使用默认值“" + DEFAULT_DIALECT + "”。");
        }
        if (sqlIdRegex == null) {
            sqlIdRegex = DEFAULT_SQLID_REGEX;
            LOG.warn("sqlIdRegex属性未配置，使用默认值“" + DEFAULT_SQLID_REGEX + "”。");
        }
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getSqlIdRegex() {
        return sqlIdRegex;
    }

    public void setSqlIdRegex(String sqlIdRegex) {
        this.sqlIdRegex = sqlIdRegex;
    }
}
