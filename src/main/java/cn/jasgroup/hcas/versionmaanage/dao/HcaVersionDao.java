package cn.jasgroup.hcas.versionmaanage.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.jasgroup.hcas.versionmaanage.dao.entity.HcaVersion;
import cn.jasgroup.jasframework.dataaccess.base.BaseJdbcDao;

/**
 * @description：TODO
 * @author：chenxiangsi
 * @date：Jun 21, 2019 10:58:50 AM
 * @version：V1.0
 * @since：JDK 1.80
 */
@Repository
public class HcaVersionDao extends BaseJdbcDao {
	@Autowired
	BaseJdbcDao baseJdbcDao;
	/**
	 * @description：TODO
	 * @parameter：
	 * @return：
	 * @author：chenxiangsi
	 * @date：Jun 21, 2019 11:32:08 AM
	 */
	public List<HcaVersion> getVersionListUsed(String oid) {
		// TODO Auto-generated method stub
        List<String> args = new ArrayList<>();
		String sql = "select *from hca_version v where v.has_used=1 and v.active=1";
        if(StringUtils.isNotBlank(oid)){
			sql += " and v.oid <> '"+oid+"'";
		}
		List<HcaVersion> versionList = baseJdbcDao.queryForList(sql, null, HcaVersion.class);
		return versionList;
	}
	/**
	 * @description：TODO
	 * @parameter：
	 * @return：
	 * @author：chenxiangsi
	 * @date：Jun 21, 2019 11:54:19 AM
	 */
	public HcaVersion getVersionByOid(String oid) {
		// TODO Auto-generated method stub
		String sql = "select *from hca_version where oid='"+oid+"'";
		HcaVersion version = (HcaVersion) baseJdbcDao.queryForObject(sql, null, HcaVersion.class);
		return version;
	}

}
