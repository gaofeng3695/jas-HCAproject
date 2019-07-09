package cn.jasgroup.hcas.controller;

import cn.jasgroup.framework.data.result.BaseResult;
import cn.jasgroup.framework.data.result.SimpleResult;
import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.data.FeatureCollection;
import cn.jasgroup.gis.data.FeatureObject;
import cn.jasgroup.gis.data.GeometryType;
import cn.jasgroup.gis.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.dataaccess.LayerQueryParam;
import cn.jasgroup.gis.geometry.Geometry;
import cn.jasgroup.gis.geometry.Point;
import cn.jasgroup.gis.geometry.Polygon;
import cn.jasgroup.gis.geometryservice.AreaAndLength;
import cn.jasgroup.gis.geometryservice.IGeometryService;
import cn.jasgroup.gis.translate.JsonAndFeatureTranslate;
import cn.jasgroup.gis.util.GeometryUtil;
import cn.jasgroup.gis.util.MapUtil;
import cn.jasgroup.gis.util.MathUtil;
import cn.jasgroup.gis.util.StringUtil;
import cn.jasgroup.hcas.areamanage.service.HcaAreaService;
import cn.jasgroup.jasframework.base.controller.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by kc on 2018/12/14.
 * 识别区、识别单元服务
 */
@Controller
@RequestMapping("/zone")
public class ZoneController extends BaseController {
	
	@Autowired
	private HcaAreaService hcaAreaService;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static Integer CongfigMaxPopulation = 1000 ;
    public static Integer CongfigSettlementMergeDistance = 50 ; //m
    public static Double CongfigRankAreaBufferDistance = 200d ; //m
    public static Double CongfigHouseholdsPopulationRadio = 3.5 ;
    public static Double CongfigCoreCellStandardLength = 2d ; //km
    public static Double CongfigAreaRankBorderBufferDistance = 0.2 ; //km

    public static Integer CongfigCoreCellPopulationCondition1 = 53 ;
    public static Integer CongfigCoreCellHouseholdsCondition1 = 15 ;

    public static Integer CongfigAreaRankHouseholdsCondition1 = 100 ;
    public static Integer CongfigAreaRankHouseholdsCondition2 = 15 ;

    @Resource(name="geodataAccessService")
    private IGeodataAccessService geodataAccessService;

    @Resource
    private IGeometryService geometryService;

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public String RankFieldName = "rank" ;
    public String DescriptionFieldName = "description" ;

    public String EvaString1 = "该地区为四级高后果区";
    public String EvaString2 = "该地区为三级高后果区";
    public String EvaString3 = "该地区存在易燃易爆场所";
    public String EvaString4 = "该地区存在特定场所";

    /**
     * 生成识别区
     * @param params
     * @return
     */
    @PostMapping(value = "area")
    @ResponseBody
    public BaseResult buildZoneBufferArea(@RequestBody Map<String ,Object> params){
        SimpleResult baseResult = new SimpleResult<String>();
        String pipelineSourceName = MapUtil.getString(params,"pipesegmentTableName","pd_pipesegment");
        String pipelineOid = MapUtil.getString(params,"pipesegmentKeyValue");
        String pipelineKeyName = MapUtil.getString(params,"pipesegmentKeyName","eventid");
        double bufferDistance = MapUtil.getDouble(params,"bufferDistance");// m
        int srid = 4490;
        String geomFieldName = "geom" ;
        //
        StringBuffer querySql = new StringBuffer();
        querySql.append(" SELECT st_astext(ST_SETSRID(ST_Buffer(ST_GeogFromText(St_asewkt(t.geom)), ?   ),? )) as geom from ") ;
        querySql.append( pipelineSourceName ) ;
        querySql.append(" as t where ") ;
        querySql.append( pipelineKeyName ) ;
        querySql.append( "=?" ) ;
        Object[] paramsArray = { bufferDistance ,srid ,pipelineOid };
        Map<String ,Object> resultMap = jdbcTemplate.queryForMap(querySql.toString() ,paramsArray);
        baseResult.setData( resultMap.get("geom").toString());
        return baseResult ;
    }

    /**
     *
     * @param start
     * @param end
     * @param buffer
     * @param pipeSourceName
     * @param pipeKeyValue
     * @param pipeKeyName
     * @param pipeGeomName
     * @return
     */
    private String queryBufferArea(double start ,double end ,double buffer,String pipeSourceName ,String pipeKeyValue ,String pipeKeyName ,String pipeGeomName){
        StringBuffer sql = new StringBuffer();
        sql.append("select st_asewkt(st_setsrid(St_buffer(St_geogfromtext(st_asewkt(ST_Force2D(ST_GeometryN(ST_locateBetween( a.").append(pipeGeomName).append(",:start,:end),1)))),:buffer,'endcap=flat join=round'),4490)) geom from ").append(pipeSourceName).append(" a where a.").append(pipeKeyName).append(" =:").append(pipeKeyName);
        Map<String,Object> param = new HashMap<>() ;
        param.put(pipeKeyName,pipeKeyValue);
        param.put("buffer",buffer);
        param.put("start",start);
        param.put("end",end);
        return namedParameterJdbcTemplate.queryForObject(sql.toString() ,param,String.class);
    }

    /**
     * 地区等级识别单元初始化
     * 由要素单元生成识别单元，并划分核心识别单元、标准识别单元、独立识别单元，合并核心识别单元
     * @param params
     * @return
     */
    @PostMapping(value = "rank/cell/init")
    @ResponseBody
    public BaseResult initAreaRankCells(@RequestBody Map<String ,Object> params) throws Exception {
        SimpleResult baseResult = new SimpleResult<>() ;
        Map<String,Object> calculateResult = new HashMap<>();

        StringUtil.time("识别单元划分");
        String pipelineSourceName = MapUtil.getString(params,"pipeSourceName","pd_pipesegment");
        String sourceName = MapUtil.getString(params,"sourceName","pd_arearankcell");
        String settlementSourceName = MapUtil.getString(params,"settlementSourceName","pd_settlement");
        String pipelineOid = MapUtil.getString(params,"pipeKeyValue");
        String pipelineKeyName = MapUtil.getString( params,"pipeKeyName","eventid");
        double bufferDistance = MapUtil.getDouble( params,"bufferDistance",CongfigRankAreaBufferDistance);// m
        Boolean deleteFlag = MapUtil.getBoolean( params,"deleteFlag",true);
        String geomName = MapUtil.getString(params ,"geomName" ,"geom") ;
        String areaSettlementSourceName = MapUtil.getString(params ,"areaSettlementSourceName" ,"pd_areasettlement") ;

        //查询管线实际里程
        double realMileage = queryRealMileage(pipelineSourceName,pipelineKeyName,pipelineOid,geomName);
        logger.info("管线{}实际里程为{}千米" ,pipelineOid ,realMileage);

        //1、查询要素单元
        Feature[] featuresBeforeMerge =  queryAndCutSettlementDataByPipeBuffer(areaSettlementSourceName,settlementSourceName,0,realMileage,bufferDistance ,pipelineSourceName,pipelineOid,pipelineKeyName,geomName);
        //Feature[] featuresBeforeMerge = querySettlementData(settlementSourceName,geomName);
        int sizeBeforeMerge = featuresBeforeMerge.length ;
        logger.info("查询到{}处要素单元" ,sizeBeforeMerge);
        StringUtil.time("合并要素单元");

        //2、居民地要素合并 ,距离小于50米合并成一个要素
        Map<String,Object> mergedResult = mergeFeatureData(featuresBeforeMerge) ;
        Feature[] featureArray = (Feature[]) mergedResult.get("features");

        int sizeAfterMerge = featureArray.length ;
        calculateResult.put("mergedSettlementOids" ,mergedResult.get("mergedOids")) ;
        StringUtil.timeEnd("合并要素单元");

        //message.append("合并后要素数量：").append(featureArray.length).append("\r");
        logger.info("合并{}处间隔距离小于50米的要素单元" ,sizeBeforeMerge - sizeAfterMerge);

        //3、合并起止里程有重合部分的要素，并生成识别单元
        List<Map<String ,Object>> dataList = calculateCellAttributes(featureArray);
        int cellsSize = dataList.size() ;
        StringUtil.time("生成识别单元" );

        //4、直接由居民地判别核心识别单元
        //判断条件 人口大于53或者户数大于15
        //如果相邻的两个核心识别单元中心点相距不足2公里，合并成一个核心识别单元
        List<Map<String,Object>> coreCells = new ArrayList<>() ;// 合并后的核心识别单元
        double lastCoreCellMiddleMileage = 0d ;
        Map<String,Object> lastCoreCell = null ;
        for(int i = 0 ; i < cellsSize ; i++){
            Map<String,Object> cell = dataList.get(i) ;
            Integer population = MapUtil.getInt(cell,"population",0);
            Integer households = (int)( population / CongfigHouseholdsPopulationRadio) ;
            Double middleMileage = MapUtil.getDouble(cell , "middle_mileage" );
            cell.put("households" ,households);
            if( population >= CongfigCoreCellPopulationCondition1 || households >= CongfigCoreCellHouseholdsCondition1){
                cell.put("cell_type" ,1); // 1：核心识别单元 ，2：标准识别单元 ，3：独立识别单元
                if( (middleMileage - lastCoreCellMiddleMileage) < CongfigCoreCellStandardLength){
                    lastCoreCell = mergeCoreCell(lastCoreCell,cell);
                }else{
                    if(lastCoreCell != null){
                        coreCells.add(lastCoreCell);
                    }
                    lastCoreCell = cell;
                }
                lastCoreCellMiddleMileage = MapUtil.getDouble(lastCoreCell,"middle_mileage");
            }
        }

        //核心识别单元边界处理,不足2公里，前后延申至2公里 ；相邻核心识别单元有遮盖，保留顺油气方向边界
        lastCoreCell = null ;
        double lastEndMileage = 0d ;
        for(int i = 0 ; i < coreCells.size() ; i++){
            Map<String,Object> cell = coreCells.get(i) ;
            double startMileage = MapUtil.getDouble(cell,"start_mileage" ,0d);
            double endMileage = MapUtil.getDouble(cell,"end_mileage" ,0d);
            double mileage = endMileage - startMileage;
            if(mileage < CongfigCoreCellStandardLength){
                double deltMileage = ( CongfigCoreCellStandardLength - mileage ) / 2;
                startMileage = startMileage - deltMileage  ;
                endMileage = endMileage + deltMileage ;
            }
            if(startMileage < 0){
                startMileage = 0d ;
            }
            if(endMileage > realMileage){
                endMileage = realMileage;
            }
            cell.put("start_mileage",startMileage);
            cell.put("end_mileage",endMileage);

            if( startMileage < lastEndMileage){
                lastCoreCell.put("end_mileage" ,startMileage);
            }
            lastCoreCell = cell;
            lastEndMileage = endMileage ;
        }
        // 划分标准识别单元和独立识别单元
        Map<String ,Object> current = null ;
        List<Map<String,Object>> cells = new ArrayList<>();
        int size = coreCells.size();
        for(int i = 0 ; i <= size ; i++){
            Map<String,Object> core = i < size ? coreCells.get(i): null ;
            List<Map<String,Object>> subList = createStandardCells(current ,core ,realMileage);
            cells.addAll(subList);
            current = core;
        }

        int coreSize = 0;
        int standardSize = 0;
        int indiSize = 0;
        //计算识别单元几何对象，统计人口和要素 ,要素类型和建筑类型
        List<Feature> cellFeatures = new ArrayList<>() ;
        for( int i = 0 ; i < cells.size() ; i++ ){
            Map<String,Object> cell = cells.get(i) ;
            double startMileage = MapUtil.getDouble(cell , "start_mileage") ;
            double endMileage = MapUtil.getDouble(cell , "end_mileage") ;

            String wkt = queryBufferArea(startMileage,endMileage,bufferDistance ,pipelineSourceName,pipelineOid,pipelineKeyName,geomName);
            //空间查询数据不准确 ？
            int type = MapUtil.getInt(cell ,"cell_type");
//            if(type != 1){
//                Map<String ,Object> attr = querySettlement(wkt,settlementSourceName);
//                cell.putAll(attr);
//            }
//            double population = MapUtil.getDouble(cell ,"population" ,0d);
//            double households = MapUtil.getDouble(cell ,"households" ,0d);
//            if(population >= CongfigCoreCellPopulationCondition1 || households >= CongfigCoreCellHouseholdsCondition1){
//                cell.put("cell_type",1);
//            }
            //统计起止里程范围内的人口和建筑类型
            if(type != 1){
                Map<String ,Object> attr = countSettlementDataByMileage(startMileage,endMileage,dataList);
                cell.putAll(attr);
            }

            switch ( MapUtil.getInt(cell,"cell_type")){
                case 1 :
                    coreSize++;break ;
                case 2 :
                    standardSize++;break ;
                case 3 :
                    indiSize++;break ;
            }
            Feature feature = new Feature() ;
            feature.setAttributes(cell);
            feature.setGeometry(wkt);
            cellFeatures.add(feature);
        }
        StringUtil.timeEnd("生成识别单元" ,"成功创建" + cellFeatures.size() + "个识别单元。");
        StringUtil.timeEnd("识别单元划分",String.format("识别单元划分结束 ，供生成%d个识别单元 ，其中%d个核心识别单元，%d个标准识别单元，%d个独立识别单元",cellFeatures.size() ,coreSize,standardSize,indiSize));
        //5、保存识别单元
        StringUtil.time("保存识别单元");
        if(deleteFlag){
            int delResult = clear(sourceName);
            logger.info("成功删除{}条识别单元数据",delResult);
        }
        Feature[] features = new Feature[ cellFeatures.size()] ;
        for(int i = 0 ; i < cellFeatures .size() ; i++){
            Feature feature = cellFeatures.get(i);
            String oid = UUID.randomUUID().toString() ;
            double start_mileage = MapUtil.getDouble(feature.getAttributes(),"start_mileage",0d);
            double end_mileage = MapUtil.getDouble(feature.getAttributes(),"end_mileage",0d);
            feature.getAttributes().put("middle_mileage",(start_mileage + end_mileage)/2);
            feature.getAttributes().put("oid",oid);
            features[i] = feature;
        }

        SimpleResult re = save( sourceName,features);
        StringUtil.timeEnd("保存识别单元","成功保存" + re.getData());
        calculateResult.put("cellCount",re.getData());

        baseResult.setData(calculateResult);
        return  baseResult;
    }

    /**
     *
     * @param startMileage
     * @param endMilege
     * @param settlementDataList
     * @return
     */
    private Map<String,Object> countSettlementDataByMileage(double startMileage,double endMilege , List<Map<String ,Object>> settlementDataList){
        Map<String,Object> attr = new HashMap<>();
        double totalPopulation = 0d ;
        double totalHouseholds = 0d ;
        String settleType = "四层以下建筑为主" ;
        for(int i = 0 ; i < settlementDataList.size() ;i++ ){
            Map<String,Object> settle = settlementDataList.get(i);
            double start = MapUtil.getDouble(settle,"start_mileage" , 0d);
            double end = MapUtil.getDouble(settle,"end_mileage" , 0d);
            if(start > startMileage && end < endMilege){
                double p = MapUtil.getDouble(settle ,"population" ,0d);
                double h = MapUtil.getDouble(settle ,"households" ,0d);
                String buildingType = MapUtil.getString(settle ,"building_type" ,"");
                if( "四层及四层以上建筑为主".equals(buildingType)){
                    settleType = buildingType;
                }
                totalPopulation += p;
                totalHouseholds += h;
            }
        }
        attr.put("population",totalPopulation);
        attr.put("households",totalHouseholds);
        attr.put("main_building_type",settleType);
        return attr ;
    }
    /**
     * 计算地区等级数据
     * @param params
     * @return
     */
    @PostMapping(value = "rank/analysis")
    @ResponseBody
    public BaseResult areaRankAnalysis(@RequestBody Map<String ,Object> params) throws Exception {
        StringUtil.time("地区等级计算");
        String sourceName = MapUtil.getString(params,"sourceName","pd_arearank");
        String areaCellSourceName = MapUtil.getString(params,"areaCellSourceName","pd_arearankcell");
        String pipeSourceName = MapUtil.getString(params,"pipeSourceName","pd_pipesegment");
        String pipelineOid = MapUtil.getString(params,"pipeKeyValue");
        String pipelineKeyName = MapUtil.getString( params,"pipeKeyName","eventid");
        String geomName = MapUtil.getString( params,"geomName","geom");
        double bufferDistance = MapUtil.getDouble( params,"bufferDistance",200d);// m

        //1、查询识别单元数据
        LayerQueryParam param = new LayerQueryParam();
        param.setOutFields("*");
        param.setSrsname( areaCellSourceName );
        param.setOrderBy( "start_mileage" );
        param.setOutputFormat( null );
        FeatureCollection<Feature> queryResult = geodataAccessService.query(param);// 空间查询
        Feature[] features = queryResult.getFeatures().toArray(new Feature[0]);
        Integer featureSize = features.length  ;

        double totalMileage = queryRealMileage(pipeSourceName,pipelineKeyName,pipelineOid,geomName);

        logger.info("查询到{}个地区等级识别单元",featureSize) ;
        //2、地区等级划分，并将二级三级四级地区的边界扩展200m
        DecimalFormat format = new DecimalFormat("0000000");
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        List<Feature> rankList = new ArrayList<>() ;
        for(int i = 0 ; i < featureSize ; i++){
            Feature feature = features[i] ;
            Map<String,Object> attr = feature.getAttributes();

            Integer population = MapUtil.getInt(attr ,"population" );
            Integer households = (int) (population / CongfigHouseholdsPopulationRadio);
            String buildingType = MapUtil.getString(attr,"main_building_type") ;
            double startMileage = MapUtil.getDouble(attr ,"start_mileage") ;
            double endMileage = MapUtil.getDouble(attr ,"end_mileage") ;

            String description = "";
            String rankValue = "";

            if(households >= CongfigAreaRankHouseholdsCondition1 && "四层及四层以上建筑为主".equals(buildingType)){
                startMileage = startMileage - CongfigAreaRankBorderBufferDistance;
                endMileage = endMileage + CongfigAreaRankBorderBufferDistance ;
                description = "识别单元人口户数为：" + households + ";建筑类型为：" + buildingType;
                rankValue = "四级地区";
                count4 ++ ;
            }else if( households >= CongfigAreaRankHouseholdsCondition1 && "四层以下建筑为主".equals(buildingType)){
                startMileage = startMileage - CongfigAreaRankBorderBufferDistance;
                endMileage = endMileage + CongfigAreaRankBorderBufferDistance ;
                description = "识别单元人口户数为：" + households + ";建筑类型为：" + buildingType;
                rankValue = "三级地区";
                count3 ++ ;
            }else if(households >= CongfigAreaRankHouseholdsCondition2 && households < CongfigAreaRankHouseholdsCondition1){
                startMileage = startMileage - CongfigAreaRankBorderBufferDistance;
                endMileage = endMileage + CongfigAreaRankBorderBufferDistance ;
                description = "识别单元人口户数为：" + households +" ,大于（包含）" + CongfigAreaRankHouseholdsCondition2 +"户，小于" + CongfigAreaRankHouseholdsCondition1+"户"  ;
                rankValue = "二级地区";
                count2 ++ ;
            }else{
                description = "识别单元人口户数为：" + households +" ,小于" + CongfigAreaRankHouseholdsCondition1 +"户" ;
                rankValue = "一级地区";
                count1 ++ ;
            }

            if(startMileage < 0){
                startMileage = 0;
            }
            if(endMileage > totalMileage){
                endMileage = totalMileage;
            }

            Map<String ,Object> rank = new HashMap<>();
            rank.put("oid" ,UUID.randomUUID().toString());
            rank.put("name","R" + format.format(i + 1 ));
            rank.put("start_mileage",startMileage);
            rank.put("end_mileage",endMileage);
            rank.put("description",description);
            rank.put("rank",rankValue);
            rank.put("households",households);
            rank.put("population",population);

            Feature rankFeature = new Feature();
            rankFeature.setAttributes(rank);
            rankList.add(rankFeature) ;
        }
        int countTotal = count1 + count2 + count3 + count4 ;
        logger.info("地区等级划分结束，共有四类地区{}个，三类地区{}个，二类地区{}个，一类地区{}，总共{}个",count4,count3,count2,count1,countTotal );

        // 合并一级地区
        int mergeCount = 0 ;
        List<Feature> megeredRankList = new ArrayList<>() ;
        Feature lastFeature = null ;
        Integer lastRankIntValue = 0 ;
        for(int i = 0 ; i < rankList .size() ;i++ ){
            Feature feature = rankList.get(i) ;
            Map<String,Object> attribute = feature.getAttributes() ;
            double endMileage = MapUtil.getDouble(attribute ,"end_mileage") ;
            String rankValue = MapUtil.getString(attribute,"rank") ;
            Integer rankIntValue = getRankIntValue(rankValue);
            //
            if(rankIntValue == 1 && rankIntValue == lastRankIntValue  && lastFeature!= null) {
                //合并人口边界
                Map<String,Object> lastRankAttr = lastFeature.getAttributes() ;
                int lastPopulation = MapUtil.getInt(lastRankAttr, "population", 0);
                int lastHouseholds = MapUtil.getInt(lastRankAttr, "households", 0);
                int population = MapUtil.getInt(attribute, "population", 0);
                int households = MapUtil.getInt(attribute, "households", 0);
                lastRankAttr.put("population", lastPopulation + population);
                lastRankAttr.put("households", lastHouseholds + households);
                lastRankAttr.put("end_mileage", endMileage);
                mergeCount ++ ;
            }else{
                if(lastFeature != null ){
                    Map<String,Object> attr = lastFeature.getAttributes();
                    int totalHouseHolds = MapUtil.getInt(attr,"population" ,0);
                    String des = MapUtil.getString(attr,"description") + ";合并后识别单元人口户数：" + (int) (totalHouseHolds/CongfigHouseholdsPopulationRadio);
                    attr.put("description" ,des);
                }
                lastFeature = feature;

                lastRankIntValue = rankIntValue ;
                megeredRankList.add(feature);
            }
        }
        logger.info("地区等级合并结束，共合并一类地区{}，总共{}处",mergeCount,megeredRankList.size() );

        // 处理互相遮盖的边界，新边界以高等级地区为主
        lastRankIntValue = 0 ;
        lastFeature = null;
        Map<String,Object> lastRankAttr = null ;
        Double lastEndMileage = 0d ;
        Double lastStartMileage = 0d ;
        Double lastMileage = 0d ;
        for( int i = 0 ; i < megeredRankList .size() ; i++){
            Feature feature = megeredRankList.get(i) ;
            Map<String,Object> attribute = feature.getAttributes() ;
            double startMileage = MapUtil.getDouble(attribute ,"start_mileage") ;
            double endMileage = MapUtil.getDouble(attribute ,"end_mileage") ;
            double currentMileage = endMileage - startMileage;
            String rankValue = MapUtil.getString(attribute,"rank") ;
            Integer rankIntValue = getRankIntValue(rankValue);
            //
            if(endMileage < lastEndMileage ){
                megeredRankList.remove(feature);
                i--;
                continue;
            }

            if(startMileage < lastEndMileage && lastRankAttr != null){
                //保证长度大于2公里的核心识别单元能够扩充边界
                if((currentMileage > CongfigCoreCellStandardLength || lastMileage > CongfigCoreCellStandardLength) && rankIntValue == lastRankIntValue  ){
                    if(currentMileage > CongfigCoreCellStandardLength  && lastMileage < CongfigCoreCellStandardLength ){
                        lastEndMileage -= CongfigAreaRankBorderBufferDistance;
                        lastRankAttr.put("end_mileage",lastEndMileage);
                    }else if(lastMileage > CongfigCoreCellStandardLength  && currentMileage < CongfigCoreCellStandardLength){
                        startMileage += CongfigAreaRankBorderBufferDistance;
                        attribute.put("start_mileage",startMileage);
                    }else if(lastMileage  > CongfigCoreCellStandardLength  && currentMileage > CongfigCoreCellStandardLength ){
                        if(lastMileage > currentMileage){
                            attribute.put("start_mileage",lastEndMileage);
                        }else{
                            lastRankAttr.put("end_mileage",startMileage);
                        }
                    }else{
                        logger.error("逻辑错误，相邻两个都为核心识别单元，且长度都超过了" + CongfigCoreCellStandardLength +"公里" );
                    }
                }else{
                    if(rankIntValue > lastRankIntValue){
                        lastEndMileage -= CongfigAreaRankBorderBufferDistance;
                        lastRankAttr.put("end_mileage",lastEndMileage);
                    }else if(rankIntValue == lastRankIntValue && rankIntValue >= 1){
                        lastRankAttr.put("end_mileage",startMileage);
                        attribute.put("start_mileage",startMileage);
                    }else{ //(rankIntValue <= lastRankIntValue)
                        startMileage += CongfigAreaRankBorderBufferDistance;
                        attribute.put("start_mileage",startMileage);
                    }
                }
            }
            if(lastEndMileage < lastStartMileage){
                megeredRankList.remove(lastFeature);
                i--;
            }
            lastRankIntValue = rankIntValue;
            lastEndMileage = endMileage;
            lastStartMileage = startMileage;
            lastMileage = endMileage - startMileage;
            lastRankAttr = attribute;
            lastFeature = feature;

        }
        //根据里程值重新生成缓冲区单元
        for( int i = 0 ; i < megeredRankList .size() ; i++) {
            Feature feature = megeredRankList.get(i) ;
            Map<String,Object> attribute = feature.getAttributes() ;
            double startMileage = MapUtil.getDouble(attribute ,"start_mileage") ;
            double endMileage = MapUtil.getDouble(attribute ,"end_mileage") ;
            String geometry = queryBufferArea(startMileage,endMileage ,bufferDistance,pipeSourceName ,pipelineOid ,pipelineKeyName,geomName);
            feature.setGeometry(geometry);
        }

        StringUtil.timeEnd("地区等级计算");

        //4.保存地区等级数据
        int delCount = clear(sourceName);
        logger.info("成功删除{}条历史数据",delCount);
        Feature[] featuresToSave = megeredRankList.toArray(new Feature[0]);
        SimpleResult updateResult = save(sourceName ,featuresToSave);
        logger.info("成功更新{}条地区等级数据",updateResult.getData());
        updateResult.setMsg("操作成功！");
        hcaAreaService.saveAndUpdateArea();
        return updateResult ;
    }
    /**
     *
     * @param str
     * @return
     */
    private int getRankIntValue(String str){
        switch (str){
            case "四级地区":
                return 4 ;
            case "三级地区":
                return 3 ;
            case "二级地区":
                return 2;
            case "一级地区":
                return 1;
            default:
                return 0;
        }
    }
    /**
     *
     * @param cell
     * @param sourceName
     * @return
     */
    private Map<String,Object> querySettlement(String cell,String sourceName) throws Exception {
        Map<String,Object> result = new HashMap<>() ;
        LayerQueryParam param = new LayerQueryParam();
        param.setOutFields("*");
        param.setGeometry(GeometryUtil.abraseSRID(cell));
        param.setSrsname(sourceName);
        param.setGeometryType(GeometryType.POLYGON.name());

        FeatureCollection<Feature> featureCollection = geodataAccessService.query(param);// 空间查询
        Feature[] features = featureCollection.getFeatures().toArray(new Feature[0] );
        //叠加人口数据
        double totalPopulation = 0d;
        double totalHouseholds = 0d;
        //String mainFeatureType = null ;
        String mainBuildingType =  "四层以下建筑为主" ;//"四层及四层以上建筑为主" ;
        for(int i = 0 ; i < features.length ; i++){
            Feature feature = features[i] ;
            Map<String,Object> attr = feature.getAttributes();
            //数值指标计算
            totalPopulation += MapUtil.getDouble(attr ,"population" ,0d) ;
            totalHouseholds += MapUtil.getDouble(attr ,"households" ,0d) ;
            String buildingType = MapUtil.getString(attr ,"building_type") ;
            if( "四层及四层以上建筑为主".equals(buildingType)){
                mainBuildingType = buildingType;
            }
        }
        result.put("population",totalPopulation);
        result.put("households",totalHouseholds);
        //result.put("main_feature_type",mainFeatureType);
        result.put("main_building_type",mainBuildingType);

        return result ;
    }
    /**
     *
     * @param source
     * @param keyName
     * @param keyValue
     * @param geomName
     * @return
     */
    private double queryRealMileage(String source,String keyName,String keyValue ,String geomName){
        String sql = " SELECT ST_Length(ST_GeogFromText( ST_AsEwkt("+geomName+"))) from " + source + " where "+keyName +"=:" +keyName;
        Map<String,Object> param = new HashMap<>() ;
        param.put(keyName,keyValue);
        return namedParameterJdbcTemplate.queryForObject(sql,param,Double.class)/1000  ;
    }
    /**
     *
     * @param core1
     * @param core2
     */
    private Map<String,Object> mergeCoreCell(Map<String,Object> core1 ,Map<String,Object> core2){
        if(core1 == null ){
            return  core2;
        }
        double startMileage = MapUtil.getDouble(core1,"start_mileage",0d);
        double endMileage = MapUtil.getDouble(core1,"end_mileage",0d);
        double endMileage2 = MapUtil.getDouble(core2,"end_mileage",0d);
        int population = MapUtil.getInt(core1,"population",0);
        int population2 = MapUtil.getInt(core2,"population",0);
        int sumPopulation = population + population2;
        double middleMileage = (startMileage + endMileage2) /2 ;
        String buildingType = MapUtil.getString( core2,"building_type");
        core1.put("end_mileage",endMileage2);
        core1.put("population",sumPopulation );
        core1.put("middle_mileage",middleMileage );
        core1.put("households",(int)(sumPopulation / CongfigHouseholdsPopulationRadio  ));
        if("四层及四层以上建筑为主".equals( buildingType)){
            core1.put("main_building_type",middleMileage );
        }
        return core1;
    }
    /**
     *
     * 合并起止里程有重叠的居民地要素
     * @return
     */
    private List<Map<String,Object>> calculateCellAttributes(Feature[] features){
        List<Map<String,Object>> dataList = new ArrayList<>() ;

        for(int i = 0 ; i < features.length ; i++){
            Feature feature = features[i];
            Map<String,Object> attributes = feature.getAttributes();
            double startMileage = MapUtil.getDouble(attributes,"start_mileage" ,0d);
            double endMileage = MapUtil.getDouble(attributes,"end_mileage" ,0d);
            int j = dataList.size() - 1 ;
            Map<String,Object> currentCell = j < 0 ? null : dataList.get(j) ;
            double cellStartMileage = MapUtil.getDouble( currentCell,"start_mileage" ,0d );
            double cellEndMileage = MapUtil.getDouble( currentCell,"end_mileage" ,0d );
            Boolean b1 = startMileage > cellStartMileage && startMileage < cellEndMileage;
            Boolean b2 = endMileage > cellEndMileage  ;
            if( b1 && b2 ){ //合并
                currentCell.put("end_mileage" ,endMileage);
                int population = MapUtil.getInt(attributes ,"population",0);
                int cellPopulation = MapUtil.getInt(currentCell ,"population",0);
                currentCell.put("population" ,population + cellPopulation);
                currentCell.put("middle_mileage" ,( cellStartMileage + endMileage )/2);
                String buildingType = MapUtil.getString(attributes ,"building_type");
                if("四层及四层以上建筑为主".equals(buildingType)){
                    currentCell.put("main_building_type" ,buildingType);
                }
            }else{
                Map<String,Object> newCell = new HashMap<>();
                newCell.put("start_mileage" ,startMileage);
                newCell.put("end_mileage" ,endMileage);
                //newCell.put("households" ,attributes.get("households"));
                newCell.put("population" ,attributes.get("population"));
                newCell.put("main_building_type" ,attributes.get("building_type"));
                newCell.put("middle_mileage" ,( startMileage + endMileage )/2);
                dataList.add(newCell) ;
            }
        }
        return dataList ;
    }
    /**
     * 生成标准和独立识别单元
     * @param beginCell
     * @param endCell
     * @return
     */
    private List<Map<String,Object>> createStandardCells(Map<String,Object> beginCell ,Map<String,Object> endCell ,double totalMileage){
        List<Map<String,Object>> subList = new ArrayList<>() ;
        List<Map<String,Object>> backList = new ArrayList<>() ;

        double startMileage = beginCell == null ? 0 :MapUtil.getDouble(beginCell,"end_mileage",0d) ;
        double endMileage = endCell == null ? totalMileage : MapUtil.getDouble(endCell,"start_mileage",0d) ;
        double leftMileage = endMileage - startMileage ;

        if(beginCell != null ){
            subList.add(beginCell) ;
        }

        while(leftMileage > 0){
            if(leftMileage > CongfigCoreCellStandardLength){
                double forwordMileage = startMileage + CongfigCoreCellStandardLength;
                Map<String,Object> forwordCell = createSingleCell(startMileage ,forwordMileage,2);
                startMileage = forwordMileage;
                leftMileage -= CongfigCoreCellStandardLength;
                subList.add(forwordCell);
            }
            if(leftMileage >= CongfigCoreCellStandardLength){
                double backwordMileage = endMileage - CongfigCoreCellStandardLength;
                Map<String,Object> backwordCell = createSingleCell(backwordMileage,endMileage,2);
                endMileage = backwordMileage;
                leftMileage -= CongfigCoreCellStandardLength;
                backList.add(backwordCell);
            }else {//创建独立单元
                Map<String,Object> cell = createSingleCell(startMileage,endMileage,3);
                leftMileage = 0 ;
                subList.add(cell);
            }
        }
        if(backList.size() > 0){
            Collections.reverse(backList);
            subList.addAll(backList) ;
        }
        return subList ;
    }
    /**
     *
     * @param start
     * @param end
     * @param type
     * @return
     */
    private Map<String ,Object> createSingleCell(double start ,double end,int type){
        Map<String,Object> map = new HashMap<>() ;
        map.put("start_mileage" ,start) ;
        map.put("end_mileage" ,end) ;
        map.put("cell_type" ,type) ;
        return map;
    }
    /**
     * 保存要素图层数据
     * @param sourceName
     * @param featureCollectionBO
     * @return
     */
    @PostMapping(value = "{source}/save")
    @ResponseBody
    public BaseResult save( @PathVariable(value="source") String sourceName ,@RequestBody FeatureCollectionBO featureCollectionBO){
        Feature[] features = featureCollectionBO.getFeatureArray();
        return save(sourceName,features) ;
    }
    //
    private SimpleResult save(String sourceName ,Feature[] updateArray){
        SimpleResult baseResult = new SimpleResult<String>();
        int count = 0 ;
        int[] updateResult = geodataAccessService.updateFeatures(sourceName,updateArray);
        List<Feature> insertList = new ArrayList<>();
        for(int i = 0 ; i < updateArray.length ;i++){
            if(updateResult[i] == 0){
                insertList.add(updateArray[i]);
            }else{
                count++;
            }
        }
        //logger.info("成功更新{}条数据" ,count);
        if(insertList.size() > 0){
            Feature[] insertArray = new Feature[insertList.size()];
            insertArray = insertList.toArray(insertArray);
            int[] addResult = geodataAccessService.addFeatures(sourceName,insertArray);
            count += MathUtil.sum(addResult);
        }
        baseResult.setData(count);
        return baseResult;
    }

    /**
     * 要素属性信息查询接口
     * @param sourceName
     * @return
     * @throws Exception
     */
    @PostMapping(value = "{source}/query")
    @ResponseBody
    public BaseResult query( @PathVariable(value="source") String sourceName ,@RequestBody Map<String ,Object> params  ) throws Exception {
        SimpleResult baseResult = new SimpleResult<String>();
        LayerQueryParam param = new LayerQueryParam();
        param.setSrsname(sourceName);
        param.setReturnGeometry(false);
        param.setOrderBy("start_mileage");
        param.setOutFields("*");
        param.setWhere(params.containsKey("where") ? (String)params.get("where"):"");
        Object result = geodataAccessService.query(param);
        baseResult.setData(result);
        return baseResult ;
    }
    /**
     * 计算高后果区
     * 1、得到识别单元
     * @return
     */
    @PostMapping(value = "data/analysis")
    @ResponseBody
    public BaseResult hcaDataCalculate( @RequestBody Map<String ,Object> params) throws Exception {
        //
        SimpleResult baseResult = new SimpleResult<String>();
        //高后果区
        //String hcaResultSourceName = MapUtil.getString(params ,"hcaSourceName","pd_hca_result");
        //高后果区识别单元
        String hcaCellSourceName = MapUtil.getString(params ,"hcaCellSourceName","pd_zonerankcell");
        //地区等级
        String areaRankSourceName = MapUtil.getString(params ,"areaRankSourceName","pd_arearank");
        //易燃易爆场所
        String gasStationSourceName = MapUtil.getString(params ,"gasStationSourceName","pd_gas_station");
        //特定场所列表
        List<String> specialPlaceSourceNames = (List<String>) MapUtil.get(params ,"specialPlaceSourceNames");
        //管道基础参数
        Map<String ,Object> pipeInfos = (Map<String,Object>) MapUtil.get(params ,"pipeInfos");
        //评价标准
        Map<String ,Object> pipeIndexes = (Map<String,Object>) MapUtil.get(params ,"pipeIndexes");

        double diameter = MapUtil.getDouble( pipeInfos ,"diameter");
        double presure = MapUtil.getDouble( pipeInfos ,"presure");

        List<Feature> featureList = new ArrayList<>();
        List<Feature> resultList = new ArrayList<>();

        StringUtil.time("高后果区分析");

        //1、获取识别单元数据
        StringUtil.time("查询识别单元数据");

        LayerQueryParam param = new LayerQueryParam();
        param.setSrsname( hcaCellSourceName );
        param.setOutputFormat("WKT");
        param.setOutFields("*");
        FeatureCollection featureCollection = geodataAccessService.query(param);
        StringUtil.timeEnd("查询识别单元数据",String.format("查询到 %d条识别单元" ,featureCollection.getSize()));

        //2、计算识别单元高后果区等级
        for(int i = 0 ; i < featureCollection.getSize(); i++){
            Feature feature = (Feature) featureCollection.getFeatures().get(i);
            Feature f = new Feature() ;
            f.setGeometry( feature.getGeometry()); //这里geom是geojson格式
            f.setAttributes( feature.getAttributes());// ??
            featureList.add(f);
        }
        //a 、由地区等级判断 ,查询与识别单元相交的地区等级数据
        StringUtil.time("地区等级分析");

        for(int i = 0 ; i < featureList.size() ;i++){
            Feature feature = featureList.get(i);
            LayerQueryParam layerQueryParam = new LayerQueryParam();
            layerQueryParam.setSrsname(areaRankSourceName);
            layerQueryParam.setReturnGeometry(false);
            layerQueryParam.setOutFields("oid,rank,start_mileage,end_mileage");
            layerQueryParam.setGeometry(feature.getGeometry().toString());
            layerQueryParam.setGeometryType(GeometryType.POLYGON.name());

            FeatureCollection queryResult = geodataAccessService.query(layerQueryParam);
            int maxRankInt = 0;
            Map<String,Object> attr = new HashMap<>() ;
            if( queryResult.getSize() > 0){
                for(int j = 0 ;j < queryResult.getSize() ;j++){
                    Feature f = (Feature) queryResult.getFeatures().get(j);
                    Map<String,Object> a = f.getAttributes();
                    String rank = MapUtil.getString(a,RankFieldName);
                    int rankInt = getRankIntValue(rank);
                    if( rankInt > maxRankInt){
                        maxRankInt = rankInt;
                    }
                }
                Map pros = feature.getAttributes() ;
                pros.put("rank",attr.get("rank"));
                pros.put(DescriptionFieldName,"");

            }else{
                logger.warn("没有查询到地区等级:" +"oid=" +  feature.getAttributes().get("oid"));
            }

            //地区等级设置
            if(4 == maxRankInt){
                feature.getAttributes().put(RankFieldName,"III") ;
                //设置评价依据
                feature.getAttributes().put(DescriptionFieldName,EvaString1) ;
                resultList.add(feature);
            }else if( 3== maxRankInt){
                feature.getAttributes().put(RankFieldName,"II") ;
                //设置评价依据
                feature.getAttributes().put(DescriptionFieldName,EvaString2) ;
                resultList.add(feature);
            }
        }
        int count = resultList.size() ;
        StringUtil.timeEnd("地区等级分析",String.format("识别出 %d 处  II级以上高后果区",count));

        //b、易燃易爆场所
        StringUtil.time("易燃易爆场所分析");

        for( int i = 0 ; i < featureList.size();i++ ){
            Feature feature = featureList.get(i);
            Map<String,Object> attributes = feature.getAttributes();
            String rank = MapUtil.getString(attributes,RankFieldName,"");
            if("III".equals(rank) || "II".equals(rank)){
                continue;
            }
            LayerQueryParam layerQueryParam = new LayerQueryParam();
            layerQueryParam.setSrsname(gasStationSourceName);
            layerQueryParam.setReturnGeometry(false);
            layerQueryParam.setOutFields("gid");
            layerQueryParam.setGeometry(feature.getGeometry().toString());
            layerQueryParam.setGeometryType(GeometryType.POLYGON.name());
            FeatureCollection<Feature> queryResult =   geodataAccessService.query(layerQueryParam);

            Feature[] array = queryResult.getFeatures().toArray(new Feature[0] );
            if(array != null && array.length > 0 ){
                //
                //Map<String,Object> attr = attributes.getInnerMap();
                //feature.setAttributes(attr);
                feature.getAttributes().put(RankFieldName,"II") ;
                //设置评价依据；
                feature.getAttributes().put(DescriptionFieldName,EvaString3) ;
                resultList.add(feature);
            }else{

            }
        }
        int count1 = resultList.size() - count ;
        StringUtil.timeEnd("易燃易爆场所分析" ,String.format("识别出 %d处单元包含易燃易爆场所",count1));

        //c、特定场所
        StringUtil.time("特定场所分析");

        for( int i = 0 ; i < featureList.size();i++ ){
            Feature feature = featureList.get(i);
            Map<String,Object> attributes = feature .getAttributes();
            String rank = MapUtil.getString(attributes,RankFieldName,"");
            if("III".equals(rank) || "II".equals(rank)){
                continue;
            }
            List<String> pointList = new ArrayList<>();
            //这里特定场所是分表存储的
            for(int j = 0 ; j < specialPlaceSourceNames.size() ; j++){
                String sourceLayer = specialPlaceSourceNames.get(j );
                LayerQueryParam layerQueryParam = new LayerQueryParam();
                layerQueryParam.setSrsname(sourceLayer);
                layerQueryParam.setReturnGeometry(true);
                layerQueryParam.setOutFields("gid");
                layerQueryParam.setGeometry(feature.getGeometry().toString());
                layerQueryParam.setGeometryType(GeometryType.POLYGON.name());

                FeatureCollection<Feature>   queryResult = geodataAccessService.query(layerQueryParam);
                Feature[] array = queryResult.getFeatures().toArray(new Feature[0]);
                if(array != null && array.length > 0){
                    if(diameter > 762 && presure > 6.9) {
                        feature.getAttributes().put(RankFieldName, "II");
                    }else {
                        feature.getAttributes().put(RankFieldName, "I");
                    }
                    for(int z = 0 ; z < array.length;z++){
                        Feature ff = array[z];
                        pointList.add((String) ff.getAttributes().get("geometry"));
                    }
                }
            }
            if(pointList.size() > 0){
                feature.getAttributes().put(DescriptionFieldName,EvaString4) ;
                resultList.add(feature);
            }
        }
        int count2 = resultList .size() - count - count1 ;
        StringUtil.timeEnd("特定场所分析",String.format("识别出 %d 处单元包含特定场所",count2));

        //得到resultList  ,保存高后果区分析结果
        Feature[] features = new Feature[resultList.size()];
        features = resultList.toArray(features);

        int[] resultArray = geodataAccessService.updateFeatures(hcaCellSourceName,features);
        int result = MathUtil.sum(resultArray);
        baseResult.setData(result);
        String messge = "成功生成 %d 个高后果地区";
        baseResult.setMsg( String.format(messge,result) );

        StringUtil.timeEnd("高后果区分析" ,String.format("成功更新 %d 处高后果区",result));

        hcaAreaService.saveAndUpdateHigh();
        return baseResult ;
    }
    /**
     * 重新修改识别单元的里程值，关联修改上游和下游的识别单元里程值
     * @param mileageResetBO
     * @return
     */
    @PostMapping(value = "mileage/reset")
    @ResponseBody
    public BaseResult resetMeasure(@RequestBody MileageResetBO mileageResetBO){
        SimpleResult result = new SimpleResult();
        String pipeSourceName = mileageResetBO.getPipeSourceName();
        String pipeKeyValue = mileageResetBO.getPipeKeyValue();
        String pipeKeyName = mileageResetBO.getPipeKeyName();
        String geomFieldName = mileageResetBO.getGeomFieldName() ;
        Double preNewStartMileage = mileageResetBO.getPreStartMileageValue();
        Double newStartMileage = mileageResetBO.getNewStartMileageValue();
        Double newEndMileage = mileageResetBO.getNewEndMileageValue();
        Double sufEndMileageValue = mileageResetBO.getSufEndMileageValue();
        Double buffer = mileageResetBO.getBufferDistance() ;

        Double oldStartMileage = mileageResetBO.getOldStartMileageValue();
        Double oldEndMileage = mileageResetBO.getOldEndMileageValue();

        int update = 0 ;
        if(preNewStartMileage > -1 ){
            String newPreGeometry = queryBufferArea(preNewStartMileage,newStartMileage,buffer ,pipeSourceName,pipeKeyValue,pipeKeyName,geomFieldName);
            String preUpdateSql = "update " + mileageResetBO.getSourceName() + " set " + mileageResetBO.getEndMileageName() + " =:new_start_mileage ,geom=st_geomfromtext(:geom) where " + mileageResetBO.getEndMileageName() + "=:old_start_mileage" ;
            Map<String,Object> preUpdateParam = new HashMap<>();
            preUpdateParam.put("new_start_mileage",newStartMileage);
            preUpdateParam.put("geom",newPreGeometry);
            preUpdateParam.put("old_start_mileage",oldStartMileage);
            update = namedParameterJdbcTemplate.update(preUpdateSql ,preUpdateParam);
        }
        String newGeometry = queryBufferArea(newStartMileage,newEndMileage,buffer ,pipeSourceName,pipeKeyValue,pipeKeyName,geomFieldName);
        String updateSql = "update " + mileageResetBO.getSourceName() + " set " + mileageResetBO.getEndMileageName() + " =:new_end_mileage ,"+ mileageResetBO.getStartMileageName()+"=:new_start_mileage,geom=st_geomfromtext(:geom) where " + mileageResetBO.getEndMileageName() + "=:old_end_milege and " + mileageResetBO.getStartMileageName() + "=:old_start_mileage" ;
        Map<String,Object> updateParam = new HashMap<>();
        updateParam.put("new_end_mileage",newEndMileage);
        updateParam.put("new_start_mileage",newStartMileage);
        updateParam.put("old_end_milege",oldEndMileage);
        updateParam.put("old_start_mileage",oldStartMileage);
        updateParam.put("geom",newGeometry);
        update = namedParameterJdbcTemplate.update(updateSql ,updateParam);

        if( sufEndMileageValue > -1){
            String newSufGeometry = queryBufferArea(newEndMileage,sufEndMileageValue,buffer ,pipeSourceName,pipeKeyValue,pipeKeyName,geomFieldName);
            String sufUpdateSql = "update " + mileageResetBO.getSourceName() + " set " + mileageResetBO.getStartMileageName() + " =:new_end_mileage ,geom=st_geomfromtext(:geom) where " + mileageResetBO.getStartMileageName() + "=:old_end_mileage" ;
            Map<String,Object> sufUpdateParam = new HashMap<>();
            sufUpdateParam.put("new_end_mileage",newEndMileage);
            sufUpdateParam.put("geom",newSufGeometry);
            sufUpdateParam.put("old_end_mileage",oldEndMileage);
            update = namedParameterJdbcTemplate.update(sufUpdateSql ,sufUpdateParam);
        }
        result.setData(update);
        result.setMsg("里程值修改成功！");
        return result;
    }
    /**
     * GeoJSON转换成WKT 字符串格式
     * @param jsonString
     * @return
     */
    public String jsonToWkt(String jsonString){
        StringBuffer result = new StringBuffer("");
        JSONObject json = JSON.parseObject(jsonString);
        String type = json.getString("type") ;
        JSONArray rings = json.getJSONArray("coordinates");
        if("Polygon".equals(type) && rings.size() > 0){
            result.append("POLYGON((");
            JSONArray ring = rings.getJSONArray(0);
            for(int i = 0 ; i < ring.size() ;i++){
                JSONArray coors = ring.getJSONArray(i);
                result.append(coors.get(0));
                result.append(" ");
                result.append(coors.get(1));
                result.append(",");
            }
            result.deleteCharAt(result.length() - 1);
            result.append("))");
        }
        return result.toString() ;
    }
    /**
     * 保存要素单元
     * 建筑用地--编号--计算里程、距离-->要素单元
     * @param settlementBO
     * @return
     */
    @RequestMapping(value="settlement/save",method=RequestMethod.POST,consumes="application/json")
    @ResponseBody
    public BaseResult saveSettlement(@RequestBody SettlementBO settlementBO) throws Exception {
        //
        String sourceName = settlementBO.getSourceName() ;
        String pipeSourceName = settlementBO.getPipelineSourceName();
        String keyValue = settlementBO.getPipelineKeyValue();
        String keyName = settlementBO.getPipelineKeyName();
        String geomName = settlementBO.getGeomName();

        StringBuffer message = new StringBuffer() ;
        //1、Query 居民地数据
        //Feature[] featureArray = querySettlementData(sourceName,geomName);
        Feature[] featureArray = settlementBO.getFeatureArray();
        int sizeBeforeMerge = featureArray.length ;

        //2、居民地要素合并 ,距离小于50米合并成一个要素
        //Feature[] featureArray = mergeFeatureData(featuresBeforeMerge) ;
        //int sizeAfterMerge = featureArray.length ;
        //message.append("合并后要素数量：").append(featureArray.length).append("\r");
        //2、属性数据预处理
        prepareSettlementData(featureArray);
        //3、要素编号
        codeSettlementData(featureArray);
        //4、参数计算，计算里程值（起止里程值）、垂直距离(距离管线的最近距离)、水平距离（结束里程减去开始里程）
        calculateMileage( featureArray ,pipeSourceName,keyValue,keyName,geomName);
        //5、清空
        /////////clearSettlementData(sourceName);
        //6、保存
        return save(sourceName,featureArray);
    }
    /**
     * 居民地数据预处理
     * 设置居民地的人口、建筑类型等属性
     * @return
     */
    @PostMapping(value = "settlement/random")
    @ResponseBody
    public BaseResult randomAreaRankCells(@RequestBody Map<String ,Object> params) throws Exception {
        BaseResult baseResult = new BaseResult();
        String sourceName = MapUtil.getString(params ,"source") ;
        Boolean populationBool = MapUtil.getBoolean(params ,"population",false) ;
        Boolean buildingTypeBool = MapUtil.getBoolean(params ,"buildingType",false) ;

        LayerQueryParam layerQueryParam = new LayerQueryParam();
        layerQueryParam.setSrsname(sourceName);
        layerQueryParam.setOutFields("*");

        FeatureCollection<Feature> queryResult = geodataAccessService.query(layerQueryParam) ;
        Feature[] features =queryResult.getFeatures().toArray(new  Feature[0]);
        int i = 0 ;
        double maxArea = 0d;
        double minArea = Double.POSITIVE_INFINITY  ;
        Geometry[] geometries = new Geometry[features.length];
        for(i = 0 ; i< features.length;i++){
            geometries[i] = (Geometry) features[i].getGeometry();
        }
        List<AreaAndLength> areaAndLengthList = geometryService.areasAndLengths(geometries);
        for(i = 0 ; i < areaAndLengthList.size(); i++){
            AreaAndLength areaAndLength = areaAndLengthList.get(i) ;
            double area = areaAndLength.getArea() ;
            if(area > maxArea){
                maxArea = area;
            }
            if(area < minArea){
                minArea = area;
            }
        }
        for(i = 0; i < features.length ;i++){
            Feature feature = features[i] ;
            Map<String,Object> attr = feature.getAttributes() ;
            if( populationBool ){
                double area = areaAndLengthList.get(i).getArea();
                int p = (int)( CongfigMaxPopulation *  (area - minArea) / (maxArea - minArea) ) + 1 ; // 根据面积给出人口
                attr.put("population" , p) ;
            }
            if(buildingTypeBool){
                double b =  Math.random()  ;
                if( b > 0.5 ){
                    attr.put("building_type" , "四层及四层以上建筑为主") ;
                }else{
                    attr.put("building_type" , "四层以下建筑为主") ;
                }
            }
        }
        baseResult.setMsg("成功处理" + i + "个要素单元");
        save(sourceName,features);
        return  baseResult;
    }
    /**
     * 根据识别区查询并设置
     * @return
     */
    public Feature[] queryAndCutSettlementDataByPipeBuffer( String areaSettlementSource  ,String settlementSource ,double startMileage ,double endMileage ,double bufferDistance,String pipelineSourceName ,String pipeKeyValue ,String pipeKeyName ,String pipeGeomName) throws Exception {
        List<Feature> result = new ArrayList<>() ;
        List<Feature> orignResult = new ArrayList<>() ;
        //1、由管线及缓冲半径创建识别区
        String bufferPolygonWKT = queryBufferArea(startMileage,endMileage,bufferDistance ,pipelineSourceName,pipeKeyValue,pipeKeyName,pipeGeomName);
        //2、查询相交的居民地
        StringUtil.time("查询识别区域相交的居民地");
        LayerQueryParam param = new LayerQueryParam() ;
        param.setSrsname(settlementSource);
        param.setOutFields("*");
        param.setGeometryType(GeometryType.POLYGON.name());
        param.setGeometry(GeometryUtil.abraseSRID(bufferPolygonWKT)) ;

        FeatureCollection<Feature> queryResult = geodataAccessService.query(param) ;
        Feature[] features =queryResult.getFeatures().toArray(new  Feature[0]);

        StringUtil.timeEnd("查询识别区域相交的居民地");
        logger.info("识别区域内查询到{}处居民地",features.length );
        //3、相交计算
        StringUtil.time("识别区域居民地相交计算");
        Geometry buffer = GeometryUtil.fromWKT( bufferPolygonWKT );
        for(int i = 0 ; i < features.length ; i++){
            Feature feature = features[i];
            Geometry g = (Geometry) feature.getGeometry();
            Geometry[] gg = new Geometry[]{g} ;
            Geometry[] geometries = geometryService.intersect(gg, buffer);
            if(geometries != null && geometries .length > 0 && geometries[0] instanceof Polygon){ // 相交计算的结果可能是点或线
                Polygon polygon = (Polygon) geometries[0];
                Map<String,Object> attr = feature.getAttributes();
                Feature f = new Feature() ;
                f.setGeometry(polygon);
                f.setAttributes(attr);
                result.add(f);
                orignResult.add(feature);
            }
        }
        StringUtil.timeEnd("查询识别区域相交的居民地");
        //4、计算面积及起止里程值，获取人口
        Feature[] fs = result.toArray(new Feature[0]);
        StringUtil.time("重新计算居民地前后里程");
        calculateMileage(fs,pipelineSourceName,pipeKeyValue,pipeKeyName,pipeGeomName);
        StringUtil.timeEnd("重新计算居民地前后里程");
        //
        StringUtil.time("重新计算居民地人口");
        int size = result.size() * 2;
        Geometry[] geometries = new Geometry[size ] ;
        for(int i = 0  ; i < result.size() ; i++){
            geometries[i] = (Geometry) result.get(i).getGeometry();
        }
        for( int i = size -1 ,j=0 ; i >=0 ; i--,j++){
            geometries[i] = (Geometry) orignResult.get(j).getGeometry();
        }
        List<AreaAndLength> areaAndLengths = geometryService.areasAndLengths(geometries) ;
        int halfSize = size / 2;
        for(int i = 0 ; i < halfSize ;i++){
            int j = size - i ;
            Feature originFeature = orignResult.get(i);
            int originPopulation = MapUtil.getInt( originFeature.getAttributes(),"population");
            double originArea = areaAndLengths.get(j).getArea() ;
            double area = areaAndLengths.get(i).getArea();
            Double population = area / originArea * originPopulation;
            //
            result.get(i) .getAttributes().put("population",population.intValue());
        }
        StringUtil.timeEnd("重新计算居民地人口");
        //5、保存
        String clearSql = "delete from " + areaSettlementSource;
        int flg = namedParameterJdbcTemplate.update(clearSql, new HashMap<String,Object>());
        log.info("清空{}条缓冲区居民地数据",flg);
        Feature[] features1 = result.toArray(new Feature[0]);
        int[] add = geodataAccessService.addFeatures(areaSettlementSource,features1) ;
        log.info("保存{}条缓冲区居民地数据",MathUtil.sum(add));

        return features1;
    }
    /**
     *
     * @param sourceName
     * @param geomName
     * @return
     * @throws Exception
     */
    private Feature[] querySettlementData(String sourceName,String geomName) throws Exception {
        LayerQueryParam layerQueryParam = new LayerQueryParam();
        layerQueryParam.setSrsname(sourceName);
        layerQueryParam.setOutFields("*");
        layerQueryParam.setOrderBy("start_mileage");
        FeatureCollection<Feature> queryResult = geodataAccessService.query(layerQueryParam) ;
        Feature[] features =queryResult.getFeatures().toArray(new  Feature[0]);
        return features ;
    }
    private void prepareSettlementData(Feature[] features ){
        //DecimalFormat df = new DecimalFormat("000000");
        for(int i = 0 ; i < features.length ; i++){
            Map<String,Object> attr = features[i].getAttributes() ;
            if(attr == null){
                attr = new HashMap<>() ;
                features[i].setAttributes(attr);
            }
            String oid = MapUtil.getString(attr,"oid",UUID.randomUUID().toString());
            //String name = MapUtil.getString(attr ,"name","JMQ-NAME-" + df.format(i+1));
            //String code = MapUtil.getString(attr ,"code","JMQ-CODE-" + df.format(i+1));
            String description = MapUtil.getString(attr ,"description","无");
            Integer population = MapUtil.getInt(attr ,"population",0);
            Integer households = MapUtil.getInt(attr ,"households",0);

            Double start_mileage = MapUtil.getDouble(attr ,"start_mileage",0d);
            Double end_mileage = MapUtil.getDouble(attr ,"end_mileage",0d);
            Double horizontal_distance = MapUtil.getDouble(attr ,"horizontal_distance",0d);
            Double vertical_distance = MapUtil.getDouble(attr ,"vertical_distance",0d);
            Double start_x = MapUtil.getDouble(attr ,"start_x",0d);
            Double start_y = MapUtil.getDouble(attr ,"start_y",0d);
            String feature_type = MapUtil.getString(attr ,"feature_type","");
            String building_type = MapUtil.getString(attr ,"building_type","");

            attr.put("oid" ,oid);
            //attr.put("name" ,name);
            //attr.put("code" ,code);
            attr.put("description" ,description);
            attr.put("households" ,households);
            attr.put("population" ,population);
            attr.put("start_mileage" ,start_mileage);
            attr.put("end_mileage" ,end_mileage);
            attr.put("horizontal_distance" ,horizontal_distance);
            attr.put("vertical_distance" ,vertical_distance);
            attr.put("start_x" ,start_x);
            attr.put("start_y" ,start_y);
            attr.put("feature_type" ,feature_type);
            attr.put("building_type" ,building_type);
        }
    }
    private int clear(String sourceName){
        String sql = "delete from " + sourceName;
        return namedParameterJdbcTemplate.update(sql,new HashMap<>());
    }
    private void codeSettlementData(Feature[] features ){
        DecimalFormat df = new DecimalFormat("000000");
        for(int i = 0 ; i < features.length ; i++){
            Map<String,Object> attr = features[i].getAttributes() ;
            if(attr == null){
                attr = new HashMap<>() ;
                features[i].setAttributes(attr);
            }
            String name = MapUtil.getString(attr ,"name","JMQ-NAME-" + df.format(i+1));
            String code = MapUtil.getString(attr ,"code","JMQ-CODE-" + df.format(i+1));
            attr.put("name" ,name);
            attr.put("code" ,code);
        }
    }
    private Map<String,Object> mergeFeatureData(Feature[] features ){
        Set<String> mergedFeatureId = new HashSet<>();
        Map<String,Object> result = new HashMap<>();
        if(features.length >= 2){
            List<Feature> list = new ArrayList<>() ;
            Integer currentFeatureIndex = 0;
            Feature preFeature = features[currentFeatureIndex] ;
            Feature sufFeature = features[currentFeatureIndex + 1];
            while(true){
                Feature newFeature = mergeFeatureByDistance(preFeature,sufFeature,CongfigSettlementMergeDistance) ;
                // ??? bug
                if(newFeature == null){
                    list.add(preFeature);
                    //list.add(sufFeature);
                    preFeature = sufFeature ;
                }else{
                    String oid = MapUtil.getString(preFeature.getAttributes(),"oid") ;
                    mergedFeatureId.add(oid);
                }
                currentFeatureIndex ++;
                if(currentFeatureIndex == features.length - 1){
                    break;
                }else{
                    sufFeature = features[currentFeatureIndex + 1];
                }
            }
            Feature[] newArray = new Feature[list.size()];
            for(int i = 0 ; i < list.size() ; i ++){
                newArray[i] = list.get(i) ;
            }
            result.put("features" , newArray) ;
        }else{
            result.put("features" , features) ;
        }
        result.put("mergedOid",mergedFeatureId);
        return result;
    }

    /**
     * 距离接近合并要素
     * @param f1
     * @param f2
     * @param distance
     * @return
     */
    private Feature mergeFeatureByDistance(Feature f1 ,Feature f2 ,double distance){
        Geometry g1 = GeometryUtil.toGeometry(f1.getGeometry());
        Geometry g2 = GeometryUtil.toGeometry(f2.getGeometry());

        double d = geometryService.distance(g1,g2); //单位可以为米 ？？

        if(d <= distance){
            Map<String,Object> attr1 = f1.getAttributes();
            Map<String,Object> attr2 = f2.getAttributes();
            int households = MapUtil.getInt(attr1,"households",0) + MapUtil.getInt(attr2,"households",0);
            int population = MapUtil.getInt(attr1,"population",0) + MapUtil.getInt(attr1,"population",0);
            attr2.put("households",households);
            attr2.put("population",population);
            Geometry geometry = mergeGeometry((Polygon)g1,(Polygon)g2);
            f2.setGeometry( geometry);
            //
            String startOid = MapUtil.getString( attr1,"oid" );
            String endOid = MapUtil.getString( attr2,"oid" );
            logger.info("要素单元{}与{}距离为{},小于{}，已合并。",startOid,endOid,d,distance);
            //
            return f2;
        }else{
            return null;
        }
    }

    private Geometry mergeGeometry(Polygon geo1 , Polygon geo2){
        Point[] pointArray1 = geo1.toCoordinates();
        Point[] pointArray2 = geo2.toCoordinates();
        int size1 = pointArray1.length ;
        int size2 = pointArray2.length ;
        int size = size1 + size2;
        pointArray1 = Arrays.copyOf( pointArray1,size );
        System.arraycopy( pointArray2,0,pointArray1,size1 ,size2 );
        return geometryService.convexHull(pointArray1);
    }
    /**
     * 计算里程值、垂直距离、水平距离
     */
    private void calculateMileage(Feature[] features ,String pipelineSourceName ,String keyValue,String keyName,String geomName){

        Map<String,Object> pipelineDataMap = queryPipelineData( pipelineSourceName,keyValue,keyName,geomName );
        String pipelineString = MapUtil.getString(pipelineDataMap,"wkt" );
        double totalMileage = MapUtil.getDouble(pipelineDataMap,"mileage" );
        //计算每个点的
        String locateSql = " SELECT  ST_LineLocatePoint(foo.the_pipeline, ST_GeomFromText(:locatePoint)) FROM (SELECT ST_GeomFromText(:pipeline) As the_pipeline) As foo ";
        //String subPipelineSql = "SELECT ST_AsEwkt(ST_Line_SubString(ST_GeomFromText(:pipeline), :startMileage, :endMileage))";
        String subPipelineLengthSql = " SELECT ST_Length(ST_GeogFromText(ST_AsEwkt(ST_Line_SubString(ST_GeomFromText(:pipeline),:startMileage,:endMileage))))";
        for(int i = 0 ; i < features.length ; i++){
            Feature feature = features[i];
            double startMileagePercent = 1 ;
            double endMileagePercent = 0d;
            double startX = 0d;
            double startY = 0d;
            Polygon polygon = (Polygon) feature.getGeometry();
            Point[] points = polygon.toCoordinates();
            for(int j = 0 ; j < points.length ;j++){
                Point p = points[j];
                String point = GeometryUtil.toWKT(p);
                Map<String,Object> param = new HashMap<>();
                param.put("locatePoint",point);
                param.put("pipeline",pipelineString);
                double percent = namedParameterJdbcTemplate.queryForObject(locateSql,param,Double.class);
                if( percent < startMileagePercent ){
                    startMileagePercent = percent;
                    startX = p.getX();
                    startY = p.getY();
                }
                if( percent > endMileagePercent ){
                    endMileagePercent = percent;
                }
            }
            //Map<String ,Object> subParam = new HashMap<>() ;
            //subParam.put("pipeline",pipelineString) ;
            //subParam.put("startMileage",startMileage) ;
            //subParam.put("endMileage",endMileage) ;
            //String subPipeline = namedParameterJdbcTemplate.queryForObject(subPipelineSql,subParam,String.class);
            Map<String ,Object> lengthParam = new HashMap<>() ;
            lengthParam.put("srid" ,4490);
            lengthParam.put("pipeline" ,pipelineString);
            lengthParam.put("startMileage" ,startMileagePercent);
            lengthParam.put("endMileage" ,endMileagePercent);
            double length = namedParameterJdbcTemplate.queryForObject(subPipelineLengthSql,lengthParam,Double.class);
            Geometry pipeline = GeometryUtil.fromWKT(pipelineString);
            double distance = geometryService.distance(polygon,pipeline);
            //
            Map<String,Object> attributes = feature.getAttributes();
            attributes.put("start_mileage",startMileagePercent * totalMileage);
            attributes.put("end_mileage",endMileagePercent * totalMileage);
            attributes.put("horizontal_distance",length);
            attributes.put("vertical_distance",distance);
            attributes.put("start_x",startX);
            attributes.put("start_y",startY);

        }
    }

    private Map<String,Object> queryPipelineData(String source ,String keyValue,String keyName,String geomName){
        Map<String,Object> result = new HashMap<>() ;
        //1、重新计算和更新管线里程
        StringBuffer updateSql = new StringBuffer() ;
        updateSql.append("update ").append(source).append(" set ").append(geomName).append("=(ST_AddMeasure(").append(geomName).append(",0,(select st_length(st_setsrid(st_geogfromtext(st_asewkt(").append(geomName).append(")),4490))/1000 length from ").append(source).append(" where ").append(keyName).append("=:").append(keyName).append("))) where ").append(keyName).append("=:").append(keyName) ;
        Map<String,Object> mileageParam = new HashMap<>() ;
        mileageParam.put(keyName ,keyValue);
        int updateResult = namedParameterJdbcTemplate.update( updateSql.toString() ,mileageParam );
        if(updateResult == 0){
            logger.error("管线里程更新出错，目标管线可能不存在，" + keyName + "=" + keyValue);
        }
        if(updateResult > 1){
            logger.error("管线里程更新出错，目标管线不止一条，" + keyName + "=" + keyValue);
        }
        //1、
        StringBuffer mileageQuery = new StringBuffer("select st_length(st_setsrid(st_geogfromtext(st_asewkt(").append(geomName).append(")),4490))/1000 length from ").append(source).append(" where ").append(keyName).append("=:").append(keyName);
        Map<String,Object> param1 = new HashMap<>();
        param1.put(keyName,keyValue);
        double mileage = namedParameterJdbcTemplate.queryForObject(mileageQuery.toString(),param1,Double.class);
        result.put("mileage",mileage);
        //2、
        String sql = " SELECT  st_asewkt(" + geomName + ") from " + source + " where " + keyName + "= :" + keyName;
        Map<String,Object> param2 = new HashMap<>();
        param2.put(keyName,keyValue);
        String wkt = namedParameterJdbcTemplate.queryForObject(sql,param2,String.class);
        result.put("wkt",wkt);
        return  result;
    }
    /**
     * 识别单元生成逻辑：
     -- 1、查询地区等级单元数据
     -- 2、由地区等级单元里程值、潜在影响半径生成高后果区识别单元
     * @param params
     * @return
     */
    @PostMapping(value = "cell/init")
    @ResponseBody
    public BaseResult initZoneRankCells(@RequestBody Map<String ,Object> params) throws Exception {
        SimpleResult baseResult = new SimpleResult<String>();
        String pipelineSourceName = MapUtil.getString(params,"pipesegmentTableName","pd_pipesegment");
        String zoneSourceName = MapUtil.getString(params,"zoneSourceName","pd_zonerankcell");
        String pipelineOid = MapUtil.getString(params,"pipesegmentKeyValue");
        String pipelineKeyName = MapUtil.getString(params,"pipesegmentKeyName","eventid");
        double bufferDistance = MapUtil.getDouble(params,"bufferDistance");// m
        String bufferArea = MapUtil.getString(params,"bufferArea");// wkt
        String areaRankTableName = MapUtil.getString(params,"areaRankTableName");//
        Boolean deleteFlag = Boolean.parseBoolean(MapUtil.get(params,"deleteFlag","true").toString());
        Boolean mileageUpdateFlag = Boolean.parseBoolean(MapUtil.get(params,"mileageUpdateFlag","true").toString());
        String geomFieldName = MapUtil.getString(params ,"geomFieldName" ,"geom");
        String startMileageField = MapUtil.getString(params ,"startMileageFieldName" ,"start_mileage");
        String endMileageField = MapUtil.getString(params ,"endMileageFieldName" ,"end_mileage");
        String rankFieldName = MapUtil.getString(params ,"rankFieldName" ,"rank");

        //1、查询地区等级单元
        LayerQueryParam param = new LayerQueryParam();
        param.setSrsname(areaRankTableName);
        param.setReturnGeometry( true );
        param.setOrderBy("start_mileage");
        param.setOutFields("*");
        FeatureCollection<Feature> areaResult = geodataAccessService.query(param);
        Feature[] features =areaResult.getFeatures().toArray(new  Feature[0]);
        logger.info("查询到{}条地区等级数据",features.length) ;
        //
        List<Object[]> cellDatalist = new ArrayList<>();
        for(int i = 0 ; i < features.length ; i++){
            Feature feature = features[i] ;
            Map properties = feature.getAttributes();
            double startMileage = MapUtil.getDouble(properties,startMileageField) ;
            double endMileage = MapUtil.getDouble(properties,endMileageField) ;
            // 计算缓冲区
            String wkt = queryBufferArea(startMileage,endMileage,bufferDistance, pipelineSourceName,pipelineOid,pipelineKeyName,geomFieldName);
            String uuid = UUID.randomUUID().toString();
            String name = "HCA" + String.format("%05d",i + 1);
            Object[] leftData = { uuid,name ,startMileage,endMileage ,wkt } ;
            cellDatalist.add(leftData) ;
        }

        // 3、
        if( deleteFlag ){
            int delResult = clear(zoneSourceName);
            logger.info("成功清空{}条识别单元数据！" ,delResult);
        }
        String cellInsertSql = "insert into pd_zonerankcell(oid ,name,start_mileage ,end_mileage  ,geom ) values( ? ,?, ? , ?  ,ST_GeometryN(st_geomfromtext( ?),1) ) ";
        int count = 0 ;
        int[] insertResult = jdbcTemplate.batchUpdate(cellInsertSql ,cellDatalist);
        for(int i = 0 ; i < insertResult.length ; i++){
            count += insertResult[i];
        }
        logger.info("成功创建{}条识别单元数据！" ,count);
        baseResult.setData(count);
        return baseResult ;
    }

}
