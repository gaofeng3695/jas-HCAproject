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
	 * 管线ID 
	 */
	private String pipelineOid;
	
	/**
	 * 管线name 
	 */
	private String pipelineName;

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

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Double getHcaLength() {
		return hcaLength;
	}

	public void setHcaLength(Double hcaLength) {
		this.hcaLength = hcaLength;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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