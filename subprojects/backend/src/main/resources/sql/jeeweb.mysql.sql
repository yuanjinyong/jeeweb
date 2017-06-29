/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.10-log : Database - jeeweb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `t_sys_department` */

DROP TABLE IF EXISTS `t_sys_department`;

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

/*Data for the table `t_sys_department` */

insert  into `t_sys_department`(`f_id`,`f_tenant_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_remark`) values (0,0,NULL,'/',0,'系统组织机构','根组织机构，系统预置数据，不能修改和删除。',NULL);

/*Table structure for table `t_sys_dict` */

DROP TABLE IF EXISTS `t_sys_dict`;

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

/*Data for the table `t_sys_dict` */

insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (1,'BaseClass','实体类可选父类','jeeweb','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (2,'JavaObjectType','Java属性类型','jeeweb','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (3,'JdbcDataType','数据库字段类型','jeeweb','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (4,'JdbcNullable','数据库字段是否为空','jeeweb','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (5,'MenuType','菜单类型','jeeweb','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (6,'ParameterDataType','系统参数类型','jeeweb','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (10,'TrueFalse','布尔型的是否','jeeweb','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (11,'UserStatus','用户状态','jeeweb','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (12,'YesNo0','枚举型的是否（0为否）','jeeweb','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (13,'YesNo2','枚举型的是否（2为否）','jeeweb','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (14,'UserNameAll','操作员列表','jeeweb','t_sys_user','f_tenant_id','f_id','f_name','f_name','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (15,'UserNameActive','当前激活的操作员列表','jeeweb','t_sys_user','f_tenant_id','f_id','f_name','f_name','WHERE f_status IN (1,2)',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (18,'ValidateRules','校验规则','jeeweb','t_sys_dict_item','0','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (19,'CompanyType','企业类型','jeeweb','t_sys_dict_item','0','f_item_code','f_item_text','f_item_order',NULL,1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (20,'CompanyStatus','企业状态','jeeweb','t_sys_dict_item','0','f_item_code','f_item_text','f_item_order',NULL,1,NULL);
insert  into `t_sys_dict`(`f_id`,`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) values (21,'HttpMethods','URL请求Methods','jeeweb','t_sys_dict_item','0','f_item_code','f_item_text','f_item_order',NULL,1,NULL);

/*Table structure for table `t_sys_dict_item` */

DROP TABLE IF EXISTS `t_sys_dict_item`;

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

/*Data for the table `t_sys_dict_item` */

insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (5,0,'JavaObjectType',1,'java.lang.String','String',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (6,0,'JavaObjectType',2,'java.lang.Boolean','Boolean',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (7,0,'JavaObjectType',3,'java.lang.Integer','Integer',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (8,0,'JavaObjectType',4,'java.lang.Double','Double',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (9,0,'JavaObjectType',5,'java.lang.Byte','Byte',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (10,0,'JavaObjectType',6,'java.math.BigDecimal','BigDecimal',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (11,0,'JavaObjectType',7,'java.sql.Timestamp','Timestamp',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (12,0,'JdbcDataType',1,'bigint','bigint',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (13,0,'JdbcDataType',2,'blob','blob',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (14,0,'JdbcDataType',3,'char','char',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (15,0,'JdbcDataType',4,'date','date',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (16,0,'JdbcDataType',5,'datetime','datetime',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (17,0,'JdbcDataType',6,'decimal','decimal',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (18,0,'JdbcDataType',7,'double','double',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (19,0,'JdbcDataType',8,'enum','enum',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (20,0,'JdbcDataType',9,'float','float',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (21,0,'JdbcDataType',10,'int','int',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (22,0,'JdbcDataType',11,'longblob','longblob',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (23,0,'JdbcDataType',12,'longtext','longtext',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (24,0,'JdbcDataType',13,'mediumblob','mediumblob',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (25,0,'JdbcDataType',14,'mediumtext','mediumtext',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (26,0,'JdbcDataType',15,'set','set',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (27,0,'JdbcDataType',16,'smallint','smallint',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (28,0,'JdbcDataType',17,'text','text',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (29,0,'JdbcDataType',18,'time','time',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (30,0,'JdbcDataType',19,'timestamp','timestamp',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (31,0,'JdbcDataType',20,'tinyint','tinyint',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (32,0,'JdbcDataType',21,'varchar','varchar',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (33,0,'JdbcNullable',1,'YES','YES',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (34,0,'JdbcNullable',2,'NO','NO',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (39,0,'ParameterDataType',1,'varchar','文本',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (40,0,'ParameterDataType',2,'int','整数',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (41,0,'ParameterDataType',3,'decimal','小数',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (42,0,'ParameterDataType',4,'datetime','日期时间',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (43,0,'ParameterDataType',5,'dict','字典',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (56,0,'TrueFalse',1,'true','是',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (57,0,'TrueFalse',2,'false','否',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (58,0,'UserStatus',1,'1','正常',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (59,0,'UserStatus',2,'2','锁定',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (60,0,'UserStatus',3,'3','注销',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (61,0,'YesNo0',1,'1','是',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (62,0,'YesNo0',2,'0','否',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (63,0,'YesNo2',1,'1','是',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (64,0,'YesNo2',2,'2','否',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (67,0,'ValidateRules',11,'*','必填（非空）',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (68,0,'ValidateRules',12,'*6-16','6到16位任意字符',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (69,0,'ValidateRules',21,'s','字母',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (70,0,'ValidateRules',22,'s6-16','6到16位字符母',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (71,0,'ValidateRules',31,'n','数字',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (72,0,'ValidateRules',32,'n6-16','6到16位数字',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (73,0,'ValidateRules',41,'1','电子邮件',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (74,0,'ValidateRules',42,'2','手机号码',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (75,0,'ValidateRules',43,'3','邮政编码',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (76,0,'ValidateRules',44,'4','URL网址',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (85,0,'BaseClass',0,'com.zhuku.framework.business.entity.BaseEntity','BaseEntity',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (86,0,'BaseClass',1,'com.zhuku.framework.business.entity.TreeNodeEntity','TreeNodeEntity',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (98,0,'MenuType',0,'0','系统菜单',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (99,0,'MenuType',1,'1','目录',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (100,0,'MenuType',2,'2','页面',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (101,0,'MenuType',3,'3','按钮',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (102,0,'MenuType',4,'4','令牌',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (103,0,'CompanyType',1,'1','施工方',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (104,0,'CompanyType',2,'2','供应商',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (105,0,'CompanyType',3,'3','业主',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (106,0,'CompanyStatus',1,'1','待审核',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (107,0,'CompanyStatus',2,'2','正常',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (108,0,'CompanyStatus',3,'3','审核未通过',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (109,0,'CompanyStatus',4,'4','注销',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (110,0,'HttpMethods',1,'[]','[]',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (111,0,'HttpMethods',2,'[DELETE]','[DELETE]',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (112,0,'HttpMethods',3,'[GET]','[GET]',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (113,0,'HttpMethods',4,'[POST]','[POST]',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) values (114,0,'HttpMethods',5,'[PUT]','[PUT]',1);

/*Table structure for table `t_sys_menu` */

DROP TABLE IF EXISTS `t_sys_menu`;

CREATE TABLE `t_sys_menu` (
  `f_id` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '主键，以模块加横杠分隔，如 XTGL-QXGL 表示系统管理-权限管理',
  `f_parent_id` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '父级菜单',
  `f_parent_path` varchar(1024) COLLATE utf8_bin NOT NULL COMMENT '树形结构的路径，以“/”开头、分隔和结尾。',
  `f_order` int(11) NOT NULL DEFAULT '10' COMMENT '排序',
  `f_name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `f_desc` text COLLATE utf8_bin COMMENT '菜单描述',
  `f_icon` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `f_type` int(11) NOT NULL DEFAULT '3' COMMENT '类型，1目录、2页面、3按钮、4令牌',
  `f_route_path` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单对应的URL，目录和按钮不需要填写，只有对应页面的菜单才需要填写',
  `f_is_web` int(11) DEFAULT '1' COMMENT 'Web端是否启用，1启用，2不启用',
  `f_is_android` int(11) DEFAULT '1' COMMENT 'Android端是否启用，1启用，2不启用',
  `f_is_ios` int(11) DEFAULT '1' COMMENT 'IOS端是否启用，1启用，2不启用',
  `f_status` int(11) DEFAULT '1' COMMENT '状态，1、启用；2、禁用',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  KEY `SYS_MENU_PARENT_ID` (`f_parent_id`),
  KEY `SYS_MENU_IS_SHOW` (`f_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单（权限）表';

/*Data for the table `t_sys_menu` */

insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('GRZX','ROOT','/ROOT/',10,'个人中心','个人中心模块','fa fa-user-circle',1,NULL,1,1,1,1,'日常工作常用功能的快捷入口。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ','ROOT','/ROOT/',995,'开发人员工具','开发人员工具模块','fa fa-wrench',1,NULL,1,0,0,1,'仅限开发人员使用，请不要授权给客户使用。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL','KFGJ','/ROOT/KFGJ/',20,'菜单管理','菜单管理页面','fa fa-bars',2,'/admin/platform/sys/menu',1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-DCSQL','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',100,'导出SQL','导出SQL脚本',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-SC','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',30,'删除','删除菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-XG','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',20,'修改','修改菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-ZJ','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',10,'增加','增加菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-DMSC','KFGJ','/ROOT/KFGJ/',40,'代码生成','代码生成规则配置',NULL,2,'/admin/platform/tools/code/generation',1,1,1,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-DMSC-SC','KFGJ-DMSC','/ROOT/KFGJ/KFGJ-DMSC/',30,'删除','删除代码生成规则',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-DMSC-SCDM','KFGJ-DMSC','/ROOT/KFGJ/KFGJ-DMSC/',40,'生成代码','生成代码',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-DMSC-XG','KFGJ-DMSC','/ROOT/KFGJ/KFGJ-DMSC/',20,'修改','修改代码生成规则',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-DMSC-ZJ','KFGJ-DMSC','/ROOT/KFGJ/KFGJ-DMSC/',10,'增加','增加代码生成规则',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-SJKGL','KFGJ','/ROOT/KFGJ/',100,'数据库管理','数据库管理页面','fa fa-table',2,'/admin/platform/tools/information/schema',1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL','KFGJ','/ROOT/KFGJ/',30,'URL管理','URL管理页面','fa fa-link',2,'/admin/platform/sys/url',1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL-SC','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',30,'删除','删除URL',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL-XG','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',20,'修改','修改URL',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL-ZJ','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',10,'增加','增加URL',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYGL','ROOT','/ROOT/',100,'企业管理','企业管理模块','fa fa-creative-commons',1,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYGL-QYXXGL','QYGL','/ROOT/QYGL/',50,'企业信息管理','企业信息管理页面','fa fa-copyright',2,'/admin/zkpms/master/company',1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYGL-QYXXGL-SH','QYGL-QYXXGL','/ROOT/QYGL/QYGL-QYXXGL/',30,'审核','审核企业信息',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYGL-QYXXGL-XG','QYGL-QYXXGL','/ROOT/QYGL/QYGL-QYXXGL/',20,'修改','修改企业信息',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYGL-SJYGL','QYGL','/ROOT/QYGL/',40,'企业库数据源管理','企业库数据源管理页面','fa fa-table',2,'/admin/zkpms/master/datasource',1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYGL-SJYGL-XG','QYGL-SJYGL','/ROOT/QYGL/QYGL-SJYGL/',20,'修改','修改数据源',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYXTGL','ROOT','/ROOT/',200,'系统管理','系统管理模块','fa fa-sun-o',1,NULL,1,1,1,1,'企业系统管理。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYXTGL-JCSJ','QYXTGL','/ROOT/QYXTGL/',90,'基础数据管理','基础数据管理模块','fa fa-futbol-o',1,NULL,1,1,1,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYXTGL-QYXXWH','QYXTGL','/ROOT/QYXTGL/',100,'企业信息维护','企业信息维护页面','fa fa-copyright',2,'/admin/zkpms/master/sys/company',1,1,1,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYXTGL-QYYHSP','QYXTGL','/ROOT/QYXTGL/',110,'加入申请审批','加入申请审批页面','fa fa-check-circle-o',2,'/admin/zkpms/master/sys/user',1,1,1,1,'用户申请加入企业后，企业审批是否同意其加入。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYXTGL-ZZRY','QYXTGL','/ROOT/QYXTGL/',80,'组织人员管理','组织人员管理模块','fa fa-id-badge',1,NULL,1,1,1,1,'包含企业部门、岗位、员工的管理。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYXTGL-ZZRY-BMGL','QYXTGL-ZZRY','/ROOT/QYXTGL/QYXTGL-ZZRY/',10,'部门管理','部门管理页面','fa fa-sitemap',2,'/admin/zkpms/company/sys/department',1,1,1,1,'用于维护企业组织机构。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYXTGL-ZZRY-GWGL','QYXTGL-ZZRY','/ROOT/QYXTGL/QYXTGL-ZZRY/',20,'岗位管理','岗位管理页面','fa fa-flag',2,'/admin/zkpms/company/sys/post',1,1,1,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('QYXTGL-ZZRY-YGGL','QYXTGL-ZZRY','/ROOT/QYXTGL/QYXTGL-ZZRY/',30,'员工管理','员工管理页面','fa fa-user',2,'/admin/zkpms/company/sys/employee',1,1,1,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('ROOT',NULL,'/',0,'系统菜单','根菜单',NULL,0,NULL,1,1,1,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL','ROOT','/ROOT/',990,'系统管理','系统管理模块','fa fa-cog',1,NULL,1,1,1,1,'平台系统管理。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-JSGL','XTGL','/ROOT/XTGL/',20,'角色管理','角色管理页面','fa fa-flag',2,'/admin/platform/sys/role',1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-JSGL-SC','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',30,'删除','删除角色',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-JSGL-SQ','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',40,'授权','授权可以操作的功能',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-JSGL-XG','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',20,'修改','修改角色',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-JSGL-ZJ','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',10,'增加','增加角色',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ','XTGL','/ROOT/XTGL/',70,'系统设置','系统设置页面','fa fa-wrench',2,'/admin/platform/sys/setting',1,2,2,1,'系统设置（参数）管理功能。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ-DCSQL','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',40,'导出SQL','导出SQL脚本',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ-SC','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',30,'删除','删除系统设置项',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ-SZQZ','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',50,'设置取值','设置系统设置项的取值',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ-XG','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',20,'修改','修改系统设置项',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-XTSZ-ZJ','XTGL-XTSZ','/ROOT/XTGL/XTGL-XTSZ/',10,'增加','增加系统设置项',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL','XTGL','/ROOT/XTGL/',30,'用户管理','用户管理页面','fa fa-user',2,'/admin/platform/sys/user',1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-CZMM','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',50,'重置密码','重置用户密码',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-JS','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',60,'解锁','解锁用户',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-SC','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',30,'删除','删除用户',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-SQ','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',40,'授权','授权可以操作的功能',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-XG','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',20,'修改','修改用户',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-YHGL-ZJ','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',10,'增加','增加用户',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-ZDGL','XTGL','/ROOT/XTGL/',60,'字典管理','字典管理页面','fa fa-book',2,'/admin/platform/sys/dict',1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-ZDGL-DCSQL','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',40,'导出SQL','导出SQL脚本',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-ZDGL-SC','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',30,'删除','删除字典组',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-ZDGL-XG','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',20,'修改','修改字典组',NULL,3,NULL,1,2,2,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL-ZDGL-ZJ','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',10,'增加','增加字典组',NULL,3,NULL,1,2,2,1,NULL);

/*Table structure for table `t_sys_menu_url` */

DROP TABLE IF EXISTS `t_sys_menu_url`;

CREATE TABLE `t_sys_menu_url` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_menu_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单ID，关联t_sys_menu表的f_id',
  `f_url_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'URL的ID，关联t_sys_url表的f_id',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNQ_MENU_URL` (`f_menu_id`,`f_url_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单可以访问的URL地址';

/*Data for the table `t_sys_menu_url` */

insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (3,'KFGJ-CDGL','0c0362062f72869516168ed479fa82bb');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (1,'KFGJ-CDGL','4050f6a3ffd77bdc4534d886e11f8012');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (2,'KFGJ-CDGL','55847dc87d27c4b9146caf118c45321c');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (4,'KFGJ-CDGL','d7b9102569dc7cfdc45956fa9cd32ad3');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (43,'KFGJ-CDGL-DCSQL','d7e533dd5cc473d1f2fcec5557881f91');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (6,'KFGJ-CDGL-SC','5a91bc0915d3e5c0b2e49d45c1b168ca');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (7,'KFGJ-CDGL-XG','fed4f0beac39e30f0c8354eaa65fd25f');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (8,'KFGJ-CDGL-ZJ','a61340a1087d2e7bed041cf57d43a397');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (55,'KFGJ-DMSC','501a43823e58e14860b498ba282d3aea');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (56,'KFGJ-DMSC','c4be57739389e79d93e5b3b5e3f9e8cc');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (65,'KFGJ-DMSC-SC','4f183e1230ff79c37a2fc9a5ec2bde73');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (66,'KFGJ-DMSC-SCDM','ce3da5c6e39ce2e43fef92bc659f909e');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (64,'KFGJ-DMSC-XG','f9505ca328c81c026890995a61218838');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (63,'KFGJ-DMSC-ZJ','acb254d89b6dbc46d6e6ab944a16292b');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (58,'KFGJ-SJKGL','5cf851af135d49453d13f68313176112');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (59,'KFGJ-SJKGL','b6f804b70de1d791ed057678b9748b4e');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (57,'KFGJ-SJKGL','bace82089e32ac0b0c43288a83bd02ac');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (9,'KFGJ-URLGL','0c0362062f72869516168ed479fa82bb');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (10,'KFGJ-URLGL','d7b9102569dc7cfdc45956fa9cd32ad3');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (12,'KFGJ-URLGL-SC','4087eb30e3c4c0dac2765498b3b0cab7');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (11,'KFGJ-URLGL-SC','66e99840d588bd859cad21d43a1d8ae3');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (13,'KFGJ-URLGL-XG','b73c369d73f83cd700ca2c554f76d504');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (14,'KFGJ-URLGL-ZJ','f59ac63ca88503cd64ed14020d1cbead');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (102,'QYGL-QYXXGL','43ed570de1d66bb5ee760112181bb2bf');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (101,'QYGL-QYXXGL','5312ba0c5e8c972ebab536cdfd5c686c');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (106,'QYGL-QYXXGL-SH','b94a85025e764299b55e2f17f1406d40');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (105,'QYGL-QYXXGL-XG','a646465e5e2bf9785ebce9bf7bd8e46b');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (107,'QYGL-SJYGL','0a500a4ef872c0da332adfae737caf03');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (108,'QYGL-SJYGL','7aafa152872e021e5d5d11ce6689dcec');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (109,'QYGL-SJYGL-XG','e3068b381c8a5eaebc083fd85a040af2');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (91,'QYXTGL-QYXXWH','43ed570de1d66bb5ee760112181bb2bf');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (92,'QYXTGL-QYXXWH','a646465e5e2bf9785ebce9bf7bd8e46b');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (95,'QYXTGL-QYYHSP','0dd5d9d6c0cac5fe0060b1e18a9d0330');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (96,'QYXTGL-QYYHSP','d0852f50406b1e7248bc532fcb1a0236');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (97,'QYXTGL-ZZRY-BMGL','abf3fe0dc3e3dcc78809ad149cfa94e9');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (98,'QYXTGL-ZZRY-BMGL','efd60947ee70556a20640e6a480bb890');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (23,'XTGL-JSGL','0eadc8c7c022f13cdedaa7e7413a0fe1');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (22,'XTGL-JSGL','2f12fc88084ea062e3bdde086342a323');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (24,'XTGL-JSGL','83b5dcfd8eae19d04a703d55c507f35b');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (18,'XTGL-JSGL-SC','eace04d50010a26a1e37225f2e203b26');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (37,'XTGL-JSGL-SQ','2d32ed761f669dfccc7ef6fbff98a9b6');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (20,'XTGL-JSGL-XG','d5d29576cc3167cbdb8fe3a0b2e9cb58');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (21,'XTGL-JSGL-ZJ','fa4524d97c93bb8f196782d4a66ecdd0');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (61,'XTGL-QYXXGL','43ed570de1d66bb5ee760112181bb2bf');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (60,'XTGL-QYXXGL','5312ba0c5e8c972ebab536cdfd5c686c');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (62,'XTGL-QYXXGL-XG','a646465e5e2bf9785ebce9bf7bd8e46b');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (53,'XTGL-SJYGL','0a500a4ef872c0da332adfae737caf03');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (54,'XTGL-SJYGL','7aafa152872e021e5d5d11ce6689dcec');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (52,'XTGL-SJYGL-XG','e3068b381c8a5eaebc083fd85a040af2');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (78,'XTGL-XTSZ','8ba694590f475c4d3e2faffca32820de');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (79,'XTGL-XTSZ','f1255871f4c57f850378235d3b556a5c');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (82,'XTGL-XTSZ-SC','3139c2251a87ef1c11c42638491c968f');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (81,'XTGL-XTSZ-XG','73fad798792127d298e178a9c6c20ba3');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (80,'XTGL-XTSZ-ZJ','b403fa836b154a9a8456d631f2d7f5ca');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (42,'XTGL-YHGL','2f12fc88084ea062e3bdde086342a323');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (40,'XTGL-YHGL','954c06cf2c5133fb3bdf244d26955a14');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (41,'XTGL-YHGL','c2e253ad345ea3998b90df4e6e97e356');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (39,'XTGL-YHGL','d44c436777a17f4868813efbbb2ffffb');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (35,'XTGL-YHGL-CZMM','394365a60a740d7c0527badd4b964897');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (36,'XTGL-YHGL-JS','c2d22998a0d5e0fac6400a86e9a14a3f');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (33,'XTGL-YHGL-SC','ff40e182cbb096c35d1e4ad60c3eab09');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (38,'XTGL-YHGL-SQ','30d0c0b3dbcb1124cd1d5b0bbd514dde');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (29,'XTGL-YHGL-XG','b91a4be3d763864cbc7efe78010e3980');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (28,'XTGL-YHGL-ZJ','b640c19e353f49757e9fa7991b62ed7a');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (67,'XTGL-ZDGL','247045bb9c8412526f9ff48b9fc4b98b');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (68,'XTGL-ZDGL','fb8f9ccaad4855f891ec1c4521f6e51d');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (71,'XTGL-ZDGL-SC','0e0b103069ec1bef20429d38dfaeae38');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (70,'XTGL-ZDGL-XG','de1613a5a8f4f669e345085786e60831');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (69,'XTGL-ZDGL-ZJ','a7e30f1ae19ec6b6bde9a7d9efbd5f82');

/*Table structure for table `t_sys_parameter` */

DROP TABLE IF EXISTS `t_sys_parameter`;

CREATE TABLE `t_sys_parameter` (
  `f_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'ID',
  `f_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `f_desc` text COLLATE utf8_bin COMMENT '描述',
  `f_order` int(11) NOT NULL DEFAULT '999999999' COMMENT '排序',
  `f_is_editable` int(11) NOT NULL DEFAULT '1' COMMENT '是否开放给客户编辑，1、是；2、否',
  `f_field_type` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '输入控件类型，比如varchar，int，decimal，datetime，dict',
  `f_field_cfg` text COLLATE utf8_bin NOT NULL COMMENT '输入控件配置',
  `f_init_value` text COLLATE utf8_bin NOT NULL COMMENT '出厂值',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_PARAMETER_NAME` (`f_name`),
  UNIQUE KEY `UNI_PARAMETER_ORDER` (`f_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统参数定义表';

/*Data for the table `t_sys_parameter` */

insert  into `t_sys_parameter`(`f_id`,`f_name`,`f_desc`,`f_order`,`f_is_editable`,`f_field_type`,`f_field_cfg`,`f_init_value`,`f_remark`) values ('DefaultPassword','默认密码','新增操作员时的初始登录密码。',100000002,1,'varchar','512','12345678',NULL);
insert  into `t_sys_parameter`(`f_id`,`f_name`,`f_desc`,`f_order`,`f_is_editable`,`f_field_type`,`f_field_cfg`,`f_init_value`,`f_remark`) values ('ExpiryDate','授权有效期','系统授权使用有效期，过期后将会锁定系统，需要重新购买使用权。',100000003,0,'datetime','date','2016-12-31 23:59:59',NULL);

/*Table structure for table `t_sys_parameter_value` */

DROP TABLE IF EXISTS `t_sys_parameter_value`;

CREATE TABLE `t_sys_parameter_value` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_tenant_id` int(11) NOT NULL DEFAULT '1' COMMENT '租户ID',
  `f_parameter_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '参数ID',
  `f_parameter_value` text COLLATE utf8_bin NOT NULL COMMENT '参数取值',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_PARAMETER_VALUE` (`f_tenant_id`,`f_parameter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统参数取值表';

/*Data for the table `t_sys_parameter_value` */

/*Table structure for table `t_sys_role` */

DROP TABLE IF EXISTS `t_sys_role`;

CREATE TABLE `t_sys_role` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_company_id` int(11) NOT NULL DEFAULT '0' COMMENT '企业ID，0为所有企业公用的',
  `f_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `f_desc` text COLLATE utf8_bin COMMENT '角色描述',
  `f_is_preset` int(2) NOT NULL DEFAULT '2' COMMENT '是否系统预置，1、系统预置；2、操作员创建',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_ROLE_NAME` (`f_name`,`f_company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

/*Data for the table `t_sys_role` */

insert  into `t_sys_role`(`f_id`,`f_company_id`,`f_name`,`f_desc`,`f_is_preset`,`f_remark`) values (1,0,'系统管理员角色','用于系统的运营维护。',1,'系统主要分为平台运维、施工企业、业主监督等子系统。');
insert  into `t_sys_role`(`f_id`,`f_company_id`,`f_name`,`f_desc`,`f_is_preset`,`f_remark`) values (2,0,'普通用户角色',NULL,1,NULL);
insert  into `t_sys_role`(`f_id`,`f_company_id`,`f_name`,`f_desc`,`f_is_preset`,`f_remark`) values (3,0,'平台管理员角色','用于平台的运营维护。',1,'注意：不能分配企业相关的功能给该角色。');
insert  into `t_sys_role`(`f_id`,`f_company_id`,`f_name`,`f_desc`,`f_is_preset`,`f_remark`) values (4,0,'企业管理员角色','用于企业的运营维护，主要为企业的权限分配。',1,'注意：不能分配平台相关的功能给该角色。');

/*Table structure for table `t_sys_role_menu` */

DROP TABLE IF EXISTS `t_sys_role_menu`;

CREATE TABLE `t_sys_role_menu` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_role_id` int(11) NOT NULL COMMENT '角色ID',
  `f_menu_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限表';

/*Data for the table `t_sys_role_menu` */

insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (1,1,'ROOT');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (2,1,'GRZX');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (3,1,'QYGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (4,1,'QYGL-SJYGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (5,1,'QYGL-SJYGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (6,1,'QYGL-QYXXGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (7,1,'QYGL-QYXXGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (8,1,'QYGL-QYXXGL-SH');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (9,1,'QYXTGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (10,1,'QYXTGL-ZZRY');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (11,1,'QYXTGL-ZZRY-BMGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (12,1,'QYXTGL-ZZRY-GWGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (13,1,'QYXTGL-ZZRY-YGGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (14,1,'QYXTGL-JCSJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (15,1,'QYXTGL-QYXXWH');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (16,1,'QYXTGL-QYYHSP');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (17,1,'XTGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (18,1,'XTGL-JSGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (19,1,'XTGL-JSGL-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (20,1,'XTGL-JSGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (21,1,'XTGL-JSGL-SC');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (22,1,'XTGL-JSGL-SQ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (23,1,'XTGL-YHGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (24,1,'XTGL-YHGL-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (25,1,'XTGL-YHGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (26,1,'XTGL-YHGL-SC');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (27,1,'XTGL-YHGL-SQ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (28,1,'XTGL-YHGL-CZMM');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (29,1,'XTGL-YHGL-JS');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (30,1,'XTGL-ZDGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (31,1,'XTGL-ZDGL-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (32,1,'XTGL-ZDGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (33,1,'XTGL-ZDGL-SC');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (34,1,'XTGL-ZDGL-DCSQL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (35,1,'XTGL-XTSZ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (36,1,'XTGL-XTSZ-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (37,1,'XTGL-XTSZ-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (38,1,'XTGL-XTSZ-SC');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (39,1,'XTGL-XTSZ-DCSQL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (40,1,'XTGL-XTSZ-SZQZ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (41,2,'ROOT');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (42,2,'GRZX');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (43,3,'ROOT');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (44,3,'GRZX');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (45,3,'QYGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (46,3,'QYGL-SJYGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (47,3,'QYGL-SJYGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (48,3,'QYGL-QYXXGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (49,3,'QYGL-QYXXGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (50,3,'QYGL-QYXXGL-SH');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (51,3,'XTGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (52,3,'XTGL-JSGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (53,3,'XTGL-JSGL-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (54,3,'XTGL-JSGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (55,3,'XTGL-JSGL-SC');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (56,3,'XTGL-JSGL-SQ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (57,3,'XTGL-YHGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (58,3,'XTGL-YHGL-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (59,3,'XTGL-YHGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (60,3,'XTGL-YHGL-SC');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (61,3,'XTGL-YHGL-SQ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (62,3,'XTGL-YHGL-CZMM');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (63,3,'XTGL-YHGL-JS');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (64,3,'XTGL-ZDGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (65,3,'XTGL-ZDGL-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (66,3,'XTGL-ZDGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (67,3,'XTGL-ZDGL-SC');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (68,3,'XTGL-ZDGL-DCSQL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (69,3,'XTGL-XTSZ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (70,3,'XTGL-XTSZ-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (71,3,'XTGL-XTSZ-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (72,3,'XTGL-XTSZ-SC');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (73,3,'XTGL-XTSZ-DCSQL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (74,3,'XTGL-XTSZ-SZQZ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (75,4,'ROOT');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (76,4,'GRZX');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (77,4,'QYXTGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (78,4,'QYXTGL-ZZRY');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (79,4,'QYXTGL-ZZRY-BMGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (80,4,'QYXTGL-ZZRY-GWGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (81,4,'QYXTGL-ZZRY-YGGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (82,4,'QYXTGL-JCSJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (83,4,'QYXTGL-QYXXWH');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (84,4,'QYXTGL-QYYHSP');

/*Table structure for table `t_sys_setting` */

DROP TABLE IF EXISTS `t_sys_setting`;

CREATE TABLE `t_sys_setting` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `f_code` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '编码',
  `f_name` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `f_desc` text COLLATE utf8_bin COMMENT '描述',
  `f_order` int(11) NOT NULL DEFAULT '999999999' COMMENT '排序',
  `f_is_editable` int(11) NOT NULL DEFAULT '1' COMMENT '是否开放给客户编辑，1、是；2、否',
  `f_field_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '输入控件类型，比如varchar，int，decimal，datetime，dict',
  `f_field_cfg` text COLLATE utf8_bin COMMENT '输入控件配置',
  `f_init_value` text COLLATE utf8_bin NOT NULL COMMENT '出厂值',
  `f_value` text COLLATE utf8_bin NOT NULL COMMENT '当前值',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_SETTINGS_CODE` (`f_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统设置（参数）表';

/*Data for the table `t_sys_setting` */

insert  into `t_sys_setting`(`f_id`,`f_code`,`f_name`,`f_desc`,`f_order`,`f_is_editable`,`f_field_type`,`f_field_cfg`,`f_init_value`,`f_value`,`f_remark`) values (1,'ZKPMS.MASTER.COMPANY.REGISTER.AUDIT','企业注册是否需要审核','1、是；2、否。',1,1,'String',NULL,'1','2','为了方便推广，现在企业注册暂时设置为不需要审核。');

/*Table structure for table `t_sys_tenant` */

DROP TABLE IF EXISTS `t_sys_tenant`;

CREATE TABLE `t_sys_tenant` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_parent_id` int(11) DEFAULT NULL COMMENT '父级租户',
  `f_parent_path` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '树形结构的路径，以“/”开头、分隔和结尾。',
  `f_order` int(11) NOT NULL DEFAULT '10' COMMENT '同一个父级租户下的排序',
  `f_name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `f_desc` text COLLATE utf8_bin COMMENT '描述',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  KEY `SYS_TENANT_PARENT_ID` (`f_parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户表';

/*Data for the table `t_sys_tenant` */

insert  into `t_sys_tenant`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_remark`) values (0,NULL,'/',0,'根租户','','系统预置数据，不能修改和删除。');

/*Table structure for table `t_sys_url` */

DROP TABLE IF EXISTS `t_sys_url`;

CREATE TABLE `t_sys_url` (
  `f_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'f_url和f_methods的MD5值',
  `f_url` varchar(512) COLLATE utf8_bin NOT NULL COMMENT 'URL',
  `f_desc` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'URL描述',
  `f_patterns` varchar(512) COLLATE utf8_bin NOT NULL COMMENT 'URL表达式',
  `f_methods` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '[]' COMMENT '提交方式',
  `f_params` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '匹配查询参数',
  `f_headers` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '匹配HTTP头参数',
  `f_consumes` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '匹配Content-type，如application/json、application/xml、text/xml',
  `f_produces` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `f_custom` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `f_handler_method` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '处理方法',
  `f_is_log` int(11) NOT NULL DEFAULT '2' COMMENT '是否记录日志。如查询列表，详情界面等，都不记录，而删除、修改、增加就需要记录',
  `f_is_auto` int(11) NOT NULL DEFAULT '1' COMMENT '是否自动扫描生成',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='URL定义表';

/*Data for the table `t_sys_url` */

/*Table structure for table `t_sys_user` */

DROP TABLE IF EXISTS `t_sys_user`;

CREATE TABLE `t_sys_user` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_tenant_id` int(11) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `f_account` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `f_password` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `f_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `f_department_id` int(11) NOT NULL DEFAULT '0' COMMENT '部门ID',
  `f_creator_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建人',
  `f_created_time` datetime NOT NULL COMMENT '创建时间',
  `f_last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `f_locked_time` datetime DEFAULT NULL COMMENT '锁定时间',
  `f_unregister_time` datetime DEFAULT NULL COMMENT '注销时间',
  `f_is_can_login` int(11) NOT NULL DEFAULT '1' COMMENT '是否允许登录，1、是；2、否',
  `f_is_preset` int(2) NOT NULL DEFAULT '2' COMMENT '是否系统预置，1、系统预置；2、操作员创建',
  `f_status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1、正常；2、锁定；3、注销',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_USER_ACCOUNT` (`f_account`),
  KEY `IDX_USER_TENANT` (`f_tenant_id`),
  KEY `IDX_USER_DEPARTMENT` (`f_department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户（操作员）表';

/*Data for the table `t_sys_user` */

insert  into `t_sys_user`(`f_id`,`f_tenant_id`,`f_account`,`f_password`,`f_name`,`f_department_id`,`f_creator_id`,`f_created_time`,`f_last_login_time`,`f_locked_time`,`f_unregister_time`,`f_is_can_login`,`f_is_preset`,`f_status`,`f_remark`) values (1,0,'SuperAdmin','$2a$10$o6R1D7JhRW7d56inhUm50eF5xy8fF1l3KPCc0kdgpKBMw6olsamiq','超级管理员',0,0,'1970-01-01 00:00:00','2017-06-29 21:37:34',NULL,NULL,1,1,1,'该账号是开发人员维护系统用，不能提供给客户使用。');
insert  into `t_sys_user`(`f_id`,`f_tenant_id`,`f_account`,`f_password`,`f_name`,`f_department_id`,`f_creator_id`,`f_created_time`,`f_last_login_time`,`f_locked_time`,`f_unregister_time`,`f_is_can_login`,`f_is_preset`,`f_status`,`f_remark`) values (2,0,'admin','$2a$10$BtvvDJwfxzJP7TRGEoG6p.PQbW1hWC6wvqiDUqjvMrfLoMgHjvjBa','系统管理员',0,0,'1970-01-01 00:00:00','2017-06-25 07:20:38',NULL,NULL,1,1,1,'该账号用于维护系统设置和权限分配。');

/*Table structure for table `t_sys_user_menu` */

DROP TABLE IF EXISTS `t_sys_user_menu`;

CREATE TABLE `t_sys_user_menu` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_user_id` int(11) NOT NULL COMMENT '用户ID',
  `f_menu_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户权限表';

/*Data for the table `t_sys_user_menu` */

/*Table structure for table `t_sys_user_role` */

DROP TABLE IF EXISTS `t_sys_user_role`;

CREATE TABLE `t_sys_user_role` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_user_id` int(11) NOT NULL COMMENT '用户ID',
  `f_role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色用户表';

/*Data for the table `t_sys_user_role` */

insert  into `t_sys_user_role`(`f_id`,`f_user_id`,`f_role_id`) values (2,2,1);

/*Table structure for table `t_tool_generate_rule` */

DROP TABLE IF EXISTS `t_tool_generate_rule`;

CREATE TABLE `t_tool_generate_rule` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_code` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '编码',
  `f_name` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `f_menu_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单ID',
  `f_menu_name` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单名称',
  `f_menu_remark` longtext COLLATE utf8_bin COMMENT '菜单描述',
  `f_menu_parent_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父级菜单',
  `f_menu_parent_path` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '树形结构的路径，以“/”开头、分隔和结尾。',
  `f_menu_order` int(11) NOT NULL DEFAULT '10' COMMENT '同一个父级菜单下的排序',
  `f_request_url` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '后台Rest API的URL',
  `f_package_name` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '所属模块的包名',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代码生成规则表';

/*Data for the table `t_tool_generate_rule` */

/*Table structure for table `t_tool_generate_rule_field` */

DROP TABLE IF EXISTS `t_tool_generate_rule_field`;

CREATE TABLE `t_tool_generate_rule_field` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_table_id` int(11) DEFAULT NULL COMMENT '数据库表信息ID',
  `f_order` int(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `f_column_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '字段名',
  `f_column_comment` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '字段描述',
  `f_column_type` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '字段类型',
  `f_java_type` varchar(256) COLLATE utf8_bin DEFAULT 'java.lang.String' COMMENT '字段对应Java数据类型',
  `f_is_primary` tinyint(1) DEFAULT '2' COMMENT '是否为主健，1是，2否',
  `f_is_super_class_field` tinyint(1) DEFAULT '2' COMMENT '是否为父类字段，1是，2否',
  `f_is_override_field` tinyint(1) DEFAULT '2' COMMENT '是否为覆写或实现接口字段，1是，2否',
  `f_is_insert` tinyint(1) DEFAULT '1' COMMENT '是否为插入字段，1是，2否',
  `f_is_update` tinyint(1) DEFAULT '1' COMMENT '是否为更新字段，1是，2否',
  `f_is_select` tinyint(1) DEFAULT '1' COMMENT '是否为查询字段，1是，2否',
  `f_is_equal` tinyint(1) DEFAULT '2' COMMENT '是否为等于查询条件，1是，2否',
  `f_is_like` tinyint(1) DEFAULT '2' COMMENT '是否为左右模糊查询条件，1是，2否',
  `f_is_left_like` tinyint(1) DEFAULT '2' COMMENT '是否为左模糊查询条件，1是，2否',
  `f_is_right_like` tinyint(1) DEFAULT '2' COMMENT '是否为右模糊查询条件，1是，2否',
  `f_is_in` tinyint(1) DEFAULT '2' COMMENT '是否为IN查询条件，1是，2否',
  `f_is_not_in` tinyint(1) DEFAULT '2' COMMENT '是否为NOT IN查询条件，1是，2否',
  `f_is_between` tinyint(1) DEFAULT '2' COMMENT '是否为BETWEEN查询条件，1是，2否',
  `f_is_search` tinyint(1) DEFAULT '2' COMMENT '是否在搜索栏中显示，1是，2否',
  `f_is_grid` tinyint(1) DEFAULT '1' COMMENT '是否在Grid表格中显示，1是，2否',
  `f_is_form` tinyint(1) DEFAULT '1' COMMENT '是否在Form表单中显示，1是，2否',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代码生成规则字段信息';

/*Data for the table `t_tool_generate_rule_field` */

/*Table structure for table `t_tool_generate_rule_table` */

DROP TABLE IF EXISTS `t_tool_generate_rule_table`;

CREATE TABLE `t_tool_generate_rule_table` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_rule_id` int(11) DEFAULT NULL COMMENT '生成规则ID',
  `f_db_name` varchar(125) COLLATE utf8_bin DEFAULT NULL COMMENT '数据库名',
  `f_table_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '数据库表名',
  `f_order` int(11) DEFAULT '1' COMMENT '排序',
  `f_is_main` tinyint(1) DEFAULT '1' COMMENT '是否为主表，1是，2否',
  `f_entity_interface` longtext COLLATE utf8_bin COMMENT 'Java实体类实现的接口列表',
  `f_entity_base_class` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'Java实体类父类的全路径类名',
  `f_entity_class` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'Java实体类的全路径类名',
  `f_mapper_base_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `f_mapper_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `f_service_base_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `f_service_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `f_rest_base_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `f_rest_class` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='代码生成规则数据库表信息';

/*Data for the table `t_tool_generate_rule_table` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
