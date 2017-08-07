-- liquibase formatted


-- changeset 袁进勇:20170802000101
-- comment: 创建系统设置（参数）表结构
CREATE TABLE `t_sys_setting` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `f_code` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '编码',
  `f_name` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `f_desc` text COLLATE utf8_bin COMMENT '描述',
  `f_order` int(11) NOT NULL DEFAULT '999999999' COMMENT '排序',
  `f_is_editable` int(11) NOT NULL DEFAULT '1' COMMENT '是否开放给客户编辑，1、是；2、否',
  `f_field_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '输入控件类型，比如varchar，int，decimal，datetime，dict',
  `f_field_cfg` text COLLATE utf8_bin COMMENT '输入控件配置',
  `f_init_value` text COLLATE utf8_bin NOT NULL COMMENT '出厂值',
  `f_value` text COLLATE utf8_bin NOT NULL COMMENT '当前值',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_SETTINGS_CODE` (`f_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统设置（参数）表';
-- rollback DROP TABLE IF EXISTS t_sys_setting;


-- changeset 袁进勇:20170802000102
-- comment: 预置系统设置（参数）项
/*Data for the table `t_sys_setting` */
insert into `t_sys_setting` (`f_id`, `f_code`, `f_name`, `f_desc`, `f_order`, `f_is_editable`, `f_field_type`, `f_field_cfg`, `f_init_value`, `f_value`, `f_remark`) values('1','ZKPMS.MASTER.COMPANY.REGISTER.AUDIT','企业注册是否需要审核','1、是；2、否。','1','1','String',NULL,'1','2','为了方便推广，现在企业注册暂时设置为不需要审核。');
-- rollback DELETE FROM t_sys_setting WHERE f_id IN (1);

