package cn.jasgroup.hcas.controller;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.SimpleResult;
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
import cn.jasgroup.hcas.analysis.IBuildingsAutoRecognizeService;
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

    @Resource
    private IBuildingsAutoRecognizeService groundFeatureRecognizedService ;

    /**
     *
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(value = "area/grade")
    @ResponseBody
    public BaseResult doAreaGradeAnalysis(@RequestBody Map<String ,Object> params) {
        SimpleResult result = new SimpleResult();
        String pipelineOid = MapUtil.getString(params,"pipesegmentKeyValue");
        Double buffer = MapUtil.getDouble(params,"bufferDistance",200d);
        if(StringUtil.isBlank(pipelineOid) ) {
            result.setStatus(0);
            result.setMsg("参数错误，需要管线id！");
            return result;
        }
        try {
            HcaAnalysisResult hcaAnalysisResult = areaGradeAnalysisService.executeAnalysis(pipelineOid,buffer);
            result.setData(hcaAnalysisResult);
        }catch (Exception e){
            e.printStackTrace();
            result.setStatus(0);
            result.setMsg(e.getMessage());
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
     * 执行高后果区分析
     * @param areaVersionId
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/high/{areaVersionId}/doHcaAnalysis")
    @ResponseBody
    public BaseResult doHcaAnalysis(@PathVariable String areaVersionId) {
        SimpleResult result = new SimpleResult();
        if(StringUtil.isBlank(areaVersionId)){
            result.setMsg("缺少地区等级版本参数areaVersionId");
            result.setStatus(0);
            return result;
        }
        try {
            HcaAnalysisResult hcaAnalysisResult = highImpactAnalysisService.executeAnalysis(areaVersionId);
            result.setData(hcaAnalysisResult);
        }catch (Exception e){
            e.printStackTrace();
            result.setStatus(0);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     *执行高后果区分析
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(value = "high")
    @ResponseBody
    public BaseResult doHcaAnalysis2(@RequestBody Map<String ,Object> params) {
        String areaVersionId = MapUtil.getString(params,"areaVersionId");
        return doHcaAnalysis(areaVersionId);
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
        try {
            Polyline polyline = (Polyline) collection.getFeatures().get(0).getGeometry();
            Geometry area = geometryService.buffer(polyline, bufferDistance);
            baseResult.setData(JSONObject.parse(area.toGeoJSON()));
        }catch (Exception e){
            e.printStackTrace();
            baseResult.setData(e.getMessage());
            baseResult.setStatus(0);
        }
        return baseResult ;
    }

    /**
     * 将自动识别地物的成果物数据导入到空间表hca_buildings_auto中
     * @param params
     * @return
     */
    @PostMapping(value = "buildings/auto/shape/import")
    @ResponseBody
    public BaseResult importShapefileDataToDatabase(@RequestBody Map<String ,Object> params){
        String folderPath = MapUtil.getString(params ,"folderPath","E:\\mapdata\\hca\\pipe-wv2") ;
        String tableName = MapUtil.getString(params,"tableName","hca_buildings")  ;
        int count  = 0;
        SimpleResult simpleResult = new SimpleResult() ;
        try {
            StringUtil.time("读取shape数据并导入");
            count = groundFeatureRecognizedService.importToDB(folderPath ,tableName);
            StringUtil.timeEnd("读取shape数据并导入");
            simpleResult.setData(count);
        } catch (InterruptedException e) {
            e.printStackTrace();
            simpleResult.setStatus(0);
            simpleResult.setMsg(e.getMessage());
        }
        return simpleResult;
    }

    /**
     * 绑定管线数据
     * @param params
     * @return
     */
    @PostMapping(value = "buildings/auto/pipedata/attach")
    @ResponseBody
    public BaseResult attachPipelineData(@RequestBody Map<String ,Object> params){
        SimpleResult simpleResult = new SimpleResult() ;
        String pipelineOid = MapUtil.getString( params ,"pipelineOid" ) ;
        if(StringUtil.isBlank(pipelineOid)){
            simpleResult.setMsg("参数缺少管线OID");
            simpleResult.setStatus(0);
            return  simpleResult;
        }
        try{
            int result = groundFeatureRecognizedService.attachPipelineData(pipelineOid);
            simpleResult.setData( result);
        }catch (Exception e){
            e.printStackTrace();
            simpleResult.setMsg(e.getMessage());
            simpleResult.setStatus(0);
        }
        return simpleResult;
    }
    /**
     * 同步到构筑物表
     * @return
     */
    @GetMapping(value = "buildings/auto/sync")
    @ResponseBody
    public BaseResult syncBuildingsData(){
        SimpleResult simpleResult = new SimpleResult() ;
        try {
            int result = groundFeatureRecognizedService.syncBuildingsData();
            simpleResult.setData(result);
        }catch (Exception e){
            e.printStackTrace();
            simpleResult.setMsg(e.getMessage());
            simpleResult.setStatus(0);
        }
        return simpleResult;
    }

}
