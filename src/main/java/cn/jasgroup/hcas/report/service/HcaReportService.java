package cn.jasgroup.hcas.report.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;

import cn.jasgroup.hcas.areamanage.query.HcaAreaQuery;
import cn.jasgroup.hcas.highimpactarea.query.HcaHighImpactAreaQuery;
import cn.jasgroup.hcas.highimpactarea.query.bo.HcaHighImpactAreaBo;
import cn.jasgroup.hcas.pipelinemanage.query.HcaPipelineQuery;
import cn.jasgroup.hcas.pipelinemanage.query.bo.HcaPipelineBo;
import cn.jasgroup.hcas.report.data.HcaDocData;
import cn.jasgroup.hcas.report.data.HcaSegment;
import cn.jasgroup.jasframework.engine.jdbc.service.CommonDataJdbcService;
import cn.jasgroup.jasframework.utils.ReadConfigUtil;

/**
 * @description 识别报告service
 * @author zhangyi
 * @date 2019年8月12日下午3:05:36
 * @version V1.0
 * @since JDK 1.80
 */

@Service
@Transactional
public class HcaReportService extends CommonDataJdbcService {

	public String createAreaReport() {
		/**
		 * 管道参数：名称、编号、起始里程、终止里程、长度、外管径、最大压强
		 * 管道周边人口和自然环境情况
		 * a.	地区等级划分统计表
		 * b.	地区等级划分长度比例图
		 * c.	再识别日期
		 */
		HcaPipelineQuery pipelineQuery = new HcaPipelineQuery();
		List<?> pipelineList = super.getList(pipelineQuery);

		HcaAreaQuery areaQuery = new HcaAreaQuery();
		List<?> areaList = super.getList(areaQuery);

		return null;
	}

	/**
	 *<p>功能描述：生成高后果区报告。</p>
	 * <p> 张毅 </p>	
	 * @param pipelineOid
	 * @param versionOid
	 * @return
	 * @since JDK1.8。
	 * <p>创建日期:2019年8月13日 下午3:03:47。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public String createHcaReport(String pipelineOid, String versionOid) {
		String templatePath = "";
		String segmentPath = "";
		Resource templateResource = new ClassPathResource("/hca-template/hcaReport.docx");
		Resource segmentResource = new ClassPathResource("/hca-template/hcaReportSegment.docx");
		templatePath = templateResource.toString();
		segmentPath = segmentResource.toString();
		try {
			templatePath = templateResource.getFile().getAbsolutePath();
			segmentPath = segmentResource.getFile().getAbsolutePath();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}

		HcaDocData docData = new HcaDocData();
		Calendar calendar = Calendar.getInstance();
		int[] months = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		int year = calendar.get(Calendar.YEAR);
		int month = months[calendar.get(Calendar.MONTH)];
		docData.setReportTime(year + " 年 " + month + " 月");
		/************** 管线数据 ******************/
		HcaPipelineQuery pipelineQuery = new HcaPipelineQuery();
		pipelineQuery.setOid(pipelineOid);
		List<?> pipelineList = super.getList(pipelineQuery);
		RowRenderData header = RowRenderData.build(new TextRenderData("管线名称"), new TextRenderData("管线编号"),
				new TextRenderData("长度"), new TextRenderData("外管径"), new TextRenderData("最大压强"));
		List<RowRenderData> rowRenderDataList = new ArrayList<>();
		int pipelineSize = pipelineList.size();
		for (int i = 0; i < pipelineSize; i++) {
			HcaPipelineBo bo = (HcaPipelineBo) pipelineList.get(i);
			docData.setReportTitle(bo.getPipelineName());
			RowRenderData row = RowRenderData.build(bo.getPipelineName(), bo.getPipelineCode(),
					String.valueOf(bo.getPipelineLength()), String.valueOf(bo.getOutsideDiameter()),
					String.valueOf(bo.getPressure()));
			rowRenderDataList.add(row);
		}
		MiniTableRenderData miniTableRenderData = new MiniTableRenderData(header, rowRenderDataList,
				MiniTableRenderData.WIDTH_A4_MEDIUM_FULL);
		docData.setPipelineTableData(miniTableRenderData);

		/************** 识别区数据 ******************/
		HcaHighImpactAreaQuery hcaAreaQuery = new HcaHighImpactAreaQuery();
		hcaAreaQuery.setVersionOid(versionOid);
		List<?> hcaAreaList = super.getList(hcaAreaQuery);
		docData.setResultDesc(getHcaResultDesc(versionOid));
		List<HcaSegment> hcaSegments = new ArrayList<HcaSegment>();
		int hcaListSize = hcaAreaList.size();
		for (int i = 0; i < hcaListSize; i++) {
			HcaSegment s = new HcaSegment();
			HcaHighImpactAreaBo bo = (HcaHighImpactAreaBo) hcaAreaList.get(i);
			s.setHighImpactAreaCode(bo.getHighImpactAreaCode());
			Double length = bo.getHcaLength();
			s.setHcaLength(length == null ? "" : String.valueOf(length));
			Double startMileage = bo.getStartMileage();
			s.setStartMileage(startMileage == null ? "" : String.valueOf(startMileage));
			Double endMileage = bo.getEndMileage();
			s.setEndMileage(endMileage == null ? "" : String.valueOf(endMileage));
			s.setHighImpactLevel(bo.getHighImpactLevelName());
			s.setDescription(bo.getDescription());
			hcaSegments.add(s);
		}

		DocxRenderData segment = new DocxRenderData(new File(segmentPath), hcaSegments);
		docData.setSegment(segment);
		/************** 识别区数据 ******************/

		XWPFTemplate template = XWPFTemplate.compile(templatePath).render(docData);
		FileOutputStream out = null;
		String outFileName = "";
		try {

			String outPath = ReadConfigUtil.getPlatformConfig("hcaReport.createDoc");
			File outFile = new File(outPath);
			if (!outFile.exists()) {
				outFile.mkdirs();
			}
			outFileName = String.valueOf(System.currentTimeMillis());
			String outFilePathName  = outPath + "/" + outFileName  + ".docx";
			out = new FileOutputStream(outFilePathName);
			template.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.flush();
					out.close();
				}
				template.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return outFileName;
	}

	/**
	 *<p>功能描述：生成高后果区报告描述。</p>
	 * <p> 张毅 </p>	
	 * @param pipelineOid
	 * @param versionOid
	 * @return
	 * @since JDK1.8。
	 * <p>创建日期:2019年8月13日 下午3:03:47。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@SuppressWarnings("unchecked")
	public String getHcaResultDesc(String versionOid) {
		/**
		 * 1.查管线长度
		 * 2.统计当前管线上高后果区识别个数，总长度，长度占比
		 * 3.统计各级高后果区个数、长度、长度占比
		 * 
		 * 
		 * */

		HcaHighImpactAreaQuery hcaQuery = new HcaHighImpactAreaQuery();
		hcaQuery.setVersionOid(versionOid);
		List<HcaHighImpactAreaBo> hcaList = (List<HcaHighImpactAreaBo>) super.getList(hcaQuery);
		int hcaListSize = hcaList.size();
		Double hcaLength = countLength(hcaList);
		List<HcaHighImpactAreaBo> oneLevelList = new ArrayList<>();
		List<HcaHighImpactAreaBo> twoLevelList = new ArrayList<>();
		List<HcaHighImpactAreaBo> threeLevelList = new ArrayList<>();
		for (int i = 0; i < hcaListSize; i++) {
			HcaHighImpactAreaBo bo = hcaList.get(i);
			String level = bo.getHighImpactLevelName();
			if (StringUtils.isBlank(level)) {
				continue;
			}
			switch (level) {
			case "Ⅰ级":
				oneLevelList.add(bo);
				break;
			case "Ⅱ级":
				twoLevelList.add(bo);
				break;
			case "Ⅲ级":
				threeLevelList.add(bo);
				break;
			default:
				break;
			}
		}
		int oneSize = oneLevelList.size();
		int twoSize = twoLevelList.size();
		int threeSize = threeLevelList.size();
		Double oneLength = countLength(oneLevelList);
		Double twoLength = countLength(twoLevelList);
		Double threeLength = countLength(threeLevelList);
		// 管线
		HcaPipelineQuery pipelineQuery = new HcaPipelineQuery();
		HcaPipelineBo pipelineBo = new HcaPipelineBo();
		if (hcaListSize > 0) {
			String pipelineOid = hcaList.get(0).getPipelineOid();
			pipelineQuery.setOid(pipelineOid);
			List<?> pipelineList = super.getList(pipelineQuery);
			if (pipelineList.size() > 0) {
				pipelineBo = (HcaPipelineBo) pipelineList.get(0);
			}
		}
		Double pipelineLength = pipelineBo.getPipelineLength();
		String pipelineName = pipelineBo.getPipelineName();
		String hcaPercent = countLength(hcaLength, pipelineLength);
		String onePercent = countLength(oneLength, pipelineLength);
		String twoPercent = countLength(twoLength, pipelineLength);
		String threePercent = countLength(threeLength, pipelineLength);

		StringBuilder resultDesc = new StringBuilder("本次共识别");
		resultDesc.append(pipelineName);
		resultDesc.append(pipelineLength);
		resultDesc.append("公里，高后果区共识别");
		resultDesc.append(hcaListSize);
		resultDesc.append("段，总长度");
		resultDesc.append(hcaLength);
		resultDesc.append("km,");
		resultDesc.append("高后果区长度占管道识别总长度");
		resultDesc.append(hcaPercent);
		resultDesc.append("。");
		if (oneSize > 0 || twoSize > 0 || threeSize > 0) {
			resultDesc.append("其中，");
			if (oneSize > 0) {
				resultDesc.append("Ⅰ级高后果区");
				resultDesc.append(oneSize);
				resultDesc.append("段，长度");
				resultDesc.append(oneLength);
				resultDesc.append("km,");
				resultDesc.append("占管道总长度");
				resultDesc.append(onePercent);
				resultDesc.append("；");
			}
			if (twoSize > 0) {
				resultDesc.append("Ⅱ级高后果区");
				resultDesc.append(twoSize);
				resultDesc.append("段，长度");
				resultDesc.append(twoLength);
				resultDesc.append("km，");
				resultDesc.append("占管道总长度");
				resultDesc.append(twoPercent);
				resultDesc.append("；");
			}
			if (threeSize > 0) {
				resultDesc.append("Ⅲ级高后果区");
				resultDesc.append(threeSize);
				resultDesc.append("段，长度");
				resultDesc.append(threeLength);
				resultDesc.append("km，");
				resultDesc.append("占管道总长度");
				resultDesc.append(threePercent);
				resultDesc.append("。");
			}
		}
		int resultDescLength = resultDesc.length();
		resultDesc.replace(resultDescLength - 1, resultDescLength, "。");
		return resultDesc.toString();
	}

	/**
	 *<p>功能描述：计算长度。</p>
	 * <p> 张毅 </p>	
	 * @param list
	 * @return
	 * @since JDK1.8。
	 * <p>创建日期:2019年8月15日 下午2:53:26。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public Double countLength(List<HcaHighImpactAreaBo> list) {
		int size = list.size();
		Double totalLength = 0d;
		for (int i = 0; i < size; i++) {
			HcaHighImpactAreaBo bo = list.get(i);
			Double startMileage = bo.getStartMileage();
			Double endMileage = bo.getEndMileage();
			totalLength += (endMileage - startMileage);
		}
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(4);
		String result = numberFormat.format(totalLength);
		return Double.valueOf(result);
	}

	/**
	 *<p>功能描述：计算百分比。</p>
	 * <p> 张毅 </p>	
	 * @param a
	 * @param b
	 * @return
	 * @since JDK1.8。
	 * <p>创建日期:2019年8月15日 下午2:53:22。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public String countLength(Double a, Double b) {
		if (null == a && null == b || 0d == b) {
			return null;
		}
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		String result = numberFormat.format((Double) (a / b) * 100) + "%";
		return result;
	}
}
