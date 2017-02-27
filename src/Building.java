public class Building {

    private Air air;

    public Building(double initialHumidity, double initialTemperature, double initialAirQuality){
        this.air = new Air(initialHumidity,initialTemperature,initialAirQuality);
    }

}
