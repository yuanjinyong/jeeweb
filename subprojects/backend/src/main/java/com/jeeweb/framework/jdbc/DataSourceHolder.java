package com.jeeweb.framework.jdbc;

/**
 * 多个登录用户可能需要同时切换数据源，所以这里需要写一个线程安全的ThreadLocal 用户切换数据源只要在程序中使用DataSourceHolder.setDataSourceId(1)即可完成数据源切换
 * 
 * @author 袁进勇
 * 
 */
public class DataSourceHolder {
    private static final ThreadLocal<Integer> contextHolder = new ThreadLocal<>();

    public static void setDataSourceId(int dataSourceId) {
        contextHolder.set(dataSourceId);
    }

    public static Integer getDataSourceId() {
        return contextHolder.get();
    }

    public static void clearDataSourceId() {
        contextHolder.remove();
    }

}
