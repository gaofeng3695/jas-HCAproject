/*
*角色视图
*/


-- ----------------------------
-- Table structure for V_ROLE_UNITNUMBER
-- ----------------------------
CREATE TABLE "V_ROLE_UNITNUMBER" (
"OBJECTID" NUMBER NOT NULL ,
"OID" NVARCHAR2(36) NULL ,
"ROLE_NAME" NVARCHAR2(50) NULL ,
"DESCRIPTION" NVARCHAR2(200) NULL ,
"ROLE_TYPE" NVARCHAR2(20) NULL ,
"UNIT_ID" NVARCHAR2(36) NULL ,
"HIERARCHY" NVARCHAR2(200) NULL ,
"UNITNAME" NVARCHAR2(50) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of V_ROLE_UNITNUMBER
-- ----------------------------
INSERT INTO "V_ROLE_UNITNUMBER" VALUES ('1', '1bda96e5-86cc-4a9a-b6c4-14c3bb06ecf3', 'docadmin', null, '2', 'f4b587f5-4f84-4e51-9ae7-529a7f16b2f6', 'Unit.0001', '顶层部门');
INSERT INTO "V_ROLE_UNITNUMBER" VALUES ('2', '40288add39ce15780139ce1c7ad10000', 'admin', '保护角色', '2', 'f4b587f5-4f84-4e51-9ae7-529a7f16b2f6', 'Unit.0001', '顶层部门');
INSERT INTO "V_ROLE_UNITNUMBER" VALUES ('3', '402894ec4d27f11c014d285742de0009', 'superadmin', '超级管理员', '2', 'f4b587f5-4f84-4e51-9ae7-529a7f16b2f6', 'Unit.0001', '顶层部门');
INSERT INTO "V_ROLE_UNITNUMBER" VALUES ('4', '464907af-8a83-44f1-ac7d-fd48304b53a5', 'test', null, '2', 'f4b587f5-4f84-4e51-9ae7-529a7f16b2f6', 'Unit.0001', '顶层部门');
INSERT INTO "V_ROLE_UNITNUMBER" VALUES ('5', '84003c54-d8a5-4724-8e77-0f5d7d029b79', '自治区领导', null, '2', 'f4b587f5-4f84-4e51-9ae7-529a7f16b2f6', 'Unit.0001', '顶层部门');
INSERT INTO "V_ROLE_UNITNUMBER" VALUES ('6', 'cd5f681b-3f2f-476d-a0ee-d7a85cbae4fe', '市级领导', null, '2', 'f4b587f5-4f84-4e51-9ae7-529a7f16b2f6', 'Unit.0001', '顶层部门');
INSERT INTO "V_ROLE_UNITNUMBER" VALUES ('7', '8c80764a-439a-4488-9afe-8e6a31308b29', '企业领导', null, '2', 'f4b587f5-4f84-4e51-9ae7-529a7f16b2f6', 'Unit.0001', '顶层部门');

-- ----------------------------
-- Indexes structure for table V_ROLE_UNITNUMBER
-- ----------------------------
CREATE UNIQUE INDEX "R1003_SDE_ROWID_UK"
ON "V_ROLE_UNITNUMBER" ("OBJECTID" ASC)
NOLOGGING
VISIBLE;

-- ----------------------------
-- Checks structure for table V_ROLE_UNITNUMBER
-- ----------------------------
ALTER TABLE "V_ROLE_UNITNUMBER" ADD CHECK ("OBJECTID" IS NOT NULL);
ALTER TABLE "V_ROLE_UNITNUMBER" ADD CHECK ("OBJECTID" IS NOT NULL);
