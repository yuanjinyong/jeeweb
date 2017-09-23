/**
 * 
 */
package com.jeeweb.framework.jdbc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.atomikos.jdbc.AtomikosDataSourceBean;

/**
 * @author 袁进勇
 *
 */
public abstract class DynamicDataSource extends AbstractRoutingDataSource implements DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicDataSource.class);

    // private AtomikosDataSourceBean defaultDataSource;
    private Map<Object, Object> dataSources = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        // defaultDataSource = getDefaultDataSource();
        // super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(dataSources);
        super.afterPropertiesSet();
    }

    @Override
    public void destroy() {
        LOG.info("释放动态数据源连接池。");
        for (Object value : dataSources.values()) {
            AtomikosDataSourceBean dataSource = (AtomikosDataSourceBean) value;
            try {
                dataSource.close();
            } catch (Exception e) {
                LOG.error("释放数据源" + dataSource.getUniqueResourceName() + "连接失败！", e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
     */
    @Override
    protected Object determineCurrentLookupKey() {
        Integer dataSourceId = DataSourceHolder.getDataSourceId();
        if (dataSourceId == null) {
            AtomikosDataSourceBean defaultDataSource = getDefaultDataSource();
            LOG.info("使用默认数据源{}。", defaultDataSource.getUniqueResourceName());
            return defaultDataSource.getUniqueResourceName();
        }

        IDataSourceProperties dataSourceProperties = getDataSourceProperties(dataSourceId);
        String dataSourceName = dataSourceProperties.getDataSourceName();
        if (!dataSources.containsKey(dataSourceName)) {
            try {
                dataSources.put(dataSourceName, createDataSource(dataSourceProperties));
                super.setTargetDataSources(dataSources);
                super.afterPropertiesSet();
            } catch (SQLException e) {
                throw new RuntimeException("初始化数据源" + dataSourceName + "失败！", e);
            }
        }

        LOG.info("使用动态数据源{}。", dataSourceName);
        return dataSourceName;
    }

    protected abstract IDataSourceProperties getDataSourceProperties(Integer dataSourceId);

    protected abstract AtomikosDataSourceBean getDefaultDataSource();

    public abstract AtomikosDataSourceBean createDataSource(IDataSourceProperties dataSourceProperties)
            throws SQLException;
}
