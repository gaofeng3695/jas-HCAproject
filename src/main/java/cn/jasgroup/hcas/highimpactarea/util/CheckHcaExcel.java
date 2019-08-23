package cn.jasgroup.hcas.highimpactarea.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jasgroup.jasframework.excel.annotation.CheckExcelData;
import cn.jasgroup.jasframework.excel.util.ImportAutoCheck;

/**
 * @description 校验高后果区地区等级
 * @author zhangyi
 * @date 2019年8月19日上午9:45:36
 * @version V1.0
 * @since JDK 1.80
 */

@CheckExcelData(functionName = "hcahighinfo", tableName = "hca_high_impact_area")
public class CheckHcaExcel implements ImportAutoCheck {

	/**
	 * <p>功能描述:校验导入的数据里程范围。</p>	
	 * @param validatedData
	 * @return
	 * <p> 张毅</p>
	 * @since JDK1.8。
	 * <p>创建日期2019年8月19日 上午9:44:40。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@Override
	public Map<String, Object> autoCheckData(List<Map<String, Object>> validatedData) {
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("");
		/**
		 * 1. 起始里程与终止里程不等
		 * 2. 第二个起始里程应大于或等于上一个终止里程
		 */
		int listSize = validatedData.size();
		for (int i = 0; i < listSize; i++) {
			Map<String, Object> map = validatedData.get(i);
			Double start = (Double) map.get("start_mileage");
			Double end = (Double) map.get("end_mileage");
			if (start == end) {
				sb.append("第");
				sb.append(map.get("rowIndex"));
				sb.append("条数据有误！起始里程必须小于终止里程！</br>");
			}
			if (i != listSize - 1) {
				Map<String, Object> nextMap = validatedData.get(i + 1);
				Double nextStart = (Double) nextMap.get("start_mileage");
				if (nextStart < end) {
					sb.append("第");
					sb.append(map.get("rowIndex"));
					sb.append("条和");
					sb.append("第");
					sb.append(nextMap.get("rowIndex"));
					sb.append("条数据有误！两条数据里程范围不能交叉！</br>");
				}
			}
		}
		result.put("errorMessage", sb.toString());
		result.put("autoValidatedData", validatedData);
		return result;
	}

	@Override
	public Map<String, Object> handleDataAterImport(List<Map<String, Object>> validatedData, String successIndexs) {
		// TODO Auto-generated method stub
		return null;
	}

}
