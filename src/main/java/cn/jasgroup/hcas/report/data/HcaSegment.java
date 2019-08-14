package cn.jasgroup.hcas.report.data;

/**
 * @description 高后果区子文档
 * @author zhangyi
 * @date 2019年8月13日下午5:15:32
 * @version V1.0
 * @since JDK 1.80
 */

public class HcaSegment {

	private String highImpactAreaCode;
	private String startMileage;
	private String endMileage;
	private String hcaLength;
	private String highImpactLevel;
	private String description;
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
	
	
}
