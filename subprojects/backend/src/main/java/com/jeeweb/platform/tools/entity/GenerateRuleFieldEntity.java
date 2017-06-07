package com.jeeweb.platform.tools.entity;

import com.jeeweb.framework.business.entity.BaseEntity;

public class GenerateRuleFieldEntity extends BaseEntity<Integer> {
    private static final long serialVersionUID = -4363776482523982780L;

    private Integer f_table_id; // 数据库表信息ID
    private Integer f_order; // 排序
    private String f_column_name; // 字段名
    private String f_column_comment; // 字段描述
    private String f_column_type; // 字段类型
    private String f_java_type; // 字段对应Java数据类型
    private String f_short_java_type; // 字段对应Java数据类型的类名
    private Integer f_is_primary; // 是否为主健，1是，2否
    private Integer f_is_super_class_field; // 是否为父类字段，1是，2否
    private Integer f_is_override_field; // 是否为覆写或实现接口字段，1是，2否
    private Integer f_is_insert; // 是否为插入字段，1是，2否
    private Integer f_is_update; // 是否为更新字段，1是，2否
    private Integer f_is_select; // 是否为查询字段，1是，2否
    private Integer f_is_equal; // 是否为等于查询条件，1是，2否
    private Integer f_is_like; // 是否为左右模糊查询条件，1是，2否
    private Integer f_is_left_like; // 是否为左模糊查询条件，1是，2否
    private Integer f_is_right_like; // 是否为右模糊查询条件，1是，2否
    private Integer f_is_in; // 是否为IN查询条件，1是，2否
    private Integer f_is_not_in; // 是否为NOT IN查询条件，1是，2否
    private Integer f_is_between; // 是否为BETWEEN查询条件，1是，2否
    private Integer f_is_search; // 是否在搜索栏中显示，1是，2否
    private Integer f_is_grid; // 是否在Grid表格中显示，1是，2否
    private Integer f_is_form; // 是否在Form表单中显示，1是，2否

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String f_is_nullable;
    private String f_default_value;
    private String f_data_type;
    private Long f_max_length;
    private Long f_octet_length;
    private Long f_numeric_precision;
    private Long f_numeric_scale;
    private Long f_datetime_precision;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Integer getF_table_id() {
        return f_table_id;
    }

    public void setF_table_id(Integer f_table_id) {
        this.f_table_id = f_table_id;
    }

    public Integer getF_order() {
        return f_order;
    }

    public void setF_order(Integer f_order) {
        this.f_order = f_order;
    }

    public String getF_column_name() {
        return f_column_name;
    }

    public void setF_column_name(String f_column_name) {
        this.f_column_name = f_column_name;
    }

    public String getF_column_comment() {
        return f_column_comment;
    }

    public void setF_column_comment(String f_column_comment) {
        this.f_column_comment = f_column_comment;
    }

    public String getF_java_type() {
        return f_java_type;
    }

    public void setF_java_type(String f_java_type) {
        this.f_java_type = f_java_type;
    }

    public String getF_short_java_type() {
        return f_short_java_type;
    }

    public void setF_short_java_type(String f_short_java_type) {
        this.f_short_java_type = f_short_java_type;
    }

    public Integer getF_is_primary() {
        return f_is_primary;
    }

    public void setF_is_primary(Integer f_is_primary) {
        this.f_is_primary = f_is_primary;
    }

    public Integer getF_is_super_class_field() {
        return f_is_super_class_field;
    }

    public void setF_is_super_class_field(Integer f_is_super_class_field) {
        this.f_is_super_class_field = f_is_super_class_field;
    }

    public Integer getF_is_override_field() {
        return f_is_override_field;
    }

    public void setF_is_override_field(Integer f_is_override_field) {
        this.f_is_override_field = f_is_override_field;
    }

    public Integer getF_is_insert() {
        return f_is_insert;
    }

    public void setF_is_insert(Integer f_is_insert) {
        this.f_is_insert = f_is_insert;
    }

    public Integer getF_is_update() {
        return f_is_update;
    }

    public void setF_is_update(Integer f_is_update) {
        this.f_is_update = f_is_update;
    }

    public Integer getF_is_select() {
        return f_is_select;
    }

    public void setF_is_select(Integer f_is_select) {
        this.f_is_select = f_is_select;
    }

    public Integer getF_is_equal() {
        return f_is_equal;
    }

    public void setF_is_equal(Integer f_is_equal) {
        this.f_is_equal = f_is_equal;
    }

    public Integer getF_is_like() {
        return f_is_like;
    }

    public void setF_is_like(Integer f_is_like) {
        this.f_is_like = f_is_like;
    }

    public Integer getF_is_left_like() {
        return f_is_left_like;
    }

    public void setF_is_left_like(Integer f_is_left_like) {
        this.f_is_left_like = f_is_left_like;
    }

    public Integer getF_is_right_like() {
        return f_is_right_like;
    }

    public void setF_is_right_like(Integer f_is_right_like) {
        this.f_is_right_like = f_is_right_like;
    }

    public Integer getF_is_in() {
        return f_is_in;
    }

    public void setF_is_in(Integer f_is_in) {
        this.f_is_in = f_is_in;
    }

    public Integer getF_is_not_in() {
        return f_is_not_in;
    }

    public void setF_is_not_in(Integer f_is_not_in) {
        this.f_is_not_in = f_is_not_in;
    }

    public Integer getF_is_between() {
        return f_is_between;
    }

    public void setF_is_between(Integer f_is_between) {
        this.f_is_between = f_is_between;
    }

    public Integer getF_is_search() {
        return f_is_search;
    }

    public void setF_is_search(Integer f_is_search) {
        this.f_is_search = f_is_search;
    }

    public Integer getF_is_grid() {
        return f_is_grid;
    }

    public void setF_is_grid(Integer f_is_grid) {
        this.f_is_grid = f_is_grid;
    }

    public Integer getF_is_form() {
        return f_is_form;
    }

    public void setF_is_form(Integer f_is_form) {
        this.f_is_form = f_is_form;
    }

    public String getF_column_type() {
        return f_column_type;
    }

    public void setF_column_type(String f_column_type) {
        this.f_column_type = f_column_type;
    }

    public String getF_is_nullable() {
        return f_is_nullable;
    }

    public void setF_is_nullable(String f_is_nullable) {
        this.f_is_nullable = f_is_nullable;
    }

    public String getF_default_value() {
        return f_default_value;
    }

    public void setF_default_value(String f_default_value) {
        this.f_default_value = f_default_value;
    }

    public String getF_data_type() {
        return f_data_type;
    }

    public void setF_data_type(String f_data_type) {
        this.f_data_type = f_data_type;
    }

    public Long getF_max_length() {
        return f_max_length;
    }

    public void setF_max_length(Long f_max_length) {
        this.f_max_length = f_max_length;
    }

    public Long getF_octet_length() {
        return f_octet_length;
    }

    public void setF_octet_length(Long f_octet_length) {
        this.f_octet_length = f_octet_length;
    }

    public Long getF_numeric_precision() {
        return f_numeric_precision;
    }

    public void setF_numeric_precision(Long f_numeric_precision) {
        this.f_numeric_precision = f_numeric_precision;
    }

    public Long getF_numeric_scale() {
        return f_numeric_scale;
    }

    public void setF_numeric_scale(Long f_numeric_scale) {
        this.f_numeric_scale = f_numeric_scale;
    }

    public Long getF_datetime_precision() {
        return f_datetime_precision;
    }

    public void setF_datetime_precision(Long f_datetime_precision) {
        this.f_datetime_precision = f_datetime_precision;
    }
}
