package cn.jasgroup.hcas.analysis;

/**
 * Created by jas on 2019/6/3.
 */
public abstract class AnalysisBaseService {


    /**
     * 导出高后果区报告
     *
     * @return
     */
    public String exportReport() {

        return null;
    }

    /**
     * 重置识别单元的起止里程
     * @return
     */
    public int resetMileage(){

        return 1;
    }
}
