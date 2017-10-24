-- liquibase formatted



-- changeset 袁进勇:20170706010000 runOnChange:true
-- comment: GRZX 个人中心
-- GRZX 个人中心 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id = 'GRZX';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id = 'GRZX';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id = 'GRZX';
DELETE FROM `t_sys_menu` WHERE f_id = 'GRZX';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('GRZX','ROOT','/ROOT/',10,'个人中心','个人中心模块','fa fa-user-circle',1,NULL,1,1,1,1,'日常工作常用功能的快捷入口。');

INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'GRZX');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (2,'GRZX');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'GRZX');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (4,'GRZX');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'GRZX');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'GRZX');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (4,'GRZX');
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id = 'GRZX';
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id = 'GRZX';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id = 'GRZX';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id = 'GRZX';
-- GRZX 个人中心 END **************************


-- changeset 袁进勇:20170706010001 runOnChange:true
-- comment: 菜单 XTGL 系统管理
-- XTGL 系统管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id = 'XTGL';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id = 'XTGL';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id = 'XTGL';
DELETE FROM `t_sys_menu` WHERE f_id = 'XTGL';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL','ROOT','/ROOT/',990,'系统管理','系统管理模块','fa fa-cog',1,NULL,1,1,1,1,'平台系统管理。');

INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id = 'XTGL';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id = 'XTGL';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id = 'XTGL';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id = 'XTGL';
-- XTGL 系统管理 END **************************



-- changeset 袁进勇:20170706010002 runOnChange:true
-- comment: 菜单 KFGJ-URLGL URL管理
-- KFGJ-URLGL URL管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'KFGJ-URLGL%';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'KFGJ-URLGL%';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'KFGJ-URLGL%';
DELETE FROM `t_sys_menu` WHERE f_id LIKE 'KFGJ-URLGL%';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('KFGJ-URLGL','KFGJ','/ROOT/KFGJ/',30,'URL管理','URL管理页面','fa fa-link',2,'/admin/platform/sys/url',1,0,0,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('KFGJ-URLGL-ZJ','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',10,'增加','增加URL',NULL,3,NULL,1,0,0,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('KFGJ-URLGL-XG','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',20,'修改','修改URL',NULL,3,NULL,1,0,0,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('KFGJ-URLGL-SC','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',30,'删除','删除URL',NULL,3,NULL,1,0,0,1,NULL);

INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-URLGL','0c0362062f72869516168ed479fa82bb');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-URLGL','d7b9102569dc7cfdc45956fa9cd32ad3');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-URLGL-SC','4087eb30e3c4c0dac2765498b3b0cab7');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-URLGL-SC','66e99840d588bd859cad21d43a1d8ae3');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-URLGL-XG','b73c369d73f83cd700ca2c554f76d504');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-URLGL-ZJ','f59ac63ca88503cd64ed14020d1cbead');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'KFGJ-URLGL%';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'KFGJ-URLGL%';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'KFGJ-URLGL%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id LIKE 'KFGJ-URLGL%';
-- KFGJ-URLGL URL管理 END **************************



-- changeset 袁进勇:20170706010003 runOnChange:true
-- comment: 菜单 KFGJ-CDGL 菜单管理
-- KFGJ-CDGL 菜单管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'KFGJ-CDGL%';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'KFGJ-CDGL%';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'KFGJ-CDGL%';
DELETE FROM `t_sys_menu` WHERE f_id LIKE 'KFGJ-CDGL%';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('KFGJ-CDGL','KFGJ','/ROOT/KFGJ/',20,'菜单管理','菜单管理页面','fa fa-bars',2,'/admin/platform/sys/menu',1,0,0,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('KFGJ-CDGL-ZJ','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',10,'增加','增加菜单',NULL,3,NULL,1,0,0,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('KFGJ-CDGL-XG','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',20,'修改','修改菜单',NULL,3,NULL,1,0,0,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('KFGJ-CDGL-SC','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',30,'删除','删除菜单',NULL,3,NULL,1,0,0,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('KFGJ-CDGL-DCSQL','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',100,'导出SQL','导出SQL脚本',NULL,3,NULL,1,0,0,1,NULL);

INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-CDGL','0c0362062f72869516168ed479fa82bb');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-CDGL','4050f6a3ffd77bdc4534d886e11f8012');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-CDGL','55847dc87d27c4b9146caf118c45321c');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-CDGL','d7b9102569dc7cfdc45956fa9cd32ad3');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-CDGL-DCSQL','d7e533dd5cc473d1f2fcec5557881f91');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-CDGL-SC','5a91bc0915d3e5c0b2e49d45c1b168ca');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-CDGL-XG','fed4f0beac39e30f0c8354eaa65fd25f');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('KFGJ-CDGL-ZJ','a61340a1087d2e7bed041cf57d43a397');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'KFGJ-CDGL%';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'KFGJ-CDGL%';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'KFGJ-CDGL%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id LIKE 'KFGJ-CDGL%';
-- KFGJ-CDGL 菜单管理 END **************************



-- changeset 袁进勇:20170706010004 runOnChange:true
-- comment: 菜单 XTGL-JSGL 角色管理
-- XTGL-JSGL 角色管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'XTGL-JSGL%';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'XTGL-JSGL%';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'XTGL-JSGL%';
DELETE FROM `t_sys_menu` WHERE f_id LIKE 'XTGL-JSGL%';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-JSGL','XTGL','/ROOT/XTGL/',20,'角色管理','角色管理页面','fa fa-flag',2,'/admin/platform/sys/role',1,0,0,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-JSGL-ZJ','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',10,'增加','增加角色',NULL,3,NULL,1,0,0,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-JSGL-XG','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',20,'修改','修改角色',NULL,3,NULL,1,0,0,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-JSGL-SC','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',30,'删除','删除角色',NULL,3,NULL,1,0,0,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-JSGL-SQ','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',40,'授权','授权可以操作的功能',NULL,3,NULL,1,0,0,1,NULL);

INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-JSGL','0eadc8c7c022f13cdedaa7e7413a0fe1');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-JSGL','2f12fc88084ea062e3bdde086342a323');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-JSGL','83b5dcfd8eae19d04a703d55c507f35b');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-JSGL-SC','eace04d50010a26a1e37225f2e203b26');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-JSGL-SQ','2d32ed761f669dfccc7ef6fbff98a9b6');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-JSGL-XG','d5d29576cc3167cbdb8fe3a0b2e9cb58');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-JSGL-ZJ','fa4524d97c93bb8f196782d4a66ecdd0');

INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-JSGL');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-JSGL-SC');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-JSGL-SQ');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-JSGL-XG');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-JSGL-ZJ');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-JSGL');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-JSGL-SC');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-JSGL-SQ');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-JSGL-XG');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-JSGL-ZJ');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-JSGL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-JSGL-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-JSGL-SQ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-JSGL-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-JSGL-ZJ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-JSGL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-JSGL-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-JSGL-SQ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-JSGL-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-JSGL-ZJ');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'XTGL-JSGL%';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'XTGL-JSGL%';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'XTGL-JSGL%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id LIKE 'XTGL-JSGL%';
-- XTGL-JSGL 角色管理 END **************************



-- changeset 袁进勇:20170706010005 runOnChange:true
-- comment: 菜单 XTGL-YHGL 用户管理
-- XTGL-YHGL 用户管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'XTGL-YHGL%';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'XTGL-YHGL%';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'XTGL-YHGL%';
DELETE FROM `t_sys_menu` WHERE f_id LIKE 'XTGL-YHGL%';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-YHGL','XTGL','/ROOT/XTGL/',30,'用户管理','用户管理页面','fa fa-user',2,'/admin/platform/sys/user',1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-YHGL-ZJ','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',10,'增加','增加用户',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-YHGL-XG','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',20,'修改','修改用户',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-YHGL-SC','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',30,'注销','注销用户',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-YHGL-SQ','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',40,'授权','授权可以操作的功能',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-YHGL-CZMM','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',50,'重置密码','重置用户密码',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-YHGL-JS','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',60,'解锁','解锁用户',NULL,3,NULL,1,2,2,1,NULL);

INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-YHGL','2f12fc88084ea062e3bdde086342a323');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-YHGL','954c06cf2c5133fb3bdf244d26955a14');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-YHGL','c2e253ad345ea3998b90df4e6e97e356');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-YHGL','d44c436777a17f4868813efbbb2ffffb');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-YHGL-CZMM','394365a60a740d7c0527badd4b964897');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-YHGL-JS','c2d22998a0d5e0fac6400a86e9a14a3f');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-YHGL-SC','ff40e182cbb096c35d1e4ad60c3eab09');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-YHGL-SQ','30d0c0b3dbcb1124cd1d5b0bbd514dde');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-YHGL-XG','b91a4be3d763864cbc7efe78010e3980');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-YHGL-ZJ','b640c19e353f49757e9fa7991b62ed7a');

INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-CZMM');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-JS');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-SC');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-SQ');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-XG');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-ZJ');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-CZMM');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-JS');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-SC');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-SQ');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-XG');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-ZJ');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-CZMM');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-JS');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-SQ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-YHGL-ZJ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-CZMM');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-JS');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-SQ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-YHGL-ZJ');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'XTGL-YHGL%';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'XTGL-YHGL%';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'XTGL-YHGL%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id LIKE 'XTGL-YHGL%';
-- XTGL-YHGL 用户管理 END **************************



-- changeset 袁进勇:20170706010006 runOnChange:true
-- comment: 菜单 XTGL-ZDGL 字典管理
-- XTGL-ZDGL 字典管理 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'XTGL-ZDGL%';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'XTGL-ZDGL%';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'XTGL-ZDGL%';
DELETE FROM `t_sys_menu` WHERE f_id LIKE 'XTGL-ZDGL%';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-ZDGL','XTGL','/ROOT/XTGL/',60,'字典管理','字典管理页面','fa fa-book',2,'/admin/platform/sys/dict',1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-ZDGL-ZJ','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',10,'增加','增加字典组',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-ZDGL-XG','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',20,'修改','修改字典组',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-ZDGL-SC','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',30,'删除','删除字典组',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-ZDGL-DCSQL','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',40,'导出SQL','导出SQL脚本',NULL,3,NULL,1,2,2,1,NULL);

INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-ZDGL','247045bb9c8412526f9ff48b9fc4b98b');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-ZDGL','5cf851af135d49453d13f68313176112');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-ZDGL','7632e2b173c88248ecfc42614ab3472f');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-ZDGL','b6f804b70de1d791ed057678b9748b4e');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-ZDGL','bace82089e32ac0b0c43288a83bd02ac');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-ZDGL','fb8f9ccaad4855f891ec1c4521f6e51d');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-ZDGL-SC','0e0b103069ec1bef20429d38dfaeae38');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-ZDGL-XG','de1613a5a8f4f669e345085786e60831');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-ZDGL-ZJ','a7e30f1ae19ec6b6bde9a7d9efbd5f82');

INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-ZDGL');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-ZDGL-DCSQL');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-ZDGL-SC');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-ZDGL-XG');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-ZDGL-ZJ');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-ZDGL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-ZDGL-DCSQL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-ZDGL-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-ZDGL-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-ZDGL-ZJ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-ZDGL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-ZDGL-DCSQL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-ZDGL-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-ZDGL-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-ZDGL-ZJ');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'XTGL-ZDGL%';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'XTGL-ZDGL%';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'XTGL-ZDGL%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id LIKE 'XTGL-ZDGL%';
-- XTGL-ZDGL 字典管理 END **************************



-- changeset 袁进勇:20170706010007 runOnChange:true
-- comment: 菜单 XTGL-XTSZ 系统设置
-- XTGL-XTSZ 系统设置 BEGIN ************************
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'XTGL-XTSZ%';
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'XTGL-XTSZ%';
DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'XTGL-XTSZ%';
DELETE FROM `t_sys_menu` WHERE f_id LIKE 'XTGL-XTSZ%';

INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-XTSZ','XTGL','/ROOT/XTGL/',70,'系统设置','系统设置页面','fa fa-wrench',2,'/admin/platform/sys/setting',1,2,2,1,'系统设置（参数）管理功能。');
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-XTSZ-ZJ','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',10,'增加','增加系统设置项',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-XTSZ-XG','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',20,'修改','修改系统设置项',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-XTSZ-SC','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',30,'删除','删除系统设置项',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-XTSZ-DCSQL','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',40,'导出SQL','导出SQL脚本',NULL,3,NULL,1,2,2,1,NULL);
INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES ('XTGL-XTSZ-SZQZ','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',50,'设置取值','设置系统设置项的取值',NULL,3,NULL,1,2,2,1,NULL);

INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-XTSZ','8ba694590f475c4d3e2faffca32820de');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-XTSZ','f1255871f4c57f850378235d3b556a5c');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-XTSZ-SC','3139c2251a87ef1c11c42638491c968f');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-XTSZ-XG','73fad798792127d298e178a9c6c20ba3');
INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES ('XTGL-XTSZ-ZJ','b403fa836b154a9a8456d631f2d7f5ca');

INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ-DCSQL');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ-SC');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ-SZQZ');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ-XG');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ-ZJ');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-XTSZ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-XTSZ-DCSQL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-XTSZ-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-XTSZ-SZQZ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-XTSZ-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'XTGL-XTSZ-ZJ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ-DCSQL');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ-SC');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ-SZQZ');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ-XG');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'XTGL-XTSZ-ZJ');
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id LIKE 'XTGL-XTSZ%';
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id LIKE 'XTGL-XTSZ%';
-- rollback DELETE FROM `t_sys_menu_url` WHERE f_menu_id LIKE 'XTGL-XTSZ%';
-- rollback DELETE FROM `t_sys_menu` WHERE f_id LIKE 'XTGL-XTSZ%';
-- XTGL-XTSZ 系统设置 END **************************
