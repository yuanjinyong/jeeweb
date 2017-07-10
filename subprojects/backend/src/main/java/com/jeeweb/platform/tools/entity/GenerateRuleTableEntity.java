package com.jeeweb.platform.tools.entity;

import java.util.List;

import com.jeeweb.framework.business.entity.BaseEntity;

public class GenerateRuleTableEntity extends BaseEntity<Integer> {
    private static final long serialVersionUID = 9118850333556862796L;

    private Integer f_rule_id; // 生成规则ID
    private String f_db_name; // 数据库名称
    private String f_table_name; // 数据库表名
    private Integer f_order; // 排序
    private Integer f_is_main; // 是否为主表，1是，2否

    private String f_entity_interface; // Java实体类实现的接口列表
    private String f_entity_class; // Java实体类的全路径类名
    private String f_entity_class_name; // Java实体类的类名
    private String f_entity_base_class; // Java实体类父类的全路径类名
    private String f_entity_base_class_name; // Java实体类父类的全路径类名
    private String f_mapper_base_class; //
    private String f_mapper_base_class_name; //
    private String f_mapper_class; //
    private String f_mapper_class_name; //
    private String f_service_base_class; //
    private String f_service_base_class_name; //
    private String f_service_class; //
    private String f_service_class_name; //
    private String f_rest_base_class; //
    private String f_rest_base_class_name; //
    private String f_rest_class; //
    private String f_rest_class_name; //

    private GenerateRuleFieldEntity primaryField;
    private List<GenerateRuleFieldEntity> fieldList;

    public Integer getF_rule_id() {
        return f_rule_id;
    }

    public void setF_rule_id(Integer f_rule_id) {
        this.f_rule_id = f_rule_id;
    }

    public String getF_db_name() {
        return f_db_name;
    }

    public void setF_db_name(String f_db_name) {
        this.f_db_name = f_db_name;
    }

    public String getF_table_name() {
        return f_table_name;
    }

    public void setF_table_name(String f_table_name) {
        this.f_table_name = f_table_name;
    }

    public Integer getF_order() {
        return f_order;
    }

    public void setF_order(Integer f_order) {
        this.f_order = f_order;
    }

    public Integer getF_is_main() {
        return f_is_main;
    }

    public void setF_is_main(Integer f_is_main) {
        this.f_is_main = f_is_main;
    }

    public String getF_entity_interface() {
        return f_entity_interface;
    }

    public void setF_entity_interface(String f_entity_interface) {
        this.f_entity_interface = f_entity_interface;
    }

    public String getF_entity_class() {
        return f_entity_class;
    }

    public void setF_entity_class(String f_entity_class) {
        this.f_entity_class = f_entity_class;
    }

    public String getF_entity_class_name() {
        return f_entity_class_name;
    }

    public void setF_entity_class_name(String f_entity_class_name) {
        this.f_entity_class_name = f_entity_class_name;
    }

    public String getF_entity_base_class() {
        return f_entity_base_class;
    }

    public void setF_entity_base_class(String f_entity_base_class) {
        this.f_entity_base_class = f_entity_base_class;
    }

    public String getF_entity_base_class_name() {
        return f_entity_base_class_name;
    }

    public void setF_entity_base_class_name(String f_entity_base_class_name) {
        this.f_entity_base_class_name = f_entity_base_class_name;
    }

    public String getF_mapper_base_class() {
        return f_mapper_base_class;
    }

    public void setF_mapper_base_class(String f_mapper_base_class) {
        this.f_mapper_base_class = f_mapper_base_class;
    }

    public String getF_mapper_base_class_name() {
        return f_mapper_base_class_name;
    }

    public void setF_mapper_base_class_name(String f_mapper_base_class_name) {
        this.f_mapper_base_class_name = f_mapper_base_class_name;
    }

    public String getF_mapper_class() {
        return f_mapper_class;
    }

    public void setF_mapper_class(String f_mapper_class) {
        this.f_mapper_class = f_mapper_class;
    }

    public String getF_mapper_class_name() {
        return f_mapper_class_name;
    }

    public void setF_mapper_class_name(String f_mapper_class_name) {
        this.f_mapper_class_name = f_mapper_class_name;
    }

    public String getF_service_base_class() {
        return f_service_base_class;
    }

    public void setF_service_base_class(String f_service_base_class) {
        this.f_service_base_class = f_service_base_class;
    }

    public String getF_service_base_class_name() {
        return f_service_base_class_name;
    }

    public void setF_service_base_class_name(String f_service_base_class_name) {
        this.f_service_base_class_name = f_service_base_class_name;
    }

    public String getF_service_class() {
        return f_service_class;
    }

    public void setF_service_class(String f_service_class) {
        this.f_service_class = f_service_class;
    }

    public String getF_service_class_name() {
        return f_service_class_name;
    }

    public void setF_service_class_name(String f_service_class_name) {
        this.f_service_class_name = f_service_class_name;
    }

    public String getF_rest_base_class() {
        return f_rest_base_class;
    }

    public void setF_rest_base_class(String f_rest_base_class) {
        this.f_rest_base_class = f_rest_base_class;
    }

    public String getF_rest_base_class_name() {
        return f_rest_base_class_name;
    }

    public void setF_rest_base_class_name(String f_rest_base_class_name) {
        this.f_rest_base_class_name = f_rest_base_class_name;
    }

    public String getF_rest_class() {
        return f_rest_class;
    }

    public void setF_rest_class(String f_rest_class) {
        this.f_rest_class = f_rest_class;
    }

    public String getF_rest_class_name() {
        return f_rest_class_name;
    }

    public void setF_rest_class_name(String f_rest_class_name) {
        this.f_rest_class_name = f_rest_class_name;
    }

    public GenerateRuleFieldEntity getPrimaryField() {
        return primaryField;
    }

    public void setPrimaryField(GenerateRuleFieldEntity primaryField) {
        this.primaryField = primaryField;
    }

    public List<GenerateRuleFieldEntity> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<GenerateRuleFieldEntity> fieldList) {
        this.fieldList = fieldList;
    }
}
