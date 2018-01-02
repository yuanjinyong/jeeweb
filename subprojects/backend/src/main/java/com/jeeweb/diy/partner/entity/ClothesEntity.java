package com.jeeweb.diy.partner.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.jeeweb.framework.business.entity.BaseEntity;
import com.jeeweb.framework.business.model.ICreator;


public class ClothesEntity extends BaseEntity<Long> implements ICreator {
    private static final long serialVersionUID = 1L;

    private Long f_partner_id; // 关联合作伙伴ID
    private String f_partner_name; // 关联合作伙伴名称
    private Long f_style_id; // 关联衣服款式ID
    private String f_style_name; // 关联衣服款式名称
    private Long f_size_id; // 关联衣服尺码ID
    private String f_size_name; // 关联衣服尺码名称
    private Long f_color_id; // 关联衣服颜色ID
    private String f_color_name; // 关联衣服颜色名称
    private BigDecimal f_cost_price; // 成本价，为空时，取衣服款式的成本价
    private String f_desc; // 描述
    private Long f_creator_id; // 创建人ID
    private String f_creator_name; // 创建人姓名
    private Timestamp f_created_time; // 创建时间
    private Integer f_status; // 状态：101、出售；102、停售
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

    public Long getF_style_id() {
        return f_style_id;
    }

    public void setF_style_id(Long f_style_id) {
        this.f_style_id = f_style_id;
    }

    public String getF_style_name() {
        return f_style_name;
    }

    public void setF_style_name(String f_style_name) {
        this.f_style_name = f_style_name;
    }

    public Long getF_size_id() {
        return f_size_id;
    }

    public void setF_size_id(Long f_size_id) {
        this.f_size_id = f_size_id;
    }

    public String getF_size_name() {
        return f_size_name;
    }

    public void setF_size_name(String f_size_name) {
        this.f_size_name = f_size_name;
    }

    public Long getF_color_id() {
        return f_color_id;
    }

    public void setF_color_id(Long f_color_id) {
        this.f_color_id = f_color_id;
    }

    public String getF_color_name() {
        return f_color_name;
    }

    public void setF_color_name(String f_color_name) {
        this.f_color_name = f_color_name;
    }

    public BigDecimal getF_cost_price() {
        return f_cost_price;
    }

    public void setF_cost_price(BigDecimal f_cost_price) {
        this.f_cost_price = f_cost_price;
    }

    public String getF_desc() {
        return f_desc;
    }

    public void setF_desc(String f_desc) {
        this.f_desc = f_desc;
    }

    @Override
    public Long getF_creator_id() {
        return f_creator_id;
    }

    @Override
    public void setF_creator_id(Long f_creator_id) {
        this.f_creator_id = f_creator_id;
    }

    @Override
    public String getF_creator_name() {
        return f_creator_name;
    }

    @Override
    public void setF_creator_name(String f_creator_name) {
        this.f_creator_name = f_creator_name;
    }

    @Override
    public Timestamp getF_created_time() {
        return f_created_time;
    }

    @Override
    public void setF_created_time(Timestamp f_created_time) {
        this.f_created_time = f_created_time;
    }

    public Integer getF_status() {
        return f_status;
    }

    public void setF_status(Integer f_status) {
        this.f_status = f_status;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

}
