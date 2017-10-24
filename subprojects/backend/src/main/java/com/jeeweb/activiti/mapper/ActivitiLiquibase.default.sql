-- liquibase formatted



-- changeset 袁进勇:20170807000000 runOnChange:true
-- comment: 菜单 GZLGL 工作流管理
-- GZLGL 工作流管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id = 'GZLGL';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id = 'GZLGL';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id = 'GZLGL';
DELETE FROM `t_sys_menu` WHERE f_id = 'GZLGL';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('GZLGL','ROOT','/ROOT/',800,'工作流管理','工作流管理模块','fa fa-random',1,NULL,1,2,2,1,NULL);

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'GZLGL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'GZLGL');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id = 'GZLGL';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id = 'GZLGL';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id = 'GZLGL';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id = 'GZLGL';
-- GZLGL 工作流管理 END **************************


-- changeset 袁进勇:20170807000001 runOnChange:true
-- comment: 菜单 GZLGL-LCMXGL 流程模型管理
-- GZLGL-LCMXGL 流程模型管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'GZLGL-LCMXGL%';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'GZLGL-LCMXGL%';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'GZLGL-LCMXGL%';
DELETE FROM `t_sys_menu` WHERE f_id LIKE 'GZLGL-LCMXGL%';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('GZLGL-LCMXGL','GZLGL','/ROOT/GZLGL/',10,'流程模型管理','流程模型管理页面','fa fa-meetup',2,'/admin/activiti/process/model',1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('GZLGL-LCMXGL-ZJ','GZLGL-LCMXGL','/ROOT/GZLGL/GZLGL-LCMXGL/',10,'增加','增加流程模型',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('GZLGL-LCMXGL-XG','GZLGL-LCMXGL','/ROOT/GZLGL/GZLGL-LCMXGL/',20,'修改','修改流程模型',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('GZLGL-LCMXGL-SC','GZLGL-LCMXGL','/ROOT/GZLGL/GZLGL-LCMXGL/',30,'删除','删除流程模型',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('GZLGL-LCMXGL-BJ','GZLGL-LCMXGL','/ROOT/GZLGL/GZLGL-LCMXGL/',40,'编辑','编辑流程节点',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('GZLGL-LCMXGL-FB','GZLGL-LCMXGL','/ROOT/GZLGL/GZLGL-LCMXGL/',50,'发布','发布流程模型',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('GZLGL-LCMXGL-XZ','GZLGL-LCMXGL','/ROOT/GZLGL/GZLGL-LCMXGL/',60,'下载','下载流程模型',NULL,3,NULL,1,1,1,1,NULL);

INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('GZLGL-LCMXGL','19f724015a1fb097f4e09c3b07ad2bad');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('GZLGL-LCMXGL','96a85a8b1405d380300f787e9813dcd8');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('GZLGL-LCMXGL','cb68695db4af5b997e239bc81bf7cf60');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('GZLGL-LCMXGL','e8d53df51cd333f9644c4bd5ba1d26d0');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('GZLGL-LCMXGL-BJ','fcc787ec71f20d4309b9d803fbd7de67');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('GZLGL-LCMXGL-FB','324f8615ce3c58af8e331cf04f1b4ee3');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('GZLGL-LCMXGL-SC','12c35ec35cc79fb9e63bc33a46b2271c');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('GZLGL-LCMXGL-XZ','d9f4a2b25b12aef725e109e2805d5354');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('GZLGL-LCMXGL-ZJ','c9bfc2a9eb78149340e65972708a25be');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'GZLGL-LCMXGL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'GZLGL-LCMXGL-BJ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'GZLGL-LCMXGL-FB');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'GZLGL-LCMXGL-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'GZLGL-LCMXGL-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'GZLGL-LCMXGL-XZ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'GZLGL-LCMXGL-ZJ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'GZLGL-LCMXGL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'GZLGL-LCMXGL-BJ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'GZLGL-LCMXGL-FB');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'GZLGL-LCMXGL-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'GZLGL-LCMXGL-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'GZLGL-LCMXGL-XZ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'GZLGL-LCMXGL-ZJ');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'GZLGL-LCMXGL%';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'GZLGL-LCMXGL%';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'GZLGL-LCMXGL%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id LIKE 'GZLGL-LCMXGL%';
-- GZLGL-LCMXGL 流程模型管理 END **************************
