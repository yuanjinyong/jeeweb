-- liquibase formatted


-- changeset 袁进勇:20170401000002
-- comment: 创建代码生成规则表结构
CREATE TABLE `t_tool_generate_rule_table` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_rule_id` bigint(20) DEFAULT NULL COMMENT '生成规则ID',
  `f_db_name` varchar(125) COLLATE utf8_bin DEFAULT NULL COMMENT '数据库名',
  `f_table_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '数据库表名',
  `f_order` int(11) DEFAULT '1' COMMENT '排序',
  `f_is_main` tinyint(3) unsigned NOT NULL DEFAULT '101' COMMENT '是否为主表：101、是；102、否',
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
-- rollback DROP TABLE IF EXISTS `t_tool_generate_rule_table`;