package cn.jasgroup.jasframework.acquisitiondata.material.appendages.markstake.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.SimpleResult;
import cn.jasgroup.jasframework.acquisitiondata.material.appendages.markstake.query.DaqAppendagesMarkStakeQuery;
import cn.jasgroup.jasframework.acquisitiondata.material.appendages.markstake.query.bo.DaqAppendagesMarkStakeBo;
import cn.jasgroup.jasframework.acquisitiondata.material.appendages.markstake.service.DaqAppendagesMarkStakeService;
import cn.jasgroup.jasframework.base.controller.BaseController;

/**
 * @description 标志桩
 * @author zhangyi
 * @date 2018年7月21日上午9:33:49
 * @version V1.0
 * @since JDK 1.80
 */

@RestController
@RequestMapping("daq/appendagesMarkStake")
public class DaqAppendagesMarkStakeController extends BaseController{

	@Autowired
	private DaqAppendagesMarkStakeService stakeService;
	
	/**
	 * <p>功能描述：数据审核。</p>
	 * <p>张毅 </p>	
	 * @param request
	 * @param paramMap	<idList, 数据oid集合>, <approveStatus, 数据状态>
	 * @return
	 * @since JDK1.8。
	 * <p>创建日期:2018年7月11日 下午4:56:20。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/approve")
	public Object approve(HttpServletRequest request , @RequestBody Map<String, Object> paramMap){
		BaseResult result = null;
		try {
			List<String> idList = (List<String>) paramMap.get("idList");
			Integer approveStatus = (Integer) paramMap.get("approveStatus");
			if(null == idList || null == approveStatus){
				result = new SimpleResult<Boolean>(-1, "400", "error");
			}
			Boolean b = this.stakeService.approve(paramMap);
			if(b){
				result = new SimpleResult<Boolean>(b);
			}else{
				result = new SimpleResult<Boolean>(-1, "400", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new SimpleResult<Boolean>(-1, "500", "error");
		}
		return result;
	}
	
	/**
	 * <p>功能描述：获取详情信息。</p>
	 * <p>张毅 </p>	
	 * @param request
	 * @param oid
	 * @return
	 * @since JDK1.8。
	 * <p>创建日期:2018年7月16日 下午3:01:41。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@GetMapping("/get")
	public Object get(HttpServletRequest request, DaqAppendagesMarkStakeQuery query){
		BaseResult result = null;
		try {
			if(StringUtils.isBlank(query.getOid())){
				result = new SimpleResult<Boolean>(-1, "400", "error");
			}
			DaqAppendagesMarkStakeBo bo = this.stakeService.get(query);
			result = new SimpleResult<DaqAppendagesMarkStakeBo>(bo);
		} catch (Exception e) {
			e.printStackTrace();
			result = new SimpleResult<String>(-1, "500", "error");
		}
		return result;
	}
}
