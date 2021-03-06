package com.jeeweb.diy.partner.entity;

import java.math.BigDecimal;

import com.jeeweb.framework.business.entity.BaseEntity;

public class ClothesStyleEntity extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;

    private Long f_partner_id; // 关联合作伙伴ID
    private String f_partner_name; // 合作伙伴名称
    private String f_code; // 款式编号
    private String f_name; // 款式名称
    private String f_desc; // 描述
    private BigDecimal f_cost_price; // 成本价，单位元，如果尺码有成本价以尺码的成本价算尺码优先
    private Integer f_crowd_type; // 款式人群类型：101、通款(不分男女)；102、通款+童装；103、分款(男款女款)；104、分款+童装；105、童装
    private Integer f_weight; // 重量，单位g(克)，用于计算运费
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

    public BigDecimal getF_cost_price() {
        return f_cost_price;
    }

    public void setF_cost_price(BigDecimal f_cost_price) {
        this.f_cost_price = f_cost_price;
    }

    public Integer getF_crowd_type() {
        return f_crowd_type;
    }

    public void setF_crowd_type(Integer f_crowd_type) {
        this.f_crowd_type = f_crowd_type;
    }

    public Integer getF_weight() {
        return f_weight;
    }

    public void setF_weight(Integer f_weight) {
        this.f_weight = f_weight;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

}
