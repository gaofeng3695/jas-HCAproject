package cn.jasgroup.hcas.highimpactarea.query;

import org.apache.commons.lang.StringUtils;

import cn.jasgroup.hcas.areamanage.query.bo.HcaAreaBo;
import cn.jasgroup.jasframework.base.annotation.QueryConfig;
import cn.jasgroup.jasframework.base.data.BaseJavaQuery;

/**
 * @description TODO
 * @author zhangyi
 * @date 2019年6月25日下午6:15:36
 * @version V1.0
 * @since JDK 1.80
 */

@QueryConfig(scene = "/hcarankcell/getPage", resultClass = HcaAreaBo.class)
public class HcaPdZoneRankCell extends BaseJavaQuery{

	/**
	 * oid
	 */
	private String oid;
	
	/**
	 * 编号
	 */
	private String areaCode;
	
	@Override
	public String getQuerySql() {
		String sql = "select t.oid,t.name as areaCode,\n"
				+ "t.rank as region_level_name,\n"
				+ "t.description,\n"
				+ "t.start_mileage,t.end_mileage\n"
				+ " from pd_zonerankcell t\n"
				+ " where 1=1 ";
		if (StringUtils.isNotBlank(oid)) {
			sql += " and t.oid = :oid ";
		}else{
			if (StringUtils.isNotBlank(areaCode)) {
				sql += " and t.area_code like :areaCode ";
			}
		}
		sql +=" order by t.start_mileage asc";
		return sql;
	}

	public String getAreaCode() {
		if (StringUtils.isNotBlank(areaCode)) {
			return '%' + areaCode + '%';
		}
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
}
