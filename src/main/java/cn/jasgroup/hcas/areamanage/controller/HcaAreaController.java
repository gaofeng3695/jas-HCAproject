package cn.jasgroup.hcas.areamanage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import cn.jasgroup.hcas.areamanage.service.HcaAreaService;
import cn.jasgroup.jasframework.base.controller.BaseController;

/**
 * @description 地区等级区域
 * @author zhangyi
 * @date 2019年1月16日下午3:17:28
 * @version V1.0
 * @since JDK 1.80
 */

@RestController("/hcaarea")
public class HcaAreaController extends BaseController{

	@Autowired
	private HcaAreaService hcaAreaService;
	
	
}
