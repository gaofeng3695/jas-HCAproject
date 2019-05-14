package cn.jasgroup.hcas.controller;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.util.GeometryUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by kc on 2019/3/4.
 */
public class FeatureCollectionBO {

    private String f = "json" ;

    private String type = "FeatureCollection";

    private List<Map<String,Object>> features ;

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public List<Map<String, Object>> getFeatures() {
        return features;
    }

    public void setFeatures(List<Map<String, Object>> features) {
        this.features = features;
    }

    public Feature[] getFeatureArray (){
        int featureSize = features.size() ;
        Feature[] fArray = new Feature[featureSize] ;
        for(int i = 0 ; i < featureSize ;i++ ){
            Map<String,Object> fMap = features.get(i);
            Map<String,Object> properties = (Map<String, Object>) fMap.get("properties");
            Map<String,Object> geometry = (Map<String, Object>) fMap.get("geometry");
            Feature feature = new Feature() ;
            feature.setAttributes(properties);
            feature.setGeometry(GeometryUtil.fromGeoJson(geometry));
            fArray[i] = feature;
        }
        return  fArray;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
