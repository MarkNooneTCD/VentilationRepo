public class Building implements Component{

    private double volume;
    private Air air;

    public Building(double volume, Air insideAir){
        this.air = insideAir;
        this.volume = volume;
    }

    public Building(double volume, double initialHumidity, double initialTemperature, double initialAirQuality){
        this(volume, new Air(initialHumidity,initialTemperature,initialAirQuality));
    }

    public void update(Data currentData){
        //TODO modify air here
    }

}
