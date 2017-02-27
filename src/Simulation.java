public class Simulation {

    private Data previousData;
    private Data currentData;

    private DataList dataList;
    private Building building;
    private Outside outside;

    public Simulation(DataList dataList){
        if(!dataList.hasNext())
            throw new IllegalArgumentException("Datalist must contain at least one line of data");

        this.dataList = dataList;

    }

    public void setInitialBuildingValues(){
        
    }



    public void run(){

        currentData = dataList.getNext();


        while(dataList.hasNext()){


        }


    }



}
