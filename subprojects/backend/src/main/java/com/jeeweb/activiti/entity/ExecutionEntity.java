/**
 * 
 */
package com.jeeweb.activiti.entity;

import com.jeeweb.framework.business.entity.BaseEntity;

/**
 * @author 袁进勇
 *
 */
public class ExecutionEntity extends BaseEntity<String> {
    private static final long serialVersionUID = -1308298335771205404L;
    private String f_tenant_id; // 租户ID
    private String f_name;
    private String f_description;
    private String f_activity_id; // 节点ID
    private String f_proc_inst_id;
    private String f_parent_id;
    private boolean suspended;
    private boolean ended;

    public String getF_tenant_id() {
        return f_tenant_id;
    }

    public void setF_tenant_id(String f_tenant_id) {
        this.f_tenant_id = f_tenant_id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_description() {
        return f_description;
    }

    public void setF_description(String f_description) {
        this.f_description = f_description;
    }

    public String getF_activity_id() {
        return f_activity_id;
    }

    public void setF_activity_id(String f_activity_id) {
        this.f_activity_id = f_activity_id;
    }

    public String getF_proc_inst_id() {
        return f_proc_inst_id;
    }

    public void setF_proc_inst_id(String f_proc_inst_id) {
        this.f_proc_inst_id = f_proc_inst_id;
    }

    public String getF_parent_id() {
        return f_parent_id;
    }

    public void setF_parent_id(String f_parent_id) {
        this.f_parent_id = f_parent_id;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }
}
