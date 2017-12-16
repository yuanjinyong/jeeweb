-- liquibase formatted


-- changeset 袁进勇:20170401000003
-- comment: 创建代码生成规则表结构
CREATE TABLE `t_tool_generate_rule_field` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_table_id` bigint(20) NOT NULL COMMENT '数据库表信息ID',
  `f_order` int(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `f_column_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '字段名',
  `f_column_comment` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '字段描述',
  `f_column_type` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '字段类型',
  `f_java_type` varchar(256) COLLATE utf8_bin DEFAULT 'java.lang.String' COMMENT '字段对应Java数据类型',
  `f_is_primary` tinyint(3) unsigned DEFAULT '102' COMMENT '是否为主健：101、是；102、否',
  `f_is_super_class_field` tinyint(3) unsigned DEFAULT '102' COMMENT '是否为父类字段：101、是；102、否',
  `f_is_override_field` tinyint(3) unsigned DEFAULT '102' COMMENT '是否为覆写或实现接口字段：101、是；102、否',
  `f_is_insert` tinyint(3) unsigned DEFAULT '101' COMMENT '是否为插入字段：101、是；102、否',
  `f_is_update` tinyint(3) unsigned DEFAULT '101' COMMENT '是否为更新字段：101、是；102、否',
  `f_is_select` tinyint(3) unsigned DEFAULT '101' COMMENT '是否为查询字段：101、是；102、否',
  `f_is_equal` tinyint(3) unsigned DEFAULT '102' COMMENT '是否为等于查询条件：101、是；102、否',
  `f_is_like` tinyint(3) unsigned DEFAULT '102' COMMENT '是否为左右模糊查询条件：101、是；102、否',
  `f_is_left_like` tinyint(3) unsigned DEFAULT '102' COMMENT '是否为左模糊查询条件：101、是；102、否',
  `f_is_right_like` tinyint(3) unsigned DEFAULT '102' COMMENT '是否为右模糊查询条件：101、是；102、否',
  `f_is_in` tinyint(3) unsigned DEFAULT '102' COMMENT '是否为IN查询条件：101、是；102、否',
  `f_is_not_in` tinyint(3) unsigned DEFAULT '102' COMMENT '是否为NOT IN查询条件：101、是；102、否',
  `f_is_between` tinyint(3) unsigned DEFAULT '102' COMMENT '是否为BETWEEN查询条件：101、是；102、否',
  `f_is_search` tinyint(3) unsigned DEFAULT '102' COMMENT '是否在搜索栏中显示：101、是；102、否',
  `f_is_grid` tinyint(3) unsigned DEFAULT '101' COMMENT '是否在Grid表格中显示：101、是；102、否',
  `f_is_form` tinyint(3) unsigned DEFAULT '101' COMMENT '是否在Form表单中显示：101、是；102、否',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代码生成规则字段信息';
-- rollback DROP TABLE IF EXISTS t_tool_generate_rule_field;