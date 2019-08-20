package cn.jasgroup.hcas.pipelinemanage.query;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.jasgroup.hcas.pipelinemanage.query.bo.HcaPipelineBo;
import cn.jasgroup.jasframework.base.annotation.QueryConfig;
import cn.jasgroup.jasframework.base.data.BaseJavaQuery;

/**
 * @description 管线query类
 * @author zhangyi
 * @date 2019年1月15日上午11:38:29
 * @version V1.0
 * @since JDK 1.80
 */

@QueryConfig(scene = "/hcapipeline/getPage", resultClass = HcaPipelineBo.class)
public class HcaPipelineQuery extends BaseJavaQuery {

	/**
	 * oid
	 */
	private String oid;
	/**
	 * 管线名称
	 */
	private String pipelineName;
	/**
	 * 管线编号
	 */
	private String pipelineCode;

	private String keyWord;
	
	/**
	 * 数据oid集合
	 */
	private List<String> oids;

	@Override
	public String getQuerySql() {
		String sql = "select t.* from hca_pipeline t where t.active=1 ";
		if (StringUtils.isNotBlank(oid)) {
			sql += " and t.oid = :oid ";
		} else if (null != oids && oids.size() > 0) {
			sql += " and t.oid in (:oids) ";
		} else {
			if (StringUtils.isNotBlank(pipelineCode)) {
				sql += " and t.pipeline_code like :pipelineCode ";
			}
			if (StringUtils.isNotBlank(pipelineName)) {
				sql += " and t.pipeline_name like :pipelineName ";
			}
			if(StringUtils.isNotBlank(keyWord)){
				sql += " and (t.pipeline_code like :keyWord or t.pipeline_name like :keyWord) ";
			}
		}
		sql += " order by t.create_datetime desc";
		return sql;
	}

	public String getOid() {
		return oid;
	}
	
	public void setOid(String oid) {
		this.oid = oid;
	}
	
	public String getPipelineName() {
		if (StringUtils.isNotBlank(pipelineName)) {
			return '%' + pipelineName + '%';
		}
		return pipelineName;
	}

	public void setPipelineName(String pipelineName) {
		this.pipelineName = pipelineName;
	}

	public String getPipelineCode() {
		if (StringUtils.isNotBlank(pipelineCode)) {
			return '%' + pipelineCode + '%';
		}
		return pipelineCode;
	}

	public void setPipelineCode(String pipelineCode) {
		this.pipelineCode = pipelineCode;
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
	
	public List<String> getOids() {
		return oids;
	}

	public void setOids(List<String> oids) {
		this.oids = oids;
	}
}
