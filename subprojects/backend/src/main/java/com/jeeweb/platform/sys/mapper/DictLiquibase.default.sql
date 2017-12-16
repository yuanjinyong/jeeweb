-- liquibase formatted


-- changeset 袁进勇:20170502000201
-- comment: 创建字典表结构
CREATE TABLE `t_sys_dict` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '字典组编码',
  `f_name` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '字典组名称',
  `f_db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'defaultSqlMapper' COMMENT '字典组对应数据库名',
  `f_table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 't_sys_dict_item' COMMENT '字典组对应数据表名',
  `f_tenant_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_tenant_id' COMMENT '字典项租户ID对应数据库表中字段',
  `f_code_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_code' COMMENT '字典项编码对应数据库表中字段',
  `f_name_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_text' COMMENT '字典项名称对应数据库表中字段',
  `f_order_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_order' COMMENT '字典项排序对应数据库表中字段',
  `f_where_clause` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '拼接到查询SQL语句中的where条件',
  `f_is_preset` tinyint(3) unsigned NOT NULL DEFAULT '101' COMMENT '是否系统预置：101、是；102、否',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_DICT_CODE` (`f_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典组定义表';

CREATE TABLE `t_sys_dict_item` (
  `f_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `f_dict_code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '字典组编码',
  `f_item_order` int(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `f_item_code` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '字典项编码',
  `f_item_text` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '字典项名称',
  `f_is_preset`  tinyint(3) unsigned NOT NULL DEFAULT '101' COMMENT '是否系统预置：101、是；102、否',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_DICT_ITEM` (`f_tenant_id`,`f_dict_code`,`f_item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通用字典项定义表';


-- rollback DROP TABLE IF EXISTS t_sys_dict_item;
-- rollback DROP TABLE IF EXISTS t_sys_dict;


-- changeset 袁进勇:20170810094900 runOnChange:true
-- comment: 增加字典数据
DELETE FROM t_sys_dict_item WHERE f_dict_code = 'BaseClass';
DELETE FROM t_sys_dict WHERE f_code = 'BaseClass';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('BaseClass','实体类可选父类','defaultSqlMapper','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',101,NULL);

insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'BaseClass',0,'com.jeeweb.framework.business.entity.BaseEntity','BaseEntity',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'BaseClass',1,'com.jeeweb.framework.business.entity.TreeNodeEntity','TreeNodeEntity',101);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'BaseClass';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'BaseClass';


DELETE FROM t_sys_dict_item WHERE f_dict_code = 'JavaObjectType';
DELETE FROM t_sys_dict WHERE f_code = 'JavaObjectType';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('JavaObjectType','Java属性类型','defaultSqlMapper','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',101,NULL);

insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',1,'java.lang.String','String',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',2,'java.lang.Boolean','Boolean',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',3,'java.lang.Integer','Integer',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',4,'java.lang.Double','Double',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',5,'java.lang.Byte','Byte',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',6,'java.math.BigDecimal','BigDecimal',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',7,'java.sql.Timestamp','Timestamp',101);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'JavaObjectType';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'JavaObjectType';


DELETE FROM t_sys_dict_item WHERE f_dict_code = 'JdbcDataType';
DELETE FROM t_sys_dict WHERE f_code = 'JdbcDataType';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('JdbcDataType','数据库字段类型','defaultSqlMapper','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',101,NULL);

insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',1,'bigint','bigint',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',2,'blob','blob',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',3,'char','char',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',4,'date','date',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',5,'datetime','datetime',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',6,'decimal','decimal',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',7,'double','double',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',8,'enum','enum',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',9,'float','float',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',10,'int','int',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',11,'longblob','longblob',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',12,'longtext','longtext',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',13,'mediumblob','mediumblob',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',14,'mediumtext','mediumtext',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',15,'set','set',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',16,'smallint','smallint',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',17,'text','text',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',18,'time','time',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',19,'timestamp','timestamp',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',20,'tinyint','tinyint',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',21,'varchar','varchar',101);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'JdbcDataType';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'JdbcDataType';

DELETE FROM t_sys_dict_item WHERE f_dict_code = 'JdbcNullable';
DELETE FROM t_sys_dict WHERE f_code = 'JdbcNullable';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('JdbcNullable','数据库字段是否为空','defaultSqlMapper','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',101,NULL);

insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcNullable',1,'YES','YES',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcNullable',2,'NO','NO',101);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'JdbcNullable';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'JdbcNullable';

DELETE FROM t_sys_dict_item WHERE f_dict_code = 'HttpMethods';
DELETE FROM t_sys_dict WHERE f_code = 'HttpMethods';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('HttpMethods','URL请求Methods','defaultSqlMapper','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order',NULL,101,NULL);

insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'HttpMethods',1,'[]','[]',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'HttpMethods',2,'[DELETE]','[DELETE]',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'HttpMethods',3,'[GET]','[GET]',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'HttpMethods',4,'[POST]','[POST]',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'HttpMethods',5,'[PUT]','[PUT]',101);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'HttpMethods';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'HttpMethods';


DELETE FROM t_sys_dict_item WHERE f_dict_code = 'MenuType';
DELETE FROM t_sys_dict WHERE f_code = 'MenuType';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('MenuType','菜单类型','defaultSqlMapper','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',101,NULL);

insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'MenuType',0,'0','系统菜单',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'MenuType',1,'1','目录',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'MenuType',2,'2','页面',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'MenuType',3,'3','按钮',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'MenuType',4,'4','令牌',101);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'MenuType';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'MenuType';


DELETE FROM t_sys_dict_item WHERE f_dict_code = 'ParameterDataType';
DELETE FROM t_sys_dict WHERE f_code = 'ParameterDataType';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('ParameterDataType','系统参数类型','defaultSqlMapper','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',101,NULL);

insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ParameterDataType',1,'varchar','文本',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ParameterDataType',2,'int','整数',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ParameterDataType',3,'decimal','小数',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ParameterDataType',4,'datetime','日期时间',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ParameterDataType',5,'dict','字典',101);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'ParameterDataType';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'ParameterDataType';


DELETE FROM t_sys_dict_item WHERE f_dict_code = 'TrueFalse';
DELETE FROM t_sys_dict WHERE f_code = 'TrueFalse';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('TrueFalse','布尔型的是否','defaultSqlMapper','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',101,NULL);

insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'TrueFalse',1,'101','是',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'TrueFalse',2,'102','否',101);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'TrueFalse';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'TrueFalse';


DELETE FROM t_sys_dict_item WHERE f_dict_code = 'UserStatus';
DELETE FROM t_sys_dict WHERE f_code = 'UserStatus';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('UserStatus','用户状态','defaultSqlMapper','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',101,NULL);

insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'UserStatus',1,'101','正常',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'UserStatus',2,'102','锁定',101);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'UserStatus',3,'103','注销',101);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'UserStatus';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'UserStatus';


DELETE FROM t_sys_dict_item WHERE f_dict_code = 'UserNameAll';
DELETE FROM t_sys_dict WHERE f_code = 'UserNameAll';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('UserNameAll','操作员列表','defaultSqlMapper','t_sys_user','f_tenant_id','f_id','f_name','f_name','',101,NULL);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'UserNameAll';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'UserNameAll';


DELETE FROM t_sys_dict_item WHERE f_dict_code = 'UserNameActive';
DELETE FROM t_sys_dict WHERE f_code = 'UserNameActive';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('UserNameActive','当前激活的操作员列表','defaultSqlMapper','t_sys_user','f_tenant_id','f_id','f_name','f_name','WHERE f_status IN (1,2)',101,NULL);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'UserNameActive';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'UserNameActive';

