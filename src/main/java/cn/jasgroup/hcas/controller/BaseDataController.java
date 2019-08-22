package cn.jasgroup.hcas.controller;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.SimpleResult;
import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.data.FeatureCollection;
import cn.jasgroup.gis.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.dataaccess.LayerQueryParam;
import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.geometryservice.AreaAndLength;
import cn.jasgroup.gis.geometryservice.IGeometryService;
import cn.jasgroup.gis.util.FeatureCollectionUtil;
import cn.jasgroup.gis.util.MapUtil;
import cn.jasgroup.gis.util.MathUtil;
import cn.jasgroup.hcas.analysis.HcaAnalysisContext;
import cn.jasgroup.hcas.analysis.IAreaGradeAnalysisService;
import cn.jasgroup.hcas.analysis.IHighImpactAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by kc on 2019/5/27.
 */
@Controller
@RequestMapping("/basedata")
public class BaseDataController {

    @Resource
    JdbcTemplate jdbcTemplate;
    /**
     *
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *<p>查询所有的阈值数据。</p>
     *<p>kongchao</p>
     * @return
     * @throws Exception
     */
    @PostMapping(value = "domain/all")
    @ResponseBody
    public BaseResult getAllDomainData() throws Exception {
        String sql = "select OID,DOMAIN_NAME as DOMAINNAME,CODE_ID as CODEID,CODE_NAME as CODENAME from sys_domain where active = 1";
        List<Map<String,Object>> data = jdbcTemplate.queryForList(sql);
        SimpleResult result = new SimpleResult<>();
        result.setData(data);
        return result;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @PostMapping(value = "unique/check")
    @ResponseBody
    public BaseResult checkUniqueValue(@RequestBody UniqueCheckBO bo) throws Exception {
        String sql = "select count(1) from " + bo.getTableName() + " where " + bo.getFieldName() + "= ? ";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{ bo.getFieldValue() },Integer.class);
        SimpleResult result = new SimpleResult<>();
        result.setData(count);
        return result;
    }

}
