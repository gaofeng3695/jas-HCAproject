package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.dataaccess.IGeodataAccessService;
import cn.jasgroup.gis.io.shapefile.ShapeDataSupport;
import cn.jasgroup.gis.io.shapefile.ShapeTransformOptions;
import cn.jasgroup.gis.util.MathUtil;
import cn.jasgroup.gis.util.ReadConfigUtil;
import cn.jasgroup.gis.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
import java.util.UUID;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/8/26
 * @since JDK 1.80
 */
public class ShapeDataTransformThread extends Thread {

    /**
     *
     */
    protected static String targetShapeFileSuffix = "_poly";

    /**
     *
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    static{
        String fileSuffix = ReadConfigUtil.getPlatformConfig("hca.buildings.shapesuffix");
        if(StringUtil.isNotBlank(fileSuffix)){
            targetShapeFileSuffix = fileSuffix;
        }
    }

    private String threadName = "";

    private String sourceName ;

    private ShapeDataTransformProgress progress ;

    private File[] files = null ;

    private IGeodataAccessService geodataAccessService ;

    public ShapeDataTransformThread( String threadName, IGeodataAccessService geodataAccessService ,File[] files , ShapeDataTransformProgress progress){
        this.threadName = threadName ;
        this.sourceName = "hca_buildings_auto" ;
        this.progress = progress ;
        this.files = files ;
        this.geodataAccessService = geodataAccessService ;
    }

    /**
     *
     * @param folder
     * @return
     */
    public Boolean parseAndSaveShapeData( File folder ){
        String code = folder.getName() ;
        String shapeFileName = code + targetShapeFileSuffix ;
        String fileName = folder.getPath() + File.separator + shapeFileName + ".shp" ;
        ShapeDataSupport shapeDataSupport = new ShapeDataSupport();
        ShapeTransformOptions options = new ShapeTransformOptions();
        Feature[] features = shapeDataSupport.readShapeFile(fileName,options);

        if(features == null ){
            logger.warn("数据{}解析出错!" , code);
            return false;
        }
        if(features.length == 0){
            logger.warn("shp文件没有找到要素数据！" );
            return true;
        }
        for( int i = 0 ; i < features.length ; i++ ){
            Map<String ,Object> attr = features[i].getAttributes() ;
            String uuid = UUID.randomUUID().toString();
            attr.put( HcaAnalysisContext.TableKeyName , uuid );
            attr.put( HcaAnalysisContext.buildingCodeFieldName , "auto-" + code + "-" + String.format("%05d", i + 1 ) );
            attr.put( HcaAnalysisContext.buildingTypeFieldName , HcaAnalysisContext.buildingTypeValue_Settlement );
            attr.put( "active" , 1);
        }
        int result = 0 ;
        int[] resultArray = geodataAccessService.addFeatures( sourceName ,features );
        result = MathUtil.sum(resultArray) ;

        logger.info( "shape文件{}成功新增{}条要素数据,失败{}条。",shapeFileName,result,features.length - result);
        return true ;
    }

    @Override
    public void run() {
        logger.info("shape读取线程{}启动",this.threadName);
        int successCount = 0 , failCount = 0 ;
        for(int i = 0 ; i < files.length ; i++){
            File file = files[i] ;
            if(file.isDirectory()){
                Boolean success = parseAndSaveShapeData(file);
                this.progress.process(success);
                if(success){
                    successCount ++;
                }else{
                    failCount ++ ;
                }
            }
        }
        logger.info("shape读取线程{}结束 ,成功{}块 ，失败{} 块 ",this.threadName,successCount,failCount);
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
