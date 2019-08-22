package cn.jasgroup.hcas.elementunit.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.jasgroup.framework.spatial.annotation.Surface;
import cn.jasgroup.framework.spatial.entity.ArcGisSpatialObject;
import cn.jasgroup.jasframework.base.annotation.CommonDeleteConfig;
import cn.jasgroup.jasframework.base.annotation.CommonSaveConfig;
import cn.jasgroup.jasframework.base.annotation.CommonUpdateConfig;
import cn.jasgroup.jasframework.base.annotation.JdbcEntity;
import cn.jasgroup.jasframework.base.annotation.UniqueConstraintStrategy;
import cn.jasgroup.jasframework.base.annotation.UniqueConstraints;

/**
 * @description 构筑物单元
 * @author zhangyi
 * @date 2019年1月15日下午2:33:56
 * @version V1.0
 * @since JDK 1.80
 */

@UniqueConstraints(
    strategys ={
        @UniqueConstraintStrategy(columnNames={"buildingCode"},name="建（构）筑物编号")
    }
)
@CommonSaveConfig(scene="/hcabuildings/save")
@CommonUpdateConfig(scene="/hcabuildings/update")
@CommonDeleteConfig(scene="/hcabuildings/delete")
@Surface(geometryColumnName = "shape")
@JdbcEntity(name = "hca_buildings")
public class HcaBuildings extends ArcGisSpatialObject {


	/**
	 * 构筑物编号 
	 */
	private String buildingCode;
	/**
	 *
	 */
	private String geometry;

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
	private String buildingCategory;

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
	 * 备注 
	 */
	private String remarks;
	
	/**
	 * 是否占压管道（距离<5m 0 否1是）
	 */
	private Integer pressurePipeline;

	@Column(name="start_mileage")
	public Double getStartMileage() {
		return startMileage;
	}

	public void setStartMileage(Double startMileage) {
		this.startMileage = startMileage;
		super.setField("startMileage");
	}

	@Column(name="end_mileage")
	public Double getEndMileage() {
		return endMileage;
	}

	public void setEndMileage(Double endMileage) {
		this.endMileage = endMileage;
		super.setField("endMileage");
	}

	@Column(name="horizontal_distance")
	public Double getHorizontalDistance() {
		return horizontalDistance;
	}

	public void setHorizontalDistance(Double horizontalDistance) {
		this.horizontalDistance = horizontalDistance;
		super.setField("horizontalDistance");
	}

	@Column(name="vertical_distance")
	public Double getVerticalDistance() {
		return verticalDistance;
	}

	public void setVerticalDistance(Double verticalDistance) {
		this.verticalDistance = verticalDistance;
		super.setField("verticalDistance");
	}

	@Column(name="pointx")
	public Double getPointx() {
		return pointx;
	}

	public void setPointx(Double pointx) {
		this.pointx = pointx;
		super.setField("pointx");
	}

	@Column(name="pointy")
	public Double getPointy() {
		return pointy;
	}

	public void setPointy(Double pointy) {
		this.pointy = pointy;
		super.setField("pointy");
	}

	@Column(name="building_distribution")
	public String getBuildingDistribution() {
		return buildingDistribution;
	}

	public void setBuildingDistribution(String buildingDistribution) {
		this.buildingDistribution = buildingDistribution;
		super.setField("buildingDistribution");
	}

	@Column(name="households")
	public Double getHouseholds() {
		return households;
	}

	public void setHouseholds(Double households) {
		this.households = households;
		super.setField("households");
	}

	@Column(name="population")
	public Double getPopulation() {
		return population;
	}

	public void setPopulation(Double population) {
		this.population = population;
		super.setField("population");
	}

	@Column(name="shape")
	public String getGeometry() {
		return geometry;
	}

	public void setGeometry(String geometry) {
		this.geometry = geometry;
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

	@Column(name="collect_person")
	public String getCollectPerson() {
		return collectPerson;
	}

	public void setCollectPerson(String collectPerson) {
		this.collectPerson = collectPerson;
		super.setField("collectPerson");
	}

	@Column(name="remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
		super.setField("remarks");
	}

	@Column(name="building_code")
	public String getBuildingCode() {
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		super.setField("buildingCode");
		this.buildingCode = buildingCode;
	}

	@Column(name="building_type")
	public String getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(String buildingType) {
		super.setField("buildingType");
		this.buildingType = buildingType;
	}

	@Column(name="beds")
	public Double getBeds() {
		return beds;
	}

	public void setBeds(Double beds) {
		super.setField("beds");
		this.beds = beds;
	}

	@Column(name="address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		super.setField("address");
		this.address = address;
	}

	@Column(name="centerx")
	public Double getCenterx() {
		return centerx;
	}

	public void setCenterx(Double centerx) {
		super.setField("centerx");
		this.centerx = centerx;
	}

	@Column(name="centery")
	public Double getCentery() {
		return centery;
	}

	public void setCentery(Double centery) {
		super.setField("centery");
		this.centery = centery;
	}

	@Column(name="pressure_pipeline")
	public Integer getPressurePipeline() {
		return pressurePipeline;
	}

	public void setPressurePipeline(Integer pressurePipeline) {
		super.setField("pressurePipeline");
		this.pressurePipeline = pressurePipeline;
	}

}
