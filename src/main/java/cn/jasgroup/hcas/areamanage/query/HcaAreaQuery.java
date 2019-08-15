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
	 * oid
	 */
	private String oid;
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

	/**
	 * 版本Oid
	 */
	private String versionOid;

	@Override
	public String getQuerySql() {
		String sql = "select t.*,pip.pipeline_name,d01.code_name as region_level_name "
				+ " from hca_area t "
				+ " LEFT JOIN (select oid,pipeline_name from hca_pipeline where active=1) pip on t.pipeline_oid = pip.oid "
				+ " LEFT JOIN (select code_id, code_name from sys_domain where active=1 and domain_name='region_level_domain') d01 on d01.code_id=t.region_level "
				+ " where t.active=1 ";
		if (StringUtils.isNotBlank(oid)) {
			sql += " and t.oid = :oid ";
		}else{
			if (StringUtils.isNotBlank(pipelineOid)) {
				sql += " and t.pipeline_oid = :pipelineOid ";
			}
			if (StringUtils.isNotBlank(areaCode)) {
				sql += " and t.area_code like :areaCode ";
			}
			if (StringUtils.isNotBlank(regionLevel)) {
				sql += " and t.region_level = :regionLevel ";
			}
			if (StringUtils.isNotBlank(versionOid)) {
				sql += " and t.version_oid = :versionOid ";
			}
		}
		sql += " order by t.start_mileage asc";
		return sql;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
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

	public String getVersionOid() {
		return versionOid;
	}

	public void setVersionOid(String versionOid) {
		this.versionOid = versionOid;
	}
}
