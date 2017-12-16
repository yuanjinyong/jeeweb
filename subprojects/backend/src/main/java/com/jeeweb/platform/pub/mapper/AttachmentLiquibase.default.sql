-- liquibase formatted


-- changeset 郭浩:20170819170000
-- comment: 优化附件表结构
DROP TABLE IF EXISTS `t_pub_attachment`;
CREATE TABLE `t_pub_attachment` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID，主键自增',
  `f_tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `f_entity_name` varchar(128) DEFAULT NULL COMMENT '业务实体类名',
  `f_entity_id` bigint(20) DEFAULT NULL COMMENT '业务实体对象ID',
  `f_name` varchar(256) NOT NULL COMMENT '文件名',
  `f_type` varchar(32) DEFAULT NULL COMMENT '文件类型',
  `f_size` bigint(20) DEFAULT NULL COMMENT '字节数',
  `f_local_path` varchar(512) DEFAULT NULL COMMENT '本地存储路径',
  `f_remote_path` varchar(512) DEFAULT NULL COMMENT '远程存储路径',
  `f_creator_id` bigint(20) NOT NULL COMMENT '创建人，系统用户ID',
  `f_created_time` datetime NOT NULL COMMENT '创建时间',
  `f_status` tinyint(3) unsigned NOT NULL COMMENT '状态：101、待归档；102、已归档；103、已上传；104、待删除；',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件归档信息表';
-- rollback DROP TABLE IF EXISTS t_pub_attachment;


