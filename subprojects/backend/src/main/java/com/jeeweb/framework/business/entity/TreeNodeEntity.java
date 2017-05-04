/**
 * 
 */
package com.jeeweb.framework.business.entity;

import java.util.List;

/**
 * @author 袁进勇
 *
 */
public abstract class TreeNodeEntity<P, E extends TreeNodeEntity<P, E>> extends BaseEntity<P> {
    private static final long serialVersionUID = 531613364966668278L;

    private P f_parent_id; // 父级ID
    private String f_parent_name; // 父级名称
    private String f_parent_path; // 树形结构的路径，以“/”开头、分隔和结尾。
    private Integer f_order; // 同一个父级下的排序
    private List<E> children; // 子列表

    public P getF_parent_id() {
        return f_parent_id;
    }

    public void setF_parent_id(P f_parent_id) {
        this.f_parent_id = f_parent_id;
    }

    public String getF_parent_name() {
        return f_parent_name;
    }

    public void setF_parent_name(String f_parent_name) {
        this.f_parent_name = f_parent_name;
    }

    public String getF_parent_path() {
        return f_parent_path;
    }

    public void setF_parent_path(String f_parent_path) {
        this.f_parent_path = f_parent_path;
    }

    public String getF_full_path() {
        return getF_parent_path() + getF_id() + "/";
    }

    public Integer getF_order() {
        return f_order;
    }

    public void setF_order(Integer f_order) {
        this.f_order = f_order;
    }

    public List<E> getChildren() {
        return children;
    }

    public void setChildren(List<E> children) {
        this.children = children;
    }
}
