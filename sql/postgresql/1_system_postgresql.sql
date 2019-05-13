---
DROP TABLE IF EXISTS SYS_ATTACHMENT;
DROP TABLE IF EXISTS SYS_ATTACHMENTBUSINESSRELATION;
DROP TABLE IF EXISTS custom_db_table;
DROP TABLE IF EXISTS custom_db_table_fields;
DROP TABLE IF EXISTS jasdoc_user_folder_ref;
DROP TABLE IF EXISTS jasdoc_role_folder_ref;
DROP TABLE IF EXISTS jasdoc_recyclebin;
DROP TABLE IF EXISTS jasdoc_folder_share_user_ref;
DROP TABLE IF EXISTS jasdoc_folder_share;
DROP TABLE IF EXISTS jasdoc_folder;
DROP TABLE IF EXISTS jasdoc_file_backup_recovery;
DROP TABLE IF EXISTS jasdoc_file_share_user_ref;
DROP TABLE IF EXISTS jasdoc_file_associated;
DROP TABLE IF EXISTS jasdoc_file_classify_ref;
DROP TABLE IF EXISTS jasdoc_file;
DROP TABLE IF EXISTS jasdoc_file_history;
DROP TABLE IF EXISTS jasdoc_file_share;
DROP TABLE IF EXISTS jasdoc_file_folder_ref;
DROP TABLE IF EXISTS jasdoc_file_favorite_ref;
DROP TABLE IF EXISTS log_business;

DROP TABLE IF EXISTS custom_fun_unique_validate;

DROP view IF EXISTS act_id_membership ;
DROP TABLE IF EXISTS pri_user_role_ref;

DROP TABLE IF EXISTS sys_login_log;
DROP TABLE IF EXISTS sys_excel_template;
DROP TABLE IF EXISTS custom_fun_fields;
DROP TABLE IF EXISTS custom_fun_function;

DROP view IF EXISTS act_id_user;
DROP TABLE IF EXISTS pri_user;

DROP view IF EXISTS act_id_group;
DROP TABLE IF EXISTS pri_role;

DROP TABLE IF EXISTS log_operate;
DROP TABLE IF EXISTS sys_domain;
DROP TABLE IF EXISTS pri_role_func_privilege_ref;
DROP TABLE IF EXISTS jasdoc_apply_file_ref;

DROP view IF EXISTS act_custom_app ;
DROP TABLE IF EXISTS pri_application;

DROP TABLE IF EXISTS sys_spatial_object;
DROP TABLE IF EXISTS custom_dict;
DROP TABLE IF EXISTS pri_func_privilege;
DROP TABLE IF EXISTS jasdoc_classify_folder;
DROP TABLE IF EXISTS jasdoc_deleteindex;
DROP TABLE IF EXISTS jasdoc_dept_folder_ref;
DROP TABLE IF EXISTS jasdoc_download_apply;
DROP TABLE IF EXISTS jasdoc_favorite_folder;

DROP TABLE IF EXISTS pri_unit;



/*******框架相关数据库begin********/
CREATE TABLE SYS_ATTACHMENT (
	oid VARCHAR (36) NOT NULL PRIMARY KEY,
	file_name VARCHAR (200),
	file_type VARCHAR (20),
	file_size INT,
	CONTENT bytea,
	file_description VARCHAR (200),
	file_url VARCHAR (500),
	create_user_id VARCHAR (36),
	create_user_name VARCHAR (50),
	create_time TIMESTAMP (6),
	modify_user_id VARCHAR (36),
	modify_user_name VARCHAR (50),
	modify_time TIMESTAMP (6),
	active smallint not null default 1
);
CREATE TABLE SYS_ATTACHMENTBUSINESSRELATION (
	oid VARCHAR (36) NOT NULL PRIMARY KEY,
	attachment_id VARCHAR (36) NOT NULL,
	business_data_id VARCHAR (36) NOT NULL,
	business_data_table VARCHAR (50),
	business_type VARCHAR (20),
	create_user_id VARCHAR (36),
	create_user_name VARCHAR (50),
	create_time TIMESTAMP (6),
	modify_user_id VARCHAR (36),
	modify_user_name VARCHAR (50),
	modify_time TIMESTAMP (6),
	active SMALLINT NOT NULL
);

COMMENT ON TABLE SYS_ATTACHMENT IS '附件表，用来存储附件相关信息';

COMMENT ON COLUMN SYS_ATTACHMENT.oid IS '主键';

COMMENT ON COLUMN SYS_ATTACHMENT.file_name IS '文件名称';

COMMENT ON COLUMN SYS_ATTACHMENT.file_type IS '文件类型';

COMMENT ON COLUMN SYS_ATTACHMENT.file_size IS '文件大小';

COMMENT ON COLUMN SYS_ATTACHMENT. CONTENT IS '文件二进制流';

COMMENT ON COLUMN SYS_ATTACHMENT.file_description IS '文件描述';

COMMENT ON COLUMN SYS_ATTACHMENT.file_url IS '文件路径';

COMMENT ON COLUMN SYS_ATTACHMENT.create_user_id IS '创建人ID';

COMMENT ON COLUMN SYS_ATTACHMENT.create_user_name IS '创建人名称';

COMMENT ON COLUMN SYS_ATTACHMENT.create_time IS '创建时间';

COMMENT ON COLUMN SYS_ATTACHMENT.modify_user_id IS '修改人ID';

COMMENT ON COLUMN SYS_ATTACHMENT.modify_user_name IS '修改人名称';

COMMENT ON COLUMN SYS_ATTACHMENT.modify_time IS '修改时间';

COMMENT ON COLUMN SYS_ATTACHMENT.active IS '标识符';

COMMENT ON TABLE SYS_ATTACHMENTBUSINESSRELATION IS '附件与业务含义的关系表';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.oid IS '主键';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.attachment_id IS '附件ID';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.business_data_id IS '业务数据ID';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.business_data_table IS '业务数据表名称';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.business_type IS '业务含义';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.create_user_id IS '创建人ID';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.create_user_name IS '创建人名称';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.create_time IS '创建时间';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.modify_user_id IS '修改人ID';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.modify_user_name IS '修改人名称';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.modify_time IS '修改时间';

COMMENT ON COLUMN SYS_ATTACHMENTBUSINESSRELATION.active IS '标识符';

--为表SYS_ATTACHMENTBUSINESSRELATION添加列FILE_TYPE
ALTER TABLE SYS_ATTACHMENTBUSINESSRELATION ADD FILE_TYPE character varying(10);

/** 自定义表单-数据库-表单信息 */
create table custom_db_table(
	oid varchar(36) not null primary key,
	db_type varchar(30) not null ,
	db_source_name varchar(50) not null ,
	table_name varchar(50) not null ,
	table_name_cn varchar(100) not null ,
	table_type varchar(30) not null ,
	table_desc varchar(200)  ,
	row_index smallint not null ,
	create_user_id varchar(36) not null ,
	create_user_name varchar(50) not null ,
	create_datetime timestamp not null ,
	modify_user_id varchar(36) not null ,
	modify_user_name varchar(50) not null ,
	modify_datetime timestamp not null ,
	active smallint not null
);

create index INDEX_CUSTOM_DB_TABLE_TABLE_NAME_7 ON custom_db_table ( table_name );
comment on table custom_db_table is '自定义表单-数据库-表单信息';
comment on column custom_db_table.oid is '主键';
comment on column custom_db_table.db_type is '数据库类型';
comment on column custom_db_table.db_source_name is '数据源名称';
comment on column custom_db_table.table_name is '数据库表名称';
comment on column custom_db_table.table_type is '值范围（-32768 到 +32767）TT_10：业务属性表, TT_11：业务关系表, TT_20：点空间表, TT_21：线空间表, TT_22：面空间表（具体见预值表）';
comment on column custom_db_table.table_name_cn is '数据库表中文名称';
comment on column custom_db_table.table_desc is '数据库表描述';
comment on column custom_db_table.row_index is '排序字段';

/** 自定义表单-数据库-表字段信息 */
create table custom_db_table_fields(
	oid varchar(36) not null primary key,
	table_id varchar(36) not null ,
	field_name varchar(50) not null ,
	field_name_alias  varchar(50)  ,
	field_name_cn varchar(50)  ,
	field_type varchar(50) not null ,
	field_length varchar(50),
	if_pk varchar(20)  ,
	if_fk varchar(20)  ,
	fk_table varchar(50)  ,
	fk_table_field varchar(50)  ,
	fk_table_id varchar(36)  ,
	if_empty varchar(20)  ,
	if_index varchar(20)  ,
	field_default_value  varchar(50)  ,
	field_desc varchar(200)  ,
	row_index smallint not null ,
	create_user_id varchar(36) not null ,
	create_user_name varchar(50) not null ,
	create_datetime timestamp not null ,
	modify_user_id varchar(36) not null ,
	modify_user_name varchar(50) not null ,
	modify_datetime timestamp not null ,
	active smallint not null
);

comment on table custom_db_table_fields is '自定义表单-数据库-表字段信息';
comment on column custom_db_table_fields.oid is '主键';
comment on column custom_db_table_fields.table_id is '数据库表ID';
comment on column custom_db_table_fields.field_name is '数据库字段名称';
comment on column custom_db_table_fields.field_name_alias  is '数据库字段别名';
comment on column custom_db_table_fields.field_name_cn is '数据库字段中文名称';
comment on column custom_db_table_fields.field_type is '字段类型';
comment on column custom_db_table_fields.field_length is '字段长度/精度';
comment on column custom_db_table_fields.if_pk is '是否主键';
comment on column custom_db_table_fields.if_fk is '是否外键';
comment on column custom_db_table_fields.fk_table is '外键表名';
comment on column custom_db_table_fields.fk_table_field is '外键表字段';
comment on column custom_db_table_fields.fk_table_id is '外见表ID';
comment on column custom_db_table_fields.if_empty is '是否为空';
comment on column custom_db_table_fields.if_index is '是否索引';
comment on column custom_db_table_fields.field_default_value  is '默认值';
comment on column custom_db_table_fields.field_desc is '表字段描述';
comment on column custom_db_table_fields.row_index is '排序';


/** 自定义表单-功能配置-功能 */
create table custom_fun_function(
	oid varchar(36) not null primary key,
	function_name varchar(50) not null ,
	function_code varchar(50) not null ,
	table_name varchar(50) not null ,
	save_sql varchar(4000) not null ,
	update_sql varchar(4000) not null ,
	delete_sql varchar(4000) not null ,
	query_sql varchar(4000) not null ,
	details_sql varchar(4000) not null ,
	if_attachment varchar(20),
	create_user_id varchar(36) not null ,
	create_user_name varchar(50) not null ,
	create_datetime timestamp not null ,
	modify_user_id varchar(36) not null ,
	modify_user_name varchar(50) not null ,
	modify_datetime timestamp not null ,
	active smallint not null
);

comment on table custom_fun_function is '自定义表单-功能配置-功能';
comment on column custom_fun_function.oid is '主键';
comment on column custom_fun_function.function_name is '功能名称';
comment on column custom_fun_function.function_code is '功能编码(后台自动生成)';
comment on column custom_fun_function.table_name is '数据库表名称(数据来源)';
comment on column custom_fun_function.save_sql is '保存sql';
comment on column custom_fun_function.update_sql is '更新sql';
comment on column custom_fun_function.delete_sql is '删除sql';
comment on column custom_fun_function.query_sql is '列表查询sql';
comment on column custom_fun_function.details_sql is '详情查询sql';
comment on column custom_fun_function.if_attachment is '是否支持附件';


/** 自定义表单-功能配置-字段 */
create table custom_fun_fields(
	oid varchar(36) not null primary key,
	function_id varchar(36) not null ,
	field_name varchar(50) not null ,
	field_name_cn varchar(50)  ,
	field_source varchar(50) not null ,
	if_save varchar(20) not null ,
	if_update varchar(20) not null ,
	if_query varchar(20) not null ,
	if_list varchar(20) not null ,
	if_details varchar(20) not null ,
	if_required varchar(10) not null ,
	field_length smallint  ,
	field_type varchar(50)  ,
	ui_type varchar(50)  ,
	domain varchar(1000)  ,
	regular_expression varchar(200)  ,
	group_name varchar(100)  ,
	group_index smallint  ,
	row_index smallint  ,
	updateable varchar(10)  ,
	min NUMERIC (17, 7) ,
	max NUMERIC (17, 7)  ,
	placeholder varchar(50)  ,
	child_field varchar(50)  ,
	request_path varchar(500)  ,
	query_index smallint ,
	list_index smallint ,
	create_user_id varchar(36) not null ,
	create_user_name varchar(50) not null ,
	create_datetime timestamp not null ,
	modify_user_id varchar(36) not null ,
	modify_user_name varchar(50) not null ,
	modify_datetime timestamp not null ,
	if_less_today varchar(20),
	less_date_scope varchar(64),
	max_date_scope varchar(64),
	active smallint not null
);

comment on table custom_fun_fields is '自定义表单-功能配置-字段';
comment on column custom_fun_fields.oid is '主键';
comment on column custom_fun_fields.function_id is '所属功能id(引用custom_fun_function的主键)';
comment on column custom_fun_fields.field_name is '数据库字段名称';
comment on column custom_fun_fields.field_name_cn is '数据库字段中文名称';
comment on column custom_fun_fields.field_source is '字段来源';
comment on column custom_fun_fields.if_save is '是否新增字段';
comment on column custom_fun_fields.if_update is '是否修改字段';
comment on column custom_fun_fields.if_query is '是否搜索字段';
comment on column custom_fun_fields.if_list is '是否列表字段';
comment on column custom_fun_fields.if_details is '是否详情字段';
comment on column custom_fun_fields.if_required is '是否是必填';
comment on column custom_fun_fields.field_length is '字段长度';
comment on column custom_fun_fields.field_type is '字段类型';
comment on column custom_fun_fields.ui_type is '控件类别（域值';
comment on column custom_fun_fields.domain is '域值';
comment on column custom_fun_fields.regular_expression is '正则表达式';
comment on column custom_fun_fields.group_name is '页面控件分组';
comment on column custom_fun_fields.group_index is '分组排序序号';
comment on column custom_fun_fields.row_index is '字段排序序号';
comment on column custom_fun_fields.updateable is '可编辑性';
comment on column custom_fun_fields.min is '最小值';
comment on column custom_fun_fields.max is '最大值';
comment on column custom_fun_fields.placeholder is '文本框默认提示';
comment on column custom_fun_fields.child_field is '子级字段';
comment on column custom_fun_fields.request_path is '请求路径';
comment on column custom_fun_fields.query_index is '查询条件排序序号';
comment on column custom_fun_fields.list_index is '列表排序序号';
comment on column custom_db_table_fields.row_index is '排序';


/** 自定义表单-数据字典 */

create table custom_dict(
	oid varchar(36) not null primary key,
	dict_type varchar(50) not null ,
	dict_type_cn varchar(50) not null ,
	dict_code varchar(50) not null ,
	dict_value varchar(50) not null ,
	dict_value_desc varchar(50)  ,
	row_index smallint not null ,
	active smallint not null
);

create index INDEX_CUSTOM_DICT_DICT_TYPE_5 ON custom_dict ( dict_type );
comment on table custom_dict is '自定义表单-数据字典';
comment on column custom_dict.oid is '主键';
comment on column custom_dict.dict_type is '类型编码';
comment on column custom_dict.dict_type_cn is '类型中文';
comment on column custom_dict.dict_code is '值编码';
comment on column custom_dict.dict_value is '值';
comment on column custom_dict.dict_value_desc is '值描述';
comment on column custom_dict.row_index is '排序';


INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('36af025c-3b7c-457f-abb4-ec0dd99acd14', 'if_type', '是否', '0', '否', NULL, '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('320bdcf5-89d2-4b9f-a66d-09310218f059', 'if_type', '是否', '1', '是', NULL, '1', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('ff8e4dc9-9e3b-4a15-b943-befb7ae2d069', 'db_type', '数据库类型', 'DT_01', 'oracle', NULL, '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('c709575b-53a6-40f4-b7b2-998bcd8894e5', 'db_type', '数据库类型', 'DT_02', 'mysql', NULL, '1', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('c1cd99b0-c8ec-4e83-80f0-4bb7cbd4bc21', 'db_type', '数据库类型', 'DT_03', 'postgresql', NULL, '2', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('2ec56b85-e8da-4939-8036-794574db85d5', 'table_type', '数据库表类型', 'TT_10', '业务属性表', NULL, '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('02b2b8b2-1411-4797-8e11-156301290367', 'table_type', '数据库表类型', 'TT_11', '业务关系表', NULL, '1', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('7bd742a8-c649-470b-8b7c-1b0430fa4c01', 'table_type', '数据库表类型', 'TT_20', '点空间表', NULL, '2', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('bf8dd52f-e8d3-431e-8eb6-db63b4bacce1', 'table_type', '数据库表类型', 'TT_21', '线空间表', NULL, '3', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('d4afecd3-5262-4f7c-bc85-a46511569680', 'table_type', '数据库表类型', 'TT_22', '面空间表', NULL, '4', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('04005f8f-2042-4913-b477-5a925755acf6', 'oracle_fields_type', 'oracle数据库字段类型', 'varchar2', 'varchar2', '字符串（不含中文，最大4KB）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('c1e503a7-e3cc-4c0c-8792-8b5b7f6aca61', 'oracle_fields_type', 'oracle数据库字段类型', 'nvarchar2', 'nvarchar2', '字符串（包含中文，最大4KB，约1333个汉字）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('b9e83ef4-89e1-4ee4-8cfa-36a8ef096955', 'oracle_fields_type', 'oracle数据库字段类型', 'integer', 'integer', '整数（不推荐使用）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('a625813e-c529-4cfe-b8bf-a9cff678dc26', 'oracle_fields_type', 'oracle数据库字段类型', 'number', 'number(P,S)', 'P为整数位，S位小数位', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('4b710f1c-bfbe-473e-bb29-9372f1f5b5b2', 'oracle_fields_type', 'oracle数据库字段类型', 'decimal', 'decimal(P,S)', 'P为整数位，S位小数位', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('efd72a02-ec0f-4767-8f0b-ac82de2bc93e', 'oracle_fields_type', 'oracle数据库字段类型', 'blob', 'blob', '二进制类型（适用于文件存储，最大4G）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('13bab619-3c73-47df-90cf-e1b5c73e1833', 'oracle_fields_type', 'oracle数据库字段类型', 'clob', 'clob', '大文本类型（最大4G）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('7a3c8216-9a02-463f-aec9-0d601c05386a', 'oracle_fields_type', 'oracle数据库字段类型', 'date', 'date', '日期时间类型', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('c38e7436-1be5-445e-9d44-65c7ad295333', 'oracle_fields_type', 'oracle数据库字段类型', 'timestamp', 'timestamp(p)', '高精度日期时间类型（p精度，范围0~9）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('a3173502-49e1-4bd5-815a-d98ec4d45cf9', 'mysql_fields_type', 'mysql数据库字段类型', 'varchar', 'varchar(n)', '字符串（包含中文，最大长度21845）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('4dc35249-d582-42c5-9808-86e0f22beab1', 'mysql_fields_type', 'mysql数据库字段类型', 'tinyint', 'tinyint', '有符号（-128~127）无符号（0~255）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('e7de0f02-6aba-4fd0-8903-8eafa580d81e', 'mysql_fields_type', 'mysql数据库字段类型', 'smallint', 'smallint', '有符号（-32768~32767)无符号（0~65535） ', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('2e60cb38-3b77-479b-84ad-ac1067b69a5a', 'mysql_fields_type', 'mysql数据库字段类型', 'mediumint', 'mediumint', '有符号（-8338608~8388607）无符号（0~16777215）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('bfe2b023-f98c-41eb-8bb8-44caf8711104', 'mysql_fields_type', 'mysql数据库字段类型', 'int', 'int', '有符号（-2147483648~2147483647）无符号（0~4294967295）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('93aa6b87-9f33-4d3f-ba0b-c6a64d080d11', 'mysql_fields_type', 'mysql数据库字段类型', 'float', 'float', '4字节', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('6f1070d1-91cb-4f2e-8275-4d42dea92bd1', 'mysql_fields_type', 'mysql数据库字段类型', 'double', 'double', '8字节', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('26a8dc32-cdb6-45e5-b573-6d6e62cf216d', 'mysql_fields_type', 'mysql数据库字段类型', 'decimal', 'decimal(m,d)', 'm为整数位，d位小数位', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('b9bb4964-a69a-4a9d-90aa-ed25aad10894', 'mysql_fields_type', 'mysql数据库字段类型', 'text', 'text', '64KB', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('73707391-1511-4375-8153-55e8dfd22f17', 'mysql_fields_type', 'mysql数据库字段类型', 'mediumtext', 'mediumtext', '16M', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('3c75fc0e-027a-4ec3-aad5-45de4c6a21b2', 'mysql_fields_type', 'mysql数据库字段类型', 'longtext', 'longtext', '4G', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('e0223d28-e52c-44bb-8aa6-709d27695b98', 'mysql_fields_type', 'mysql数据库字段类型', 'blob', 'blob', '64KB', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('045f20ff-69dc-4442-80c9-48850027bae3', 'mysql_fields_type', 'mysql数据库字段类型', 'mediumblob', 'mediumblob', '16M', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('15b0e3a5-09e0-4184-921b-23160811897d', 'mysql_fields_type', 'mysql数据库字段类型', 'longblob', 'longblob', '4G', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('0e8f192c-8b76-4436-92c1-96ff9bbc64be', 'mysql_fields_type', 'mysql数据库字段类型', 'date', 'date', 'YYYY-MM-DD', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('d527764d-607a-4a81-ae54-e40783f62412', 'mysql_fields_type', 'mysql数据库字段类型', 'time', 'time', 'HH:MM:SS', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('0c03819d-115d-46a3-a4e7-88ede0a5ec8d', 'mysql_fields_type', 'mysql数据库字段类型', 'datetime', 'datetime', 'YYYY-MM-DD HH:MM:SS', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('85a3e694-af6a-47b6-af99-9870520c4204', 'mysql_fields_type', 'mysql数据库字段类型', 'timestamp', 'timestamp', 'timestamp', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('7fd5d65f-b90a-4c0d-9b03-5c1ee5e935bf', 'pg_fields_type', 'postgreSQL数据库字段类型', 'varchar', 'varchar(size)', '字符串', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('aa311066-2dbe-4547-9631-9fe14423a733', 'pg_fields_type', 'postgreSQL数据库字段类型', 'smallint', 'smallint', '有符号（-32768~32767）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('c252c02f-87eb-42d9-8e12-d6ff93ac92fc', 'pg_fields_type', 'postgreSQL数据库字段类型', 'integer', 'integer', '有符号（-2147483648~2147483647）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('1c87448d-ef02-40bc-980a-5a2596310dcf', 'pg_fields_type', 'postgreSQL数据库字段类型', 'real', 'real', '4字节（6位数字精度）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('96a0433a-f9d1-4d41-b6ad-5681d73c408c', 'pg_fields_type', 'postgreSQL数据库字段类型', 'double', 'double', '8字节（15位数字精度）', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('f068eb6c-31cd-417d-bf35-8ad3910b0d31', 'pg_fields_type', 'postgreSQL数据库字段类型', 'numeric', 'numeric(p,s)', 'p为整数位，s位小数位', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('04429b2f-6e74-409d-a4ef-ea23ccfc97c1', 'pg_fields_type', 'postgreSQL数据库字段类型', 'decimal', 'decimal(p,s)', 'p为整数位，s位小数位', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('4538e49a-eb78-42b4-94a4-ab8fd660a4bf', 'pg_fields_type', 'postgreSQL数据库字段类型', 'text', 'text', '文本', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('f3d8b299-7b70-4085-95b5-f81a9eac824f', 'pg_fields_type', 'postgreSQL数据库字段类型', 'date', 'date', 'YYYY-MM-DD', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('b54ff40d-a456-4764-8853-6644e6e4d9e8', 'pg_fields_type', 'postgreSQL数据库字段类型', 'time', 'time', 'HH:MM:SS', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('7672c0e7-c575-4333-8d8e-314320059b41', 'pg_fields_type', 'postgreSQL数据库字段类型', 'timestamp', 'timestamp', 'timestamp', '0', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('afab443e-f0f4-4810-86b9-0649c8b1f005', 'ui_type', 'UI控件类型', 'UT_01', '文本输入框', '', '10', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('1379e709-818a-4675-8f52-dd46697d539f', 'ui_type', 'UI控件类型', 'UT_14', '数字输入框', '', '20', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('2f5b94e9-3817-4649-b7e9-b6f529a804ce', 'ui_type', 'UI控件类型', 'UT_02', '时间输入框', '', '30', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('6eecd829-c26a-4d20-af15-6d5fe162e6e3', 'ui_type', 'UI控件类型', 'UT_03', '日期输入框', '', '40', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('c7b0287d-9b3f-4ef2-9f16-4f5be790a775', 'ui_type', 'UI控件类型', 'UT_04', '时间日期输入框', '', '50', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('4b12d1e2-65cf-4bbf-87eb-50bc32d2ccaa', 'ui_type', 'UI控件类型', 'UT_05', '单选框(sql)', '', '60', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('253005b4-bc7d-4326-8a7e-3ed18eb054ec', 'ui_type', 'UI控件类型', 'UT_06', '单选框(json)', '', '70', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('1612535a-b418-4902-8ece-8132185a864e', 'ui_type', 'UI控件类型', 'UT_07', '复选框(sql)', '', '80', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('6299de0a-2821-496f-b197-f4765a55dbfd', 'ui_type', 'UI控件类型', 'UT_08', '复选框(json)', '', '90', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('52eb5091-64eb-4316-be8c-e7347ecff36e', 'ui_type', 'UI控件类型', 'UT_09', '下拉多选框(sql)', '', '100', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('68221361-24ad-49a3-862a-a0e37b2a919d', 'ui_type', 'UI控件类型', 'UT_10', '下拉多选框(json)', '', '110', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('f1e032d0-53af-4b6f-b1c4-3a4492c01524', 'ui_type', 'UI控件类型', 'UT_11', '下拉选择框(sql)', '', '120', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('9b9a97f8-e60d-4134-a86e-e28022aa0d7f', 'ui_type', 'UI控件类型', 'UT_12', '下拉选择框(json)', '', '130', '1');
INSERT INTO custom_dict(oid, dict_type, dict_type_cn, dict_code, dict_value, dict_value_desc, row_index, active) VALUES ('fb07f70c-5354-43ec-887e-a5da99460e6f', 'ui_type', 'UI控件类型', 'UT_13', '文本域', '', '130', '1');


/** 自定义功能配置, 唯一性验证 */
create table custom_fun_unique_validate(
	oid varchar(36) not null primary key,
	function_code varchar(24) not null ,
	unique_condition varchar(128)  ,
	unique_field varchar(48) not null ,
	unique_field_message varchar(64)  ,
	condition_expression varchar(64)  ,
	logical_del_field varchar(24)  ,
	create_user_id varchar(36) not null ,
	create_user_name varchar(50) not null ,
	create_datetime timestamp not null ,
	modify_user_id varchar(36) not null ,
	modify_user_name varchar(50) not null ,
	modify_datetime timestamp not null ,
	active smallint not null
);

comment on table custom_fun_unique_validate is '自定义功能配置, 唯一性验证';
comment on column custom_fun_unique_validate.oid is '主键';
comment on column custom_fun_unique_validate.function_code is '功能编号';
comment on column custom_fun_unique_validate.unique_condition is '唯一性条件(逗号分隔)';
comment on column custom_fun_unique_validate.unique_field is '唯一性字段';
comment on column custom_fun_unique_validate.unique_field_message is '提示信息';
comment on column custom_fun_unique_validate.condition_expression is '表达式';
comment on column custom_fun_unique_validate.logical_del_field is '逻辑有效字段(默认为active)';

CREATE TABLE pri_application (
oid varchar(36)  NOT NULL,
app_name varchar(50)  NOT NULL,
description varchar(200) ,
role_id varchar(36) ,
app_url varchar(200) ,
create_user_id varchar(36) ,
create_user_name varchar(50) ,
create_datetime timestamp(6),
modify_user_id varchar(36) ,
modify_user_name varchar(50) ,
modify_datetime timestamp(6),
active int2 DEFAULT 1 NOT NULL,
CONSTRAINT pri_application_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE pri_application OWNER TO postgres;

COMMENT ON TABLE pri_application IS '应用系统表';

COMMENT ON COLUMN pri_application.oid IS '主键';

COMMENT ON COLUMN pri_application.app_name IS '应用系统名称';

COMMENT ON COLUMN pri_application.description IS '应用系统描述';

COMMENT ON COLUMN pri_application.role_id IS '应用系统管理角色ID';

COMMENT ON COLUMN pri_application.app_url IS '应用系统前缀';

COMMENT ON COLUMN pri_application.create_user_id IS '记录创建人ID';

COMMENT ON COLUMN pri_application.create_user_name IS '记录创建人姓名';

COMMENT ON COLUMN pri_application.create_datetime IS '记录创建时间';

COMMENT ON COLUMN pri_application.modify_user_id IS '最近修改人ID';

COMMENT ON COLUMN pri_application.modify_user_name IS '最近修改人姓名';

COMMENT ON COLUMN pri_application.modify_datetime IS '最近修改时间';

COMMENT ON COLUMN pri_application.active IS '数据有效标识：1有效 0无效';


CREATE TABLE pri_func_privilege (
oid varchar(36)  NOT NULL,
parent_id varchar(36) ,
privilege_name varchar(50)  NOT NULL,
privilege_code varchar(50)  NOT NULL,
privilege_type varchar(20)  NOT NULL,
open_type varchar(20) ,
url varchar(2000) ,
description varchar(200) ,
app_id varchar(36)  NOT NULL,
hierarchy varchar(200)  NOT NULL,
hlevel varchar(20)  NOT NULL,
order_num int2 NOT NULL,
create_user_id varchar(36) ,
create_user_name varchar(50) ,
create_datetime timestamp(6),
modify_user_id varchar(36) ,
modify_user_name varchar(50) ,
modify_datetime timestamp(6),
active int2 DEFAULT 1 NOT NULL,
icon varchar(50) ,
function_type varchar(50) ,
query_name varchar(200) ,
CONSTRAINT pri_func_privilege_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE pri_func_privilege OWNER TO postgres;

COMMENT ON TABLE pri_func_privilege IS '功能权限表（包含菜单权限、功能权限、表单权限）';

COMMENT ON COLUMN pri_func_privilege.oid IS '主键';

COMMENT ON COLUMN pri_func_privilege.parent_id IS '父权限id';

COMMENT ON COLUMN pri_func_privilege.privilege_name IS '权限名称';

COMMENT ON COLUMN pri_func_privilege.privilege_code IS '权限编码';

COMMENT ON COLUMN pri_func_privilege.privilege_type IS '权限类型：1菜单 2功能点 3表单 4 表单字段';

COMMENT ON COLUMN pri_func_privilege.open_type IS '菜单的打开方式：1表示tab方式，2表示新页面方式，3表示弹窗';

COMMENT ON COLUMN pri_func_privilege.url IS '权限url';

COMMENT ON COLUMN pri_func_privilege.description IS '权限说明';

COMMENT ON COLUMN pri_func_privilege.app_id IS '应用系统ID';

COMMENT ON COLUMN pri_func_privilege.hierarchy IS '层级编号';

COMMENT ON COLUMN pri_func_privilege.hlevel IS '层级等级';

COMMENT ON COLUMN pri_func_privilege.order_num IS '排序编号';

COMMENT ON COLUMN pri_func_privilege.create_user_id IS '记录创建人ID';

COMMENT ON COLUMN pri_func_privilege.create_user_name IS '记录创建人姓名';

COMMENT ON COLUMN pri_func_privilege.create_datetime IS '记录创建时间';

COMMENT ON COLUMN pri_func_privilege.modify_user_id IS '最近修改人ID';

COMMENT ON COLUMN pri_func_privilege.modify_user_name IS '最近修改人姓名';

COMMENT ON COLUMN pri_func_privilege.modify_datetime IS '最近修改时间';

COMMENT ON COLUMN pri_func_privilege.active IS '数据有效标识：1有效 0无效';

COMMENT ON COLUMN pri_func_privilege.function_type IS '功能点类型';

CREATE TABLE pri_role (
oid varchar(36)  NOT NULL,
role_name varchar(50)  NOT NULL,
role_type varchar(20)  NOT NULL,
unit_id varchar(36)  NOT NULL,
description varchar(200) ,
create_user_id varchar(36) ,
create_user_name varchar(50) ,
create_datetime timestamp(6),
modify_user_id varchar(36) ,
modify_user_name varchar(50) ,
modify_datetime timestamp(6),
active int2 DEFAULT 1 NOT NULL,
CONSTRAINT pri_role_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE pri_role OWNER TO postgres;

COMMENT ON TABLE pri_role IS '[权限]角色表';

COMMENT ON COLUMN pri_role.oid IS '主键';

COMMENT ON COLUMN pri_role.role_name IS '角色名';

COMMENT ON COLUMN pri_role.role_type IS '角色类型包括私有和保护两种。具体业务规则为：私有角色为角色所属部门私有的角色，其他部门的人员对其不可见，保护类型的角色的可见范围所属部门及其下属部门人员可见。该规则主要用于系统管理的分级管理，上级管理员可以设定保护角色，则下级管理员可以直接应用，但不能对其修改。';

COMMENT ON COLUMN pri_role.unit_id IS '角色所属部门ID';

COMMENT ON COLUMN pri_role.description IS '角色说明';

COMMENT ON COLUMN pri_role.create_user_id IS '记录创建人ID';

COMMENT ON COLUMN pri_role.create_user_name IS '记录创建人姓名';

COMMENT ON COLUMN pri_role.create_datetime IS '记录创建时间';

COMMENT ON COLUMN pri_role.modify_user_id IS '最近修改人ID';

COMMENT ON COLUMN pri_role.modify_user_name IS '最近修改人姓名';

COMMENT ON COLUMN pri_role.modify_datetime IS '最近修改时间';

COMMENT ON COLUMN pri_role.active IS '数据有效标识：1有效 0无效';

CREATE TABLE pri_role_func_privilege_ref (
oid varchar(36)  NOT NULL,
role_id varchar(36)  NOT NULL,
func_privilege_id varchar(36)  NOT NULL,
create_user_id varchar(36) ,
create_user_name varchar(50) ,
create_datetime timestamp(6),
modify_user_id varchar(36) ,
modify_user_name varchar(50) ,
modify_datetime timestamp(6),
active int2 DEFAULT 1 NOT NULL,
app_id varchar(36)  NOT NULL,
CONSTRAINT pri_role_func_privilege_ref_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE pri_role_func_privilege_ref OWNER TO postgres;

COMMENT ON TABLE pri_role_func_privilege_ref IS '角色与权限关联表';

COMMENT ON COLUMN pri_role_func_privilege_ref.oid IS '主键';

COMMENT ON COLUMN pri_role_func_privilege_ref.role_id IS '角色id';

COMMENT ON COLUMN pri_role_func_privilege_ref.func_privilege_id IS '权限id';

COMMENT ON COLUMN pri_role_func_privilege_ref.create_user_id IS '记录创建人ID';

COMMENT ON COLUMN pri_role_func_privilege_ref.create_user_name IS '记录创建人姓名';

COMMENT ON COLUMN pri_role_func_privilege_ref.create_datetime IS '记录创建时间';

COMMENT ON COLUMN pri_role_func_privilege_ref.modify_user_id IS '最近修改人ID';

COMMENT ON COLUMN pri_role_func_privilege_ref.modify_user_name IS '最近修改人姓名';

COMMENT ON COLUMN pri_role_func_privilege_ref.modify_datetime IS '最近修改时间';

COMMENT ON COLUMN pri_role_func_privilege_ref.active IS '数据有效标识：1有效 0无效';

CREATE TABLE pri_unit (
oid varchar(36)  NOT NULL,
parent_id varchar(36) ,
unit_name varchar(50)  NOT NULL,
unit_code varchar(50)  NOT NULL,
description varchar(200) ,
unit_type varchar(20)  NOT NULL,
address varchar(200) ,
phone varchar(50) ,
addata int2,
ext_field1 varchar(500) ,
ext_field2 varchar(500) ,
ext_field3 varchar(500) ,
ext_field4 varchar(500) ,
ext_field5 varchar(500) ,
hierarchy varchar(200)  NOT NULL,
hlevel varchar(20)  NOT NULL,
order_num int2 NOT NULL,
create_user_id varchar(36) ,
create_user_name varchar(50) ,
create_datetime timestamp(6),
modify_user_id varchar(36) ,
modify_user_name varchar(50) ,
modify_datetime timestamp(6),
active int2 DEFAULT 1 NOT NULL,
CONSTRAINT pri_unit_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE pri_unit OWNER TO postgres;

COMMENT ON TABLE pri_unit IS '组织机构表';

COMMENT ON COLUMN pri_unit.oid IS '主键';

COMMENT ON COLUMN pri_unit.parent_id IS '父组织机构id';

COMMENT ON COLUMN pri_unit.unit_name IS '组织机构名称';

COMMENT ON COLUMN pri_unit.unit_code IS '组织机构编码';

COMMENT ON COLUMN pri_unit.description IS '组织机构说明';

COMMENT ON COLUMN pri_unit.unit_type IS '组织机构类型分两种，部门和机关，划分的依据是组织机构所管理的业务数据范围的不同。部门可以管理本部门及下属部门和机关的业务数据，机关可以管理的业务数据范围与机关所属的部门相同。1代表机关，2代表部门';

COMMENT ON COLUMN pri_unit.address IS '组织机构地址';

COMMENT ON COLUMN pri_unit.phone IS '组织机构联系电话';

COMMENT ON COLUMN pri_unit.addata IS '0表示平台数据，1表示ldap上的数据';

COMMENT ON COLUMN pri_unit.ext_field1 IS '备用字段';

COMMENT ON COLUMN pri_unit.ext_field2 IS '备用字段';

COMMENT ON COLUMN pri_unit.ext_field3 IS '备用字段';

COMMENT ON COLUMN pri_unit.ext_field4 IS '备用字段';

COMMENT ON COLUMN pri_unit.ext_field5 IS '备用字段';

COMMENT ON COLUMN pri_unit.hierarchy IS '层级编号';

COMMENT ON COLUMN pri_unit.hlevel IS '层级等级';

COMMENT ON COLUMN pri_unit.order_num IS '同级排序编号';

COMMENT ON COLUMN pri_unit.create_user_id IS '记录创建人ID';

COMMENT ON COLUMN pri_unit.create_user_name IS '记录创建人姓名';

COMMENT ON COLUMN pri_unit.create_datetime IS '记录创建时间';

COMMENT ON COLUMN pri_unit.modify_user_id IS '最近修改人ID';

COMMENT ON COLUMN pri_unit.modify_user_name IS '最近修改人姓名';

COMMENT ON COLUMN pri_unit.modify_datetime IS '最近修改时间';

COMMENT ON COLUMN pri_unit.active IS '数据有效标识：1有效 0无效';

CREATE TABLE pri_user (
oid varchar(36)  NOT NULL,
user_name varchar(50)  NOT NULL,
login_name varchar(50)  NOT NULL,
password varchar(100)  NOT NULL,
password_expired_date timestamp(6),
validdate timestamp(6),
address varchar(200) ,
phone varchar(50) ,
email varchar(100) ,
unit_id varchar(36)  NOT NULL,
user_type varchar(20) ,
addata int2,
description varchar(200) ,
ext_field1 varchar(500) ,
ext_field2 varchar(500) ,
ext_field3 varchar(500) ,
ext_field4 varchar(500) ,
ext_field5 varchar(500) ,
find_pass_validcode varchar(50) ,
find_pass_validtime timestamp(6),
create_user_id varchar(36) ,
create_user_name varchar(50) ,
create_datetime timestamp(6),
modify_user_id varchar(36) ,
modify_user_name varchar(50) ,
modify_datetime timestamp(6),
active int2 DEFAULT 1 NOT NULL,
CONSTRAINT pri_user_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE pri_user OWNER TO postgres;

COMMENT ON TABLE pri_user IS '用户表';

COMMENT ON COLUMN pri_user.oid IS '主键';

COMMENT ON COLUMN pri_user.user_name IS '用户姓名';

COMMENT ON COLUMN pri_user.login_name IS '登陆名';

COMMENT ON COLUMN pri_user.password IS '登陆密码  MD5码';

COMMENT ON COLUMN pri_user.password_expired_date IS '密码到期时间';

COMMENT ON COLUMN pri_user.validdate IS '有效日期';

COMMENT ON COLUMN pri_user.address IS '地址';

COMMENT ON COLUMN pri_user.phone IS '电话';

COMMENT ON COLUMN pri_user.email IS '电子邮件地址';

COMMENT ON COLUMN pri_user.unit_id IS '所属部门ID';

COMMENT ON COLUMN pri_user.user_type IS '用户类型';

COMMENT ON COLUMN pri_user.addata IS '0表示平台数据，1表示ldap上的数据';

COMMENT ON COLUMN pri_user.description IS '说明';

COMMENT ON COLUMN pri_user.ext_field1 IS '备用字段';

COMMENT ON COLUMN pri_user.ext_field2 IS '备用字段';

COMMENT ON COLUMN pri_user.ext_field3 IS '备用字段';

COMMENT ON COLUMN pri_user.ext_field4 IS '备用字段';

COMMENT ON COLUMN pri_user.ext_field5 IS '备用字段';

COMMENT ON COLUMN pri_user.find_pass_validcode IS '找回密码验证码，暂时无用';

COMMENT ON COLUMN pri_user.find_pass_validtime IS '找回密码有效时间，暂时无用';

COMMENT ON COLUMN pri_user.create_user_id IS '记录创建人ID';

COMMENT ON COLUMN pri_user.create_user_name IS '记录创建人姓名';

COMMENT ON COLUMN pri_user.create_datetime IS '记录创建时间';

COMMENT ON COLUMN pri_user.modify_user_id IS '最近修改人ID';

COMMENT ON COLUMN pri_user.modify_user_name IS '最近修改人姓名';

COMMENT ON COLUMN pri_user.modify_datetime IS '最近修改时间';

COMMENT ON COLUMN pri_user.active IS '数据有效标识：1有效 0无效';


CREATE TABLE pri_user_role_ref (
oid varchar(36)  NOT NULL,
user_id varchar(36)  NOT NULL,
role_id varchar(36)  NOT NULL,
create_user_id varchar(36) ,
create_user_name varchar(50) ,
create_datetime timestamp(6),
modify_user_id varchar(36) ,
modify_user_name varchar(50) ,
modify_datetime timestamp(6),
active int2 DEFAULT 1 NOT NULL,
CONSTRAINT pri_user_role_ref_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE pri_user_role_ref OWNER TO postgres;

COMMENT ON TABLE pri_user_role_ref IS '用户与角色对应关系表';

COMMENT ON COLUMN pri_user_role_ref.oid IS '主键';

COMMENT ON COLUMN pri_user_role_ref.user_id IS '用户id';

COMMENT ON COLUMN pri_user_role_ref.role_id IS '角色id';

COMMENT ON COLUMN pri_user_role_ref.create_user_id IS '记录创建人ID';

COMMENT ON COLUMN pri_user_role_ref.create_user_name IS '记录创建人姓名';

COMMENT ON COLUMN pri_user_role_ref.create_datetime IS '记录创建时间';

COMMENT ON COLUMN pri_user_role_ref.modify_user_id IS '最近修改人ID';

COMMENT ON COLUMN pri_user_role_ref.modify_user_name IS '最近修改人姓名';

COMMENT ON COLUMN pri_user_role_ref.modify_datetime IS '最近修改时间';

COMMENT ON COLUMN pri_user_role_ref.active IS '数据有效标识：1有效 0无效';


CREATE TABLE sys_domain (
oid varchar(36)  NOT NULL,
domain_name varchar(50)  NOT NULL,
code_id varchar(50) ,
parent_code_id varchar(36) ,
code_name varchar(50) ,
ordinal int2,
description varchar ,
create_user_id varchar(36) ,
create_user_name varchar(50) ,
create_datetime timestamp(6),
modify_user_id varchar(36) ,
modify_user_name varchar(50) ,
modify_datetime timestamp(6),
active int2 NOT NULL,
CONSTRAINT sys_domain_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE sys_domain OWNER TO postgres;

COMMENT ON TABLE sys_domain IS '域值表';

COMMENT ON COLUMN sys_domain.oid IS '主键';

COMMENT ON COLUMN sys_domain.domain_name IS '域名称';

COMMENT ON COLUMN sys_domain.code_id IS '域值Id';

COMMENT ON COLUMN sys_domain.parent_code_id IS '父节点Id';

COMMENT ON COLUMN sys_domain.code_name IS '域值名称';

COMMENT ON COLUMN sys_domain.ordinal IS '序号';

COMMENT ON COLUMN sys_domain.description IS '描述';

CREATE INDEX index_sys_domain_domain_name_5 ON sys_domain USING btree (domain_name);



CREATE TABLE sys_excel_template (
oid varchar(36)  NOT NULL,
excel_template_name varchar(50) ,
excel_template_code varchar(50)  NOT NULL,
excel_template_type varchar(36) ,
remark varchar(200) ,
create_user_id varchar(36) ,
create_user_name varchar(50) ,
create_datetime timestamp(6),
modify_user_id varchar(36) ,
modify_user_name varchar(50) ,
modify_datetime timestamp(6),
active int2 NOT NULL,
excel_template_path varchar(255) ,
CONSTRAINT sys_excel_template_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE sys_excel_template OWNER TO postgres;

COMMENT ON TABLE sys_excel_template IS 'excel导入导出模板管理表';

COMMENT ON COLUMN sys_excel_template.oid IS '主键';

COMMENT ON COLUMN sys_excel_template.excel_template_name IS '管线名称';

COMMENT ON COLUMN sys_excel_template.excel_template_code IS '管线编号';

COMMENT ON COLUMN sys_excel_template.excel_template_type IS '项目id';

COMMENT ON COLUMN sys_excel_template.remark IS '备注';

COMMENT ON COLUMN sys_excel_template.excel_template_path IS '模板存储路径';

CREATE INDEX index_sys_excel_template_excel_template_code_6 ON sys_excel_template USING btree (excel_template_code);

CREATE INDEX index_sys_excel_template_excel_template_name_5 ON sys_excel_template USING btree (excel_template_name);


CREATE TABLE sys_login_log (
oid varchar(36)  NOT NULL,
user_id varchar(36)  NOT NULL,
user_name varchar(50)  NOT NULL,
login_name varchar(50)  NOT NULL,
unit_id varchar(36)  NOT NULL,
unit_name varchar(50)  NOT NULL,
unit_name_fullpath varchar(200) ,
token varchar(100)  NOT NULL,
app_id varchar(36) ,
app_name varchar(50) ,
login_datetime timestamp(6) NOT NULL,
exit_datetime timestamp(6),
server_ip varchar(25) ,
client_ip varchar(25) ,
os_type varchar(20) ,
browser_type varchar(20) ,
browser_ver varchar(20) ,
screen_dpi varchar(20) ,
CONSTRAINT sys_login_log_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE sys_login_log OWNER TO postgres;

COMMENT ON TABLE sys_login_log IS '用户登录日志信息表';

COMMENT ON COLUMN sys_login_log.oid IS '主键';

COMMENT ON COLUMN sys_login_log.user_id IS '用户ID';

COMMENT ON COLUMN sys_login_log.user_name IS '用户姓名';

COMMENT ON COLUMN sys_login_log.login_name IS '用户登录名';

COMMENT ON COLUMN sys_login_log.unit_id IS '用户部门ID';

COMMENT ON COLUMN sys_login_log.unit_name IS '用户部门名称';

COMMENT ON COLUMN sys_login_log.unit_name_fullpath IS '用户部门全路径';

COMMENT ON COLUMN sys_login_log.token IS 'token值';

COMMENT ON COLUMN sys_login_log.app_id IS '登录的应用ID';

COMMENT ON COLUMN sys_login_log.app_name IS '登录的应用名称';

COMMENT ON COLUMN sys_login_log.login_datetime IS '登录时间';

COMMENT ON COLUMN sys_login_log.exit_datetime IS '退出时间';

COMMENT ON COLUMN sys_login_log.server_ip IS '服务IP';

COMMENT ON COLUMN sys_login_log.client_ip IS '客户端IP';

COMMENT ON COLUMN sys_login_log.os_type IS '操作系统类型';

COMMENT ON COLUMN sys_login_log.browser_type IS '浏览器类型';

COMMENT ON COLUMN sys_login_log.browser_ver IS '浏览器版本';

COMMENT ON COLUMN sys_login_log.screen_dpi IS '屏幕分辨率';


CREATE INDEX index_sys_login_log_oid_140 ON sys_login_log USING btree (oid);



CREATE TABLE log_business (
oid varchar(36)  NOT NULL,
business_id varchar(36)  NOT NULL,
opt_type varchar(50)  NOT NULL,
detail text ,
remark varchar(2000) ,
create_user_id varchar(36)  NOT NULL,
create_user_name varchar(50)  NOT NULL,
create_time timestamp(6),
CONSTRAINT log_business_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE log_business OWNER TO postgres;

COMMENT ON TABLE log_business IS '业务日志记录表';

COMMENT ON COLUMN log_business.oid IS '主键';

COMMENT ON COLUMN log_business.business_id IS '业务数据ID';

COMMENT ON COLUMN log_business.opt_type IS '操作类型';

COMMENT ON COLUMN log_business.detail IS '详情。详情。若是更新，必须是json数组：[{dataItem:自主外包费,newValue:1000.0,oldValue:50000.0}]），其他类型则无限制';

COMMENT ON COLUMN log_business.remark IS '备注';


CREATE TABLE log_operate (
oid varchar(36)  NOT NULL,
business_id varchar(36) ,
business_name varchar(50) ,
table_name varchar(200) ,
opt_type varchar(50)  NOT NULL,
detail varchar(2000) ,
remark varchar(2000) ,
create_user_id varchar(36)  NOT NULL,
create_user_name varchar(50)  NOT NULL,
create_time timestamp(6),
app_id varchar(36) ,
app_name varchar(50) ,
CONSTRAINT log_operate_pkey PRIMARY KEY (oid)
)
WITH (OIDS=FALSE)
;

ALTER TABLE log_operate OWNER TO postgres;

COMMENT ON TABLE log_operate IS '操作日志记录表';

COMMENT ON COLUMN log_operate.oid IS '主键';

COMMENT ON COLUMN log_operate.business_id IS '业务数据ID';

COMMENT ON COLUMN log_operate.business_name IS '业务名称';

COMMENT ON COLUMN log_operate.table_name IS '业务表名';

COMMENT ON COLUMN log_operate.opt_type IS '操作类型';

COMMENT ON COLUMN log_operate.detail IS '详情';

COMMENT ON COLUMN log_operate.remark IS '备注';

COMMENT ON COLUMN log_operate.create_user_id IS '操作人ID';

COMMENT ON COLUMN log_operate.create_user_name IS '操作人姓名';

COMMENT ON COLUMN log_operate.create_time IS '操作时间';

COMMENT ON COLUMN log_operate.app_id IS '应用Id';

COMMENT ON COLUMN log_operate.app_name IS '应用名称';
/*******框架相关数据库end********/



/**
 * 名称：		空间对象表模块postgresql数据库初始化脚本
 * 创建人：	张锡梓
 * 创建时间：	2018-05-03 09:06	
 * 更新时间:	2018-05-03 19:01 修改记录表中的flag字段值
 * */

/**域值表**/

create table sys_spatial_object( 
	oid varchar(36) not null primary key,
	wkt  TEXT ,
	create_user_id varchar(36)  ,
	create_user_name varchar(50)  ,
	create_datetime timestamp(6)  ,
	modify_user_id varchar(36)  ,
	modify_user_name varchar(50)  ,
	modify_datetime timestamp(6)  ,
	active smallint not null 
);
comment on table  sys_spatial_object is '空间对象表';	
comment on column sys_spatial_object.oid is '主键';	
comment on column sys_spatial_object.wkt is 'wkt';	
comment on column sys_spatial_object.create_user_id is '创建人id';	
comment on column sys_spatial_object.create_user_name is '创建人姓名';	
comment on column sys_spatial_object.create_datetime is '创建时间';	
comment on column sys_spatial_object.modify_user_id is '修改人id';	
comment on column sys_spatial_object.modify_user_name is '修改人姓名';	
comment on column sys_spatial_object.modify_datetime is '修改时间';	
comment on column sys_spatial_object.active is '是否有效';



/***
 * 名称：文档管理系统postgresql初始化脚本
 * 创建人：雷凯
 * 创建时间：2018-05-14 14:37
 * 更新时间：
 */
/**下载申请-文档关联表**/

create table jasdoc_apply_file_ref (
	eventid varchar(38) not null primary key,
	applyeventid varchar(38),
	fileeventid varchar(38),
	filetype numeric
);
comment on table jasdoc_apply_file_ref is '下载申请-文档关联表';
comment on column jasdoc_apply_file_ref.eventid is 'uuid';
comment on column jasdoc_apply_file_ref.applyeventid is '申请id';
comment on column jasdoc_apply_file_ref.fileeventid is '文件或文件夹id';
comment on column jasdoc_apply_file_ref.filetype is '类型，0表示文件，1表示文件夹';

/**文档分类信息**/

create table jasdoc_classify_folder (
	id varchar(36) not null primary key,
	parentid varchar(36) not null,
	foldername varchar(200) not null,
	folderlocation varchar(500),
	createtime timestamp(6),
	createuser varchar(36),
	sort numeric(38),
	hierarchy varchar(208),
	deleteflag varchar(36),
	deleteuser varchar(36),
	updateuser varchar(36),
	updatetime timestamp(6),
	ext_field1 varchar(500),
	ext_field2 varchar(500),
	ext_field3 varchar(500),
	ext_field4 varchar(500),
	ext_field5 varchar(500),
	description varchar(1000)
);
comment on table jasdoc_classify_folder is '文档分类信息';
comment on column jasdoc_classify_folder.id is '节点id';
comment on column jasdoc_classify_folder.parentid is '父类id';
comment on column jasdoc_classify_folder.foldername is '节点名称';
comment on column jasdoc_classify_folder.createtime is '创建时间';
comment on column jasdoc_classify_folder.createuser is '添加数据用户';
comment on column jasdoc_classify_folder.deleteflag is '删除标识';
comment on column jasdoc_classify_folder.deleteuser is '删除该条数据用户id（没有删除则为空）';
comment on column jasdoc_classify_folder.updateuser is '修改数据用户';
comment on column jasdoc_classify_folder.updatetime is '创建时间';
comment on column jasdoc_classify_folder.ext_field1 is '备用字段';
comment on column jasdoc_classify_folder.ext_field2 is '备用字段';
comment on column jasdoc_classify_folder.ext_field3 is '备用字段';
comment on column jasdoc_classify_folder.ext_field4 is '备用字段';
comment on column jasdoc_classify_folder.ext_field5 is '备用字段';
comment on column jasdoc_classify_folder.description is '文件夹描述';

create table jasdoc_deleteindex(
doceventid varchar(38)
);

/**部门对文件夹授权**/

create table jasdoc_dept_folder_ref (
	id varchar(36) not null primary key,
	deptid varchar(36),
	folderid varchar(36),
	folderprivilegetype numeric(3)
);
comment on table jasdoc_dept_folder_ref is '部门对文件夹授权';
comment on column jasdoc_dept_folder_ref.id is 'uuid';
comment on column jasdoc_dept_folder_ref.deptid is '部门id';
comment on column jasdoc_dept_folder_ref.folderid is '资源树节点id';




/**文档下载申请表**/
create table jasdoc_download_apply (
	eventid varchar(38) not null primary key,
	createuser varchar(38),
	createdate timestamp(6),
	reason varchar(500),
	approvestatus numeric,
	remark varchar(500),
	title varchar(100),
	createusername varchar(50),
	updatedate timestamp(6)
);
comment on table jasdoc_download_apply is '文档下载申请表';
comment on column jasdoc_download_apply.eventid is 'uuid';
comment on column jasdoc_download_apply.createuser is '申请人';
comment on column jasdoc_download_apply.createdate is '申请时间';
comment on column jasdoc_download_apply.reason is '申请理由';
comment on column jasdoc_download_apply.approvestatus is '审核状态';
comment on column jasdoc_download_apply.remark is '备注';
comment on column jasdoc_download_apply.title is '标题';
comment on column jasdoc_download_apply.createusername is '申请人姓名';
comment on column jasdoc_download_apply.updatedate is '修改日期';


/**我的收藏夹表信息**/
create table jasdoc_favorite_folder (
	id varchar(36) not null primary key,
	parentid varchar(36) not null,
	foldername varchar(200) not null,
	folderlocation varchar(500),
	createtime timestamp(6),
	createuser varchar(36),
	sort numeric(38),
	hierarchy varchar(208),
	deleteflag varchar(36),
	deleteuser varchar(36),
	updateuser varchar(36),
	updatetime timestamp(6),
	ext_field1 varchar(500),
	ext_field2 varchar(500),
	ext_field3 varchar(500),
	ext_field4 varchar(500),
	ext_field5 varchar(500),
	description varchar(1000)
);
comment on table jasdoc_favorite_folder is '我的收藏夹表信息，文件夹的层次编号在所有文件夹中是唯一的，包括已放入回收站的文件夹。';
comment on column jasdoc_favorite_folder.id is '节点id';
comment on column jasdoc_favorite_folder.parentid is '父类id';
comment on column jasdoc_favorite_folder.foldername is '节点名称';
comment on column jasdoc_favorite_folder.createtime is '创建时间';
comment on column jasdoc_favorite_folder.createuser is '添加数据用户';
comment on column jasdoc_favorite_folder.deleteflag is '删除标识';
comment on column jasdoc_favorite_folder.deleteuser is '删除该条数据用户id（没有删除则为空）';
comment on column jasdoc_favorite_folder.updateuser is '修改数据用户';
comment on column jasdoc_favorite_folder.updatetime is '创建时间';
comment on column jasdoc_favorite_folder.ext_field1 is '备用字段';
comment on column jasdoc_favorite_folder.ext_field2 is '备用字段';
comment on column jasdoc_favorite_folder.ext_field3 is '备用字段';
comment on column jasdoc_favorite_folder.ext_field4 is '备用字段';
comment on column jasdoc_favorite_folder.ext_field5 is '备用字段';
comment on column jasdoc_favorite_folder.description is '文件夹描述';

/****/

create table jasdoc_file (
	eventid varchar(38) not null primary key,
	folderid varchar(38),
	fileno varchar(50),
	filename varchar(200),
	filetype varchar(50),
	filelocation varchar(500),
	author varchar(50),
	keyword varchar(50),
	summary varchar(4000),
	fileobj bytea,
	filesize numeric,
	uploadtime timestamp(6),
	createuser varchar(50),
	createtime timestamp(6),
	updateuser varchar(50),
	updatetime timestamp(6),
	remark varchar(4000),
	deleteflag varchar(50) not null,
	absolutepath varchar(500),
	deleteuser varchar(50),
	iscreateindex numeric(1),
	createusername varchar(50),
	versionnumber varchar(10),
	versionid varchar(38),
	versionreark varchar(300),
	versionupdatetime timestamp(6),
	auditstate numeric(1),
	audituser varchar(50),
	audittime timestamp(6),
	ext_field1 varchar(500),
	ext_field2 varchar(500),
	ext_field3 varchar(500),
	ext_field4 varchar(500),
	ext_field5 varchar(500),
	description varchar(500),
	ispreviewconversion numeric(1),
	isaddwatermark numeric(1),
	updateusername varchar(50)
);
comment on table jasdoc_file is '文档信息';
comment on column jasdoc_file.eventid is 'uuid';
comment on column jasdoc_file.fileno is '编号';
comment on column jasdoc_file.filename is '文件名';
comment on column jasdoc_file.filetype is '文档的格如doc、xls。';
comment on column jasdoc_file.filelocation is '文档存储在的文件夹';
comment on column jasdoc_file.author is '创建者';
comment on column jasdoc_file.keyword is '关键词';
comment on column jasdoc_file.summary is '摘要';
comment on column jasdoc_file.fileobj is '文件本身';
comment on column jasdoc_file.filesize is '文件的大小';
comment on column jasdoc_file.uploadtime is '附件上传时间';
comment on column jasdoc_file.createuser is '创建者';
comment on column jasdoc_file.createtime is '建档时间';
comment on column jasdoc_file.updateuser is '创建者';
comment on column jasdoc_file.updatetime is '最近一次修改的时间';
comment on column jasdoc_file.remark is '备注';
comment on column jasdoc_file.deleteflag is '文档删除标识（被删除后该字段为删除批次）(修改字段)';
comment on column jasdoc_file.absolutepath is '文件存储在服务器的相对路径，相对应用程序的根目录';
comment on column jasdoc_file.deleteuser is '删除者';
comment on column jasdoc_file.iscreateindex is '是否添加索引';
comment on column jasdoc_file.createusername is '创建者';
comment on column jasdoc_file.versionnumber is '版本号';
comment on column jasdoc_file.versionid is '历史版本id';
comment on column jasdoc_file.versionreark is '版本描述';
comment on column jasdoc_file.versionupdatetime is '版本更新时间';
comment on column jasdoc_file.auditstate is '审核状态';
comment on column jasdoc_file.audituser is '审核人';
comment on column jasdoc_file.audittime is '审核时间';
comment on column jasdoc_file.ext_field1 is '备用字段';
comment on column jasdoc_file.ext_field2 is '备用字段';
comment on column jasdoc_file.ext_field3 is '备用字段';
comment on column jasdoc_file.ext_field4 is '备用字段';
comment on column jasdoc_file.ext_field5 is '备用字段';
comment on column jasdoc_file.description is '描述信息';
comment on column jasdoc_file.ispreviewconversion is '是否已经进行预览转换';
comment on column jasdoc_file.isaddwatermark is '是否已经添加水印';
comment on column jasdoc_file.updateusername is '修改者名称';

/**文件关联表**/

create table jasdoc_file_associated (
	eventid varchar(38) not null,
	fileid varchar(38) not null,
	description varchar(2000),
	associateddate timestamp(6),
	associateduserid varchar(38),
	associatedprivilegetype numeric(3),
	associatedfileid varchar(38)
);
comment on table jasdoc_file_associated is '文件关联表';
comment on column jasdoc_file_associated.eventid is 'uuid';
comment on column jasdoc_file_associated.fileid is '文件id';
comment on column jasdoc_file_associated.description is '描述';
comment on column jasdoc_file_associated.associateddate is '关联日期';
comment on column jasdoc_file_associated.associateduserid is '关联人id';

/****/

create table jasdoc_file_backup_recovery (
	eventid varchar(38) not null primary key,
	filedescription varchar(2000),
	createdby varchar(100),
	createddate timestamp(6),
	remarks varchar(2000),
	backupdatabasepath varchar(200),
	backupfilepath varchar(200),
	backupindexpath varchar(200),
	backuptaskdate timestamp(6),
	isopenbackupfunction numeric(1),
	executionfrequency numeric(2)
);
comment on column jasdoc_file_backup_recovery.eventid is 'uuid';
comment on column jasdoc_file_backup_recovery.filedescription is '描述';
comment on column jasdoc_file_backup_recovery.createdby is '创建人';
comment on column jasdoc_file_backup_recovery.createddate is '创建时间';
comment on column jasdoc_file_backup_recovery.backupdatabasepath is '备份路径';
comment on column jasdoc_file_backup_recovery.backupfilepath is '文件路径';

/**文档与文档分类关联信息**/

create table jasdoc_file_classify_ref (
	fileid varchar(41) not null primary key,
	folderid varchar(36) not null,
	eventid varchar(38) not null
);
comment on table jasdoc_file_classify_ref is '文档与文档分类关联信息';
comment on column jasdoc_file_classify_ref.fileid is '文件id';
comment on column jasdoc_file_classify_ref.folderid is '父类id';
comment on column jasdoc_file_classify_ref.eventid is '主键id';

/**文档与收藏夹关联信息**/

create table jasdoc_file_favorite_ref (
	fileid varchar(41) not null,
	folderid varchar(36) not null,
	eventid varchar(38) not null primary key
);
comment on table jasdoc_file_favorite_ref is '文档与收藏夹关联信息；';
comment on column jasdoc_file_favorite_ref.fileid is '文件id';
comment on column jasdoc_file_favorite_ref.folderid is '父类id';
comment on column jasdoc_file_favorite_ref.eventid is '主键id';

/***1.文档与文档分类关联信息； 2.文档与我的收藏夹关联信息。***/

create table jasdoc_file_folder_ref (
	fileid varchar(41) not null,
	folderid varchar(36)  not null
);
comment on table jasdoc_file_folder_ref is '1.文档与文档分类关联信息； 2.文档与我的收藏夹关联信息。';
comment on column jasdoc_file_folder_ref.folderid is '节点id';

/**文档历史**/

create table jasdoc_file_history (
	sid numeric(27),
	eventid varchar(36) not null primary key,
	folderid varchar(36),
	fileno varchar(50),
	filename varchar(80),
	filetype varchar(20),
	filelocation varchar(500),
	author varchar(50),
	keyword varchar(50),
	summary varchar(4000),
	fileobj bytea,
	filesize numeric,
	uploadtime timestamp(6),
	createuser varchar(50),
	createtime timestamp(6),
	updateuser varchar(50),
	updatetime timestamp(6),
	remark varchar(4000),
	deleteflag varchar(50) not null,
	absolutepath varchar(300),
	deleteuser varchar(50),
	iscreateindex numeric(1),
	createusername varchar(50),
	versionnumber varchar(10),
	versionid varchar(36) not null,
	versionreark varchar(300),
	versionupdatetime timestamp(6)
);
comment on table jasdoc_file_history is '文档历史';
comment on column jasdoc_file_history.eventid is 'uuid';
comment on column jasdoc_file_history.fileno is '编号';
comment on column jasdoc_file_history.filename is '文件名';
comment on column jasdoc_file_history.filetype is '文档的格式,如doc、xls。';
comment on column jasdoc_file_history.filelocation is '文件的相对父节点位置';
comment on column jasdoc_file_history.author is '创建者';
comment on column jasdoc_file_history.keyword is '关键词';
comment on column jasdoc_file_history.summary is '摘要';
comment on column jasdoc_file_history.fileobj is '文件本身';
comment on column jasdoc_file_history.filesize is '文件的大小';
comment on column jasdoc_file_history.uploadtime is '附件上传时间';
comment on column jasdoc_file_history.createuser is '创建者';
comment on column jasdoc_file_history.createtime is '建档时间';
comment on column jasdoc_file_history.updateuser is '创建者';
comment on column jasdoc_file_history.updatetime is '最近一次修改的时间';
comment on column jasdoc_file_history.remark is '备注';
comment on column jasdoc_file_history.deleteflag is '文档删除标识（被删除后该字段为删除批次）(修改字段)';
comment on column jasdoc_file_history.absolutepath is '文件的存储绝对路径（新加字段）';
comment on column jasdoc_file_history.deleteuser is '删除记录人（新加字段）';
comment on column jasdoc_file_history.iscreateindex is '是否添加索引';
comment on column jasdoc_file_history.createusername is '上传用户名称';
comment on column jasdoc_file_history.versionnumber is '版本号';
comment on column jasdoc_file_history.versionid is '历史版本id';
comment on column jasdoc_file_history.versionreark is '版本描述';
comment on column jasdoc_file_history.versionupdatetime is '版本更新时间';

/**文档共享**/

create table jasdoc_file_share (
	id varchar(36) not null primary key,
	fileid varchar(36),
	shareuserid varchar(36),
	remark varchar(500),
	sharedate timestamp(6),
	overdate timestamp(6),
	shareprivilegetype numeric(3)
);
comment on table jasdoc_file_share is '文档共享';
comment on column jasdoc_file_share.fileid is '文档id';
comment on column jasdoc_file_share.shareuserid is '共享人id';
comment on column jasdoc_file_share.remark is '共享说明';
comment on column jasdoc_file_share.sharedate is '共享时间';
comment on column jasdoc_file_share.overdate is '共享过期时间';
comment on column jasdoc_file_share.shareprivilegetype is '共享权限类型';

/**共享文档与用户关联表**/

create table jasdoc_file_share_user_ref (
	id varchar(36) not null primary key,
	jasdoc_file_shear_id varchar(36),
	sharedid varchar(36),
	sharedtype numeric(3)
);
comment on table jasdoc_file_share_user_ref is '共享文档与用户关联表';
comment on column jasdoc_file_share_user_ref.jasdoc_file_shear_id is '文档共享id';
comment on column jasdoc_file_share_user_ref.sharedid is '共享对象id';
comment on column jasdoc_file_share_user_ref.sharedtype is '共享对象类型';

/**文档中心**/

create table jasdoc_folder (
	id varchar(36) not null primary key,
	parentid varchar(36) not null,
	foldername varchar(200) not null,
	foldertype numeric(2),
	folderlocation varchar(500),
	createtime timestamp(6),
	createuser varchar(36),
	sort numeric,
	hierarchy varchar(208),
	deleteflag varchar(36),
	isdefaultfavorite numeric(1),
	deleteuser varchar(36),
	updateuser varchar(36),
	updatetime timestamp(6),
	ext_field1 varchar(500),
	ext_field2 varchar(500),
	ext_field3 varchar(500),
	ext_field4 varchar(500),
	ext_field5 varchar(500),
	description varchar(1000),
	createusername varchar(50),
	updateusername varchar(50)
);
comment on table jasdoc_folder is '文档中心';
comment on column jasdoc_folder.id is '节点id';
comment on column jasdoc_folder.parentid is '父类id';
comment on column jasdoc_folder.foldername is '节点名称';
comment on column jasdoc_folder.foldertype is '1标识文档中心下的文件夹，2标识附件管理下的文件夹';
comment on column jasdoc_folder.createtime is '创建时间';
comment on column jasdoc_folder.createuser is '添加数据用户（更改字段）';
comment on column jasdoc_folder.deleteflag is '该字段仅对文档中心里的文件夹有效。记录删除批次';
comment on column jasdoc_folder.isdefaultfavorite is '该字段仅对我的收藏夹里的文件夹有效。';
comment on column jasdoc_folder.deleteuser is '删除该条数据用户id（没有删除则为空）';
comment on column jasdoc_folder.updateuser is '修改数据用户（新加字段）';
comment on column jasdoc_folder.updatetime is '创建时间';
comment on column jasdoc_folder.ext_field1 is '备用字段';
comment on column jasdoc_folder.ext_field2 is '备用字段';
comment on column jasdoc_folder.ext_field3 is '备用字段';
comment on column jasdoc_folder.ext_field4 is '备用字段';
comment on column jasdoc_folder.ext_field5 is '备用字段';
comment on column jasdoc_folder.description is '文件夹描述';
comment on column jasdoc_folder.createusername is '创建者名称';
comment on column jasdoc_folder.updateusername is '修改者名称';

/**文件夹共享表**/

create table jasdoc_folder_share (
	id varchar(36) not null primary key,
	folderdid varchar(36),
	shareuserid varchar(36),
	remark varchar(500),
	sharedate timestamp(6),
	overdate timestamp(6),
	shareprivilegetype numeric(3)
);
comment on table jasdoc_folder_share is '文件夹共享表';
comment on column jasdoc_folder_share.folderdid is '文件夹id';
comment on column jasdoc_folder_share.shareuserid is '共享人id';
comment on column jasdoc_folder_share.remark is '共享说明';
comment on column jasdoc_folder_share.sharedate is '共享时间';
comment on column jasdoc_folder_share.overdate is '共享过期时间';
comment on column jasdoc_folder_share.shareprivilegetype is '共享权限类型';

/**文件夹共享与用户关联表**/

create table jasdoc_folder_share_user_ref (
	id varchar(36) not null primary key,
	jasdoc_folder_shear_id varchar(36),
	sharedid varchar(36),
	sharedtype numeric(3)
);
comment on table jasdoc_folder_share_user_ref is '文件夹共享与用户关联表';
comment on column jasdoc_folder_share_user_ref.jasdoc_folder_shear_id is '文件夹共享id';
comment on column jasdoc_folder_share_user_ref.sharedid is '共享对象id';
comment on column jasdoc_folder_share_user_ref.sharedtype is '共享对象类型';

/**回收站信息**/

create table jasdoc_recyclebin (
	id varchar(36) not null primary key,
	recycleobjid varchar(36) not null,
	recycleobjtype numeric(1),
	recycletime timestamp(6),
	userid varchar(36),
	location varchar(500)
);
comment on table jasdoc_recyclebin is '回收站信息';

/**角色对文件夹授权**/

create table jasdoc_role_folder_ref (
	id varchar(40) not null primary key,
	roleid varchar(40) not null,
	folderid varchar(40) not null,
	folderprivilegetype numeric(3)
);
comment on table jasdoc_role_folder_ref is '角色对文件夹授权';
comment on column jasdoc_role_folder_ref.id is 'uuid';
comment on column jasdoc_role_folder_ref.roleid is '对象id';
comment on column jasdoc_role_folder_ref.folderid is '节点id';

/**用户对文件夹授权**/

create table jasdoc_user_folder_ref (
	id varchar(40) not null primary key,
	userid varchar(40) not null,
	folderid varchar(40) not null,
	folderprivilegetype numeric(3)
);
comment on table jasdoc_user_folder_ref is '用户对文件夹授权';
comment on column jasdoc_user_folder_ref.id is 'uuid';
comment on column jasdoc_user_folder_ref.userid is '用户id';
comment on column jasdoc_user_folder_ref.folderid is '文件夹id';