package cn.jasgroup.hcas.analysis;

/**
 * Created by kc on 2019/5/29.
 */
public class HcaParam {
    /**
     *
     */
    private String versionId;
    /**
     *
     */
    private String pipelineOid ;
    /**
     * 管道压强
     */
    private Double pressure;
    /**
     * 管径
     */
    private Double pipeDiameter ;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getPipelineOid() {
        return pipelineOid;
    }

    public void setPipelineOid(String pipelineOid) {
        this.pipelineOid = pipelineOid;
    }

    public Double getPipeDiameter() {
        return pipeDiameter;
    }

    public void setPipeDiameter(Double pipeDiameter) {
        this.pipeDiameter = pipeDiameter;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }
}
