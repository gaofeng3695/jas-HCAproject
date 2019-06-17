package cn.jasgroup.hcas.analysis;

/**
 * Created by kc on 2019/6/11.
 */
public class AreaRankCell {
    private int type ;
    private double startMileage ;
    private double endMileage ;
    private double middleMileage ;
    private String buildingType ;
    private int population ;
    private int households ;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getStartMileage() {
        return startMileage;
    }

    public void setStartMileage(double startMileage) {
        this.startMileage = startMileage;
    }

    public double getEndMileage() {
        return endMileage;
    }

    public void setEndMileage(double endMileage) {
        this.endMileage = endMileage;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getHouseholds() {
        return households;
    }

    public void setHouseholds(int households) {
        this.households = households;
    }

    public double getMiddleMileage() {
        return middleMileage;
    }

    public void setMiddleMileage(double middleMileage) {
        this.middleMileage = middleMileage;
    }
}
