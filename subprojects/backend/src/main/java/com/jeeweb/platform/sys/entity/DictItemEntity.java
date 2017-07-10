package com.jeeweb.platform.sys.entity;


import com.jeeweb.framework.business.entity.BaseEntity;
import com.jeeweb.framework.business.model.IPreset;


public class DictItemEntity extends BaseEntity<Integer> implements IPreset {
    private static final long serialVersionUID = 1L;

    private Integer f_tenant_id; // 租户ID
    private String f_dict_code; // 字典组编码
    private Integer f_item_order; // 排序
    private String f_item_code; // 字典项编码
    private String f_item_text; // 字典项名称
    private Integer f_is_preset; // 是否系统预置，1、是；2、否

    public Integer getF_tenant_id() {
        return f_tenant_id;
    }

    public void setF_tenant_id(Integer f_tenant_id) {
        this.f_tenant_id = f_tenant_id;
    }

    public String getF_dict_code() {
        return f_dict_code;
    }

    public void setF_dict_code(String f_dict_code) {
        this.f_dict_code = f_dict_code;
    }

    public Integer getF_item_order() {
        return f_item_order;
    }

    public void setF_item_order(Integer f_item_order) {
        this.f_item_order = f_item_order;
    }

    public String getF_item_code() {
        return f_item_code;
    }

    public void setF_item_code(String f_item_code) {
        this.f_item_code = f_item_code;
    }

    public String getF_item_text() {
        return f_item_text;
    }

    public void setF_item_text(String f_item_name) {
        this.f_item_text = f_item_name;
    }

    @Override
    public Integer getF_is_preset() {
        return f_is_preset;
    }

    @Override
    public void setF_is_preset(Integer f_is_preset) {
        this.f_is_preset = f_is_preset;
    }

}
