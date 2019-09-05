package cn.jasgroup.hcas.versionmaanage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jasgroup.hcas.versionmaanage.dao.HcaVersionDao;
import cn.jasgroup.hcas.versionmaanage.dao.entity.HcaVersion;
import cn.jasgroup.jasframework.engine.jdbc.dao.CommonDataJdbcDao;
import cn.jasgroup.jasframework.engine.jdbc.service.CommonDataJdbcService;
import cn.jasgroup.jasframework.utils.DateTimeUtil;

/**
 * @description：TODO
 * @author：chenxiangsi
 * @date：Jun 21, 2019 10:58:50 AM
 * @version：V1.0
 * @since：JDK 1.80
 */

@Service
@Transactional
public class HcaVersionService extends CommonDataJdbcService {
	
	@Autowired
	HcaVersionDao versionDao;
	@Autowired
	private CommonDataJdbcDao commonDataJdbcDao;
	/**
	 * @description：TODO
	 * @parameter：
	 * @return：
	 * @author：chenxiangsi
	 * @date：Jun 21, 2019 11:24:58 AM
	 */
	public boolean updateVersionUsedStatus(String oid,Integer forBusiness) {
		// TODO Auto-generated method stub
		try {
			HcaVersion versionUsed = versionDao.getVersionByOid(oid);
			versionUsed.setHasUsed(1);
			commonDataJdbcDao.update(versionUsed);
			List<HcaVersion> versionList = new ArrayList<>();
			versionList = versionDao.getVersionListUsed(oid,forBusiness);
			for(HcaVersion version : versionList){
				version.setHasUsed(0);
				commonDataJdbcDao.update(version);
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public int initToSave(String oid ,String pipelineOid ,int bussiness){
		HcaVersion entity = new HcaVersion();
		Date now = new Date();
		String code = DateTimeUtil.getFormatDate(now, "yyyyMMddHHmmss");
		String name = "未命名-" + DateTimeUtil.getFormatDate(now, "yyyyMMddHHmmss");
		entity.setOid(oid);
		entity.setVersionCode(code);
		entity.setVersionName(name);
		entity.setPipelineOid(pipelineOid);
		entity.setForBusiness(bussiness);
		return commonDataJdbcDao.save(entity);
	}
}
