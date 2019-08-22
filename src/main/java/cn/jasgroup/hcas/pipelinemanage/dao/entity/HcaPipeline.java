package cn.jasgroup.hcas.pipelinemanage.dao.entity;

import javax.persistence.Column;

import cn.jasgroup.framework.spatial.annotation.Line;
import cn.jasgroup.framework.spatial.entity.ArcGisSpatialObject;
import cn.jasgroup.jasframework.base.annotation.CommonDeleteConfig;
import cn.jasgroup.jasframework.base.annotation.CommonSaveConfig;
import cn.jasgroup.jasframework.base.annotation.CommonUpdateConfig;
import cn.jasgroup.jasframework.base.annotation.JdbcEntity;
import cn.jasgroup.jasframework.base.annotation.UniqueConstraintStrategy;
import cn.jasgroup.jasframework.base.annotation.UniqueConstraints;

/**
 * @description 管线表
 * @author zhangyi
 * @date 2019年1月15日上午11:33:19
 * @version V1.0
 * @since JDK 1.80
 */

@UniqueConstraints(
    strategys ={
//        @UniqueConstraintStrategy(columnNames={"pipelineName"},name="管线名称"),
        @UniqueConstraintStrategy(columnNames={"pipelineCode"},name="管线编号"),
    }
)
@CommonSaveConfig(scene="/hcapipeline/save")
@CommonUpdateConfig(scene="/hcapipeline/update")
@CommonDeleteConfig(scene="/hcapipeline/delete")
@Line(geometryColumnName = "shape")
@JdbcEntity(name="hca_pipeline")
public class HcaPipeline extends ArcGisSpatialObject {

	/**
	 * 管线编号 
	 */
	private String pipelineCode; 

	/**
	 * 管线名称 
	 */
	private String pipelineName; 
	
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

	@Column(name="pipeline_code")
	public String getPipelineCode() {
		return pipelineCode; 
	}

	public void setPipelineCode(String pipelineCode) {
		this.pipelineCode = pipelineCode; 
		super.setField("pipelineCode");
	}

	@Column(name="pipeline_name")
	public String getPipelineName() {
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
		super.setField("pipelineName");
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

	@Column(name="pipeline_length")
	public Double getPipelineLength() {
		return pipelineLength; 
	}

	public void setPipelineLength(Double pipelineLength) {
		this.pipelineLength = pipelineLength; 
		super.setField("pipelineLength");
	}

	@Column(name="outside_diameter")
	public Double getOutsideDiameter() {
		return outsideDiameter; 
	}

	public void setOutsideDiameter(Double outsideDiameter) {
		this.outsideDiameter = outsideDiameter; 
		super.setField("outsideDiameter");
	}

	@Column(name="pressure")
	public Double getPressure() {
		return pressure; 
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure; 
		super.setField("pressure");
	}

	@Column(name="remarks")
	public String getRemarks() {
		return remarks; 
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks; 
		super.setField("remarks");
	}

}
