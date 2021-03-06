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

public class HcaBuildingsBo extends CommonBaseBo {

	/**
	 * 数据oid 
	 */
	private String oid;
	
	/**
	 * 构筑物编号 
	 */
	private String buildingCode;

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
	 * 构筑物类别（特定场所、非特定场所、易燃易爆场所）
	 */
	private String buildingTypeParent;
	
	/**
	 * 构筑物类别（特定场所、非特定场所、易燃易爆场所）
	 */
	private String buildingTypeParentName;
	
	/**
	 * 构筑物类型 
	 */
	private String buildingType;
	
	/**
	 * 构筑物类型 name
	 */
	private String buildingTypeName;

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
	private Double households;

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
	 * 病床数 
	 */
	private Double beds;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 中心点x
	 */
	private Double centerx;
	
	/**
	 * 中心点y
	 */
	private Double centery;

	/**
	 * 空间数据状态 
	 */
	private String geoState;

	/**
	 * 备注 
	 */
	private String remarks;
	
	/**
	 * 是否占压管道（距离<5m 0 否1是）
	 */
	private Integer pressurePipeline;


	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
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

	public String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}

	public String getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	public String getBuildingTypeName() {
		return buildingTypeName;
	}

	public void setBuildingTypeName(String buildingTypeName) {
		this.buildingTypeName = buildingTypeName;
	}

	public Double getHouseholds() {
		return households;
	}

	public void setHouseholds(Double households) {
		this.households = households;
	}

	public Double getBeds() {
		return beds;
	}

	public void setBeds(Double beds) {
		this.beds = beds;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getCenterx() {
		return centerx;
	}

	public void setCenterx(Double centerx) {
		this.centerx = centerx;
	}

	public Double getCentery() {
		return centery;
	}

	public void setCentery(Double centery) {
		this.centery = centery;
	}

	public String getGeoState() {
		return geoState;
	}

	public void setGeoState(String geoState) {
		this.geoState = geoState;
	}

	public Integer getPressurePipeline() {
		return pressurePipeline;
	}

	public void setPressurePipeline(Integer pressurePipeline) {
		this.pressurePipeline = pressurePipeline;
	}

	public String getBuildingTypeParent() {
		return buildingTypeParent;
	}

	public void setBuildingTypeParent(String buildingTypeParent) {
		this.buildingTypeParent = buildingTypeParent;
	}

	public String getBuildingTypeParentName() {
		return buildingTypeParentName;
	}

	public void setBuildingTypeParentName(String buildingTypeParentName) {
		this.buildingTypeParentName = buildingTypeParentName;
	}
	
}
