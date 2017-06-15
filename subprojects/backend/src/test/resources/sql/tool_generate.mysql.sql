/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.10-log : Database - jeeweb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*Table structure for table `t_tool_generate_rule` */

DROP TABLE IF EXISTS `t_tool_generate_rule`;

CREATE TABLE `t_tool_generate_rule` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_code` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '编码',
  `f_name` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `f_menu_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单ID',
  `f_menu_name` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单名称',
  `f_menu_remark` longtext COLLATE utf8_bin COMMENT '菜单描述',
  `f_menu_parent_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父级菜单',
  `f_menu_parent_path` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '树形结构的路径，以“/”开头、分隔和结尾。',
  `f_menu_order` int(11) NOT NULL DEFAULT '10' COMMENT '同一个父级菜单下的排序',
  `f_request_url` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '后台Rest API的URL',
  `f_package_name` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '所属模块的包名',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代码生成规则表';

/*Data for the table `t_tool_generate_rule` */

/*Table structure for table `t_tool_generate_rule_field` */

DROP TABLE IF EXISTS `t_tool_generate_rule_field`;

CREATE TABLE `t_tool_generate_rule_field` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_table_id` int(11) DEFAULT NULL COMMENT '数据库表信息ID',
  `f_order` int(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `f_column_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '字段名',
  `f_column_comment` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '字段描述',
  `f_column_type` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '字段类型',
  `f_java_type` varchar(256) COLLATE utf8_bin DEFAULT 'java.lang.String' COMMENT '字段对应Java数据类型',
  `f_is_primary` tinyint(1) DEFAULT '2' COMMENT '是否为主健，1是，2否',
  `f_is_super_class_field` tinyint(1) DEFAULT '2' COMMENT '是否为父类字段，1是，2否',
  `f_is_override_field` tinyint(1) DEFAULT '2' COMMENT '是否为覆写或实现接口字段，1是，2否',
  `f_is_insert` tinyint(1) DEFAULT '1' COMMENT '是否为插入字段，1是，2否',
  `f_is_update` tinyint(1) DEFAULT '1' COMMENT '是否为更新字段，1是，2否',
  `f_is_select` tinyint(1) DEFAULT '1' COMMENT '是否为查询字段，1是，2否',
  `f_is_equal` tinyint(1) DEFAULT '2' COMMENT '是否为等于查询条件，1是，2否',
  `f_is_like` tinyint(1) DEFAULT '2' COMMENT '是否为左右模糊查询条件，1是，2否',
  `f_is_left_like` tinyint(1) DEFAULT '2' COMMENT '是否为左模糊查询条件，1是，2否',
  `f_is_right_like` tinyint(1) DEFAULT '2' COMMENT '是否为右模糊查询条件，1是，2否',
  `f_is_in` tinyint(1) DEFAULT '2' COMMENT '是否为IN查询条件，1是，2否',
  `f_is_not_in` tinyint(1) DEFAULT '2' COMMENT '是否为NOT IN查询条件，1是，2否',
  `f_is_between` tinyint(1) DEFAULT '2' COMMENT '是否为BETWEEN查询条件，1是，2否',
  `f_is_search` tinyint(1) DEFAULT '2' COMMENT '是否在搜索栏中显示，1是，2否',
  `f_is_grid` tinyint(1) DEFAULT '1' COMMENT '是否在Grid表格中显示，1是，2否',
  `f_is_form` tinyint(1) DEFAULT '1' COMMENT '是否在Form表单中显示，1是，2否',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代码生成规则字段信息';

/*Data for the table `t_tool_generate_rule_field` */

/*Table structure for table `t_tool_generate_rule_table` */

DROP TABLE IF EXISTS `t_tool_generate_rule_table`;

CREATE TABLE `t_tool_generate_rule_table` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_rule_id` int(11) DEFAULT NULL COMMENT '生成规则ID',
  `f_db_name` varchar(125) COLLATE utf8_bin DEFAULT NULL COMMENT '数据库名',
  `f_table_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '数据库表名',
  `f_order` int(11) DEFAULT '1' COMMENT '排序',
  `f_is_main` tinyint(1) DEFAULT '1' COMMENT '是否为主表，1是，2否',
  `f_entity_interface` longtext COLLATE utf8_bin COMMENT 'Java实体类实现的接口列表',
  `f_entity_base_class` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'Java实体类父类的全路径类名',
  `f_entity_class` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'Java实体类的全路径类名',
  `f_mapper_base_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `f_mapper_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `f_service_base_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `f_service_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `f_rest_base_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `f_rest_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代码生成规则数据库表信息';

/*Data for the table `t_tool_generate_rule_table` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
