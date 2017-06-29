package com.jeeweb.platform.sys.entity;

import com.jeeweb.framework.business.entity.BaseEntity;
import com.jeeweb.framework.business.model.IPreset;

public class RoleEntity extends BaseEntity<Integer> implements IPreset {
    public static final Integer ID_ADMIN_SYS = 1; // 系统管理员角色
    public static final Integer ID_NORMAL_USER = 2; // 普通用户角色
    public static final Integer ID_ADMIN_PLATFORM = 3; // 平台管理员角色
    public static final Integer ID_ADMIN_COMPANY = 4; // 企业管理员角色

    private static final long serialVersionUID = 5663590213239944053L;

    private String f_name;// 角色名称
    private String f_desc;// 角色描述
    private Integer f_is_preset;// 是否系统预置，1、系统预置；2、操作员创建
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
}
