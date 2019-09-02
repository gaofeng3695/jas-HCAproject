package cn.jasgroup.hcas.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/8/26
 * @since JDK 1.80
 */
public class ShapeDataTransformProgress {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private int currentIndex ;

    private int success  ;

    private int total ;

    private String totalPercent ;

    private String successPercent ;

    public ShapeDataTransformProgress(int total){
        this.total = total ;
    }

    public void process(Boolean isSuccess){
        currentIndex ++ ;
        if(isSuccess){
            success ++ ;
        }
        totalPercent = String.format("%.2f", currentIndex  * 100.0 / total) ;
        successPercent = String.format("%.2f", success  * 100.0 / currentIndex) ;
        logger.info("已完成：{}%" ,totalPercent);
    }


    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getTotalPercent() {
        return totalPercent;
    }

    public void setTotalPercent(String totalPercent) {
        this.totalPercent = totalPercent;
    }

    public String getSuccessPercent() {
        return successPercent;
    }

    public void setSuccessPercent(String successPercent) {
        this.successPercent = successPercent;
    }
}
