package cn.jasgroup.hcas.analysis.service;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.geometryservice.IGeometryService;
import cn.jasgroup.gis.geometryservice.arcgis.GeometryService;
import cn.jasgroup.gis.util.GeometryUtil;
import cn.jasgroup.gis.util.JtsUtil;
import cn.jasgroup.hcas.analysis.IPipelineOccupiedAnalysis;
import com.graphbuilder.geom.Geom;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/8/16
 * @since JDK 1.80
 */
@Service
public class PipelineOccupiedAnalysisService implements IPipelineOccupiedAnalysis {


    protected PipelineService pipelineService = new PipelineService();

    @Resource
    private IGeometryService geometryService;
    /**
     * 1、管线 -> 投影
     * 2、target -> 投影
     * 3、
     * @param oid
     * @param sourceName
     * @param buffer
     * @param target
     * @return
     */
    @Override
    public Boolean executePipelineOccupiedAnalysis(String oid, String sourceName, Double buffer, Geometry target) {
        Feature feature = pipelineService.queryTargetPipelineData(oid , sourceName);
        Geometry geometry = feature.getGeometry();
        Geometry projectedLine = GeometryUtil.blToGuass(geometry) ;
        Geometry bufferArea = GeometryUtil.buffer(projectedLine ,buffer,1);
        Geometry projectedTarget = GeometryUtil.blToGuass(target);
        return JtsUtil.intersect(bufferArea,projectedTarget);
    }
}
