-- liquibase formatted


-- changeset 袁进勇:20170501000201
-- comment: 创建菜单表结构
CREATE TABLE `t_sys_menu` (
  `f_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '主键，以模块加横杠分隔，如 XTGL-QXGL 表示系统管理-权限管理',
  `f_parent_id` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '父级菜单',
  `f_parent_path` varchar(1024) COLLATE utf8_bin NOT NULL COMMENT '树形结构的路径，以“/”开头、分隔和结尾。',
  `f_order` int(11) NOT NULL DEFAULT '10' COMMENT '排序',
  `f_name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `f_desc` text COLLATE utf8_bin COMMENT '菜单描述',
  `f_icon` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `f_type` tinyint(3) unsigned NOT NULL DEFAULT '3' COMMENT '类型：1、目录；2、页面；3、按钮；4、令牌',
  `f_route_path` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单对应的URL，目录和按钮不需要填写，只有对应页面的菜单才需要填写',
  `f_is_web` tinyint(3) unsigned DEFAULT '101' COMMENT 'Web端是否启用：101、启用；102、不启用',
  `f_is_android` tinyint(3) unsigned DEFAULT '101' COMMENT 'Android端是否启用：101、启用；102、不启用',
  `f_is_ios` tinyint(3) unsigned DEFAULT '101' COMMENT 'IOS端是否启用：101、启用；102、不启用',
  `f_status` tinyint(3) unsigned DEFAULT '101' COMMENT '状态：101、启用；102、禁用',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  KEY `SYS_MENU_PARENT_ID` (`f_parent_id`),
  KEY `SYS_MENU_IS_SHOW` (`f_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单（权限）表';

CREATE TABLE `t_sys_menu_url` (
  `f_id`  bigint(20) NOT NULL AUTO_INCREMENT,
  `f_menu_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '菜单ID，关联t_sys_menu表的f_id',
  `f_url_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'URL的ID，关联t_sys_url表的f_id',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNQ_MENU_URL` (`f_menu_id`,`f_url_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单可以访问的URL地址';

insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('ROOT',NULL,'/',0,'系统菜单','根菜单',NULL,0,NULL,101,101,101,101,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ','ROOT','/ROOT/',995,'开发人员工具','开发人员工具模块','fa fa-wrench',1,NULL,101,102,102,101,'仅限开发人员使用，请不要授权给客户使用。');
-- rollback DROP TABLE IF EXISTS t_sys_menu_url;
-- rollback DROP TABLE IF EXISTS t_sys_menu;
