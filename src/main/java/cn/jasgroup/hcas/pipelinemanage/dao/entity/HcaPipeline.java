package cn.jasgroup.hcas.pipelinemanage.dao.entity;

import cn.jasgroup.jasframework.base.annotation.CommonDeleteConfig;
import cn.jasgroup.jasframework.base.annotation.CommonSaveConfig;
import cn.jasgroup.jasframework.base.annotation.CommonUpdateConfig;
import cn.jasgroup.jasframework.base.annotation.JdbcEntity;
import cn.jasgroup.jasframework.base.annotation.UniqueConstraintStrategy;
import cn.jasgroup.jasframework.base.annotation.UniqueConstraints;
import cn.jasgroup.jasframework.engine.jdbc.entity.CommonJdbcEntity;

/**
 * @description 管线表
 * @author zhangyi
 * @date 2019年1月15日上午11:33:19
 * @version V1.0
 * @since JDK 1.80
 */

@UniqueConstraints(
    strategys ={
        @UniqueConstraintStrategy(columnNames={"pipelineName"},name="管线名称"),
        @UniqueConstraintStrategy(columnNames={"pipelineCode"},name="管线编号"),
    }
)
@CommonSaveConfig(scene="/hcapipeline/save")
@CommonUpdateConfig(scene="/hcapipeline/update")
@CommonDeleteConfig(scene="/hcapipeline/delete")
@JdbcEntity(name="hca_pipeline")
public class HcaPipeline extends CommonJdbcEntity {
	/**
	 * 项目ID
	 */
	private String projectOid; 

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
	private Double outsidePipeDiameter; 

	/**
	 * 管线稳态运行时允许的最大压力 
	 */
	private Double maximumAllowablePressure; 

	/**
	 * 空间数据状态 
	 */
	private String geoState; 

	/**
	 * 备注 
	 */
	private String remark; 


	public String getProjectOid() {
		return projectOid; 
	}

	public void setProjectOid(String projectOid) {
		this.projectOid = projectOid; 
		super.setField("projectOid");
	}

	public String getPipelineName() {
		return pipelineName; 
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName; 
		super.setField("pipelineName");
	}

	public String getPipelineCode() {
		return pipelineCode; 
	}

	public void setPipelineCode(String pipelineCode) {
		this.pipelineCode = pipelineCode; 
		super.setField("pipelineCode");
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

	public Double getPipelineLength() {
		return pipelineLength; 
	}

	public void setPipelineLength(Double pipelineLength) {
		this.pipelineLength = pipelineLength; 
		super.setField("pipelineLength");
	}

	public Double getOutsidePipeDiameter() {
		return outsidePipeDiameter; 
	}

	public void setOutsidePipeDiameter(Double outsidePipeDiameter) {
		this.outsidePipeDiameter = outsidePipeDiameter; 
		super.setField("outsidePipeDiameter");
	}

	public Double getMaximumAllowablePressure() {
		return maximumAllowablePressure; 
	}

	public void setMaximumAllowablePressure(Double maximumAllowablePressure) {
		this.maximumAllowablePressure = maximumAllowablePressure; 
		super.setField("maximumAllowablePressure");
	}

	public String getGeoState() {
		return geoState; 
	}

	public void setGeoState(String geoState) {
		this.geoState = geoState; 
		super.setField("geoState");
	}

	public String getRemark() {
		return remark; 
	}

	public void setRemark(String remark) {
		this.remark = remark; 
		super.setField("remark");
	}

}
