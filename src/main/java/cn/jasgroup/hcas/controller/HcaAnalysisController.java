package cn.jasgroup.hcas.controller;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.SimpleResult;
import cn.jasgroup.gis.util.StringUtil;
import cn.jasgroup.hcas.analysis.HcaAnalysisResult;
import cn.jasgroup.hcas.analysis.IAreaGradeAnalysisService;
import cn.jasgroup.gis.util.MapUtil;
import cn.jasgroup.hcas.analysis.IHighImpactAnalysisService;
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
     * @param versionCode
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



}
