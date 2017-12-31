-- Partner 合作伙伴
delete from `t_tool_generate_rule` where `f_code` = 'Partner';
insert into `t_tool_generate_rule` (`f_id`, `f_code`, `f_name`, `f_menu_id`, `f_menu_name`, `f_menu_remark`, `f_menu_parent_id`, `f_menu_parent_path`, `f_menu_order`, `f_request_url`, `f_package_name`) values('1','Partner','合作伙伴','DIY-HZHB-HZHB','合作伙伴管理','合作伙伴管理页面','DIY-HZHB','/ROOT/DIY-HZHB/','10','/api/diy/partner/partners','com.jeeweb.diy.partner');

-- t_diy_partner
delete from `t_tool_generate_rule_table` where `f_rule_id` = '1' and `f_db_name` = 'jeeweb-diy' and `f_table_name` = 't_diy_partner';
insert into `t_tool_generate_rule_table` (`f_id`, `f_rule_id`, `f_db_name`, `f_table_name`, `f_order`, `f_is_main`, `f_entity_interface`, `f_entity_base_class`, `f_entity_class`, `f_mapper_base_class`, `f_mapper_class`, `f_service_base_class`, `f_service_class`, `f_rest_base_class`, `f_rest_class`) values('1','1','jeeweb-diy','t_diy_partner','1','101','com.jeeweb.framework.business.model.ICreator','com.jeeweb.framework.business.entity.BaseEntity','com.jeeweb.diy.partner.entity.PartnerEntity','com.jeeweb.framework.business.mapper.BaseMapper','com.jeeweb.diy.partner.mapper.PartnerMapper','com.jeeweb.framework.business.service.BaseService','com.jeeweb.diy.partner.service.PartnerService','com.jeeweb.framework.business.web.api.BaseApi','com.jeeweb.diy.partner.web.api.PartnerApi');

delete from `t_tool_generate_rule_field` where `f_table_id` = '1';
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('1','1','0','f_id','ID','bigint(20)','java.lang.Long','101','101','102','102','102','101','102','102','102','102','102','102','102','102','102','102');
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('2','1','1','f_name','名称','varchar(256)','java.lang.String','102','102','102','101','101','101','102','101','102','102','102','102','102','102','102','102');
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('3','1','2','f_desc','描述','text','java.lang.String','102','102','102','101','101','101','102','102','102','102','102','102','102','102','102','102');
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('4','1','3','f_leader','负责人姓名','varchar(80)','java.lang.String','102','102','102','101','101','101','102','101','102','102','102','102','102','102','102','102');
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('5','1','4','f_phone','固定电话','varchar(50)','java.lang.String','102','102','102','101','101','101','102','101','102','102','102','102','102','102','102','102');
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('6','1','5','f_mobile','手机号码','varchar(15)','java.lang.String','102','102','102','101','101','101','102','101','102','102','102','102','102','102','102','102');
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('7','1','6','f_address','地址','varchar(256)','java.lang.String','102','102','102','101','101','101','102','101','102','102','102','102','102','102','102','102');
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('8','1','7','f_creator_id','创建人ID','bigint(20)','java.lang.Long','102','102','101','101','102','101','101','102','102','102','102','102','102','102','102','102');
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('9','1','8','f_creator_name','创建人姓名','varchar','java.lang.String','102','102','101','102','102','102','102','102','102','102','102','102','102','102','102','102');
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('10','1','9','f_created_time','创建时间','datetime','java.sql.Timestamp','102','102','101','101','102','101','102','102','102','102','102','102','101','102','102','102');
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('11','1','10','f_status','状态：101、正常合作；102、暂停合作','tinyint(3) unsigned','java.lang.Integer','102','102','102','101','101','101','101','102','102','102','101','102','102','102','102','102');
insert into `t_tool_generate_rule_field` (`f_id`, `f_table_id`, `f_order`, `f_column_name`, `f_column_comment`, `f_column_type`, `f_java_type`, `f_is_primary`, `f_is_super_class_field`, `f_is_override_field`, `f_is_insert`, `f_is_update`, `f_is_select`, `f_is_equal`, `f_is_like`, `f_is_left_like`, `f_is_right_like`, `f_is_in`, `f_is_not_in`, `f_is_between`, `f_is_search`, `f_is_grid`, `f_is_form`) values('12','1','11','f_remark','备注','longtext','java.lang.String','102','102','102','101','101','101','102','102','102','102','102','102','102','102','102','102');