package cn.jasgroup.hcas.pipelinemanage.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jasgroup.jasframework.excel.annotation.CheckExcelData;
import cn.jasgroup.jasframework.excel.util.ImportAutoCheck;

/**
 * @description 管线导入校验
 * @author zhangyi
 * @date 2019年8月22日下午4:48:53
 * @version V1.0
 * @since JDK 1.80
 */

@CheckExcelData(functionName = "管线信息", tableName = "hca_pipeline")
public class CheckPipelineExcel implements ImportAutoCheck{

	 
	/**
	 * <p>功能描述：导入前校验：
	 * 1.唯一性校验【管线名称】【管线编号】
	 * </p>	
	 * @param validatedData
	 * @return
	 * <p> 张毅</p>
	 * @since JDK1.8。
	 * <p>创建日期2019年8月22日 下午4:50:15。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@Override
	public Map<String, Object> autoCheckData(List<Map<String, Object>> validatedData) {
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder("");

		/*int listSize = validatedData.size();
		for (int i = 0; i < listSize; i++) {
			Map<String, Object> map = validatedData.get(i);
			
			if (i != listSize - 1) {
				Map<String, Object> nextMap = validatedData.get(i + 1);
				sb.append("第");
				sb.append(map.get("rowIndex"));
				sb.append("条和");
				sb.append("第");
				sb.append(nextMap.get("rowIndex"));
				sb.append("条数据重复！</br>");
			}
		}*/
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
