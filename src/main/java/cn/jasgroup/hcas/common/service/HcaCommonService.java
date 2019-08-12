package cn.jasgroup.hcas.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jasgroup.gis.util.StringUtil;
import cn.jasgroup.hcas.common.dao.HcaCommonDao;
import cn.jasgroup.jasframework.domain.entity.SysDomain;
import cn.jasgroup.jasframework.engine.jdbc.service.CommonDataJdbcService;
import cn.jasgroup.jasframework.support.ThreadLocalHolder;

/**
 * @description 公共service
 * @author zhangyi
 * @date 2019年1月16日下午3:35:00
 * @version V1.0
 * @since JDK 1.80
 */

@Service
@Transactional
public class HcaCommonService extends CommonDataJdbcService {

	@Autowired
	private HcaCommonDao hcaCommonDao;
	
	private Map<String,List<SysDomain>> parentDomainMap = null ;

	 /**
	  *<p>功能描述：获取当前用户部门下的项目下拉选列表。</p>
	  * <p> 张毅 </p>	
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2019年1月16日 下午5:05:25。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	public List<Map<String, Object>> getProjectList() {

		String unitId = ThreadLocalHolder.getCurrentUnitId();
		return hcaCommonDao.getProjectList(unitId);
	}
	
	 /**
	  *<p>功能描述：获取项目下的管线下拉选列表。</p>
	  * <p> 张毅 </p>	
	  * @param projectOid
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2019年1月16日 下午5:05:53。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	public List<Map<String, Object>> getPipelineList(String projectOid) {
		
		return hcaCommonDao.getPipelineList(projectOid);
	}
	
	public String getCodeIdsByParentCodeId(String parentCodeId){
		Map<String,List<SysDomain>> map = getParentDomainMap() ;
		List<SysDomain> sysDomains = map.get(parentCodeId) ;
		if(sysDomains == null || sysDomains.size() == 0){
			return null ;
		}
		StringBuffer buffer = new StringBuffer() ;
		for(int i = 0 ; i < sysDomains.size(); i++){
			buffer.append("'");
			buffer.append(sysDomains.get(i).getCodeId());
			buffer.append("'");
			buffer.append(",");
		}
		buffer.deleteCharAt(buffer.length() - 1) ;
		return buffer.toString();
	}

	public Map<String, List<SysDomain>> getParentDomainMap() {
		if(parentDomainMap == null ) {
			parentDomainMap = new HashMap<>() ;
			List<SysDomain> list = hcaCommonDao.getAllSysDomains();
			for(int i = 0 ; i < list.size() ;i++){
				SysDomain sysDomain = list.get(i) ;
				String parentCodeId = sysDomain.getParentCodeId();
				if(StringUtil.isBlank(parentCodeId) ){
					continue;
				}
				if(parentDomainMap.containsKey(parentCodeId)){
					List<SysDomain> sysDomains = parentDomainMap.get(parentCodeId);
					sysDomains.add(sysDomain);
				}else{
					List<SysDomain> sysDomains = new ArrayList<>();
					sysDomains.add(sysDomain);
					parentDomainMap.put(parentCodeId,sysDomains);
				}
			}
		}
		return parentDomainMap;
	}

	public void setParentDomainMap(Map<String, List<SysDomain>> parentDomainMap) {
		this.parentDomainMap = parentDomainMap;
	}
}
