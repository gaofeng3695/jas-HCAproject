package cn.jasgroup.hcas.controller;

/**
 * Created by kc on 2019/3/4.
 */
public class SettlementBO extends FeatureCollectionBO {

    private String sourceName = "pd_settlement" ;

    private String pipelineSourceName = "pd_pipesegment";

    private String pipelineKeyValue = "";

    private String pipelineKeyName = "oid";

    private String geomName = "geom" ;

    public String getPipelineSourceName() {
        return pipelineSourceName;
    }

    public void setPipelineSourceName(String pipelineSourceName) {
        this.pipelineSourceName = pipelineSourceName;
    }

    public String getPipelineKeyValue() {
        return pipelineKeyValue;
    }

    public void setPipelineKeyValue(String pipelineKeyValue) {
        this.pipelineKeyValue = pipelineKeyValue;
    }

    public String getPipelineKeyName() {
        return pipelineKeyName;
    }

    public void setPipelineKeyName(String pipelineKeyName) {
        this.pipelineKeyName = pipelineKeyName;
    }

    public String getGeomName() {
        return geomName;
    }

    public void setGeomName(String geomName) {
        this.geomName = geomName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
