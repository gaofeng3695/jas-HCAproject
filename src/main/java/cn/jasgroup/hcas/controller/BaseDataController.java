package cn.jasgroup.hcas.controller;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.SimpleResult;
import cn.jasgroup.jasframework.utils.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
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
        Object[] arr = new Object[]{ bo.getFieldValue() };
        if(StringUtil.hasText(bo.getOid())){
        	arr = new Object[]{ bo.getFieldValue(), bo.getOid() };
        	sql += " and oid !=? ";
        }
        Integer count = jdbcTemplate.queryForObject(sql, arr ,Integer.class);
        SimpleResult result = new SimpleResult<>();
        result.setData(count);
        return result;
    }

}
