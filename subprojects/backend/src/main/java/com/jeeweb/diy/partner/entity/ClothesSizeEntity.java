package com.jeeweb.diy.partner.entity;

import com.jeeweb.framework.business.entity.BaseEntity;

public class ClothesSizeEntity extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    private Long f_partner_id; // 关联合作伙伴ID
    private String f_partner_name; // 合作伙伴名称
    private String f_type; // 类型：A、通用；M、男；F、女；C、童
    private String f_size; // 尺码：XS、S、M、L、XL、2XL、3XL、4XL、5XL、6XL、7XL、8XL
    private String f_code; // 尺码编号，通：XS-A、S-A、M-A、L-A、XL-A、2XL-A、3XL-A、4XL-A、5XL-A、6XL-A、7XL-A；男：XS-M、S-M、M-M、L-M、XL-M、2XL-M、3XL-M、4XL-M、5XL-M、6XL-M、7XL-M；女：XS-W、S-W、M-W、L-W、XL-W、2XL-W、3XL-W、4XL-W、5XL-W、6XL-W、7XL-W、8XL-W；童：XS-C、S-C、M-C、L-C、XL-C、2XL-C、3XL-C、4XL-C
    private String f_name; // 尺码名称
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

    public String getF_type() {
        return f_type;
    }

    public void setF_type(String f_type) {
        this.f_type = f_type;
    }

    public String getF_size() {
        return f_size;
    }

    public void setF_size(String f_size) {
        this.f_size = f_size;
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
