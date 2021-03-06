-- liquibase formatted


-- changeset 袁进勇:20170501000401
-- comment: 创建用户表结构
CREATE TABLE `t_sys_user` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `f_account` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `f_password` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `f_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `f_telephone` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '绑定的手机号',
  `f_department_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '部门ID',
  `f_creator_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `f_created_time` datetime NOT NULL COMMENT '创建时间',
  `f_last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `f_locked_time` datetime DEFAULT NULL COMMENT '锁定时间',
  `f_unregister_time` datetime DEFAULT NULL COMMENT '注销时间',
  `f_is_can_login` tinyint(3) unsigned NOT NULL DEFAULT '101' COMMENT '是否允许登录：101、是；102、否',
  `f_is_preset` tinyint(3) unsigned NOT NULL DEFAULT '102' COMMENT '是否系统预置：101、系统预置；102、操作员创建',
  `f_status` tinyint(3) unsigned NOT NULL DEFAULT '101' COMMENT '状态：101、正常；102、锁定；103、注销',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_USER_ACCOUNT` (`f_account`),
  KEY `IDX_USER_TENANT` (`f_tenant_id`),
  KEY `IDX_USER_DEPARTMENT` (`f_department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户（操作员）表';

CREATE TABLE `t_sys_user_menu_authorization` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `f_menu_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户已授权限表';

CREATE TABLE `t_sys_user_menu_distribution` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `f_menu_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户可配权限表';

CREATE TABLE `t_sys_user_role` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `f_role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色用户表';
-- rollback DROP TABLE IF EXISTS t_sys_user_role;
-- rollback DROP TABLE IF EXISTS t_sys_user_menu_distribution;
-- rollback DROP TABLE IF EXISTS t_sys_user_menu_authorization;
-- rollback DROP TABLE IF EXISTS t_sys_user;


-- changeset 袁进勇:20170501000402 runOnChange:true
-- comment: 预置用户
DELETE FROM t_sys_user_role WHERE f_user_id IN (SELECT f_id FROM t_sys_user WHERE f_is_preset = 101);
DELETE FROM t_sys_user WHERE f_is_preset = 101;

insert  into `t_sys_user`(`f_id`,`f_tenant_id`,`f_account`,`f_password`,`f_name`,`f_telephone`,`f_department_id`,`f_creator_id`,`f_created_time`,`f_last_login_time`,`f_locked_time`,`f_unregister_time`,`f_is_can_login`,`f_is_preset`,`f_status`,`f_remark`) values (1,0,'SuperAdmin','$2a$10$o6R1D7JhRW7d56inhUm50eF5xy8fF1l3KPCc0kdgpKBMw6olsamiq','超级管理员',NULL,0,0,'1970-01-01 00:00:00','2017-07-10 11:29:52',NULL,NULL,101,101,101,'该账号是开发人员维护系统用，不能提供给客户使用。');
insert  into `t_sys_user`(`f_id`,`f_tenant_id`,`f_account`,`f_password`,`f_name`,`f_telephone`,`f_department_id`,`f_creator_id`,`f_created_time`,`f_last_login_time`,`f_locked_time`,`f_unregister_time`,`f_is_can_login`,`f_is_preset`,`f_status`,`f_remark`) values (2,0,'admin','$2a$10$BtvvDJwfxzJP7TRGEoG6p.PQbW1hWC6wvqiDUqjvMrfLoMgHjvjBa','系统管理员',NULL,0,0,'1970-01-01 00:00:00','2017-06-25 07:20:38',NULL,NULL,101,101,101,'该账号用于维护系统设置和权限分配。');

insert  into `t_sys_user_role`(`f_user_id`,`f_role_id`) values (2,1);
-- rollback DELETE FROM t_sys_user_role WHERE f_user_id IN (SELECT f_id FROM t_sys_user WHERE f_is_preset = 101);
-- rollback DELETE FROM t_sys_user WHERE f_is_preset = 101;

