package cn.jasgroup.hcas.controller;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.SimpleResult;
import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.data.FeatureCollection;
import cn.jasgroup.gis.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.dataaccess.LayerQueryParam;
import cn.jasgroup.gis.geometryservice.IGeometryService;
import cn.jasgroup.gis.util.*;
import cn.jasgroup.hcas.analysis.HcaAnalysisContext;
import cn.jasgroup.hcas.analysis.IAreaGradeAnalysisService;
import cn.jasgroup.hcas.analysis.IHighImpactAnalysisService;
import cn.jasgroup.hcas.analysis.service.GeometryCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by kc on 2019/5/27.
 */
@Controller
@RequestMapping("/datatools")
public class HcaDataToolsController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    protected IAreaGradeAnalysisService areaGradeAnalysisService  ;

    @Resource
    protected IHighImpactAnalysisService highImpactAnalysisService  ;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    private IGeometryService geometryService ;

    @Resource
    private GeometryCommonService geometryCommonService ;

    @Resource
    private IGeodataAccessService geodataAccessService ;

    /**
     * 更新构筑物人口数据，地区等级划分前需要根据人口划分。
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(value = "buildings/population/reset")
    @ResponseBody
    public BaseResult resetBuildingsPopulation(@RequestBody Map<String ,Object> params) throws Exception {
        SimpleResult result = new SimpleResult();
        Integer max = MapUtil.getInt(params,"max");
        String srs = MapUtil.getString(params,"sourceName","hca_buildings");
        String where = MapUtil.getString(params,"where"," SHAPE is not null and " + HcaAnalysisContext.buildingTypeFieldName + " like '" + HcaAnalysisContext.buildingTypeValue_Settlement + "'" );
        String outFields = MapUtil.getString(params,"outFields","OID,POPULATION,OBJECTID");
        LayerQueryParam layerQueryParam = new LayerQueryParam();
        layerQueryParam.setSrsname(srs);
        layerQueryParam.setWhere(where);
        layerQueryParam.setOutFields(outFields);
        FeatureCollection collection = geodataAccessService.query(layerQueryParam);
        if(collection==null){
            logger.error("查询构筑物表出错，请检查参数是否正确！");
        }
        Feature[] data = FeatureCollectionUtil.toLowerCaseFeature(collection);
        if(data.length == 0){
            logger.warn("没有查询到构筑物数据");
        }
        //HcaAnalysisContext.buildingsSourcePopulationFieldName
        geometryCommonService.resetAttributeValueByAreaRadio(data,HcaAnalysisContext.buildingsSourcePopulationFieldName,max);

        logger.info("开始更新数据...");
        int[] re = geodataAccessService.updateFeatures(HcaAnalysisContext.buildingsSourceName,data );
        logger.info("保存成功！");

        result.setData(MathUtil.sum(re));
        return result;
    }

    /**
     * 更新自动识别构筑物人口数据
     * @param params
     * @return
     */
    @PostMapping(value = "buildings_auto/population/reset")
    @ResponseBody
    public BaseResult resetBuildingsAutoPopulation(@RequestBody Map<String ,Object> params){
        SimpleResult result = new SimpleResult();
        Integer max = MapUtil.getInt(params,"max");
        String srs = MapUtil.getString(params,"sourceName","hca_buildings_auto");
        String where = MapUtil.getString(params,"where"," SHAPE is not null   "  );
        String outFields = MapUtil.getString(params,"outFields","OID,POPULATION,OBJECTID");
        LayerQueryParam layerQueryParam = new LayerQueryParam();
        layerQueryParam.setSrsname(srs);
        layerQueryParam.setWhere(where);
        layerQueryParam.setOutFields(outFields);
        FeatureCollection collection = geodataAccessService.query(layerQueryParam);
        if(collection==null){
            logger.error("查询构筑物auto表出错，请检查参数是否正确！");
        }
        Feature[] data = FeatureCollectionUtil.toLowerCaseFeature(collection);
        if(data.length == 0){
            logger.warn("没有查询到构筑物auto数据");
        }
        geometryCommonService.resetAttributeValueByAreaRadio(data,HcaAnalysisContext.buildingsSourcePopulationFieldName,max);

        logger.info("开始更新数据...");
        int[] re = geodataAccessService.updateFeatures("hca_buildings_auto",data );
        logger.info("保存成功！");

        result.setData(MathUtil.sum(re));
        return result;
    }



}
