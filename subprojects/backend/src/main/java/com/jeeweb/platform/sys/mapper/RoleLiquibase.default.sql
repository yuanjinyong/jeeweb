-- liquibase formatted


-- changeset 袁进勇:20170501000301
-- comment: 创建角色表结构
CREATE TABLE `t_sys_role` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `f_desc` text COLLATE utf8_bin COMMENT '角色描述',
  `f_is_preset` int(2) NOT NULL DEFAULT '2' COMMENT '是否系统预置，1、系统预置；2、操作员创建',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_ROLE_NAME` (`f_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

CREATE TABLE `t_sys_role_menu_authorization` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_role_id` int(11) NOT NULL COMMENT '角色ID',
  `f_menu_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色已授权限表';

CREATE TABLE `t_sys_role_menu_distribution` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_role_id` int(11) NOT NULL COMMENT '角色ID',
  `f_menu_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色可配权限表';
-- rollback DROP TABLE IF EXISTS t_sys_role_menu_distribution;
-- rollback DROP TABLE IF EXISTS t_sys_role_menu_authorization;
-- rollback DROP TABLE IF EXISTS t_sys_role;


-- changeset 袁进勇:20170501000302 runOnChange:true
-- comment: 预置角色
DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id = 'ROOT' AND f_role_id IN (1,2,3,4);
DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id = 'ROOT' AND f_role_id IN (1,2,3,4);
DELETE FROM `t_sys_role` WHERE f_is_preset = 1;

INSERT INTO `t_sys_role`(`f_id`,`f_name`,`f_desc`,`f_is_preset`,`f_remark`) VALUES (1,'系统管理员角色','用于系统的运营维护。',1,'系统主要分为平台运维、施工企业、业主监督等子系统。');
INSERT INTO `t_sys_role`(`f_id`,`f_name`,`f_desc`,`f_is_preset`,`f_remark`) VALUES (2,'普通用户角色',NULL,1,NULL);
INSERT INTO `t_sys_role`(`f_id`,`f_name`,`f_desc`,`f_is_preset`,`f_remark`) VALUES (3,'平台管理员角色','用于平台的运营维护。',1,'注意：不能分配企业相关的功能给该角色。');
INSERT INTO `t_sys_role`(`f_id`,`f_name`,`f_desc`,`f_is_preset`,`f_remark`) VALUES (4,'企业管理员角色','用于企业的运营维护，主要为企业的权限分配。',1,'注意：不能分配平台相关的功能给该角色。');

INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (1,'ROOT');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (2,'ROOT');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (3,'ROOT');
INSERT INTO `t_sys_role_menu_authorization`(`f_role_id`,`f_menu_id`) VALUES (4,'ROOT');

INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (1,'ROOT');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (2,'ROOT');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (3,'ROOT');
INSERT INTO `t_sys_role_menu_distribution`(`f_role_id`,`f_menu_id`) VALUES (4,'ROOT');
-- rollback DELETE FROM `t_sys_role_menu_distribution` WHERE f_menu_id = 'ROOT' AND f_role_id IN (1,2,3,4);
-- rollback DELETE FROM `t_sys_role_menu_authorization` WHERE f_menu_id = 'ROOT' AND f_role_id IN (1,2,3,4);
-- rollback DELETE FROM t_sys_role WHERE f_is_preset = 1;
