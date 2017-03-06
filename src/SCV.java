public class SCV extends VentilationSystem{

    private double supplyHumidtyThreshold;
    private double supplyTemperatureThreshold;

    private double demandHumidityThreshold;
    private double demandTemperatureThreshold;

    private SCV(Builder b){
        super(b);
        this.supplyHumidtyThreshold = b.supplyHumidityThreshold;
        this.supplyTemperatureThreshold = b.supplyTemperatureThreshold;
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

    public static class Builder extends VentilationSystem.Builder{

        double supplyHumidityThreshold;
        double supplyTemperatureThreshold;
        double demandHumidityThreshold;
        double demandTemperatureThreshold;


        public Builder supplyHumidtyThreshold(double supplyHumidityThreshold){
            this.supplyHumidityThreshold = supplyHumidityThreshold;
            return this;
        }


        public Builder supplyTemperatureThreshold(double supplyTemperateThreshold){
            this.supplyTemperatureThreshold = supplyTemperateThreshold;
            return this;
        }

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
            return new SCV(this);
        }
    }

}
