package com.jeeweb.platform.sys.entity;

import com.jeeweb.framework.business.entity.BaseEntity;

public class RoleEntity extends BaseEntity<Integer> {
    private static final long serialVersionUID = 5663590213239944053L;

    private String f_name;// 角色名称
    private String f_desc; // 角色描述
    private Integer f_is_sys;// 是否系统预置，1、系统预置的角色；2、前台创建的角色
    private String f_remark;// 角色描述

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

    public Integer getF_is_sys() {
        return f_is_sys;
    }

    public void setF_is_sys(Integer f_is_sys) {
        this.f_is_sys = f_is_sys;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }
}
