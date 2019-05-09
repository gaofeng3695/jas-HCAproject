
CREATE TABLE "public"."demo_pd_chuankuayue" (
"gid" int4  NOT NULL,
"objectid" int4,
"eventid" varchar(38) COLLATE "default",
"createdby" varchar(15) COLLATE "default",
"createddat" date,
"effectivef" date,
"effectivet" date,
"historical" int2,
"groupevent" varchar(38) COLLATE "default",
"lastmodifi" date,
"modifiedby" varchar(50) COLLATE "default",
"operationa" int2,
"origineven" varchar(50) COLLATE "default",
"processfla" varchar(10) COLLATE "default",
"dataresolu" int2,
"subtype" int2,
"beginstati" numeric,
"endstation" numeric,
"branchpare" varchar(38) COLLATE "default",
"fromseries" varchar(38) COLLATE "default",
"lineloopev" varchar(38) COLLATE "default",
"seriesname" varchar(25) COLLATE "default",
"toseriesev" varchar(38) COLLATE "default",
"seriesorde" int4,
"fromconnec" numeric,
"toconnecti" numeric,
"department" varchar(38) COLLATE "default",
"remarks" varchar(254) COLLATE "default",
"privilegen" varchar(20) COLLATE "default",
"shape_leng" numeric,
CONSTRAINT "demo_pd_chuankuayue_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_chuankuayue" OWNER TO "postgres";

CREATE TABLE "public"."demo_pd_dizai" (
"gid" int4 NOT NULL,
"id" int4,
"level" varchar(50) COLLATE "default",
CONSTRAINT "demo_pd_dizai_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_dizai" OWNER TO "postgres";


CREATE TABLE "public"."demo_pd_fashi" (
"gid" int4  NOT NULL,
"objectid" int4,
"eventid" varchar(38) COLLATE "default",
"createdby" varchar(15) COLLATE "default",
"createddat" date,
"historical" int2,
"groupevent" varchar(38) COLLATE "default",
"lastmodifi" date,
"modifiedby" varchar(15) COLLATE "default",
"operationa" int2,
"origineven" varchar(38) COLLATE "default",
"processfla" varchar(10) COLLATE "default",
"dataresolu" int2,
"stationser" varchar(38) COLLATE "default",
"station" numeric,
"locationin" varchar(128) COLLATE "default",
"sitename" varchar(25) COLLATE "default",
"sitetype" int2,
"contact" varchar(38) COLLATE "default",
"x" numeric,
"y" numeric,
"z" numeric,
"cathodicpr" varchar(38) COLLATE "default",
"enterweldn" varchar(38) COLLATE "default",
"outweldnum" varchar(38) COLLATE "default",
"startdate" date,
"enddate" date,
"remarks" varchar(254) COLLATE "default",
"subsysteme" varchar(38) COLLATE "default",
"orderid" int2,
"belongedst" varchar(20) COLLATE "default",
"department" varchar(20) COLLATE "default",
"upperuser" varchar(254) COLLATE "default",
"downuser" varchar(254) COLLATE "default",
"jasframewo" varchar(50) COLLATE "default",
"站场内是否" varchar(254) COLLATE "default",
"piggingtyp" varchar(50) COLLATE "default",
CONSTRAINT "demo_pd_fashi_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_fashi" OWNER TO "postgres";




CREATE TABLE "public"."demo_pd_fengxian" (
"gid" int4 NOT NULL,
"id" int4,
"level" varchar(50) COLLATE "default",
CONSTRAINT "demo_pd_fengxian_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_fengxian" OWNER TO "postgres";

CREATE TABLE "public"."demo_pd_fushi" (
"gid" int4  NOT NULL,
"objectid" int4,
"eventid" varchar(38) COLLATE "default",
"createdby" varchar(15) COLLATE "default",
"createddat" date,
"effectivef" date,
"effectivet" date,
"historical" int2,
"groupevent" varchar(38) COLLATE "default",
"lastmodifi" date,
"modifiedby" varchar(50) COLLATE "default",
"operationa" int2,
"origineven" varchar(50) COLLATE "default",
"processfla" varchar(10) COLLATE "default",
"dataresolu" int2,
"subtype" int2,
"beginstati" numeric,
"endstation" numeric,
"branchpare" varchar(38) COLLATE "default",
"fromseries" varchar(38) COLLATE "default",
"lineloopev" varchar(38) COLLATE "default",
"seriesname" varchar(25) COLLATE "default",
"toseriesev" varchar(38) COLLATE "default",
"seriesorde" int4,
"fromconnec" numeric,
"toconnecti" numeric,
"department" varchar(38) COLLATE "default",
"remarks" varchar(254) COLLATE "default",
"privilegen" varchar(20) COLLATE "default",
"shape_leng" numeric,
CONSTRAINT "demo_pd_fushi_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_fushi" OWNER TO "postgres";



CREATE TABLE "public"."demo_pd_gaohouguoqu" (
"gid" int4  NOT NULL,
"objectid" int4,
"eventid" varchar(38) COLLATE "default",
"createdby" varchar(15) COLLATE "default",
"createddat" date,
"effectivef" date,
"effectivet" date,
"historical" int2,
"groupevent" varchar(38) COLLATE "default",
"lastmodifi" date,
"modifiedby" varchar(50) COLLATE "default",
"operationa" int2,
"origineven" varchar(50) COLLATE "default",
"processfla" varchar(10) COLLATE "default",
"dataresolu" int2,
"subtype" int2,
"beginstati" numeric,
"endstation" numeric,
"branchpare" varchar(38) COLLATE "default",
"fromseries" varchar(38) COLLATE "default",
"lineloopev" varchar(38) COLLATE "default",
"seriesname" varchar(25) COLLATE "default",
"toseriesev" varchar(38) COLLATE "default",
"seriesorde" int4,
"fromconnec" numeric,
"toconnecti" numeric,
"department" varchar(38) COLLATE "default",
"remarks" varchar(254) COLLATE "default",
"privilegen" varchar(20) COLLATE "default",
"shape_leng" numeric,
"buff_dist" numeric,
"level" numeric,
CONSTRAINT "demo_pd_gaohouguoqu_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_gaohouguoqu" OWNER TO "postgres";


CREATE TABLE "public"."demo_pd_gongan" (
"gid" int4 NOT NULL,
"id" int4,
CONSTRAINT "demo_pd_gongan_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_gongan" OWNER TO "postgres";


CREATE TABLE "public"."demo_pd_gongdi" (
"gid" int4  NOT NULL,
"id" int4,
"level" varchar(80) COLLATE "default",
CONSTRAINT "demo_pd_gongdi_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_gongdi" OWNER TO "postgres";


CREATE TABLE "public"."demo_pd_neijiance" (
"gid" int4 NOT NULL,
"objectid" int4,
"eventid" varchar(38) COLLATE "default",
"createdby" varchar(15) COLLATE "default",
"createddat" date,
"effectivef" date,
"effectivet" date,
"historical" int2,
"groupevent" varchar(38) COLLATE "default",
"lastmodifi" date,
"modifiedby" varchar(50) COLLATE "default",
"operationa" int2,
"origineven" varchar(50) COLLATE "default",
"processfla" varchar(10) COLLATE "default",
"dataresolu" int2,
"subtype" int2,
"beginstati" numeric,
"endstation" numeric,
"branchpare" varchar(38) COLLATE "default",
"fromseries" varchar(38) COLLATE "default",
"lineloopev" varchar(38) COLLATE "default",
"seriesname" varchar(25) COLLATE "default",
"toseriesev" varchar(38) COLLATE "default",
"seriesorde" int4,
"fromconnec" numeric,
"toconnecti" numeric,
"department" varchar(38) COLLATE "default",
"remarks" varchar(254) COLLATE "default",
"privilegen" varchar(20) COLLATE "default",
"shape_leng" numeric,
CONSTRAINT "demo_pd_neijiance_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_neijiance" OWNER TO "postgres";


CREATE TABLE "public"."demo_pd_pipeline" (
"gid" int4  NOT NULL,
"objectid" int4,
"eventid" varchar(38) COLLATE "default",
"createdby" varchar(15) COLLATE "default",
"createddat" date,
"effectivef" date,
"effectivet" date,
"historical" int2,
"groupevent" varchar(38) COLLATE "default",
"lastmodifi" date,
"modifiedby" varchar(50) COLLATE "default",
"operationa" int2,
"origineven" varchar(50) COLLATE "default",
"processfla" varchar(10) COLLATE "default",
"dataresolu" int2,
"subtype" int2,
"beginstati" numeric,
"endstation" numeric,
"branchpare" varchar(38) COLLATE "default",
"fromseries" varchar(38) COLLATE "default",
"lineloopev" varchar(38) COLLATE "default",
"seriesname" varchar(25) COLLATE "default",
"toseriesev" varchar(38) COLLATE "default",
"seriesorde" int4,
"fromconnec" numeric,
"toconnecti" numeric,
"department" varchar(38) COLLATE "default",
"remarks" varchar(254) COLLATE "default",
"privilegen" varchar(20) COLLATE "default",
"shape_leng" numeric,
CONSTRAINT "demo_pd_pipeline_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_pipeline" OWNER TO "postgres";


CREATE TABLE "public"."demo_pd_shexiangtou" (
"gid" int4 NOT NULL,
"id" int4,
CONSTRAINT "demo_pd_shexiangtou_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_shexiangtou" OWNER TO "postgres";

CREATE TABLE "public"."demo_pd_shuiyuandi" (
"gid" int4  NOT NULL,
"id" int4,
CONSTRAINT "demo_pd_shuiyuandi_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_shuiyuandi" OWNER TO "postgres";




CREATE TABLE "public"."demo_pd_xiaofangdui" (
"gid" int4  NOT NULL,
"id" int4,
CONSTRAINT "demo_pd_xiaofangdui_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_xiaofangdui" OWNER TO "postgres";


CREATE TABLE "public"."demo_pd_xuexiao" (
"gid" int4  NOT NULL,
"id" int4,
CONSTRAINT "demo_pd_xuexiao_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_xuexiao" OWNER TO "postgres";


CREATE TABLE "public"."demo_pd_yinhuan" (
"gid" int4  NOT NULL,
"id" int4,
"level" varchar(80) COLLATE "default",
CONSTRAINT "demo_pd_yinhuan_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_yinhuan" OWNER TO "postgres";



CREATE TABLE "public"."demo_pd_yiyuan" (
"gid" int4  NOT NULL,
"id" int4,
CONSTRAINT "demo_pd_yiyuan_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_yiyuan" OWNER TO "postgres";


CREATE TABLE "public"."demo_pd_zhanchang" (
"gid" int4  NOT NULL,
"objectid" int4,
"eventid" varchar(38) COLLATE "default",
"createdby" varchar(15) COLLATE "default",
"createddat" date,
"historical" int2,
"groupevent" varchar(38) COLLATE "default",
"lastmodifi" date,
"modifiedby" varchar(15) COLLATE "default",
"operationa" int2,
"origineven" varchar(38) COLLATE "default",
"processfla" varchar(10) COLLATE "default",
"dataresolu" int2,
"stationser" varchar(38) COLLATE "default",
"station" numeric,
"locationin" varchar(128) COLLATE "default",
"sitename" varchar(25) COLLATE "default",
"sitetype" int2,
"contact" varchar(38) COLLATE "default",
"x" numeric,
"y" numeric,
"z" numeric,
"cathodicpr" varchar(38) COLLATE "default",
"enterweldn" varchar(38) COLLATE "default",
"outweldnum" varchar(38) COLLATE "default",
"startdate" date,
"enddate" date,
"remarks" varchar(254) COLLATE "default",
"subsysteme" varchar(38) COLLATE "default",
"orderid" int2,
"belongedst" varchar(20) COLLATE "default",
"department" varchar(20) COLLATE "default",
"upperuser" varchar(254) COLLATE "default",
"downuser" varchar(254) COLLATE "default",
"jasframewo" varchar(50) COLLATE "default",
"站场内是否" varchar(254) COLLATE "default",
"piggingtyp" varchar(50) COLLATE "default",
CONSTRAINT "demo_pd_zhanchang_pkey" PRIMARY KEY ("gid")
)
WITH (OIDS=FALSE)
;

ALTER TABLE "public"."demo_pd_zhanchang" OWNER TO "postgres";


select AddGeometryColumn('public', 'demo_pd_chuankuayue', 'geom', 4326, 'LINESTRING', 2);
CREATE INDEX demo_pd_chuankuayue_geom_idx ON public.demo_pd_chuankuayue USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_dizai', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_dizai_geom_idx ON public.demo_pd_dizai USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_fengxian', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_fengxian_geom_idx ON public.demo_pd_fengxian USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_fushi', 'geom', 4326, 'LINESTRING', 2);
CREATE INDEX demo_pd_fushi_geom_idx ON public.demo_pd_fushi USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_gaohouguoqu', 'geom', 4326, 'POLYGON', 2);
CREATE INDEX demo_pd_gaohouguoqu_geom_idx ON public.demo_pd_gaohouguoqu USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_gongan', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_gongan_geom_idx ON public.demo_pd_gongan USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_gongdi', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_gongdi_geom_idx ON public.demo_pd_gongdi USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_xiaofangdui', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_xiaofangdui_geom_idx ON public.demo_pd_xiaofangdui USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_xuexiao', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_xuexiao_geom_idx ON public.demo_pd_xuexiao USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_yinhuan', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_yinhuan_geom_idx ON public.demo_pd_yinhuan USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_yiyuan', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_yiyuan_geom_idx ON public.demo_pd_yiyuan USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_neijiance', 'geom', 4326, 'MULTILINESTRING', 2);
CREATE INDEX demo_pd_neijiance_geom_idx ON public.demo_pd_neijiance USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_pipeline', 'geom', 4326, 'MULTILINESTRING', 2);
CREATE INDEX demo_pd_pipeline_geom_idx ON public.demo_pd_pipeline USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_shuiyuandi', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_shuiyuandi_geom_idx ON public.demo_pd_shuiyuandi USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_fashi', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_fashi_geom_idx ON public.demo_pd_fashi USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_zhanchang', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_zhanchang_geom_idx ON public.demo_pd_zhanchang USING gist (geom);

select AddGeometryColumn('public', 'demo_pd_shexiangtou', 'geom', 4326, 'POINT', 2);
CREATE INDEX demo_pd_shexiangtou_geom_idx ON public.demo_pd_shexiangtou USING gist (geom);


CREATE SEQUENCE "public"."demo_pd_chuankuayue_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 8
 CACHE 1
 OWNED BY "public"."demo_pd_chuankuayue"."gid";

ALTER TABLE "public"."demo_pd_chuankuayue_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_chuankuayue_gid_seq"', 8, true);

CREATE SEQUENCE "public"."demo_pd_dizai_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 6
 CACHE 1
 OWNED BY "public"."demo_pd_dizai"."gid";

ALTER TABLE "public"."demo_pd_dizai_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_dizai_gid_seq"', 6, true);

CREATE SEQUENCE "public"."demo_pd_fashi_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 14
 CACHE 1
 OWNED BY "public"."demo_pd_fashi"."gid";

ALTER TABLE "public"."demo_pd_fashi_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_fashi_gid_seq"', 14, true);

CREATE SEQUENCE "public"."demo_pd_fengxian_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 10
 CACHE 1
 OWNED BY "public"."demo_pd_fengxian"."gid";

ALTER TABLE "public"."demo_pd_fengxian_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_fengxian_gid_seq"', 10, true);

CREATE SEQUENCE "public"."demo_pd_fushi_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3
 CACHE 1
 OWNED BY "public"."demo_pd_fushi"."gid";

ALTER TABLE "public"."demo_pd_fushi_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_fushi_gid_seq"', 3, true);

CREATE SEQUENCE "public"."demo_pd_gaohouguoqu_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 5
 CACHE 1
 OWNED BY "public"."demo_pd_gaohouguoqu"."gid";

ALTER TABLE "public"."demo_pd_gaohouguoqu_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_gaohouguoqu_gid_seq"', 5, true);

CREATE SEQUENCE "public"."demo_pd_gongan_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 7
 CACHE 1
 OWNED BY "public"."demo_pd_gongan"."gid";

ALTER TABLE "public"."demo_pd_gongan_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_gongan_gid_seq"', 7, true);

CREATE SEQUENCE "public"."demo_pd_gongdi_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 6
 CACHE 1
 OWNED BY "public"."demo_pd_gongdi"."gid";

ALTER TABLE "public"."demo_pd_gongdi_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_gongdi_gid_seq"', 6, true);


CREATE SEQUENCE "public"."demo_pd_neijiance_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 31
 CACHE 1
 OWNED BY "public"."demo_pd_neijiance"."gid";

ALTER TABLE "public"."demo_pd_neijiance_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_neijiance_gid_seq"', 31, true);

CREATE SEQUENCE "public"."demo_pd_pipeline_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 31
 CACHE 1
 OWNED BY "public"."demo_pd_pipeline"."gid";

ALTER TABLE "public"."demo_pd_pipeline_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_pipeline_gid_seq"', 31, true);

CREATE SEQUENCE "public"."demo_pd_shexiangtou_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 7
 CACHE 1
 OWNED BY "public"."demo_pd_shexiangtou"."gid";

ALTER TABLE "public"."demo_pd_shexiangtou_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_shexiangtou_gid_seq"', 7, true);

CREATE SEQUENCE "public"."demo_pd_shuiyuandi_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2
 CACHE 1
 OWNED BY "public"."demo_pd_shuiyuandi"."gid";

ALTER TABLE "public"."demo_pd_shuiyuandi_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_shuiyuandi_gid_seq"', 2, true);

CREATE SEQUENCE "public"."demo_pd_xiaofangdui_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 7
 CACHE 1
 OWNED BY "public"."demo_pd_xiaofangdui"."gid";

ALTER TABLE "public"."demo_pd_xiaofangdui_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_xiaofangdui_gid_seq"', 7, true);

CREATE SEQUENCE "public"."demo_pd_xuexiao_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 7
 CACHE 1
 OWNED BY "public"."demo_pd_xuexiao"."gid";

ALTER TABLE "public"."demo_pd_xuexiao_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_xuexiao_gid_seq"', 7, true);


CREATE SEQUENCE "public"."demo_pd_yinhuan_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 4
 CACHE 1
 OWNED BY "public"."demo_pd_yinhuan"."gid";

ALTER TABLE "public"."demo_pd_yinhuan_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_yinhuan_gid_seq"', 4, true);


CREATE SEQUENCE "public"."demo_pd_yiyuan_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 7
 CACHE 1
 OWNED BY "public"."demo_pd_yiyuan"."gid";

ALTER TABLE "public"."demo_pd_yiyuan_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_yiyuan_gid_seq"', 7, true);

CREATE SEQUENCE "public"."demo_pd_zhanchang_gid_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 19
 CACHE 1
 OWNED BY "public"."demo_pd_zhanchang"."gid";

ALTER TABLE "public"."demo_pd_zhanchang_gid_seq" OWNER TO "postgres";

SELECT setval('"public"."demo_pd_zhanchang_gid_seq"', 19, true);