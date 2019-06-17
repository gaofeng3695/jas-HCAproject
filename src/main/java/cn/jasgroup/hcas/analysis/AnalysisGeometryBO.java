package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.geometry.Polygon;
import cn.jasgroup.gis.geometry.Polyline;
import cn.jasgroup.gis.util.LinearReferenceUtil;

/**
 *
 * Created by kc on 2019/5/27.
 */
public class AnalysisGeometryBO {

    LinearReferenceUtil linearReferenceUtil;
    /**
     * 目标管线的里程
     */
    private Double totalMileage = 0d;
    /**
     * 目标管线要素几何对象
     */
    private Polyline pipeline ;
    /**
     * 识别区缓冲区几何对象
     */
    private Polygon recognitionAreaBuffer;
    /**
     * 潜在影响区缓冲区几何对象
     */
    private Polygon potentialInfluenceBuffer;

    public Double getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(Double totalMileage) {
        this.totalMileage = totalMileage;
    }

    public Polyline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Polyline pipeline) {
        this.pipeline = pipeline;
    }

    public Polygon getRecognitionAreaBuffer() {
        return recognitionAreaBuffer;
    }

    public void setRecognitionAreaBuffer(Polygon recognitionAreaBuffer) {
        this.recognitionAreaBuffer = recognitionAreaBuffer;
    }

    public Polygon getPotentialInfluenceBuffer() {
        return potentialInfluenceBuffer;
    }

    public void setPotentialInfluenceBuffer(Polygon potentialInfluenceBuffer) {
        this.potentialInfluenceBuffer = potentialInfluenceBuffer;
    }

    public LinearReferenceUtil getLinearReferenceUtil() {
        if(linearReferenceUtil == null){
            linearReferenceUtil = new LinearReferenceUtil(pipeline);
        }
        return linearReferenceUtil;
    }

    public void setLinearReferenceUtil(LinearReferenceUtil linearReferenceUtil) {
        this.linearReferenceUtil = linearReferenceUtil;
    }
}
