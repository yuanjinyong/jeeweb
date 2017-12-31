package com.jeeweb.diy.partner.entity;

import com.jeeweb.framework.business.entity.BaseEntity;

public class ClothesColorEntity extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    private Long f_partner_id; // 关联合作伙伴ID
    private String f_partner_name; // 合作伙伴名称
    private String f_code; // 颜色代码
    private String f_name; // 名称
    private String f_desc; // 描述
    private String f_remark; // 备注

    public Long getF_partner_id() {
        return f_partner_id;
    }

    public void setF_partner_id(Long f_partner_id) {
        this.f_partner_id = f_partner_id;
    }

    public String getF_partner_name() {
        return f_partner_name;
    }

    public void setF_partner_name(String f_partner_name) {
        this.f_partner_name = f_partner_name;
    }

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

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

}
