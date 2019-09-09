/*
*	阈值表
*/


-- ----------------------------
-- Table structure for SYS_DOMAIN
-- ----------------------------
CREATE TABLE "SYS_DOMAIN" (
"OID" NVARCHAR2(36) NOT NULL ,
"DOMAIN_NAME" NVARCHAR2(50) NOT NULL ,
"CODE_ID" NVARCHAR2(50) NULL ,
"PARENT_CODE_ID" NVARCHAR2(36) NULL ,
"CODE_NAME" NVARCHAR2(50) NULL ,
"ORDINAL" NUMBER(5) NULL ,
"DESCRIPTION" NVARCHAR2(255) NULL ,
"CREATE_USER_ID" NVARCHAR2(36) NULL ,
"CREATE_USER_NAME" NVARCHAR2(50) NULL ,
"CREATE_DATETIME" TIMESTAMP(6)  NULL ,
"MODIFY_USER_ID" NVARCHAR2(36) NULL ,
"MODIFY_USER_NAME" NVARCHAR2(50) NULL ,
"MODIFY_DATETIME" TIMESTAMP(6)  NULL ,
"ACTIVE" NUMBER(5) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

-- ----------------------------
-- Records of SYS_DOMAIN
-- ----------------------------
INSERT INTO "SYS_DOMAIN" VALUES ('9d26fe17-8df6-4afb-8eac-52247db027a8', 'building_distribution_domain', 'building_distribution_001', null, '四层及四层以上建筑为主', '1', '建筑分布', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:24:14:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:24:14:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('0d772c09-b164-460e-aac4-905f2f5aaaf0', 'building_distribution_domain', 'building_distribution_002', null, '四层以下建筑为主', '2', '建筑分布', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:24:38:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:24:38:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('cdbe8d39-8e6d-4b55-8786-bad964a27456', 'high_impact_level_domain', 'high_impact_level_001', null, 'Ⅰ级', '1', '高后果区等级', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:25:22:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:25:22:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('db29bccc-d7b5-4ca2-8616-80a4db7af6c5', 'high_impact_level_domain', 'high_impact_level_002', null, 'Ⅱ级', '2', '高后果区等级', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:26:05:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:26:05:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('5c51b740-6cd8-4d95-b657-a5e4d9b25aaa', 'high_impact_level_domain', 'high_impact_level_003', null, 'Ⅲ级', '3', '高后果区等级', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:26:32:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:26:32:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('087a6b5f-feec-4d8d-b2f8-6659a323d7e9', 'region_level_domain', 'region_level_002', null, '二级地区', '2', '地区等级', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:27:47:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:27:47:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('e7d06ce9-21a4-488a-beb5-f6371b3c94ca', 'region_level_domain', 'region_level_003', null, '三级地区', '3', '地区等级', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:28:21:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:28:21:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('6a9698fa-341d-4a82-95b0-d38dde30b81f', 'region_level_domain', 'region_level_004', null, '四级地区', '4', '地区等级', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:28:45:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:28:45:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('0fc55622-a4cf-4654-b117-e69ae2a09c19', 'region_level_domain', 'region_level_001', null, '一级地区', '1', '地区等级', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:27:23:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:27:23:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('9b7183e4-39ca-4425-88be-caf05c870940', 'building_type_domain', 'building_type_011', 'building_type_parent_001', '运动场', '11', '构筑物类型', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:17:15:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:17:15:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('871b45d2-c382-4199-88cc-9d4d12eb6d5b', 'building_type_domain', 'building_type_010', 'building_type_parent_001', '寺庙', '10', '构筑物类型', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:16:43:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:16:43:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('d63adc48-4c11-47e3-b1f6-db170842c9ea', 'building_type_domain', 'building_type_009', 'building_type_parent_001', '集贸市场', '9', '构筑物类型', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:14:20:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:14:20:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('f5cd1bd5-d2bc-40c3-a9d7-4a40b5caa098', 'building_type_parent_domain', 'building_type_parent_002', null, '易燃易爆场所', '3', '构筑物类型父级', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:22:50:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:22:50:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('c4a401a4-2d60-4301-9b39-2bbbc641c010', 'building_type_parent_domain', 'building_type_parent_001', null, '特定场所', '2', '构筑物分类父级', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:21:43:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:21:43:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('786ad03b-b1d7-4e38-ab52-e520fc8e49be', 'building_type_domain', 'building_type_016', 'building_type_parent_002', '加油站', '16', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:39:17:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:39:17:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('d700a08b-93a9-4dd9-9cb0-a407b19434db', 'building_type_domain', 'building_type_017', 'building_type_parent_002', '加气站', '17', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:39:39:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:39:39:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('dd9fbf34-95b3-482d-9eb5-440f24849ab6', 'building_type_domain', 'building_type_018', 'building_type_parent_002', '炸药库', '18', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:40:03:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:40:03:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('1c9985ae-ec68-4671-92c3-0350d51c0841', 'building_type_domain', 'building_type_015', 'building_type_parent_001', '露营地', '15', '构筑物类型', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:19:28:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:19:28:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('6357b12b-90b8-4f16-809e-deba634ce7b4', 'building_type_domain', 'building_type_014', 'building_type_parent_001', '剧院', '14', '构筑物类型', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:19:05:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:19:05:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('5ad8e566-f0b3-439d-a142-911dd330c882', 'building_type_domain', 'building_type_013', 'building_type_parent_001', '娱乐休闲地', '13', '构筑物类型', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:18:40:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:18:40:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('6c45d44c-1664-4f43-b55d-a73ce16cdda4', 'building_type_domain', 'building_type_012', 'building_type_parent_001', '广场', '12', '构筑物类型', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:17:45:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-07-01 16:17:45:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('36be504f-4dd8-4f3c-a931-540b16d59669', 'building_type_domain', 'building_type_008', 'building_type_parent_001', '商场', '8', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:38:34:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:38:34:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('d4322713-9169-4ab0-97f2-9da47ab4a4fe', 'building_type_domain', 'building_type_007', 'building_type_parent_001', '工厂', '7', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:36:12:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:36:12:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('4a122ef7-ec59-4643-8f4d-4a177863e7db', 'building_type_domain', 'building_type_006', 'building_type_parent_001', '监狱', '6', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:35:48:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:35:48:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('a2ebb1f0-8d53-4f40-aa70-12c9f942accc', 'building_type_domain', 'building_type_005', 'building_type_parent_001', '养老院', '5', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:35:07:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:35:07:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('4cabf821-ae2c-4f58-9514-8538372fcaaa', 'building_type_domain', 'building_type_004', 'building_type_parent_001', '幼儿园', '4', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:34:22:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:34:22:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('5f911a6e-c3a2-4054-99c6-38cae672be41', 'building_type_domain', 'building_type_003', 'building_type_parent_001', '托儿所', '3', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:33:43:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:33:43:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('71f25105-c90a-41a9-94f3-d0d2b5d10860', 'building_type_domain', 'building_type_002', 'building_type_parent_001', '学校', '2', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:32:44:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:32:44:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('5c155c6d-c932-4c31-8990-dda152dc2621', 'building_type_domain', 'building_type_001', 'building_type_parent_001', '医院', '1', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:23:39:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-21 10:23:39:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('9bf511ac-345a-4d8c-adc0-d75fcc79ec9f', 'building_type_domain', 'building_type_019', 'building_type_parent_003', '独立民居', '19', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:40:29:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:40:29:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('85e42cf8-cd82-42ab-8b64-37708d2a5616', 'building_type_domain', 'building_type_020', 'building_type_parent_003', '居民区', '20', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:40:58:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:40:58:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('85c0d9e7-cff5-4197-98ec-a094d60441ad', 'building_type_domain', 'building_type_021', 'building_type_parent_003', '商业区', '21', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:41:25:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:41:25:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('f2289e6f-3cdc-453e-8af1-878576c8a936', 'building_type_domain', 'building_type_022', 'building_type_parent_003', '工业区', '22', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:42:02:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:42:02:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('dde1f848-a366-436f-a632-e68728db77f0', 'building_type_domain', 'building_type_023', 'building_type_parent_003', '规划区', '23', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:42:31:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:42:31:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('b266b30f-b292-48c6-89bf-42c482a4ac11', 'building_type_domain', 'building_type_024', 'building_type_parent_003', '市郊居民区', '24', '构筑物类型', '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:43:08:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '8acae2a5-e9d2-460a-864c-00cb6d0df2bd', '自治区领导', TO_TIMESTAMP(' 2019-06-24 11:43:08:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('c4a401a4-2d60-4301-9b39-2bbbc641c011', 'building_type_parent_domain', 'building_type_parent_003', null, '非特定场所', '1', '构筑物分类父级', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-08-19 09:36:58:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-08-19 09:37:18:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('b266b30f-b292-48c6-89bf-42c482a4ac12', 'building_type_domain', 'building_type_025', 'building_type_parent_001', '其他', '25', '构筑物类型', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-08-20 15:25:27:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-08-20 15:25:44:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('b266b30f-b292-48c6-89bf-42c482a4ac13', 'building_type_domain', 'building_type_026', 'building_type_parent_002', '其他', '26', '构筑物类型', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-08-20 15:26:07:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-08-20 15:26:18:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');
INSERT INTO "SYS_DOMAIN" VALUES ('b266b30f-b292-48c6-89bf-42c482a4ac14', 'building_type_domain', 'building_type_027', 'building_type_parent_003', '其他', '27', '构筑物类型', '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-08-20 15:28:27:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1df621c3346c4a8bac91d1238afc8400', '管理员', TO_TIMESTAMP(' 2019-08-20 15:28:37:000000', 'YYYY-MM-DD HH24:MI:SS:FF6'), '1');

-- ----------------------------
-- Indexes structure for table SYS_DOMAIN
-- ----------------------------
CREATE INDEX "I1215DOMAIN_NAME"
ON "SYS_DOMAIN" ("DOMAIN_NAME" ASC)
NOLOGGING
VISIBLE;
CREATE INDEX "I1215OID"
ON "SYS_DOMAIN" ("OID" ASC)
NOLOGGING
VISIBLE;

-- ----------------------------
-- Checks structure for table SYS_DOMAIN
-- ----------------------------
ALTER TABLE "SYS_DOMAIN" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "SYS_DOMAIN" ADD CHECK ("DOMAIN_NAME" IS NOT NULL);
ALTER TABLE "SYS_DOMAIN" ADD CHECK ("ACTIVE" IS NOT NULL);
ALTER TABLE "SYS_DOMAIN" ADD CHECK ("OID" IS NOT NULL);
ALTER TABLE "SYS_DOMAIN" ADD CHECK ("DOMAIN_NAME" IS NOT NULL);
ALTER TABLE "SYS_DOMAIN" ADD CHECK ("ACTIVE" IS NOT NULL);
