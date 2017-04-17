package com.jeeweb.platform.sys.entity;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeeweb.framework.business.entity.BaseEntity;
import com.jeeweb.framework.core.model.RowMap;

public class UserEntity extends BaseEntity<Integer> {
    public static final String SUPERADMIN = "SuperAdmin";
    public static final String ADMIN = "admin";
    public static final Integer STATUS_NORMAL = 1;
    public static final Integer STATUS_LOCKED = 2;
    public static final Integer STATUS_DEREGISTER = 3;

    private Integer f_tenant_id; // 租户ID
    private String f_tenant_name; // 租户名称
    private String f_account;// 账号
    @JsonIgnore
    private String f_password;// 密码，这个字段不能被Json序列化出去，否则导致密码泄露，所以这里需要添加@JsonIgnore注解
    private String f_name;// 姓名
    private String f_remark;// 备注说明
    private Integer f_status;// 状态，1(正常)、2（锁定）、3（注销）
    private Integer f_department_id;// 部门ID
    private String f_department_name;// 部门名称
    private Timestamp f_create_time; // 创建时间
    private Timestamp f_last_login_time; // 最后登录时间
    private Timestamp f_locked_time; // 锁定时间
    private Timestamp f_unregister_time; // 注销时间
    private Integer f_is_can_login; // 是否允许登录，1(是)、2(否)
    private List<RowMap> roleList; // 用户（操作员）角色列表
    private String f_role_ids;
    private String f_role_names;
    private String f_tenant_ids; // 用户所能够操作的租户ID

    public Integer getF_tenant_id() {
        return f_tenant_id;
    }

    public void setF_tenant_id(Integer f_tenant_id) {
        this.f_tenant_id = f_tenant_id;
    }

    public String getF_tenant_name() {
        return f_tenant_name;
    }

    public void setF_tenant_name(String f_tenant_name) {
        this.f_tenant_name = f_tenant_name;
    }

    public String getF_account() {
        return f_account;
    }

    public void setF_account(String f_account) {
        this.f_account = f_account;
    }

    public String getF_password() {
        return f_password;
    }

    public void setF_password(String f_password) {
        this.f_password = f_password;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

    public Integer getF_status() {
        return f_status;
    }

    public void setF_status(Integer f_status) {
        this.f_status = f_status;
    }

    public Timestamp getF_create_time() {
        return f_create_time;
    }

    public void setF_create_time(Timestamp f_create_time) {
        this.f_create_time = f_create_time;
    }

    public Timestamp getF_last_login_time() {
        return f_last_login_time;
    }

    public void setF_last_login_time(Timestamp f_last_login_time) {
        this.f_last_login_time = f_last_login_time;
    }

    public Timestamp getF_locked_time() {
        return f_locked_time;
    }

    public void setF_locked_time(Timestamp f_locked_time) {
        this.f_locked_time = f_locked_time;
    }

    public Timestamp getF_unregister_time() {
        return f_unregister_time;
    }

    public void setF_unregister_time(Timestamp f_unregister_time) {
        this.f_unregister_time = f_unregister_time;
    }

    public Integer getF_is_can_login() {
        return f_is_can_login;
    }

    public void setF_is_can_login(Integer f_is_can_login) {
        this.f_is_can_login = f_is_can_login;
    }

    public List<RowMap> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RowMap> roleList) {
        this.roleList = roleList;
    }

    public String getF_role_ids() {
        return f_role_ids;
    }

    public void setF_role_ids(String f_role_ids) {
        this.f_role_ids = f_role_ids;
    }

    public boolean isSuperAdmin() {
        return SUPERADMIN.equals(f_account);
    }

    public boolean isAdmin() {
        return ADMIN.equals(f_account);
    }

    public Integer getF_department_id() {
        return f_department_id;
    }

    public void setF_department_id(Integer f_department_id) {
        this.f_department_id = f_department_id;
    }

    public String getF_department_name() {
        return f_department_name;
    }

    public void setF_department_name(String f_department_name) {
        this.f_department_name = f_department_name;
    }

    public String getF_role_names() {
        return f_role_names;
    }

    public void setF_role_names(String f_role_names) {
        this.f_role_names = f_role_names;
    }

    public String getF_tenant_ids() {
        return f_tenant_ids;
    }

    public void setF_tenant_ids(String f_tenant_ids) {
        this.f_tenant_ids = f_tenant_ids;
    }
}
