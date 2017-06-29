package com.jeeweb.framework.core.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

public abstract class SuperService implements InitializingBean {
    @Value("${spring.datasource.default.url:#{null}}")
    protected String defaultUrl;

    protected String defaultDatabaseName;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 暂时写死成只解析mysql的连接串，后续如果需要解析其他数据库的再来改造成正则表达式解析
        // jdbc:mysql://127.0.0.1:3306/zhuku_company?useSSL=false&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true
        String[] tempStrs = defaultUrl.split("\\?");
        String[] jdbcProperties = tempStrs[0].replace("//", "").replace('/', ':').split(":");
        defaultDatabaseName = jdbcProperties[jdbcProperties.length - 1];
    }
}
