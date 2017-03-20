public class DCV extends VentilationSystem {

    double demandHumidityThresholdLow;
    double demandHumidityThresholdHigh;
    double demandTemperatureThresholdHigh;
    double demandTemperatureThresholdLow;
    private double totalEnergyUsed;

    private DCV(Builder b){
        super(b);
        this.demandHumidityThresholdLow = b.demandHumidityThresholdLow;
        this.demandHumidityThresholdHigh = b.demandHumidityThresholdHigh;
        this.demandTemperatureThresholdHigh = b.demandTemperatureThresholdHigh;
        this.demandTemperatureThresholdLow = b.demandTemperatureThresholdLow;
    }


    @Override
    public void simulate() {
        Air newAir = building.getAir().mix(outside.getAir(volumeInput));

        
        building.setAir(newAir);
    }

    private boolean exceedsTemperatureThreshold(){
        double temp = building.getAir().getTemperature().celsius();
        return temp > demandTemperatureThresholdHigh || temp < demandTemperatureThresholdLow;
    }

    private boolean exceedsRelativeHumidityThreshold(){
        double rh = building.getAir().getRelativeHumidity();
        return rh > demandHumidityThresholdHigh || rh < demandHumidityThresholdLow;
    }


    public static Builder createDCV(){
        return new Builder();
    }


    /**
     * Builder class to help construct a DCV instance without having a large constructor
     */
    public static class Builder extends VentilationSystem.Builder{

        private double demandHumidityThresholdLow;
        private double demandHumidityThresholdHigh;
        private double demandTemperatureThresholdHigh;
        private double demandTemperatureThresholdLow;

        public Builder demandHumidityThreshold(double low, double high){
            this.demandHumidityThresholdLow = low;
            this.demandHumidityThresholdHigh = high;
            return this;
        }

        public Builder demandTemperatureThreshold(double low, double high){
            this.demandTemperatureThresholdLow = low;
            this.demandTemperatureThresholdHigh = high;
            return this;
        }

        @Override
        public VentilationSystem build() {
            return new DCV(this);
        }
    }

}
