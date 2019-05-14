package cn.jasgroup.hcas.elementunit.dao.entity;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.jasgroup.jasframework.base.annotation.CommonDeleteConfig;
import cn.jasgroup.jasframework.base.annotation.CommonSaveConfig;
import cn.jasgroup.jasframework.base.annotation.CommonUpdateConfig;
import cn.jasgroup.jasframework.base.annotation.JdbcEntity;
import cn.jasgroup.jasframework.engine.jdbc.entity.CommonJdbcEntity;

/**
 * @description 要素单元
 * @author zhangyi
 * @date 2019年1月15日下午2:33:56
 * @version V1.0
 * @since JDK 1.80
 */

@CommonSaveConfig(scene="/hcaelementunit/save")
@CommonUpdateConfig(scene="/hcaelementunit/update")
@CommonDeleteConfig(scene="/hcaelementunit/delete")
@JdbcEntity(name = "hca_element_unit")
public class HcaElementUnit extends CommonJdbcEntity {

	/**
	 * 项目ID
	 */
	private String projectOid;

	/**
	 * 管线ID
	 */
	private String pipelineOid;

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
	 * 建筑分布 
	 */
	private String buildingDistribution;

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
	 * 是否历史数据 
	 */
	private Integer isHistory = 0;

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

	public String getElementCode() {
		return elementCode;
	}

	public void setElementCode(String elementCode) {
		this.elementCode = elementCode;
		super.setField("elementCode");
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

	public Double getHorizontalDistance() {
		return horizontalDistance;
	}

	public void setHorizontalDistance(Double horizontalDistance) {
		this.horizontalDistance = horizontalDistance;
		super.setField("horizontalDistance");
	}

	public Double getVerticalDistance() {
		return verticalDistance;
	}

	public void setVerticalDistance(Double verticalDistance) {
		this.verticalDistance = verticalDistance;
		super.setField("verticalDistance");
	}

	public Double getPointx() {
		return pointx;
	}

	public void setPointx(Double pointx) {
		this.pointx = pointx;
		super.setField("pointx");
	}

	public Double getPointy() {
		return pointy;
	}

	public void setPointy(Double pointy) {
		this.pointy = pointy;
		super.setField("pointy");
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
		super.setField("elementType");
	}

	public String getBuildingDistribution() {
		return buildingDistribution;
	}

	public void setBuildingDistribution(String buildingDistribution) {
		this.buildingDistribution = buildingDistribution;
		super.setField("buildingDistribution");
	}

	public Double getNumberOfHouseholds() {
		return numberOfHouseholds;
	}

	public void setNumberOfHouseholds(Double numberOfHouseholds) {
		this.numberOfHouseholds = numberOfHouseholds;
		super.setField("numberOfHouseholds");
	}

	public Double getPopulation() {
		return population;
	}

	public void setPopulation(Double population) {
		this.population = population;
		super.setField("population");
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@Temporal(TemporalType.DATE)
	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
		super.setField("collectDate");
	}

	public String getCollectPerson() {
		return collectPerson;
	}

	public void setCollectPerson(String collectPerson) {
		this.collectPerson = collectPerson;
		super.setField("collectPerson");
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
