package cn.jasgroup.hcas.analysis.service;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.geometryservice.AreaAndLength;
import cn.jasgroup.gis.geometryservice.arcgis.GeometryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/8/29
 * @since JDK 1.80
 */
@Service
public class GeometryCommonService {

    /**
     *
     */
    @Resource
    private GeometryService geometryService;

    /**
     * 根据面积比值更新属性值
     * @param features
     * @param fieldName
     * @param peakValue
     */
    public void resetAttributeValueByAreaRadio(Feature[] features ,String fieldName ,Integer peakValue){
        double maxArea = 0d;
        Geometry[] geometries = new Geometry[features.length];
        for(int i = 0 ; i < features.length ;i++){
            Geometry geom = features[i].getGeometry();
            geometries[i] = geom;
        }
        List<AreaAndLength> areas = geometryService.areasAndLengths(geometries);
        for(int i = 0 ; i < areas.size() ;i++){
            maxArea = Math.max( maxArea,areas.get(i).getArea() );
        }
        for(int i = 0 ; i < areas.size() ; i++){
            int po = (int) ( areas.get(i).getArea() / maxArea * peakValue);
            features[i].getAttributes().put(fieldName,po) ;
        }
    }
}
