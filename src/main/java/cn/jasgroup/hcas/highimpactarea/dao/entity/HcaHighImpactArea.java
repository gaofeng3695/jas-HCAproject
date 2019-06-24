package cn.jasgroup.hcas.highimpactarea.dao.entity;

import cn.jasgroup.jasframework.base.annotation.CommonDeleteConfig;
import cn.jasgroup.jasframework.base.annotation.CommonSaveConfig;
import cn.jasgroup.jasframework.base.annotation.CommonUpdateConfig;
import cn.jasgroup.jasframework.base.annotation.JdbcEntity;
import cn.jasgroup.jasframework.engine.jdbc.entity.CommonJdbcEntity;

/**
 * @description 高后果区
 * @author zhangyi
 * @date 2019年1月15日下午3:14:04
 * @version V1.0
 * @since JDK 1.80
 */

@CommonSaveConfig(scene="/hca_high_impact_area/save")
@CommonUpdateConfig(scene="/hca_high_impact_area/update")
@CommonDeleteConfig(scene="/hca_high_impact_area/delete")
@JdbcEntity(name="hca_high_impact_area")
public class HcaHighImpactArea extends CommonJdbcEntity {

	/**
	 * 管线ID 
	 */
	private String pipelineOid; 
	
	/**
	 * 版本ID 
	 */
	private String versionOid; 

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
	 * 高后果区描述 
	 */
	private String description; 

	/**
	 * 起始里程 
	 */
	private Double startMileage; 

	/**
	 * 终止里程 
	 */
	private Double endMileage; 

	/**
	 * 长度 
	 */
	private Double hcaLength; 

	/**
	 * 空间数据状态 
	 */
	private String geoState; 

	/**
	 * 备注 
	 */
	private String remarks; 


	public String getPipelineOid() {
		return pipelineOid; 
	}

	public void setPipelineOid(String pipelineOid) {
		this.pipelineOid = pipelineOid; 
		super.setField("pipelineOid");
	}

	public String getHighImpactAreaCode() {
		return highImpactAreaCode; 
	}

	public void setHighImpactAreaCode(String highImpactAreaCode) {
		this.highImpactAreaCode = highImpactAreaCode; 
		super.setField("highImpactAreaCode");
	}

	public String getHighImpactAreaName() {
		return highImpactAreaName; 
	}

	public void setHighImpactAreaName(String highImpactAreaName) {
		this.highImpactAreaName = highImpactAreaName; 
		super.setField("highImpactAreaName");
	}

	public String getHighImpactLevel() {
		return highImpactLevel; 
	}

	public void setHighImpactLevel(String highImpactLevel) {
		this.highImpactLevel = highImpactLevel; 
		super.setField("highImpactLevel");
	}

	public String getDescription() {
		return description; 
	}

	public void setHighImpactAreaDescription(String description) {
		this.description = description; 
		super.setField("description");
	}

	public Double getStartMileage() {
		return startMileage; 
	}

	public void setStartMileage(Double startMileage) {
		this.startMileage = startMileage; 
		super.setField("startMileage");
	}

	public Double getEndMileage() {
		return endMileage; 
	}

	public void setEndMileage(Double endMileage) {
		this.endMileage = endMileage; 
		super.setField("endMileage");
	}

	public Double getHcaLength() {
		return hcaLength; 
	}

	public void setHcaLength(Double hcaLength) {
		this.hcaLength = hcaLength; 
		super.setField("hcaLength");
	}

	public String getGeoState() {
		return geoState; 
	}

	public void setGeoState(String geoState) {
		this.geoState = geoState; 
		super.setField("geoState");
	}

	public String getRemarks() {
		return remarks; 
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks; 
		super.setField("remarks");
	}

	public String getVersionOid() {
		return versionOid;
	}

	public void setVersionOid(String versionOid) {
		this.versionOid = versionOid;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}