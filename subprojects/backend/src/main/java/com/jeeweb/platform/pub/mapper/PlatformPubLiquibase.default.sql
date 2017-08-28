-- liquibase formatted



-- changeset 郭浩:20170825175401 runOnChange:true
-- comment: 附件应用服务器存储路径以及附件管理
DELETE FROM t_sys_setting WHERE f_code = 'PLATFORM.PUB.ATTACHMENT.DIRECTORY';

insert into `t_sys_setting` (`f_code`, `f_name`, `f_desc`, `f_order`, `f_is_editable`, `f_field_type`, `f_field_cfg`, `f_init_value`, `f_value`, `f_remark`) values('PLATFORM.PUB.ATTACHMENT.DIRECTORY','附件本地存储目录',NULL,'2','1','String',NULL,'/home/ci/attachment','/home/ci/attachment',NULL);
-- rollback DELETE FROM t_sys_setting WHERE f_code = 'PLATFORM.PUB.ATTACHMENT.DIRECTORY';

-- changeset 郭浩:20170825175402 runOnChange:true
-- comment: 附件管理
DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'KFGJ-FJGL%';
DELETE FROM t_sys_menu WHERE f_id LIKE 'KFGJ-FJGL%';

insert into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-FJGL','KFGJ','/ROOT/KFGJ/',110,'附件管理','附件管理',NULL,2,'/admin/platform/pub/attachment',1,1,1,1,NULL);

insert into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-FJGL','01f4ad0a3f2fe5780d098ad17187e02a');
insert into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-FJGL','4c249aa403de6fc51c53c74962b31315');
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'KFGJ-FJGL%';
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id = 'KFGJ-FJGL';

