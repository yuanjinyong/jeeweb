-- ${rule.f_code} ${rule.f_name}
delete from `t_tool_generate_rule` where `f_code` = '${rule.f_code}';
insert into `t_tool_generate_rule` (`f_id`, `f_code`, `f_name`, `f_menu_id`, `f_menu_name`, `f_menu_remark`, `f_menu_parent_id`, `f_menu_order`, `f_request_url`, `f_package_name`) values('${rule.f_id}','${rule.f_code}','${rule.f_name}','${rule.f_menu_id}','${rule.f_menu_name}','${rule.f_menu_remark}','${rule.f_menu_parent_id}','${rule.f_menu_order}','${rule.f_request_url}','${rule.f_package_name}');
<%
rule.tableList.eachWithIndex { table, idx1 ->
    println ""
    println "-- ${table.f_table_name}"
    println "delete from `t_tool_generate_rule_table` where `f_rule_id` = '${rule.f_id}' and `f_db_name` = '${table.f_db_name}' and `f_table_name` = '${table.f_table_name}';"
    println "insert into `t_tool_generate_rule_table` (`f_id`, `f_rule_id`, `f_db_name`, `f_table_name`, `f_order`, `f_is_main`, `f_entity_interface`, `f_entity_base_class`, `f_entity_class`, `f_mapper_base_class`, `f_mapper_class`, `f_service_base_class`, `f_service_class`, `f_rest_base_class`, `f_rest_class`) values('${table.f_id}','${table.f_rule_id}','${table.f_db_name}','${table.f_table_name}','${table.f_order}','${table.f_is_main}','${table.f_entity_interface}','${table.f_entity_base_class}','${table.f_entity_class}','${table.f_mapper_base_class}','${table.f_mapper_class}','${table.f_service_base_class}','${table.f_service_class}','${table.f_rest_base_class}','${table.f_rest_class}');"
    println ""
    println "delete from `t_tool_generate_rule_field` where `f_table_id` = '${table.f_id}';"
    table.fieldList.eachWithIndex { field, idx2 ->
        println "insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('${field.f_id}','${field.f_table_id}','${field.f_order}','${field.f_column_name}','${field.f_column_comment}','${field.f_column_type}','${field.f_java_type}','${field.f_is_primary}','${field.f_is_super_class_field}','${field.f_is_override_field}','${field.f_is_insert}','${field.f_is_update}','${field.f_is_select}','${field.f_is_equal}','${field.f_is_like}','${field.f_is_left_like}','${field.f_is_right_like}','${field.f_is_in}','${field.f_is_not_in}','${field.f_is_between}','${field.f_is_search}','${field.f_is_grid}','${field.f_is_form}');"
    }
}
%>