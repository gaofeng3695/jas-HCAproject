package cn.jasgroup.hcas.elementunit.service;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.data.FeatureCollection;
import cn.jasgroup.gis.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.dataaccess.LayerQueryParam;
import cn.jasgroup.gis.dataaccess.arcgis.GeodataAccessService;
import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.geometry.Point;
import cn.jasgroup.gis.geometry.Polygon;
import cn.jasgroup.gis.geometry.Polyline;
import cn.jasgroup.gis.util.CoordinateSystemUtil;
import cn.jasgroup.gis.util.GeometryUtil;
import cn.jasgroup.gis.util.LinearReferenceUtil;
import cn.jasgroup.gis.util.MapUtil;
import cn.jasgroup.hcas.analysis.HcaAnalysisContext;
import cn.jasgroup.hcas.elementunit.dao.entity.HcaBuildings;
import cn.jasgroup.hcas.elementunit.query.bo.HcaBuildings2;
import cn.jasgroup.jasframework.engine.jdbc.service.CommonDataJdbcService;
import cn.jasgroup.jasframework.utils.InvokeSupportUtils;
import cn.jasgroup.jasframework.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.faces.view.facelets.FaceletException;
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
public class HcaBuildingService extends CommonDataJdbcService {


    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static String geomFieldName = "shape";

    @Autowired
    private IGeodataAccessService geodataAccessService    ;
    /**
     *
     * @param hcaBuildings
     * @return
     */
    public int save( HcaBuildings2 hcaBuildings){
        int result = 0 ;
        if(StringUtil.hasText(hcaBuildings.getOid() ) && hcaBuildings.getObjectId() >0){
            result = update(hcaBuildings);
        }else{
            result = add(hcaBuildings);
        }
        return result ;
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
        Feature feature = new Feature();
        feature.setAttributes(attributes);
        if(StringUtil.hasText(geoText)){
            Geometry geometry = GeometryUtil.createPolygon(geoText);
            feature.setGeometry(geometry);
        }
        return geodataAccessService.updateFeature(sourceName,feature);
    }

    /**
     *
     * @param hcaBuildings
     * @return
     */
    public int add(HcaBuildings2 hcaBuildings){
        hcaBuildings.setActive(1);

        String geoText = hcaBuildings.getGeometry();
        if(!StringUtil.hasText(geoText)){
            throw new IllegalArgumentException("构筑物新建必须包含坐标数据！");
        }
        Polygon geometry = GeometryUtil.createPolygon(geoText);
        prepareBuildingsData(geometry,hcaBuildings);

        Map<String,Object> attributes = InvokeSupportUtils.getTableValueMap(hcaBuildings);
        String sourceName = InvokeSupportUtils.getTableName(HcaBuildings2.class);
        Feature feature = new Feature();
        feature.setAttributes(attributes);
        feature.setGeometry(geometry);

        return geodataAccessService.addFeature(sourceName,feature);
    }

    /**
     *
     * @param area
     * @param hcaBuildings
     */
    protected void prepareBuildingsData(Geometry area,HcaBuildings2 hcaBuildings){
        String pipelineOid = hcaBuildings.getPipelineOid() ;
        //$、查询管线
        LayerQueryParam param = new LayerQueryParam();
        param.setSrsname(HcaAnalysisContext.pipelineSourceName);
        param.setWhere(HcaAnalysisContext.TableKeyName + " like '" + pipelineOid + "'");
        FeatureCollection collection = geodataAccessService.query(param);
        if(collection == null || collection.getSize() == 0) {
            throw new RuntimeException("没有查询到管线数据，table="+HcaAnalysisContext.pipelineSourceName+",oid=" + pipelineOid);
        }
        Feature f = collection.getFeatures().get(0);
        Polyline pipeline = (Polyline) f.getGeometry();
        //$、创建线性参考
        LinearReferenceUtil linearReferenceUtil = new LinearReferenceUtil(pipeline);
        linearReferenceUtil.resetMeasureByLocalLength();//
        //$、计算前后里程值
        Polygon projected = (Polygon) GeometryUtil.blToGuass(area);
        Point[] points = projected.toPoints();
        double startMileage = linearReferenceUtil.getLinearPolyline().getTotalLength() ;
        double endMileage = 0d ;
        Polyline linearLine = (Polyline) linearReferenceUtil.getLinearPolyline().getPolyline();
        double verticalDistance = Double.MAX_VALUE ;
        for(int i = 0 ; i < points .length ; i++){
            Point point = points[i];
            double mileage = linearReferenceUtil.interpolatePoint(point);
            startMileage = Math.min(startMileage ,mileage);
            endMileage = Math.max(endMileage ,mileage);
            double distance = GeometryUtil.distance(point,linearLine);
            verticalDistance = Math.min(verticalDistance,distance);
        }
        double start = Double.valueOf( String.format("%.3f",startMileage / 1000));
        double end = Double.valueOf( String.format("%.3f",endMileage / 1000));
        hcaBuildings.setStartMileage(start);
        hcaBuildings.setEndMileage(end);
        hcaBuildings.setHorizontalDistance(1000 *( end - start));
        hcaBuildings.setVerticalDistance(verticalDistance);

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
