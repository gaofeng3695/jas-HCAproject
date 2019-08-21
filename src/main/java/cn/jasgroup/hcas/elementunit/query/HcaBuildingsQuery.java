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
	 * oid
	 */
	private String oid;
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
	 * 起始里程
	 */
	private double startMileage;
	/**
	 * 终止里程
	 */
	private double endMileage;

	private String keyWord;
	
	/**
	 * 类别
	 */
	private String buildingTypeParent;

	@Override
	public String getQuerySql() {
		String sql = "select t.oid,t.building_code,t.start_mileage,t.end_mileage,t.address,\n"
				+ "t.horizontal_distance,t.vertical_distance,t.pointx,t.pointy,t.building_type,t.building_distribution,\n"
				+ "d01.code_name as building_type_name, "
				+ "d02.code_name as building_distribution_name,"
				+ "d01.parent_code_name as building_type_parent_name,"
				+ "d01.parent_code_id as building_type_parent,"
				+ "t.households,t.population,to_char(t.collect_date, 'yyyy-MM-dd') as collect_date,t.collect_person,t.remarks,t.pressure_pipeline,\n"
				+ "t.create_datetime,t.create_user_id,t.create_user_name,t.modify_datetime,t.modify_user_id,t.modify_user_name\n"
				+ " from hca_buildings t\n"
//				+ " LEFT JOIN (select code_id, code_name from sys_domain where active=1 and domain_name='building_type_domain') d01\n"
				+ " LEFT JOIN ("
				+"select b.parent_code_name,a.parent_code_id,a.code_id, a.code_name from sys_domain a\n" +
				"inner JOIN\n" +
				"(select code_id, code_name as parent_code_name from sys_domain where active=1 and domain_name='building_type_parent_domain') b\n" +
				"on a.parent_code_id=b.code_id\n" +
				"where a.active=1 and a.domain_name='building_type_domain'"
				+ ") d01\n"
				+ " on d01.code_id=t.building_type\n"
				+ " LEFT JOIN (select code_id, code_name from sys_domain where active=1 and domain_name='building_distribution_domain') d02\n"
				+ " on d02.code_id=t.building_distribution\n"
				+ " where t.active=1";
		if (StringUtils.isNotBlank(oid)) {
			sql += " and t.oid = :oid ";
		}else {
			if (null != oids && oids.size() > 0) {
				sql += " and t.oid in (:oids) ";
			} else {
				if (StringUtils.isNotBlank(buildingCode)) {
					sql += " and t.building_code like :buildingCode ";
				}
				if (StringUtils.isNotBlank(buildingType)) {
					sql += " and t.building_type = :buildingType ";
				}else if (StringUtils.isNotBlank(buildingTypeParent)) {
					sql += " and d01.parent_code_id = :buildingTypeParent ";
				}
				if (startMileage != 0) {
					sql += " and t.start_mileage = :startMileage ";
				}
				if (endMileage != 0) {
					sql += " and t.end_mileage = :endMileage ";
				}
				if (StringUtils.isNotBlank(keyWord)) {
					sql += " and (t.building_code like :keyWord or d01.code_name like :keyWord) ";
				}
			}
			sql += " order by t.start_mileage asc";
		}
		return sql;
	}

	@Override
	public String getOid() {
		return oid;
	}

	@Override
	public void setOid(String oid) {
		this.oid = oid;
	}

	public double getStartMileage() {
		return startMileage;
	}

	public void setStartMileage(double startMileage) {
		this.startMileage = startMileage;
	}

	public double getEndMileage() {
		return endMileage;
	}

	public void setEndMileage(double endMileage) {
		this.endMileage = endMileage;
	}

	public List<String> getOids() {
		return oids;
	}

	public void setOids(List<String> oids) {
		this.oids = oids;
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

	public String getKeyWord() {
		if (StringUtils.isNotBlank(keyWord)) {
			return '%' + keyWord + '%';
		}
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getBuildingTypeParent() {
		return buildingTypeParent;
	}

	public void setBuildingTypeParent(String buildingTypeParent) {
		this.buildingTypeParent = buildingTypeParent;
	}
	
}
