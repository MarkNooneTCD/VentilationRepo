public class Simulation {

    private DataList dataList;
    private Building building;
    private Outside outside;

    public Simulation(DataList dataList){
        if(!dataList.hasNext())
            throw new IllegalArgumentException("Datalist must contain at least one line of data");

        this.dataList = dataList;

    }

    public void setInitialBuildingValues(double volume, Air insideAir){
        this.building = new Building(volume,insideAir);
    }

    public void setInitialOutsideConditions(Air outsideAir){
        this.outside = new Outside(outsideAir);
    }



    public void simulate(){

        while(dataList.hasNext()){
            Data currentData = dataList.getNext();
            building.update(currentData);
            outside.update(currentData);
        }

    }



}
