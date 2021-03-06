/**
 * 
 */
package com.jeeweb.diy.configure;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ObjectUtils;

/**
 * @author 袁进勇
 *
 */
@Configuration
@MapperScan(basePackages = { "com.jeeweb.diy.**.mapper.**" }, sqlSessionTemplateRef = "diySqlSessionTemplate")
public class DiyMybatisConfiguration {
    @Autowired(required = false)
    private Interceptor[] interceptors;
    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    @Value("${mybatis.mapper.diy.typeAliasesPackage:com.jeeweb.diy.**.entity}")
    private String typeAliasesPackage;
    @Value("${mybatis.mapper.diy.mapperLocations:classpath:/com/jeeweb/diy/**/mapper/*Mapper.xml}")
    private String mapperLocations;

    @Bean(name = "diySqlSessionFactory")
    public SqlSessionFactory diySqlSessionFactory(@Qualifier("diyDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            factory.setPlugins(this.interceptors);
        }
        if (this.databaseIdProvider != null) {
            factory.setDatabaseIdProvider(this.databaseIdProvider);
        }
        factory.setTypeAliasesPackage(typeAliasesPackage);
        factory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return factory.getObject();
    }

    @Bean(name = "diySqlSessionTemplate")
    public SqlSessionTemplate diySqlSessionTemplate(
            @Qualifier("diySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
