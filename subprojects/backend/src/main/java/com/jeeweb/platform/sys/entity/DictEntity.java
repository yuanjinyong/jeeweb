package com.jeeweb.platform.sys.entity;


import java.util.List;

import com.jeeweb.framework.business.entity.BaseEntity;
import com.jeeweb.framework.business.model.IPreset;


public class DictEntity extends BaseEntity<Integer> implements IPreset {
    private static final long serialVersionUID = 1L;

    private String f_code; // 字典组编码
    private String f_name; // 字典组名称
    private String f_db_name; // 字典组对应数据库名
    private String f_table_name; // 字典组对应数据表名
    private String f_tenant_column; // 字典项租户ID对应数据库表中字段
    private String f_code_column; // 字典项编码对应数据库表中字段
    private String f_name_column; // 字典项描述对应数据库表中字段
    private String f_order_column; // 字典项排序对应数据库表中字段
    private String f_where_clause; // 拼接到查询SQL语句中的where条件
    private Integer f_is_preset; // 是否系统预置，1、是；2、否
    private String f_remark; // 备注

    private List<DictItemEntity> itemList; // 字典项列表

    public String getF_code() {
        return f_code;
    }

    public void setF_code(String f_code) {
        this.f_code = f_code;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_db_name() {
        return f_db_name;
    }

    public void setF_db_name(String f_ds_name) {
        this.f_db_name = f_ds_name;
    }

    public String getF_table_name() {
        return f_table_name;
    }

    public void setF_table_name(String f_table_name) {
        this.f_table_name = f_table_name;
    }

    public String getF_tenant_column() {
        return f_tenant_column;
    }

    public void setF_tenant_column(String f_tenant_column) {
        this.f_tenant_column = f_tenant_column;
    }

    public String getF_code_column() {
        return f_code_column;
    }

    public void setF_code_column(String f_code_column) {
        this.f_code_column = f_code_column;
    }

    public String getF_name_column() {
        return f_name_column;
    }

    public void setF_name_column(String f_text_column) {
        this.f_name_column = f_text_column;
    }

    public String getF_order_column() {
        return f_order_column;
    }

    public void setF_order_column(String f_order_column) {
        this.f_order_column = f_order_column;
    }

    public String getF_where_clause() {
        return f_where_clause;
    }

    public void setF_where_clause(String f_where_clause) {
        this.f_where_clause = f_where_clause;
    }

    @Override
    public Integer getF_is_preset() {
        return f_is_preset;
    }

    @Override
    public void setF_is_preset(Integer f_is_preset) {
        this.f_is_preset = f_is_preset;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

    public List<DictItemEntity> getItemList() {
        return itemList;
    }

    public void setItemList(List<DictItemEntity> itemList) {
        this.itemList = itemList;
    }

}
