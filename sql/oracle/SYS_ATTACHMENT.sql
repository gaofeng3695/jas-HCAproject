/*
*	附件表
*/

-- ----------------------------
-- Table structure for SYS_ATTACHMENT
-- ----------------------------
CREATE TABLE "SYS_ATTACHMENT" (
"OID" NVARCHAR2(36) NOT NULL ,
"FILE_NAME" NVARCHAR2(200) NULL ,
"FILE_TYPE" NVARCHAR2(20) NULL ,
"FILE_SIZE" NUMBER NULL ,
"CONTENT" BLOB NULL ,
"FILE_DESCRIPTION" NVARCHAR2(200) NULL ,
"FILE_URL" NVARCHAR2(500) NULL ,
"CREATE_USER_ID" NVARCHAR2(36) NULL ,
"CREATE_USER_NAME" NVARCHAR2(50) NULL ,
"CREATE_TIME" TIMESTAMP(6)  NULL ,
"MODIFY_USER_ID" NVARCHAR2(36) NULL ,
"MODIFY_USER_NAME" NVARCHAR2(50) NULL ,
"MODIFY_TIME" TIMESTAMP(6)  NULL ,
"ACTIVE" NUMBER(5) NOT NULL ,
"PDF_SAVE_PATH" NVARCHAR2(500) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Table structure for SYS_ATTACHMENTBUSINESSRELATION
-- ----------------------------
CREATE TABLE "SYS_ATTACHMENTBUSINESSRELATION" (
"OID" NVARCHAR2(36) NOT NULL ,
"ATTACHMENT_ID" NVARCHAR2(36) NOT NULL ,
"BUSINESS_DATA_ID" NVARCHAR2(36) NOT NULL ,
"BUSINESS_DATA_TABLE" NVARCHAR2(50) NULL ,
"BUSINESS_TYPE" NVARCHAR2(20) NULL ,
"CREATE_USER_ID" NVARCHAR2(36) NULL ,
"CREATE_USER_NAME" NVARCHAR2(50) NULL ,
"CREATE_TIME" TIMESTAMP(6)  NULL ,
"MODIFY_USER_ID" NVARCHAR2(36) NULL ,
"MODIFY_USER_NAME" NVARCHAR2(50) NULL ,
"MODIFY_TIME" TIMESTAMP(6)  NULL ,
"ACTIVE" NUMBER(5) NOT NULL ,
"FILE_TYPE" NVARCHAR2(10) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Indexes structure for table SYS_ATTACHMENT
-- ----------------------------
CREATE INDEX "I1201OID"
ON "SYS_ATTACHMENT" ("OID" ASC)
NOLOGGING
VISIBLE;

-- ----------------------------
-- Checks structure for table SYS_ATTACHMENT
-- ----------------------------
ALTER TABLE "SYS_ATTACHMENT" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "SYS_ATTACHMENT" ADD CHECK ("ACTIVE" IS NOT NULL);
ALTER TABLE "SYS_ATTACHMENT" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "SYS_ATTACHMENT" ADD CHECK ("ACTIVE" IS NOT NULL);

-- ----------------------------
-- Indexes structure for table SYS_ATTACHMENTBUSINESSRELATION
-- ----------------------------
CREATE INDEX "I1175OID"
ON "SYS_ATTACHMENTBUSINESSRELATION" ("OID" ASC)
NOLOGGING
VISIBLE;

-- ----------------------------
-- Checks structure for table SYS_ATTACHMENTBUSINESSRELATION
-- ----------------------------
ALTER TABLE "SYS_ATTACHMENTBUSINESSRELATION" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "SYS_ATTACHMENTBUSINESSRELATION" ADD CHECK ("ATTACHMENT_ID" IS NOT NULL);
ALTER TABLE "SYS_ATTACHMENTBUSINESSRELATION" ADD CHECK ("BUSINESS_DATA_ID" IS NOT NULL);
ALTER TABLE "SYS_ATTACHMENTBUSINESSRELATION" ADD CHECK ("ACTIVE" IS NOT NULL);
ALTER TABLE "SYS_ATTACHMENTBUSINESSRELATION" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "SYS_ATTACHMENTBUSINESSRELATION" ADD CHECK ("ATTACHMENT_ID" IS NOT NULL);
ALTER TABLE "SYS_ATTACHMENTBUSINESSRELATION" ADD CHECK ("BUSINESS_DATA_ID" IS NOT NULL);
ALTER TABLE "SYS_ATTACHMENTBUSINESSRELATION" ADD CHECK ("ACTIVE" IS NOT NULL);
