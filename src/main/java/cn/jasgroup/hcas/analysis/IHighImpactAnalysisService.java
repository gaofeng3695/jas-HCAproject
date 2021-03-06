package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.data.Feature;

/**
 * Created by kc on 2019/5/27.
 * 地区等级识别接口
 */
public interface IHighImpactAnalysisService {

    /**
     * 高后果区识别入口
     * @return
     */
    HcaAnalysisResult executeAnalysis(String versionCode);

    /**
     * 高后果区分类
     * @param areaData
     * @param bo
     * @param versionId
     * @return
     */
    Feature[] classifyHighConsequenceArea(Feature[] areaData, HcaLinearParam bo, String versionId);

    /**
     * 计算潜在影响区缓冲区半径
     * @param pressure
     * @param outerDimension
     * @return
     */
    double calculatePotentialInfluenceBufferDistance(double pressure, double outerDimension) ;

    /**
     * 特定场所判定
     * @param areaFeature
     * @param hcaFeature
     * @param bo
     * @return
     */
    BuildingLocationMap specialPlaceAnalysis(Feature[] areaFeature, Feature[] hcaFeature, HcaLinearParam bo) ;

    /**
     * 易燃易爆场所判定
     * @param areaFeature
     * @param hcaFeature
     * @param bo
     * @return
     */
    BuildingLocationMap explosivePlaceAnalysis(Feature[] areaFeature, Feature[] hcaFeature, HcaLinearParam bo) ;

    /**
     *
     * @param hcaDataBeforeMerge
     * @param explosiveLocationMap
     * @param specialLocationMap
     * @return
     */
    Feature[] mergeHcaFeatures(Feature[] hcaDataBeforeMerge, BuildingLocationMap explosiveLocationMap, BuildingLocationMap specialLocationMap);

    /**
     * 地区等级判定
     * @param feature
     * @param versionId
     * @param pipelineOid
     * @return
     */
    Feature[] areaGradeAnalysis(Feature[] feature, String versionId, String pipelineOid) ;

    /**
     *
     * @param result
     * @param areaVersionId
     * @param hcaVersionId
     * @param pipelineOid
     * @return
     */
    int saveHcaResult(Feature[] result, String areaVersionId, String hcaVersionId, String pipelineOid) ;

}
