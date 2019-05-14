package cn.jasgroup.hcas.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.ListResult;
import cn.jasgroup.hcas.common.service.HcaCommonService;
import cn.jasgroup.jasframework.base.controller.BaseController;

/**
 * @description 公共controller
 * @author zhangyi
 * @date 2019年1月16日下午3:29:02
 * @version V1.0
 * @since JDK 1.80
 */

@RestController
@RequestMapping("/hcacommon")
public class HcaCommonController extends BaseController {

	@Autowired
	private HcaCommonService HcaCommonService;

	 /**
	  *<p>功能描述：获取当前用户部门下的项目下拉选列表。</p>
	  * <p> 张毅 </p>	
	  * @param request
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2019年1月16日 下午5:04:40。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@RequestMapping("/getProjectList")
	public BaseResult getProjectList(HttpServletRequest request) {
		ListResult<Map<String,Object>> result = null;
		
		try {
			List<Map<String,Object>> list = this.HcaCommonService.getProjectList();
			result = new ListResult<>(1, "200", "ok", list);
		} catch (Exception e) {
			result = new ListResult<>(-1, "500", "error");
			e.printStackTrace();
		}
		
		return result;

	}

	 /**
	  *<p>功能描述：获取项目下的管线下拉选列表。</p>
	  * <p> 张毅 </p>	
	  * @param request
	  * @param projectOid
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2019年1月16日 下午5:04:07。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@RequestMapping("/getPipelineList")
	public BaseResult getPipelineList(HttpServletRequest request, @RequestParam String projectOid) {
		ListResult<Map<String,Object>> result = null;
		if(StringUtils.isBlank(projectOid)){
			return new ListResult<>(-1, "400", "error"); 
		}
		try {
			List<Map<String,Object>> list = this.HcaCommonService.getPipelineList(projectOid);
			result = new ListResult<>(1, "200", "ok", list);
		} catch (Exception e) {
			result = new ListResult<>(-1, "500", "error");
			e.printStackTrace();
		}
		return result;

	}
}
