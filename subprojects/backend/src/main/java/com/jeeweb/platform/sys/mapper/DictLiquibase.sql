-- liquibase formatted


-- changeset 袁进勇:20170802000201
CREATE TABLE `t_sys_dict` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '字典组编码',
  `f_name` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '字典组名称',
  `f_db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'zhuku_master' COMMENT '字典组对应数据库名',
  `f_table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 't_sys_dict_item' COMMENT '字典组对应数据表名',
  `f_tenant_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_tenant_id' COMMENT '字典项租户ID对应数据库表中字段',
  `f_code_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_code' COMMENT '字典项编码对应数据库表中字段',
  `f_name_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_text' COMMENT '字典项名称对应数据库表中字段',
  `f_order_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_order' COMMENT '字典项排序对应数据库表中字段',
  `f_where_clause` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '拼接到查询SQL语句中的where条件',
  `f_is_preset` int(2) NOT NULL DEFAULT '1' COMMENT '是否系统预置，1、是；2、否',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_DICT_CODE` (`f_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典组定义表';

CREATE TABLE `t_sys_dict_item` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_tenant_id` int(11) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `f_dict_code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '字典组编码',
  `f_item_order` int(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `f_item_code` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '字典项编码',
  `f_item_text` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '字典项名称',
  `f_is_preset` int(2) NOT NULL DEFAULT '1' COMMENT '是否系统预置，1、是；2、否',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_DICT_ITEM` (`f_tenant_id`,`f_dict_code`,`f_item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通用字典项定义表';


-- rollback DROP TABLE IF EXISTS t_sys_dict_item;
-- rollback DROP TABLE IF EXISTS t_sys_dict;