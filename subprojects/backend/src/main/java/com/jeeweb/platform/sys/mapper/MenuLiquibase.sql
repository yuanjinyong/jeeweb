-- liquibase formatted


-- changeset 袁进勇:20170801000201
-- comment: 创建菜单表结构
CREATE TABLE `t_sys_menu` (
  `f_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '主键，以模块加横杠分隔，如 XTGL-QXGL 表示系统管理-权限管理',
  `f_parent_id` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '父级菜单',
  `f_parent_path` varchar(1024) COLLATE utf8_bin NOT NULL COMMENT '树形结构的路径，以“/”开头、分隔和结尾。',
  `f_order` int(11) NOT NULL DEFAULT '10' COMMENT '排序',
  `f_name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `f_desc` text COLLATE utf8_bin COMMENT '菜单描述',
  `f_icon` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `f_type` int(2) NOT NULL DEFAULT '3' COMMENT '类型，1目录、2页面、3按钮、4令牌',
  `f_route_path` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单对应的URL，目录和按钮不需要填写，只有对应页面的菜单才需要填写',
  `f_is_web` int(2) DEFAULT '1' COMMENT 'Web端是否启用，1启用，2不启用',
  `f_is_android` int(2) DEFAULT '1' COMMENT 'Android端是否启用，1启用，2不启用',
  `f_is_ios` int(2) DEFAULT '1' COMMENT 'IOS端是否启用，1启用，2不启用',
  `f_status` int(2) DEFAULT '1' COMMENT '状态，1、启用；2、禁用',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  KEY `SYS_MENU_PARENT_ID` (`f_parent_id`),
  KEY `SYS_MENU_IS_SHOW` (`f_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单（权限）表';

CREATE TABLE `t_sys_menu_url` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_menu_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '菜单ID，关联t_sys_menu表的f_id',
  `f_url_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'URL的ID，关联t_sys_url表的f_id',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNQ_MENU_URL` (`f_menu_id`,`f_url_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单可以访问的URL地址';

insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('ROOT',NULL,'/',0,'系统菜单','根菜单',NULL,0,NULL,1,1,1,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('GRZX','ROOT','/ROOT/',10,'个人中心','个人中心模块','fa fa-user-circle',1,NULL,1,1,1,1,'日常工作常用功能的快捷入口。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL','ROOT','/ROOT/',990,'系统管理','系统管理模块','fa fa-cog',1,NULL,1,1,1,1,'平台系统管理。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ','ROOT','/ROOT/',995,'开发人员工具','开发人员工具模块','fa fa-wrench',1,NULL,1,0,0,1,'仅限开发人员使用，请不要授权给客户使用。');
-- rollback DROP TABLE IF EXISTS t_sys_menu_url;
-- rollback DROP TABLE IF EXISTS t_sys_menu;
