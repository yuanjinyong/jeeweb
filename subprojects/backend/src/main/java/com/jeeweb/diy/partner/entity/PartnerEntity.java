package com.jeeweb.diy.partner.entity;

import java.sql.Timestamp;

import com.jeeweb.framework.business.entity.BaseEntity;
import com.jeeweb.framework.business.model.ICreator;


public class PartnerEntity extends BaseEntity<Long> implements ICreator {
    private static final long serialVersionUID = 1L;

    private String f_name; // 名称
    private String f_desc; // 描述
    private String f_leader; // 负责人姓名
    private String f_phone; // 固定电话
    private String f_mobile; // 手机号码
    private String f_address; // 地址
    private Long f_creator_id; // 创建人ID
    private String f_creator_name; // 创建人姓名
    private Timestamp f_created_time; // 创建时间
    private Integer f_status; // 状态：101、正常合作；102、暂停合作
    private String f_remark; // 备注

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

    public String getF_leader() {
        return f_leader;
    }

    public void setF_leader(String f_leader) {
        this.f_leader = f_leader;
    }

    public String getF_phone() {
        return f_phone;
    }

    public void setF_phone(String f_phone) {
        this.f_phone = f_phone;
    }

    public String getF_mobile() {
        return f_mobile;
    }

    public void setF_mobile(String f_mobile) {
        this.f_mobile = f_mobile;
    }

    public String getF_address() {
        return f_address;
    }

    public void setF_address(String f_address) {
        this.f_address = f_address;
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
