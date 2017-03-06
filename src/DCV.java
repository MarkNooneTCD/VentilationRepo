public class DCV extends VentilationSystem{

    private double demandHumidityThreshold;
    private double demandTemperatureThreshold;

    private double totalEnergyUsed;

    private DCV(Builder b){
        super(b);
        this.demandHumidityThreshold = b.demandHumidityThreshold;
        this.demandTemperatureThreshold = b.demandTemperatureThreshold;
    }


    @Override
    public void simulate() {
        ///////////////////////////
        //TODO all logic goes in here
        //////////////////////////
    }

    @Override
    public Builder create() {
        return new Builder();
    }

    /**
     * Builder class to help construct a DCV instance without having a large constructor
     */
    public static class Builder extends VentilationSystem.Builder{

        double demandHumidityThreshold;
        double demandTemperatureThreshold;

        public Builder demandHumidtyThreshold(double demandHumidityThreshold){
            this.demandHumidityThreshold = demandHumidityThreshold;
            return this;
        }

        public Builder demandTemperatureThreshold(double demandTemperatureThreshold){
            this.demandTemperatureThreshold = demandTemperatureThreshold;
            return this;
        }

        @Override
        public VentilationSystem build() {
            return new DCV(this);
        }
    }

}
