/**
 * 
 */
package com.jeeweb.framework.core.model;

import java.util.List;

/**
 * @author 袁进勇
 *
 */
public class Page<T> {
    private Integer totalCount;
    private Integer pageSize;
    private Integer pageNo;
    private String orderBy;
    private List<T> items;

    public Page() {
        this(10, 0);
    }

    public Page(Sort sort) {
        this(10, 0, sort);
    }

    public Page(List<T> items) {
        this(items, null);
    }

    public Page(List<T> items, String orderBy) {
        this.pageNo = 0;
        this.pageSize = 0;
        this.totalCount = items.size();
        this.items = items;
        this.orderBy = orderBy;
    }

    public Page(Integer pageSize, Integer currentPage) {
        this(pageSize, currentPage, new Sort());
    }

    public Page(Integer pageSize, Integer currentPage, Sort sort) {
        this.pageSize = pageSize;
        this.pageNo = currentPage;
        this.orderBy = sort.toString();
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalSize) {
        this.totalCount = totalSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer currentPage) {
        this.pageNo = currentPage;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
