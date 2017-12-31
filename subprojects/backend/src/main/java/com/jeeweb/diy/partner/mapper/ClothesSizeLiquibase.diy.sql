-- liquibase formatted


-- changeset 袁进勇:20171203000001
-- comment: 创建 衣服尺码 表结构
CREATE TABLE `t_diy_clothes_size` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `f_partner_id` bigint(20) NOT NULL COMMENT '关联合作伙伴ID',
  `f_type` varchar(3) NOT NULL DEFAULT 'A' COMMENT '类型：A、通用；M、男；F、女；C、童',
  `f_size` varchar(8) NOT NULL DEFAULT 'L' COMMENT '尺码：XS、S、M、L、XL、2XL、3XL、4XL、5XL、6XL、7XL、8XL',
  `f_code` varchar(32) NOT NULL COMMENT '尺码编号，通：XS-A、S-A、M-A、L-A、XL-A、2XL-A、3XL-A、4XL-A、5XL-A、6XL-A、7XL-A；男：XS-M、S-M、M-M、L-M、XL-M、2XL-M、3XL-M、4XL-M、5XL-M、6XL-M、7XL-M；女：XS-W、S-W、M-W、L-W、XL-W、2XL-W、3XL-W、4XL-W、5XL-W、6XL-W、7XL-W、8XL-W；童：XS-C、S-C、M-C、L-C、XL-C、2XL-C、3XL-C、4XL-C',
  `f_name` varchar(64) DEFAULT NULL COMMENT '尺码名称',
  `f_desc` text COMMENT '描述',
  `f_remark` longtext COMMENT '备注',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='衣服尺码';
-- rollback DROP TABLE IF EXISTS t_diy_clothes_size;
