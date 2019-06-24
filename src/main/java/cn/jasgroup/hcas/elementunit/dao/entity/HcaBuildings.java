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
 * @description 构筑物单元
 * @author zhangyi
 * @date 2019年1月15日下午2:33:56
 * @version V1.0
 * @since JDK 1.80
 */

@CommonSaveConfig(scene="/hcabuildings/save")
@CommonUpdateConfig(scene="/hcabuildings/update")
@CommonDeleteConfig(scene="/hcabuildings/delete")
@JdbcEntity(name = "hca_buildings")
public class HcaBuildings extends CommonJdbcEntity {


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
	 * 构筑物类型 
	 */
	private String buildingType;

	/**
	 * 建筑分布 
	 */
	private String buildingDistribution;

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

	public String getBuildingDistribution() {
		return buildingDistribution;
	}

	public void setBuildingDistribution(String buildingDistribution) {
		this.buildingDistribution = buildingDistribution;
		super.setField("buildingDistribution");
	}

	public Double getHouseholds() {
		return households;
	}

	public void setHouseholds(Double households) {
		this.households = households;
		super.setField("households");
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

	public String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		super.setField("buildingCode");
		this.buildingCode = buildingCode;
	}

	public String getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(String buildingType) {
		super.setField("buildingType");
		this.buildingType = buildingType;
	}

	public Double getBeds() {
		return beds;
	}

	public void setBeds(Double beds) {
		super.setField("beds");
		this.beds = beds;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		super.setField("address");
		this.address = address;
	}

	public Double getCenterx() {
		return centerx;
	}

	public void setCenterx(Double centerx) {
		super.setField("centerx");
		this.centerx = centerx;
	}

	public Double getCentery() {
		return centery;
	}

	public void setCentery(Double centery) {
		super.setField("centery");
		this.centery = centery;
	}

	
}
