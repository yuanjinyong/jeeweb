-- liquibase formatted


-- changeset 袁进勇:20171201000001
-- comment: 创建 合作伙伴 表结构
CREATE TABLE `t_diy_partner` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `f_name` varchar(256) NOT NULL COMMENT '名称',
  `f_desc` text COMMENT '描述',
  `f_leader` varchar(80) DEFAULT NULL COMMENT '负责人姓名',
  `f_phone` varchar(50) DEFAULT NULL COMMENT '固定电话',
  `f_mobile` varchar(15) DEFAULT NULL COMMENT '手机号码',
  `f_address` varchar(256) DEFAULT NULL COMMENT '地址',
  `f_creator_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `f_created_time` datetime NOT NULL COMMENT '创建时间',
  `f_status` tinyint(3) unsigned NOT NULL DEFAULT '101' COMMENT '状态：101、正常合作；102、暂停合作',
  `f_remark` longtext COMMENT '备注',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合作伙伴';
-- rollback DROP TABLE IF EXISTS t_diy_partner;
