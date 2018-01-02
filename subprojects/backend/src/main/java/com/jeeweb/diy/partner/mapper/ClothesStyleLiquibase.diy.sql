-- liquibase formatted


-- changeset 袁进勇:20171201000002
-- comment: 创建 衣服款式 表结构
CREATE TABLE `t_diy_clothes_style` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `f_partner_id` bigint(20) NOT NULL COMMENT '关联合作伙伴ID',
  `f_code` varchar(50) NOT NULL COMMENT '款式编号',
  `f_name` varchar(80) DEFAULT NULL COMMENT '款式名称',
  `f_desc` text COMMENT '描述',
  `f_cost_price` decimal(13,2) NOT NULL DEFAULT '0.00' COMMENT '成本价，单位元，如果尺码有成本价以尺码的成本价算尺码优先',
  `f_crowd_type` tinyint(3) unsigned DEFAULT '101' COMMENT '款式人群类型：101、通款(不分男女)；102、通款+童装；103、分款(男款女款)；104、分款+童装；105、童装',
  `f_weight` int(11) DEFAULT NULL COMMENT '重量，单位g(克)，用于计算运费',
  `f_remark` longtext COMMENT '备注',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='衣服款式';
-- rollback DROP TABLE IF EXISTS t_diy_clothes_style;
