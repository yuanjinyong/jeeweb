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

insert  into `t_sys_department`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_remark`) values (0,NULL,'/',0,'系统组织机构','根组织机构，系统预置数据，不能修改和删除。',NULL);

/*Table structure for table `t_sys_dict_cfg` */

DROP TABLE IF EXISTS `t_sys_dict_cfg`;

CREATE TABLE `t_sys_dict_cfg` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '字典组编码',
  `f_name` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '字典组名称',
  `f_ds_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'defaultDataSource' COMMENT '字典组对应数据源名称',
  `f_table_name` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 't_sys_dict_item' COMMENT '字典组对应数据库表',
  `f_tenant_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_tenant_id' COMMENT '字典项租户ID对应数据库表中字段',
  `f_code_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_code' COMMENT '字典项编码对应数据库表中字段',
  `f_text_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_text' COMMENT '字典项描述对应数据库表中字段',
  `f_order_column` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT 'f_item_order' COMMENT '字典项排序对应数据库表中字段',
  `f_where_clause` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '拼接到查询SQL语句中的where条件',
  `f_is_sys` int(11) NOT NULL DEFAULT '1' COMMENT '是否系统预置，1、是；2、否',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_DICT_CFG` (`f_code`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典组定义表';

/*Data for the table `t_sys_dict_cfg` */

insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (1,'BaseClass','实体类可选父类','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (2,'JavaObjectType','Java属性类型','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (3,'JdbcDataType','数据库字段类型','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (4,'JdbcNullable','数据库字段是否为空','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (5,'MenuType','菜单类型','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (6,'ParameterDataType','系统参数类型','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (7,'RequestMethod','URL提交方式','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (8,'TemplateDataType','报表字段类型','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (9,'TemplateStatus','模板状态','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (10,'TrueFalse','布尔型的是否','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (11,'UserStatus','用户状态','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (12,'YesNo','枚举型的是否（0为否）','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (13,'YesNo2','枚举型的是否（2为否）','sysDataSource','t_sys_dict_item','f_tenant_id','f_item_code','f_item_text','f_item_order','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (14,'UserNameAll','操作员列表','sysDataSource','t_sys_user','f_tenant_id','f_id','f_name','f_name','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (15,'UserNameActive','当前激活的操作员列表','sysDataSource','t_sys_user','f_tenant_id','f_id','f_name','f_name','AND f_status IN (1,2)',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (16,'TenantName','租户名称列表','sysDataSource','t_sys_tenant','0','f_id','f_name','f_name','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (17,'FormDataSourceType','表单数据源类型','sysDataSource','t_sys_tenant','0','f_id','f_name','f_name','',1,NULL);
insert  into `t_sys_dict_cfg`(`f_id`,`f_code`,`f_name`,`f_ds_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_text_column`,`f_order_column`,`f_where_clause`,`f_is_sys`,`f_remark`) values (18,'ValidateRules','校验规则','sysDataSource','t_sys_tenant','0','f_id','f_name','f_name','',1,NULL);

/*Table structure for table `t_sys_dict_item` */

DROP TABLE IF EXISTS `t_sys_dict_item`;

CREATE TABLE `t_sys_dict_item` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_tenant_id` int(11) NOT NULL DEFAULT '0' COMMENT '租户ID',
  `f_dict_code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '字典组编码',
  `f_item_order` int(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `f_item_code` varchar(128) COLLATE utf8_bin NOT NULL COMMENT '字典项编码',
  `f_item_name` varchar(512) COLLATE utf8_bin NOT NULL COMMENT '字典项名称',
  `f_is_sys` int(11) NOT NULL DEFAULT '1' COMMENT '是否系统预置，1、是；2、否',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_DICT_ITEM` (`f_tenant_id`,`f_dict_code`,`f_item_code`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通用字典项定义表';

/*Data for the table `t_sys_dict_item` */

insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (1,0,'BaseClass',1,'com.jeeweb.mis.common.framework.entity.ABaseEntity','com.jeeweb.mis.common.framework.entity.ABaseEntity',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (2,0,'BaseClass',2,'com.jeeweb.mis.common.framework.entity.ATenantEntity','com.jeeweb.mis.common.framework.entity.ATenantEntity',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (3,0,'BaseClass',3,'com.jeeweb.mis.common.framework.entity.ATreeNodeEntity','com.jeeweb.mis.common.framework.entity.ATreeNodeEntity',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (5,0,'JavaObjectType',1,'java.lang.String','java.lang.String',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (6,0,'JavaObjectType',2,'java.lang.Boolean','java.lang.Boolean',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (7,0,'JavaObjectType',3,'java.lang.Integer','java.lang.Integer',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (8,0,'JavaObjectType',4,'java.lang.Double','java.lang.Double',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (9,0,'JavaObjectType',5,'java.lang.Byte','java.lang.Byte',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (10,0,'JavaObjectType',6,'java.math.BigDecimal','java.math.BigDecimal',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (11,0,'JavaObjectType',7,'java.sql.Timestamp','java.sql.Timestamp',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (12,0,'JdbcDataType',1,'bigint','bigint',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (13,0,'JdbcDataType',2,'blob','blob',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (14,0,'JdbcDataType',3,'char','char',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (15,0,'JdbcDataType',4,'date','date',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (16,0,'JdbcDataType',5,'datetime','datetime',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (17,0,'JdbcDataType',6,'decimal','decimal',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (18,0,'JdbcDataType',7,'double','double',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (19,0,'JdbcDataType',8,'enum','enum',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (20,0,'JdbcDataType',9,'float','float',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (21,0,'JdbcDataType',10,'int','int',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (22,0,'JdbcDataType',11,'longblob','longblob',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (23,0,'JdbcDataType',12,'longtext','longtext',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (24,0,'JdbcDataType',13,'mediumblob','mediumblob',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (25,0,'JdbcDataType',14,'mediumtext','mediumtext',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (26,0,'JdbcDataType',15,'set','set',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (27,0,'JdbcDataType',16,'smallint','smallint',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (28,0,'JdbcDataType',17,'text','text',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (29,0,'JdbcDataType',18,'time','time',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (30,0,'JdbcDataType',19,'timestamp','timestamp',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (31,0,'JdbcDataType',20,'tinyint','tinyint',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (32,0,'JdbcDataType',21,'varchar','varchar',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (33,0,'JdbcNullable',1,'YES','YES',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (34,0,'JdbcNullable',2,'NO','NO',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (35,0,'MenuType',1,'0','系统菜单',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (36,0,'MenuType',2,'1','目录',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (37,0,'MenuType',3,'2','页面',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (38,0,'MenuType',4,'3','按钮',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (39,0,'ParameterDataType',1,'varchar','文本',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (40,0,'ParameterDataType',2,'int','整数',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (41,0,'ParameterDataType',3,'decimal','小数',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (42,0,'ParameterDataType',4,'datetime','日期时间',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (43,0,'ParameterDataType',5,'dict','字典',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (44,0,'RequestMethod',1,'[]','[]',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (45,0,'RequestMethod',2,'[GET]','[GET]',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (46,0,'RequestMethod',3,'[POST]','[POST]',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (47,0,'RequestMethod',4,'[PUT]','[PUT]',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (48,0,'RequestMethod',5,'[DELETE]','[DELETE]',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (49,0,'TemplateDataType',1,'varchar(512)','文本',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (50,0,'TemplateDataType',2,'bigint(20)','整数',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (51,0,'TemplateDataType',3,'decimal(16,2)','小数',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (52,0,'TemplateDataType',4,'datetime','日期时间',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (53,0,'TemplateStatus',1,'1','草稿',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (54,0,'TemplateStatus',2,'2','已发布',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (55,0,'TemplateStatus',3,'3','已撤回',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (56,0,'TrueFalse',1,'true','是',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (57,0,'TrueFalse',2,'false','否',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (58,0,'UserStatus',1,'1','正常',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (59,0,'UserStatus',2,'2','锁定',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (60,0,'UserStatus',3,'3','注销',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (61,0,'YesNo',1,'1','是',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (62,0,'YesNo',2,'0','否',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (63,0,'YesNo2',1,'1','是',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (64,0,'YesNo2',2,'2','否',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (65,0,'FormDataSourceType',1,'1','数据库表',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (66,0,'FormDataSourceType',2,'2','动态SQL语句',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (67,0,'ValidateRules',11,'*','必填（非空）',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (68,0,'ValidateRules',12,'*6-16','6到16位任意字符',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (69,0,'ValidateRules',21,'s','字母',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (70,0,'ValidateRules',22,'s6-16','6到16位字符母',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (71,0,'ValidateRules',31,'n','数字',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (72,0,'ValidateRules',32,'n6-16','6到16位数字',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (73,0,'ValidateRules',41,'1','电子邮件',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (74,0,'ValidateRules',42,'2','手机号码',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (75,0,'ValidateRules',43,'3','邮政编码',1);
insert  into `t_sys_dict_item`(`f_id`,`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_name`,`f_is_sys`) values (76,0,'ValidateRules',44,'4','URL网址',1);

/*Table structure for table `t_sys_menu` */

DROP TABLE IF EXISTS `t_sys_menu`;

CREATE TABLE `t_sys_menu` (
  `f_id` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '主键，以模块加横杠分隔，如 XTGL-QXGL 表示系统管理-权限管理',
  `f_parent_id` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '父级菜单',
  `f_parent_path` varchar(1024) COLLATE utf8_bin NOT NULL COMMENT '树形结构的路径，以“/”开头、分隔和结尾。',
  `f_order` int(11) NOT NULL DEFAULT '10' COMMENT '排序',
  `f_name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `f_desc` text COLLATE utf8_bin COMMENT '菜单描述',
  `f_type` int(11) NOT NULL DEFAULT '3' COMMENT '类型，1、目录；2、页面；3、按钮',
  `f_icon` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `f_url_id` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单对应的URL，目录和按钮不需要填写，只有对应页面的菜单才需要填写',
  `f_status` int(11) DEFAULT '1' COMMENT '状态，1、启用；2、禁用',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  KEY `SYS_MENU_PARENT_ID` (`f_parent_id`),
  KEY `SYS_MENU_IS_SHOW` (`f_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单（权限）表';

/*Data for the table `t_sys_menu` */

insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('KFGJ','ROOT','/ROOT/',995,'开发人员工具','仅限开发人员使用，请不要授权给客户使用。',1,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('KFGJ-CDGL','KFGJ','/ROOT/KFGJ/',20,'菜单管理','菜单管理页面',2,'','673fb9ec0993d6217beeecb06ec4195a',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('KFGJ-CDGL-DCSQL','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',100,'导出SQL','导出SQL脚本',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('KFGJ-CDGL-SC','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',30,'删除','删除菜单',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('KFGJ-CDGL-XG','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',20,'修改','修改菜单',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('KFGJ-CDGL-ZJ','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',10,'增加','增加菜单',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('KFGJ-URLGL','KFGJ','/ROOT/KFGJ/',100,'URL管理','URL管理页面',2,'','52d9c048c8009cd584878420f67c7f0d',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('KFGJ-URLGL-SC','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',30,'删除','删除URL',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('KFGJ-URLGL-XG','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',20,'修改','修改URL',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('KFGJ-URLGL-ZJ','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',10,'增加','增加URL',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('ROOT',NULL,'/',0,'系统菜单','根菜单',0,NULL,'',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL','ROOT','/ROOT/',990,'系统管理','系统管理模块',1,NULL,'',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-BMGL','XTGL','/ROOT/XTGL/',20,'部门管理','部门管理页面',2,'','009d0552693b74e00f0a9f8b2f40d797',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-BMGL-SC','XTGL-BMGL','/ROOT/XTGL/XTGL-BMGL/',30,'删除','删除部门',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-BMGL-XG','XTGL-BMGL','/ROOT/XTGL/XTGL-BMGL/',20,'修改','修改部门',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-BMGL-ZJ','XTGL-BMGL','/ROOT/XTGL/XTGL-BMGL/',10,'增加','增加部门',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-CSGL','XTGL','/ROOT/XTGL/',100,'参数管理','系统参数管理页面',2,'','6c2045991d74e12dd4919302b635523b',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-CSGL-SC','XTGL-CSGL','/ROOT/XTGL/XTGL-CSGL/',30,'删除','删除参数',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-CSGL-SZ','XTGL-CSGL','/ROOT/XTGL/XTGL-CSGL/',100,'设值','设值系统参数的取值',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-CSGL-XG','XTGL-CSGL','/ROOT/XTGL/XTGL-CSGL/',20,'修改','修改参数',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-CSGL-ZJ','XTGL-CSGL','/ROOT/XTGL/XTGL-CSGL/',10,'增加','增加参数',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-JSGL','XTGL','/ROOT/XTGL/',30,'角色管理','角色管理页面',2,'','8fe6ae755d69861cda2f379e95493ccb',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-JSGL-SC','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',30,'删除','删除角色',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-JSGL-SQ','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',100,'授权','授权角色可以使用的功能',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-JSGL-XG','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',20,'修改','修改角色',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-JSGL-ZJ','XTGL-JSGL','/ROOT/XTGL/XTGL-JSGL/',10,'增加','增加角色',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-YHGL','XTGL','/ROOT/XTGL/',40,'用户管理','用户管理页面',2,'','1238c535cd19156d1c09f9737e50bd4f',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-YHGL-SC','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',30,'删除','删除用户',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-YHGL-SQ','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',100,'授权','授权用户可以使用的功能',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-YHGL-XG','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',20,'修改','修改用户',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-YHGL-ZJ','XTGL-YHGL','/ROOT/XTGL/XTGL-YHGL/',10,'增加','增加用户',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-ZDGL','XTGL','/ROOT/XTGL/',110,'字典管理','字典管理页面',2,'','6fad3d8989748cf6b60a4d3b8b9e1f7f',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-ZDGL-SC','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',30,'删除','删除字典',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-ZDGL-XG','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',20,'修改','修改字典',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-ZDGL-ZJ','XTGL-ZDGL','/ROOT/XTGL/XTGL-ZDGL/',10,'增加','增加字典',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-ZHGL','XTGL','/ROOT/XTGL/',10,'租户管理','租户管理页面',2,'','7d781fbffdbb774295ac96205f2d9810',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-ZHGL-SC','XTGL-ZHGL','/ROOT/XTGL/XTGL-ZHGL/',30,'删除','删除租户',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-ZHGL-XG','XTGL-ZHGL','/ROOT/XTGL/XTGL-ZHGL/',20,'修改','修改租户',3,'','',1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_type`,`f_icon`,`f_url_id`,`f_status`,`f_remark`) values ('XTGL-ZHGL-ZJ','XTGL-ZHGL','/ROOT/XTGL/XTGL-ZHGL/',10,'增加','增加租户',3,'','',1,NULL);

/*Table structure for table `t_sys_menu_url` */

DROP TABLE IF EXISTS `t_sys_menu_url`;

CREATE TABLE `t_sys_menu_url` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_menu_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单ID，关联t_sys_menu表的f_id',
  `f_url_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'URL的ID，关联t_sys_url表的f_id',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNQ_MENU_URL` (`f_menu_id`,`f_url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单可以访问的URL地址';

/*Data for the table `t_sys_menu_url` */

insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (1,'KFGJ-CDGL','17dd5f959e243ddf22805438b44fe117');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (2,'KFGJ-CDGL','28db4d5e06b2fbe92e2f6dfc289c051f');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (3,'KFGJ-CDGL','e747dafcc3e9af9070b9709f8605298b');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (4,'KFGJ-CDGL-DCSQL','dc940553c07e3e561120940431832c19');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (5,'KFGJ-CDGL-SC','00cb26b34340320ee6f365b3d22859b2');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (6,'KFGJ-CDGL-XG','52cb77720c0ad2b1e78818e50270a709');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (7,'KFGJ-CDGL-XG','a6bca5541d1cef54765ded893033acb2');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (8,'KFGJ-CDGL-ZJ','52cb77720c0ad2b1e78818e50270a709');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (9,'KFGJ-CDGL-ZJ','a6bca5541d1cef54765ded893033acb2');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (10,'KFGJ-URLGL','e2b929d5f205c97e11dcc539e89b964b');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (11,'KFGJ-URLGL','f59a933ff213482561e27f5e99b22c3d');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (12,'KFGJ-URLGL-SC','0d5dcece874070ed78c3ecd1f879aad2');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (13,'KFGJ-URLGL-XG','b814b128d2048fde92557543041f73f7');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (14,'KFGJ-URLGL-ZJ','b814b128d2048fde92557543041f73f7');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (15,'XTGL-BMGL','54571dc56696695724a084820ecbee70');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (16,'XTGL-BMGL','f4cfbf2400d14c71bc3a011ffeacca6a');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (17,'XTGL-BMGL-SC','ec1a312e7d8716816d61d06bb2350eb8');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (18,'XTGL-BMGL-XG','b2355ea1e3d20c851428f2cbec77d2c8');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (19,'XTGL-BMGL-ZJ','b2355ea1e3d20c851428f2cbec77d2c8');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (20,'XTGL-CSGL','6ed4cb5ebe1555ce56dc25235e52121a');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (21,'XTGL-CSGL','84724fe074596c9ad2a6ffb661592e13');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (22,'XTGL-CSGL','a457817d5dfee119fcaedf78328365a5');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (23,'XTGL-CSGL','ebef155bb2aa31b8e97a15efe4ce5660');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (24,'XTGL-CSGL-SC','61e3cb2c08738e453b91381a2b7ae551');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (25,'XTGL-CSGL-SZ','0cb92d0d74d14bc5ef9ecc22646414a2');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (26,'XTGL-CSGL-XG','13db2d48bb17d52b269201bd798c4f96');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (27,'XTGL-CSGL-ZJ','13db2d48bb17d52b269201bd798c4f96');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (28,'XTGL-JSGL','118c71dc5636900b5eb076d04232cfe5');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (29,'XTGL-JSGL','22dcb036653488523296cf89f79030f9');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (30,'XTGL-JSGL','30a19b86f969dcc4b12b70403574e0c2');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (31,'XTGL-JSGL','fae94ab72dd49e052f65c86b5170771a');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (32,'XTGL-JSGL-SC','10a24f61f84b33bcce96084c6ee1ff11');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (33,'XTGL-JSGL-SQ','f6e6741545229ee0e43071461fc3f807');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (34,'XTGL-JSGL-XG','2f1e1c46397e58bd8a662e476f0066da');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (35,'XTGL-JSGL-ZJ','2f1e1c46397e58bd8a662e476f0066da');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (36,'XTGL-YHGL','22dcb036653488523296cf89f79030f9');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (37,'XTGL-YHGL','650e82f9ff9628f8f1068b4d9c6907a2');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (38,'XTGL-YHGL','68c8da7aed6bd842982f5949e5bd82e1');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (39,'XTGL-YHGL','b0f2fb7a797dd5e83c605256deb69e99');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (40,'XTGL-YHGL','e110183931f7e487fd9e6178d919dc41');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (41,'XTGL-YHGL-SC','ca2db278c0c26b4c13956795740d17e5');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (42,'XTGL-YHGL-SQ','446cb579530ce9cb898cbefe0fc744ec');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (43,'XTGL-YHGL-XG','c8547e08fc73981533e37f92ba0f2e43');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (44,'XTGL-YHGL-ZJ','c8547e08fc73981533e37f92ba0f2e43');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (45,'XTGL-ZDGL','0104b4b5a8a240b00ee9b32b0d792542');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (46,'XTGL-ZDGL','50cd2812ab7d38c6b996c8561ddceb7d');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (47,'XTGL-ZDGL','6d184b7ec4223316b584ba1a47439254');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (48,'XTGL-ZDGL','6f7d95cb10e57b1e8b572c01488d64ba');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (49,'XTGL-ZDGL','ebef155bb2aa31b8e97a15efe4ce5660');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (50,'XTGL-ZDGL-SC','6fbdc1eafd5c6c399405c23b8a67c4cc');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (51,'XTGL-ZDGL-SC','79755b0315afe55969e09ef148166eb1');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (52,'XTGL-ZDGL-XG','7fc4b6f58c05e3b68a6127c4038e2498');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (53,'XTGL-ZDGL-XG','ff513da93fc9436c2509dd1031a54900');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (54,'XTGL-ZDGL-ZJ','7fc4b6f58c05e3b68a6127c4038e2498');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (55,'XTGL-ZDGL-ZJ','ff513da93fc9436c2509dd1031a54900');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (56,'XTGL-ZHGL','c6e565b257f5907c519b0865100687ff');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (57,'XTGL-ZHGL','ebef155bb2aa31b8e97a15efe4ce5660');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (58,'XTGL-ZHGL-SC','89287fc0534d93c3675441b07eb07d88');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (59,'XTGL-ZHGL-XG','f5869f0796da95049bcef05a81bd4b12');
insert  into `t_sys_menu_url`(`f_id`,`f_menu_id`,`f_url_id`) values (60,'XTGL-ZHGL-ZJ','f5869f0796da95049bcef05a81bd4b12');

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
  `f_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `f_desc` text COLLATE utf8_bin COMMENT '角色描述',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_ROLE_NAME` (`f_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

/*Data for the table `t_sys_role` */

insert  into `t_sys_role`(`f_id`,`f_name`,`f_desc`,`f_remark`) values (0,'系统管理员角色','维护系统的权限分配。',NULL);

/*Table structure for table `t_sys_role_menu` */

DROP TABLE IF EXISTS `t_sys_role_menu`;

CREATE TABLE `t_sys_role_menu` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_role_id` int(11) NOT NULL COMMENT '角色ID',
  `f_menu_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限表';

/*Data for the table `t_sys_role_menu` */

insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (1,0,'ROOT');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (2,0,'XTGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (3,0,'XTGL-JSGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (4,0,'XTGL-JSGL-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (5,0,'XTGL-JSGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (6,0,'XTGL-JSGL-SC');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (7,0,'XTGL-JSGL-SQ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (8,0,'XTGL-YHGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (9,0,'XTGL-YHGL-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (10,0,'XTGL-YHGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (11,0,'XTGL-YHGL-SC');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (12,0,'XTGL-YHGL-SQ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (13,0,'XTGL-CSGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (14,0,'XTGL-CSGL-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (15,0,'XTGL-CSGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (16,0,'XTGL-CSGL-SC');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (17,0,'XTGL-CSGL-SZ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (18,0,'XTGL-ZDGL');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (19,0,'XTGL-ZDGL-ZJ');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (20,0,'XTGL-ZDGL-XG');
insert  into `t_sys_role_menu`(`f_id`,`f_role_id`,`f_menu_id`) values (21,0,'XTGL-ZDGL-SC');

/*Table structure for table `t_sys_tenant` */

DROP TABLE IF EXISTS `t_sys_tenant`;

CREATE TABLE `t_sys_tenant` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_parent_id` int(11) DEFAULT NULL COMMENT '父级租户',
  `f_parent_path` varchar(1024) COLLATE utf8_bin NOT NULL COMMENT '树形结构的路径，以“/”开头、分隔和结尾。',
  `f_order` int(11) NOT NULL DEFAULT '10' COMMENT '同一个父级租户下的排序',
  `f_name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `f_desc` text COLLATE utf8_bin COMMENT '描述',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  KEY `SYS_TENANT_PARENT_ID` (`f_parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户表';

/*Data for the table `t_sys_tenant` */

insert  into `t_sys_tenant`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_remark`) values (0,NULL,'/',0,'系统租户','根租户，系统预置数据，不能修改和删除。',NULL);

/*Table structure for table `t_sys_url` */

DROP TABLE IF EXISTS `t_sys_url`;

CREATE TABLE `t_sys_url` (
  `f_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'f_url和f_methods的MD5值',
  `f_url` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'URL',
  `f_desc` text COLLATE utf8_bin COMMENT 'URL描述',
  `f_patterns` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'URL表达式',
  `f_methods` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '[]' COMMENT '提交方式',
  `f_params` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '匹配查询参数',
  `f_headers` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '匹配HTTP头参数',
  `f_consumes` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '匹配Content-type，如application/json、application/xml、text/xml',
  `f_produces` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `f_custom` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `f_handler_method` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '处理方法',
  `f_is_log` int(11) NOT NULL DEFAULT '2' COMMENT '是否记录日志。如查询列表，进入增加界面等，都不记录，而删除、修改、增加就需要记录',
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
  `f_status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，1、正常；2、锁定；3、注销',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_USER_ACCOUNT` (`f_account`),
  KEY `IDX_USER_TENANT` (`f_tenant_id`),
  KEY `IDX_USER_DEPARTMENT` (`f_department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户（操作员）表';

/*Data for the table `t_sys_user` */

insert  into `t_sys_user`(`f_id`,`f_tenant_id`,`f_account`,`f_password`,`f_name`,`f_department_id`,`f_creator_id`,`f_created_time`,`f_last_login_time`,`f_locked_time`,`f_unregister_time`,`f_is_can_login`,`f_status`,`f_remark`) values (0,0,'SuperAdmin','25d55ad283aa400af464c76d713c07ad','超级管理员',0,0,'1970-01-01 00:00:00',NULL,NULL,NULL,1,1,'该账号是开发人员维护系统用，不能提供给客户使用。');
insert  into `t_sys_user`(`f_id`,`f_tenant_id`,`f_account`,`f_password`,`f_name`,`f_department_id`,`f_creator_id`,`f_created_time`,`f_last_login_time`,`f_locked_time`,`f_unregister_time`,`f_is_can_login`,`f_status`,`f_remark`) values (1,0,'admin','25d55ad283aa400af464c76d713c07ad','系统管理员',0,0,'1970-01-01 00:00:00',NULL,NULL,NULL,1,1,'该账号用于维护系统设置和权限分配。');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色用户表';

/*Data for the table `t_sys_user_role` */

insert  into `t_sys_user_role`(`f_id`,`f_user_id`,`f_role_id`) values (1,1,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
