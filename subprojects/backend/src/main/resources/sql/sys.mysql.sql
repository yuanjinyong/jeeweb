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

insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ','ROOT','/ROOT/',995,'开发人员工具','开发人员工具模块','fa fa-wrench',1,NULL,1,0,0,1,'仅限开发人员使用，请不要授权给客户使用。');
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL','KFGJ','/ROOT/KFGJ/',20,'菜单管理','菜单管理页面','fa fa-bars',2,'/admin/platform/sys/menu',1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-DCSQL','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',100,'导出SQL','导出SQL脚本',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-SC','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',30,'删除','删除菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-XG','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',20,'修改','修改菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-CDGL-ZJ','KFGJ-CDGL','/ROOT/KFGJ/KFGJ-CDGL/',10,'增加','增加菜单',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL','KFGJ','/ROOT/KFGJ/',30,'URL管理','URL管理页面','fa fa-link',2,'/admin/platform/sys/url',1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL-SC','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',30,'删除','删除URL',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL-XG','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',20,'修改','修改URL',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('KFGJ-URLGL-ZJ','KFGJ-URLGL','/ROOT/KFGJ/KFGJ-URLGL/',10,'增加','增加URL',NULL,3,NULL,1,0,0,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('ROOT',NULL,'/',0,'系统菜单','根菜单',NULL,0,NULL,1,1,1,1,NULL);
insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values ('XTGL','ROOT','/ROOT/',990,'系统管理','系统管理模块','fa fa-cog',1,NULL,1,1,1,1,NULL);

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

insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','4050f6a3ffd77bdc4534d886e11f8012');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','55847dc87d27c4b9146caf118c45321c');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','0c0362062f72869516168ed479fa82bb');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL','d7b9102569dc7cfdc45956fa9cd32ad3');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-DCSQL','dc940553c07e3e561120940431832c19');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-SC','5a91bc0915d3e5c0b2e49d45c1b168ca');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-XG','fed4f0beac39e30f0c8354eaa65fd25f');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-CDGL-ZJ','a61340a1087d2e7bed041cf57d43a397');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL','0c0362062f72869516168ed479fa82bb');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL','d7b9102569dc7cfdc45956fa9cd32ad3');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL-SC','66e99840d588bd859cad21d43a1d8ae3');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL-SC','4087eb30e3c4c0dac2765498b3b0cab7');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL-XG','b73c369d73f83cd700ca2c554f76d504');
insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values ('KFGJ-URLGL-ZJ','f59ac63ca88503cd64ed14020d1cbead');

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
  `f_is_sys` int(11) NOT NULL DEFAULT '2' COMMENT '是否系统预置，1、系统预置的角色；2、前台创建的角色',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_ROLE_NAME` (`f_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

/*Data for the table `t_sys_role` */

insert  into `t_sys_role`(`f_id`,`f_name`,`f_desc`,`f_is_sys`,`f_remark`) values (0,'系统管理员角色','维护系统的权限分配。',1,'系统预置的角色，不能删除。');

/*Table structure for table `t_sys_role_menu` */

DROP TABLE IF EXISTS `t_sys_role_menu`;

CREATE TABLE `t_sys_role_menu` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_role_id` int(11) NOT NULL COMMENT '角色ID',
  `f_menu_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限表';

/*Data for the table `t_sys_role_menu` */

insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (0,'ROOT');
insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (0,'XTGL');

/*Table structure for table `t_sys_tenant` */

DROP TABLE IF EXISTS `t_sys_tenant`;

CREATE TABLE `t_sys_tenant` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_parent_id` int(11) DEFAULT NULL COMMENT '父级租户',
  `f_parent_path` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '树形结构的路径，以“/”开头、分隔和结尾。',
  `f_order` int(11) NOT NULL DEFAULT '10' COMMENT '同一个父级租户下的排序',
  `f_name` varchar(256) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `f_desc` text COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `f_remark` longtext COLLATE utf8_bin COMMENT '备注',
  PRIMARY KEY (`f_id`),
  KEY `SYS_TENANT_PARENT_ID` (`f_parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户表';

/*Data for the table `t_sys_tenant` */
insert  into `t_sys_tenant` (`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_remark`) values ('0',NULL,'/','0','根租户','','系统预置数据，不能修改和删除。');

/*Table structure for table `t_sys_url` */

DROP TABLE IF EXISTS `t_sys_url`;

CREATE TABLE `t_sys_url` (
  `f_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'f_url和f_methods的MD5值',
  `f_url` varchar(512) COLLATE utf8_bin NOT NULL COMMENT 'URL',
  `f_desc` text COLLATE utf8_bin DEFAULT NULL COMMENT 'URL描述',
  `f_patterns` varchar(512) COLLATE utf8_bin NOT NULL COMMENT 'URL表达式',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户（操作员）表';

/*Data for the table `t_sys_user` */

insert  into `t_sys_user`(`f_id`,`f_tenant_id`,`f_account`,`f_password`,`f_name`,`f_department_id`,`f_creator_id`,`f_created_time`,`f_last_login_time`,`f_locked_time`,`f_unregister_time`,`f_is_can_login`,`f_status`,`f_remark`) values (0,0,'SuperAdmin','$2a$10$o6R1D7JhRW7d56inhUm50eF5xy8fF1l3KPCc0kdgpKBMw6olsamiq','超级管理员',0,0,'1970-01-01 00:00:00',NULL,NULL,NULL,1,1,'该账号是开发人员维护系统用，不能提供给客户使用。');
insert  into `t_sys_user`(`f_id`,`f_tenant_id`,`f_account`,`f_password`,`f_name`,`f_department_id`,`f_creator_id`,`f_created_time`,`f_last_login_time`,`f_locked_time`,`f_unregister_time`,`f_is_can_login`,`f_status`,`f_remark`) values (1,0,'admin','$2a$10$o6R1D7JhRW7d56inhUm50eF5xy8fF1l3KPCc0kdgpKBMw6olsamiq','系统管理员',0,0,'1970-01-01 00:00:00',NULL,NULL,NULL,1,1,'该账号用于维护系统设置和权限分配。');

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

insert  into `t_sys_user_role`(`f_user_id`,`f_role_id`) values (1,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
