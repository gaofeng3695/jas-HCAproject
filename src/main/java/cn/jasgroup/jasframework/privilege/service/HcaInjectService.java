package cn.jasgroup.jasframework.privilege.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cn.jasgroup.jasframework.base.data.BaseJavaQuery;
import cn.jasgroup.jasframework.security.dao.entity.PriUnit;
import cn.jasgroup.jasframework.security.service.UnitService;
import cn.jasgroup.jasframework.support.ThreadLocalHolder;

/**
 * @description 权限定义
 * @author zhangyi
 * @date 2019年8月22日上午9:31:07
 * @version V1.0
 * @since JDK 1.80
 */

@Service
@Transactional
public class HcaInjectService {
	
	@Resource
	private UnitService unitService;

	/**
	 *<p>功能描述：用户企业权限，同企业可查看数据。</p>
	 * <p> 张毅 </p>	
	 * @param query
	 * @param dataAuthoritySql
	 * @since JDK1.8。
	 * <p>创建日期:2019年8月22日 上午9:37:12。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public void injectDataAuthoritySql(BaseJavaQuery query,String dataAuthoritySql){
		// 同一企业数据
		String unitOid = ThreadLocalHolder.getCurrentUser().getUnitId();
		PriUnit unitEntity = (PriUnit)unitService.get(PriUnit.class,unitOid);
		if(unitEntity==null){
			dataAuthoritySql = "";
			return;
		}
		// 根据同一企业下
		String hierarchy = unitEntity.getHierarchy();
		dataAuthoritySql = " and t.create_user_id in ("
				+ "select oid from pri_user where unit_id in(select oid from pri_unit where hierarchy like "
				+ "'" + hierarchy + "%') )";
		query.setDataAuthoritySql(dataAuthoritySql);
	}
}
