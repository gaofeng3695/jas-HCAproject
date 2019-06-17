package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.data.Feature;

/**
 * Created by kc on 2019/5/27.
 * 地区等级识别接口
 */
public interface IAnalysisService {

    /**
     *
     * @param pipelineOid
     * @param buffer
     * @return
     */
    AnalysisGeometryBO preparePipelineData(String pipelineOid ,Double buffer) ;

    /**
     *
     * @return
     */
    Feature[] prepareBuildingsFeatureData(AnalysisGeometryBO bo);

    /**
     *
     * @return
     */
    Feature[] mergeBuildingsFeatureData(Feature[] features);
    /**
     *
     * @param from
     * @param to
     * @return
     */
    Feature mergeFeatureProperties(Feature from ,Feature to) ;

    /**
     *
     * @param buildings
     * @param bo
     * @return
     */
    Feature[] classifyAreaRankGradeFeatures(Feature[] buildings ,AnalysisGeometryBO bo);

    /**
     * 高后果区识别
     * @return
     */
    AnalysisResult executeHcaAnalysis(HcaParam param);

    /**
     * 地区等级划分
     * @return
     */
    AnalysisResult executeAreaGradeAnalysis(String pipelineOid, Double buffer);


}
