package com.jeeweb.platform.pub.entity;

import java.sql.Timestamp;

import com.jeeweb.framework.business.entity.BaseEntity;
import com.jeeweb.framework.business.model.ICreator;

public class AttachmentEntity extends BaseEntity<Integer> implements ICreator {
    public static final Integer STATUS_INIT = 1;
    public static final Integer STATUS_ARCHIVED = 2;
    public static final Integer STATUS_UPLOADED = 3;
    public static final Integer STATUS_PREDELETE = 4;

    private static final long serialVersionUID = 1L;

    private Integer f_tenant_id; // 企业ID，用于做数据隔离
    private String f_entity_name; // 业务实体类名
    private Integer f_entity_id; // 业务实体对象ID
    private String f_name; // 文件名
    private String f_type; // 文件类型
    private Long f_size; // 字节数
    private String f_local_path; // 本地存储路径
    private String f_remote_path; // 远程存储路径
    private Integer f_creator_id; // 创建人，系统用户ID
    private String f_creator_name; // 创建人名称
    private Timestamp f_created_time; // 创建时间
    private Integer f_status; // 状态，1、待归档；2、已归档；3、已上传；4、待删除；

    private String name;
    private String url;

    public Integer getF_tenant_id() {
        return f_tenant_id;
    }

    public void setF_tenant_id(Integer f_company_id) {
        this.f_tenant_id = f_company_id;
    }

    public String getF_entity_name() {
        return f_entity_name;
    }

    public void setF_entity_name(String f_entity_name) {
        this.f_entity_name = f_entity_name;
    }

    public Integer getF_entity_id() {
        return f_entity_id;
    }

    public void setF_entity_id(Integer f_entity_id) {
        this.f_entity_id = f_entity_id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_type() {
        return f_type;
    }

    public void setF_type(String f_type) {
        this.f_type = f_type;
    }

    public Long getF_size() {
        return f_size;
    }

    public void setF_size(Long f_size) {
        this.f_size = f_size;
    }

    public String getF_local_path() {
        return f_local_path;
    }

    public void setF_local_path(String f_local_path) {
        this.f_local_path = f_local_path;
    }

    public String getF_remote_path() {
        return f_remote_path;
    }

    public void setF_remote_path(String f_remote_path) {
        this.f_remote_path = f_remote_path;
    }

    @Override
    public Integer getF_creator_id() {
        return f_creator_id;
    }

    @Override
    public void setF_creator_id(Integer f_creator_id) {
        this.f_creator_id = f_creator_id;
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

    @Override
    public String getF_creator_name() {
        return f_creator_name;
    }

    @Override
    public void setF_creator_name(String f_creator_name) {
        this.f_creator_name = f_creator_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
