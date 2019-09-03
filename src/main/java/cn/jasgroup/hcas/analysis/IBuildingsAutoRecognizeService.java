package cn.jasgroup.hcas.analysis;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/8/26
 * @since JDK 1.80
 */
public interface IBuildingsAutoRecognizeService {

    /**
     * 读取目标目录shp数据,将地物识别数据导入到空间表
     */
    public int importToDB(String folderPath ,String sourceName) throws InterruptedException;

    /**
     * 给地物图形数据关联管线，并计算坐标起止里程等相关属性参数，
     */
    public int attachPipelineData(String pipelineOid);

    /**
     * 同步构筑物数据，将结果数据同步到构筑物表
     * @return
     */
    public int syncBuildingsData();


}
