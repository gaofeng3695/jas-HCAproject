package cn.jasgroup.hcas.controller;

/**
 * Created by jas on 2019/4/1.
 */
public class MileageResetBO {
    private String sourceName ;
    private String projectOid ;
    private String startMileageName = "start_mileage";
    private String endMileageName  = "end_mileage";
    private String geomFieldName = "geom";
    private Double preStartMileageValue = -1d ;
    private Double newStartMileageValue ;
    private Double newEndMileageValue ;
    private Double oldStartMileageValue ;
    private Double oldEndMileageValue ;
    private Double sufEndMileageValue = -1d ;

    private Double bufferDistance= 200d;

    private String pipeSourceName = "";
    private String pipeKeyName = "eventid";
    private String pipeKeyValue = "";


    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getProjectOid() {
        return projectOid;
    }

    public void setProjectOid(String projectOid) {
        this.projectOid = projectOid;
    }

    public String getStartMileageName() {
        return startMileageName;
    }

    public void setStartMileageName(String startMileageName) {
        this.startMileageName = startMileageName;
    }

    public String getEndMileageName() {
        return endMileageName;
    }

    public void setEndMileageName(String endMileageName) {
        this.endMileageName = endMileageName;
    }

    public Double getNewStartMileageValue() {
        return newStartMileageValue;
    }

    public void setNewStartMileageValue(Double newStartMileageValue) {
        this.newStartMileageValue = newStartMileageValue;
    }

    public Double getNewEndMileageValue() {
        return newEndMileageValue;
    }

    public void setNewEndMileageValue(Double newEndMileageValue) {
        this.newEndMileageValue = newEndMileageValue;
    }

    public Double getOldStartMileageValue() {
        return oldStartMileageValue;
    }

    public void setOldStartMileageValue(Double oldStartMileageValue) {
        this.oldStartMileageValue = oldStartMileageValue;
    }

    public Double getOldEndMileageValue() {
        return oldEndMileageValue;
    }

    public void setOldEndMileageValue(Double oldEndMileageValue) {
        this.oldEndMileageValue = oldEndMileageValue;
    }

    public Double getPreStartMileageValue() {
        return preStartMileageValue;
    }

    public void setPreStartMileageValue(Double preStartMileageValue) {
        this.preStartMileageValue = preStartMileageValue;
    }

    public Double getSufEndMileageValue() {
        return sufEndMileageValue;
    }

    public void setSufEndMileageValue(Double sufEndMileageValue) {
        this.sufEndMileageValue = sufEndMileageValue;
    }

    public String getGeomFieldName() {
        return geomFieldName;
    }

    public void setGeomFieldName(String geomFieldName) {
        this.geomFieldName = geomFieldName;
    }

    public String getPipeSourceName() {
        return pipeSourceName;
    }

    public void setPipeSourceName(String pipeSourceName) {
        this.pipeSourceName = pipeSourceName;
    }

    public String getPipeKeyName() {
        return pipeKeyName;
    }

    public void setPipeKeyName(String pipeKeyName) {
        this.pipeKeyName = pipeKeyName;
    }

    public String getPipeKeyValue() {
        return pipeKeyValue;
    }

    public void setPipeKeyValue(String pipeKeyValue) {
        this.pipeKeyValue = pipeKeyValue;
    }

    public Double getBufferDistance() {
        return bufferDistance;
    }

    public void setBufferDistance(Double bufferDistance) {
        this.bufferDistance = bufferDistance;
    }
}
