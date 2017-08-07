-- liquibase formatted


-- changeset 袁进勇:20170702000001
-- comment: 创建部门表结构
CREATE TABLE `t_sys_department` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_tenant_id` int(11) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `f_parent_id` int(11) DEFAULT NULL COMMENT '父级部门',
  `f_parent_path` varchar(1024) COLLATE utf8_bin NOT NULL COMMENT '树形结构的路径，以“/”开头、分隔和结尾。',
  `f_order` int(11) NOT NULL DEFAULT '10' COMMENT '同一个父级部门下的排序',
  `f_name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `f_desc` text COLLATE utf8_bin COMMENT '描述',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  KEY `SYS_DEPT_PARENT_ID` (`f_parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='部门表';

insert  into `t_sys_department`(`f_id`,`f_tenant_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_remark`) values (0,0,NULL,'/',0,'系统组织机构','根组织机构，系统预置数据，不能修改和删除。',NULL);
-- rollback DROP TABLE IF EXISTS t_sys_department;
