package cn.jasgroup.hcas.analysis.service;
import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.data.FeatureCollection;
import cn.jasgroup.gis.data.GeometryType;
import cn.jasgroup.gis.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.dataaccess.LayerQueryParam;
import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.geometry.Polygon;
import cn.jasgroup.gis.geometry.Polyline;
import cn.jasgroup.gis.geometryservice.IGeometryService;
import cn.jasgroup.gis.util.*;
import cn.jasgroup.hcas.analysis.*;
import cn.jasgroup.hcas.common.service.HcaCommonService;
import cn.jasgroup.hcas.versionmaanage.dao.entity.HcaVersion;
import cn.jasgroup.hcas.versionmaanage.query.HcaVersionQuery;
import cn.jasgroup.hcas.versionmaanage.service.HcaVersionService;
import cn.jasgroup.jasframework.dataaccess.base.BaseJdbcDao;
import cn.jasgroup.jasframework.engine.jdbc.dao.CommonDataJdbcDao;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/6/21
 * @since JDK 1.80
 */
@Service
public class HighImpactAnalysisService implements IHighImpactAnalysisService {

    /**
     *
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     */
    protected LoggerUtil loggerUtil = new LoggerUtil(logger) ;

    @Resource
    private IGeodataAccessService geodataAccessService;

    @Resource
    private IGeometryService geometryService;

    @Autowired
    private PipelineService pipelineService ;

    @Autowired
    private HcaCommonService hcaCommonService;

    @Autowired
    private CommonDataJdbcDao commonDataJdbcDao;

    @Autowired
    private BaseJdbcDao baseJdbcDao;

    @Autowired
    private HcaVersionService hcaVersionService;


    private DecimalFormat codeDf = new DecimalFormat("000000");

    /**
     *
     * @param areaVersionCode 地区等级版本ID
     * @return
     */
    @Override
    public HcaAnalysisResult executeAnalysis(String areaVersionCode) {

        HcaAnalysisResult hcaAnalysisResult = new HcaAnalysisResult();

        loggerUtil.time("执行高后果区分析");
        //1、查询版本数据
        HcaVersion version = queryHcaVersion(areaVersionCode) ;
        String areaVersionOid = version.getOid();
        if(version == null ){
            hcaAnalysisResult.setTotal(0);
            return hcaAnalysisResult;
        }
        //2、查询管线基本信息
        String pipelineOid = version.getPipelineOid() ;
        HcaLinearParam bo = preparePipelineData(pipelineOid);
        //3、查询地区等级数据
        Feature[] areaData = queryHcaAreaByVersionId(areaVersionOid);
        //4、执行分类
        String hcaVersionId = UUID.randomUUID().toString() ;
        Feature[] hcaData = classifyHighConsequenceArea(areaData ,bo,hcaVersionId);
        //5、保存数据
        int saveCount = saveHcaResult(hcaData,areaVersionOid, hcaVersionId ,pipelineOid);
        hcaAnalysisResult.setTotal( saveCount);
        hcaAnalysisResult.setFeatures( hcaData );

        loggerUtil.timeEnd("执行高后果区分析");

        return hcaAnalysisResult;
    }

    /**
     * 划分高后果区
     * @param areaData
     * @param bo
     * @return
     */
    @Override
    public Feature[] classifyHighConsequenceArea(Feature[] areaData, HcaLinearParam bo , String hcaVersionOid) {
        //
        Feature[] hcaFeatures = areaGradeAnalysis(areaData ,hcaVersionOid ,bo.getPipelineOid());//根据地区等级数据划分出高后果区单元
        //保存易燃易爆数据
        BuildingLocationMap explosiveLocationMap = explosivePlaceAnalysis(areaData,hcaFeatures,bo);
        //保存特定场所数据
        BuildingLocationMap specialLocationMap = specialPlaceAnalysis(areaData,hcaFeatures,bo);
        //高后果区单元边界处理
        //..
        return mergeHcaFeatures(hcaFeatures ,explosiveLocationMap,specialLocationMap);
    }

    /**
     *
     * @param pressure
     * @param outerDimension
     * @return
     */
    @Override
    public double calculatePotentialInfluenceBufferDistance(double pressure, double outerDimension) {
        double r = 0d ;
        if( outerDimension <= 762 && outerDimension >= 273 && pressure <= 6.9 && pressure >= 1.6 ){
            r = 200 ;
        }else{
            r = 0.099 * Math.sqrt( outerDimension * outerDimension * pressure );
        }
        return r;
    }

    /**
     * 判定潜在影响区域有无特定场所，返回相交查询结果要素
     *
     * @param hcaFeatures
     * @param bo
     * @return
     */
    @Override
    public BuildingLocationMap specialPlaceAnalysis(Feature[] areaFeatures ,Feature[] hcaFeatures , HcaLinearParam bo) {
        LinearReferenceUtil linearReferenceUtil = bo.getLinearReferenceUtil();
        String remarkFieldName = HcaAnalysisContext.hcaRemarkFieldName;
        String rankFieldName = HcaAnalysisContext.areaRankFieldName;
        String hcaRankFieldName = HcaAnalysisContext.hcaRankFieldName;
        String oidFieldName = HcaAnalysisContext.TableKeyName;

        int outSrid = bo.getPipeline() .getSpatialReference().getWkid();
        int inSrid = bo.getPotentialInfluenceBuffer().getSpatialReference().getWkid();
        double pressure = bo.getPressure();
        double dimension = bo.getOuterDimension();
        double buffer = calculatePotentialInfluenceBufferDistance(pressure,dimension);

        BuildingLocationMap result = new BuildingLocationMap() ;

        for(int i = 0 ; i < areaFeatures.length ; i++){
            Map<String,Object> props = areaFeatures[i].getAttributes();
            Double startMileage = MapUtil.getDouble(props , HcaAnalysisContext.startMileageFieldName) * 1000  ;
            Double endMileage = MapUtil.getDouble(props , HcaAnalysisContext.endMileageFieldName) * 1000   ;
            Polyline polyline = linearReferenceUtil.locateBetween( startMileage,endMileage,0d);
            Geometry bufferArea = JtsUtil.buffer( polyline,buffer,2) ;
            Geometry polygon = geometryService.transform( bufferArea,  inSrid ,outSrid);
            hcaFeatures[i].setGeometry(polygon);

            int rank = MapUtil.getInt( props ,rankFieldName);
            if(rank == HcaAnalysisContext.AreaGradeLevel_III || rank == HcaAnalysisContext.AreaGradeLevel_IV){
                continue;
            }
            String bufferAreaText = polygon.toGeoJSON();
            LayerQueryParam queryParam = new LayerQueryParam();
            queryParam.setSrsname(HcaAnalysisContext.buildingsSourceName);
            queryParam.setGeometry(bufferAreaText);
            queryParam.setGeometryType(GeometryType.POLYLINE.name());
            queryParam.setOutputFormat(null);
            queryParam.setWhere("active=1 and " + getSpecialQueryExpress());
            FeatureCollection<Feature> featureCollection = geodataAccessService.query(queryParam) ;
            Feature[] features = featureCollection.getFeatures().toArray(new Feature[0]);
            if(features .length > 0){
                Map<String,Object> attribute = hcaFeatures[i].getAttributes();
                String desc = MapUtil.getString(attribute,remarkFieldName);
                String oid = MapUtil.getString(attribute,oidFieldName);
                desc += "潜在影响区内含有" + features.length + "处特定场所";
                attribute.put(remarkFieldName,desc);
                putHcaLevelByPipeParams( attribute ,pressure,dimension ,hcaRankFieldName);
                result.putFeatures(oid,features);
            }
        }
        return result;
    }

    /**
     *
     * @param props
     * @param pressure
     * @param outerDimension
     * @param hcaRankFieldName
     */
    private void putHcaLevelByPipeParams( Map props ,double pressure ,double outerDimension,String hcaRankFieldName){
        if(outerDimension > 762 && pressure > 6.9){
            props.put(hcaRankFieldName, HcaAnalysisContext.HcaLevel_II);
        }else{
            props.put(hcaRankFieldName, HcaAnalysisContext.HcaLevel_I);
        }
    }

    /**
     *
     * @param hcaFeatures
     * @param bo
     * @return
     */
    @Override
    public BuildingLocationMap explosivePlaceAnalysis(Feature[] areaFeatures , Feature[] hcaFeatures, HcaLinearParam bo) {
        LinearReferenceUtil linearReferenceUtil = bo.getLinearReferenceUtil();
        Double buffer = HcaAnalysisContext.ConfigRankAreaBufferDistance;
        String remarkFieldName = HcaAnalysisContext.hcaRemarkFieldName;
        String rankFieldName = HcaAnalysisContext.areaRankFieldName;
        String hcaRankFieldName = HcaAnalysisContext.hcaRankFieldName;
        String oidFieldName = HcaAnalysisContext.TableKeyName;

        BuildingLocationMap result = new BuildingLocationMap();

        for(int i = 0 ; i < areaFeatures.length ; i++){
            Map<String,Object> props = areaFeatures[i].getAttributes();
            Double startMileage = MapUtil.getDouble(props , HcaAnalysisContext.startMileageFieldName) ;
            Double endMileage = MapUtil.getDouble(props , HcaAnalysisContext.endMileageFieldName) ;
            int rank = MapUtil.getInt(props ,rankFieldName);
            if(rank == HcaAnalysisContext.AreaGradeLevel_III || rank == HcaAnalysisContext.AreaGradeLevel_IV){
                continue;
            }
            Polyline polyline = linearReferenceUtil.locateBetween(startMileage,endMileage,0d);
            Geometry bufferArea = JtsUtil.buffer(polyline,buffer,2) ;
            String bufferAreaText = GeometryUtil.toWKT(bufferArea,false,false) ;
            LayerQueryParam queryParam = new LayerQueryParam();
            queryParam.setSrsname(HcaAnalysisContext.buildingsSourceName);
            queryParam.setGeometry(bufferAreaText);
            queryParam.setGeometryType(GeometryType.POLYLINE.name());
            queryParam.setOutputFormat(null);
            queryParam.setWhere("active=1 and " + getExplosiveQueryExpress());// ?
            FeatureCollection<Feature> featureCollection = geodataAccessService.query(queryParam) ;
            Feature[] features = featureCollection.getFeatures().toArray(new Feature[0]);
            if(features .length > 0){
                Map<String,Object> attribute = hcaFeatures[i].getAttributes();
                String oid = MapUtil.getString(attribute ,oidFieldName) ;
                String desc = MapUtil.getString(props,remarkFieldName);
                desc += buffer + "米内含有" + features.length + "处易燃易爆场所";
                attribute.put(remarkFieldName,desc);
                attribute.put(hcaRankFieldName, HcaAnalysisContext.HcaLevel_II);
                result.putFeatures(oid,features) ;
            }

        }
        return result;
    }

    /**
     *
     * @param hcaDataBeforeMerge
     * @param explosiveLocationMap
     * @param specialLocationMap
     * @return
     */
    @Override
    public Feature[] mergeHcaFeatures(Feature[] hcaDataBeforeMerge, BuildingLocationMap explosiveLocationMap, BuildingLocationMap specialLocationMap) {
        for(int i = 0 ; i < hcaDataBeforeMerge.length ;i++){

        }
        return hcaDataBeforeMerge;
    }

    /**
     *
     * @param areaFeatures
     * @param versionId
     * @param pipelineOid
     * @return
     */
    @Override
    public Feature[] areaGradeAnalysis(Feature[] areaFeatures ,String versionId ,String pipelineOid) {
        String startFiledName = HcaAnalysisContext.startMileageFieldName;
        String endFiledName = HcaAnalysisContext.endMileageFieldName;
        String rankFieldName = HcaAnalysisContext.areaRankFieldName;
        String remarkFieldName = HcaAnalysisContext.hcaRemarkFieldName;
        String oidFieldName = HcaAnalysisContext.TableKeyName;
        String versionIdFieldName = HcaAnalysisContext.versionIdFieldName;
        String pipelineOidFieldName = HcaAnalysisContext.pipelineOidFieldName;
        String hcaCodeFieldName = HcaAnalysisContext.hcaCodeFieldName;
        String hcaNameFieldName = HcaAnalysisContext.hcaNameFieldName;

        List<Feature> hcaFeatures = new ArrayList<>(areaFeatures.length);
        for(int i = 0 ; i < areaFeatures.length ; i ++){
            Feature area = areaFeatures[i] ;
            Map<String,Object> attr = area.getAttributes() ;
            int rank = MapUtil.getInt(attr ,rankFieldName);
            Feature feature = new Feature();

            Map<String,Object> pros = new HashMap<>();
            pros.put( startFiledName, area.getAttributes().get(startFiledName));
            pros.put( endFiledName, area.getAttributes().get(endFiledName));
            pros.put( versionIdFieldName,versionId );
            pros.put( pipelineOidFieldName ,pipelineOid);
            pros.put( oidFieldName ,UUID.randomUUID().toString());
            pros.put( hcaCodeFieldName ,"HCA-" + codeDf.format(i + 1));
            pros.put( hcaNameFieldName , "HCA-NAME-" + (i + 1) );

            if(rank == HcaAnalysisContext.AreaGradeLevel_IV){
                pros.put( HcaAnalysisContext.hcaRankFieldName, HcaAnalysisContext.HcaLevel_III);
                pros.put( remarkFieldName,"地区等级为四级;");
            }else if(rank == HcaAnalysisContext.AreaGradeLevel_III){
                pros.put( HcaAnalysisContext.hcaRankFieldName, HcaAnalysisContext.HcaLevel_II);
                pros.put( remarkFieldName,"地区等级为三级;");
            }else{
                pros.put( HcaAnalysisContext.hcaRankFieldName,null );
                pros.put( remarkFieldName,"");
            }
            feature.setAttributes(pros);
            hcaFeatures.add(feature) ;
        }
        return hcaFeatures.toArray(new Feature[0]);
    }

    /**
     *
     * @param queryParam
     * @return
     */
    protected Feature[] spacialQueryBuildings(LayerQueryParam queryParam ){
        FeatureCollection<Feature> featureCollection = geodataAccessService.query(queryParam) ;
        return featureCollection.getFeatures().toArray(new Feature[0]);
    }

    /**
     *
     * @param features
     * @param areaVersionId
     * @param hcaVersionId
     * @param pipelineOid
     * @return
     */
    @Override
    public int saveHcaResult( Feature[] features ,String areaVersionId ,String hcaVersionId,String pipelineOid) {
        //1、保存要素
        String source = HcaAnalysisContext.hcaSourceName;
        int[] result = geodataAccessService.addFeatures(source,features) ;
        int count = MathUtil.sum( result ) ;
        //2、保存版本
        int hcaVersionFlg = hcaVersionService.initToSave(hcaVersionId ,pipelineOid ,1);
        if(hcaVersionFlg == 1){
            logger.info("高后果区版本数据保存成功,oid=" + hcaVersionId  );
        }else{
            logger.error("高后果区版本保存失败！oid=" + hcaVersionId );
        }
        //3、保存版本关联
        String sql = "insert into hca_area_version_ref( oid ,hca_version_oid ,area_version_oid,active) values( ? ,? ,? ,?)";
        String oid = UUID.randomUUID().toString();
        Object[] params = new Object[]{ oid,hcaVersionId ,areaVersionId ,1};
        int flg = baseJdbcDao.save(sql ,params);
        if(flg == 1){
            logger.info("地区等级和高后果区版本关联表保存,oid=" + oid );
        }else{
            logger.error("地区等级和高后果区版本关联表保存失败！oid=" + oid );
        }
        return count;
    }

    /**
     *
     * @param key
     * @return
     */
    private HcaLinearParam preparePipelineData(String key ){
        String sourceName = HcaAnalysisContext.pipelineSourceName;
        HcaLinearParam analysisSchema = new HcaLinearParam();

        logger.info("查询管线{}" ,key );
        Feature f = pipelineService.queryTargetPipelineData(key ,sourceName) ;
        Geometry p = GeometryUtil.toGeometry(f.getGeometry()) ;
        Map<String,Object> props = f.getAttributes() ;
        analysisSchema.setPipeline((Polyline) p);// 这里设置的是地理坐标！
        if( p == null ){
            logger.error("出错了，没有查询到管线{}坐标数据！" ,key );
        }

        double totalMileage = analysisSchema.getTotalMileage();
        logger.info("管线{}实际里程为{}千米" ,key ,totalMileage);

        if(!props.containsKey(HcaAnalysisContext.pipePressureFieldName)){
            logger.error("管线数据缺少压强字段" + HcaAnalysisContext.pipePressureFieldName);
        }else{
            analysisSchema.setPressure(MapUtil.getDouble(props,HcaAnalysisContext.pipePressureFieldName));
        }
        if(!props.containsKey(HcaAnalysisContext.pipeOuterDiameterFieldName)){
            logger.error("管线数据缺少管径字段" + HcaAnalysisContext.pipeOuterDiameterFieldName);
        }else{
            analysisSchema.setOuterDimension(MapUtil.getDouble(props,HcaAnalysisContext.pipeOuterDiameterFieldName));
        }
        analysisSchema.setPipelineOid(key);
        return analysisSchema;
    }

    /**
     *
     * @param versionId
     * @return
     */
    private Feature[] queryHcaAreaByVersionId(String versionId){
        LayerQueryParam param = new LayerQueryParam();
        param.setSrsname(HcaAnalysisContext.areaGradeSourceName);
        param.setWhere("active=1 and " + HcaAnalysisContext.versionIdFieldName + "='" + versionId + "'");
        param.setOutputFormat(null);
        param.setOutFields("*");
        FeatureCollection collection = geodataAccessService.query(param);
        List<Feature> features = collection.getFeatures();
        return features.toArray(new Feature[0]);
    }

    /**
     *
     * @return
     */
    private HcaVersion queryHcaVersion(String oid){
        HcaVersionQuery query = new HcaVersionQuery();
        query.setOid(oid);
        List<HcaVersion> list = commonDataJdbcDao.getList(query,HcaVersion.class);
        return list.get(0);
    }

    /**
     *
     * @return
     */
    private String getExplosiveQueryExpress(){
        String exp = HcaAnalysisContext.getExplosivePlaceExpress();
        if(StringUtil.isBlank(exp)){
            String domains = hcaCommonService.getCodeIdsByParentCodeId("building_type_parent_001");
            exp = "building_type in(" + domains + ")" ;
            HcaAnalysisContext.setExplosivePlaceExpress(exp);
        }
        return exp;
    }

    private String getSpecialQueryExpress(){
        String exp = HcaAnalysisContext.getSpecialPlaceExpress();
        if(StringUtil.isBlank(exp)){
            String domains = hcaCommonService.getCodeIdsByParentCodeId("building_type_parent_002");
            exp = "building_type in(" + domains + ")" ;
            HcaAnalysisContext.setSpecialPlaceExpress(exp);
        }
        return exp;
    }

}
