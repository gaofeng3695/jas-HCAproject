package cn.jasgroup.hcas.analysis;

import cn.jasgroup.gis.data.Feature;
import cn.jasgroup.gis.util.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 由构筑物要素，划分里程段分布
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/6/25
 * @since JDK 1.80
 */
public class BuildingsMileageScope {

    private Double minMileage = 0d;

    private Double maxMileage = Double.POSITIVE_INFINITY;

    private List<MileageScope> mileageScopes = new ArrayList<>();

    public BuildingsMileageScope(Feature[] buildings){
        for(int i = 0 ; i < buildings.length ; ++i){
            Map props = buildings[i].getAttributes();
            Double start = MapUtil.getDouble(props , HcaAnalysisContext.startMileageFieldName ,0d);
            Double end = MapUtil.getDouble(props , HcaAnalysisContext.endMileageFieldName ,0d);
            int index = check(start,end);
            if(index != -1){
                MileageScope target = mileageScopes.get(index);
                target.resetScope(start,end);
            }else{
                mileageScopes.add(new MileageScope(start,end));
            }
        }
    }

    public Double getMinMileage() {
        return minMileage;
    }

    public void setMinMileage(Double minMileage) {
        this.minMileage = minMileage;
    }

    public Double getMaxMileage() {
        return maxMileage;
    }

    public void setMaxMileage(Double maxMileage) {
        this.maxMileage = maxMileage;
    }

    public List<MileageScope> getMileageScopes() {
        return mileageScopes;
    }

    public void setMileageScopes(List<MileageScope> mileageScopes) {
        this.mileageScopes = mileageScopes;
    }

    public Double checkStartMileageBorder(Double startMileage, Double buffer){
        Double end = startMileage ;
        Double start = end - buffer ;
        while (true){
            int index = check(start ,end);
            if(index == -1){
                return start > 0 ? start : 0 ;
            }else{
                MileageScope scope = mileageScopes.get(index) ;
                end = scope.getStartMileage();
                start = end - buffer;
            }
        }
    }

    public Double checkEndMileageBorder(Double endMileage,Double buffer){
        Double start = endMileage ;
        Double end = start + buffer;
        while(true){
            int index = check(start ,end);
            if(index == -1){
                return end;
            }else{
                MileageScope scope = mileageScopes.get(index) ;
                start = scope.getEndMileage();
                end = start + buffer;
            }
        }
    }

    public int check(Double start , Double end){
        for(int i =0 ; i < mileageScopes.size() ;i++){
            MileageScope sege = mileageScopes.get(i);
            if(sege.getEndMileage() < start || sege.getStartMileage() > end ){
                continue;
            }
            if(sege.checkScope(start,end)){
                return i;
            }
        }
        return -1;
    }

}
