package cn.jasgroup.hcas.highimpactarea.query;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.jasgroup.hcas.highimpactarea.query.bo.HcaHighImpactAreaBo;
import cn.jasgroup.jasframework.base.annotation.QueryConfig;
import cn.jasgroup.jasframework.base.data.BaseJavaQuery;

/**
 * @description 高后果区query
 * @author zhangyi
 * @date 2019年1月15日下午3:39:46
 * @version V1.0
 * @since JDK 1.80
 */

@QueryConfig(scene = "/hcahighimpactarea/getPage", resultClass = HcaHighImpactAreaBo.class)
public class HcaHighImpactAreaQuery extends BaseJavaQuery {
	
	/**
	 * 数据oid集合
	 */
	private List<String> oids;
	/**
	 * 项目oid
	 */
	private String projectOid;
	/**
	 * 管线oid
	 */
	private String pipelineOid;
	/**
	 * 地区等级区域oid
	 */
	private String areaOid;
	/**
	 * 高后果区编号
	 */
	private String highImpactAreaCode;
	/**
	 * 高后果区名称
	 */
	private String highImpactAreaName;
	/**
	 * 高后果区等级
	 */
	private String highImpactLevel;

	@Override
	public String getQuerySql() {
		String sql = "select t.oid,t.project_oid,pro.project_name,t.pipeline_oid,pip.pipeline_name,t.area_oid,\n"
				+ "a.area_code,t.high_impact_area_code,t.high_impact_area_name,t.high_impact_level,\n"
				+ "d01.code_name as high_impact_level_name,t.high_impact_area_description,\n"
				+ "t.start_mileage,t.end_mileage,t.length,t.remarks,\n"
				+ "t.create_datetime,t.create_user_id,t.create_user_name,t.modify_datetime,t.modify_user_id,t.modify_user_name\n"
				+ " from hca_high_impact_area t\n"
				+ " LEFT JOIN (select oid,pipeline_name from hca_pipeline where active=1) pip\n"
				+ " on t.pipeline_oid = pip.oid\n"
				+ " LEFT JOIN (select oid,project_name,enterprise_id from hca_project where active=1) pro\n"
				+ " on t.project_oid = pro.oid\n"
				+ " LEFT JOIN (select oid,area_code from hca_area where active=1) a\n"
				+ " on t.area_oid = a.oid\n"
				+ " LEFT JOIN (select code_id, code_name from sys_domain where active=1 and domain_name='high_impact_level_domain' order by ordinal) d01\n"
				+ " on d01.code_id=t.high_impact_level\n"
				+ " where t.active=1 and t.is_history=0 ";
		if (StringUtils.isNotBlank(projectOid)) {
			sql += " and t.project_oid = :projectOid ";
		}
		if (StringUtils.isNotBlank(pipelineOid)) {
			sql += " and t.pipeline_oid = :pipelineOid ";
		}
		if (StringUtils.isNotBlank(areaOid)) {
			sql += " and t.area_oid = :areaOid ";
		}
		if (StringUtils.isNotBlank(highImpactAreaCode)) {
			sql += " and t.high_impact_area_code like :highImpactAreaCode ";
		}
		if (StringUtils.isNotBlank(highImpactAreaName)) {
			sql += " and t.high_impact_area_name like :highImpactAreaName ";
		}
		if (StringUtils.isNotBlank(highImpactLevel)) {
			sql += " and t.high_impact_type = :highImpactLevel ";
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

	public String getPipelineOid() {
		return pipelineOid;
	}

	public void setPipelineOid(String pipelineOid) {
		this.pipelineOid = pipelineOid;
	}

	public String getAreaOid() {
		return areaOid;
	}

	public void setAreaOid(String areaOid) {
		this.areaOid = areaOid;
	}

	public String getHighImpactAreaCode() {
		if (StringUtils.isNotBlank(highImpactAreaCode)) {
			return '%' + highImpactAreaCode + '%';
		}
		return highImpactAreaCode;
	}

	public void setHighImpactAreaCode(String highImpactAreaCode) {
		this.highImpactAreaCode = highImpactAreaCode;
	}

	public String getHighImpactAreaName() {
		if (StringUtils.isNotBlank(highImpactAreaName)) {
			return '%' + highImpactAreaName + '%';
		}
		return highImpactAreaName;
	}

	public void setHighImpactAreaName(String highImpactAreaName) {
		this.highImpactAreaName = highImpactAreaName;
	}

	public String getHighImpactLevel() {
		return highImpactLevel;
	}

	public void setHighImpactLevel(String highImpactLevel) {
		this.highImpactLevel = highImpactLevel;
	}

}
