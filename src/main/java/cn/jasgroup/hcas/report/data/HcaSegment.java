package cn.jasgroup.hcas.report.data;

import java.util.List;

import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.PictureRenderData;

/**
 * @description 高后果区子文档
 * @author zhangyi
 * @date 2019年8月13日下午5:15:32
 * @version V1.0
 * @since JDK 1.80
 */

public class HcaSegment {

	/**
	 * 高后果区编号
	 */
	private String highImpactAreaCode;
	/**
	 * 起始里程
	 */	
	private String startMileage;
	/**
	 * 终止里程
	 */	
	private String endMileage;
	/**
	 * 长度
	 */		
	private String hcaLength;
	/**
	 * 等级
	 */	
	private String highImpactLevel;
	/**
	 * 描述
	 */	
	private String description;
	/**
	 * 图片子文档
	 */	
	private DocxRenderData pictureSegment;
	
	public String getHighImpactAreaCode() {
		return highImpactAreaCode;
	}
	public void setHighImpactAreaCode(String highImpactAreaCode) {
		this.highImpactAreaCode = highImpactAreaCode;
	}
	public String getStartMileage() {
		return startMileage;
	}
	public void setStartMileage(String startMileage) {
		this.startMileage = startMileage;
	}
	public String getEndMileage() {
		return endMileage;
	}
	public void setEndMileage(String endMileage) {
		this.endMileage = endMileage;
	}
	public String getHcaLength() {
		return hcaLength;
	}
	public void setHcaLength(String hcaLength) {
		this.hcaLength = hcaLength;
	}
	public String getHighImpactLevel() {
		return highImpactLevel;
	}
	public void setHighImpactLevel(String highImpactLevel) {
		this.highImpactLevel = highImpactLevel;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public DocxRenderData getPictureSegment() {
		return pictureSegment;
	}
	public void setPictureSegment(DocxRenderData pictureSegment) {
		this.pictureSegment = pictureSegment;
	}

}
