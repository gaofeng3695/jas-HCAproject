package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.geometry.Point;
import cn.jasgroup.gis.util.GeometryUtil;

import java.util.*;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/6/28
 * @since JDK 1.80
 */
public class BuildingLocationMap {

    private Map<String,Feature[]> map = new HashMap<>();
    /**
     *
     * @param oid
     * @return
     */
    public Feature[] get(String oid){
        if(contains(oid))
            return map.get(oid);
        return null;
    }

    /**
     *
     * @param oid
     * @param points
     */
    public void put(String oid,Feature[] points){
        map.put(oid,points);
    }

    /**
     *
     * @param to
     * @param from
     */
    public void merge(String from,String to){
        if(!contains(from)){
            return;
        }
        if(!contains(to)){
            put(to,new Feature[0]);
        }
        Feature[] features = map.get(from);
        Feature[] features0 = map.get(to);
        int size = features.length + features0 .length ;
        Feature[] features1 = new Feature[size];
        System.arraycopy(features0, 0, features1, 0, features0.length);
        System.arraycopy(features, 0, features1, features0.length, features.length);
        put(to,features1);
    }
    /**
     *
     *
     * @param key
     * @return
     */
    public boolean contains(String key){
        return map.containsKey(key);
    }

    /**
     *
     * @param oid
     * @param buildings
     * @return
     */
    public void putFeatures(String oid ,Feature[] buildings){
        put(oid,buildings);
    }

    public int size(String oid){
        if(map.containsKey(oid)){
            return get(oid).length;
        }
        return -1 ;
    }

    public Map<String, Feature[]> getMap() {
        return map;
    }

    public void setMap(Map<String, Feature[]> map) {
        this.map = map;
    }
}
