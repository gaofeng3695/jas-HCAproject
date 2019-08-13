package cn.jasgroup.jasframework.acquisitiondata;

import com.deepoove.poi.config.Name;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.MiniTableRenderData;

/**
 * @description 表格标签
 * @author zhangyi
 * @date 2019年8月13日上午10:41:15
 * @version V1.0
 * @since JDK 1.80
 */

public class DocUtil {
	
	private DocxRenderData segment;
	
	private DocxRenderData pipelnesegment;

	
	public DocxRenderData getPipelnesegment() {
		return pipelnesegment;
	}

	public void setPipelnesegment(DocxRenderData pipelnesegment) {
		this.pipelnesegment = pipelnesegment;
	}

	public DocxRenderData getSegment() {
		return segment;
	}

	public void setSegment(DocxRenderData segment) {
		this.segment = segment;
	}

	private MiniTableRenderData tableData;
	
	@Name("detail_pipeline_table")
	private DetailData detailPipelineTable;
	
	@Name("detail_table")
	private DetailData detailTable;
	private String subtotal;
	private String other;
	private String totalHca;
	public MiniTableRenderData getTableData() {
		return tableData;
	}
	public void setTableData(MiniTableRenderData tableData) {
		this.tableData = tableData;
	}
	public DetailData getDetailTable() {
		return detailTable;
	}
	public void setDetailTable(DetailData detailTable) {
		this.detailTable = detailTable;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getTotalHca() {
		return totalHca;
	}
	public void setTotalHca(String totalHca) {
		this.totalHca = totalHca;
	}
	public DetailData getDetailPipelineTable() {
		return detailPipelineTable;
	}
	public void setDetailPipelineTable(DetailData detailPipelineTable) {
		this.detailPipelineTable = detailPipelineTable;
	}

	
}
