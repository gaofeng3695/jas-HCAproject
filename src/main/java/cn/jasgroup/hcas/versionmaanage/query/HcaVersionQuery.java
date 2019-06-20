package cn.jasgroup.hcas.versionmaanage.query;

import org.apache.commons.lang.StringUtils;

import cn.jasgroup.hcas.versionmaanage.query.bo.HcaVersionBo;
import cn.jasgroup.jasframework.base.annotation.QueryConfig;
import cn.jasgroup.jasframework.base.data.BaseJavaQuery;

/**
 * @description query
 * @author zhangyi
 * @date 2019年4月9日上午10:26:23
 * @version V1.0
 * @since JDK 1.80
 */

@QueryConfig(scene = "/hcaversion/getPage", resultClass = HcaVersionBo.class)
public class HcaVersionQuery extends BaseJavaQuery{
	/**
	 * oid
	 */
	private String oid;
	/**
	 * 管线ID
	 */
	private String pipelineOid;
	
	/**
	 * 版本名称
	 */
	private String versionName;
	
	/**
	 * 版本编号
	 */
	private String versionCode;
	
	/**
	 * （ 0 地区等级 1 高后果区）
	 */
	private Integer forBusiness;
	
	@Override
	public String getQuerySql() {
		String sql = "select t.*,p.pipeline_name from hca_version t left join hca_pipeline p "
				+ " on p.oid=t.pipeline_oid "
				+ " where t.active=1 ";
		if (StringUtils.isNotBlank(oid)) {
			sql += " and t.oid = :oid ";
		}else{
			if(StringUtils.isNotBlank(pipelineOid)){
				sql += " and t.pipeline_oid = :pipelineOid ";
			}
			if(StringUtils.isNotBlank(versionName)){
				sql += " and t.version_name like :versionName ";
			}
			if(StringUtils.isNotBlank(versionCode)){
				sql += " and t.version_code like :versionCode ";
			}
			if(null != forBusiness){
				sql += " and t.for_business = :forBusiness ";
			}
		}
		sql+= " order by t.create_datetime desc";
		return sql;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getVersionName() {
		if (StringUtils.isNotBlank(versionName)) {
			return '%' + versionName + '%';
		}
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionCode() {
		if (StringUtils.isNotBlank(versionCode)) {
			return '%' + versionCode + '%';
		}
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public Integer getForBusiness() {
		return forBusiness;
	}

	public void setForBusiness(Integer forBusiness) {
		this.forBusiness = forBusiness;
	}

	public String getPipelineOid() {
		return pipelineOid;
	}

	public void setPipelineOid(String pipelineOid) {
		this.pipelineOid = pipelineOid;
	}

}
