-- liquibase formatted


-- changeset 袁进勇:20170502000201
-- comment: 创建字典表结构
CREATE TABLE `t_sys_dict` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '字典组编码',
  `f_name` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '字典组名称',
  `f_db_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'zhuku_master' COMMENT '字典组对应数据库名',
  `f_table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 't_sys_dict_item' COMMENT '字典组对应数据表名',
  `f_tenant_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_tenant_id' COMMENT '字典项租户ID对应数据库表中字段',
  `f_code_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_code' COMMENT '字典项编码对应数据库表中字段',
  `f_name_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_text' COMMENT '字典项名称对应数据库表中字段',
  `f_order_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_order' COMMENT '字典项排序对应数据库表中字段',
  `f_where_clause` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '拼接到查询SQL语句中的where条件',
  `f_is_preset` int(2) NOT NULL DEFAULT '1' COMMENT '是否系统预置，1、是；2、否',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_DICT_CODE` (`f_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典组定义表';

CREATE TABLE `t_sys_dict_item` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_tenant_id` int(11) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `f_dict_code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '字典组编码',
  `f_item_order` int(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `f_item_code` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '字典项编码',
  `f_item_text` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '字典项名称',
  `f_is_preset` int(2) NOT NULL DEFAULT '1' COMMENT '是否系统预置，1、是；2、否',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_DICT_ITEM` (`f_tenant_id`,`f_dict_code`,`f_item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通用字典项定义表';


-- rollback DROP TABLE IF EXISTS t_sys_dict_item;
-- rollback DROP TABLE IF EXISTS t_sys_dict;


-- changeset 袁进勇:20170810094900
-- comment: 增加字典数据
insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('BaseClass','实体类可选父类','zkpms_master','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'BaseClass',0,'com.jeeweb.framework.business.entity.BaseEntity','BaseEntity',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'BaseClass',1,'com.jeeweb.framework.business.entity.TreeNodeEntity','TreeNodeEntity',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'BaseClass';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'BaseClass';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('JavaObjectType','Java属性类型','zkpms_master','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',1,'java.lang.String','String',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',2,'java.lang.Boolean','Boolean',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',3,'java.lang.Integer','Integer',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',4,'java.lang.Double','Double',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',5,'java.lang.Byte','Byte',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',6,'java.math.BigDecimal','BigDecimal',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JavaObjectType',7,'java.sql.Timestamp','Timestamp',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'JavaObjectType';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'JavaObjectType';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('JdbcDataType','数据库字段类型','zkpms_master','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);

insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',1,'bigint','bigint',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',2,'blob','blob',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',3,'char','char',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',4,'date','date',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',5,'datetime','datetime',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',6,'decimal','decimal',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',7,'double','double',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',8,'enum','enum',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',9,'float','float',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',10,'int','int',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',11,'longblob','longblob',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',12,'longtext','longtext',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',13,'mediumblob','mediumblob',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',14,'mediumtext','mediumtext',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',15,'set','set',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',16,'smallint','smallint',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',17,'text','text',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',18,'time','time',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',19,'timestamp','timestamp',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',20,'tinyint','tinyint',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcDataType',21,'varchar','varchar',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'JdbcDataType';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'JdbcDataType';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('JdbcNullable','数据库字段是否为空','zkpms_master','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcNullable',1,'YES','YES',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'JdbcNullable',2,'NO','NO',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'JdbcNullable';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'JdbcNullable';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('HttpMethods','URL请求Methods','zkpms_master','t_sys_dict_item','0','f_item_code','f_item_text','f_item_order',NULL,1,NULL);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'HttpMethods',1,'[]','[]',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'HttpMethods',2,'[DELETE]','[DELETE]',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'HttpMethods',3,'[GET]','[GET]',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'HttpMethods',4,'[POST]','[POST]',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'HttpMethods',5,'[PUT]','[PUT]',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'HttpMethods';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'HttpMethods';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('MenuType','菜单类型','zkpms_master','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'MenuType',0,'0','系统菜单',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'MenuType',1,'1','目录',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'MenuType',2,'2','页面',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'MenuType',3,'3','按钮',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'MenuType',4,'4','令牌',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'MenuType';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'MenuType';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('ParameterDataType','系统参数类型','zkpms_master','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ParameterDataType',1,'varchar','文本',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ParameterDataType',2,'int','整数',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ParameterDataType',3,'decimal','小数',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ParameterDataType',4,'datetime','日期时间',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ParameterDataType',5,'dict','字典',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'ParameterDataType';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'ParameterDataType';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('TrueFalse','布尔型的是否','zkpms_master','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'TrueFalse',1,'true','是',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'TrueFalse',2,'false','否',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'TrueFalse';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'TrueFalse';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('YesNo0','枚举型的是否（0为否）','zkpms_master','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'YesNo0',1,'1','是',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'YesNo0',2,'0','否',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'YesNo0';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'YesNo0';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('YesNo2','枚举型的是否（2为否）','zkpms_master','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'YesNo2',1,'1','是',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'YesNo2',2,'2','否',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'YesNo2';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'YesNo2';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('UserStatus','用户状态','zkpms_master','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'UserStatus',1,'1','正常',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'UserStatus',2,'2','锁定',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'UserStatus',3,'3','注销',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'UserStatus';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'UserStatus';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('UserNameAll','操作员列表','zkpms_master','t_sys_user','f_tenant_id','f_id','f_name','f_name','',1,NULL);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'UserNameAll';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'UserNameAll';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('UserNameActive','当前激活的操作员列表','zkpms_master','t_sys_user','f_tenant_id','f_id','f_name','f_name','WHERE f_status IN (1,2)',1,NULL);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'UserNameActive';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'UserNameActive';

insert  into `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values ('ValidateRules','校验规则','zkpms_master','t_sys_dict_item','0','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ValidateRules',11,'*','必填（非空）',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ValidateRules',12,'*6-16','6到16位任意字符',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ValidateRules',21,'s','字母',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ValidateRules',22,'s6-16','6到16位字符母',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ValidateRules',31,'n','数字',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ValidateRules',32,'n6-16','6到16位数字',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ValidateRules',41,'1','电子邮件',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ValidateRules',42,'2','手机号码',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ValidateRules',43,'3','邮政编码',1);
insert  into `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (0,'ValidateRules',44,'4','URL网址',1);
-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = 'ValidateRules';
-- rollback DELETE FROM t_sys_dict WHERE f_code = 'ValidateRules';

