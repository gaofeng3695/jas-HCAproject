package cn.jasgroup.jasframework.acquisitiondata;

import java.util.List;

import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.PictureRenderData;

/**
 * @description TODO
 * @author zhangyi
 * @date 2019年8月13日下午4:08:14
 * @version V1.0
 * @since JDK 1.80
 */

public class SegmentDataDoc {

	private String highImpactAreaCode;
	private String startMileage;
	private String endMileage;
	private String hcaLength;
	private String highImpactLevel;
	private String description;
//	private PictureRenderData gispictures;
	private List<PictureRenderData> gispicturesList;
	
	private DocxRenderData pictureSegment;
	
	public DocxRenderData getPictureSegment() {
		return pictureSegment;
	}
	public void setPictureSegment(DocxRenderData pictureSegment) {
		this.pictureSegment = pictureSegment;
	}
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
	public List<PictureRenderData> getGispicturesList() {
		return gispicturesList;
	}
	public void setGispicturesList(List<PictureRenderData> gispicturesList) {
		this.gispicturesList = gispicturesList;
	}
//	public PictureRenderData getGispictures() {
//		return gispictures;
//	}
//	public void setGispictures(PictureRenderData gispictures) {
//		this.gispictures = gispictures;
//	}
	
	
}
