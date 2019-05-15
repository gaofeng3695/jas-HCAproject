package cn.jasgroup.hcas.highimpactarea.query.bo;

import cn.jasgroup.jasframework.base.data.CommonBaseBo;

/**
 * @description 高后果区
 * @author zhangyi
 * @date 2019年1月15日下午3:14:04
 * @version V1.0
 * @since JDK 1.80
 */

public class HcaHighImpactAreaBo extends CommonBaseBo{

	/**
	 * 数据ID 
	 */
	private String oid;
	
	/**
	 * 项目ID 
	 */
	private String projectOid;
	
	/**
	 * 项目name
	 */
	private String projectName; 

	/**
	 * 管线ID 
	 */
	private String pipelineOid;
	
	/**
	 * 管线name 
	 */
	private String pipelineName; 

	/**
	 * 地区等级区域ID 
	 */
	private String areaOid;
	
	/**
	 * 地区等级区域name
	 */
	private String areaName; 

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
	 * 高后果区等级name
	 */
	private String highImpactLevelName; 

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
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getAreaOid() {
		return areaOid;
	}

	public void setAreaOid(String areaOid) {
		this.areaOid = areaOid;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getHighImpactAreaCode() {
		return highImpactAreaCode;
	}

	public void setHighImpactAreaCode(String highImpactAreaCode) {
		this.highImpactAreaCode = highImpactAreaCode;
	}

	public String getHighImpactAreaName() {
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

	public String getHighImpactLevelName() {
		return highImpactLevelName;
	}

	public void setHighImpactLevelName(String highImpactLevelName) {
		this.highImpactLevelName = highImpactLevelName;
	}

	public String getHighImpactAreaDescription() {
		return highImpactAreaDescription;
	}

	public void setHighImpactAreaDescription(String highImpactAreaDescription) {
		this.highImpactAreaDescription = highImpactAreaDescription;
	}

	public Double getStartMileage() {
		return startMileage;
	}

	public void setStartMileage(Double startMileage) {
		this.startMileage = startMileage;
	}

	public Double getEndMileage() {
		return endMileage;
	}

	public void setEndMileage(Double endMileage) {
		this.endMileage = endMileage;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Integer getIsHistory() {
		return isHistory;
	}

	public void setIsHistory(Integer isHistory) {
		this.isHistory = isHistory;
	}

	public String getGeoState() {
		return geoState;
	}

	public void setGeoState(String geoState) {
		this.geoState = geoState;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	} 

}