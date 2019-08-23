package cn.jasgroup.hcas.highimpactarea.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.data.FeatureCollection;
import cn.jasgroup.gis.dataaccess.LayerQueryParam;
import cn.jasgroup.gis.dataaccess.arcgis.GeodataAccessService;
import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.util.GeometryUtil;
import cn.jasgroup.hcas.highimpactarea.query.HcaHighImpactAreaQuery;
import cn.jasgroup.jasframework.engine.jdbc.service.CommonDataJdbcService;

/**
 * @description 高后果区
 * @author zhangyi
 * @date 2019年8月23日下午8:51:01
 * @version V1.0
 * @since JDK 1.80
 */

@Service
public class HcaHighService extends CommonDataJdbcService{

    @Autowired
    private GeodataAccessService geodataAccessService;
    
	/**
	 *<p>功能描述：导出Geometry。</p>
	 * <p> 张毅 </p>	
	 * @param query
	 * @return
	 * @since JDK1.8。
	 * <p>创建日期:2019年8月23日 下午8:06:30。</p>
	 * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public List<Map<String, String>> queryGeometryList(HcaHighImpactAreaQuery query){
		List<Map<String, String>> list = new ArrayList<>();
		try {
			LayerQueryParam layerQueryParam = new LayerQueryParam();
			layerQueryParam.setSrsname("hca_high_impact_area");
			StringBuilder where= new StringBuilder("");
			List<String> oids = query.getOids();
			if (null != oids && oids.size() > 0) {
				int oidsSize = oids.size();
				where.append(" oid in (");
				for(int i=0; i<oidsSize; i++){
					if(i < oidsSize-1){
						where.append(" '"+ oids.get(i) +"',");
						continue;
					}
					where.append(" '"+ oids.get(i) +"'");
				}
				where.append(")");
			} else {
				String code = query.getHighImpactAreaCode();
				String level = query.getHighImpactLevel();
				String versionOid = query.getVersionOid();
				if (StringUtils.isNotBlank(versionOid)) {
					where.append(" version_oid = '" + versionOid + "' ");
				}
				if (StringUtils.isNotBlank(code)) {
					where.append(" and high_impact_area_code like '" + code + "' ");
				}
				if (StringUtils.isNotBlank(level)) {
					where.append(" and high_impact_level = '" + level + "' ");
				}
			}
			layerQueryParam.setWhere(where.toString());
			layerQueryParam.setOrderBy(" start_mileage asc");
			layerQueryParam.setOutFields("oid");
			FeatureCollection COL =geodataAccessService.query(layerQueryParam);
			List<Feature> f = COL.getFeatures();
			if(null != f){
				int size = f.size();
				for(int i=0; i<size; i++){
					Geometry g = f.get(i).getGeometry();
					String text = GeometryUtil.toText(g);
					Map<String, String> map = new HashMap<>();
					map.put("shape", text);
					list.add(map);
				}
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		
		return list;
	}
}
