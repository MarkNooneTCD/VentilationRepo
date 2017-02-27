public class Air {

    private double humidity;
    private double temperature;
    private double airQuality;

    public Air(double humidity, double temperature, double airQuality){
        this.humidity = humidity;
        this.temperature = temperature;
        this.airQuality = airQuality;
    }

    public void changeHumidity(double humidityChange){
        this.humidity += humidityChange;
    }

    public void changeTemperature(double temperatureChange){
        this.temperature += temperatureChange;
    }

    public void changeAirQuality(double airQualityChange){
        this.airQuality += airQualityChange;
    }

    public double getHumidty() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(double airQuality) {
        this.airQuality = airQuality;
    }
}
