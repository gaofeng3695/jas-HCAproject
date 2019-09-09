/**
 * 高后果区导入导出配置
 */

-- ----------------------------
-- Table structure for EXCEL_COLUMNINFO
-- ----------------------------
CREATE TABLE "EXCEL_COLUMNINFO" (
"OID" VARCHAR2(36 BYTE) NOT NULL ,
"TABLE_ID" VARCHAR2(36 BYTE) NOT NULL ,
"FIELD_NAME" VARCHAR2(100 BYTE) NOT NULL ,
"FIELD_ALIAS" VARCHAR2(100 BYTE) NULL ,
"COLUMN_LENGTH" NUMBER(5) NULL ,
"PRECISION_LENGTH" NUMBER(2) NULL ,
"IS_MANDATORY" NUMBER(1) NULL ,
"IS_PK_FIELD" NUMBER(1) NOT NULL ,
"IS_FOREIGN_FIELD" NUMBER(1) NOT NULL ,
"FOREIGN_FIELD" VARCHAR2(200 BYTE) NULL ,
"INPUT_TYPE" VARCHAR2(50 BYTE) NULL ,
"DATA_SOURCE" VARCHAR2(300 BYTE) NULL ,
"REMARK" NVARCHAR2(1000) NULL ,
"FIELD_TYPE" VARCHAR2(100 BYTE) NULL ,
"GROUP_NAME" NVARCHAR2(100) NULL ,
"EXCEL_DATA_TYPE" VARCHAR2(100 BYTE) NULL ,
"VALIDATE_TYPE" NUMBER(1) NULL ,
"EXCEL_OPERATOR" NUMBER(1) NULL ,
"VALIDATE_BEGIN_VALUE" VARCHAR2(50 BYTE) NULL ,
"VALIDATE_END_VALUE" VARCHAR2(50 BYTE) NULL ,
"EXCEL_INPUT_MSG" NVARCHAR2(100) NULL ,
"EXCEL_ERROR_MODE" NUMBER(1) NULL ,
"EXCEL_ERROR_MSG" NVARCHAR2(300) NULL ,
"BEGIN_ROW" NUMBER(8) NULL ,
"BEGIN_COLUMN" NUMBER(8) NULL ,
"END_ROW" NUMBER(8) NULL ,
"END_COLUMN" NUMBER(8) NULL ,
"ASSOCIATE_FIELD" VARCHAR2(100 BYTE) NULL ,
"ASSOCIATE_VALUE" VARCHAR2(100 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "EXCEL_COLUMNINFO" IS '导入模板字段配置信息';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."OID" IS '主键';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."TABLE_ID" IS '所属表ID';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."FIELD_NAME" IS '字段名称';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."FIELD_ALIAS" IS '字段别名';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."COLUMN_LENGTH" IS '字段长度';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."PRECISION_LENGTH" IS '精度';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."IS_MANDATORY" IS '是否必填';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."IS_PK_FIELD" IS '是否主键字段 (1：是主键 0：非主键)';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."IS_FOREIGN_FIELD" IS '是否外键字段（1：是外键，0非外键）';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."FOREIGN_FIELD" IS '外键字段';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."INPUT_TYPE" IS '输入类型';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."DATA_SOURCE" IS '数据来源';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."REMARK" IS '备注';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."FIELD_TYPE" IS '字段类型';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."GROUP_NAME" IS '所属分组名';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."EXCEL_DATA_TYPE" IS 'excel数据类型';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."VALIDATE_TYPE" IS 'excel验证类型（0：任何值，1：整数、2：小数，4：日期）';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."EXCEL_OPERATOR" IS 'excel验证运算符（0：介于，1：未介于 2：等于，3：不等于，4：大于，5：小于，6：大于等于，7：小于等于）';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."VALIDATE_BEGIN_VALUE" IS 'excel验证起始值';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."VALIDATE_END_VALUE" IS 'excel验证终止值';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."EXCEL_INPUT_MSG" IS 'excel输入提示信息(单元格获取焦点时的提示信息)';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."EXCEL_ERROR_MODE" IS 'excel错误模式：数据非法时,EXCEL接下来的动作，分为（0：停止:1：警告:2：信息提示）';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."EXCEL_ERROR_MSG" IS 'excel数据非法时错误信息';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."BEGIN_ROW" IS '单元起始行';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."BEGIN_COLUMN" IS '单元起始列';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."END_ROW" IS '单元终止行';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."END_COLUMN" IS '单元终止列';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."ASSOCIATE_FIELD" IS '关联其他字段';
COMMENT ON COLUMN "EXCEL_COLUMNINFO"."ASSOCIATE_VALUE" IS '关联表的关联字段';

-- ----------------------------
-- Records of EXCEL_COLUMNINFO
-- ----------------------------
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('8741879b-fbef-4197-bfe3-79fbed715d9a', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'modify_user_name', null, '100', null, '0', '0', '0', null, 'auto_userName', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('2ae979f7-564c-47d8-97db-5126d85caf48', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'modify_user_id', null, '72', null, '0', '0', '0', null, 'auto_userid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('6d37cfea-5b02-4767-8500-e0c463ebb55f', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'building_type', '建（构）筑物类型', '100', null, '1', '0', '1', 'sys_domain,code_name,code_id', 'input', 'select code_id,code_name from  sys_domain where active=1 and domain_name=''building_type_domain'' ORDER BY ordinal', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '3', '2', '3', 'building_type_parent', 'parent_code_id');
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('a672af76-bd3b-4ad9-bcf7-cda8d2832c9a', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'building_distribution', '建筑分布', '100', null, '0', '0', '0', null, 'select', 'select code_id,code_name from  sys_domain where active=1 and domain_name=''building_distribution_domain'' ORDER BY ordinal', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '4', '2', '4', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('8daaddd6-5832-4d2b-85f0-eeb394cb29f7', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'active', null, '22', '5', '1', '0', '0', null, 'auto_active', ' ', null, 'NUMBER', null, 'int', '1', '0', '-4294967296', '4294967296', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('503676a0-7390-4ef6-9523-30ef4b14e798', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'oid', null, '72', null, '1', '1', '0', null, 'auto_uuid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('a475e2db-fc08-49b9-880e-47dcefd0f9fc', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'households', '户数', '22', null, '0', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'int', '1', '0', '-4294967296', '4294967296', null, '0', '数据格式不正确！', '2', '5', '2', '5', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('e8f1935c-76e3-4625-99c3-3a8698d848b1', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'population', '人口', '22', null, '0', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'int', '1', '0', '-4294967296', '4294967296', null, '0', '数据格式不正确！', '2', '6', '2', '6', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('78f8a99b-037d-4e8f-bf60-6b18df3f2211', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'address', '地址', '100', null, '0', '0', '0', null, 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '7', '2', '7', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('c43a2eb4-df08-487b-8801-666c4e631bd4', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'remarks', '备注', '2000', null, '0', '0', '0', null, 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '10', '2', '10', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('919a9280-0f9d-4cb4-9101-30366ed8b732', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'collect_date', '采集时间', '11', null, '0', '0', '0', null, 'input', ' ', null, 'TIMESTAMP(6)', null, 'date', '4', '0', '1900-01-01', '9999-08-08', null, '0', '数据格式不正确！', '2', '9', '2', '9', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('8af573c3-79ff-49f8-abfd-342e41efd0e0', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'building_type_parent', '建（构）筑物类别', '100', null, '1', '0', '1', 'sys_domain,code_name,code_id', 'input', ' select code_id,code_name from  sys_domain where active=1 and domain_name=''building_type_parent_domain'' ORDER BY ordinal', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '2', '2', '2', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('6b454981-a231-4566-bb4c-6a1543cd8338', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'building_code', '建（构）筑物编号', '100', null, '1', '0', '0', null, 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '1', '2', '1', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('c5244540-882a-4619-b361-792fec0cbca9', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'create_datetime', null, '11', null, '0', '0', '0', null, 'auto_systime', ' ', null, 'TIMESTAMP(6)', null, 'date', '4', '0', '1900-01-01', '9999-08-08', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('f424d9df-2b5b-4718-bbf4-de506bcaded6', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'create_user_id', null, '72', null, '0', '0', '0', null, 'auto_userid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('b71acb50-e037-4382-95d7-61ffad698de5', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'create_user_name', null, '100', null, '0', '0', '0', null, 'auto_userName', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('32bb190f-eb2b-4081-8294-8844b9b5405f', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'modify_datetime', null, '11', null, '0', '0', '0', null, 'auto_systime', ' ', null, 'TIMESTAMP(6)', null, 'date', '4', '0', '1900-01-01', '9999-08-08', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('94b0ba6e-1b45-49d9-9860-92a360eed6ee', '7eae2269-39c8-4cc8-bc89-98470d8170f3', 'pressure_pipeline', '是否占压', '22', '5', '0', '0', '0', null, 'select', '[{"key":"0","value":"否"},{"key":"1","value":"是"}]', null, 'NUMBER', null, 'int', '1', '0', '-4294967296', '4294967296', null, '0', '数据格式不正确！', '2', '8', '2', '8', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('00b02bb7-9710-4f15-bc73-b0e740dd929d', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'oid', null, '72', null, '1', '1', '0', null, 'auto_uuid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('0ff2049f-b435-48fe-85b7-4ac3d6dc5e49', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'oid', null, '72', null, '1', '1', '0', null, 'auto_uuid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('5bd61364-4a6b-4428-8cf6-d7dcd98ae461', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'pipeline_oid', '管线名称', '72', null, '1', '0', '1', 'HCA_PIPELINE,PIPELINE_NAME,OID', 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '1', '2', '1', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('c6b4dd5b-a01f-4f15-942d-f8f0785c340a', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'version_oid', '识别名称', '72', null, '1', '0', '1', 'HCA_VERSION,VERSION_NAME,OID', 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '2', '2', '2', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('dd7b6ce0-698d-41c7-8f3b-79854a9400d9', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'high_impact_area_code', '高后果区编号', '100', null, '1', '0', '0', null, 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '3', '2', '3', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('b3e406e2-58a2-49c5-80ac-dc95a15c42b8', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'active', null, '22', '5', '1', '0', '0', null, 'auto_active', ' ', null, 'NUMBER', null, 'int', '1', '0', '-4294967296', '4294967296', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('e6d65405-1500-4f56-af54-c560c142397c', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'high_impact_area_name', '高后果区名称', '100', null, '0', '0', '0', null, 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '4', '2', '4', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('9ec05b4d-3513-405e-b281-ffd050745221', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'high_impact_level', '高后果区等级', '100', null, '0', '0', '0', null, 'select', 'select code_id,code_name from  sys_domain where active=1 and domain_name=''high_impact_level_domain'' ORDER BY ordinal', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '5', '2', '5', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('3e557810-13f1-4924-bf15-9bf59de01243', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'end_mileage', '终止里程（km）', '22', '38', '1', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'number', '2', '0', '-4294967296.0', '4294967296.0', null, '0', '数据格式不正确！', '2', '7', '2', '7', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('cba35c9e-2dfd-4b53-923e-f24bb2a3308a', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'start_mileage', '起始里程（km）', '22', '38', '1', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'number', '2', '0', '-4294967296.0', '4294967296.0', null, '0', '数据格式不正确！', '2', '6', '2', '6', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('df58513e-ea95-44ce-8413-452f1c31496e', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'hca_length', '高后果区长度', '22', '38', '0', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'number', '2', '0', '-4294967296.0', '4294967296.0', null, '0', '数据格式不正确！', '2', '8', '2', '8', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('01bfea89-595f-48b2-a20d-23524b7d9855', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'description', '描述', '100', null, '0', '0', '0', null, 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '9', '2', '9', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('7ab8995d-8d35-4a37-a034-ff5045cc6c70', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'modify_datetime', null, '11', null, '0', '0', '0', null, 'auto_systime', ' ', null, 'TIMESTAMP(6)', null, 'date', '4', '0', '1900-01-01', '9999-08-08', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('c2c9ac6e-5792-46a6-87f0-ee5760bbb8e0', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'modify_user_name', null, '100', null, '0', '0', '0', null, 'auto_userName', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('2d8168e7-1a82-4716-834d-121660ecf49f', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'modify_user_id', null, '72', null, '0', '0', '0', null, 'auto_userid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('a120b987-a457-4c8c-ad1a-f8ba158e69f0', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'create_datetime', null, '11', null, '0', '0', '0', null, 'auto_systime', ' ', null, 'TIMESTAMP(6)', null, 'date', '4', '0', '1900-01-01', '9999-08-08', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('e3691869-8f60-4d5d-b5e0-1e2fa60f5551', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'create_user_name', null, '100', null, '0', '0', '0', null, 'auto_userName', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('83536bda-4a95-4946-b536-96c778c80b02', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'pipeline_name', '管线名称', '100', null, '1', '0', '0', null, 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '1', '2', '1', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('f4ac466a-7b9f-4ca5-9511-9275538ff80d', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'pipeline_code', '管线编号', '100', null, '1', '0', '0', null, 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '2', '2', '2', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('0b3db327-1719-4f0a-9033-5b26ddf5187a', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'active', null, '22', '5', '1', '0', '0', null, 'auto_active', ' ', null, 'NUMBER', null, 'int', '1', '0', '-4294967296', '4294967296', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('28a19430-9a71-4c26-a942-99bf9926b4d6', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'create_user_id', null, '72', null, '0', '0', '0', null, 'auto_userid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('54775a06-6711-49ab-acca-d37312e957fc', 'cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'shape', '空间坐标', '256', null, '1', '0', '0', null, 'input', ' ', null, 'ST_GEOMETRY', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '10', '2', '10', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('d102de1d-a2e7-46bc-ad39-e48d1ce9570b', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'start_mileage', '起始里程', '22', '38', '0', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'number', '2', '0', '-4294967296.0', '4294967296.0', null, '0', '数据格式不正确！', '2', '3', '2', '3', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('053176ae-0fd1-4ade-aea5-82967c0250ad', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'end_mileage', '终止里程', '22', '38', '0', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'number', '2', '0', '-4294967296.0', '4294967296.0', null, '0', '数据格式不正确！', '2', '4', '2', '4', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('36e1b1e6-8792-4906-a13b-2574aa87d0df', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'pipeline_length', '管线长度', '22', '38', '0', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'number', '2', '0', '-4294967296.0', '4294967296.0', null, '0', '数据格式不正确！', '2', '5', '2', '5', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('ca3d9b8c-0b7a-4dd3-96de-a3dffe247cf0', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'outside_diameter', '管道外管径', '22', '38', '0', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'number', '2', '0', '-4294967296.0', '4294967296.0', null, '0', '数据格式不正确！', '2', '6', '2', '6', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('51666f5c-bda3-42d4-8151-cf393c239eb7', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'pressure', '最大压力', '22', '38', '0', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'number', '2', '0', '-4294967296.0', '4294967296.0', null, '0', '数据格式不正确！', '2', '7', '2', '7', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('5bf1e654-c643-4b5f-92d6-494d91d09ccf', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'remarks', '备注', '2000', null, '0', '0', '0', null, 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '8', '2', '8', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('aa3520ce-1051-4c4d-840c-cc80c34ca0c8', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'create_user_id', null, '72', null, '0', '0', '0', null, 'auto_userid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('161ea981-9159-4c8a-9923-d53161fa5c10', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'modify_user_id', null, '72', null, '0', '0', '0', null, 'auto_userid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('a701947c-fa42-43f2-a9c8-3072bdc7edec', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'create_datetime', null, '11', null, '0', '0', '0', null, 'auto_systime', ' ', null, 'TIMESTAMP(6)', null, 'date', '4', '0', '1900-01-01', '9999-08-08', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('bd6c2c40-8713-46e8-8980-44b9663133e6', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'modify_user_name', null, '100', null, '0', '0', '0', null, 'auto_userName', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('2dfea9de-b444-439c-81b1-448e5d1eb908', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'modify_datetime', null, '11', null, '0', '0', '0', null, 'auto_systime', ' ', null, 'TIMESTAMP(6)', null, 'date', '4', '0', '1900-01-01', '9999-08-08', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('218802ba-5371-47d7-821d-ba2b379b1ef4', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'create_user_name', null, '100', null, '0', '0', '0', null, 'auto_userName', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('37932c18-dc2d-4cfd-a319-040f7f86ee37', 'e4b20050-7bc0-4260-be12-6efd6f268422', 'shape', '空间坐标', '256', null, '1', '0', '0', null, 'input', ' ', null, 'ST_GEOMETRY', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '9', '2', '9', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('c164c8dc-59df-4d30-8e05-2cc248ed04a6', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'oid', null, '72', null, '1', '1', '0', null, 'auto_uuid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('3494335b-361e-40da-a62f-1fe522eb6b4b', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'pipeline_oid', '管线名称', '72', null, '1', '0', '1', 'HCA_PIPELINE,PIPELINE_NAME,OID', 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '1', '2', '1', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('f13b2ccf-faa0-47b1-8298-980ac05acd16', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'area_code', '地区等级编号', '100', null, '1', '0', '0', null, 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '3', '2', '3', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('cbad781d-4334-4b11-b235-65d8bbd4fc73', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'active', null, '22', '5', '1', '0', '0', null, 'auto_active', ' ', null, 'NUMBER', null, 'int', '1', '0', '-4294967296', '4294967296', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('495ecadc-016e-46ef-a8d0-28e27bf1a7cd', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'version_oid', '地区等级划分名称', '72', null, '1', '0', '1', 'HCA_VERSION,VERSION_NAME,OID', 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '2', '2', '2', 'pipeline_oid', 'pipeline_oid');
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('d181bb7b-a655-46de-9cbf-5bb3a2b96d39', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'start_mileage', '起始里程（km）', '22', '38', '1', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'number', '2', '0', '-4294967296.0', '4294967296.0', null, '0', '数据格式不正确！', '2', '4', '2', '4', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('96d0ec95-f41f-4694-bebb-0b326206f855', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'end_mileage', '终止里程（km）', '22', '38', '1', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'number', '2', '0', '-4294967296.0', '4294967296.0', null, '0', '数据格式不正确！', '2', '5', '2', '5', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('8beae93f-2869-4f3f-b64c-adcd9a5f2454', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'region_level', '地区等级', '100', null, '0', '0', '0', null, 'select', 'select code_id,code_name from  sys_domain where active=1 and domain_name=''region_level_domain'' ORDER BY ordinal', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '6', '2', '6', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('fc673c90-ea75-4a38-a71f-6926664a5170', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'area_length', '地区等级长度', '22', '38', '0', '0', '0', null, 'input', ' ', null, 'NUMBER', null, 'number', '2', '0', '-4294967296.0', '4294967296.0', null, '0', '数据格式不正确！', '2', '7', '2', '7', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('60ac278c-8ea0-4f11-90ff-5326be6bd1ac', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'description', '地区等级描述', '2000', null, '0', '0', '0', null, 'input', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '8', '2', '8', null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('632ed59f-9ef0-476e-bbf5-36864c5183fc', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'modify_datetime', null, '11', null, '0', '0', '0', null, 'auto_systime', ' ', null, 'TIMESTAMP(6)', null, 'date', '4', '0', '1900-01-01', '9999-08-08', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('776f8788-6fad-4836-ab33-ab8cc14b9589', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'modify_user_name', null, '100', null, '0', '0', '0', null, 'auto_userName', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('c4d32665-ea3b-4348-aab4-cc5993dc09ae', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'modify_user_id', null, '72', null, '0', '0', '0', null, 'auto_userid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('fbcc8b1a-0f67-4943-a62b-4fd943537287', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'create_datetime', null, '11', null, '0', '0', '0', null, 'auto_systime', ' ', null, 'TIMESTAMP(6)', null, 'date', '4', '0', '1900-01-01', '9999-08-08', null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('9f413783-43b5-4b33-8d1a-fd9196759c6c', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'create_user_name', null, '100', null, '0', '0', '0', null, 'auto_userName', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('20bd255b-1c6b-4b40-ac9b-8d8faff9359b', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'create_user_id', null, '72', null, '0', '0', '0', null, 'auto_userid', ' ', null, 'NVARCHAR2', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', null, null, null, null, null, null);
INSERT INTO "EXCEL_COLUMNINFO" VALUES ('79bd249d-0573-4218-817c-8b0bd53eafe1', 'f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'shape', '空间坐标', '256', null, '1', '0', '0', null, 'input', ' ', null, 'ST_GEOMETRY', null, 'string', '0', '0', null, null, null, '0', '数据格式不正确！', '2', '9', '2', '9', null, null);

-- ----------------------------
-- Table structure for EXCEL_TABLEINFO
-- ----------------------------
CREATE TABLE "EXCEL_TABLEINFO" (
"OID" VARCHAR2(36 BYTE) NOT NULL ,
"TABLE_NAME" VARCHAR2(100 BYTE) NOT NULL ,
"TABLE_ALIAS" VARCHAR2(100 BYTE) NOT NULL ,
"FORM_TYPE" NUMBER(1) NOT NULL ,
"REMARK" NVARCHAR2(1000) NULL ,
"TEMPLATE_ID" VARCHAR2(36 BYTE) NOT NULL ,
"GEOMETRY_TYPE" VARCHAR2(50 BYTE) NULL ,
"SHEET_INDEX" NUMBER(1) NOT NULL ,
"IS_MAIN_TABLE" NUMBER(1) NOT NULL ,
"FIELD_NAMES" VARCHAR2(200 BYTE) NULL ,
"ENTITY_PATH" VARCHAR2(200 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "EXCEL_TABLEINFO" IS '导入模板表信息';
COMMENT ON COLUMN "EXCEL_TABLEINFO"."OID" IS '主键';
COMMENT ON COLUMN "EXCEL_TABLEINFO"."TABLE_NAME" IS '表名称';
COMMENT ON COLUMN "EXCEL_TABLEINFO"."TABLE_ALIAS" IS '表别名';
COMMENT ON COLUMN "EXCEL_TABLEINFO"."FORM_TYPE" IS '表单类型(1：表格 2：列表)';
COMMENT ON COLUMN "EXCEL_TABLEINFO"."REMARK" IS '备注';
COMMENT ON COLUMN "EXCEL_TABLEINFO"."TEMPLATE_ID" IS '模板Id';
COMMENT ON COLUMN "EXCEL_TABLEINFO"."GEOMETRY_TYPE" IS '表类型（none属性表 Poin点空间表 Polyline线空间表 Polygon面空间表）';
COMMENT ON COLUMN "EXCEL_TABLEINFO"."SHEET_INDEX" IS '所属sheet索引，默认为0';
COMMENT ON COLUMN "EXCEL_TABLEINFO"."IS_MAIN_TABLE" IS '是否是主表';
COMMENT ON COLUMN "EXCEL_TABLEINFO"."FIELD_NAMES" IS '更新依据字段';
COMMENT ON COLUMN "EXCEL_TABLEINFO"."ENTITY_PATH" IS ' 实体全路径';

-- ----------------------------
-- Records of EXCEL_TABLEINFO
-- ----------------------------
INSERT INTO "EXCEL_TABLEINFO" VALUES ('7eae2269-39c8-4cc8-bc89-98470d8170f3', 'hca_buildings', '建（构）筑物', '2', null, '46d459b9-6b15-4430-8a11-94a819de4976', 'none', '0', '0', 'building_code', null);
INSERT INTO "EXCEL_TABLEINFO" VALUES ('e4b20050-7bc0-4260-be12-6efd6f268422', 'hca_pipeline', '管线', '2', null, 'f1ebeb13-752f-44d3-9aee-7b1f97ab827f', 'Polyline', '0', '0', 'pipeline_code', null);
INSERT INTO "EXCEL_TABLEINFO" VALUES ('f596c952-cff3-47ea-b25b-9f3bf8eee6d4', 'hca_area', '地区等级', '2', null, '12e62a30-1064-4698-b92c-4353f026d270', 'Polygon', '0', '0', 'area_code,version_oid,pipeline_oid', null);
INSERT INTO "EXCEL_TABLEINFO" VALUES ('cefac140-28a7-4d85-ba1c-509f9db5b6c1', 'hca_high_impact_area', '高后果区', '2', null, 'e3c221f4-fe57-437f-9c92-974d51eaac50', 'Polygon', '0', '0', 'pipeline_oid,version_oid,high_impact_area_code', null);

-- ----------------------------
-- Table structure for EXCEL_TEMPLATEINFO
-- ----------------------------
CREATE TABLE "EXCEL_TEMPLATEINFO" (
"OID" VARCHAR2(36 BYTE) NOT NULL ,
"TEMPLATE_NAME" NVARCHAR2(100) NOT NULL ,
"DATA_SOURCE_NAME" VARCHAR2(100 BYTE) NOT NULL ,
"TEMPLATE_TYPE" NUMBER(1) NOT NULL ,
"REMARK" NVARCHAR2(1000) NULL ,
"FUNCTION_NAME" NVARCHAR2(100) NOT NULL ,
"TEMPLATE_PATH" NVARCHAR2(255) NULL ,
"CREATE_USER_ID" VARCHAR2(36 BYTE) NULL ,
"CREATE_USER_NAME" NVARCHAR2(50) NULL ,
"CREATE_TIME" DATE NULL ,
"MODIFY_USER_ID" VARCHAR2(36 BYTE) NULL ,
"MODIFY_USER_NAME" NVARCHAR2(50) NULL ,
"MODIFY_TIME" DATE NULL ,
"ACTIVE" NUMBER(1) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "EXCEL_TEMPLATEINFO" IS '导入模板信息';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."OID" IS '主键';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."TEMPLATE_NAME" IS '模板名称';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."DATA_SOURCE_NAME" IS '数据源';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."TEMPLATE_TYPE" IS '模板类型（1单表单sheet 2多表单sheet 3多表多sheet）';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."REMARK" IS '备注';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."FUNCTION_NAME" IS '功能名称';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."TEMPLATE_PATH" IS '模板路径';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."CREATE_USER_ID" IS '创建人id';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."CREATE_USER_NAME" IS '创建人姓名';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."CREATE_TIME" IS '创建时间';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."MODIFY_USER_ID" IS '修改人id';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."MODIFY_USER_NAME" IS '修改人姓名';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."MODIFY_TIME" IS '修改时间';
COMMENT ON COLUMN "EXCEL_TEMPLATEINFO"."ACTIVE" IS '是否有效';

-- ----------------------------
-- Records of EXCEL_TEMPLATEINFO
-- ----------------------------
INSERT INTO "EXCEL_TEMPLATEINFO" VALUES ('46d459b9-6b15-4430-8a11-94a819de4976', '建（构）筑物导入模板', 'defaultDataSource', '1', null, 'hcabuildingsinfo', '/hca-template/建（构）筑物模板.xlsx', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_DATE('2019-08-12 12:01:00', 'YYYY-MM-DD HH24:MI:SS'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_DATE('2019-08-23 15:09:39', 'YYYY-MM-DD HH24:MI:SS'), '1');
INSERT INTO "EXCEL_TEMPLATEINFO" VALUES ('f1ebeb13-752f-44d3-9aee-7b1f97ab827f', '管线信息', 'defaultDataSource', '1', null, 'pipelineinfo', '/hca-template/管线信息.xls', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_DATE('2019-08-12 14:03:36', 'YYYY-MM-DD HH24:MI:SS'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_DATE('2019-08-23 14:54:10', 'YYYY-MM-DD HH24:MI:SS'), '1');
INSERT INTO "EXCEL_TEMPLATEINFO" VALUES ('12e62a30-1064-4698-b92c-4353f026d270', '地区等级划分信息', 'defaultDataSource', '1', null, 'areainfo', '/hca-template/地区等级划分信息.xls', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_DATE('2019-08-12 14:14:06', 'YYYY-MM-DD HH24:MI:SS'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_DATE('2019-08-23 15:03:46', 'YYYY-MM-DD HH24:MI:SS'), '1');
INSERT INTO "EXCEL_TEMPLATEINFO" VALUES ('e3c221f4-fe57-437f-9c92-974d51eaac50', '高后果区识别信息', 'defaultDataSource', '1', null, 'hcahighinfo', '/hca-template/高后果区识别信息.xls', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_DATE('2019-08-12 14:22:43', 'YYYY-MM-DD HH24:MI:SS'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_DATE('2019-08-23 15:04:10', 'YYYY-MM-DD HH24:MI:SS'), '1');

-- ----------------------------
-- Indexes structure for table EXCEL_COLUMNINFO
-- ----------------------------

-- ----------------------------
-- Checks structure for table EXCEL_COLUMNINFO
-- ----------------------------
ALTER TABLE "EXCEL_COLUMNINFO" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "EXCEL_COLUMNINFO" ADD CHECK ("TABLE_ID" IS NOT NULL);
ALTER TABLE "EXCEL_COLUMNINFO" ADD CHECK ("FIELD_NAME" IS NOT NULL);
ALTER TABLE "EXCEL_COLUMNINFO" ADD CHECK ("IS_PK_FIELD" IS NOT NULL);
ALTER TABLE "EXCEL_COLUMNINFO" ADD CHECK ("IS_FOREIGN_FIELD" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table EXCEL_COLUMNINFO
-- ----------------------------
ALTER TABLE "EXCEL_COLUMNINFO" ADD PRIMARY KEY ("OID");

-- ----------------------------
-- Indexes structure for table EXCEL_TABLEINFO
-- ----------------------------

-- ----------------------------
-- Checks structure for table EXCEL_TABLEINFO
-- ----------------------------
ALTER TABLE "EXCEL_TABLEINFO" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "EXCEL_TABLEINFO" ADD CHECK ("TABLE_NAME" IS NOT NULL);
ALTER TABLE "EXCEL_TABLEINFO" ADD CHECK ("TABLE_ALIAS" IS NOT NULL);
ALTER TABLE "EXCEL_TABLEINFO" ADD CHECK ("FORM_TYPE" IS NOT NULL);
ALTER TABLE "EXCEL_TABLEINFO" ADD CHECK ("TEMPLATE_ID" IS NOT NULL);
ALTER TABLE "EXCEL_TABLEINFO" ADD CHECK ("SHEET_INDEX" IS NOT NULL);
ALTER TABLE "EXCEL_TABLEINFO" ADD CHECK ("IS_MAIN_TABLE" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table EXCEL_TABLEINFO
-- ----------------------------
ALTER TABLE "EXCEL_TABLEINFO" ADD PRIMARY KEY ("OID");

-- ----------------------------
-- Indexes structure for table EXCEL_TEMPLATEINFO
-- ----------------------------

-- ----------------------------
-- Checks structure for table EXCEL_TEMPLATEINFO
-- ----------------------------
ALTER TABLE "EXCEL_TEMPLATEINFO" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "EXCEL_TEMPLATEINFO" ADD CHECK ("TEMPLATE_NAME" IS NOT NULL);
ALTER TABLE "EXCEL_TEMPLATEINFO" ADD CHECK ("DATA_SOURCE_NAME" IS NOT NULL);
ALTER TABLE "EXCEL_TEMPLATEINFO" ADD CHECK ("TEMPLATE_TYPE" IS NOT NULL);
ALTER TABLE "EXCEL_TEMPLATEINFO" ADD CHECK ("FUNCTION_NAME" IS NOT NULL);
ALTER TABLE "EXCEL_TEMPLATEINFO" ADD CHECK ("ACTIVE" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table EXCEL_TEMPLATEINFO
-- ----------------------------
ALTER TABLE "EXCEL_TEMPLATEINFO" ADD PRIMARY KEY ("OID");
