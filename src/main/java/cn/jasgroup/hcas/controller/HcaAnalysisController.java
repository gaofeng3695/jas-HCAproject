package cn.jasgroup.hcas.controller;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.SimpleResult;
import cn.jasgroup.hcas.analysis.AnalysisResult;
import cn.jasgroup.hcas.analysis.IAnalysisService;
import cn.jasgroup.gis.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    protected IAnalysisService analysisService ;

    @PostMapping(value = "area/grade")
    @ResponseBody
    public BaseResult doAreaGradeAnalysis(@RequestBody Map<String ,Object> params) throws Exception {
        String pipelineOid = MapUtil.getString(params,"pipesegmentKeyValue");
        Double buffer = MapUtil.getDouble(params,"bufferDistance",200d);
        AnalysisResult analysisResult = analysisService.executeAreaGradeAnalysis(pipelineOid,buffer);
        SimpleResult result = new SimpleResult();
        result.setData(analysisResult);
        return result;
    }
    @PostMapping(value = "hca")
    @ResponseBody
    public BaseResult doHcaAnalysis(@RequestBody Map<String ,Object> params) throws Exception {

        return null;
    }

}
