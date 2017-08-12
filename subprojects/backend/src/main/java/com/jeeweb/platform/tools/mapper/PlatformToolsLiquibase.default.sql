-- liquibase formatted



-- changeset 袁进勇:20170705010000
-- comment: 数据库管理
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-SJKGL','KFGJ','/ROOT/KFGJ/',100,'数据库管理','数据库管理页面','fa fa-table',2,'/admin/platform/tools/information/schema',1,2,2,1,NULL);

/*Data for the table `t_sys_menu_url` */
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-SJKGL','5cf851af135d49453d13f68313176112');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-SJKGL','b6f804b70de1d791ed057678b9748b4e');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-SJKGL','bace82089e32ac0b0c43288a83bd02ac');
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'KFGJ-SJKGL%';
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'KFGJ-SJKGL%';



-- changeset 袁进勇:20170705010001
-- comment: 代码生成
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-DMSC','KFGJ','/ROOT/KFGJ/',40,'代码生成','代码生成规则配置',NULL,2,'/admin/platform/tools/code/generation',1,1,1,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-DMSC-ZJ','KFGJ-DMSC','/ROOT/KFGJ/KFGJ-DMSC/',10,'增加','增加代码生成规则',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-DMSC-XG','KFGJ-DMSC','/ROOT/KFGJ/KFGJ-DMSC/',20,'修改','修改代码生成规则',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-DMSC-SC','KFGJ-DMSC','/ROOT/KFGJ/KFGJ-DMSC/',30,'删除','删除代码生成规则',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-DMSC-SCDM','KFGJ-DMSC','/ROOT/KFGJ/KFGJ-DMSC/',40,'生成代码','生成代码',NULL,3,NULL,1,2,2,1,NULL);

/*Data for the table `t_sys_menu_url` */
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-DMSC','501a43823e58e14860b498ba282d3aea');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-DMSC','c4be57739389e79d93e5b3b5e3f9e8cc');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-DMSC-SC','4f183e1230ff79c37a2fc9a5ec2bde73');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-DMSC-SCDM','ce3da5c6e39ce2e43fef92bc659f909e');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-DMSC-XG','f9505ca328c81c026890995a61218838');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-DMSC-ZJ','acb254d89b6dbc46d6e6ab944a16292b');
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'KFGJ-DMSC%';
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'KFGJ-DMSC%';

