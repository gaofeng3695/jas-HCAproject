package cn.jasgroup.hcas.versionmaanage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.SimpleResult;
import cn.jasgroup.hcas.versionmaanage.query.bo.HcaVersionBo;
import cn.jasgroup.hcas.versionmaanage.service.HcaVersionService;
import cn.jasgroup.jasframework.base.controller.BaseController;

/**
 * @description：TODO
 * @author：chenxiangsi
 * @date：Jun 21, 2019 10:58:50 AM
 * @version：V1.0
 * @since：JDK 1.80
 */

@Controller
@RequestMapping("/hcaversion")
public class HcaVersionController  extends BaseController{
	@Autowired
	private HcaVersionService versionService;
	
	/**
	 * 
	 * @description：更新启用状态
	 * @parameter：
	 * @return：
	 * @author：chenxiangsi
	 * @date：Jun 21, 2019 11:08:28 AM
	 */
	@RequestMapping(value="/updateUsed", method = RequestMethod.POST)
    @ResponseBody	
    public Object getDaqMvPipeNodeList(HttpServletRequest request, @RequestBody Map<String,String> param) throws Exception {
		String oid = param.get("oid");
		Integer forBusiness = Integer.valueOf(param.get("forBusiness"));
		boolean flag = versionService.updateVersionUsedStatus(oid,forBusiness);
        return flag;
		
	}
}
