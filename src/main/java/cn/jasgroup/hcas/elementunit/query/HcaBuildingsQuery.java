package cn.jasgroup.hcas.elementunit.query;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.jasgroup.hcas.elementunit.query.bo.HcaBuildingsBo;
import cn.jasgroup.jasframework.base.annotation.QueryConfig;
import cn.jasgroup.jasframework.base.data.BaseJavaQuery;

/**
 * @description 构筑物query
 * @author zhangyi
 * @date 2019年1月15日下午2:40:12
 * @version V1.0
 * @since JDK 1.80
 */

@QueryConfig(scene = "/hcaelementunit/getPage", resultClass = HcaBuildingsBo.class)
public class HcaBuildingsQuery extends BaseJavaQuery {

	/**
	 * 数据oid集合
	 */
	private List<String> oids;
	/**
	 * 构筑物编号
	 */
	private String buildingCode;
	/**
	 * 构筑物类型
	 */
	private String buildingType;
	/**
	 * 建筑分布
	 */
	private String buildingDistribution;

	@Override
	public String getQuerySql() {
		String sql = "select t.oid,t.building_code,t.start_mileage,t.end_mileage,\n"
				+ "t.horizontal_distance,t.vertical_distance,t.pointx,t.pointy,t.building_type,t.building_distribution,\n"
				+ "d01.code_name as building_type_name, "
//				+ "d02.code_name as building_distribution_name,\n"
				+ "t.households,t.population,to_char(t.collect_date, 'yyyy-MM-dd') as collect_date,t.collect_person,t.remarks,\n"
				+ "t.create_datetime,t.create_user_id,t.create_user_name,t.modify_datetime,t.modify_user_id,t.modify_user_name\n"
				+ " from hca_buildings t\n"
				+ " LEFT JOIN (select code_id, code_name from sys_domain where active=1 and domain_name='building_type_domain' order by ordinal) d01\n"
				+ " on d01.code_id=t.building_type\n"
//				+ " LEFT JOIN (select code_id, code_name from sys_domain where active=1 and domain_name='building_distribution_domain' order by ordinal) d02\n"
//				+ " on d02.code_id=t.building_distribution\n"
				+ " where t.active=1";
		if (StringUtils.isNotBlank(buildingCode)) {
			sql += " and t.building_code like :elementCode ";
		}
		if (StringUtils.isNotBlank(buildingType)) {
			sql += " and t.building_type = :buildingType ";
		}
		if (StringUtils.isNotBlank(buildingDistribution)) {
			sql += " and t.building_distribution = :buildingDistribution ";
		}
		if (null != oids && oids.size() > 0) {
			sql += " and t.oid in (:oids) ";
		}
		sql += " order by t.create_datetime desc";
		return sql;
	}

	public List<String> getOids() {
		return oids;
	}

	public void setOids(List<String> oids) {
		this.oids = oids;
	}

	public String getBuildingDistribution() {
		return buildingDistribution;
	}

	public void setBuildingDistribution(String buildingDistribution) {
		this.buildingDistribution = buildingDistribution;
	}

	public String getBuildingCode() {
		if (StringUtils.isNotBlank(buildingCode)) {
			return '%' + buildingCode + '%';
		}
		return buildingCode;
	}

	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}

	public String getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	
}
