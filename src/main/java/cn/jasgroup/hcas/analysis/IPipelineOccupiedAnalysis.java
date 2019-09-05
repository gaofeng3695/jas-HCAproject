package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.geometry.Geometry;

/**
 * Created by kc on 2019/6/3.
 */
public interface IPipelineOccupiedAnalysis {

    /***
     * 管线占压分析
     * @return
     */
    Boolean executePipelineOccupiedAnalysis(String oid ,String sourceName,Double buffer,Geometry target);

}
