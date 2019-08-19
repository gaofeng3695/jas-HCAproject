package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.geometry.Polygon;
import cn.jasgroup.gis.geometry.Polyline;
import cn.jasgroup.gis.util.JtsUtil;
import cn.jasgroup.gis.util.LinearReferenceUtil;

/**
 *
 * Created by kc on 2019/5/27.
 */
public class HcaLinearParam {

    private String pipelineOid ;

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
    private Geometry recognitionAreaBuffer;
    /**
     * 管线外径
     */
    private Double outerDimension = 0d;
    /**
     * 管道压强
     */
    private Double pressure = 0d;
    /**
     * 潜在影响区缓冲区几何对象
     */
    private Polygon potentialInfluenceBuffer;
    /**
     *
     */
    private LinearReferenceUtil linearReferenceUtil;

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
        initLinearReference();
    }

    public Geometry getRecognitionAreaBuffer() {
        return recognitionAreaBuffer;
    }

    public void setRecognitionAreaBuffer(Geometry recognitionAreaBuffer) {
        this.recognitionAreaBuffer = recognitionAreaBuffer;
    }

    public void createRecognitionAreaBuffer(double buffer) {
        Geometry bufferArea = JtsUtil.buffer(linearReferenceUtil.getLinearPolyline().getPolyline(),buffer ,2);
        this.recognitionAreaBuffer = bufferArea;
    }
    public Polygon getPotentialInfluenceBuffer() {
        return potentialInfluenceBuffer;
    }

    public void setPotentialInfluenceBuffer(Polygon potentialInfluenceBuffer) {
        this.potentialInfluenceBuffer = potentialInfluenceBuffer;
    }

    private void initLinearReference(){
        linearReferenceUtil = new LinearReferenceUtil(pipeline);
        linearReferenceUtil.resetMeasureByLocalLength();
        this.totalMileage = linearReferenceUtil.getLinearPolyline().getTotalMeasure();
    }

    public LinearReferenceUtil getLinearReferenceUtil() {
        if(linearReferenceUtil == null){
            initLinearReference();
        }
        return linearReferenceUtil;
    }

    public void setLinearReferenceUtil(LinearReferenceUtil linearReferenceUtil) {
        this.linearReferenceUtil = linearReferenceUtil;
    }

    public Double getOuterDimension() {
        return outerDimension;
    }

    public void setOuterDimension(Double outerDimension) {
        this.outerDimension = outerDimension;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public String getPipelineOid() {
        return pipelineOid;
    }

    public void setPipelineOid(String pipelineOid) {
        this.pipelineOid = pipelineOid;
    }
}
