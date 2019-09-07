package cn.jasgroup.hcas.elementunit.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.ListResult;
import cn.jasgroup.framework.data.result.SimpleResult;
import cn.jasgroup.hcas.elementunit.query.bo.HcaBuildings2;
import cn.jasgroup.hcas.elementunit.service.HcaBuildingService;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.jasgroup.hcas.elementunit.query.HcaBuildingsQuery;
import cn.jasgroup.hcas.elementunit.query.bo.HcaBuildingsBo;
import cn.jasgroup.jasframework.attachment.dao.entity.SysAttachment;
import cn.jasgroup.jasframework.attachment.dao.entity.SysAttachmentBusinessRelation;
import cn.jasgroup.jasframework.attachment.service.AttachmentService;
import cn.jasgroup.jasframework.base.controller.BaseController;
import cn.jasgroup.jasframework.engine.jdbc.service.CommonDataJdbcService;
import cn.jasgroup.jasframework.excel.util.ExcelExportUtil;
import cn.jasgroup.jasframework.utils.DateTimeUtil;
import cn.jasgroup.jasframework.utils.ReadConfigUtil;
import cn.jasgroup.jasframework.utils.StringUtil;

/**
 * @description 建构筑物
 * @author zhangyi
 * @date 2019年8月14日下午8:05:35
 * @version V1.0
 * @since JDK 1.80
 */

@RestController
@RequestMapping("hcabuildings")
public class HcaBuildingsController extends BaseController {

	@Autowired
	private CommonDataJdbcService commonDataJdbcService;

    @Resource
    private HcaBuildingService hcaBuildingService ;
    
	@Autowired
	private AttachmentService attachmentService;

	/**
	 *<p>功能描述：导出网格选中的全部数据为excel格式文件。</p>
	 * <p> 张毅 </p>	
	 * @param request
	 * @param response
	 * @param query	查询策略
	 * @return
	 * @since JDK1.8。
	 * <p>创建日期:2019年8月14日 下午7:39:16。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportToExcelAction")
	public String exportToExcelAction(HttpServletRequest request, HttpServletResponse response, HcaBuildingsQuery query) {
		// 列属性名称
		String propertyName = "buildingCode,buildingTypeParent,buildingType,buildingDistribution,households,population,address,pressurePipeline,collectDate,remarks";
		List<String> propertyList = new ArrayList<String>();
		if (propertyName != null) {
			propertyList = Arrays.asList(propertyName.split(","));
		}
		// 列属性描述
		String propertyDes = "建（构）筑物编号,建（构）筑物类别,建（构）筑物类型,建筑分布,户数,人口,地址,是否占压,采集时间,备注";
		List<String> propertyDesList = new ArrayList<String>();
		if (propertyName != null) {
			propertyDesList = Arrays.asList(propertyDes.split(","));
		}
		List<HcaBuildingsBo> list = (List<HcaBuildingsBo>) this.commonDataJdbcService.getList(query);
		// format导出数据的格式，确保数据的导出的正确性
		List<Map<String, String>> map = new ArrayList<Map<String, String>>();
		for (HcaBuildingsBo bo : list) {
			Map<String, Object> ms = bo.getValueMap();
			Set<String> key = ms.keySet();
			Map<String, String> mss = new HashMap<>();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				Object valueObject = ms.get(s);
				if("buildingTypeParent".equals(s)){
					valueObject = ms.get("buildingTypeParentName");
				}
				if("buildingType".equals(s)){
					valueObject = ms.get("buildingTypeName");
				}
				if("buildingDistribution".equals(s)){
					valueObject = ms.get("buildingDistributionName");
				}
				if("pressurePipeline".equals(s)){
					valueObject = ms.get("pressurePipeline");
					if(null == valueObject){
						valueObject = "";
					}else if("0".equals(valueObject.toString())){
						valueObject = "否";
					}else{
						valueObject = "是";
					}
				}
				
				String valueString = "";
				// 如果为日期
				if (valueObject instanceof Date) {
					valueString = DateTimeUtil.getFormatDate((Date) valueObject, DateTimeUtil.DATE_FORMAT);
				} else if (valueObject != null) {
					valueString = String.valueOf(valueObject);
					if("households".equals(s) || "population".equals(s)){
						String[]  arr = valueString.split("\\.");
						valueString = arr[0];
					}
				}
				mss.put(s, valueString);
			}
			map.add(mss);
		}
		// 调用导出工具类导出数据
		String[] typeArr = { "建（构）筑物导入模板", "建（构）筑物导入模板" }; // {标题名,sheet名}
		// 第一个参数表名为非空字符串,则进行模板查询,若有模板利用模板导出,没有则自动生成Excel导出;参数为空,则自动生成Excel导出
		new ExcelExportUtil().exportWithTemplate("hca_buildings", "hcabuildingsinfo", "建（构）筑物导入模板.xlsx", map, propertyList,
				propertyDesList, request, response, typeArr);
		return null;
	}

	/**
	 *<p>保存构筑物信息。</p>
	 *<p>kongchao</p>
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(value = "save")
	@ResponseBody
	public BaseResult save(@RequestBody HcaBuildings2 hcaBuildings) {

		SimpleResult result = new SimpleResult<>();
		try {
			int data = hcaBuildingService.save(hcaBuildings);
			result.setData(hcaBuildings.getOid());
			if(data == 0 ){
				result.setStatus(0);
				result.setMsg("保存失败！");
			}else{
				result.setMsg("保存成功");
			}
		}catch (Exception e){
			result.setStatus(0);
			result.setMsg(e.getMessage());
		}

		return result;
	}

	 
	 /**
	  *<p>功能描述：保存base64的图片接口。</p>
	  * <p> 张毅 </p>	
	  * @param request
	  * @param businessId	业务Id
	  * @param businessType	业务类型（业务含义）, 默认值为"attachment"
	  * @param fileType	文件类型file/pic, 默认值为"attachment"
	  * @param moduleCode	所属模块（kass系统使用）
	  * @param folderId	文件Id
	  * @param fileDataMap	{"fileData": base64字符串}
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2019年9月4日 下午7:33:45。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@RequestMapping(value = "/uploadImage")
	@ResponseBody
	public BaseResult uploadImage(HttpServletRequest request, @RequestParam("businessId") String businessId,
			@RequestParam(value = "businessType", required = false, defaultValue = "attachment") String businessType,
			@RequestParam(value = "fileType", required = false, defaultValue = "attachment") String fileType,
			@RequestParam(value = "moduleCode", defaultValue = "default", required = false) String moduleCode,
			@RequestParam(value = "folderId", defaultValue = "8b0d4463-af4e-4e83-8004-659833162ee9", required = false) String folderId,
			@RequestBody Map<String, String> fileDataMap) {

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 內存限制，文件大小超过该限制将被保存成临时文件
		String memorySizeThreshold = ReadConfigUtil.getPlatformConfig("fileUpload.memorySizeThreshold");
		if (StringUtil.hasText(memorySizeThreshold)) {
			factory.setSizeThreshold(Integer.parseInt(memorySizeThreshold));
		}
		if(null == fileDataMap){
			return new BaseResult(-1,"400","截图为空");
		}
		// 单个文件大小限制
		String singleFileMaxsize = ReadConfigUtil.getPlatformConfig("fileUpload.singleFileMaxsize");
		long maxsize =99999999L;
		
		if(StringUtil.hasText(singleFileMaxsize)){
			maxsize = Long.valueOf(singleFileMaxsize);
		}
		List<String> fileIdList = new ArrayList<String>();
		try {
			String fileData = fileDataMap.get("fileData");
			String fileName = fileDataMap.get("buildingCode");
			
			if(StringUtil.hasText(fileName)) {
				fileName = URLDecoder.decode(fileName, "UTF-8");
			}else{
				fileName = "建构筑物截图";
			}
			// 二进制文件数组
			byte[] b = Base64.decodeBase64(fileData.replace("data:image/png;base64,", ""));
			long fileSize = b.length;
			if(fileSize == 0 || fileSize > maxsize){
				return new BaseResult(-1,"400","图片过大");
			}
			SysAttachment sysAttachment = new SysAttachment();
			sysAttachment.setFileName(fileName + ".png");
			InputStream inputStream = new ByteArrayInputStream(b);
			sysAttachment.setContentInputStream(inputStream);
			sysAttachment.setFileSize(fileSize);
			String fileId = attachmentService.saveAttachment(sysAttachment, moduleCode, folderId);

			//附件ID为空, 上传失败
			if(!StringUtil.hasText(fileId)) {
				return new BaseResult(-1,"400","截图保存出错");
			}

			fileIdList.add(fileId);
			
			/* 保存业务对象与附件关系 */
			for (String attachId : fileIdList) {
				SysAttachmentBusinessRelation businessRelation = new SysAttachmentBusinessRelation();
				businessRelation.setAttachmentId(attachId);
				businessRelation.setBusinessDataId(businessId);
				businessRelation.setBusinessType(businessType);
				businessRelation.setFileType(fileType);
				attachmentService.saveAttachmentBusinessRelation(businessRelation);
			}

		} catch (Exception e) {
			log.error("[截图保存出错]:{}", e);
			e.printStackTrace();
			return new BaseResult(-1,"400","截图保存出错");
		}
		return new ListResult<>(fileIdList);
		
	}
}
