package com.jeeweb.activiti.entity;

import com.jeeweb.framework.business.entity.BaseEntity;

public class ProcessDefinitionEntity extends BaseEntity<String> {
    private static final long serialVersionUID = 8357562010328298908L;

    private String f_tenant_id; // 租户ID
    private String f_key; // 编码
    private String f_name; // 名称
    private String f_category; // 类别
    private String f_description; //
    private Integer f_version; // 版本
    private String f_deployment_id; // 部署批次
    private String f_resource_name; // 资源文件名称
    private String f_diagram_resource_name; // 图片资源文件名称
    private boolean hasStartFormKey; // start节点是否存在formKey
    private boolean hasGraphicalNotation;
    private boolean suspended; // 是否挂起

    public String getF_tenant_id() {
        return f_tenant_id;
    }

    public void setF_tenant_id(String f_tenant_id) {
        this.f_tenant_id = f_tenant_id;
    }

    public String getF_key() {
        return f_key;
    }

    public void setF_key(String f_key) {
        this.f_key = f_key;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_category() {
        return f_category;
    }

    public void setF_category(String f_category) {
        this.f_category = f_category;
    }

    public String getF_description() {
        return f_description;
    }

    public void setF_description(String f_description) {
        this.f_description = f_description;
    }

    public Integer getF_version() {
        return f_version;
    }

    public void setF_version(Integer f_version) {
        this.f_version = f_version;
    }

    public String getF_deployment_id() {
        return f_deployment_id;
    }

    public void setF_deployment_id(String f_deployment_id) {
        this.f_deployment_id = f_deployment_id;
    }

    public String getF_resource_name() {
        return f_resource_name;
    }

    public void setF_resource_name(String f_resource_name) {
        this.f_resource_name = f_resource_name;
    }

    public String getF_diagram_resource_name() {
        return f_diagram_resource_name;
    }

    public void setF_diagram_resource_name(String f_diagram_resource_name) {
        this.f_diagram_resource_name = f_diagram_resource_name;
    }

    public boolean isHasStartFormKey() {
        return hasStartFormKey;
    }

    public void setHasStartFormKey(boolean hasStartFormKey) {
        this.hasStartFormKey = hasStartFormKey;
    }

    public boolean isHasGraphicalNotation() {
        return hasGraphicalNotation;
    }

    public void setHasGraphicalNotation(boolean hasGraphicalNotation) {
        this.hasGraphicalNotation = hasGraphicalNotation;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }
}
