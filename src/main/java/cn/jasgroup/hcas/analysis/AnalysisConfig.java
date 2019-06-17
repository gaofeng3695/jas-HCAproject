package cn.jasgroup.hcas.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 高后果区
 * Created by kc on 2019/5/27.
 */
public class AnalysisConfig {
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
    public static String areaGradeSourceName = "hca_area_grade" ;
    /**
     *
     */
    public static String hcaSourceName = "hca_consequence_area" ;
    /**
     *
     */
    public static String buildingsSourcePopulationFieldName = "population";

    public static String householdsFieldName = "households";

    public static String startMileageFieldName = "start_mileage";

    public static String endMileageFieldName = "end_mileage";

    public static String horizontalDistanceFieldName = "horizontal_distance";

    public static String verticalDistanceFieldName = "vertical_distance";

    public static String startXFieldName = "start_x";

    public static String startYFieldName = "start_y";

    public static String buildingTypeFieldName = "building_type";

    /**
     * 居民地合并最小距离（单位：米）
     */
    public static Integer CongfigSettlementMergeDistance = 50 ; //m
    /**
     * 地区等级识别区域半径（单位：米）
     */
    public static Double CongfigRankAreaBufferDistance = 200d ; //m
    /**
     * 户数人口比值
     */
    public static Double CongfigHouseholdsPopulationRadio = 3.5 ;
    /**
     * 标准识别单元长度（单位：公里）
     */
    public static Double CongfigCoreCellStandardLength = 2d ; //km
    /**
     * 识别单元内部要素单元距离边界的最小距离
     */
    public static Double CongfigAreaRankBorderBufferDistance = 0.2 ; //km
    /**
     *
     */
    public static Integer CongfigCoreCellPopulationCondition1 = 53 ;
    /**
     *
     */
    public static Integer CongfigCoreCellHouseholdsCondition1 = 15 ;
    /**
     *
     */
    public static Integer CongfigAreaRankHouseholdsCondition1 = 100 ;
    /**
     *
     */
    public static Integer CongfigAreaRankHouseholdsCondition2 = 15 ;


    public final static String HighBuildingType = "四层及四层以上建筑为主";

    public final static String LowBuildingType = "四层以下建筑为主";

    protected static Logger logger = LoggerFactory.getLogger(AnalysisConfig.class);


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
