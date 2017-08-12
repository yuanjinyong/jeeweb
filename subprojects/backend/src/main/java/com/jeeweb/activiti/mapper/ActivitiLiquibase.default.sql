-- liquibase formatted



-- changeset 袁进勇:20170807000000
-- comment: 工作流管理
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('GZLGL','ROOT','/ROOT/','800','工作流管理','工作流管理模块','fa fa-random','1',NULL,'1','2','2','1',NULL);
-- rollback DELETE FROM t_sys_menu WHERE f_id = 'GZLGL';


-- changeset 袁进勇:20170807000001
-- comment: 流程模型管理
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('GZLGL-LCMXGL','GZLGL','/ROOT/GZLGL/','10','流程模型管理','流程模型管理页面','fa fa-meetup','2','/admin/activiti/process/model','1','2','2','1',NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('GZLGL-LCMXGL-BJ','GZLGL-LCMXGL','/ROOT/GZLGL/GZLGL-LCMXGL/','40','编辑','编辑流程节点',NULL,'3',NULL,'1','2','2','1',NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('GZLGL-LCMXGL-SC','GZLGL-LCMXGL','/ROOT/GZLGL/GZLGL-LCMXGL/','30','删除','删除流程模型',NULL,'3',NULL,'1','2','2','1',NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('GZLGL-LCMXGL-XG','GZLGL-LCMXGL','/ROOT/GZLGL/GZLGL-LCMXGL/','20','修改','修改流程模型',NULL,'3',NULL,'1','2','2','1',NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('GZLGL-LCMXGL-ZJ','GZLGL-LCMXGL','/ROOT/GZLGL/GZLGL-LCMXGL/','10','增加','增加流程模型',NULL,'3',NULL,'1','2','2','1',NULL);

/*Data for the table `t_sys_menu_url` */
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('GZLGL-LCMXGL','19f724015a1fb097f4e09c3b07ad2bad');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('GZLGL-LCMXGL','96a85a8b1405d380300f787e9813dcd8');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('GZLGL-LCMXGL','cb68695db4af5b997e239bc81bf7cf60');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('GZLGL-LCMXGL','e8d53df51cd333f9644c4bd5ba1d26d0');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('GZLGL-LCMXGL-BJ','fcc787ec71f20d4309b9d803fbd7de67');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('GZLGL-LCMXGL-SC','12c35ec35cc79fb9e63bc33a46b2271c');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('GZLGL-LCMXGL-ZJ','c9bfc2a9eb78149340e65972708a25be');
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'GZLGL-LCMXGL%';
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'GZLGL-LCMXGL%';
