package cn.jasgroup.hcas.elementunit.query;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.jasgroup.hcas.elementunit.query.bo.HcaElementUnitBo;
import cn.jasgroup.jasframework.base.annotation.QueryConfig;
import cn.jasgroup.jasframework.base.data.BaseJavaQuery;

/**
 * @description 要素单元query
 * @author zhangyi
 * @date 2019年1月15日下午2:40:12
 * @version V1.0
 * @since JDK 1.80
 */

@QueryConfig(scene = "/hcaelementunit/getPage", resultClass = HcaElementUnitBo.class)
public class HcaElementUnitQuery extends BaseJavaQuery {

	/**
	 * 数据oid集合
	 */
	private List<String> oids;
	/**
	 * 项目oid
	 */
	private String projectOid;
	/**
	 * 管线oid
	 */
	private String pipelineOid;
	/**
	 * 要素编号
	 */
	private String elementCode;
	/**
	 * 要素单元类型
	 */
	private String elementType;
	/**
	 * 建筑分布
	 */
	private String buildingDistribution;

	@Override
	public String getQuerySql() {
		String sql = "select t.oid,t.project_oid,pro.project_name,t.pipeline_oid,pip.pipeline_name, t.element_code,t.start_mileage,t.end_mileage,\n"
				+ "t.horizontal_distance,t.vertical_distance,t.pointx,t.pointy,t.element_type,t.building_distribution,\n"
				+ "d01.code_name as element_type_name, d02.code_name as building_distribution_name,\n"
				+ "t.number_of_households,t.population,to_char(t.collect_date, 'yyyy-MM-dd') as collect_date,t.collect_person,t.remarks,\n"
				+ "t.create_datetime,t.create_user_id,t.create_user_name,t.modify_datetime,t.modify_user_id,t.modify_user_name\n"
				+ " from hca_element_unit t\n"
				+ " LEFT JOIN (select code_id, code_name from sys_domain where active=1 and domain_name='element_type_domain' order by ordinal) d01\n"
				+ " on d01.code_id=t.element_type\n"
				+ " LEFT JOIN (select code_id, code_name from sys_domain where active=1 and domain_name='building_distribution_domain' order by ordinal) d02\n"
				+ " on d02.code_id=t.building_distribution\n"
				+ " LEFT JOIN (select oid,pipeline_name from hca_pipeline where active=1) pip\n"
				+ " on t.pipeline_oid = pip.oid\n"
				+ " LEFT JOIN (select oid,project_name,enterprise_id from hca_project where active=1) pro\n"
				+ " on t.project_oid = pro.oid\n"
				+ " where t.active=1 and t.is_history=0 ";
		
		if (StringUtils.isNotBlank(projectOid)) {
			sql += " and t.project_oid = :projectOid ";
		}
		if (StringUtils.isNotBlank(pipelineOid)) {
			sql += " and t.pipeline_oid = :pipelineOid ";
		}
		if (StringUtils.isNotBlank(elementCode)) {
			sql += " and t.element_code like :elementCode ";
		}
		if (StringUtils.isNotBlank(elementType)) {
			sql += " and t.element_type = :elementType ";
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

	public String getProjectOid() {
		return projectOid;
	}

	public void setProjectOid(String projectOid) {
		this.projectOid = projectOid;
	}

	public String getPipelineOid() {
		return pipelineOid;
	}

	public void setPipelineOid(String pipelineOid) {
		this.pipelineOid = pipelineOid;
	}

	public String getElementCode() {
		if (StringUtils.isNotBlank(elementCode)) {
			return '%' + elementCode + '%';
		}
		return elementCode;
	}

	public void setElementCode(String elementCode) {
		this.elementCode = elementCode;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public String getBuildingDistribution() {
		return buildingDistribution;
	}

	public void setBuildingDistribution(String buildingDistribution) {
		this.buildingDistribution = buildingDistribution;
	}

}
