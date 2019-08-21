package cn.jasgroup.hcas.areamanage.dao.entity;

import javax.persistence.Column;

import cn.jasgroup.framework.spatial.annotation.Surface;
import cn.jasgroup.framework.spatial.entity.ArcGisSpatialObject;
import cn.jasgroup.jasframework.base.annotation.CommonDeleteConfig;
import cn.jasgroup.jasframework.base.annotation.JdbcEntity;

/**
 * @description 地区等级区域
 * @author zhangyi
 * @date 2019年1月15日下午3:14:04
 * @version V1.0
 * @since JDK 1.80
 */

@CommonDeleteConfig(scene="/hcaarea/delete")
@Surface(geometryColumnName = "shape")
@JdbcEntity(name="hca_area")
public class HcaArea extends ArcGisSpatialObject {


	/**
	 * 管线ID 
	 */
	private String pipelineOid;

	/**
	 * 版本ID
	 */
	private String versionOid;
	/**
	 * 地区编号 
	 */
	private String areaCode; 

	/**
	 * 地区等级 
	 */
	private String regionLevel; 

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
	 * 备注 
	 */
	private String remarks; 
	
	@Column(name="pipeline_oid")
	public String getPipelineOid() {
		return pipelineOid; 
	}

	public void setPipelineOid(String pipelineOid) {
		this.pipelineOid = pipelineOid; 
		super.setField("pipelineOid");
	}

	@Column(name="version_oid")
	public String getVersionOid() {
		return versionOid;
	}

	public void setVersionOid(String versionOid) {
		this.versionOid = versionOid;
		super.setField("versionOid");
	}

	@Column(name="area_code")
	public String getAreaCode() {
		return areaCode; 
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode; 
		super.setField("areaCode");
	}

	@Column(name="region_level")
	public String getRegionLevel() {
		return regionLevel; 
	}

	public void setRegionLevel(String regionLevel) {
		this.regionLevel = regionLevel; 
		super.setField("regionLevel");
	}

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

	@Column(name="area_length")
	public Double getAreaLength() {
		return areaLength; 
	}

	public void setAreaLength(Double areaLength) {
		this.areaLength = areaLength; 
		super.setField("areaLength");
	}

    @Column(name="population")
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
        super.setField("population");
    }

    @Column(name="remarks")
	public String getRemarks() {
		return remarks; 
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks; 
		super.setField("remarks");
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		super.setField("description");
	}

	
}
