package cn.jasgroup.hcas.controller;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.SimpleResult;
import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.data.FeatureCollection;
import cn.jasgroup.gis.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.dataaccess.LayerQueryParam;
import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.geometry.Polyline;
import cn.jasgroup.gis.geometryservice.IGeometryService;
import cn.jasgroup.gis.util.StringUtil;
import cn.jasgroup.hcas.analysis.HcaAnalysisResult;
import cn.jasgroup.hcas.analysis.IAreaGradeAnalysisService;
import cn.jasgroup.gis.util.MapUtil;
import cn.jasgroup.hcas.analysis.IHighImpactAnalysisService;
import com.alibaba.fastjson.JSONObject;
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
@RequestMapping("/analysis")
public class HcaAnalysisController {

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
    private IGeodataAccessService geodataAccessService ;
    /**
     *
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(value = "area/grade")
    @ResponseBody
    public BaseResult doAreaGradeAnalysis(@RequestBody Map<String ,Object> params) throws Exception {
        SimpleResult result = new SimpleResult();
        String pipelineOid = MapUtil.getString(params,"pipesegmentKeyValue");
        Double buffer = MapUtil.getDouble(params,"bufferDistance",200d);
        if(StringUtil.isBlank(pipelineOid) ) {
            result.setStatus(0);
            result.setMsg("参数错误，需要管线id！");
        }else{
            HcaAnalysisResult hcaAnalysisResult = areaGradeAnalysisService.executeAnalysis(pipelineOid,buffer);
            result.setData(hcaAnalysisResult);
        }
        return result;
    }

    /**
     *
     * @param source
     * @return
     * @throws Exception
     */
    @GetMapping(value = "{source}/clear")
    @ResponseBody
    public BaseResult doClearAreaGradeAnalysis( @PathVariable String source) throws Exception {
        int count = namedParameterJdbcTemplate.update("delete from " + source , (Map<String, ?>) null);
        SimpleResult result = new SimpleResult();
        result.setData(count);
        return result;
    }

    /**
     *
     * @param areaVersionId
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/high/{areaVersionId}/doHcaAnalysis")
    @ResponseBody
    public BaseResult doHcaAnalysis(@PathVariable String areaVersionId) throws Exception {
        HcaAnalysisResult hcaAnalysisResult = highImpactAnalysisService.executeAnalysis(areaVersionId);
        SimpleResult result = new SimpleResult();
        result.setData(hcaAnalysisResult);
        return result;
    }
    /**
     *
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(value = "high")
    @ResponseBody
    public BaseResult doHcaAnalysis2(@RequestBody Map<String ,Object> params) throws Exception {
        String areaVersionId = MapUtil.getString(params,"areaVersionId");
        if(StringUtil.isBlank(areaVersionId)){
            throw new IllegalArgumentException("缺少地区等级版本参数areaVersionId");
        }
        HcaAnalysisResult hcaAnalysisResult = highImpactAnalysisService.executeAnalysis(areaVersionId);
        SimpleResult result = new SimpleResult();
        result.setData(hcaAnalysisResult);
        return result;
    }
    /**
     * 生成识别缓冲区
     * @param params
     * @return
     */
    @PostMapping(value = "area")
    @ResponseBody
    public BaseResult areaBuffer(@RequestBody Map<String ,Object> params){
        SimpleResult baseResult = new SimpleResult<String>();
        String pipelineSourceName = MapUtil.getString(params,"pipesegmentTableName","hca_pipeline");
        String pipelineOid = MapUtil.getString(params,"pipesegmentKeyValue");
        String pipelineKeyName = MapUtil.getString(params,"pipesegmentKeyName","oid");
        double bufferDistance = MapUtil.getDouble(params,"bufferDistance");// m
        //
        LayerQueryParam layerQueryParam = new LayerQueryParam();
        layerQueryParam.setSrsname(pipelineSourceName);
        layerQueryParam.setWhere(pipelineKeyName + " like '" + pipelineOid +"'");
        FeatureCollection collection = geodataAccessService.query(layerQueryParam);
        if(collection.getSize() < 0){
            baseResult.setStatus(0);
            baseResult.setMsg("没有查询到数据，table=" + pipelineSourceName + "," + pipelineKeyName + "=" + pipelineOid);
            return baseResult;
        }
        Polyline polyline = (Polyline) collection.getFeatures().get(0).getGeometry();
        Geometry area = geometryService.buffer(polyline,bufferDistance);
        baseResult.setData(JSONObject.parse(area.toGeoJSON()));
        baseResult.setStatus(1);
        return baseResult ;
    }


}
