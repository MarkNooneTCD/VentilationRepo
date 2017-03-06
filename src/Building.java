public class Building{

    private double volume;
    private Air air;

    public Building(double volume, Air insideAir){
        this.air = insideAir;
        this.volume = volume;
    }

    public Building(double volume, double initialHumidity, double initialTemperature, double initialAirQuality){
        this(volume, new Air(initialHumidity,initialTemperature,initialAirQuality));
    }



    /**
     * Simulate based on the current state of the simulation
     */
    public void simulate() {
        //TODO simulate any changes that occur such as heat transfer through the walls
    }

    /**
     * Updates the component based on the currentData being passed in
     *
     * @param currentData the current data to update from
     */
    public void update(Data currentData) {
        //TODO change the value of internal air based on passed in data
    }
}
