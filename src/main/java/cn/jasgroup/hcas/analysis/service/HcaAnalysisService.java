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
import cn.jasgroup.hcas.areamanage.service.HcaAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by kc on 2019/5/28.
 */
@Service
public class HcaAnalysisService extends AnalysisBaseService implements IAnalysisService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected LoggerUtil loggerUtil = new LoggerUtil(logger) ;

    @Resource
    private IGeodataAccessService geodataAccessService;

    @Resource
    private IGeometryService geometryService;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private HcaAreaService hcaAreaService;


    public static String middleMileageFieldName = "middle_mileage";
    public static String cellTypeFieldName = "cell_type";
    public static String startMileageFieldName = AnalysisConfig.startMileageFieldName;
    public static String endMileageFieldName = AnalysisConfig.endMileageFieldName;
    public static String populationFieldName = AnalysisConfig.buildingsSourcePopulationFieldName;
    public static String householdsFieldName= AnalysisConfig.householdsFieldName;
    public static String buildingTypeFieldName= AnalysisConfig.buildingTypeFieldName;
    public static String verticalDistanceFieldName = AnalysisConfig.verticalDistanceFieldName ;
    public static String horizontalDistanceFieldName = AnalysisConfig.horizontalDistanceFieldName ;
    public static String startXFieldName = AnalysisConfig.startXFieldName ;
    public static String startYFieldName = AnalysisConfig.startYFieldName ;


    /**
     * 查询管线信息
     * @param key
     * @param sourceName
     * @return
     * @throws Exception
     */
    private Polyline queryTargetPipelineData(String key , String sourceName)  {
        LayerQueryParam param = new LayerQueryParam();
        param.setSrsname(sourceName);
        param.setOutputFormat(null);//返回对象 或传入WKT
        param.setWhere(AnalysisConfig.TableKeyName +"='"+ key + "'");
        FeatureCollection collection = geodataAccessService.query(param);
        if(collection.getFeatures().size() > 0){
            Feature feature = (Feature)collection.getFeatures().get(0);
            return (Polyline) GeometryUtil.toGeometry(feature.getGeometry());
        }
        return null;
    }
    /**
     *
     * @param polyline
     * @return
     */
    private Double queryPipelineMileage(Polyline polyline) {
        Geometry[] geometries = new Geometry[1];
        geometries[0] = polyline ;
        Double[] lengths = geometryService.lengths(geometries);
        DecimalFormat f = new DecimalFormat("#.######");
        return Double.valueOf(f.format(lengths[0]/1000d)) ;

    }
    /**
     * step1 : 查询管线数据，并计算识别缓冲区
     * @param key
     * @return
     */
    @Override
    public AnalysisGeometryBO preparePipelineData(String key , Double buffer )  {
        String sourceName = AnalysisConfig.pipelineSourceName;
        AnalysisGeometryBO analysisSchema = new AnalysisGeometryBO();
        //
        logger.info("查询管线{}" ,key );
        Geometry p = queryTargetPipelineData(key ,sourceName) ;
        if(p == null ){
            logger.error("出错了，没有查询到管线{}坐标数据！" ,key );
        }
        p =  geometryService.transform(p,p.getSpatialReference().getWkid(),3857);
        analysisSchema.setPipeline((Polyline) p);
        Double totalMileage = queryPipelineMileage((Polyline) p);
        analysisSchema.setTotalMileage(totalMileage);
        logger.info("管线{}实际里程为{}千米" ,key ,totalMileage);
        Polygon bufferArea = (Polygon) geometryService.buffer(p,buffer);
        analysisSchema.setRecognitionAreaBuffer(bufferArea);
        return analysisSchema;
    }

    @Override
    public Feature[] prepareBuildingsFeatureData(AnalysisGeometryBO bo) {
        Polygon bufferPg = bo.getRecognitionAreaBuffer();
        String bufferWKT = GeometryUtil.toWKT(bufferPg);
        String buildingsSource = AnalysisConfig.buildingsSourceName;
        LayerQueryParam param = new LayerQueryParam() ;
        param.setSrsname(buildingsSource);
        param.setGeometryType(GeometryType.POLYGON.name());
        param.setGeometry(bufferWKT);
        param.setOutFields("*");
        param.setWhere("1=1");// where ?
        param.setOrderBy("start_mileage");

        loggerUtil.time("查询识别区域相交的居民地");
        FeatureCollection queryResult = geodataAccessService.query(param) ;
        Feature[] settlementData = queryResult.getFeatures().toArray(new Feature[0]);
        int totalSize = settlementData.length;
        loggerUtil.timeEnd("查询识别区域内的居民地","查询到" + totalSize + "处居民地");

        //相交计算
        String populationFieldName = AnalysisConfig.buildingsSourcePopulationFieldName ;
        String startMileageFieldName = AnalysisConfig.startMileageFieldName ;
        String endMileageFieldName = AnalysisConfig.endMileageFieldName ;
        String verticalDistanceFieldName = AnalysisConfig.verticalDistanceFieldName ;
        String horizontalDistanceFieldName = AnalysisConfig.horizontalDistanceFieldName ;
        String startXFieldName = AnalysisConfig.startXFieldName ;
        String startYFieldName = AnalysisConfig.startYFieldName ;

        List<Feature> subList = new ArrayList<>() ;
        List<Geometry> geometriesList = new ArrayList<>() ;

        loggerUtil.time("识别区域居民地相交计算");
        for(int i = 0 ; i < settlementData.length ; i++){
            Feature feature = settlementData[i];
            Geometry g = GeometryUtil.toGeometry(feature.getGeometry());
            Geometry[] geometries = geometryService.intersect(new Geometry[]{g}, bufferPg);
            //相交计算的结果可能是点或线，这里只需处理面
            if(geometries != null && geometries[0] instanceof Polygon){
                Polygon p = (Polygon) geometries[0];
                subList.add(feature);
                geometriesList.add(g);
                geometriesList.add(p);
            }
        }
        loggerUtil.timeEnd("识别区域居民地相交计算","查询到" + subList.size() + " 处居民地跨越缓冲区边界，需要相交计算。");

        //由相交面积来计算人口
        loggerUtil.time("计算居民地人口、起始里程、水平距离、垂直距离等属性信息");
        Geometry[] fsGeometries = geometriesList.toArray( new Geometry[0] );
        List<AreaAndLength> areaAndLengths = geometryService.areasAndLengths(fsGeometries);
        for(int i = 0 ; i < areaAndLengths.size() ;++i){
            Feature feature = subList.get(i) ;
            Map<String,Object> attributes = feature.getAttributes();
            int population = MapUtil.getInt(attributes ,populationFieldName);
            double originArea = areaAndLengths.get(i).getArea();
            double subArea = areaAndLengths.get(++i).getArea();
            int pop = (int)( population * subArea / originArea) ;
            attributes.put(populationFieldName,pop);
            feature.setGeometry(geometriesList.get(i));
        }
        //重新计算里程值、垂直距离、水平距离等参数
        LinearReferenceUtil linearReferenceUtil = bo.getLinearReferenceUtil();

        for(int i = 0; i < settlementData.length; i++){
            Feature feature = settlementData[i];
            Map<String,Object> attributes = feature.getAttributes();
            Polygon polygon = (Polygon) GeometryUtil.toGeometry(feature.getGeometry());
            Point[] points = polygon.getCoordinates();//相交

            double startFraction = 1 ;
            double endFraction = 0d;
            double verticalLength = 0d / 0 ;

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

            attributes.put(startMileageFieldName,start.getM());
            attributes.put(endMileageFieldName,end.getM());
            attributes.put(horizontalDistanceFieldName,horizontalLength);
            attributes.put(verticalDistanceFieldName,verticalLength);
            attributes.put(startXFieldName,startX);
            attributes.put(startYFieldName,startY);
        }
        loggerUtil.timeEnd("计算居民地人口、起始里程、水平距离、垂直距离等属性信息");
        return settlementData;
    }

    @Override
    public Feature[] mergeBuildingsFeatureData(Feature[] settlementData) {
        String startMileageFieldName = AnalysisConfig.startMileageFieldName ;
        String endMileageFieldName = AnalysisConfig.endMileageFieldName ;
        double mergeDomainDistance = AnalysisConfig.CongfigSettlementMergeDistance;
        String mes = "合并间距小于" + mergeDomainDistance + "米的居民地";
        loggerUtil.time( mes );
        List<Feature> mergedFeatureList = new ArrayList<>() ;
        Feature currentFeature = settlementData[0];
        Double currentEndMileage = MapUtil.getDouble(currentFeature.getAttributes(),endMileageFieldName);
        mergedFeatureList.add(currentFeature);
        //
        for(int i = 1; i < settlementData.length ; i++){
            Feature feature = settlementData[i];
            Double startMileage = MapUtil.getDouble(feature.getAttributes(),startMileageFieldName);
            if(startMileage - currentEndMileage > mergeDomainDistance   ){
                // 不需要合并
                mergedFeatureList.add(feature);
                currentFeature = feature;
            }else{
                currentFeature = mergeFeatureProperties(feature ,currentFeature);
            }
            currentEndMileage = MapUtil.getDouble(currentFeature.getAttributes(),endMileageFieldName);
        }
        Feature[] result = mergedFeatureList.toArray(new Feature[0]);
        loggerUtil.timeEnd( mes ,"合并" + (settlementData.length - result.length) + "处居民地" );
        return result ;
    }

    /**
     *
     * @param pipelineOid
     * @param buffer
     * @return
     */
    @Override
    public AnalysisResult executeAreaGradeAnalysis(String pipelineOid, Double buffer) {
        AnalysisGeometryBO analysisGeometryBO = preparePipelineData(pipelineOid ,buffer) ;
        Feature[] settlementFeatures = prepareBuildingsFeatureData(analysisGeometryBO);
        Feature[] mergedFeatures = mergeBuildingsFeatureData(settlementFeatures);
        Feature[] resultFeatures = classifyAreaRankGradeFeatures(mergedFeatures ,analysisGeometryBO);

        AnalysisResult analysisResult = new AnalysisResult();
        analysisResult.setTotal(0);
        analysisResult.setVersionId("");
        return null;
    }

    @Override
    public Feature mergeFeatureProperties(Feature from, Feature to) {
        if(to.getAttributes() == null ) {
            to.setAttributes(new HashMap());
        }
        Map<String,Object> fromProperties = from.getAttributes() ;
        Map<String,Object> toProperties = to.getAttributes() ;
        toProperties.put(populationFieldName ,MapUtil.getInt(fromProperties,populationFieldName) + MapUtil.getInt(toProperties,populationFieldName));
        toProperties.put(endMileageFieldName ,MapUtil.getInt(fromProperties,endMileageFieldName));
        String buildingType = MapUtil.getString(fromProperties ,buildingTypeFieldName);
        if(AnalysisConfig.HighBuildingType.equals(buildingType)){
            toProperties.put(buildingTypeFieldName,buildingType);
        }
        return to;
    }

    /**
     *
     * @param cell
     * @return
     */
    private Boolean isCoreCell(Feature cell){
        Integer cellType = MapUtil.getInt(cell.getAttributes(),cellTypeFieldName);
        if( cellType == 1 ){
            return true;
        }
        Integer population = MapUtil.getInt(cell.getAttributes(),populationFieldName);
        Integer households = MapUtil.getInt(cell.getAttributes(),householdsFieldName);
        return population >= AnalysisConfig.CongfigCoreCellPopulationCondition1 || households >= AnalysisConfig.CongfigCoreCellHouseholdsCondition1;
    }
    private Boolean isCoreCellNeedToMerge(Feature last ,Feature current){
        double currentMiddleMileage = MapUtil.getDouble(current.getAttributes() ,middleMileageFieldName);
        double lastMiddleMileage = MapUtil.getDouble(last.getAttributes() ,middleMileageFieldName);
        return currentMiddleMileage - lastMiddleMileage <= AnalysisConfig.CongfigCoreCellStandardLength;
    }
    private Boolean isCellNeedToMerge(Feature first ,Feature second){
        double currentStartMileage = MapUtil.getDouble(second.getAttributes() ,startMileageFieldName);
        double lastEndMileage = MapUtil.getDouble(first.getAttributes(),endMileageFieldName);
        return currentStartMileage - lastEndMileage <= AnalysisConfig.CongfigAreaRankBorderBufferDistance;
    }

    /**
     *
     * @param buildings
     * @param bo
     * @return
     */
    @Override
    public Feature[] classifyAreaRankGradeFeatures(Feature[] buildings, AnalysisGeometryBO bo) {
        List<Feature> coreCellList = new ArrayList<>() ;
        loggerUtil.time("划分核心识别单元");
        //copy buildings
        int cellIndex = 1;
        Feature currentCell = buildings[0] ;
        while(cellIndex < buildings.length){
            Feature next = buildings[cellIndex] ;
            if(isCoreCell(currentCell) || isCoreCell(next)){
                Boolean isMerge = isCellNeedToMerge(currentCell ,next);
                if(isMerge){
                    currentCell = mergeFeatureProperties(next,currentCell);
                }else{
                    currentCell = next ;
                }
                cellIndex++ ;
            }
        }
        for( int i = 0 ; i < buildings.length ; ++i){
            Feature feature = buildings[i] ;
            Boolean isCoreCell = isCoreCell(feature);
            //识别核心识别单元
            if( isCoreCell){
                Feature core = createCoreCell(feature.getAttributes(),1);
                expandStartAndEndMileage(core,AnalysisConfig.CongfigAreaRankBorderBufferDistance);//
                coreCellList.add(core);
            }
        }
        //2、中心间距小于2公里合并核心识别单元
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
        loggerUtil.timeEnd("划分核心识别单元" );

        //3、标准识别单元和独立识别单元
        Double currentStartMileage = 0d ;
        for(int i = 0 ; i < mergedCoreCellList.size() ;i++ ){
            Feature coreCell = mergedCoreCellList.get(i);
            Map<String ,Object> attr = coreCell.getAttributes();
            Double startMileage = MapUtil.getDouble(attr,startMileageFieldName);
            Double endMileage = MapUtil.getDouble(attr,endMileageFieldName);
            Double subMileage = startMileage - currentStartMileage;
            if(subMileage > 0){
                List<Feature> subList = createStandardCells(currentStartMileage,startMileage,buildings );
                mergedCoreCellList.addAll(subList) ;
            }
            currentStartMileage = endMileage;
        }
        // 排序
        sortFeaturesByMileage(mergedCoreCellList);
        //4、边界处理

        //5、生成几何对象
        for(int i = 0 ; i < mergedCoreCellList.size() ;++i){
            Feature feature = mergedCoreCellList.get(i) ;
            double startMileage = MapUtil.getDouble(feature.getAttributes() ,startMileageFieldName) ;
            double endMileage = MapUtil.getDouble(feature.getAttributes(),endMileageFieldName) ;
            Polyline po =  bo.getLinearReferenceUtil().locateBetween(startMileage,endMileage,0d);
            Polygon buffer = JtsUtil.buffer(po,AnalysisConfig.CongfigRankAreaBufferDistance,3); //直角的buffer
            feature.setGeometry(buffer);
        }
        return mergedCoreCellList.toArray(new Feature[0]);
    }
    private double resetMiddleMileage(Feature feature){
        Map<String,Object> properties = feature.getAttributes();
        Double startMileage = MapUtil.getDouble(properties ,startMileageFieldName);
        Double endMileage = MapUtil.getDouble(properties ,endMileageFieldName);
        double  middleMileage =  (startMileage + endMileage ) / 2;
        properties.put(middleMileageFieldName,middleMileage);
        return  middleMileage;
    }

    /**
     *
     * @param startMileage
     * @param endMileage
     * @param buildings
     * @return
     */
    private List<Feature> createStandardCells(Double startMileage ,Double endMileage ,Feature[] buildings ){
        List<Feature> result = new ArrayList<>() ;
        double standardLength = AnalysisConfig.CongfigCoreCellStandardLength ;
        double subMileage = endMileage - startMileage;
        while(subMileage > 0){
            double ml = subMileage - standardLength;
            Feature feature ;
            if(ml >= 0){
                feature = createSingleCell(startMileage,startMileage + standardLength,2);
            }else {
                feature = createSingleCell(startMileage,startMileage + subMileage,3);
            }
            subMileage = ml;
            countPopulationByMileage(feature ,buildings) ;
            result.add(feature);
        }
        return result;
    }
    /**
     *
     * @param start
     * @param end
     * @param type
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
        //Double population = MapUtil.getDouble(properties ,populationFieldName);
        //Double households = MapUtil.getDouble(properties ,householdsFieldName);
        Double endMileage = MapUtil.getDouble(properties ,endMileageFieldName)  ;
        Double middleMileage = ( startMileage + endMileage ) / 2;
        String buildingType = MapUtil.getString( properties ,buildingTypeFieldName );
        attributes.put(cellTypeFieldName ,type);
        attributes.put(middleMileageFieldName,middleMileage);
        attributes.put(startMileageFieldName,startMileage);
        attributes.put(endMileageFieldName,endMileage);
        //attributes.put(populationFieldName,population);
        //attributes.put(householdsFieldName,households);
        attributes.put(buildingTypeFieldName,buildingType);
        //feature.setAttributes(attributes);
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
        int cellPopulation = MapUtil.getInt(f.getAttributes(),populationFieldName) ;
        String cellBuildingType = AnalysisConfig.LowBuildingType;
        for(int i = 0 ; i < buildings.length ; ++i){
            Feature building = buildings[i] ;
            Map<String,Object> pros = building .getAttributes() ;
            double start = MapUtil.getDouble(pros ,startMileageFieldName) ;
            double end = MapUtil.getDouble(pros,endMileageFieldName) ;
            int population = MapUtil.getInt(pros,populationFieldName) ;
            String buildingType = MapUtil.getString(pros,buildingTypeFieldName);
            if((start >= startMileage && start <= endMileage) || (end >= startMileage && end <= endMileage)){
                if(start >= startMileage && end <= endMileage){
                    cellPopulation += population;
                }else if(start > startMileage && start < endMileage){
                    cellPopulation += (int)(  (endMileage - start) /(end - start) * population );
                }else if(end > startMileage && end < endMileage){
                    cellPopulation += (int)(  (end - startMileage) /(end - start) * population );
                }
                if(AnalysisConfig.HighBuildingType.equals(buildingType)){
                    cellBuildingType = buildingType;
                }
            }else{

            }
        }
        f.getAttributes().put(populationFieldName ,cellPopulation);
        f.getAttributes().put(buildingTypeFieldName ,cellBuildingType);
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
     * @param param
     * @return
     */
    @Override
    public AnalysisResult executeHcaAnalysis(HcaParam param) {

        return null;
    }


}
