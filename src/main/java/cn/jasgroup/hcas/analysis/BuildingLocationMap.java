package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.geometry.Point;
import cn.jasgroup.gis.util.GeometryUtil;
import cn.jasgroup.gis.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/6/28
 * @since JDK 1.80
 */
public class BuildingLocationMap {

    protected  Logger logger = LoggerFactory.getLogger(getClass());


    private Map<String,Map<String,Feature>> map = new HashMap<>();
    /**
     *
     * @param oid
     * @return
     */
    public Feature[] get(String oid){
        if(contains(oid)){
            Map featureMap = map.get(oid);
            return (Feature[]) featureMap.values().toArray(new Feature[0]);
        }
        return null;
    }

    /**
     *
     * @param oid1
     * @param oid2
     * @return
     */
    public Feature[] getSameFeatures(String oid1,String oid2){
        if(!contains(oid1) || !contains(oid2)){
            return new Feature[0] ;
        }
        List<Feature> list = new ArrayList<>() ;
        Map<String,Feature> featureMap = map.get(oid1);
        Map<String,Feature> featureMap2 = map.get(oid2);
        Iterator<String> iterator = featureMap.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator .next() ;
            if(featureMap2.containsKey(key)){
                list.add(featureMap.get(key));
            }
        }
        return list.toArray(new Feature[0]) ;
    }
    /**
     *
     * @param oid
     * @param features
     */
    public void put(String oid,Feature[] features){
        if(!map.containsKey(oid)){
            map.put(oid,new HashMap<String,Feature>());
        }
        Map<String,Feature> featureMap = map.get(oid);
        for(int i = 0 ; i < features.length;i++){
            String buildingOid = MapUtil.getString(features[i].getAttributes() ,HcaAnalysisContext.TableKeyName);
            if(featureMap.containsKey(buildingOid)){
                logger.warn("识别单元{}已经包含构筑物{},原有构筑物将被覆盖",oid ,buildingOid);
            }
            featureMap.put(buildingOid,features[i]);
        }
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
        Map<String,Feature> features = map.get(from);
        Map<String,Feature> features0 = map.get(to);
        Iterator<String> iterator = features.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            features0.put(key,features.get(key));
        }
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

    public Map<String, Map<String,Feature>> getMap() {
        return map;
    }

    public void setMap(Map<String, Map<String,Feature>> map) {
        this.map = map;
    }
}
