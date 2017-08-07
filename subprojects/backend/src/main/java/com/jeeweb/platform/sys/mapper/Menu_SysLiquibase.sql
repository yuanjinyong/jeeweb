-- liquibase formatted



-- changeset 袁进勇:20170801010000
-- comment: KFGJ-SJKGL 数据库管理
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-SJKGL','KFGJ','/ROOT/KFGJ/',100,'数据库管理','数据库管理页面','fa fa-table',2,'/admin/platform/tools/information/schema',1,2,2,1,NULL);

/*Data for the table `t_sys_menu_url` */
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-SJKGL','5cf851af135d49453d13f68313176112');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-SJKGL','b6f804b70de1d791ed057678b9748b4e');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-SJKGL','bace82089e32ac0b0c43288a83bd02ac');
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'KFGJ-SJKGL%';
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'KFGJ-SJKGL%';



-- changeset 袁进勇:20170801010001
-- comment: KFGJ-DMSC 代码生成
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




-- changeset 袁进勇:20170801010002
-- comment: URL管理
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL','KFGJ','/ROOT/KFGJ/',30,'URL管理','URL管理页面','fa fa-link',2,'/admin/platform/sys/url',1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL-ZJ','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',10,'增加','增加URL',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL-XG','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',20,'修改','修改URL',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL-SC','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',30,'删除','删除URL',NULL,3,NULL,1,0,0,1,NULL);

/*Data for the table `t_sys_menu_url` */
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL','0c0362062f72869516168ed479fa82bb');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL','d7b9102569dc7cfdc45956fa9cd32ad3');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL-SC','4087eb30e3c4c0dac2765498b3b0cab7');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL-SC','66e99840d588bd859cad21d43a1d8ae3');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL-XG','b73c369d73f83cd700ca2c554f76d504');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL-ZJ','f59ac63ca88503cd64ed14020d1cbead');
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'KFGJ-URLGL%';
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'KFGJ-URLGL%';



-- changeset 袁进勇:20170801010003
-- comment: 菜单管理
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL','KFGJ','/ROOT/KFGJ/',20,'菜单管理','菜单管理页面','fa fa-bars',2,'/admin/platform/sys/menu',1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-ZJ','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',10,'增加','增加菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-XG','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',20,'修改','修改菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-SC','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',30,'删除','删除菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-DCSQL','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',100,'导出SQL','导出SQL脚本',NULL,3,NULL,1,0,0,1,NULL);

/*Data for the table `t_sys_menu_url` */
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','0c0362062f72869516168ed479fa82bb');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','4050f6a3ffd77bdc4534d886e11f8012');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','55847dc87d27c4b9146caf118c45321c');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','d7b9102569dc7cfdc45956fa9cd32ad3');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-DCSQL','d7e533dd5cc473d1f2fcec5557881f91');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-SC','5a91bc0915d3e5c0b2e49d45c1b168ca');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-XG','fed4f0beac39e30f0c8354eaa65fd25f');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-ZJ','a61340a1087d2e7bed041cf57d43a397');
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'KFGJ-CDGL%';
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'KFGJ-CDGL%';



-- changeset 袁进勇:20170801010003
-- comment: 菜单管理
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL','KFGJ','/ROOT/KFGJ/',20,'菜单管理','菜单管理页面','fa fa-bars',2,'/admin/platform/sys/menu',1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-ZJ','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',10,'增加','增加菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-XG','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',20,'修改','修改菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-SC','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',30,'删除','删除菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-DCSQL','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',100,'导出SQL','导出SQL脚本',NULL,3,NULL,1,0,0,1,NULL);

/*Data for the table `t_sys_menu_url` */
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','0c0362062f72869516168ed479fa82bb');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','4050f6a3ffd77bdc4534d886e11f8012');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','55847dc87d27c4b9146caf118c45321c');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','d7b9102569dc7cfdc45956fa9cd32ad3');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-DCSQL','d7e533dd5cc473d1f2fcec5557881f91');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-SC','5a91bc0915d3e5c0b2e49d45c1b168ca');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-XG','fed4f0beac39e30f0c8354eaa65fd25f');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-ZJ','a61340a1087d2e7bed041cf57d43a397');
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'KFGJ-CDGL%';
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'KFGJ-CDGL%';



-- changeset 袁进勇:20170801010004
-- comment: 角色管理
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-JSGL','XTGL','/ROOT/XTGL/',20,'角色管理','角色管理页面','fa fa-flag',2,'/admin/platform/sys/role',1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-JSGL-ZJ','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',10,'增加','增加角色',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-JSGL-XG','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',20,'修改','修改角色',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-JSGL-SC','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',30,'删除','删除角色',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-JSGL-SQ','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',40,'授权','授权可以操作的功能',NULL,3,NULL,1,0,0,1,NULL);

/*Data for the table `t_sys_menu_url` */
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-JSGL','0eadc8c7c022f13cdedaa7e7413a0fe1');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-JSGL','2f12fc88084ea062e3bdde086342a323');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-JSGL','83b5dcfd8eae19d04a703d55c507f35b');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-JSGL-SC','eace04d50010a26a1e37225f2e203b26');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-JSGL-SQ','2d32ed761f669dfccc7ef6fbff98a9b6');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-JSGL-XG','d5d29576cc3167cbdb8fe3a0b2e9cb58');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-JSGL-ZJ','fa4524d97c93bb8f196782d4a66ecdd0');

/*Data for the table `t_sys_role_menu` */
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-JSGL');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-JSGL-SC');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-JSGL-SQ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-JSGL-XG');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-JSGL-ZJ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-JSGL');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-JSGL-SC');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-JSGL-SQ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-JSGL-XG');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-JSGL-ZJ');
-- rollback DELETE FROM t_sys_role_menu WHERE f_menu_id LIKE 'XTGL-JSGL%';
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'XTGL-JSGL%';
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'XTGL-JSGL%';



-- changeset 袁进勇:20170801010005
-- comment: XTGL-YHGL 用户管理
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL','XTGL','/ROOT/XTGL/',30,'用户管理','用户管理页面','fa fa-user',2,'/admin/platform/sys/user',1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-ZJ','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',10,'增加','增加用户',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-XG','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',20,'修改','修改用户',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-SC','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',30,'注销','注销用户',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-SQ','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',40,'授权','授权可以操作的功能',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-CZMM','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',50,'重置密码','重置用户密码',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-JS','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',60,'解锁','解锁用户',NULL,3,NULL,1,2,2,1,NULL);

/*Data for the table `t_sys_menu_url` */
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-YHGL','2f12fc88084ea062e3bdde086342a323');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-YHGL','954c06cf2c5133fb3bdf244d26955a14');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-YHGL','c2e253ad345ea3998b90df4e6e97e356');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-YHGL','d44c436777a17f4868813efbbb2ffffb');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-YHGL-CZMM','394365a60a740d7c0527badd4b964897');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-YHGL-JS','c2d22998a0d5e0fac6400a86e9a14a3f');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-YHGL-SC','ff40e182cbb096c35d1e4ad60c3eab09');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-YHGL-SQ','30d0c0b3dbcb1124cd1d5b0bbd514dde');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-YHGL-XG','b91a4be3d763864cbc7efe78010e3980');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-YHGL-ZJ','b640c19e353f49757e9fa7991b62ed7a');

/*Data for the table `t_sys_role_menu` */
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-YHGL');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-YHGL-CZMM');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-YHGL-JS');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-YHGL-SC');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-YHGL-SQ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-YHGL-XG');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-YHGL-ZJ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-YHGL');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-YHGL-CZMM');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-YHGL-JS');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-YHGL-SC');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-YHGL-SQ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-YHGL-XG');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-YHGL-ZJ');
-- rollback DELETE FROM t_sys_role_menu WHERE f_menu_id LIKE 'XTGL-YHGL%';
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'XTGL-YHGL%';
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'XTGL-YHGL%';



-- changeset 袁进勇:20170801010006
-- comment: XTGL-ZDGL 字典管理
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-ZDGL','XTGL','/ROOT/XTGL/',60,'字典管理','字典管理页面','fa fa-book',2,'/admin/platform/sys/dict',1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-ZDGL-ZJ','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',10,'增加','增加字典组',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-ZDGL-XG','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',20,'修改','修改字典组',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-ZDGL-SC','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',30,'删除','删除字典组',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-ZDGL-DCSQL','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',40,'导出SQL','导出SQL脚本',NULL,3,NULL,1,2,2,1,NULL);

/*Data for the table `t_sys_menu_url` */
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-ZDGL','247045bb9c8412526f9ff48b9fc4b98b');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-ZDGL','fb8f9ccaad4855f891ec1c4521f6e51d');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-ZDGL-SC','0e0b103069ec1bef20429d38dfaeae38');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-ZDGL-XG','de1613a5a8f4f669e345085786e60831');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-ZDGL-ZJ','a7e30f1ae19ec6b6bde9a7d9efbd5f82');

/*Data for the table `t_sys_role_menu` */
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-ZDGL');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-ZDGL-DCSQL');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-ZDGL-SC');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-ZDGL-XG');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-ZDGL-ZJ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-ZDGL');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-ZDGL-DCSQL');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-ZDGL-SC');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-ZDGL-XG');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-ZDGL-ZJ');
-- rollback DELETE FROM t_sys_role_menu WHERE f_menu_id LIKE 'XTGL-ZDGL%';
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'XTGL-ZDGL%';
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'XTGL-ZDGL%';



-- changeset 袁进勇:20170801010007
-- comment: XTGL-XTSZ 系统设置
/*Data for the table `t_sys_menu` */
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ','XTGL','/ROOT/XTGL/',70,'系统设置','系统设置页面','fa fa-wrench',2,'/admin/platform/sys/setting',1,2,2,1,'系统设置（参数）管理功能。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ-ZJ','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',10,'增加','增加系统设置项',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ-XG','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',20,'修改','修改系统设置项',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ-SC','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',30,'删除','删除系统设置项',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ-DCSQL','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',40,'导出SQL','导出SQL脚本',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ-SZQZ','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',50,'设置取值','设置系统设置项的取值',NULL,3,NULL,1,2,2,1,NULL);

/*Data for the table `t_sys_menu_url` */
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-XTSZ','8ba694590f475c4d3e2faffca32820de');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-XTSZ','f1255871f4c57f850378235d3b556a5c');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-XTSZ-SC','3139c2251a87ef1c11c42638491c968f');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-XTSZ-XG','73fad798792127d298e178a9c6c20ba3');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('XTGL-XTSZ-ZJ','b403fa836b154a9a8456d631f2d7f5ca');

/*Data for the table `t_sys_role_menu` */
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-XTSZ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-XTSZ-DCSQL');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-XTSZ-SC');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-XTSZ-SZQZ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-XTSZ-XG');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (1,'XTGL-XTSZ-ZJ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-XTSZ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-XTSZ-DCSQL');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-XTSZ-SC');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-XTSZ-SZQZ');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-XTSZ-XG');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (3,'XTGL-XTSZ-ZJ');
-- rollback DELETE FROM t_sys_role_menu WHERE f_menu_id LIKE 'XTGL-XTSZ%';
-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE 'XTGL-XTSZ%';
-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE 'XTGL-XTSZ%';

