/*
* 操作日志表
*/


-- ----------------------------
-- Table structure for LOG_BUSINESS
-- ----------------------------
CREATE TABLE "LOG_BUSINESS" (
"OID" VARCHAR2(36 BYTE) NOT NULL ,
"BUSINESS_ID" VARCHAR2(36 BYTE) NOT NULL ,
"OPT_TYPE" NVARCHAR2(50) NOT NULL ,
"DETAIL" NVARCHAR2(2000) NULL ,
"REMARK" NVARCHAR2(2000) NULL ,
"CREATE_USER_ID" VARCHAR2(36 BYTE) NOT NULL ,
"CREATE_USER_NAME" NVARCHAR2(50) NOT NULL ,
"CREATE_TIME" TIMESTAMP(9)  NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "LOG_BUSINESS" IS '业务日志记录表';
COMMENT ON COLUMN "LOG_BUSINESS"."OID" IS '主键';
COMMENT ON COLUMN "LOG_BUSINESS"."BUSINESS_ID" IS '业务数据ID';
COMMENT ON COLUMN "LOG_BUSINESS"."OPT_TYPE" IS '操作类型';
COMMENT ON COLUMN "LOG_BUSINESS"."DETAIL" IS '详情。详情。若是更新，必须是json数组：[{"dataItem":"自主外包费","newValue":1000.0,"oldValue":50000.0}]），其他类型则无限制';
COMMENT ON COLUMN "LOG_BUSINESS"."REMARK" IS '备注';

-- ----------------------------
-- Table structure for LOG_OPERATE
-- ----------------------------
CREATE TABLE "LOG_OPERATE" (
"OID" VARCHAR2(36 BYTE) NOT NULL ,
"BUSINESS_ID" VARCHAR2(36 BYTE) NULL ,
"BUSINESS_NAME" NVARCHAR2(50) NULL ,
"TABLE_NAME" NVARCHAR2(200) NULL ,
"OPT_TYPE" VARCHAR2(50 BYTE) NOT NULL ,
"DETAIL" NVARCHAR2(2000) NULL ,
"REMARK" NVARCHAR2(2000) NULL ,
"CREATE_USER_ID" VARCHAR2(36 BYTE) NOT NULL ,
"CREATE_USER_NAME" NVARCHAR2(50) NOT NULL ,
"CREATE_TIME" TIMESTAMP(9)  NOT NULL ,
"APP_ID" VARCHAR2(36 BYTE) NULL ,
"APP_NAME" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON TABLE "LOG_OPERATE" IS '操作日志记录表';
COMMENT ON COLUMN "LOG_OPERATE"."OID" IS '主键';
COMMENT ON COLUMN "LOG_OPERATE"."BUSINESS_ID" IS '业务数据ID';
COMMENT ON COLUMN "LOG_OPERATE"."BUSINESS_NAME" IS '业务名称';
COMMENT ON COLUMN "LOG_OPERATE"."TABLE_NAME" IS '业务表名';
COMMENT ON COLUMN "LOG_OPERATE"."OPT_TYPE" IS '操作类型';
COMMENT ON COLUMN "LOG_OPERATE"."DETAIL" IS '详情';
COMMENT ON COLUMN "LOG_OPERATE"."REMARK" IS '备注';
COMMENT ON COLUMN "LOG_OPERATE"."CREATE_USER_ID" IS '操作人ID';
COMMENT ON COLUMN "LOG_OPERATE"."CREATE_USER_NAME" IS '操作人姓名';
COMMENT ON COLUMN "LOG_OPERATE"."CREATE_TIME" IS '操作时间';
COMMENT ON COLUMN "LOG_OPERATE"."APP_ID" IS '应用Id';
COMMENT ON COLUMN "LOG_OPERATE"."APP_NAME" IS '应用名称';

-- ----------------------------
-- Indexes structure for table LOG_BUSINESS
-- ----------------------------
CREATE INDEX "INDEX_LOG_BUSINESS_ID_5"
ON "LOG_BUSINESS" ("BUSINESS_ID" ASC)
LOGGING
VISIBLE;

-- ----------------------------
-- Checks structure for table LOG_BUSINESS
-- ----------------------------
ALTER TABLE "LOG_BUSINESS" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "LOG_BUSINESS" ADD CHECK ("BUSINESS_ID" IS NOT NULL);
ALTER TABLE "LOG_BUSINESS" ADD CHECK ("OPT_TYPE" IS NOT NULL);
ALTER TABLE "LOG_BUSINESS" ADD CHECK ("CREATE_USER_ID" IS NOT NULL);
ALTER TABLE "LOG_BUSINESS" ADD CHECK ("CREATE_USER_NAME" IS NOT NULL);
ALTER TABLE "LOG_BUSINESS" ADD CHECK ("CREATE_TIME" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table LOG_BUSINESS
-- ----------------------------
ALTER TABLE "LOG_BUSINESS" ADD PRIMARY KEY ("OID");

-- ----------------------------
-- Indexes structure for table LOG_OPERATE
-- ----------------------------

-- ----------------------------
-- Checks structure for table LOG_OPERATE
-- ----------------------------
ALTER TABLE "LOG_OPERATE" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "LOG_OPERATE" ADD CHECK ("OPT_TYPE" IS NOT NULL);
ALTER TABLE "LOG_OPERATE" ADD CHECK ("CREATE_USER_ID" IS NOT NULL);
ALTER TABLE "LOG_OPERATE" ADD CHECK ("CREATE_USER_NAME" IS NOT NULL);
ALTER TABLE "LOG_OPERATE" ADD CHECK ("CREATE_TIME" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table LOG_OPERATE
-- ----------------------------
ALTER TABLE "LOG_OPERATE" ADD PRIMARY KEY ("OID");
