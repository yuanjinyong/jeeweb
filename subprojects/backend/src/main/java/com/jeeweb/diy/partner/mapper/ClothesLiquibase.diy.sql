-- liquibase formatted


-- changeset 袁进勇:20171205000001
-- comment: 创建 衣服 表结构
CREATE TABLE `t_diy_clothes` (
  `f_id` bigint(20) NOT NULL COMMENT 'ID',
  `f_partner_id` bigint(20) NOT NULL COMMENT '关联合作伙伴ID',
  `f_style_id` bigint(20) NOT NULL COMMENT '关联衣服款式ID',
  `f_size_id` bigint(20) NOT NULL COMMENT '关联衣服尺码ID',
  `f_color_id` bigint(20) NOT NULL COMMENT '关联衣服颜色ID',
  `f_cost_price` decimal(13,2) DEFAULT NULL COMMENT '成本价，为空时，取衣服款式的成本价',
  `f_desc` text COMMENT '描述',
  `f_creator_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人ID',
  `f_created_time` datetime NOT NULL COMMENT '创建时间',
  `f_remark` longtext COMMENT '备注',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='衣服';
-- rollback DROP TABLE IF EXISTS t_diy_clothes;
