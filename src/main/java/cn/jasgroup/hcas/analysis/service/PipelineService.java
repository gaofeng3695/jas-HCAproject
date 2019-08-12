package cn.jasgroup.hcas.analysis.service;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.data.FeatureCollection;
import cn.jasgroup.gis.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.dataaccess.LayerQueryParam;
import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.geometry.Polyline;
import cn.jasgroup.gis.geometryservice.IGeometryService;
import cn.jasgroup.gis.util.GeometryUtil;
import cn.jasgroup.gis.util.LoggerUtil;
import cn.jasgroup.hcas.analysis.HcaAnalysisContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.DecimalFormat;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/6/26
 * @since JDK 1.80
 */
@Service
@Transactional
public class PipelineService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected LoggerUtil loggerUtil = new LoggerUtil(logger) ;

    @Resource
    private IGeodataAccessService geodataAccessService;

    @Resource
    private IGeometryService geometryService;

    /**
     * 查询管线信息
     * @param key
     * @param sourceName
     * @return
     * @throws Exception
     */
    protected Feature queryTargetPipelineData(String key , String sourceName)  {
        LayerQueryParam param = new LayerQueryParam();
        param.setSrsname(sourceName);
        param.setOutputFormat(null);//返回对象 或传入WKT
        param.setWhere(HcaAnalysisContext.TableKeyName +"='"+ key + "'");
        param.setOutFields("*");
        FeatureCollection collection = geodataAccessService.query(param);
        if(collection.getFeatures().size() > 0){
            return  (Feature)collection.getFeatures().get(0);
        }
        return null;
    }
    /**
     *
     * @param polyline
     * @return
     */
    protected Double queryPipelineMileage(Polyline polyline) {
        Geometry[] geometries = new Geometry[1];
        geometries[0] = polyline ;
        Double[] lengths = geometryService.lengths(geometries);
        DecimalFormat f = new DecimalFormat("#.######");
        return Double.valueOf(f.format(lengths[0]/1000d)) ;

    }

}
