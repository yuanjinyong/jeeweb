-- liquibase formatted


-- changeset 袁进勇:20171204000001
-- comment: 创建 衣服颜色 表结构
CREATE TABLE `t_diy_clothes_color` (
  `f_id` bigint(20) NOT NULL COMMENT 'ID',
  `f_partner_id` bigint(20) NOT NULL COMMENT '关联合作伙伴ID',
  `f_code` varchar(20) NOT NULL COMMENT '颜色代码',
  `f_name` varchar(50) DEFAULT NULL COMMENT '名称',
  `f_desc` text COMMENT '描述',
  `f_remark` longtext COMMENT '备注',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='衣服颜色';
-- rollback DROP TABLE IF EXISTS t_diy_clothes_color;
