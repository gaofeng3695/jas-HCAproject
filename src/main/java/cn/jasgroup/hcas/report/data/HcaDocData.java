package cn.jasgroup.hcas.report.data;

import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.MiniTableRenderData;

/**
 * @description 高后果区文档实体
 * @author zhangyi
 * @date 2019年8月13日下午4:59:20
 * @version V1.0
 * @since JDK 1.80
 */

public class HcaDocData {

	/**
	 * 待合并的子文档集合
	 */
	private DocxRenderData segment;

	/**
	 * 管线列表数据
	 */
	private MiniTableRenderData pipelineTableData;

	/**
	 * 报告标题
	 */
	private String reportTitle;
	
	/**
	 * 报告时间	年 月
	 */
	private String reportTime;

	private String resultDesc;

	public DocxRenderData getSegment() {
		return segment;
	}

	public void setSegment(DocxRenderData segment) {
		this.segment = segment;
	}

	public MiniTableRenderData getPipelineTableData() {
		return pipelineTableData;
	}

	public void setPipelineTableData(MiniTableRenderData pipelineTableData) {
		this.pipelineTableData = pipelineTableData;
	}

	public String getReportTitle() {
		return reportTitle;
	}

	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	
	
}
