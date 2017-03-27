import metrics.Temperature;

public class Outside {

    private static Temperature temperature = null;
    private static double relativeHumidity = -1;

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

    public static Temperature getTemperature(){
        if(temperature == null)
            throw new NullPointerException("Fields not set yet");
        return temperature;
    }

}
