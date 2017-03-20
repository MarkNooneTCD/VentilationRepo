import metrics.Temperature;

public class Outside {

    private Temperature temperature;
    private double relativeHumidity;

    public Outside(Temperature t, double relativeHumidity) {
        this.temperature = t;
        this.relativeHumidity = relativeHumidity;
    }

    public Air getAir(double volumeAmountToGet){
        return new Air(temperature, relativeHumidity, volumeAmountToGet);
    }

    public void update(Data currentData) {
        temperature = currentData.getTemperature();
        relativeHumidity = currentData.getRelativeHumidity();
    }

}
