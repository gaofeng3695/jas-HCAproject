package cn.jasgroup.hcas.pipelinemanage.query;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.jasgroup.hcas.pipelinemanage.query.bo.HcaPipelineBo;
import cn.jasgroup.jasframework.base.annotation.QueryConfig;
import cn.jasgroup.jasframework.base.data.BaseJavaQuery;

/**
 * @description 管线query类
 * @author zhangyi
 * @date 2019年1月15日上午11:38:29
 * @version V1.0
 * @since JDK 1.80
 */

@QueryConfig(scene = "/hcapipeline/getPage", resultClass = HcaPipelineBo.class)
public class HcaPipelineQuery extends BaseJavaQuery {

	/**
	 * 数据oid集合
	 */
	private List<String> oids;
	/**
	 * 项目oid
	 */
	private String projectOid;
	/**
	 * 管线名称
	 */
	private String pipelineName;
	/**
	 * 管线编号
	 */
	private String pipelineCode;
	

	@Override
	public String getQuerySql() {
		String sql = "select t.oid,t.project_oid,pro.project_name,t.pipeline_name, t.pipeline_code,t.start_mileage,t.end_mileage,\n"
				+ "t.pipeline_length,t.outside_diameter,t.pressure,t.remarks,\n"
				+ "t.create_datetime,t.create_user_id,t.create_user_name,t.modify_datetime,t.modify_user_id,t.modify_user_name\n"
				+ " from hca_pipeline t\n"
				+ " LEFT JOIN (select oid,project_name,enterprise_id from hca_project where active=1) pro\n"
				+ " on t.project_oid = pro.oid\n"
				+ " where t.active=1 ";
		if (StringUtils.isNotBlank(projectOid)) {
			sql += " and t.project_oid like :projectOid ";
		}
		if (StringUtils.isNotBlank(pipelineCode)) {
			sql += " and t.pipeline_code like :pipelineCode ";
		}
		if (StringUtils.isNotBlank(pipelineName)) {
			sql += " and t.pipeline_name like :pipelineName ";
		}
		if (null != oids && oids.size() > 0) {
			sql += " and t.oid in (:oids) ";
		}
		
		sql += " order by t.create_datetime desc";
		return sql;
	}

	public List<String> getOids() {
		return oids;
	}

	public void setOids(List<String> oids) {
		this.oids = oids;
	}
	
	public String getProjectOid() {
		return projectOid;
	}

	public void setProjectOid(String projectOid) {
		this.projectOid = projectOid;
	}

	public String getPipelineName() {
		if (StringUtils.isNotBlank(pipelineName)) {
			return '%' + pipelineName + '%';
		}
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public String getPipelineCode() {
		if (StringUtils.isNotBlank(pipelineCode)) {
			return '%' + pipelineCode + '%';
		}
		return pipelineCode;
	}

	public void setPipelineCode(String pipelineCode) {
		this.pipelineCode = pipelineCode;
	}

}
