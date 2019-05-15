package cn.jasgroup.hcas.versionmaanage.query.bo;

import cn.jasgroup.jasframework.base.data.CommonBaseBo;

/**
 * @description 版本管理Bo
 * @author zhangyi
 * @date 2019年4月9日上午10:52:45
 * @version V1.0
 * @since JDK 1.80
 */

public class HcaVersionBo extends CommonBaseBo{
	
	/**
	 * 主键oid 
	 */
	private String oid;
	
	/**
	 * 管线id
	 */
	private String pipelineOid;
	
	/**
	 * 管线名称
	 */
	private String pipelineName;

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


	/**
	 * 是否启用（ 0 否 1 是）
	 */
	private Integer hasUsed; 
	
	/**
	 * 备注
	 */
	private String remarks;

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionCode() {
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

	public Integer getHasUsed() {
		return hasUsed;
	}

	public void setHasUsed(Integer hasUsed) {
		this.hasUsed = hasUsed;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPipelineOid() {
		return pipelineOid;
	}

	public void setPipelineOid(String pipelineOid) {
		this.pipelineOid = pipelineOid;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
}
