package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.geometry.Point;
import cn.jasgroup.gis.util.GeometryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/6/28
 * @since JDK 1.80
 */
public class BuildingLocationMap {
    /**
     *
     */
    private Map<String,Point[]> map = new HashMap();

    /**
     *
     * @param oid
     * @return
     */
    public Point[] get(String oid){
        if(contains(oid))
            return map.get(oid);
        return null;
    }

    /**
     *
     * @param oid
     * @param points
     */
    public void put(String oid,Point[] points){
        map.put(oid,points);
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
    public int putFeatures(String oid ,Feature[] buildings){
        List<Point> points = new ArrayList<>();
        for(int i = 0 ; i < buildings.length ; i++){
            Point p = (Point) GeometryUtil.toGeometry(buildings[i].getGeometry());
            if(p != null){
                points.add(p);
            }
        }
        if(points.size() > 0){
            Point[] arr = points.toArray(new Point[0]);
            put(oid ,arr);
        }
        return points.size();
    }
}
