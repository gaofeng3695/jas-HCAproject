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
	 * 项目ID 
	 */
	private String projectOid; 

	/**
	 * 管线ID 
	 */
	private String pipelineOid; 

	/**
	 * 地区等级区域ID 
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

	/**
	 * 高后果区描述 
	 */
	private String highImpactAreaDescription; 

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
	private Double length; 

	/**
	 * 是否历史数据 
	 */
	private Integer isHistory; 

	/**
	 * 空间数据状态 
	 */
	private String geoState; 

	/**
	 * 备注 
	 */
	private String remarks; 


	public String getProjectOid() {
		return projectOid; 
	}

	public void setProjectOid(String projectOid) {
		this.projectOid = projectOid; 
		super.setField("projectOid");
	}

	public String getPipelineOid() {
		return pipelineOid; 
	}

	public void setPipelineOid(String pipelineOid) {
		this.pipelineOid = pipelineOid; 
		super.setField("pipelineOid");
	}

	public String getAreaOid() {
		return areaOid; 
	}

	public void setAreaOid(String areaOid) {
		this.areaOid = areaOid; 
		super.setField("areaOid");
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

	public String getHighImpactAreaDescription() {
		return highImpactAreaDescription; 
	}

	public void setHighImpactAreaDescription(String highImpactAreaDescription) {
		this.highImpactAreaDescription = highImpactAreaDescription; 
		super.setField("highImpactAreaDescription");
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

	public Double getLength() {
		return length; 
	}

	public void setLength(Double length) {
		this.length = length; 
		super.setField("length");
	}

	public Integer getIsHistory() {
		return isHistory; 
	}

	public void setIsHistory(Integer isHistory) {
		this.isHistory = isHistory; 
		super.setField("isHistory");
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

}