package cn.jasgroup.hcas.areamanage.dao.entity;

import cn.jasgroup.jasframework.base.annotation.CommonDeleteConfig;
import cn.jasgroup.jasframework.base.annotation.CommonSaveConfig;
import cn.jasgroup.jasframework.base.annotation.CommonUpdateConfig;
import cn.jasgroup.jasframework.base.annotation.JdbcEntity;
import cn.jasgroup.jasframework.engine.jdbc.entity.CommonJdbcEntity;

/**
 * @description 地区等级区域
 * @author zhangyi
 * @date 2019年1月15日下午3:14:04
 * @version V1.0
 * @since JDK 1.80
 */

@CommonSaveConfig(scene="/hcaarea/save")
@CommonUpdateConfig(scene="/hcaarea/update")
@CommonDeleteConfig(scene="/hcaarea/delete")
@JdbcEntity(name="hca_area")
public class HcaArea extends CommonJdbcEntity {


	/**
	 * 管线ID 
	 */
	private String pipelineOid; 

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
	 * 描述
	 */
	private String description; 
	 
	/**
	 * 空间数据状态 
	 */
	private String geoState; 

	/**
	 * 备注 
	 */
	private String remarks; 
	

	public String getPipelineOid() {
		return pipelineOid; 
	}

	public void setPipelineOid(String pipelineOid) {
		this.pipelineOid = pipelineOid; 
		super.setField("pipelineOid");
	}

	public String getAreaCode() {
		return areaCode; 
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode; 
		super.setField("areaCode");
	}

	public String getRegionLevel() {
		return regionLevel; 
	}

	public void setRegionLevel(String regionLevel) {
		this.regionLevel = regionLevel; 
		super.setField("regionLevel");
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

	public Double getAreaLength() {
		return areaLength; 
	}

	public void setAreaLength(Double areaLength) {
		this.areaLength = areaLength; 
		super.setField("areaLength");
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		super.setField("description");
	}

	
}
