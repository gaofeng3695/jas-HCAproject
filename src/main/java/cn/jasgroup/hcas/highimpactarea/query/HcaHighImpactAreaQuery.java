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
	 * oid
	 */
	private String oid;
	/**
	 * 数据oid集合
	 */
	private List<String> oids;
	/**
	 * 管线oid
	 */
	private String pipelineOid;
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
	/**
	 * 版本Oid
	 */
	private String versionOid;
	
	@Override
	public String getQuerySql() {
		String sql = "select t.oid,t.pipeline_oid,pip.pipeline_name,t.version_oid,v.version_name,\n"
				+ "t.high_impact_area_code,t.high_impact_area_name,t.high_impact_level,\n"
				+ "d01.code_name as high_impact_level_name,t.description,\n"
				+ "t.start_mileage,t.end_mileage,t.hca_length,t.remarks,\n"
				+ "t.create_datetime,t.create_user_id,t.create_user_name,t.modify_datetime,t.modify_user_id,t.modify_user_name\n"
				+ " from hca_high_impact_area t\n"
				+ " LEFT JOIN (select oid,pipeline_name from hca_pipeline where active=1) pip\n"
				+ " on t.pipeline_oid = pip.oid\n"
				+ " LEFT JOIN (select code_id, code_name from sys_domain where active=1 and domain_name='high_impact_level_domain' order by ordinal) d01\n"
				+ " on d01.code_id=t.high_impact_level\n"
				+ " LEFT JOIN (select oid,version_name from hca_version where active=1) v on v.oid=t.version_oid\n"
				+ " where t.active=1";
		if (StringUtils.isNotBlank(oid)) {
			sql += " and t.oid = :oid ";
		}else {
			if (StringUtils.isNotBlank(pipelineOid)) {
				sql += " and t.pipeline_oid = :pipelineOid ";
			}
			if (StringUtils.isNotBlank(highImpactAreaCode)) {
				sql += " and t.high_impact_area_code like :highImpactAreaCode ";
			}
			if (StringUtils.isNotBlank(highImpactAreaName)) {
				sql += " and t.high_impact_area_name like :highImpactAreaName ";
			}
			if (StringUtils.isNotBlank(highImpactLevel)) {
				sql += " and t.high_impact_level = :highImpactLevel ";
			}
			if (null != oids && oids.size() > 0) {
				sql += " and t.oid in (:oids) ";
			}
			if (StringUtils.isNotBlank(versionOid)) {
				sql += " and t.version_oid = :versionOid ";
			}
			sql += " order by t.start_mileage asc";
		}
		return sql;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public List<String> getOids() {
		return oids;
	}

	public void setOids(List<String> oids) {
		this.oids = oids;
	}

	public String getPipelineOid() {
		return pipelineOid;
	}

	public void setPipelineOid(String pipelineOid) {
		this.pipelineOid = pipelineOid;
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

	public String getVersionOid() {
		return versionOid;
	}

	public void setVersionOid(String versionOid) {
		this.versionOid = versionOid;
	}

}
