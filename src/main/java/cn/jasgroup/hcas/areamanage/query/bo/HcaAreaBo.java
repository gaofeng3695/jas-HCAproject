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
	 * 管线ID 
	 */
	private String pipelineOid; 
	
	/**
	 * 管线name 
	 */
	private String pipelineName; 

	/**
	 * 地区编号 
	 */
	private String areaCode; 

	/**
	 * 地区等级 
	 */
	private String regionLevel;
	
	/**
	 * 地区等级name 
	 */
	private String regionLevelName; 

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
	private Double areaLength;
	/**
	 * 人口数量
	 */
	private Integer population;
	/**
	 * 描述
	 */
	private String description; 

	/**
	 * 空间数据状态 
	 */
	private String shapeState; 

	/**
	 * 备注 
	 */
	private String remarks;
	
	/**
	 * 版本Oid 
	 */
	private String versionOid;
	/**
	 * 版本名称 
	 */
	private String versionName;
	


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

	public String getAreaCode() {
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

	public Double getAreaLength() {
		return areaLength;
	}

	public void setAreaLength(Double areaLength) {
		this.areaLength = areaLength;
	}

	public String getShapeState() {
		return shapeState;
	}

	public void setShapeState(String shapeState) {
		this.shapeState = shapeState;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRegionLevelName() {
		return regionLevelName;
	}

	public void setRegionLevelName(String regionLevelName) {
		this.regionLevelName = regionLevelName;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPopulation() {
		return population;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}

	public String getVersionOid() {
		return versionOid;
	}

	public void setVersionOid(String versionOid) {
		this.versionOid = versionOid;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
}
