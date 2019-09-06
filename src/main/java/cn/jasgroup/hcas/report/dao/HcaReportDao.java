package cn.jasgroup.hcas.report.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;

import cn.jasgroup.jasframework.engine.jdbc.dao.CommonDataJdbcDao;

/**
 * @description 报告dao
 * @author zhangyi
 * @date 2019年9月5日下午5:37:59
 * @version V1.0
 * @since JDK 1.80
 */

@Repository
public class HcaReportDao extends CommonDataJdbcDao{
	
	 
	 /**
	  *<p>功能描述：获取图片路径。</p>
	  * <p> 张毅 </p>	
	  * @param oid	高后果区oid
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2019年9月5日 下午6:44:23。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getFilePathList(String oid){
		
		String sql = "select file_url from SYS_ATTACHMENT sa \n" +
				"inner  JOIN SYS_ATTACHMENTBUSINESSRELATION sbf \n" +
				"on sbf.attachment_id=sa.oid and sa.file_type='png' or sa.file_type='jpg'\n" +
				"inner JOIN HCA_HIGH_IMPACT_AREA_REF hf\n" +
				"on hf.building_oid=sbf.business_data_id\n" +
				"where hca_high_impact_area_oid=:oid";
		
		return super.queryForList(sql, ImmutableMap.of("oid", oid));
	}

}
