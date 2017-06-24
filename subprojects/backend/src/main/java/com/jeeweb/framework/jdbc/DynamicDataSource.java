/**
 * 
 */
package com.jeeweb.framework.jdbc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.atomikos.jdbc.AtomikosDataSourceBean;

/**
 * @author 袁进勇
 *
 */
public abstract class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger LOG = LoggerFactory.getLogger(DynamicDataSource.class);

    private AtomikosDataSourceBean defaultDataSource;
    private Map<Object, Object> dataSources;

    @Override
    public void afterPropertiesSet() {
        defaultDataSource = getDefaultDataSource();
        dataSources = new HashMap<>();

        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(dataSources);
        super.afterPropertiesSet();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
     */
    @Override
    protected Object determineCurrentLookupKey() {
        Integer dataSourceId = DataSourceHolder.getDataSourceId();
        if (dataSourceId == null) {
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

    protected abstract AtomikosDataSourceBean createDataSource(IDataSourceProperties dataSourceProperties)
            throws SQLException;
}
