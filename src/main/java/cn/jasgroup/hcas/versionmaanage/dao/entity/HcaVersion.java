package cn.jasgroup.hcas.versionmaanage.dao.entity;

import javax.persistence.Column;

import cn.jasgroup.jasframework.base.annotation.CommonDeleteConfig;
import cn.jasgroup.jasframework.base.annotation.CommonSaveConfig;
import cn.jasgroup.jasframework.base.annotation.CommonUpdateConfig;
import cn.jasgroup.jasframework.base.annotation.JdbcEntity;
import cn.jasgroup.jasframework.base.annotation.UniqueConstraintStrategy;
import cn.jasgroup.jasframework.base.annotation.UniqueConstraints;
import cn.jasgroup.jasframework.engine.jdbc.entity.CommonJdbcEntity;

/**
 * @description version版本记录表
 * @author zhangyi
 * @date 2019年4月9日上午10:20:57
 * @version V1.0
 * @since JDK 1.80
 */

@UniqueConstraints(
    strategys ={
        @UniqueConstraintStrategy(columnNames={"versionName"},name="版本名称"),
        @UniqueConstraintStrategy(columnNames={"versionCode"},name="版本编号")
    }
)
@CommonSaveConfig(scene="/hcaversion/save")
@CommonUpdateConfig(scene="/hcaversion/update")
@CommonDeleteConfig(scene="/hcaversion/delete")
@JdbcEntity(name="hca_version")
public class HcaVersion extends CommonJdbcEntity {
	
	/**
	 * 管线id
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


	/**
	 * 是否启用（ 0 否 1 是）
	 */
	private Integer hasUsed = 0; 
	
	/**
	 * 备注
	 */
	private String remarks; 

	@Column(name="remarks")
	public String getRemarks() {
		return remarks; 
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks; 
		super.setField("remarks");
	}

	@Column(name="version_name")
	public String getVersionName() {
		return versionName; 
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName; 
		super.setField("versionName");
	}

	@Column(name="version_code")
	public String getVersionCode() {
		return versionCode; 
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode; 
		super.setField("versionCode");
	}

	@Column(name="for_business")
	public Integer getForBusiness() {
		return forBusiness; 
	}

	public void setForBusiness(Integer forBusiness) {
		this.forBusiness = forBusiness; 
		super.setField("forBusiness");
	}

	@Column(name="has_used")
	public Integer getHasUsed() {
		return hasUsed; 
	}

	public void setHasUsed(Integer hasUsed) {
		this.hasUsed = hasUsed; 
		super.setField("hasUsed");
	}

	@Column(name="pipeline_oid")
	public String getPipelineOid() {
		return pipelineOid;
	}

	public void setPipelineOid(String pipelineOid) {
		this.pipelineOid = pipelineOid;
		super.setField("pipelineOid");
	}
	
}
