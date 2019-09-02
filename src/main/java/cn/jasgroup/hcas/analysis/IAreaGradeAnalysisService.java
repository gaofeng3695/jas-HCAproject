package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.data.Feature;

/**
 * Created by kc on 2019/5/27.
 * 地区等级识别接口
 */
public interface IAreaGradeAnalysisService {

    /**
     * 查询管线数据
     * @param pipelineOid
     * @param buffer
     * @return
     */
    HcaLinearParam preparePipelineData(String pipelineOid , Double buffer) ;

    /**
     * 查询构筑物数据并计算必要的参数
     * @return
     */
    Feature[] prepareBuildingsFeatureData(HcaLinearParam bo);

    /**
     * 划分识别单元
     * @param buildings
     * @param bo
     * @return cellFeatures
     */
    Feature[] classifyAreaRankCellFeatures(Feature[] buildings ,HcaLinearParam bo);

    /**
     * 地区等级分类
     * @param cellFeatures
     * @param bo
     * @return
     */
    Feature[] classifyAreaRankGradeFeatures(Feature[] cellFeatures ,HcaLinearParam bo);

    /**
     * 地区等级划分
     * @return
     */
    HcaAnalysisResult executeAnalysis(String pipelineOid, Double buffer);

}
