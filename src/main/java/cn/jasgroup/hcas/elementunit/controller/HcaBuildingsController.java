package cn.jasgroup.hcas.elementunit.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jasgroup.jasframework.domain.service.SysDomainService;
import cn.jasgroup.jasframework.domain.service.bo.SysDomainBo;
import cn.jasgroup.jasframework.security.service.bo.UnitBo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jasgroup.hcas.elementunit.query.HcaBuildingsQuery;
import cn.jasgroup.hcas.elementunit.query.bo.HcaBuildingsBo;
import cn.jasgroup.jasframework.base.controller.BaseController;
import cn.jasgroup.jasframework.engine.jdbc.service.CommonDataJdbcService;
import cn.jasgroup.jasframework.excel.util.ExcelExportUtil;
import cn.jasgroup.jasframework.utils.DateTimeUtil;
import cn.jasgroup.jasframework.domain.utils.DomainUtil;

/**
 * @description 建构筑物
 * @author zhangyi
 * @date 2019年8月14日下午8:05:35
 * @version V1.0
 * @since JDK 1.80
 */

@RestController
@RequestMapping("hcabuildings")
public class HcaBuildingsController extends BaseController {

	@Autowired
	private CommonDataJdbcService commonDataJdbcService;
    @Autowired
    private SysDomainService sysDomainService;
	/**
	 *<p>功能描述：导出网格选中的全部数据为excel格式文件。</p>
	 * <p> 张毅 </p>	
	 * @param request
	 * @param response
	 * @param query	查询策略
	 * @return
	 * @since JDK1.8。
	 * <p>创建日期:2019年8月14日 下午7:39:16。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportToExcelAction")
	public String exportToExcelAction(HttpServletRequest request, HttpServletResponse response, HcaBuildingsQuery query) {
		// 列属性名称
		String propertyName = "buildingCode,buildingType,buildingDistribution,households,population,address,collectDate,remarks";
		List<String> propertyList = new ArrayList<String>();
		if (propertyName != null) {
			propertyList = Arrays.asList(propertyName.split(","));
		}
		// 列属性描述
		String propertyDes = "建（构）筑物编号,建（构）筑物类型,建筑分布,户数,人口,地址,采集时间,备注";
		List<String> propertyDesList = new ArrayList<String>();
		if (propertyName != null) {
			propertyDesList = Arrays.asList(propertyDes.split(","));
		}
		List<HcaBuildingsBo> list = (List<HcaBuildingsBo>) this.commonDataJdbcService.getList(query);
		// format导出数据的格式，确保数据的导出的正确性
		List<Map<String, String>> map = new ArrayList<Map<String, String>>();
		for (HcaBuildingsBo bo : list) {
			Map<String, Object> ms = bo.getValueMap();
			Set<String> key = ms.keySet();
			Map<String, String> mss = new HashMap<>();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				Object valueObject = ms.get(s);
				if("buildingType".equals(s)){
					valueObject = ms.get("buildingTypeName");
				}
				if("buildingDistribution".equals(s)){
					valueObject = ms.get("buildingDistributionName");
				}
				String valueString = "";
				// 如果为日期
				if (valueObject instanceof Date) {
					valueString = DateTimeUtil.getFormatDate((Date) valueObject, DateTimeUtil.DATE_FORMAT);
				} else if (valueObject != null) {
					valueString = String.valueOf(valueObject);
				}
				// 转换值域
				mss.put(s, valueString);
			}
			map.add(mss);
		}
		// 调用导出工具类导出数据
		String[] typeArr = { "建（构）筑物导入模板", "建（构）筑物导入模板" }; // {标题名,sheet名}
		// 第一个参数表名为非空字符串,则进行模板查询,若有模板利用模板导出,没有则自动生成Excel导出;参数为空,则自动生成Excel导出
		new ExcelExportUtil().exportWithTemplate("hca_buildings", "建构筑物", "建（构）筑物导入模板.xlsx", map, propertyList,
				propertyDesList, request, response, typeArr);
		return null;
	}
}
