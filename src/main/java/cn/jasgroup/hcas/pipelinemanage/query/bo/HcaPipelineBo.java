package cn.jasgroup.hcas.pipelinemanage.query.bo;

import cn.jasgroup.jasframework.base.data.CommonBaseBo;

/**
 * @description 管线bo类
 * @author zhangyi
 * @date 2019年1月15日下午1:51:33
 * @version V1.0
 * @since JDK 1.80
 */

public class HcaPipelineBo extends CommonBaseBo {
	
	/**
	 * 主键oid 
	 */
	private String oid;

	/**
	 * 项目ID 
	 */
	private String projectOid; 
	
	/**
	 * 项目名称 
	 */
	private String projectName; 

	/**
	 * 管线名称 
	 */
	private String pipelineName; 

	/**
	 * 管线编号 
	 */
	private String pipelineCode; 

	/**
	 * 起始里程 
	 */
	private Double startMileage; 

	/**
	 * 终止里程 
	 */
	private Double endMileage; 

	/**
	 * 管道长度 
	 */
	private Double pipelineLength; 

	/**
	 * 管道外管径 
	 */
	private Double outsideDiameter; 

	/**
	 * 管线稳态运行时允许的最大压力 
	 */
	private Double pressure; 

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

	public String getPipelineCode() {
		return pipelineCode;
	}

	public void setPipelineCode(String pipelineCode) {
		this.pipelineCode = pipelineCode;
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

	public Double getPipelineLength() {
		return pipelineLength;
	}

	public void setPipelineLength(Double pipelineLength) {
		this.pipelineLength = pipelineLength;
	}

	public Double getOutsideDiameter() {
		return outsideDiameter;
	}

	public void setOutsidePipeDiameter(Double outsideDiameter) {
		this.outsideDiameter = outsideDiameter;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	} 
	

}
