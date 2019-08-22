package cn.jasgroup.hcas.elementunit.service;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.dataaccess.arcgis.GeodataAccessService;
import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.util.GeometryUtil;
import cn.jasgroup.gis.util.MapUtil;
import cn.jasgroup.hcas.elementunit.dao.entity.HcaBuildings;
import cn.jasgroup.hcas.elementunit.query.bo.HcaBuildings2;
import cn.jasgroup.jasframework.utils.InvokeSupportUtils;
import cn.jasgroup.jasframework.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/8/22
 * @since JDK 1.80
 */
@Service
public class HcaBuildingService {


    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static String geomFieldName = "shape";

    @Autowired
    private IGeodataAccessService geodataAccessService = new GeodataAccessService();
    /**
     *
     * @param hcaBuildings
     * @return
     */
    public int save( HcaBuildings2 hcaBuildings){
        if(StringUtil.hasText(hcaBuildings.getOid() ) && hcaBuildings.getObjectId() > -1){
            return update(hcaBuildings);
        }
        return add(hcaBuildings);
    }

    /**
     *
     * @param hcaBuildings
     * @return
     */
    public int update(HcaBuildings2 hcaBuildings){
        Map<String,Object> attributes = InvokeSupportUtils.getTableValueMap(hcaBuildings);
        String sourceName = InvokeSupportUtils.getTableName(HcaBuildings2.class);
        String geoText = MapUtil.getString(attributes,geomFieldName);
        Geometry geometry = GeometryUtil.createPolygon(geoText);
        Feature feature = new Feature();
        feature.setAttributes(attributes);
        feature.setGeometry(geometry);
        return geodataAccessService.updateFeature(sourceName,feature);
    }

    /**
     *
     * @param hcaBuildings
     * @return
     */
    public int add(HcaBuildings2 hcaBuildings){
        String oid = UUID.randomUUID().toString();
        hcaBuildings.setOid(oid);
        Map<String,Object> attributes = InvokeSupportUtils.getTableValueMap(HcaBuildings2.class);
        String sourceName = InvokeSupportUtils.getTableName(HcaBuildings2.class);
        String geoText = MapUtil.getString(attributes,geomFieldName);
        Geometry geometry = GeometryUtil.createPolygon(geoText);
        Feature feature = new Feature();
        feature.setAttributes(attributes);
        feature.setGeometry(geometry);
        return geodataAccessService.addFeature(sourceName,feature);
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public static String getGeomFieldName() {
        return geomFieldName;
    }

    public static void setGeomFieldName(String geomFieldName) {
        HcaBuildingService.geomFieldName = geomFieldName;
    }

    public IGeodataAccessService getGeodataAccessService() {
        return geodataAccessService;
    }

    public void setGeodataAccessService(IGeodataAccessService geodataAccessService) {
        this.geodataAccessService = geodataAccessService;
    }
}
