-- liquibase formatted


-- changeset 袁进勇:20170801000101
-- comment: 创建URL表结构
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
-- rollback DROP TABLE IF EXISTS t_sys_url;

