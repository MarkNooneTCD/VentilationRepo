import metrics.Temperature;

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

        Temperature t = building.getAir().getTemperature();
        if(t.celsius() < demandTemperatureThresholdLow){
            //heat up
        }else if(t.celsius() > demandTemperatureThresholdLow){
            //cool down
        }

        double rh = building.getAir().getRelativeHumidity();
        if(rh < demandHumidityThresholdLow) {
            //humidify
        }else if(rh > demandHumidityThresholdHigh){
            //dehumidify
        }
//
//        Air outsideAir = outside.getAir(volumeInput);
//        Air insideAir = building.getAir();
//        Air newAir = building.getAir().mix(outside.getAir(volumeInput));
//
//        System.out.println(String.format("Outside : %f , %f\nInside : %f , %f\nNew Inside : %f , %f",
//                outsideAir.getRelativeHumidity(), outsideAir.getTemperature().celsius()
//        ,insideAir.getRelativeHumidity(), insideAir.getTemperature().celsius(),
//                newAir.getRelativeHumidity(), newAir.getTemperature().celsius()));
//
//        newAir.setVolume(building.getVolume()); //pretend we vent out the extra volume
//
//        building.setAir(newAir);
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
