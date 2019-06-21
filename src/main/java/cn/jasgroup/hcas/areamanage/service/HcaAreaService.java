package cn.jasgroup.hcas.areamanage.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jasgroup.hcas.areamanage.dao.entity.HcaArea;
import cn.jasgroup.hcas.versionmaanage.dao.entity.HcaVersion;
import cn.jasgroup.jasframework.dataaccess.base.BaseJdbcDao;
import cn.jasgroup.jasframework.engine.jdbc.dao.CommonDataJdbcDao;
import cn.jasgroup.jasframework.engine.jdbc.service.CommonDataJdbcService;

/**
 * @description 地区等级区域
 * @author zhangyi
 * @date 2019年1月15日下午5:47:38
 * @version V1.0
 * @since JDK 1.80
 */

@Service
@Transactional
public class HcaAreaService extends CommonDataJdbcService {
	
	@Autowired
	private CommonDataJdbcDao commonDataJdbcDao;
	
	@Autowired
	private BaseJdbcDao baseJdbcDao;

	public void saveRef(HcaArea hcaArea){
		String pipelineId = hcaArea.getPipelineOid();
		// 识别单元id集合
		List<String> idList = hcaArea.getUnitIdList();
		
	}
	
	public void saveAndUpdateArea(){
		HcaVersion version = new HcaVersion();
		String sql = "update hca_version set has_used=0 where for_business=0 and active=1";
		baseJdbcDao.update(sql, null);
		String pipelineOid = "aa64052b-671e-4d45-989e-0d67658d47c5";
		version.setPipelineOid(pipelineOid);
		long time = System.currentTimeMillis();
		version.setVersionName("启用" + time);
		version.setVersionCode("test" + time);
		version.setHasUsed(1);
		version.setForBusiness(0);
		
		commonDataJdbcDao.save(version);
	}
	public void saveAndUpdateHigh(){
		HcaVersion version = new HcaVersion();
		String sql = "update hca_version set has_used=0 where for_business=1 and active=1";
		baseJdbcDao.update(sql, null);
		String pipelineOid = "aa64052b-671e-4d45-989e-0d67658d47c5";
		version.setPipelineOid(pipelineOid);
		long time = System.currentTimeMillis();
		version.setVersionName("启用" + time);
		version.setVersionCode("test" + time);
		version.setHasUsed(1);
		version.setForBusiness(1);
		
		commonDataJdbcDao.save(version);
	}
}
