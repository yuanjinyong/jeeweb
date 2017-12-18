-- liquibase formatted


-- changeset 袁进勇:20171206000001
-- comment: 创建 定制店铺 表结构
CREATE TABLE `t_diy_store` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `f_name` varchar(64) NOT NULL COMMENT '名称',
  `f_desc` text COMMENT '说明',
  `f_type` tinyint(3) unsigned NOT NULL COMMENT '店铺类型：101、淘宝C店；102、淘宝企业店；103、天猫店；104、京东店；255、其他',
  `f_remark` longtext COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_STORE` (`f_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定制店铺';
-- rollback DROP TABLE IF EXISTS t_diy_store;
