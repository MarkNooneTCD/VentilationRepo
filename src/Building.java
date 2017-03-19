import metrics.Temperature;

public class Building{

    private double volume;
    private Air air;

    public Building(double volume, Temperature temperature, double relativeHumidity){
        this.air = new Air(temperature, relativeHumidity, volume);
        this.volume = volume;
    }



    public void simulate() {
        //TODO simulate any changes that occur such as heat transfer through the walls
    }


    public void update(Data currentData) {
        //TODO change the value of internal air based on passed in data
    }
}
