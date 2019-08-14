package cn.jasgroup.hcas.report.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.SimpleResult;
import cn.jasgroup.hcas.report.service.HcaReportService;
import cn.jasgroup.jasframework.base.controller.BaseController;
import cn.jasgroup.jasframework.utils.ReadConfigUtil;
import cn.jasgroup.jasframework.utils.file.FileConvertUtil;

/**
 * @description 识别报告controller
 * @author zhangyi
 * @date 2019年8月12日下午3:06:05
 * @version V1.0
 * @since JDK 1.80
 */
@RestController
@RequestMapping("/hcaReport")
public class HcaReportController extends BaseController {

	@Autowired
	private HcaReportService hcaReportService;

	 
	 /**
	  *<p>功能描述：生成高后果区报告，返回文件名。</p>
	  * <p> 张毅 </p>	
	  * @param request
	  * @param paramMap	Map["pipelineOid": "管线Oid","versionOid": "识别版本Oid"]
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2019年8月14日 下午4:20:53。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@PostMapping("/createHcaDoc")
	public BaseResult createHcaDoc(HttpServletRequest request, @RequestBody Map<String, Object> paramMap) {

		BaseResult result = new BaseResult();
		try {
			String pipelineOid = (String) paramMap.get("pipelineOid");
			String versionOid = (String) paramMap.get("versionOid");
			if (StringUtils.isBlank(pipelineOid) || StringUtils.isBlank(versionOid)) {
				return new SimpleResult<Boolean>(-1, "400", "error");
			}
			String fileId = this.hcaReportService.createHcaReport(pipelineOid, versionOid);
			if(StringUtils.isBlank(fileId)){
				return new SimpleResult<Boolean>(-1, "500", "error");
			}
			result = new SimpleResult<String>(fileId);
		} catch (Exception e) {
			e.printStackTrace();
			result = new SimpleResult<Boolean>(-1, "500", "error");
		}
		return result;
	}

	 /**
	  *<p>功能描述：通过文件Id下载报告。</p>
	  * <p> 张毅 </p>	
	  * @param request
	  * @param response
	  * @param fileId
	  * @since JDK1.8。
	  * <p>创建日期:2019年8月14日 下午4:20:49。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@GetMapping("/downloadHcaReport")
	public void downloadReport(HttpServletRequest request, HttpServletResponse response, @RequestParam String fileId) {
		String fileType = ".docx";
		String downLoadPath = ReadConfigUtil.getPlatformConfig("hcaReport.createDoc");
		if(StringUtils.isBlank(downLoadPath)){
			downLoadPath = "";
		}
		String docFilePath = downLoadPath + File.separator + fileId + fileType;
		File file = new File(docFilePath);
		FileInputStream fileInput = null;
		OutputStream os = null;
		try {
			if (file.exists()) {
				fileInput = new FileInputStream(file);
				/* 在页面上弹出一个下载窗口 */
				response.setContentType("application/vnd.ms-excel;");
				/* 设置报头信息，弹出窗口中显示的文件名 newpath */
				response.setHeader("Content-Disposition", "attachment; filename=高后果区识别报告.docx");
				/* 具体的输入输出流操作 */
				os = response.getOutputStream();
				int rc = 0;
				byte[] b = new byte[1024 * 1024 * 5];
				while ((rc = fileInput.read(b)) > 0) {
					os.write(b, 0, rc);
				}
				os.flush();
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	 /**
	  *<p>功能描述：预览pdf。</p>
	  * <p> 张毅 </p>	
	  * @param request
	  * @param response
	  * @param fileId	文件ID
	  * @since JDK1.8。
	  * <p>创建日期:2019年8月14日 下午4:43:29。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@GetMapping("/previewpdf")
	public void preview(HttpServletRequest request, HttpServletResponse response, @RequestParam String fileId){
		String downLoadPath = ReadConfigUtil.getPlatformConfig("hcaReport.createDoc");
		if(StringUtils.isBlank(fileId)){
			return;
		}
		if(StringUtils.isBlank(downLoadPath)){
			downLoadPath = "";
		}
		String docFilePath = downLoadPath + File.separator + fileId + ".docx";
		String outputFilePath = ReadConfigUtil.getPlatformConfig("hcaReport.previewpdf"); 
		if(StringUtils.isBlank(outputFilePath)){
			outputFilePath = "";
		}
		try {
			File filePath = new File(outputFilePath);
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			String outputFileName = outputFilePath + "/" + fileId + ".pdf";
			FileConvertUtil.convert2Pdf(docFilePath, outputFileName);
			File file = new File(outputFileName);
			FileInputStream fileInput = null;
			OutputStream os = null;
			try {
				if (file.exists()) {
					fileInput = new FileInputStream(file);
					/* 在页面上弹出一个下载窗口 */
					response.setContentType("application/vnd.ms-excel;");
					/* 设置报头信息，弹出窗口中显示的文件名 newpath */
					response.setHeader("Content-Disposition", "attachment; filename=高后果区识别报告.docx");
					/* 具体的输入输出流操作 */
					os = response.getOutputStream();
					int rc = 0;
					byte[] b = new byte[1024 * 1024 * 5];
					while ((rc = fileInput.read(b)) > 0) {
						os.write(b, 0, rc);
					}
					os.flush();
					file.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (os != null)
						os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
