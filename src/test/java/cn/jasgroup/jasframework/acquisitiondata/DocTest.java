package cn.jasgroup.jasframework.acquisitiondata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;

import cn.jasgroup.hcas.pipelinemanage.dao.entity.HcaPipeline;

/**
 * @description 测试poi-tl
 * @author zhangyi
 * @date 2019年8月12日下午4:18:04
 * @version V1.0
 * @since JDK 1.80
 */

public class DocTest {
	
	 
	 /**
	  *<p>功能描述：单个普通表格填充。</p>
	  * <p> 张毅 </p>	
	  * @since JDK1.8。
	  * <p>创建日期:2019年8月13日 下午4:26:16。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Test
	public void testCreateTable(){
		// 单个普通表格录入
		String path = "E://hcaReport.docx";
		
		HcaPipeline pipeline = new HcaPipeline();
		pipeline.setPipelineName("干线-河南");
		HcaPipeline pipeline2 = new HcaPipeline();
		pipeline2.setPipelineName("干线1-河南");
		
		List<HcaPipeline> pipelineList = new ArrayList<>();
		pipelineList.add(pipeline);
		pipelineList.add(pipeline2);
		RowRenderData header = RowRenderData.build(new TextRenderData("管线名称"), new TextRenderData("管线长度"));
		int size = pipelineList.size();
		List<RowRenderData> rowRenderDataList = new ArrayList<>();
 		for(int i =0; i < size; i++){
			HcaPipeline pipeline1 = pipelineList.get(i);
			RowRenderData row = RowRenderData.build(pipeline1.getPipelineName(), String.valueOf(pipeline1.getPipelineLength()));
			rowRenderDataList.add(row);
		}
 		MiniTableRenderData  miniTableRenderData = new MiniTableRenderData(header, 
 				rowRenderDataList, MiniTableRenderData.WIDTH_A4_MEDIUM_FULL);
 		List<MiniTableRenderData> tableList = new ArrayList<>();
 		tableList.add(miniTableRenderData);
 		Map<String, Object> datas = new HashMap(){
 			{
 				put("order", miniTableRenderData);
 			}
 		};
 		
		XWPFTemplate template = XWPFTemplate.compile(path).render(datas);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("E://out_template.docx");
			template.write(out); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != out){
					out.flush();
					out.close();
				}
				template.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	 
	 /**
	  *<p>功能描述：error。</p>
	  * <p> 张毅 </p>	
	  * @since JDK1.8。
	  * <p>创建日期:2019年8月13日 下午4:26:41。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Test
	public void test(){
		DocUtil datas = new DocUtil();
		
		String path = "E://hcaReport.docx";
		HashMap<String, Object> hasMap = new HashMap<>();
		hasMap.put("reportTitle", "Poi-tl 模板引擎");
		HcaPipeline pipeline = new HcaPipeline();
		pipeline.setPipelineName("干线-河南");
		HcaPipeline pipeline2 = new HcaPipeline();
		pipeline2.setPipelineName("干线1-河南");
		
		List<HcaPipeline> pipelineList = new ArrayList<>();
		pipelineList.add(pipeline);
		pipelineList.add(pipeline2);
		RowRenderData header = RowRenderData.build(new TextRenderData("FFFFFF", "管线名称"), new TextRenderData("FFFFFF", "管线长度"));
		int size = pipelineList.size();
		List<RowRenderData> rowRenderDataList = new ArrayList<>();
 		for(int i =0; i < size; i++){
			HcaPipeline pipeline1 = pipelineList.get(i);
			RowRenderData row = RowRenderData.build(pipeline1.getPipelineName(), String.valueOf(pipeline1.getPipelineLength()));
			rowRenderDataList.add(row);
		}
 		MiniTableRenderData  miniTableRenderData = new MiniTableRenderData(header, 
 				rowRenderDataList, MiniTableRenderData.WIDTH_A4_MEDIUM_FULL);
 		datas.setTableData(miniTableRenderData); 
 		DetailData detailTable = new  DetailData();
 		detailTable.setRowData(rowRenderDataList);
 		datas.setDetailTable(detailTable);
 		
 		
 		Configure config = Configure.newBuilder().customPolicy("detail_table", new DetailTablePolicy()).build();
		XWPFTemplate template = XWPFTemplate.compile(path, config).render(datas);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("E://out_template.docx");
			template.write(out); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != out){
					out.flush();
					out.close();
				}
				template.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	 
	 /**
	  *<p>功能描述：自定义表格。</p>
	  * <p> 张毅 </p>	
	  * @since JDK1.8。
	  * <p>创建日期:2019年8月13日 下午4:26:00。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Test
	public void testCreateBatchTable(){
		DocUtil datas = new DocUtil();
		
		// 单个普通表格录入
		String path = "E://hcaReport.docx";
		
		HcaPipeline pipeline = new HcaPipeline();
		pipeline.setPipelineName("干线-河南");
		HcaPipeline pipeline2 = new HcaPipeline();
		pipeline2.setPipelineName("干线1-河南");
		
		List<HcaPipeline> pipelineList = new ArrayList<>();
		pipelineList.add(pipeline);
		pipelineList.add(pipeline2);
		RowRenderData header = RowRenderData.build(new TextRenderData("管线名称"), new TextRenderData("管线长度"));
		int size = pipelineList.size();
		List<RowRenderData> rowRenderDataList = new ArrayList<>();
 		for(int i =0; i < size; i++){
			HcaPipeline pipeline1 = pipelineList.get(i);
			RowRenderData row = RowRenderData.build(pipeline1.getPipelineName(), String.valueOf(pipeline1.getPipelineLength()));
			rowRenderDataList.add(row);
		}
 		MiniTableRenderData  miniTableRenderData = new MiniTableRenderData(header, 
 				rowRenderDataList, MiniTableRenderData.WIDTH_A4_MEDIUM_FULL);
 		datas.setTableData(miniTableRenderData); 
 		DetailData detailTable = new  DetailData();
 		detailTable.setRowData(rowRenderDataList);
 		datas.setDetailTable(detailTable);
 		
 		
 		Configure config = Configure.newBuilder()
                .customPolicy("detail_table", new CustomTableRenderPolicy()).build();
		XWPFTemplate template = XWPFTemplate.compile(path, config).render(datas);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("E://out_template.docx");
			template.write(out); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != out){
					out.flush();
					out.close();
				}
				template.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	 
	 /**
	  *<p>功能描述：文档合并。</p>
	  * <p> 张毅 </p>	
	  * @since JDK1.8。
	  * <p>创建日期:2019年8月13日 下午4:25:51。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Test
	public void testCreateSegment(){
		DocUtil datas = new DocUtil();
		
		String path = "E://hcaReport.docx";
		
		/************** 管线数据 ******************/
		HcaPipeline pipeline = new HcaPipeline();
		pipeline.setPipelineName("干线-河南");
		HcaPipeline pipeline2 = new HcaPipeline();
		pipeline2.setPipelineName("干线1-河南");
		
		List<HcaPipeline> pipelineList = new ArrayList<>();
		pipelineList.add(pipeline);
		pipelineList.add(pipeline2);
		RowRenderData header = RowRenderData.build(new TextRenderData("管线名称"), new TextRenderData("管线长度"));
		int size = pipelineList.size();
		List<RowRenderData> rowRenderDataList = new ArrayList<>();
 		for(int i =0; i < size; i++){
			HcaPipeline pipeline1 = pipelineList.get(i);
			RowRenderData row = RowRenderData.build(pipeline1.getPipelineName(), String.valueOf(pipeline1.getPipelineLength()));
			rowRenderDataList.add(row);
		}
 		MiniTableRenderData  miniTableRenderData = new MiniTableRenderData(header, 
 				rowRenderDataList, MiniTableRenderData.WIDTH_A4_MEDIUM_FULL);
		datas.setTableData(miniTableRenderData);
		/************** 识别区数据 ******************/
		 List<SegmentDataDoc> segments = new ArrayList<SegmentDataDoc>();	
		 
		 SegmentDataDoc s1 = new SegmentDataDoc();
		 
		 s1.setHighImpactAreaCode("s1");
		 segments.add(s1);
		 SegmentDataDoc s2 = new SegmentDataDoc();
		 
		 s2.setHighImpactAreaCode("s2");
		 segments.add(s2);
		 DocxRenderData segment = new DocxRenderData(new File("E://hcaReportSegment.docx"), segments );
		 datas.setSegment(segment);
		 /************** 识别区数据 ******************/
		 
		XWPFTemplate template = XWPFTemplate.compile(path).render(datas);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("E://out_template.docx");
			template.write(out); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != out){
					out.flush();
					out.close();
				}
				template.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
