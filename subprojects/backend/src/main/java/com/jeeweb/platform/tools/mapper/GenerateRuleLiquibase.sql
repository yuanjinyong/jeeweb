-- liquibase formatted


-- changeset 袁进勇:20170601000001
-- comment: 创建代码生成规则表结构
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
-- rollback DROP TABLE IF EXISTS `t_tool_generate_rule`;