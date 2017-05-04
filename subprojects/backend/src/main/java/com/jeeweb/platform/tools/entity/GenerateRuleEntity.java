package com.jeeweb.platform.tools.entity;

import java.util.List;

import com.jeeweb.framework.business.entity.BaseEntity;

public class GenerateRuleEntity extends BaseEntity<Integer> {
    private static final long serialVersionUID = -2759064061686001170L;

    private String f_code; // 名称
    private String f_name; // 描述
    private String f_menu_id; // 菜单ID
    private String f_menu_name; // 菜单名称
    private String f_menu_remark; // 菜单描述
    private String f_menu_parent_id; // 父级菜单
    private Integer f_menu_order; // 同一个父级菜单下的排序
    private String f_request_url; // 后台Rest API的URL
    private String f_view_path; // 前台jsp的路径
    private String f_package_name; // 所属模块的包名
    private String f_package_dir; // 所属模块的包路径

    private GenerateRuleTableEntity mainTable;
    private List<GenerateRuleTableEntity> tableList;

    public String getF_code() {
        return f_code;
    }

    public void setF_code(String f_name) {
        this.f_code = f_name;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_remark) {
        this.f_name = f_remark;
    }

    public String getF_menu_id() {
        return f_menu_id;
    }

    public void setF_menu_id(String f_menu_id) {
        this.f_menu_id = f_menu_id;
    }

    public String getF_menu_name() {
        return f_menu_name;
    }

    public void setF_menu_name(String f_menu_name) {
        this.f_menu_name = f_menu_name;
    }

    public String getF_menu_remark() {
        return f_menu_remark;
    }

    public void setF_menu_remark(String f_menu_remark) {
        this.f_menu_remark = f_menu_remark;
    }

    public String getF_menu_parent_id() {
        return f_menu_parent_id;
    }

    public void setF_menu_parent_id(String f_parent_menu_id) {
        this.f_menu_parent_id = f_parent_menu_id;
    }

    public Integer getF_menu_order() {
        return f_menu_order;
    }

    public void setF_menu_order(Integer f_menu_order) {
        this.f_menu_order = f_menu_order;
    }

    public String getF_request_url() {
        return f_request_url;
    }

    public void setF_request_url(String f_url) {
        this.f_request_url = f_url;
    }

    public String getF_view_path() {
        return f_view_path;
    }

    public void setF_view_path(String f_view_path) {
        this.f_view_path = f_view_path;
    }

    public String getF_package_name() {
        return f_package_name;
    }

    public void setF_package_name(String f_pkg) {
        this.f_package_name = f_pkg;
    }

    public String getF_package_dir() {
        return f_package_dir;
    }

    public void setF_package_dir(String f_pkg_dir) {
        this.f_package_dir = f_pkg_dir;
    }

    public GenerateRuleTableEntity getMainTable() {
        return mainTable;
    }

    public void setMainTable(GenerateRuleTableEntity mainTable) {
        this.mainTable = mainTable;
    }

    public List<GenerateRuleTableEntity> getTableList() {
        return tableList;
    }

    public void setTableList(List<GenerateRuleTableEntity> tableList) {
        this.tableList = tableList;
    }
}
