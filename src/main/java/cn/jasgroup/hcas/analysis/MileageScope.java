package cn.jasgroup.hcas.analysis;
/**
 * 一个里程段分布
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/6/25
 * @since JDK 1.80
 */
public class MileageScope {
    private Double startMileage ;
    private Double endMileage ;

    public MileageScope(Double startMileage , Double endMileage ){
        this.startMileage = startMileage;
        this.endMileage = endMileage;
    }

    public Boolean checkScope(Double startMileage ,Double endMileage){
        return !(startMileage >= this.endMileage || endMileage <= this.startMileage) ;
    }

    public void resetScope(Double startMileage ,Double endMileage){
        Double start = Math.min(this.startMileage ,startMileage);
        Double end = Math.max(this.endMileage ,endMileage);
        this.startMileage = start;
        this.endMileage = end;
    }

    public Double getStartMileage() {
        return startMileage;
    }

    public void setStartMileage(Double startMileage) {
        this.startMileage = startMileage;
    }

    public Double getEndMileage() {
        return endMileage;
    }

    public void setEndMileage(Double endMileage) {
        this.endMileage = endMileage;
    }
}
