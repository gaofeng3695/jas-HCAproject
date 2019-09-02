package cn.jasgroup.hcas.analysis.service;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.data.FeatureCollection;
import cn.jasgroup.gis.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.dataaccess.LayerQueryParam;
import cn.jasgroup.gis.geometry.Point;
import cn.jasgroup.gis.geometry.Polygon;
import cn.jasgroup.gis.geometry.Polyline;
import cn.jasgroup.gis.util.*;
import cn.jasgroup.hcas.analysis.HcaAnalysisContext;
import cn.jasgroup.hcas.analysis.IBuildingsAutoRecognizeService;
import cn.jasgroup.hcas.analysis.ShapeDataTransformProgress;
import cn.jasgroup.hcas.analysis.ShapeDataTransformThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Map;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/8/26
 * @since JDK 1.80
 */
@Service
public class BuildingsAutoRecognizeService implements IBuildingsAutoRecognizeService {

    /**
     *
     */
    protected static String dataFolderPath = "E:\\mapdata\\hca\\pipe-wv2";

    /**
     *
     */
    protected static Integer threadSize = 3;

    /**
     *
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    protected IGeodataAccessService geodataAccessService ;

    /**
     *
     */
    static{
        String shapeDataFolder = ReadConfigUtil.getPlatformConfig("hca.buildings.shapefolder");
        if(StringUtil.isNotBlank(shapeDataFolder)){
            dataFolderPath = shapeDataFolder;
        }
        String size = ReadConfigUtil.getPlatformConfig("hca.buildings.threadSize");
        if(StringUtil.isNotBlank(size)){
            threadSize = Integer.valueOf(size);
        }
    }

    /**
     *
     * @param folder
     * @param sourceName
     */
    @Override
    public int importToDB(String folder, String sourceName) {
        //
        String folderPath = StringUtil.isBlank(folder) ? dataFolderPath : folder ;
        //1、读取shape文件目录，遍历将数据转化为jas要素对象并写入到内存
        File root = new File(folderPath) ;
        File[] files = root.listFiles() ;
        if(files == null ){
            logger.warn("根目录下 " + folderPath + "没有文件夹数据!");
            return 0 ;
        }else{
            logger.warn("根目录 " + folderPath + "包含 " + files.length + "个分块结果 ");
        }
        //2、
        ShapeDataTransformProgress progress = new ShapeDataTransformProgress(files.length);

        int preSize = files.length / threadSize ;
        int count = 0 ;
        try {
            for(int i = 1 ; i <= threadSize  ; i++){
                int arrSize = 0;
                if(count + preSize <= files.length ){
                    arrSize = preSize ;
                }else{
                    arrSize = files.length - count;
                }
                File[] sub = new File[arrSize];
                System.arraycopy( files, count , sub ,0,arrSize);
                Thread thread = new ShapeDataTransformThread("shape reader thread_" + i , geodataAccessService ,sub ,progress) ;
                thread.start();
                thread.join();
                count += preSize;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return progress.getSuccess();
    }

    /**
     * @param pipelineOid
     * @return
     */
    @Override
    public int attachPipelineData(  String pipelineOid) {
        String sourceName = "hca_buildings_auto";
         //$、查询管线
        LayerQueryParam param = new LayerQueryParam();
        param.setSrsname(HcaAnalysisContext.pipelineSourceName);
        param.setWhere(HcaAnalysisContext.TableKeyName + " like '" + pipelineOid + "'");
        FeatureCollection collection = geodataAccessService.query(param);
        if(collection == null || collection.getSize() == 0) {
            throw new RuntimeException("没有查询到管线数据，table="+HcaAnalysisContext.pipelineSourceName+",oid=" + pipelineOid);
        }
        Feature f = collection.getFeatures().get(0);
        Polyline pipeline = (Polyline) f.getGeometry();
        //$、创建线性参考
        LinearReferenceUtil linearReferenceUtil = new LinearReferenceUtil(pipeline);
        linearReferenceUtil.resetMeasureByLocalLength();//

        Feature[] data = getAllFeatures();

        for(int j = 0 ; j < data.length ; j++){
            Feature feature = data[j];
            Polygon area = (Polygon) feature.getGeometry();
            Map<String,Object> attr = feature.getAttributes();
            //$、计算前后里程值
            Polygon projected = (Polygon) GeometryUtil.blToGuass(area);
            Point[] points = projected.toPoints();
            double startMileage = linearReferenceUtil.getLinearPolyline().getTotalLength() ;
            double endMileage = 0d ;
            Polyline linearLine = (Polyline) linearReferenceUtil.getLinearPolyline().getPolyline();
            double verticalDistance = Double.MAX_VALUE ;
            for(int i = 0 ; i < points .length ; i++){
                Point point = points[i];
                double mileage = linearReferenceUtil.interpolatePoint(point);
                startMileage = Math.min(startMileage ,mileage);
                endMileage = Math.max(endMileage ,mileage);
                double distance = GeometryUtil.distance(point,linearLine);
                verticalDistance = Math.min(verticalDistance,distance);
            }
            double start = Double.valueOf( String.format("%.3f",startMileage / 1000));
            double end = Double.valueOf( String.format("%.3f",endMileage / 1000));
            attr .put("start_mileage",start);
            attr .put("end_mileage",end);
            attr .put("horizontal_distance",1000 *( end - start));
            attr .put("vertical_distance",verticalDistance);
            attr .put("pipeline_oid",pipelineOid);
            attr .put("pipeline_oid",pipelineOid);
        }
        //计算属性数据
        int[] re = geodataAccessService.updateFeatures(sourceName ,data);
        int result = MathUtil.sum(re) ;
        return result;
    }

    protected Feature[] getAllFeatures(){
        LayerQueryParam layerQueryParam = new LayerQueryParam();
        layerQueryParam.setSrsname("hca_buildings_auto");
        layerQueryParam.setWhere("active=1");
        layerQueryParam.setOutFields("*");
        FeatureCollection collection = geodataAccessService.query(layerQueryParam);
        if(collection == null){
            logger.error("查询自动识别构筑物表出错，请检查参数是否正确！");
        }else{
            logger.info("查询自动识别构筑物表，获取到{}条数据。" ,collection .getSize());
        }

        Feature[] data = FeatureCollectionUtil.toLowerCaseFeature(collection);
        return data;
    }

    /**
     *
     * @return
     */
    @Override
    public int syncBuildingsData() {
        Feature[] data = getAllFeatures();
        int[] re = geodataAccessService.addFeatures("hca_buildings",data );
        int result = MathUtil.sum(re) ;
        logger.info("同步结束，成功新增{}条数据。" ,result);
        return result;
    }

}
