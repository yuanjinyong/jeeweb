-- liquibase formatted



-- changeset 罗亮:20171222224927 runOnChange:true
-- comment: 菜单 DIY-HZHB 合作伙伴
-- DIY-HZHB 合作伙伴 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id = 'DIY-HZHB';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id = 'DIY-HZHB';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id = 'DIY-HZHB';
DELETE FROM `t_sys_menu` WHERE f_id = 'DIY-HZHB';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB','ROOT','/ROOT/',500,'合作伙伴','合作伙伴模块','fa fa-address-card-o',1,NULL,101,101,101,101,NULL);

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

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-HZHB','DIY-HZHB','/ROOT/DIY-HZHB/',10,'合作伙伴管理','合作伙伴管理页面','fa fa-user',2,'/admin/diy/partner/partner',101,101,101,101,NULL);
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





-- changeset 袁进勇:20171231084048 runOnChange:true
-- comment: 字典 ClothesSizeSize 衣服尺码
-- ClothesSizeSize 衣服尺码 BEGIN ************************
DELETE FROM `t_sys_dict_item` WHERE f_dict_code = 'ClothesSizeSize';
DELETE FROM `t_sys_dict` WHERE f_code = 'ClothesSizeSize';

INSERT INTO `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) VALUES ('ClothesSizeSize','衣服尺码','defaultSqlMapper','t_sys_dict_item','0','f_item_code','f_item_name','f_item_order',NULL,101,NULL);

INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',0,'XS','XS',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',1,'S','S',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',2,'M','M',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',3,'L','L',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',4,'XL','XL',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',5,'2XL','2XL',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',6,'3XL','3XL',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',7,'4XL','4XL',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',8,'5XL','5XL',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',9,'6XL','6XL',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',10,'7XL','7XL',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeSize',11,'8XL','8XL',101);
-- rollback DELETE FROM `t_sys_dict_item` WHERE f_dict_code = 'ClothesSizeSize';
-- rollback DELETE FROM `t_sys_dict` WHERE f_code = 'ClothesSizeSize';
-- ClothesSizeSize 衣服尺码 END **************************


-- changeset 袁进勇:20171231084138 runOnChange:true
-- comment: 字典 ClothesSizeType 衣服尺码类型
-- ClothesSizeType 衣服尺码类型 BEGIN ************************
DELETE FROM `t_sys_dict_item` WHERE f_dict_code = 'ClothesSizeType';
DELETE FROM `t_sys_dict` WHERE f_code = 'ClothesSizeType';

INSERT INTO `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) VALUES ('ClothesSizeType','衣服尺码类型','defaultSqlMapper','t_sys_dict_item','0','f_item_code','f_item_name','f_item_order',NULL,101,NULL);

INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeType',0,'101','通用',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeType',1,'102','男',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeType',2,'103','女',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesSizeType',3,'104','童',101);
-- rollback DELETE FROM `t_sys_dict_item` WHERE f_dict_code = 'ClothesSizeType';
-- rollback DELETE FROM `t_sys_dict` WHERE f_code = 'ClothesSizeType';
-- ClothesSizeType 衣服尺码类型 END **************************




-- changeset 袁进勇:20171231084457 runOnChange:true
-- comment: 字典 ClothesStyleCrowdType 款式人群类型
-- ClothesStyleCrowdType 款式人群类型 BEGIN ************************
DELETE FROM `t_sys_dict_item` WHERE f_dict_code = 'ClothesStyleCrowdType';
DELETE FROM `t_sys_dict` WHERE f_code = 'ClothesStyleCrowdType';

INSERT INTO `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) VALUES ('ClothesStyleCrowdType','款式人群类型','defaultSqlMapper','t_sys_dict_item','0','f_item_code','f_item_name','f_item_order',NULL,101,NULL);

INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesStyleCrowdType',0,'101','通款(不分男女)',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesStyleCrowdType',1,'102','通款+童装',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesStyleCrowdType',2,'103','分款(男款女款)',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesStyleCrowdType',3,'104','分款+童装',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesStyleCrowdType',4,'105','童装',101);
-- rollback DELETE FROM `t_sys_dict_item` WHERE f_dict_code = 'ClothesStyleCrowdType';
-- rollback DELETE FROM `t_sys_dict` WHERE f_code = 'ClothesStyleCrowdType';
-- ClothesStyleCrowdType 款式人群类型 END **************************



-- changeset 袁进勇:20171231084840 runOnChange:true
-- comment: 字典 ClothesStyleStatus 衣服款式状态
-- ClothesStyleStatus 衣服款式状态 BEGIN ************************
DELETE FROM `t_sys_dict_item` WHERE f_dict_code = 'ClothesStyleStatus';
DELETE FROM `t_sys_dict` WHERE f_code = 'ClothesStyleStatus';

INSERT INTO `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) VALUES ('ClothesStyleStatus','衣服款式状态','defaultSqlMapper','t_sys_dict_item','0','f_item_code','f_item_name','f_item_order',NULL,101,NULL);

INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesStyleStatus',0,'101','出售',101);
INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES (0,'ClothesStyleStatus',1,'102','停售',101);
-- rollback DELETE FROM `t_sys_dict_item` WHERE f_dict_code = 'ClothesStyleStatus';
-- rollback DELETE FROM `t_sys_dict` WHERE f_code = 'ClothesStyleStatus';
-- ClothesStyleStatus 衣服款式状态 END **************************







-- changeset 袁进勇:20171231085039 runOnChange:true
-- comment: 菜单 DIY-HZHB-YFCM 衣服尺码管理
-- DIY-HZHB-YFCM 衣服尺码管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'DIY-HZHB-YFCM%';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'DIY-HZHB-YFCM%';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'DIY-HZHB-YFCM%';
DELETE FROM `t_sys_menu` WHERE f_id LIKE 'DIY-HZHB-YFCM%';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFCM','DIY-HZHB','/ROOT/DIY-HZHB/',100,'衣服尺码管理','衣服尺码管理页面','fa fa-arrows-alt',2,'/admin/diy/partner/clothes/size',101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFCM-ZJ','DIY-HZHB-YFCM','/ROOT/DIY-HZHB/DIY-HZHB-YFCM/',10,'增加','增加衣服尺码',NULL,3,NULL,101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFCM-XG','DIY-HZHB-YFCM','/ROOT/DIY-HZHB/DIY-HZHB-YFCM/',20,'修改','修改衣服尺码',NULL,3,NULL,101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFCM-SC','DIY-HZHB-YFCM','/ROOT/DIY-HZHB/DIY-HZHB-YFCM/',30,'删除','删除衣服尺码',NULL,3,NULL,101,101,101,101,NULL);

INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFCM','8a2f2cb5270cfd034afcaeec98ad77d2');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFCM','bba9ec7d3a568f4ea700d6cc522f8fe2');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFCM-SC','5c9bbc5adda8df074abf0811e783840f');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFCM-XG','2ef89dc46d74f0ab6b15d9d9a4a6df02');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFCM-ZJ','63458d9e4981111cacee77a65f9c940e');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFCM');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFCM-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFCM-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFCM-ZJ');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'DIY-HZHB-YFCM%';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'DIY-HZHB-YFCM%';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'DIY-HZHB-YFCM%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id LIKE 'DIY-HZHB-YFCM%';
-- DIY-HZHB-YFCM 衣服尺码管理 END **************************



-- changeset 袁进勇:20171231085105 runOnChange:true
-- comment: 菜单 DIY-HZHB-YFYS 衣服颜色管理
-- DIY-HZHB-YFYS 衣服颜色管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'DIY-HZHB-YFYS%';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'DIY-HZHB-YFYS%';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'DIY-HZHB-YFYS%';
DELETE FROM `t_sys_menu` WHERE f_id LIKE 'DIY-HZHB-YFYS%';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFYS','DIY-HZHB','/ROOT/DIY-HZHB/',110,'衣服颜色管理','衣服颜色管理页面','fa fa-adjust',2,'/admin/diy/partner/clothes/color',101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFYS-ZJ','DIY-HZHB-YFYS','/ROOT/DIY-HZHB/DIY-HZHB-YFYS/',10,'增加','增加衣服颜色',NULL,3,NULL,101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFYS-XG','DIY-HZHB-YFYS','/ROOT/DIY-HZHB/DIY-HZHB-YFYS/',20,'修改','修改衣服颜色',NULL,3,NULL,101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFYS-SC','DIY-HZHB-YFYS','/ROOT/DIY-HZHB/DIY-HZHB-YFYS/',30,'删除','删除衣服颜色',NULL,3,NULL,101,101,101,101,NULL);

INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFYS','0cbd5a6c41bb4733d6996aeb2426b1d1');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFYS','244ee1acc38483b1e2e242970cc21c06');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFYS-SC','56fb7e4edcc444abfdf1d2c3d31f876c');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFYS-XG','0791011732a2ea37b9f4df6a879fca19');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFYS-ZJ','885843f8947eadac213d342694c0364c');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFYS');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFYS-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFYS-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFYS-ZJ');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'DIY-HZHB-YFYS%';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'DIY-HZHB-YFYS%';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'DIY-HZHB-YFYS%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id LIKE 'DIY-HZHB-YFYS%';
-- DIY-HZHB-YFYS 衣服颜色管理 END **************************



-- changeset 袁进勇:20171231085123 runOnChange:true
-- comment: 菜单 DIY-HZHB-YFKS 衣服款式管理
-- DIY-HZHB-YFKS 衣服款式管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'DIY-HZHB-YFKS%';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'DIY-HZHB-YFKS%';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'DIY-HZHB-YFKS%';
DELETE FROM `t_sys_menu` WHERE f_id LIKE 'DIY-HZHB-YFKS%';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFKS','DIY-HZHB','/ROOT/DIY-HZHB/',120,'衣服款式管理','衣服款式管理页面','fa fa-shopping-bag',2,'/admin/diy/partner/clothes/style',101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFKS-ZJ','DIY-HZHB-YFKS','/ROOT/DIY-HZHB/DIY-HZHB-YFKS/',10,'增加','增加衣服款式',NULL,3,NULL,101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFKS-XG','DIY-HZHB-YFKS','/ROOT/DIY-HZHB/DIY-HZHB-YFKS/',20,'修改','修改衣服款式',NULL,3,NULL,101,101,101,101,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('DIY-HZHB-YFKS-SC','DIY-HZHB-YFKS','/ROOT/DIY-HZHB/DIY-HZHB-YFKS/',30,'删除','删除衣服款式',NULL,3,NULL,101,101,101,101,NULL);

INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFKS','896d42f7a97808b24aedd4df912e149e');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFKS','a26584872663683733203e7b9be3ec2e');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFKS-SC','c69f1c1643bca25bdc7410e68d2d4c37');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFKS-XG','56d67ea6f66bb601d5a54fb50ae5a67f');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('DIY-HZHB-YFKS-ZJ','bc703599bbfa85fc9e2115bbb091689e');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFKS');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFKS-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFKS-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'DIY-HZHB-YFKS-ZJ');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'DIY-HZHB-YFKS%';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'DIY-HZHB-YFKS%';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'DIY-HZHB-YFKS%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id LIKE 'DIY-HZHB-YFKS%';
-- DIY-HZHB-YFKS 衣服款式管理 END **************************



