package cn.jasgroup.hcas.elementunit.query.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.jasgroup.jasframework.base.data.CommonBaseBo;

/**
 * @description 要素单元
 * @author zhangyi
 * @date 2019年1月15日下午2:33:56
 * @version V1.0
 * @since JDK 1.80
 */

public class HcaElementUnitBo extends CommonBaseBo {

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

	/**
	 * 要素编号 
	 */
	private String elementCode;

	/**
	 * 起始里程 
	 */
	private Double startMileage;

	/**
	 * 终止里程 
	 */
	private Double endMileage;

	/**
	 * 水平距离 
	 */
	private Double horizontalDistance;

	/**
	 * 垂直距离 
	 */
	private Double verticalDistance;

	/**
	 * 起始坐标X 
	 */
	private Double pointx;

	/**
	 * 起始坐标Y 
	 */
	private Double pointy;

	/**
	 * 要素类型 
	 */
	private String elementType;

	/**
	 * 要素类型name
	 */
	private String elementTypeName;

	/**
	 * 建筑分布 
	 */
	private String buildingDistribution;

	/**
	 * 建筑分布name
	 */
	private String buildingDistributionName;

	/**
	 * 户数 
	 */
	private Double numberOfHouseholds;

	/**
	 * 人口 
	 */
	private Double population;

	/**
	 * 采集时间 
	 */
	private Date collectDate;

	/**
	 * 采集人员 
	 */
	private String collectPerson;

	/**
	 * 备注 
	 */
	private String remarks;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getProjectOid() {
		return projectOid;
	}

	public void setProjectOid(String projectOid) {
		this.projectOid = projectOid;
	}

	public String getPipelineOid() {
		return pipelineOid;
	}

	public void setPipelineOid(String pipelineOid) {
		this.pipelineOid = pipelineOid;
	}

	public String getElementCode() {
		return elementCode;
	}

	public void setElementCode(String elementCode) {
		this.elementCode = elementCode;
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

	public Double getHorizontalDistance() {
		return horizontalDistance;
	}

	public void setHorizontalDistance(Double horizontalDistance) {
		this.horizontalDistance = horizontalDistance;
	}

	public Double getVerticalDistance() {
		return verticalDistance;
	}

	public void setVerticalDistance(Double verticalDistance) {
		this.verticalDistance = verticalDistance;
	}

	public Double getPointx() {
		return pointx;
	}

	public void setPointx(Double pointx) {
		this.pointx = pointx;
	}

	public Double getPointy() {
		return pointy;
	}

	public void setPointy(Double pointy) {
		this.pointy = pointy;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public String getElementTypeName() {
		return elementTypeName;
	}

	public void setElementTypeName(String elementTypeName) {
		this.elementTypeName = elementTypeName;
	}

	public String getBuildingDistribution() {
		return buildingDistribution;
	}

	public void setBuildingDistribution(String buildingDistribution) {
		this.buildingDistribution = buildingDistribution;
	}

	public String getBuildingDistributionName() {
		return buildingDistributionName;
	}

	public void setBuildingDistributionName(String buildingDistributionName) {
		this.buildingDistributionName = buildingDistributionName;
	}

	public Double getNumberOfHouseholds() {
		return numberOfHouseholds;
	}

	public void setNumberOfHouseholds(Double numberOfHouseholds) {
		this.numberOfHouseholds = numberOfHouseholds;
	}

	public Double getPopulation() {
		return population;
	}

	public void setPopulation(Double population) {
		this.population = population;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	public String getCollectPerson() {
		return collectPerson;
	}

	public void setCollectPerson(String collectPerson) {
		this.collectPerson = collectPerson;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

}
