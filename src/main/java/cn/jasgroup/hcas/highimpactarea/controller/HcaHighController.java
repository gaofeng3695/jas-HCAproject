package cn.jasgroup.hcas.highimpactarea.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jasgroup.hcas.highimpactarea.query.HcaHighImpactAreaQuery;
import cn.jasgroup.hcas.highimpactarea.query.bo.HcaHighImpactAreaBo;
import cn.jasgroup.hcas.highimpactarea.service.HcaHighService;
import cn.jasgroup.jasframework.base.controller.BaseController;
import cn.jasgroup.jasframework.engine.jdbc.service.CommonDataJdbcService;
import cn.jasgroup.jasframework.excel.util.ExcelExportUtil;
import cn.jasgroup.jasframework.utils.DateTimeUtil;

/**
 * @description 高后果区
 * @author zhangyi
 * @date 2019年8月14日下午8:18:33
 * @version V1.0
 * @since JDK 1.80
 */

@RestController
@RequestMapping("hcahighimpactarea")
public class HcaHighController extends BaseController {

	@Autowired
	private CommonDataJdbcService commonDataJdbcService;
	
	@Autowired
	private HcaHighService hcaHighService;

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
	public String exportToExcelAction(HttpServletRequest request, HttpServletResponse response, HcaHighImpactAreaQuery query) {
		// 列属性名称
		String propertyName = "pipelineOid,versionOid,highImpactAreaCode,highImpactAreaName,highImpactLevel,"
//				+ "startMileage,endMileage,hcaLength,description,shape";
		+ "startMileage,endMileage,hcaLength,description";
		List<String> propertyList = new ArrayList<String>();
		if (propertyName != null) {
			propertyList = Arrays.asList(propertyName.split(","));
		}
		// 列属性描述
//		String propertyDes = "管线名称,识别名称,高后果区编号,高后果区名称,高后果区等级,起始里程,终止里程,高后果区长度,描述,空间坐标";
		String propertyDes = "管线名称,识别名称,高后果区编号,高后果区名称,高后果区等级,起始里程,终止里程,高后果区长度,描述";
		List<String> propertyDesList = new ArrayList<String>();
		if (propertyName != null) {
			propertyDesList = Arrays.asList(propertyDes.split(","));
		}
		List<HcaHighImpactAreaBo> list = (List<HcaHighImpactAreaBo>) this.commonDataJdbcService.getList(query);
//		List<Map<String, String>> geometryList = this.hcaHighService.queryGeometryList(query);
		// format导出数据的格式，确保数据的导出的正确性
		List<Map<String, String>> map = new ArrayList<Map<String, String>>();
		if(null != list && list.size() > 0 ){
			int listSize = list.size();
			for (int i=0; i<listSize; i++) {
				HcaHighImpactAreaBo bo = list.get(i);
				/*String shapeText = "";
				if(geometryList.size()>0){
					shapeText = geometryList.get(i).get("shape");
				}*/
				Map<String, Object> ms = bo.getValueMap();
//				ms.put("shape", "");
				Set<String> key = ms.keySet();
				Map<String, String> mss = new HashMap<>();
				for (Iterator it = key.iterator(); it.hasNext();) {
					String s = (String) it.next();
					Object valueObject = null;
					switch (s) {
					case "pipelineOid":
						valueObject = ms.get("pipelineName");
						break;
					case "versionOid":
						valueObject = ms.get("versionName");
						break;
					case "highImpactLevel":
						valueObject = ms.get("highImpactLevelName");
						break;
					default:
						valueObject = ms.get(s);
						break;
					}
					String valueString = "";
					// 如果为日期
					if (valueObject instanceof Date) {
						valueString = DateTimeUtil.getFormatDate((Date) valueObject, DateTimeUtil.DATE_FORMAT);
					} else if (valueObject != null) {
						valueString = String.valueOf(valueObject);
					}
					/*if("shape".equals(s)){
						valueString = shapeText;
					}*/
					mss.put(s, valueString);
				}
				map.add(mss);
			}
		}
		// 调用导出工具类导出数据
		String[] typeArr = { "高后果区识别信息", "高后果区识别信息" }; // {标题名,sheet名}
		// 第一个参数表名为非空字符串,则进行模板查询,若有模板利用模板导出,没有则自动生成Excel导出;参数为空,则自动生成Excel导出
		new ExcelExportUtil().exportWithTemplate("hca_high_impact_area", "hcahighinfo", "高后果区识别信息.xls", map, propertyList,
				propertyDesList, request, response, typeArr);
		return null;
	}
}
