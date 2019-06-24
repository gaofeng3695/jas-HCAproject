package cn.jasgroup.hcas.areamanage.query;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.jasgroup.hcas.areamanage.query.bo.HcaAreaBo;
import cn.jasgroup.jasframework.base.annotation.QueryConfig;
import cn.jasgroup.jasframework.base.data.BaseJavaQuery;

/**
 * @description 地区等级区域query
 * @author zhangyi
 * @date 2019年1月15日下午3:16:14
 * @version V1.0
 * @since JDK 1.80
 */

@QueryConfig(scene = "/hcaarea/getPage", resultClass = HcaAreaBo.class)
public class HcaAreaQuery extends BaseJavaQuery {

	/**
	 * 数据oid集合
	 */
	private List<String> oids;
	/**
	 * 管线oid
	 */
	private String pipelineOid;
	/**
	 * 地区编号
	 */
	private String areaCode;
	/**
	 * 地区等级
	 */
	private String regionLevel;
	
	
	@Override
	public String getQuerySql() {
		String sql = "select t.oid,t.pipeline_oid,pip.pipeline_name,\n"
				+ "t.area_code,t.region_level,d01.code_name as region_level_name,\n"
				+ "t.start_mileage,t.end_mileage,t.area_length,t.remarks,t.description,\n"
				+ "t.create_datetime,t.create_user_id,t.create_user_name,t.modify_datetime,t.modify_user_id,t.modify_user_name\n"
				+ " from hca_area t\n"
				+ " LEFT JOIN (select oid,pipeline_name from hca_pipeline where active=1) pip\n"
				+ " on t.pipeline_oid = pip.oid\n"
				+ " LEFT JOIN (select code_id, code_name from sys_domain where active=1 and domain_name='region_level_domain' order by ordinal) d01\n"
				+ " on d01.code_id=t.region_level\n"
				+ " where t.active=1 ";
		if (StringUtils.isNotBlank(pipelineOid)) {
			sql += " and t.pipeline_oid = :pipelineOid ";
		}
		if (StringUtils.isNotBlank(areaCode)) {
			sql += " and t.area_code like :areaCode ";
		}
		if (StringUtils.isNotBlank(regionLevel)) {
			sql += " and t.region_level = :regionLevel ";
		}
		if (null != oids && oids.size() > 0) {
			sql += " and t.oid in (:oids) ";
		}
		sql += " order by t.start_mileage asc";
		return sql;
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

	public String getAreaCode() {
		if (StringUtils.isNotBlank(areaCode)) {
			return '%' + areaCode + '%';
		}
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getRegionLevel() {
		return regionLevel;
	}

	public void setRegionLevel(String regionLevel) {
		this.regionLevel = regionLevel;
	}

	
}
