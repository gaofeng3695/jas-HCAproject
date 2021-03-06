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
import org.springframework.jdbc.core.JdbcTemplate;
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
    private JdbcTemplate jdbcTemplate ;

    @Autowired
    private HcaVersionService hcaVersionService;


    private DecimalFormat codeDf = new DecimalFormat("000000");

    /**
     *
     * @param areaVersionCode ??????????????????ID
     * @return
     */
    @Override
    public HcaAnalysisResult executeAnalysis(String areaVersionCode) {

        HcaAnalysisResult hcaAnalysisResult = new HcaAnalysisResult();

        String hcaVersionId = UUID.randomUUID().toString() ;
        loggerUtil.time("????????????????????????,OID=" + hcaVersionId);
        //1?????????????????????
        HcaVersion version = queryHcaVersion(areaVersionCode) ;
        String areaVersionOid = version.getOid();
        if(version == null ){
            hcaAnalysisResult.setTotal(0);
            return hcaAnalysisResult;
        }
        //2???????????????????????????
        String pipelineOid = version.getPipelineOid() ;
        HcaLinearParam bo = preparePipelineData(pipelineOid);
        //???????????????????????????
        double buffer = HcaAnalysisContext.calculateHCABufferRadius(bo.getOuterDimension(),bo.getPressure());
        bo.createPotentialInfluenceBuffer(buffer);

        //3???????????????????????????
        Feature[] areaData = queryHcaAreaByVersionId(areaVersionOid);
        //4???????????????
        Feature[] hcaData = classifyHighConsequenceArea(areaData ,bo,hcaVersionId);

        //??????????????????????????????,??????????????????
        Feature[] features = preparedFeatureData(hcaData ,bo ,pipelineOid ,buffer);

        if(features .length ==0 ){
            throw new RuntimeException("?????????????????????????????????????????????????????????????????????????????????????????????????????????");
        }
        int saveCount = saveHcaResult(features,areaVersionOid, hcaVersionId ,pipelineOid);
        hcaAnalysisResult.setTotal( saveCount);

        hcaAnalysisResult.setVersionId(hcaVersionId);
        loggerUtil.timeEnd("????????????????????????,OID=" + hcaVersionId);

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
        //???????????????????????????????????????
        loggerUtil.time("??????????????????????????????");
        LinearReferenceUtil linearReferenceUtil = bo.getLinearReferenceUtil();

        List<Feature> result = new ArrayList<>();
        for(int i = 0 ; i < hcaData.length ; i++){
            Map<String,Object> attrs = hcaData[i] .getAttributes();
            Double startMileage = MapUtil.getDouble(attrs,HcaAnalysisContext.startMileageFieldName );
            Double endMileage = MapUtil.getDouble(attrs,HcaAnalysisContext.endMileageFieldName );

            startMileage = Double.valueOf(String.format("%.3f", startMileage)) ;
            endMileage = Double.valueOf(String.format("%.3f", endMileage));

            attrs.put(HcaAnalysisContext.startMileageFieldName,startMileage);
            attrs.put(HcaAnalysisContext.endMileageFieldName,endMileage);
            attrs.put(HcaAnalysisContext.hcaLengthFieldName,endMileage - startMileage);

            //??????????????????????????????m
            startMileage *= 1000;
            endMileage *= 1000 ;

            // ???????????????
            attrs.put(HcaAnalysisContext.pipelineOidFieldName,pipelineOid);
            attrs.put(HcaAnalysisContext.versionNameFieldName,"");
            attrs.put("active",1);
            if(startMileage < 0 || endMileage < 0 || endMileage > linearReferenceUtil.getLinearPolyline().getTotalMeasure()){
                logger.error("????????????{}???????????????[{},{}]???????????????",MapUtil.getString( attrs ,"oid" ),startMileage ,endMileage);
                continue;
            }
            Polyline p = linearReferenceUtil.locateBetween(startMileage ,endMileage ,0d);
            Geometry po =  GeometryUtil.buffer(p,buffer ,2);
            Geometry geom = GeometryUtil.gaussToBL(po);
            hcaData[i].setGeometry(geom);
            String rank = MapUtil.getString(attrs,HcaAnalysisContext.hcaRankFieldName);
            if(StringUtil.isNotBlank(rank)){
                result.add(hcaData[i]);
            }
        }
        loggerUtil.timeEnd("??????????????????????????????");
        return result.toArray(new Feature[0]) ;
    }
    /**
     * ??????????????????
     * @param areaData
     * @param bo
     * @return
     */
    @Override
    public Feature[] classifyHighConsequenceArea(Feature[] areaData, HcaLinearParam bo , String hcaVersionOid) {
        //
        if(areaData .length == 0 ){
            throw new RuntimeException("??????????????????????????????????????????????????????????????????");
        }
        Feature[] hcaFeatures = areaGradeAnalysis(areaData ,hcaVersionOid ,bo.getPipelineOid());//???????????????????????????????????????????????????
        //????????????????????????
        BuildingLocationMap explosiveLocationMap = explosivePlaceAnalysis(areaData,hcaFeatures,bo);
        //????????????????????????
        BuildingLocationMap specialLocationMap = specialPlaceAnalysis(areaData,hcaFeatures,bo);
        //??????????????????????????????
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
     * ???????????????????????????????????????????????????????????????????????????
     *
     * @param hcaFeatures
     * @param bo
     * @return
     */
    @Override
    public BuildingLocationMap specialPlaceAnalysis(Feature[] areaFeatures ,Feature[] hcaFeatures , HcaLinearParam bo) {

        loggerUtil.time("??????????????????????????????");
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
            String oid = MapUtil.getString(props , HcaAnalysisContext.TableKeyName);
            //
            Double startMileage = MapUtil.getDouble(props , HcaAnalysisContext.startMileageFieldName) ;
            Double endMileage = MapUtil.getDouble(props , HcaAnalysisContext.endMileageFieldName) ;

            Polyline polyline = linearReferenceUtil.locateBetween( startMileage * 1000 ,endMileage * 1000,0d);
            Geometry bufferArea = GeometryUtil.buffer( polyline,buffer,2) ;
            //Geometry polygon = GeometryUtil.gaussToBL(bufferArea);

            String rank = MapUtil.getString( props ,rankFieldName );
            int outSrid = linearReferenceUtil.getLinearPolyline().getPolyline().getSpatialReference().getWkid();

            LayerQueryParam queryParam = new LayerQueryParam();
            queryParam.setSrsname(HcaAnalysisContext.buildingsSourceName);
            queryParam.setGeometry(bufferArea);
            queryParam.setGeometryType(GeometryType.POLYGON.getType());
            queryParam.setOutputFormat(null);
            queryParam.setOutFields("*");
            queryParam.setWhere( getSpecialQueryExpress());
            queryParam.setInputSRID(outSrid);
            queryParam.setOutputSRID(outSrid);
            FeatureCollection featureCollection = geodataAccessService.query(queryParam) ;

            if(featureCollection == null ){
                throw new RuntimeException("???????????????????????????" );
            }
            Feature[] features = FeatureCollectionUtil.toLowerCaseFeature(featureCollection);
            if(features == null || features .length == 0) {
                //logger.info("{}???????????????????????????????????????" , MapUtil.getString(props ,"oid"));
                continue;
            }

            logger.info("??????????????????{}???????????????????????????{}??????????????????",oid ,features.length);

            //String des = "????????????????????????" + features.length + "???????????????";
            result.putFeatures(oid,features);
            //?????????????????????????????????????????????
            double[] mileages = locateBetweenPoints(linearReferenceUtil,features);
            if(mileages[0] < 0 || mileages[1] < 0){
                logger.error("?????????{}???????????????????????????[{} ,{}]???????????? ???",oid,mileages[0] ,mileages[1]);
            }
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
        loggerUtil.timeEnd("??????????????????????????????");

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
            logger.info("??????{}???????????????????????????????????????????????????{}???",oid ,HcaAnalysisContext.HcaLevel_II);

        }else{
            props.put(hcaRankFieldName, HcaAnalysisContext.HcaLevel_I);
            logger.info("??????{}???????????????????????????????????????????????????{}???",oid ,HcaAnalysisContext.HcaLevel_I);
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
        queryParam.setOutputFormat(null);
        queryParam.setOutFields("*");
        queryParam.setInputSRID(cell.getSpatialReference().getWkid());
        queryParam.setOutputSRID(outSrid);
        queryParam.setWhere( getExplosiveQueryExpress());// ?
        FeatureCollection  featureCollection = geodataAccessService.query(queryParam) ;
        if(featureCollection == null ){
            throw new RuntimeException("???????????????????????????????????????");
        }
        Feature[] features = FeatureCollectionUtil.toLowerCaseFeature(featureCollection);
        return features;
    }

    /**
     * ????????????????????????
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

        loggerUtil.time("????????????????????????????????????");

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
            logger.info("??????????????????{}????????????????????????{}????????????????????????",oid ,features.length);

            result.putFeatures(oid,features) ;//???????????????
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
            logger.info("??????{}?????????????????????????????????????????????????????????{}???",oid ,HcaAnalysisContext.HcaLevel_II);

        }
        loggerUtil.timeEnd("????????????????????????????????????");

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

    /**
     * ????????????????????????????????????????????????
     * @param pre
     * @param last
     * @return
     */
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
     *
     * @param currentOid
     * @param explosiveLocationMap
     * @param specialLocationMap
     * @return
     */
    public boolean hasExplosiveOrSpecial( String currentOid , BuildingLocationMap explosiveLocationMap , BuildingLocationMap specialLocationMap){
        return explosiveLocationMap.size(currentOid) > 0  ||  specialLocationMap.size(currentOid) > 0;
    }
    /**
     * ??????????????????????????????
     * @param hcaDataBeforeMerge
     * @param explosiveLocationMap
     * @param specialLocationMap
     * @return
     */
    @Override
    public Feature[] mergeHcaFeatures(Feature[] hcaDataBeforeMerge, BuildingLocationMap explosiveLocationMap, BuildingLocationMap specialLocationMap) {
        //
        List<Feature> hcaDataBeforeMergeList = new ArrayList<>();
        // ????????????
        //1?????????????????? ????????????????????????????????? ?????????????????????;
        Feature last = hcaDataBeforeMerge[0];
        hcaDataBeforeMergeList.add(last);

        int size0 = hcaDataBeforeMerge.length ;

        for(int i = 1 ; i < hcaDataBeforeMerge.length ;i++){
            Feature cell = hcaDataBeforeMerge[i] ;
            Map<String,Object> props = cell.getAttributes();
            Double startMileage = MapUtil.getDouble(props , HcaAnalysisContext.startMileageFieldName) ;
            Double endMileage = MapUtil.getDouble(props , HcaAnalysisContext.endMileageFieldName) ;
            String level = MapUtil.getString(props,HcaAnalysisContext.hcaRankFieldName);
            String oid = MapUtil.getString(props ,HcaAnalysisContext.TableKeyName);

            Map<String,Object> lastProps = last.getAttributes();
            String lastLevel = MapUtil.getString(lastProps ,HcaAnalysisContext.hcaRankFieldName);
            String lastOid = MapUtil.getString(lastProps ,HcaAnalysisContext.TableKeyName);
            Double lastStartMileage = MapUtil.getDouble(lastProps , HcaAnalysisContext.startMileageFieldName) ;
            Double lastEndMileage = MapUtil.getDouble(lastProps , HcaAnalysisContext.endMileageFieldName) ;

            boolean flg = isUpperLevel(level,lastLevel);

            if( explosiveLocationMap.getSameFeatures(oid,lastOid).length > 0  || specialLocationMap.getSameFeatures(oid,lastOid).length > 0 ||( hasExplosiveOrSpecial( oid ,explosiveLocationMap ,specialLocationMap) && !flg  ) ){
                double largerEndMileage = Math.max(lastEndMileage,endMileage);
                double largerStartMileage = Math.min(lastStartMileage,startMileage);
                lastProps.put( HcaAnalysisContext.endMileageFieldName,largerEndMileage );
                lastProps.put( HcaAnalysisContext.startMileageFieldName,largerStartMileage);
                explosiveLocationMap.merge( oid ,lastOid );
                specialLocationMap.merge( oid ,lastOid );
                appendFeatureStringValue( cell,last,HcaAnalysisContext.hcaRemarkFieldName );
                if(!flg){
                    lastProps.put( HcaAnalysisContext.hcaRankFieldName,level );
                }

            }else{
                last = cell ;
                hcaDataBeforeMergeList.add(last);
            }

        }
        int size1 = hcaDataBeforeMergeList.size() ;
        logger.info("????????????????????????????????????????????????{}???????????????",( size0 - size1) );

        //
        last = hcaDataBeforeMergeList.get(0);
        List<Feature> result = new ArrayList<>();
        result.add(last);

        for(int i = 1 ; i < hcaDataBeforeMergeList.size() ;i++){
            Feature cell = hcaDataBeforeMergeList.get(i) ;
            Map<String,Object> props = cell.getAttributes();
            Double startMileage = MapUtil.getDouble(props , HcaAnalysisContext.startMileageFieldName) ;
            Double endMileage = MapUtil.getDouble(props , HcaAnalysisContext.endMileageFieldName) ;
            String level = MapUtil.getString(props,HcaAnalysisContext.hcaRankFieldName);
            String oid = MapUtil.getString(props ,HcaAnalysisContext.TableKeyName);

            Map<String,Object> lastProps = last.getAttributes();
            String lastLevel = MapUtil.getString(lastProps ,HcaAnalysisContext.hcaRankFieldName);
            String lastOid = MapUtil.getString(lastProps ,HcaAnalysisContext.TableKeyName);
            Double lastStartMileage = MapUtil.getDouble(lastProps , HcaAnalysisContext.startMileageFieldName) ;
            Double lastEndMileage = MapUtil.getDouble(lastProps , HcaAnalysisContext.endMileageFieldName) ;
            if( lastLevel.equalsIgnoreCase(level)) {
                double largerEndMileage = Math.max(lastEndMileage,endMileage);
                double largerStartMileage = Math.min(lastStartMileage,startMileage);
                lastProps.put( HcaAnalysisContext.endMileageFieldName,largerEndMileage );
                lastProps.put( HcaAnalysisContext.startMileageFieldName,largerStartMileage);
                explosiveLocationMap.merge(oid, lastOid);
                specialLocationMap.merge(oid, lastOid);
            }else{
                last = cell ;
                result.add(last);
            }
        }

        int size2 = result.size() ;
        logger.info("??????????????????????????????{}???????????????",( size1 - size2) );

        //3??????????????????????????? ??????????????????????????????,?????????????????????????????????????????????
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
                logger.info("????????????{}???{}??????????????????",lastOid,oid);
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
                desc += "??????????????????" + explosiveLocationMap.size(oid) + "?????????????????????;";
                props.put(HcaAnalysisContext.hcaRemarkFieldName,desc);
                //??????????????????-?????????????????????
                List<String> buildingOids = getBuildingOids(explosiveLocationMap.get(oid));
                insertHcaBuildingRefData(oid ,buildingOids);

            }
            if(specialLocationMap.size(oid) > 0){
                String desc =  MapUtil.getString(props,HcaAnalysisContext.hcaRemarkFieldName);
                desc += "????????????????????????" + specialLocationMap.size(oid) + "???????????????;";
                props.put(HcaAnalysisContext.hcaRemarkFieldName,desc);
                //??????????????????-?????????????????????
                List<String> buildingOids = getBuildingOids(specialLocationMap.get(oid));
                insertHcaBuildingRefData(oid ,buildingOids);
            }
        }

        logger.info("?????????????????????????????????{}?????????????????????",count0);

        Feature[] hcaDataAfterMerge = result.toArray(new Feature[0]);
        return hcaDataAfterMerge;
    }

    protected List<String> getBuildingOids(Feature[] features ){
        List<String> oids = new ArrayList<>();
        for(int i = 0 ; i < features.length ; i++){
            Map<String,Object> attr = features[i].getAttributes();
            if(attr != null && attr.containsKey(HcaAnalysisContext.TableKeyName)){
                oids.add(attr.get(HcaAnalysisContext.TableKeyName).toString());
            }
        }
        return oids ;
    }

    /**
     *
     * @param hcaOid
     * @param buildingOids
     * @return
     */
    protected int insertHcaBuildingRefData(String hcaOid ,List<String> buildingOids ){
        int size = buildingOids.size() ;
        List<Object[]> args = new ArrayList<>(size);
        for(int i = 0 ; i < size ; i++){
            String uuid = UUID.randomUUID().toString();
            String buildingOid = buildingOids.get(i);
            args.add( new Object[]{  uuid,  buildingOid ,hcaOid });
        }
        String sql = "insert into " + HcaAnalysisContext.highImpactAreaBuildingsRefSourceName + "(OID , BUILDING_OID ,HCA_HIGH_IMPACT_AREA_OID,ACTIVE) values(?,?,?,1)";
        int[] re = jdbcTemplate.batchUpdate(sql ,args );
        int sum =  MathUtil.sum(re) ;
        if(sum == size){
            logger.info("????????????{}????????????{}??????????????????",hcaOid ,sum);
        }
        return sum;
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
                pros.put( remarkFieldName,"??????????????????????????????;");
                count3++ ;
            }else if( HcaAnalysisContext.AreaGradeLevel_III.equalsIgnoreCase(rank)){
                pros.put( HcaAnalysisContext.hcaRankFieldName, HcaAnalysisContext.HcaLevel_II);
                pros.put( remarkFieldName,"??????????????????????????????;");
                count2++;
            }else{
                pros.put( HcaAnalysisContext.hcaRankFieldName,"" );
                pros.put( remarkFieldName,"");
                count1++;
            }
            feature.setAttributes(pros);
            hcaFeatures.add(feature) ;
        }
        logger.info("?????????????????????????????????????????????{}??????????????????{}?????????????????????{}??????" ,count3,count2,count1);

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
        loggerUtil.time("??????????????????????????????");
        //1???????????????
        String source = HcaAnalysisContext.hcaSourceName;


        int[] result = geodataAccessService.addFeatures(source,features) ;

        int count = MathUtil.sum( result ) ;
        if(count == 0 ){
            throw new RuntimeException("???????????????????????????????????????");
        }else{
            logger.info("????????????{}?????????????????????",count);
        }
        //2???????????????
        int hcaVersionFlg = hcaVersionService.initToSave(hcaVersionId ,pipelineOid ,1);
        if(hcaVersionFlg == 1){
            logger.info("????????????????????????????????????,oid=" + hcaVersionId  );
        }else{
            throw new RuntimeException("?????????????????????????????????oid=" + hcaVersionId );
        }
        //3?????????????????????
        String sql = "insert into hca_area_version_ref( oid ,hca_version_oid ,area_version_oid,active) values( ? ,? ,? ,?)";
        String oid = UUID.randomUUID().toString();
        Object[] params = new Object[]{ oid,hcaVersionId ,areaVersionId ,1};
        int flg = baseJdbcDao.save(sql ,params);
        if(flg == 1){
            logger.info("????????????????????????????????????????????????,oid=" + oid );
        }else{
            throw new RuntimeException("?????????????????????????????????????????????????????????oid=" + oid );
        }
        loggerUtil.timeEnd("??????????????????????????????");

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

        logger.info("????????????{}" ,key );
        Feature f = pipelineService.queryTargetPipelineData(key ,sourceName) ;
        Geometry p = GeometryUtil.toGeometry(f.getGeometry()) ;
        Map<String,Object> props = f.getAttributes() ;
        analysisSchema.setPipeline((Polyline) p);// ?????????????????????????????????
        if( p == null ){
            logger.error("?????????????????????????????????{}???????????????" ,key );
        }

        double totalMileage = analysisSchema.getTotalMileage();
        logger.info("??????{}???????????????{}??????" ,key ,String.format("%.3f",totalMileage/1000));

        if(!props.containsKey(HcaAnalysisContext.pipePressureFieldName)){
            logger.error("??????????????????????????????" + HcaAnalysisContext.pipePressureFieldName);
        }else{
            analysisSchema.setPressure(MapUtil.getDouble(props,HcaAnalysisContext.pipePressureFieldName));
        }
        if(!props.containsKey(HcaAnalysisContext.pipeOuterDiameterFieldName)){
            logger.error("??????????????????????????????" + HcaAnalysisContext.pipeOuterDiameterFieldName);
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
        loggerUtil.time("??????????????????????????????" );
        logger.info("???????????????" + versionId);
        LayerQueryParam param = new LayerQueryParam();
        param.setSrsname(HcaAnalysisContext.areaGradeSourceName);
        param.setWhere("active=1 and " + HcaAnalysisContext.versionIdFieldName + "='" + versionId + "'");
        param.setOutputFormat(null);
        param.setOutFields("*");
        param.setOrderBy("start_mileage");
        FeatureCollection collection = geodataAccessService.query(param);
        if(collection == null){
            throw new RuntimeException("?????????????????????????????????????????????????????????????????????");
        }
        Feature[] features = FeatureCollectionUtil.toLowerCaseFeature(collection);
        loggerUtil.timeEnd("??????????????????????????????","?????????" + features.length + "????????????????????????" );

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
            String domains = hcaCommonService.getCodeIdsByParentCodeId(HcaAnalysisContext.buildingTypeValue_SpecialPlace);
            exp = "building_type in(" + domains + ")" ;
            HcaAnalysisContext.setSpecialPlaceExpress(exp);
        }
        return exp;
    }

}
