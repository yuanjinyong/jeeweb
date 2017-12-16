package com.jeeweb.platform.sys.entity;


import com.jeeweb.framework.business.entity.BaseEntity;


public class SettingEntity extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    private String f_code; // 编码
    private String f_name; // 名称
    private String f_desc; // 描述
    private Integer f_order; // 排序
    private Integer f_is_editable; // 是否开放给客户编辑：101、是；102、否
    private String f_field_type; // 输入控件类型，比如varchar，int，decimal，datetime，dict
    private String f_field_cfg; // 输入控件配置
    private String f_init_value; // 出厂值
    private String f_value; // 当前值
    private String f_remark; // 备注

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

    public String getF_desc() {
        return f_desc;
    }

    public void setF_desc(String f_desc) {
        this.f_desc = f_desc;
    }

    public Integer getF_order() {
        return f_order;
    }

    public void setF_order(Integer f_order) {
        this.f_order = f_order;
    }

    public Integer getF_is_editable() {
        return f_is_editable;
    }

    public void setF_is_editable(Integer f_is_editable) {
        this.f_is_editable = f_is_editable;
    }

    public String getF_field_type() {
        return f_field_type;
    }

    public void setF_field_type(String f_field_type) {
        this.f_field_type = f_field_type;
    }

    public String getF_field_cfg() {
        return f_field_cfg;
    }

    public void setF_field_cfg(String f_field_cfg) {
        this.f_field_cfg = f_field_cfg;
    }

    public String getF_init_value() {
        return f_init_value;
    }

    public void setF_init_value(String f_init_value) {
        this.f_init_value = f_init_value;
    }

    public String getF_value() {
        return f_value;
    }

    public void setF_value(String f_value) {
        this.f_value = f_value;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

}
