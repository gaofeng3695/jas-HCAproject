package cn.jasgroup.hcas.areamanage.query.bo;

import cn.jasgroup.jasframework.base.data.CommonBaseBo;

/**
 * @description 地区等级区域Bo
 * @author zhangyi
 * @date 2019年1月15日下午3:14:04
 * @version V1.0
 * @since JDK 1.80
 */

public class HcaAreaBo extends CommonBaseBo {
	
	/**
	 * 数据oid 
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

//	/**
//	 * 识别单元ID 
//	 */
//	private String identificationUnitOid; 

	/**
	 * 地区编号 
	 */
	private String areaCode; 

	/**
	 * 地区等级 
	 */
	private String regionaLevel;
	
	/**
	 * 地区等级name 
	 */
	private String regionaLevelName; 

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

//	public String getIdentificationUnitOid() {
//		return identificationUnitOid;
//	}
//
//	public void setIdentificationUnitOid(String identificationUnitOid) {
//		this.identificationUnitOid = identificationUnitOid;
//	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getRegionaLevel() {
		return regionaLevel;
	}

	public void setRegionaLevel(String regionaLevel) {
		this.regionaLevel = regionaLevel;
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

	public String getRegionaLevelName() {
		return regionaLevelName;
	}

	public void setRegionaLevelName(String regionaLevelName) {
		this.regionaLevelName = regionaLevelName;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	} 

}
