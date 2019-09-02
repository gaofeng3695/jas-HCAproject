package cn.jasgroup.hcas.analysis.service;
import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.data.FeatureCollection;
import cn.jasgroup.gis.data.GeometryType;
import cn.jasgroup.gis.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.dataaccess.LayerQueryParam;
import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.geometry.Point;
import cn.jasgroup.gis.geometry.Polygon;
import cn.jasgroup.gis.geometry.Polyline;
import cn.jasgroup.gis.util.*;
import cn.jasgroup.hcas.analysis.*;
import cn.jasgroup.hcas.common.service.HcaCommonService;
import cn.jasgroup.hcas.versionmaanage.dao.entity.HcaVersion;
import cn.jasgroup.hcas.versionmaanage.query.HcaVersionQuery;
import cn.jasgroup.hcas.versionmaanage.service.HcaVersionService;
import cn.jasgroup.jasframework.dataaccess.base.BaseJdbcDao;
import cn.jasgroup.jasframework.engine.jdbc.dao.CommonDataJdbcDao;
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
        //创建潜在影响缓冲区
        double buffer = HcaAnalysisContext.calculateHCABufferRadius(bo.getOuterDimension(),bo.getPressure());
        bo.createPotentialInfluenceBuffer(buffer);

        //3、查询地区等级数据
        Feature[] areaData = queryHcaAreaByVersionId(areaVersionOid);
        //4、执行分类
        String hcaVersionId = UUID.randomUUID().toString() ;
        Feature[] hcaData = classifyHighConsequenceArea(areaData ,bo,hcaVersionId);

        //重新生成高后果区单元,补充描述信息
        Feature[] features = preparedFeatureData(hcaData ,bo ,pipelineOid ,buffer);

        //5、保存数据
        int saveCount = saveHcaResult(features,areaVersionOid, hcaVersionId ,pipelineOid);

        hcaAnalysisResult.setTotal( saveCount);
        //hcaAnalysisResult.setFeatures( hcaData );
        hcaAnalysisResult.setVersionId(hcaVersionId);

        loggerUtil.timeEnd("执行高后果区分析");

        return hcaAnalysisResult;
    }

    /**
     *
     * @param level
     * @return
     */
    protected String parseHcaLevel(int level){
        switch (level){
            case 1 :
                return HcaAnalysisContext.HcaLevel_I;
            case 2 :
                return HcaAnalysisContext.HcaLevel_II;
            case 3 :
                return HcaAnalysisContext.HcaLevel_III;
            default:
                return "";
        }
    }
    protected Feature[] preparedFeatureData(Feature[] hcaData,HcaLinearParam bo ,String pipelineOid,Double buffer){
        //合并后重新计算单元几何坐标
        loggerUtil.time("重新生成高后果区单元");
        LinearReferenceUtil linearReferenceUtil = bo.getLinearReferenceUtil();

        List<Feature> result = new ArrayList<>();
        for(int i = 0 ; i < hcaData.length ; i++){
            Map<String,Object> attrs = hcaData[i] .getAttributes();
            Double startMileage = MapUtil.getDouble(attrs,HcaAnalysisContext.startMileageFieldName );
            Double endMileage = MapUtil.getDouble(attrs,HcaAnalysisContext.endMileageFieldName );

            startMileage = Double.valueOf(String.format("%.3f", startMileage));
            endMileage = Double.valueOf(String.format("%.3f", endMileage));

            attrs.put(HcaAnalysisContext.startMileageFieldName,startMileage);
            attrs.put(HcaAnalysisContext.endMileageFieldName,endMileage);
            // 不为空字段
            attrs.put(HcaAnalysisContext.pipelineOidFieldName,pipelineOid);
            attrs.put(HcaAnalysisContext.versionNameFieldName,"");
            attrs.put("active",1);

            Polyline p = linearReferenceUtil.locateBetween(startMileage * 1000,endMileage * 1000,0d);
            Geometry po =  GeometryUtil.buffer(p,buffer ,2);
            Geometry geom = GeometryUtil.gaussToBL(po);
            hcaData[i].setGeometry(geom);
            String rank = MapUtil.getString(attrs,HcaAnalysisContext.hcaRankFieldName);
            if(StringUtil.isNotBlank(rank)){
                result.add(hcaData[i]);
            }
        }
        loggerUtil.timeEnd("重新生成高后果区单元");
        return result.toArray(new Feature[0]) ;
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
        Feature[] features = mergeHcaFeatures( hcaFeatures ,explosiveLocationMap,specialLocationMap);



        return features;
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

        loggerUtil.time("开始进行特定场所分析");
        LinearReferenceUtil linearReferenceUtil = bo.getLinearReferenceUtil();
        String remarkFieldName = HcaAnalysisContext.hcaRemarkFieldName;
        String rankFieldName = HcaAnalysisContext.areaRankFieldName;
        String hcaRankFieldName = HcaAnalysisContext.hcaRankFieldName;
        String oidFieldName = HcaAnalysisContext.TableKeyName;

        double pressure = bo.getPressure();
        double dimension = bo.getOuterDimension();
        double buffer = calculatePotentialInfluenceBufferDistance(pressure,dimension);

        BuildingLocationMap result = new BuildingLocationMap() ;

        for(int i = 0 ; i < hcaFeatures.length ; i++){

            Map<String,Object> props = hcaFeatures[i].getAttributes();
            //
            Double startMileage = MapUtil.getDouble(props , HcaAnalysisContext.startMileageFieldName)  ;
            Double endMileage = MapUtil.getDouble(props , HcaAnalysisContext.endMileageFieldName)   ;
            Polyline polyline = linearReferenceUtil.locateBetween( startMileage,endMileage,0d);
            Geometry bufferArea = GeometryUtil.buffer( polyline,buffer,2) ;
            Geometry polygon = GeometryUtil.gaussToBL(bufferArea);

            String rank = MapUtil.getString( props ,rankFieldName );

            LayerQueryParam queryParam = new LayerQueryParam();
            queryParam.setSrsname(HcaAnalysisContext.buildingsSourceName);
            queryParam.setGeometry(polygon);
            queryParam.setGeometryType(GeometryType.POLYGON.getType());
            queryParam.setOutputFormat(null);
            queryParam.setWhere( getSpecialQueryExpress());
            FeatureCollection featureCollection = geodataAccessService.query(queryParam) ;

            if(featureCollection == null ){
                throw new RuntimeException("查询特定场所出错！");
            }
            Feature[] features = FeatureCollectionUtil.toLowerCaseFeature(featureCollection);
            if(features == null || features .length == 0) {
                continue;
            }
            String oid = MapUtil.getString(props,oidFieldName);

            logger.info("高后果区单元{}潜在影响区内查询到{}处特定场所。",oid ,features.length);

            //String des = "潜在影响区内含有" + features.length + "处特定场所";
            result.putFeatures(oid,features);

            double[] mileages = locateBetweenPoints(linearReferenceUtil,features);
            if(startMileage > mileages[0]){
                props.put(HcaAnalysisContext.startMileageFieldName,mileages[0]);
            }
            if(endMileage < mileages[1]){
                props.put(HcaAnalysisContext.endMileageFieldName,mileages[1]);
            }
            if(rank == HcaAnalysisContext.HcaLevel_III || rank == HcaAnalysisContext.HcaLevel_II){
                continue;
            }
            putHcaLevelByPipeParams( oid ,props ,pressure,dimension ,hcaRankFieldName);
        }
        loggerUtil.timeEnd("开始进行特定场所分析");

        return result;
    }

    /**
     *
     * @param oid
     * @param props
     * @param pressure
     * @param outerDimension
     * @param hcaRankFieldName
     */
    private void putHcaLevelByPipeParams(String oid, Map props ,double pressure ,double outerDimension,String hcaRankFieldName){
        if(outerDimension > 762 && pressure > 6.9){
            props.put(hcaRankFieldName, HcaAnalysisContext.HcaLevel_II);
            logger.info("单元{}含有特定场所，高后果区等级提升为为{}。",oid ,HcaAnalysisContext.HcaLevel_II);

        }else{
            props.put(hcaRankFieldName, HcaAnalysisContext.HcaLevel_I);
            logger.info("单元{}含有特定场所，高后果区等级提升为为{}。",oid ,HcaAnalysisContext.HcaLevel_I);
        }
    }

    /**
     *
     * @param cell
     * @param outSrid
     * @return
     */
    protected Feature[] explosiveSpatialQuery(Polygon cell , int outSrid ){
        LayerQueryParam queryParam = new LayerQueryParam();
        queryParam.setSrsname( HcaAnalysisContext.buildingsSourceName ) ;
        queryParam.setGeometry(cell);
        queryParam.setGeometryType(GeometryType.POLYGON.getType());
        queryParam.setWhere(null);
        queryParam.setOutputFormat(null);
        queryParam.setInputSRID(cell.getSpatialReference().getWkid());
        queryParam.setOutputSRID(outSrid);
        queryParam.setWhere( getExplosiveQueryExpress());// ?
        FeatureCollection  featureCollection = geodataAccessService.query(queryParam) ;
        if(featureCollection == null ){
            throw new RuntimeException("空间查询易燃易爆场所出错！");
        }
        Feature[] features = FeatureCollectionUtil.toLowerCaseFeature(featureCollection);
        return features;
    }

    /**
     * 计算点的最值里程
     * @return
     */
    public double[] locateBetweenPoints(LinearReferenceUtil util ,Feature[] features){
        int size = features.length ;
        double min = util.getLinearPolyline().getTotalLength();
        double max = 0 ;
        for(int i = 0 ; i < size ; i++){
            Geometry geometry = features[i].getGeometry();
            Double[][] points = geometry.toCoordinates();
            for(int j = 0 ; j < points.length ;j++){
                Point p = new Point(points[j],geometry.getSpatialReference().getWkid());
                double m = util.interpolatePoint(p);
                min = Math.min(min,m);
                max = Math.max(max,m);
            }
        }
        double pad = HcaAnalysisContext.ConfigAreaRankBorderBufferDistance;
        return new double[]{ ( min - pad) / 1000 ,( max + pad) / 1000 };
    }

    /**
     *
     * @param hcaFeatures
     * @param bo
     * @return
     */
    @Override
    public BuildingLocationMap explosivePlaceAnalysis(Feature[] areaFeatures , Feature[] hcaFeatures, HcaLinearParam bo) {

        loggerUtil.time("开始进行易燃易爆场所分析");

        LinearReferenceUtil linearReferenceUtil = bo.getLinearReferenceUtil();
        Double buffer = HcaAnalysisContext.ConfigRankAreaBufferDistance;
        String remarkFieldName = HcaAnalysisContext.hcaRemarkFieldName;
        String rankFieldName = HcaAnalysisContext.areaRankFieldName;
        String hcaRankFieldName = HcaAnalysisContext.hcaRankFieldName;
        String oidFieldName = HcaAnalysisContext.TableKeyName;

        BuildingLocationMap result = new BuildingLocationMap();

        for(int i = 0 ; i < hcaFeatures.length ; i++){
            Map<String,Object> props = hcaFeatures[i].getAttributes();

            Geometry geometry = areaFeatures[i].getGeometry();

            Double startMileage = MapUtil.getDouble(props , HcaAnalysisContext.startMileageFieldName) ;
            Double endMileage = MapUtil.getDouble(props , HcaAnalysisContext.endMileageFieldName) ;

            String rank = MapUtil.getString(props ,rankFieldName);
            int outSrid = linearReferenceUtil.getLinearPolyline().getPolyline().getSpatialReference().getWkid();
            Feature[] features = explosiveSpatialQuery((Polygon) geometry ,outSrid) ;
            if(features == null || features .length == 0){
                continue;
            }

            String oid = MapUtil.getString(props ,oidFieldName) ;
            logger.info("高后果区单元{}识别区域内查询到{}处易燃易爆场所。",oid ,features.length);

            result.putFeatures(oid,features) ;//计算里程？
            double[] mileages = locateBetweenPoints(linearReferenceUtil,features);

            if(startMileage > mileages[0]){
                props.put(HcaAnalysisContext.startMileageFieldName,mileages[0]);
            }
            if(endMileage < mileages[1]){
                props.put(HcaAnalysisContext.endMileageFieldName,mileages[1]);
            }
            if(rank == HcaAnalysisContext.AreaGradeLevel_III || rank == HcaAnalysisContext.AreaGradeLevel_IV){
                continue;
            }
            props.put( hcaRankFieldName, HcaAnalysisContext.HcaLevel_II );
            logger.info("单元{}含有易燃易爆场所，高后果区等级提升为为{}。",oid ,HcaAnalysisContext.HcaLevel_II);

        }
        loggerUtil.timeEnd("开始进行易燃易爆场所分析");

        return result;
    }

    /**
     *
     * @param from
     * @param to
     * @param fieldName
     */
    protected void mergeFeatureNumberValue(Feature from ,Feature to,String fieldName){
        double fromValue = MapUtil.getDouble(from.getAttributes(),fieldName,0d);
        double toValue = MapUtil.getDouble(to.getAttributes(),fieldName,0d);
        to.getAttributes().put(fieldName, toValue + fromValue ) ;
    }

    protected void appendFeatureStringValue(Feature from ,Feature to,String fieldName){
        String fromValue = MapUtil.getString(from.getAttributes(),fieldName);
        String toValue = MapUtil.getString(to.getAttributes(),fieldName);
        to.getAttributes().put(fieldName,toValue + fromValue) ;
    }

    protected Boolean isUpperLevel(String pre ,String last){
        if(StringUtil.isBlank(last)){
            return true ;
        }
        if(StringUtil.isBlank(pre)){
            return false;
        }
        String[] preStrs = pre.split("_");
        String[] sufStrs = last.split("_");
        int levelInteger = Integer.valueOf(preStrs[preStrs.length - 1]);
        int lastLevelInteger = Integer.valueOf(sufStrs[sufStrs.length - 1]);
        return levelInteger > lastLevelInteger ;
    }


    /**
     * 合并计算高后果区等级
     * @param hcaDataBeforeMerge
     * @param explosiveLocationMap
     * @param specialLocationMap
     * @return
     */
    @Override
    public Feature[] mergeHcaFeatures(Feature[] hcaDataBeforeMerge, BuildingLocationMap explosiveLocationMap, BuildingLocationMap specialLocationMap) {
        //
        List<Feature> result = new ArrayList<>();

        //1、
        Feature last = hcaDataBeforeMerge[0];
        result.add(last);
        int size0 = hcaDataBeforeMerge.length ;

        for(int i = 1 ; i < hcaDataBeforeMerge.length ;i++){
            Feature cell = hcaDataBeforeMerge[i] ;
            Map<String,Object> props = cell.getAttributes();
            Double endMileage = MapUtil.getDouble(props , HcaAnalysisContext.endMileageFieldName) ;
            String level = MapUtil.getString(props,HcaAnalysisContext.hcaRankFieldName);
            String oid = MapUtil.getString(props ,HcaAnalysisContext.TableKeyName);

            String lastLevel = MapUtil.getString(last.getAttributes() ,HcaAnalysisContext.hcaRankFieldName);
            String lastOid = MapUtil.getString(last.getAttributes() ,HcaAnalysisContext.TableKeyName);

            // 合并逻辑 （需要合并人口、描述信息）
            // 1、同级合并
            // 2、如果含有易燃易爆场所，低就高
            boolean flg = isUpperLevel(level,lastLevel);

            if( lastLevel.equalsIgnoreCase(level)){
                last.getAttributes().put(HcaAnalysisContext.endMileageFieldName,endMileage);
                //mergeFeatureNumberValue(cell,last,HcaAnalysisContext.buildingsSourcePopulationFieldName);
                explosiveLocationMap.merge(oid ,lastOid);
                specialLocationMap.merge(oid ,lastOid);
            }else if( explosiveLocationMap.size(oid) > 0 && !flg){
                last.getAttributes().put(HcaAnalysisContext.endMileageFieldName,endMileage);
                explosiveLocationMap.merge(oid ,lastOid);
                specialLocationMap.merge(oid ,lastOid);
                //mergeFeatureNumberValue(cell,last,HcaAnalysisContext.buildingsSourcePopulationFieldName);
                //String l = flg ? level:lastLevel ;
                //last.getAttributes().put(HcaAnalysisContext.hcaRankFieldName,l);
                appendFeatureStringValue(cell,last,HcaAnalysisContext.hcaRemarkFieldName );
            }else{
                last = cell ;
                result.add(last);
            }
        }
        int size1 = result.size() ;

        logger.info("高后果区合并{}处识别区。",( size0 - size1) );

        //3、重复边界，低就高 （这样合并会有问题）,补充易燃易爆、特定场所信息提示
        Feature lastFeature = result.get(0);
        int count0 = 0 ;
        for(int i = 1 ; i < result.size() ;i++){
            Feature current = result.get(i) ;
            Map<String,Object> props = current.getAttributes();
            String oid = MapUtil.getString(props ,HcaAnalysisContext.TableKeyName);
            Double startMileage = MapUtil.getDouble(props , HcaAnalysisContext.startMileageFieldName) ;
            String level = MapUtil.getString(props,HcaAnalysisContext.hcaRankFieldName);

            String lastOid = MapUtil.getString(lastFeature.getAttributes()  ,HcaAnalysisContext.TableKeyName);
            Double lastEndMileage =  MapUtil.getDouble(lastFeature.getAttributes() , HcaAnalysisContext.endMileageFieldName) ;
            String lastLevel = MapUtil.getString(lastFeature.getAttributes() ,HcaAnalysisContext.hcaRankFieldName);
            if( lastEndMileage > startMileage){
                if(isUpperLevel(lastLevel,level)){
                    current.getAttributes().put(HcaAnalysisContext.startMileageFieldName,lastEndMileage);
                }else{
                    lastFeature.getAttributes().put(HcaAnalysisContext.endMileageFieldName,startMileage);
                }
                count0++;
                logger.info("重新划分{}和{}单元重叠区域",lastOid,oid);
            }else{
                lastFeature = current;
            }

        }
        for(int i = 0 ; i < result.size() ; i++){
            Feature current = result.get(i) ;
            Map<String,Object> props = current.getAttributes();
            String oid = MapUtil.getString(props ,HcaAnalysisContext.TableKeyName);

            if(explosiveLocationMap.size(oid) > 0){
                String desc =  MapUtil.getString(props,HcaAnalysisContext.hcaRemarkFieldName);
                desc += "识别区内含有" + explosiveLocationMap.size(oid) + "处易燃易爆场所;";
                props.put(HcaAnalysisContext.hcaRemarkFieldName,desc);
            }
            if(specialLocationMap.size(oid) > 0){
                String desc =  MapUtil.getString(props,HcaAnalysisContext.hcaRemarkFieldName);
                desc += "潜在影响区内含有" + specialLocationMap.size(oid) + "处特定场所;";
                props.put(HcaAnalysisContext.hcaRemarkFieldName,desc);
            }
        }
        logger.info("边界重叠处理结束，处理{}处边界重叠单元",count0);

        Feature[] hcaDataAfterMerge = result.toArray(new Feature[0]);
        return hcaDataAfterMerge;
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

        int count3 = 0 ;
        int count2 = 0 ;
        int count1 = 0 ;

        for(int i = 0 ; i < areaFeatures.length ; i ++){
            Feature area = areaFeatures[i] ;
            Map<String,Object> attr = area.getAttributes() ;
            String rank = MapUtil.getString(attr ,rankFieldName);
            Feature feature = new Feature();

            Map<String,Object> pros = new HashMap<>();
            pros.put( startFiledName, attr.get(startFiledName));
            pros.put( endFiledName, attr.get(endFiledName));
            pros.put( versionIdFieldName,versionId );
            pros.put( pipelineOidFieldName ,pipelineOid);
            pros.put( oidFieldName ,UUID.randomUUID().toString());
            pros.put( hcaCodeFieldName ,"HCA-" + codeDf.format(i + 1));
            pros.put( hcaNameFieldName ,"HCA-NAME-" + (i + 1) );

            if( HcaAnalysisContext.AreaGradeLevel_IV.equalsIgnoreCase(rank)){
                pros.put( HcaAnalysisContext.hcaRankFieldName, HcaAnalysisContext.HcaLevel_III);
                pros.put( remarkFieldName,"该区域地区等级为四级;");
                count3++ ;
            }else if( HcaAnalysisContext.AreaGradeLevel_III.equalsIgnoreCase(rank)){
                pros.put( HcaAnalysisContext.hcaRankFieldName, HcaAnalysisContext.HcaLevel_II);
                pros.put( remarkFieldName,"该区域地区等级为三级;");
                count2++;
            }else{
                pros.put( HcaAnalysisContext.hcaRankFieldName,"" );
                pros.put( remarkFieldName,"");
                count1++;
            }
            feature.setAttributes(pros);
            hcaFeatures.add(feature) ;
        }
        logger.info("根据地区等级数据，三级高后果区{}个，二级地区{}个，其他未分级{}个。" ,count3,count2,count1);

        return hcaFeatures.toArray(new Feature[0]);
    }

    /**
     *
     * @param queryParam
     * @return
     */
    protected Feature[] spacialQueryBuildings(LayerQueryParam queryParam ){
        FeatureCollection  featureCollection = geodataAccessService.query(queryParam) ;
        return FeatureCollectionUtil.toLowerCaseFeature(featureCollection);
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
        loggerUtil.time("保存高后果区分析结果");
        //1、保存要素
        String source = HcaAnalysisContext.hcaSourceName;


        int[] result = geodataAccessService.addFeatures(source,features) ;

        int count = MathUtil.sum( result ) ;
        if(count == 0 ){
            throw new RuntimeException("高后果区分析结果保存失败！");
        }else{
            logger.info("成功保存{}条高后果区数据",count);
        }
        //2、保存版本
        int hcaVersionFlg = hcaVersionService.initToSave(hcaVersionId ,pipelineOid ,1);
        if(hcaVersionFlg == 1){
            logger.info("高后果区版本数据保存成功,oid=" + hcaVersionId  );
        }else{
            throw new RuntimeException("高后果区版本保存失败！oid=" + hcaVersionId );
        }
        //3、保存版本关联
        String sql = "insert into hca_area_version_ref( oid ,hca_version_oid ,area_version_oid,active) values( ? ,? ,? ,?)";
        String oid = UUID.randomUUID().toString();
        Object[] params = new Object[]{ oid,hcaVersionId ,areaVersionId ,1};
        int flg = baseJdbcDao.save(sql ,params);
        if(flg == 1){
            logger.info("地区等级和高后果区版本关联表保存,oid=" + oid );
        }else{
            throw new RuntimeException("地区等级和高后果区版本关联表保存失败！oid=" + oid );
        }
        loggerUtil.timeEnd("保存高后果区分析结果");

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
        logger.info("管线{}实际里程为{}千米" ,key ,String.format("%.3f",totalMileage/1000));

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
        loggerUtil.time("查询地区等级划分数据" );
        logger.info("评价版本：" + versionId);
        LayerQueryParam param = new LayerQueryParam();
        param.setSrsname(HcaAnalysisContext.areaGradeSourceName);
        param.setWhere("active=1 and " + HcaAnalysisContext.versionIdFieldName + "='" + versionId + "'");
        param.setOutputFormat(null);
        param.setOutFields("*");
        param.setOrderBy("start_mileage");
        FeatureCollection collection = geodataAccessService.query(param);
        if(collection == null){
            throw new RuntimeException("没有查询到地区等级数据，请先进行地区等级划分！");
        }
        Feature[] features = FeatureCollectionUtil.toLowerCaseFeature(collection);
        loggerUtil.timeEnd("查询地区等级划分数据","查询到" + features.length + "处地区等级单元。" );

        return features;
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
            String domains = hcaCommonService.getCodeIdsByParentCodeId("building_type_parent_002");
            exp = "building_type in(" + domains + ")" ;
            HcaAnalysisContext.setExplosivePlaceExpress(exp);
        }
        return exp;
    }

    private String getSpecialQueryExpress(){
        String exp = HcaAnalysisContext.getSpecialPlaceExpress();
        if(StringUtil.isBlank(exp)){
            String domains = hcaCommonService.getCodeIdsByParentCodeId("building_type_parent_001");
            exp = "building_type in(" + domains + ")" ;
            HcaAnalysisContext.setSpecialPlaceExpress(exp);
        }
        return exp;
    }

}
