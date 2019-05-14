package cn.jasgroup.hcas.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableMap;

import cn.jasgroup.jasframework.engine.jdbc.dao.CommonDataJdbcDao;

/**
 * @description 公共dao
 * @author zhangyi
 * @date 2019年1月16日下午3:38:09
 * @version V1.0
 * @since JDK 1.80
 */

@Repository
public class HcaCommonDao extends CommonDataJdbcDao{

	 
	 /**
	  *<p>功能描述：获取当前用户部门下的项目下拉选列表。</p>
	  * <p> 张毅 </p>	
	  * @param unitId
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2019年1月16日 下午5:06:06。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getProjectList(String unitId){
		
		String sql = "select oid as key, project_name as value from hca_project where active=1 and enterprise_id=:enterpriseId";
		
		return super.queryForList(sql, ImmutableMap.of("enterpriseId", unitId));
	}
	
	 /**
	  *<p>功能描述：获取项目下的管线下拉选列表。</p>
	  * <p> 张毅 </p>	
	  * @param projectOid
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2019年1月16日 下午5:06:01。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPipelineList(String projectOid){
		
		String sql = "select oid as key, pipeline_name as value from hca_pipeline where active=1 and project_oid=:projectOid";
		
		return super.queryForList(sql, ImmutableMap.of("projectOid", projectOid));
	}
}
