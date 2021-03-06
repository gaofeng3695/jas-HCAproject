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
import cn.jasgroup.gis.geometryservice.AreaAndLength;
import cn.jasgroup.gis.geometryservice.IGeometryService;
import cn.jasgroup.gis.util.*;
import cn.jasgroup.hcas.analysis.*;
import cn.jasgroup.hcas.common.service.HcaCommonService;
import cn.jasgroup.hcas.versionmaanage.service.HcaVersionService;
import cn.jasgroup.jasframework.domain.utils.DomainUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @description TODO
 * @author kongchao
 * @version V1.0
 * @date 2019/6/21
 * @since JDK 1.80
 */
@Service
public class AreaGradeAnalysisService extends AnalysisBaseService implements IAreaGradeAnalysisService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected LoggerUtil loggerUtil = new LoggerUtil(logger) ;

    @Resource
    private IGeodataAccessService geodataAccessService;

    @Resource
    private IGeometryService geometryService;

    @Autowired
    private HcaVersionService hcaVersionService;

    @Autowired
    private PipelineService pipelineService ;

    @Autowired
    private HcaCommonService hcaCommonService;


    public static String middleMileageFieldName = "middle_mileage";
    public static String cellTypeFieldName = "region_level";
    public static String startMileageFieldName = HcaAnalysisContext.startMileageFieldName;
    public static String endMileageFieldName = HcaAnalysisContext.endMileageFieldName;
    public static String populationFieldName = HcaAnalysisContext.buildingsSourcePopulationFieldName;
    public static String householdsFieldName= HcaAnalysisContext.householdsFieldName;
    public static String buildingDistributionTypeFieldName= HcaAnalysisContext.buildingDistributionTypeFieldName;
    public static String verticalDistanceFieldName = HcaAnalysisContext.verticalDistanceFieldName ;
    public static String horizontalDistanceFieldName = HcaAnalysisContext.horizontalDistanceFieldName ;
    public static String startXFieldName = HcaAnalysisContext.startXFieldName ;
    public static String startYFieldName = HcaAnalysisContext.startYFieldName ;


    /**
     * 合并间距小于50米的居民地数据
     * @param buildingsData
     * @return
     */
    public Feature[] mergeBuildingsFeatureData(Feature[] buildingsData) {
        List<Feature> settlementList = Arrays.asList(buildingsData);
        sortFeaturesByMileage(settlementList);
        Feature[] settlementData = settlementList.toArray(new Feature[0]);

        String startMileageFieldName = HcaAnalysisContext.startMileageFieldName ;
        String endMileageFieldName = HcaAnalysisContext.endMileageFieldName ;
        double mergeDomainDistance = HcaAnalysisContext.ConfigSettlementMergeDistance;
        String mes = "合并间距小于" + mergeDomainDistance + "米的居民地";
        loggerUtil.time( mes );
        List<Feature> mergedFeatureList = new ArrayList<>() ;
        Feature last = settlementData[0];

        Double lastEndMileage = MapUtil.getDouble(last.getAttributes(),endMileageFieldName);
        mergedFeatureList.add(last);
        for(int i = 1; i < settlementData.length ; i++){
            Feature feature = settlementData[i];
            Map<String,Object> attr = feature.getAttributes();
            Double startMileage = MapUtil.getDouble(attr,startMileageFieldName);
            if( isBuildingNeedMerge( lastEndMileage,startMileage,mergeDomainDistance ) ){
                last = mergeFeatureProperties(feature ,last);
            }else{
                mergedFeatureList.add(feature);
                last = feature;
            }
            lastEndMileage = MapUtil.getDouble(last.getAttributes(),endMileageFieldName);
        }
        Feature[] result = mergedFeatureList.toArray(new Feature[0]);
        loggerUtil.timeEnd( mes ,"合并" + (settlementData.length - result.length) + "处居民地" );
        return result ;
    }

    private Boolean isBuildingNeedMerge(Double endMileage ,Double currentStartMileage ,Double domainDistance){
        return currentStartMileage != null && endMileage != null && Math.abs( currentStartMileage - endMileage) < domainDistance  ;
    }

    /**
     *
     * @param areaGradeFeatures
     * @return
     */
    public int saveFeaturesData(Feature[] areaGradeFeatures){
        String sourceName = HcaAnalysisContext.areaGradeSourceName;
        int[] re = geodataAccessService.addFeatures(sourceName,areaGradeFeatures);
        int count = MathUtil.sum(re);
        if(count == areaGradeFeatures.length ){
            logger.info( "地区等级计算结果保存成功" );
        }else{
            logger.warn( "地区等级计算结果{}条，实际保存{}条" ,areaGradeFeatures.length ,count);
        }
        return count;
    }

    public Feature mergeFeatureProperties(Feature from, Feature to) {
        if(to.getAttributes() == null ) {
            to.setAttributes(new HashMap());
        }
        Map<String,Object> fromProperties = from.getAttributes() ;
        Map<String,Object> toProperties = to.getAttributes() ;
        toProperties.put(populationFieldName ,MapUtil.getDouble(fromProperties,populationFieldName) + MapUtil.getDouble(toProperties,populationFieldName));
        toProperties.put(endMileageFieldName ,MapUtil.getDouble(fromProperties,endMileageFieldName));

        String buildingType = MapUtil.getString(fromProperties ,buildingDistributionTypeFieldName);
        String buildingTypeValue = DomainUtil.getValue(HcaAnalysisContext.BuildingDistributionDomain,buildingType);

        if(HcaAnalysisContext.HighBuildingDistributionType.equals(buildingTypeValue)){
            toProperties.put(buildingDistributionTypeFieldName,buildingTypeValue);
        }else{
            //toProperties.put(buildingDistributionTypeFieldName,buildingTypeValue);
        }
        return to;
    }

    /**
     *
     * @param cell
     * @return
     */
    private Boolean isCoreCell(Feature cell){
        Integer cellType = MapUtil.getInt(cell.getAttributes(),cellTypeFieldName,-1);
        if( cellType == 1 ){
            return true;
        }
        Double population = MapUtil.getDouble(cell.getAttributes(),populationFieldName,0d);
        Integer households = MapUtil.getInt(cell.getAttributes(),householdsFieldName,0);
        return population >= HcaAnalysisContext.ConfigCoreCellPopulationCondition1 || households >= HcaAnalysisContext.ConfigCoreCellHouseholdsCondition1;
    }

    private Boolean isCoreCellNeedToMerge(Feature last ,Feature current){
        double currentMiddleMileage = MapUtil.getDouble(current.getAttributes() ,middleMileageFieldName);
        double lastMiddleMileage = MapUtil.getDouble(last.getAttributes() ,middleMileageFieldName);
        return currentMiddleMileage - lastMiddleMileage <= HcaAnalysisContext.ConfigCoreCellStandardLength;
    }

    /**
     *
     * @param startMileage
     * @param endMileage
     * @return
     */
    private List<Feature> createStandardCells(Double startMileage ,Double endMileage  ){
        List<Feature> result = new ArrayList<>() ;
        double standardLength = HcaAnalysisContext.ConfigCoreCellStandardLength ;
        double subMileage = endMileage - startMileage;
        double start = startMileage;
        double end ;
        int type ;
        while(subMileage > 0){
            double ml = subMileage - standardLength;
            if(ml >= 0){
                end = start + standardLength;
                type = 2;
            }else {
                end = start + subMileage;
                type = 3 ;
            }
            Feature feature = createSingleCell(start,end,type);
            start = end;
            subMileage = ml;

            result.add(feature);
        }
        return result;
    }

    /**
     *
     * @param start
     * @param end
     * @param type 1:核心识别单元，2：标准识别单元，3：独立识别单元
     * @return
     */
    private Feature createSingleCell(double start ,double end,int type){
        Feature feature = new Feature();
        Map<String,Object> map = new HashMap<>() ;
        map.put(startMileageFieldName ,start) ;
        map.put(endMileageFieldName ,end) ;
        map.put(cellTypeFieldName ,type) ;
        feature.setAttributes(map);
        return feature;
    }

    private void expandStartAndEndMileage(Feature f , double deltMileage){
        Double startMileage = MapUtil.getDouble(f.getAttributes() ,startMileageFieldName)  - deltMileage ;
        Double endMileage = MapUtil.getDouble(f.getAttributes() ,endMileageFieldName) + deltMileage ;
        f.getAttributes() .put(startMileageFieldName,startMileage);
        f.getAttributes() .put(endMileageFieldName,endMileage);
    }

    private Feature createCoreCell(Map<String,Object> properties,int type){
        Feature feature = new Feature();
        Map<String,Object> attributes = new HashMap<>() ;
        Double startMileage = MapUtil.getDouble(properties ,startMileageFieldName) ;
        Double population = MapUtil.getDouble(properties ,populationFieldName);
        //Double households = MapUtil.getDouble(properties ,householdsFieldName);
        Double endMileage = MapUtil.getDouble(properties ,endMileageFieldName)  ;
        Double middleMileage = ( startMileage + endMileage ) / 2;
        String buildingType = MapUtil.getString( properties ,buildingDistributionTypeFieldName );
        attributes.put(cellTypeFieldName ,type);
        attributes.put(middleMileageFieldName,middleMileage);
        attributes.put(startMileageFieldName,startMileage);
        attributes.put(endMileageFieldName,endMileage);
        attributes.put(populationFieldName,population);
        //attributes.put(householdsFieldName,households);
        attributes.put(buildingDistributionTypeFieldName,buildingType);
        attributes.put("building_oid",MapUtil.getString(properties,"oid"));
        feature.setAttributes(attributes);
        return feature;
    }

    /**
     * 统计识别单元内的构筑物的人口和建筑类型
     * @param f
     * @param buildings
     */
    private void countPopulationByMileage(Feature f ,Feature[] buildings){
        double startMileage = MapUtil.getDouble(f.getAttributes() ,startMileageFieldName) ;
        double endMileage = MapUtil.getDouble(f.getAttributes() ,endMileageFieldName) ;
        int cellPopulation = 0;
        String cellBuildingType = HcaAnalysisContext.LowBuildingDistributionType;
        for(int i = 0 ; i < buildings.length ; ++i){
            Feature building = buildings[i] ;
            Map<String,Object> pros = building .getAttributes() ;
            double start = MapUtil.getDouble(pros ,startMileageFieldName) ;
            double end = MapUtil.getDouble(pros,endMileageFieldName) ;
            Double population = MapUtil.getDouble(pros,populationFieldName) ;
            String buildingType = MapUtil.getString(pros,buildingDistributionTypeFieldName);
            if((start >= startMileage && start <= endMileage) || (end >= startMileage && end <= endMileage)){
                if(start >= startMileage && end <= endMileage){
                    cellPopulation += population;
                }else if(start > startMileage && start < endMileage){
                    cellPopulation += (int)(  (endMileage - start) /(end - start) * population );
                }else if(end > startMileage && end < endMileage){
                    cellPopulation += (int)(  (end - startMileage) /(end - start) * population );
                }
                if(HcaAnalysisContext.HighBuildingDistributionType.equals(buildingType)){
                    cellBuildingType = buildingType;
                }
            }else{

            }
        }
        f.getAttributes().put(populationFieldName , cellPopulation * 1.0);
        f.getAttributes().put(buildingDistributionTypeFieldName ,cellBuildingType);
    }

    private void sortFeaturesByMileage(List<Feature> features ) {
        Collections.sort(features, new Comparator<Feature>() {
            @Override
            public int compare(Feature o1, Feature o2) {
                double start1 = MapUtil.getDouble(o1.getAttributes(),startMileageFieldName);
                double start2 = MapUtil.getDouble(o2.getAttributes(),startMileageFieldName);
                return (int)(start1 - start2) ;
            }
        });
    }

    /**
     *
     * @param features
     * @param versionOid
     * @param pipelineOid
     */
    private void prepareHcaAttributes(Feature[] features ,String versionOid ,String pipelineOid){
        for(int i = 0 ; i < features .length ; i++){
            Feature feature = features[i] ;
            feature.getAttributes().put("oid",UUID.randomUUID().toString());
            feature.getAttributes().put("active",1);
            feature.getAttributes().put("pipeline_oid",pipelineOid);
            feature.getAttributes().put("version_oid",versionOid);
            feature.getAttributes().put("area_code","area-" + (i + 1) );

            double startMileage = MapUtil.getDouble(feature.getAttributes(),startMileageFieldName,0d) / 1000;
            double endMileage = MapUtil.getDouble(feature.getAttributes(),endMileageFieldName,0d) / 1000;

            startMileage = new BigDecimal(startMileage).setScale(3 ,BigDecimal.ROUND_DOWN).doubleValue() ;
            endMileage =new BigDecimal(endMileage).setScale(3 ,BigDecimal.ROUND_DOWN).doubleValue();

            feature.getAttributes().put(startMileageFieldName,startMileage);
            feature.getAttributes().put(endMileageFieldName,endMileage);
            feature.getAttributes().put(HcaAnalysisContext.areaLengthFieldName,endMileage -startMileage );
        }
    }

    /**
     *
     * @param feature
     * @return
     */
    private double resetMiddleMileage(Feature feature){
        Map<String,Object> properties = feature.getAttributes();
        Double startMileage = MapUtil.getDouble(properties ,startMileageFieldName);
        Double endMileage = MapUtil.getDouble(properties ,endMileageFieldName);
        double  middleMileage =  (startMileage + endMileage ) / 2;
        properties.put(middleMileageFieldName,middleMileage);
        return  middleMileage;
    }

    /**
     * step1 : 查询管线数据，并计算识别缓冲区
     * @param key
     * @return
     */
    @Override
    public HcaLinearParam preparePipelineData(String key , Double buffer )  {
        String sourceName = HcaAnalysisContext.pipelineSourceName;
        HcaLinearParam analysisSchema = new HcaLinearParam();
        //
        logger.info("查询管线{}" ,key );
        Feature f = pipelineService.queryTargetPipelineData(key ,sourceName) ;
        Geometry p = GeometryUtil.toGeometry(f.getGeometry()) ;
        analysisSchema.setPipeline((Polyline) p);// 这里设置的是地理坐标！
        if( p == null ){
            logger.error("出错了，没有查询到管线{}坐标数据！" ,key );
        }
        double totalMileage = analysisSchema.getTotalMileage();
        logger.info("管线{}实际里程为{}千米" ,key ,String.format("%.3f",totalMileage/1000));

        analysisSchema.createRecognitionAreaBuffer(buffer);

        return analysisSchema;
    }

    /**
     *
     * @param bufferPg
     * @return
     */
    protected Feature[] querySettlementData(Geometry bufferPg){
        String buildingsSource = HcaAnalysisContext.buildingsSourceName;
        LayerQueryParam param = new LayerQueryParam() ;
        param.setSrsname( buildingsSource);
        param.setGeometryType( GeometryType.POLYGON.getType());
        param.setGeometry( bufferPg);
        param.setOutFields("*");
        param.setWhere( getUnSpecialQueryExpress());
        param.setInputSRID( bufferPg.getSpatialReference().getWkid());
        param.setOutputSRID( bufferPg.getSpatialReference().getWkid());
        param.setOrderBy(HcaAnalysisContext.startMileageFieldName);
        loggerUtil.time("查询识别区域内的居民地");
        FeatureCollection queryResult = geodataAccessService.query(param) ;
        Feature[] settlementData = FeatureCollectionUtil.toLowerCaseFeature(queryResult);
        return  settlementData;
    }
    /**
     * step:2
     * 1、识别区投影 ->平面坐标
     * 2、空间查询居民地数据
     * 3、居民地与识别区做相交计算，得到相交后的居民地
     * 4、由面积占比 计算人口、起止里程等属性数据
     *
     * @param bo
     * @return
     */
    @Override
    public Feature[] prepareBuildingsFeatureData(HcaLinearParam bo) {
        Geometry bufferPg = bo.getRecognitionAreaBuffer();
        //空间查询
        Feature[] settlementData = querySettlementData(bufferPg);
        int totalSize = settlementData.length;
        loggerUtil.timeEnd("查询识别区域内的居民地","查询到" + totalSize + "处居民地");
        if(totalSize == 0){
            throw new RuntimeException("识别区域内没有查询到居民地要素数据，请检查居民地数据或查询参数！");
        }
        //相交计算
        String populationFieldName = HcaAnalysisContext.buildingsSourcePopulationFieldName ;
        String startMileageFieldName = HcaAnalysisContext.startMileageFieldName ;
        String endMileageFieldName = HcaAnalysisContext.endMileageFieldName ;
        String verticalDistanceFieldName = HcaAnalysisContext.verticalDistanceFieldName ;
        String horizontalDistanceFieldName = HcaAnalysisContext.horizontalDistanceFieldName ;
        String startXFieldName = HcaAnalysisContext.startXFieldName ;
        String startYFieldName = HcaAnalysisContext.startYFieldName ;

        List<Feature> subList = new ArrayList<>() ;
        List<Geometry> geometriesList = new ArrayList<>() ;

        loggerUtil.time("识别区域居民地相交计算");
        Geometry[] interacts = new Geometry[settlementData.length];
        for(int i = 0 ; i < settlementData.length ; i++){
            Feature feature = settlementData[i];
            Geometry g = GeometryUtil.toGeometry(feature.getGeometry());
            interacts[i] = g;
        }
        Geometry[] geometries = new Geometry[0];
        if(interacts.length > 0){
            geometries = geometryService.intersect(interacts, bufferPg);
        }
        //相交计算的结果可能是点或线，这里只需处理面
        for(int i = 0 ; i < geometries.length ;i++){
            if(geometries != null && geometries[i] instanceof Polygon){
                subList.add(settlementData[i]);
                geometriesList.add(interacts[i]);//相交的居民地
                geometriesList.add(geometries[i]);//相交部分
            }
        }

        loggerUtil.timeEnd("识别区域居民地相交计算","查询到" + subList.size() + " 处居民地跨越缓冲区边界。");

        //由相交面积来计算人口
        loggerUtil.time("计算居民地人口、起始里程、水平距离、垂直距离等属性信息");
        //由相交的面积占比计算人口
        Geometry[] fsGeometries = geometriesList.toArray( new Geometry[0] );
        List<AreaAndLength> areaAndLengths = geometryService.areasAndLengths(fsGeometries);
        for(int i = 0 ; i < subList.size() ;++i){
            int idx = i * 2 ;
            Feature feature = subList.get(i) ;
            Map<String,Object> attributes = feature.getAttributes();
            Double population = MapUtil.getDouble( attributes ,populationFieldName ,0d);
            double originArea = areaAndLengths.get(idx).getArea();
            double subArea = areaAndLengths.get(idx+1).getArea();
            if(subArea - originArea > 0.0000001 ){
                logger.error("相交计算出错！" + attributes.get("oid") + " origin:" + originArea + ",sub:" + subArea);
                logger.error( fsGeometries[idx].toWKT());
                logger.error( fsGeometries[idx + 1].toWKT());
                logger.error( "--------------");
            }
            Double pop = (int)( population * subArea / originArea) * 1.0  ;
            attributes.put( populationFieldName,pop  );
            feature.setGeometry( fsGeometries[idx + 1]);
        }
        //重新计算里程值、垂直距离、水平距离等参数
        LinearReferenceUtil linearReferenceUtil = bo.getLinearReferenceUtil();

        for(int i = 0; i < settlementData.length; i++){
            Feature feature = settlementData[i];
            Map<String,Object> attributes = feature.getAttributes();
            Polygon polygon = (Polygon) GeometryUtil.toGeometry(feature.getGeometry());
            Point[] points = polygon.toPoints();//相交

            double startFraction = 1 ;
            double endFraction = 0d;
            double verticalLength = Double.POSITIVE_INFINITY ;
            /**
             * 计算多边形点的
             */
            for(int j = 0 ; j < points.length ;j++){
                double fraction = linearReferenceUtil.lineLocatePoint(points[j]);
                if(fraction > endFraction){
                    endFraction = fraction;
                }
                if(fraction < startFraction){
                    startFraction = fraction;
                }
                double length = linearReferenceUtil.getLinearPolyline().closestDistance(points[j]);
                if(length < verticalLength){
                    verticalLength = length ;
                }
            }
            double horizontalLength = linearReferenceUtil.getLinearPolyline().getTotalLength() * (endFraction - startFraction);
            Point start = linearReferenceUtil.lineInterpolatePoint(startFraction);
            Point end = linearReferenceUtil.lineInterpolatePoint(endFraction);
            double startX = start.getX() ;
            double startY = start.getY() ;
            if(start.getM() == null || end.getM() == null){
                logger.warn("没有有效的里程值 " + start.getM()  );
            }
            attributes.put(startMileageFieldName,start.getM());
            attributes.put(endMileageFieldName,end.getM());
            attributes.put(horizontalDistanceFieldName,horizontalLength);
            attributes.put(verticalDistanceFieldName,verticalLength);
            attributes.put(startXFieldName,startX);
            attributes.put(startYFieldName,startY);
        }
        loggerUtil.timeEnd("计算居民地人口、起始里程、水平距离、垂直距离等属性信息");

        return mergeBuildingsFeatureData(settlementData);
    }

    /**
     *
     * @param pipelineOid
     * @param buffer
     * @return
     */
    @Override
    public HcaAnalysisResult executeAnalysis(String pipelineOid, Double buffer) {

        String versionOid = UUID.randomUUID().toString();
        loggerUtil.time("地区等级划分，OID=" + versionOid);

        HcaAnalysisResult hcaAnalysisResult = new HcaAnalysisResult();
        HcaLinearParam analysisGeometryBO = preparePipelineData(pipelineOid ,buffer) ;
        Feature[] settlementFeatures = prepareBuildingsFeatureData(analysisGeometryBO);
        if( settlementFeatures == null || settlementFeatures.length == 0 ){
            logger.error("没有查询到构筑物数据，分析计算退出！");
            hcaAnalysisResult.setTotal(0);
            return hcaAnalysisResult;
        }
        Feature[] cellFeatures = classifyAreaRankCellFeatures(settlementFeatures ,analysisGeometryBO);

        Feature[] resultFeatures = classifyAreaRankGradeFeatures(cellFeatures,analysisGeometryBO);


        prepareHcaAttributes( resultFeatures,versionOid ,pipelineOid);

        int count = saveFeaturesData(resultFeatures);
        int flg = hcaVersionService.initToSave(versionOid ,pipelineOid ,0);
        if(flg == 1){
            logger.info("地区等级版本数据保存成功。" );
        }else{
            logger.error("地区等级版本数据保存出错！oid=" + versionOid);
        }
        hcaAnalysisResult.setVersionId( versionOid);
        hcaAnalysisResult.setTotal( count);
        //暂时不返回要素结果，前端通过更新图层获取计算结果数据
        //hcaAnalysisResult.setFeatures( resultFeatures);
        loggerUtil.timeEnd("地区等级划分，OID=" + versionOid);

        return hcaAnalysisResult;
    }

    /**
     *
     * @param buildings
     * @return
     */
    protected List<Feature> classifyCoreCells(Feature[] buildings){
        List<Feature> coreCellList = new ArrayList<>() ;
        //1、核心识别单元
        for( int i = 0 ; i < buildings.length ; ++i){
            Feature feature = buildings[i] ;
            Boolean isCoreCell = isCoreCell(feature);
            if( isCoreCell){
                Feature core = createCoreCell(feature.getAttributes(),1);
                //Double startMileage = MapUtil.getDouble(core.getAttributes() ,startMileageFieldName);
                //Double endMileage = MapUtil.getDouble(core.getAttributes() ,endMileageFieldName);
                //Double newStartMileage = scopes.checkStartMileageBorder(startMileage,AnalysisConfig.ConfigAreaRankBorderBufferDistance );
                //Double newEndMileage = scopes.checkEndMileageBorder(endMileage,AnalysisConfig.ConfigAreaRankBorderBufferDistance );
                //feature.getAttributes().put(startMileageFieldName,newStartMileage);
                //feature.getAttributes().put(endMileageFieldName,newEndMileage);
                coreCellList.add( core );
            }
        }
        return coreCellList;
    }

    /**
     *
     * @param coreCellList
     * @return
     */
    protected List<Feature> mergeCoreCells(List<Feature> coreCellList){
        Feature last = coreCellList.get(0);
        List<Feature> mergedCoreCellList = new ArrayList<>() ;
        mergedCoreCellList.add(last);
        for(int i = 1 ; i < coreCellList.size() ;++i){
            Feature feature = coreCellList.get(i) ;
            if(isCoreCellNeedToMerge(last,feature)){
                last = mergeFeatureProperties(feature,last);
                resetMiddleMileage(last);
            }else{
                mergedCoreCellList.add(feature);
                last = feature;
            }
        }
        return mergedCoreCellList;
    }

    /**
     * 扩充核心识别单元边界
     * @param cellList
     * @param scopes
     */
    protected void expendCellBorder(List<Feature> cellList ,BuildingsMileageScope scopes){
        for(int i= 0; i < cellList.size() ; i++){
            Feature feature = cellList.get(i) ;
            Double startMileage = MapUtil.getDouble(feature.getAttributes() ,startMileageFieldName);
            Double endMileage = MapUtil.getDouble(feature.getAttributes() ,endMileageFieldName);
            Double newStartMileage = scopes.checkStartMileageBorder(startMileage, HcaAnalysisContext.ConfigAreaRankBorderBufferDistance );
            Double newEndMileage = scopes.checkEndMileageBorder(endMileage, HcaAnalysisContext.ConfigAreaRankBorderBufferDistance );
            feature.getAttributes().put(startMileageFieldName,newStartMileage);
            feature.getAttributes().put(endMileageFieldName,newEndMileage);
        }
    }

    /**
     *
     * @param buildings
     * @param bo
     * @return
     */
    public List<Feature> classifyCoreCellFeatures(Feature[] buildings,HcaLinearParam bo){
        //识别单元边界处理工具类
        BuildingsMileageScope scopes = new BuildingsMileageScope(buildings);
        scopes.setMinMileage(0d);
        scopes.setMaxMileage(bo.getTotalMileage());

        loggerUtil.time("划分识别单元");
        List<Feature> coreCellList = classifyCoreCells(buildings);
        if(coreCellList.size() == 0){
            logger.warn("没有划分出核心识别单元，请检查构筑属性信息中人口或建筑类型数据是否完整！");
            return new ArrayList<>(0);
        }
        //2、中心间距小于2公里合并核心识别单元
        List<Feature> mergedCoreCellList = mergeCoreCells(coreCellList);
        logger.info("合并"+ ( coreCellList.size() - mergedCoreCellList.size() ) + "个核心识别单元" );
        //3.两侧边界扩充200m
        expendCellBorder(mergedCoreCellList,scopes);

        return  mergedCoreCellList ;
    }

    /**
     *
     * @param buildings
     * @param coreCellList
     * @return
     */
    public List<Feature> classifyStandardCellFeatures(Feature[] buildings ,List<Feature> coreCellList){
        int coreCellSize = coreCellList.size() ;
        List<Feature> dataList = null;
        if(coreCellSize == 0 ){
            dataList = Arrays.asList(buildings);
        }else{
            dataList = coreCellList;
        }
        Double currentStartMileage = 0d ;
        List<Feature> cellsList = new ArrayList<>() ;
        for(int i = 0 ; i < dataList.size() ;i++ ){
            Feature coreCell = dataList.get(i);
            Map<String ,Object> attr = coreCell.getAttributes();
            Double startMileage = MapUtil.getDouble(attr,startMileageFieldName);
            Double endMileage = MapUtil.getDouble(attr,endMileageFieldName);
            Double subMileage = startMileage - currentStartMileage;
            if( subMileage > 0 ){
                List<Feature> subList = createStandardCells( currentStartMileage,startMileage );
                logger.info("里程值" + String.format("%.3f",subMileage ) + "创建识别单元" + subList.size() + "个");
                cellsList.addAll( subList ) ;
            }
            currentStartMileage = endMileage;
            cellsList.add(coreCell);
        }
        int standardCellSize = cellsList .size() - coreCellSize;
        //sortFeaturesByMileage(mergedCoreCellList);
        loggerUtil.timeEnd("划分识别单元" ,"核心识别单元" + coreCellSize + "个 ，标准识别单元和独立识别单元" + standardCellSize + "个");
        //4、统计人口
        for(int i = 0 ; i < cellsList.size() ; i++){
            Feature feature = cellsList.get(i) ;
            countPopulationByMileage(feature ,buildings) ;
        }
        return cellsList;
    }

    /**
     * 划分识别单元
     * 1、划分核心识别单元 ；
     * 2、划分标准和独立识别单元  ；
     * 3、边界处理  ？
     * @param buildings
     * @param bo
     * @return
     */
    @Override
    public Feature[] classifyAreaRankCellFeatures(Feature[] buildings, HcaLinearParam bo) {
        if(buildings.length == 0){
            logger.warn("识别区域内没有查询到构筑物数据！");
            return new Feature[0] ;
        }
        //划分核心识别单元
        List<Feature> mergedCoreCellList = classifyCoreCellFeatures(buildings,bo);
        //3、标准识别单元和独立识别单元
        List<Feature> cellsList = classifyStandardCellFeatures(buildings,mergedCoreCellList) ;
        //4、
        return cellsList.toArray(new Feature[0]);
    }

    /**
     *
     * @param features
     * @param bo
     * @return
     */
    @Override
    public Feature[] classifyAreaRankGradeFeatures(Feature[] features, HcaLinearParam bo){
        //1、地区等级划分
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        for(int i = 0 ; i < features.length ; i++) {
            Feature feature = features[i];
            Map<String, Object> attr = feature.getAttributes();
            Double population = MapUtil.getDouble(attr, populationFieldName);
            Integer households = (int) (population / HcaAnalysisContext.ConfigHouseholdsPopulationRadio);
            String buildingType = MapUtil.getString( attr, buildingDistributionTypeFieldName);
            double startMileage = MapUtil.getDouble( attr, startMileageFieldName);
            double endMileage = MapUtil.getDouble( attr, endMileageFieldName);
            String description = "";
            String rankValue  ;
            if (households >= HcaAnalysisContext.ConfigAreaRankHouseholdsCondition1 && HcaAnalysisContext.HighBuildingDistributionType.equals(buildingType)) {
                startMileage = startMileage - HcaAnalysisContext.ConfigAreaRankBorderBufferDistance;
                endMileage = endMileage + HcaAnalysisContext.ConfigAreaRankBorderBufferDistance;
                description = "识别单元人口户数为：" + households + ";建筑类型为：" + buildingType;
                rankValue = HcaAnalysisContext.AreaGradeLevel_IV;
                count4++;
            } else if (households >= HcaAnalysisContext.ConfigAreaRankHouseholdsCondition1 && HcaAnalysisContext.LowBuildingDistributionType.equals(buildingType)) {
                startMileage = startMileage - HcaAnalysisContext.ConfigAreaRankBorderBufferDistance;
                endMileage = endMileage + HcaAnalysisContext.ConfigAreaRankBorderBufferDistance;
                description = "识别单元人口户数为：" + households + ";建筑类型为：" + buildingType;
                rankValue = HcaAnalysisContext.AreaGradeLevel_III;
                count3++;
            } else if (households >= HcaAnalysisContext.ConfigAreaRankHouseholdsCondition2 && households < HcaAnalysisContext. ConfigAreaRankHouseholdsCondition1) {
                startMileage = startMileage - HcaAnalysisContext. ConfigAreaRankBorderBufferDistance;
                endMileage = endMileage + HcaAnalysisContext.ConfigAreaRankBorderBufferDistance;
                description = "识别单元人口户数为：" + households + " ,大于（包含）" + HcaAnalysisContext.ConfigAreaRankHouseholdsCondition2 + "户，小于" + HcaAnalysisContext.ConfigAreaRankHouseholdsCondition1 + "户";
                rankValue = HcaAnalysisContext.AreaGradeLevel_II;
                count2++;
            } else {
                description = "识别单元人口户数为：" + households + " ,小于" + HcaAnalysisContext.ConfigAreaRankHouseholdsCondition1 + "户";
                rankValue = HcaAnalysisContext.AreaGradeLevel_I;
                count1++;
            }
            //feature.getAttributes().put(startMileage ,rankValue);
            feature.getAttributes().put(HcaAnalysisContext.areaRankFieldName ,rankValue);
            //feature.getAttributes().put(buildingDistributionTypeFieldName ,rankValue);
            feature.getAttributes().put(HcaAnalysisContext.areaRandRemarkFieldName ,description);
        }
        //
        double totalLength = bo.getTotalMileage();
        Feature last = features[features.length - 1] ;
        double lastMileage = new BigDecimal( MapUtil.getDouble(last.getAttributes() ,endMileageFieldName)).setScale(3,BigDecimal.ROUND_DOWN) .doubleValue();

        if( lastMileage < totalLength){
            Feature f = createSingleCell(lastMileage , totalLength, 3 );
            f.getAttributes().put(HcaAnalysisContext.areaRankFieldName ,HcaAnalysisContext.AreaGradeLevel_I);
            count1++;
            List<Feature> featureList = new ArrayList<>( Arrays.asList(features));
            featureList.add(f);
            features = featureList.toArray(new Feature[0]);
        }
        //2、生成几何对象
        for(int i = 0 ; i < features.length ;++i){
            Feature feature = features[i] ;
            double startMileage = MapUtil.getDouble(feature.getAttributes() ,startMileageFieldName) ;
            double endMileage = MapUtil.getDouble(feature.getAttributes(),endMileageFieldName) ;
            String oid = MapUtil.getString(feature.getAttributes(),"building_oid") ;

            if(startMileage > endMileage){
                logger.error( "识别单元" + oid + "起始的测量值" + startMileage + "不能大于结束测量值" + endMileage);
            }
            if(startMileage < 0 || endMileage < 0){
                logger.error( "识别单元" + oid + "起止里程值【{},{}】计算错误，" ,startMileage,endMileage);

            }
            Polyline po =  bo.getLinearReferenceUtil().locateBetween(startMileage,endMileage,0d);
            Geometry buffer = JtsUtil.buffer(po, HcaAnalysisContext.ConfigRankAreaBufferDistance,2); //直角的buffer
            //Geometry gg = geometryService.transform(buffer, pcsSrid, gcsSrid);
            Geometry gg = GeometryUtil.gaussToBL(buffer);
            feature.setGeometry(gg);
        }
        logger.info("地区等级划分结束，四级地区" + count4 + "处，三级地区"+ count3 + "处，二级地区" + count2 + "处 ，一级地区"+ count1 + "处。");
        return  features;
    }

    /**
     * 获取非特定场所
     * @return
     */
    private String getUnSpecialQueryExpress(){
        String exp = HcaAnalysisContext.getUnSpecialPlaceExpress();
        if(StringUtil.isBlank(exp)){
            String domains = hcaCommonService.getCodeIdsByParentCodeId(HcaAnalysisContext.buildingTypeValue_UnSpecialPlace);
            exp = "building_type in(" + domains + ")" ;
            HcaAnalysisContext.setUnSpecialPlaceExpress(exp);
        }
        return exp;
    }

}
