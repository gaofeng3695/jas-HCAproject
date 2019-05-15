package cn.jasgroup.hcas.areamanage.dao.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

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
	 * 项目ID 
	 */
	private String projectOid; 

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
	private String regionaLevel; 

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
	private Integer isHistory = 0; 

	/**
	 * 空间数据状态 
	 */
	private String geoState; 

	/**
	 * 备注 
	 */
	private String remarks; 
	
	private List<String> unitIdList = new ArrayList<>();


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

	public String getAreaCode() {
		return areaCode; 
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode; 
		super.setField("areaCode");
	}

	public String getRegionaLevel() {
		return regionaLevel; 
	}

	public void setRegionaLevel(String regionaLevel) {
		this.regionaLevel = regionaLevel; 
		super.setField("regionaLevel");
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

	public Double getLength() {
		return length; 
	}

	public void setLength(Double length) {
		this.length = length; 
		super.setField("length");
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

	@Transient
	public List<String> getUnitIdList() {
		return unitIdList;
	}

	public void setUnitIdList(List<String> unitIdList) {
		this.unitIdList = unitIdList;
	}

}
