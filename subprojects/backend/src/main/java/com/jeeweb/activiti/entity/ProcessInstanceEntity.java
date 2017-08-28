/**
 * 
 */
package com.jeeweb.activiti.entity;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @author 袁进勇
 *
 */
public class ProcessInstanceEntity extends ExecutionEntity {
    private static final long serialVersionUID = -3093114239838727401L;

    private String f_start_user_id;
    private Timestamp f_start_time;
    private Timestamp f_end_time;
    private Long f_duration;
    private String f_localized_name;
    private String f_localized_description;
    private String f_proc_def_id;
    private String f_proc_def_key;
    private String f_proc_def_name;
    private Integer f_proc_def_version;
    private String f_deployment_id;
    private String f_business_key;
    private String f_delete_reason;
    private Map<String, Object> variables;

    public String getF_start_user_id() {
        return f_start_user_id;
    }

    public void setF_start_user_id(String f_start_user_id) {
        this.f_start_user_id = f_start_user_id;
    }

    public Timestamp getF_start_time() {
        return f_start_time;
    }

    public void setF_start_time(Timestamp f_start_time) {
        this.f_start_time = f_start_time;
    }

    public Timestamp getF_end_time() {
        return f_end_time;
    }

    public void setF_end_time(Timestamp f_end_time) {
        this.f_end_time = f_end_time;
    }

    public Long getF_duration() {
        return f_duration;
    }

    public void setF_duration(Long f_duration) {
        this.f_duration = f_duration;
    }

    public String getF_localized_name() {
        return f_localized_name;
    }

    public void setF_localized_name(String f_localized_name) {
        this.f_localized_name = f_localized_name;
    }

    public String getF_localized_description() {
        return f_localized_description;
    }

    public void setF_localized_description(String f_localized_description) {
        this.f_localized_description = f_localized_description;
    }

    public String getF_proc_def_id() {
        return f_proc_def_id;
    }

    public void setF_proc_def_id(String f_proc_def_id) {
        this.f_proc_def_id = f_proc_def_id;
    }

    public String getF_proc_def_key() {
        return f_proc_def_key;
    }

    public void setF_proc_def_key(String f_proc_def_key) {
        this.f_proc_def_key = f_proc_def_key;
    }

    public String getF_proc_def_name() {
        return f_proc_def_name;
    }

    public void setF_proc_def_name(String f_proc_def_name) {
        this.f_proc_def_name = f_proc_def_name;
    }

    public Integer getF_proc_def_version() {
        return f_proc_def_version;
    }

    public void setF_proc_def_version(Integer f_proc_def_version) {
        this.f_proc_def_version = f_proc_def_version;
    }

    public String getF_deployment_id() {
        return f_deployment_id;
    }

    public void setF_deployment_id(String f_deployment_id) {
        this.f_deployment_id = f_deployment_id;
    }

    public String getF_business_key() {
        return f_business_key;
    }

    public void setF_business_key(String f_business_key) {
        this.f_business_key = f_business_key;
    }

    public String getF_delete_reason() {
        return f_delete_reason;
    }

    public void setF_delete_reason(String f_delete_reason) {
        this.f_delete_reason = f_delete_reason;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
