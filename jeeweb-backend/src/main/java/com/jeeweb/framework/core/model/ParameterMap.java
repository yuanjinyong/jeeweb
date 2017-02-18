/**
 * 
 */
package com.jeeweb.framework.core.model;

import java.util.List;
import java.util.Map;

/**
 * @author 袁进勇
 *
 */
public class ParameterMap extends RowMap {
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    public static final Integer MAX_PAGE_SIZE = 10000;
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_NO = "pageNo";
    public static final String PAGE_ITEMS = "pageItems";
    public static final String TOTAL_COUNT = "totalCount";
    public static final String ORDER_BY = "orderBy";

    private static final long serialVersionUID = 1L;

    public ParameterMap() {
        super();
    }

    public ParameterMap(final String key, Object value) {
        super();
        put(key, value);
    }

    public ParameterMap(Map<String, Object> map) {
        super();
        this.putAll(map);
    }

    public ParameterMap setPageSize(Integer pageSize) {
        return this.put(PAGE_SIZE, pageSize);
    }

    public ParameterMap setPageSizeWithDefault() {
        return this.put(PAGE_SIZE, DEFAULT_PAGE_SIZE);
    }

    public ParameterMap setPageSizeWithMax() {
        return this.put(PAGE_SIZE, MAX_PAGE_SIZE);
    }

    public Integer getPageSize() {
        return this.getInteger(PAGE_SIZE, 0);
    }

    public ParameterMap setPageNo(Integer pageNo) {
        return this.put(PAGE_NO, pageNo);
    }

    public Integer getPageNo() {
        Integer pageNo = this.getInteger(PAGE_NO, null);
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
            this.put(PAGE_NO, pageNo);
        }
        return pageNo;
    }

    public <E> ParameterMap setPageItems(List<E> pageItems) {
        return this.put(PAGE_ITEMS, pageItems);
    }

    public List<?> getPageItems() {
        return (List<?>) this.get(PAGE_ITEMS);
    }

    public Integer getBeginRowNo() {
        return (this.getPageNo() - 1) * this.getPageSize();
    }

    public ParameterMap setTotalCount(Integer totalCount) {
        return this.put(TOTAL_COUNT, totalCount);
    }

    public Integer getTotalCount() {
        Integer totalCount = this.getInteger(TOTAL_COUNT, null);
        if (totalCount == null) {
            totalCount = 0;
            this.put(TOTAL_COUNT, totalCount);
        }
        return totalCount;
    }

    public ParameterMap setOrderBy(String orderBy) {
        return this.put(ORDER_BY, orderBy);
    }

    public String getOrderBy() {
        return this.getString(ORDER_BY, null);
    }

    public boolean hasPagenation() {
        return this.getInteger(PAGE_SIZE, 0) > 0;
    }

    public <E> Page<E> page(List<E> pageItems) {
        Page<E> page = new Page<>(this.getPageSize(), this.getPageNo());
        page.setTotalCount(this.hasPagenation() ? this.getTotalCount() : pageItems.size());
        page.setOrderBy(this.getOrderBy());
        page.setItems(pageItems);
        return page;
    }

    public ParameterMap disableTenantIsolate() {
        return this.put("disableTenantIsolate", true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public ParameterMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
