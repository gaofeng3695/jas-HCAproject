package cn.jasgroup.hcas.identificationunit.dao.entity;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.jasgroup.jasframework.base.annotation.JdbcEntity;
import cn.jasgroup.jasframework.engine.jdbc.entity.CommonJdbcEntity;

/**
 * @description 识别单元
 * @author zhangyi
 * @date 2019年1月15日下午4:18:07
 * @version V1.0
 * @since JDK 1.80
 */

@JdbcEntity(name="hca_identification_unit")
public class HcaIdentificationUnit extends CommonJdbcEntity {

	/**
	 * 项目ID 
	 */
	private String projectOid; 

	/**
	 * 管线ID 
	 */
	private String pipelineOid; 

	/**
	 * 识别单元编号 
	 */
	private String identificationUnitCode; 

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
	 * 要素描述 
	 */
	private String featureDescription; 

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

	public String getIdentificationUnitCode() {
		return identificationUnitCode; 
	}

	public void setIdentificationUnitCode(String identificationUnitCode) {
		this.identificationUnitCode = identificationUnitCode; 
		super.setField("identificationUnitCode");
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

	public String getFeatureDescription() {
		return featureDescription; 
	}

	public void setFeatureDescription(String featureDescription) {
		this.featureDescription = featureDescription; 
		super.setField("featureDescription");
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

	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
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

}
