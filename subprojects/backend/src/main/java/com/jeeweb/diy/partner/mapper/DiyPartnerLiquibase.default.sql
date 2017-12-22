-- liquibase formatted



-- changeset 罗亮:20171222224927 runOnChange:true
-- comment: 菜单 DIY-HZHB 合作伙伴
-- DIY-HZHB 合作伙伴 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id = 'DIY-HZHB';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id = 'DIY-HZHB';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id = 'DIY-HZHB';
DELETE FROM `t_sys_menu` WHERE f_id = 'DIY-HZHB';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB','ROOT','/ROOT/',500,'合作伙伴','合作伙伴模块',NULL,1,NULL,101,101,101,101,NULL);

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id = 'DIY-HZHB';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id = 'DIY-HZHB';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id = 'DIY-HZHB%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id = 'DIY-HZHB';
-- DIY-HZHB 合作伙伴 END **************************






-- changeset 罗亮:20171222225554 runOnChange:true
-- comment: 菜单 DIY-HZHB-HZHB 合作伙伴管理
-- DIY-HZHB-HZHB 合作伙伴管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'DIY-HZHB-HZHB%';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'DIY-HZHB-HZHB%';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'DIY-HZHB-HZHB%';
DELETE FROM `t_sys_menu` WHERE f_id LIKE 'DIY-HZHB-HZHB%';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-HZHB','DIY-HZHB','/ROOT/DIY-HZHB/',10,'合作伙伴管理','合作伙伴管理页面',NULL,2,'/admin/diy/partner/partner',101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-HZHB-ZJ','DIY-HZHB-HZHB','/ROOT/DIY-HZHB/DIY-HZHB-HZHB/',10,'增加','增加合作伙伴',NULL,3,NULL,101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-HZHB-XG','DIY-HZHB-HZHB','/ROOT/DIY-HZHB/DIY-HZHB-HZHB/',20,'修改','修改合作伙伴',NULL,3,NULL,101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-HZHB-SC','DIY-HZHB-HZHB','/ROOT/DIY-HZHB/DIY-HZHB-HZHB/',30,'删除','删除合作伙伴',NULL,3,NULL,101,101,101,101,NULL);

INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-HZHB','c5717e2650b0a98a8195c47064ed815c');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-HZHB','e83363ff5eebf910e5989c475eddddc6');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-HZHB-SC','727cdd100c6c0ecd346f452e16d3a804');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-HZHB-XG','71b593e6e01b0a4f6b4a11e20b97f874');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-HZHB-ZJ','36191ea9e6786170d2bf28d314a1428a');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-HZHB');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-HZHB-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-HZHB-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-HZHB-ZJ');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'DIY-HZHB-HZHB%';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'DIY-HZHB-HZHB%';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'DIY-HZHB-HZHB%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id LIKE 'DIY-HZHB-HZHB%';
-- DIY-HZHB-HZHB 合作伙伴管理 END **************************












-- changeset 罗亮:20171222225842 runOnChange:true
-- comment: 字典 PartnerStatus 合作伙伴状态
-- PartnerStatus 合作伙伴状态 BEGIN ************************
DELETE FROM `t_sys_dict_item` WHERE f_dict_code = 'PartnerStatus';
DELETE FROM `t_sys_dict` WHERE f_code = 'PartnerStatus';

INSERT INTO `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) VALUES ('PartnerStatus','合作伙伴状态','defaultSqlMapper','t_sys_dict_item','0','f_item_code','f_item_name','f_item_order',NULL,101,NULL);

INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'PartnerStatus',0,'101','正常合作',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'PartnerStatus',1,'102','暂停合作',101);
-- rollback DELETE FROM `t_sys_dict_item` WHERE f_dict_code = 'PartnerStatus';
-- rollback DELETE FROM `t_sys_dict` WHERE f_code = 'PartnerStatus';
-- PartnerStatus 合作伙伴状态 END **************************




