package cn.jasgroup.hcas.pipelinemanage.service;

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
import cn.jasgroup.hcas.pipelinemanage.query.HcaPipelineQuery;
import cn.jasgroup.jasframework.engine.jdbc.service.CommonDataJdbcService;

/**
 * @description 管线
 * @author zhangyi
 * @date 2019年8月23日下午9:17:48
 * @version V1.0
 * @since JDK 1.80
 */

@Service
public class HcaPipelineService extends CommonDataJdbcService{

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
	public List<Map<String, String>> queryGeometryList(HcaPipelineQuery query){
		List<Map<String, String>> list = new ArrayList<>();
		try {
			LayerQueryParam layerQueryParam = new LayerQueryParam();
			layerQueryParam.setSrsname("hca_pipeline");
			StringBuilder where= new StringBuilder("");
			List<String> oids = query.getOids();
			if (null != oids && oids.size() > 0) {
				int oidsSize = oids.size();
				where.append(" oid in (");
				for(int i=0; i<oidsSize; i++){
					if(i < oidsSize-1){
						where.append("'"+ oids.get(i) +"',");
						continue;
					}
					where.append("'"+ oids.get(i) +"'");
				}
				where.append(")");
			} else {
				String pipelineName = query.getPipelineName();
				String code = query.getPipelineCode();
				if (StringUtils.isNotBlank(pipelineName)) {
					where.append(" pipeline_name like '" + pipelineName + "' ");
				}
				if (StringUtils.isNotBlank(code)) {
					if(where.length() > 0){
						where.append(" and ");
					}
					where.append(" pipeline_code like '" + code + "' ");
				}
			}
			layerQueryParam.setWhere(where.toString());
			layerQueryParam.setOrderBy(" create_datetime desc");
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
