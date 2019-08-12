package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.data.Feature;

/**
 * Created by kc on 2019/5/29.
 */
public class HcaAnalysisResult {

    private String versionId ;

    private int total ;

    private Feature[] features;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Feature[] getFeatures() {
        return features;
    }

    public void setFeatures(Feature[] features) {
        this.features = features;
    }
}
