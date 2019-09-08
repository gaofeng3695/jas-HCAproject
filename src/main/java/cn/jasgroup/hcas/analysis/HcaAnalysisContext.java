package cn.jasgroup.hcas.analysis;

/**
 *
 * 高后果区
 * Created by kc on 2019/5/27.
 */
public class HcaAnalysisContext {

    /**
     *
     */
    public static final String BuildingDistributionDomain = "building_distribution_domain";
    /**
     *
     */
    private String taskId;
    /**
     *
     */
    public static String TableKeyName = "oid";

    /**
     *
     */
    public static String pipelineSourceName = "hca_pipeline";

    /**
     *
     */
    public static String buildingsSourceName = "hca_buildings" ;
    /**
     *
     */
    public static String highImpactAreaBuildingsRefSourceName = "hca_high_impact_area_ref" ;

    /**
     *
     */
    public static String areaGradeSourceName = "hca_area" ;
    /**
     *
     */
    public static String hcaSourceName = "hca_high_impact_area" ;

    /**
     *
     */
    public static String versionIdFieldName = "version_oid";

    public static String versionNameFieldName = "version_name";
    /**
     *
     */
    public static String buildingsSourcePopulationFieldName = "population";

    public static String householdsFieldName = "households";

    public static String startMileageFieldName = "start_mileage";

    public static String endMileageFieldName = "end_mileage";

    public static String hcaLengthFieldName = "hca_length";

    public static String areaLengthFieldName = "area_length";



    public static String horizontalDistanceFieldName = "horizontal_distance";

    public static String verticalDistanceFieldName = "vertical_distance";

    public static String startXFieldName = "start_x";

    public static String startYFieldName = "start_y";

    public static String buildingTypeFieldName = "building_type";

    public static String buildingCodeFieldName = "building_code";

    /**
     *
     */
    public static String buildingTypeValue_Settlement= "building_type_020";
    /**
     *
     */
    public static String buildingTypeValue_SpecialPlace= "building_type_parent_001";
    /**
     *
     */
    public static String buildingTypeValue_UnSpecialPlace= "building_type_parent_003";


    public static String buildingDistributionTypeFieldName = "building_distribution";

    public static String areaRandRemarkFieldName = "description";//

    public static String hcaRemarkFieldName = "description";//描述

    public static String areaRankFieldName = "region_level";

    public static String hcaRankFieldName = "high_impact_level";

    public static String hcaCodeFieldName = "high_impact_area_code";

    public static String hcaNameFieldName = "high_impact_area_name";

    public static String pipePressureFieldName = "pressure";

    public static String pipeOuterDiameterFieldName = "outside_diameter";

    public static String pipelineOidFieldName = "pipeline_oid";

    /**
     * 居民地合并最小距离（单位：米）
     */
    public static Integer ConfigSettlementMergeDistance = 50 ; //m
    /**
     * 地区等级识别区域半径（单位：米）
     */
    public static Double ConfigRankAreaBufferDistance = 200d ; //m
    /**
     * 户数人口比值
     */
    public static Double ConfigHouseholdsPopulationRadio = 3.5 ;
    /**
     * 标准识别单元长度（单位：公里）
     */
    public static Double ConfigCoreCellStandardLength = 2000d ; //m
    /**
     * 识别单元内部要素单元距离边界的最小距离
     */
    public static Double ConfigAreaRankBorderBufferDistance = 200d ; //m
    /**
     *
     */
    public static Integer ConfigCoreCellPopulationCondition1 = 53 ;
    /**
     *
     */
    public static Integer ConfigCoreCellHouseholdsCondition1 = 15 ;
    /**
     *
     */
    public static Integer ConfigAreaRankHouseholdsCondition1 = 100 ;
    /**
     *
     */
    public static Integer ConfigAreaRankHouseholdsCondition2 = 15 ;

    public final static String AreaGradeLevel_IV = "region_level_004" ;

    public final static String AreaGradeLevel_III =  "region_level_003" ;

    public final static String AreaGradeLevel_II =  "region_level_002" ;

    public final static String AreaGradeLevel_I =  "region_level_001" ;

    public final static String HcaLevel_III = "high_impact_level_003" ;

    public final static String HcaLevel_II = "high_impact_level_002"  ;

    public final static String HcaLevel_I = "high_impact_level_001" ;

    public final static String HighBuildingDistributionType = "四层及四层以上建筑为主";

    public final static String LowBuildingDistributionType = "四层以下建筑为主";

    private static String explosivePlaceExpress = null ;

    private static String specialPlaceExpress = null ;

    private static String unSpecialPlaceExpress = null ;

    public static String getExplosivePlaceExpress() {
        return explosivePlaceExpress;
    }

    public static void setExplosivePlaceExpress(String explosivePlaceExpress) {
        HcaAnalysisContext.explosivePlaceExpress = explosivePlaceExpress;
    }

    public static String getSpecialPlaceExpress() {

        return specialPlaceExpress;
    }

    public static void setSpecialPlaceExpress(String specialPlaceExpress) {
        HcaAnalysisContext.specialPlaceExpress = specialPlaceExpress;
    }


    public static String getUnSpecialPlaceExpress() {
        return unSpecialPlaceExpress;
    }

    public static void setUnSpecialPlaceExpress(String unSpecialPlaceExpress) {
        HcaAnalysisContext.unSpecialPlaceExpress = unSpecialPlaceExpress;
    }

    /**
     * 计算潜在影响范围半径
     * @param d 管径（单位：毫米）
     * @param p 压强（单位：MPa）
     * @return
     */
    public static Double calculateHCABufferRadius(double d ,double p){
        Double r = 0d ;
        if( d <= 762 && d >= 273 && p <= 6.9 && p >= 1.6 ){
            r = 200d ;
        }else{
            r = 0.099 * Math.sqrt( d * d * p );
        }
        return r;
    }

}
