import metrics.Temperature;

public class DCV extends VentilationSystem {

    double demandHumidityThresholdLow;
    double demandHumidityThresholdHigh;
    double demandTemperatureThresholdHigh;
    double demandTemperatureThresholdLow;


    private DCV(Builder b){
        super(b);
        this.demandHumidityThresholdLow = b.demandHumidityThresholdLow;
        this.demandHumidityThresholdHigh = b.demandHumidityThresholdHigh;
        this.demandTemperatureThresholdHigh = b.demandTemperatureThresholdHigh;
        this.demandTemperatureThresholdLow = b.demandTemperatureThresholdLow;
    }


    @Override
    public void simulate() {

        Air i = building.getAir();
        Air o = outside.getAir(volumeInput);
        //if inside lower than threshold
        if(i.getTemperature().celsius() < demandTemperatureThresholdLow+1){

            //if outside hotter than inside
            if(o.getTemperature().celsius() > i.getTemperature().celsius()){
                ventInAir(volumeInput);
            }else{ //heat up some other way
               double energyNeeded = i.getMassOfAir()*1.01
                       *Math.abs(i.getTemperature().celsius() - demandTemperatureThresholdLow);
                i.setTemperature(demandTemperatureThresholdLow);
                i.setRelativeHumidity(i.getWaterVapourPressure()
                        /Air.getSaturationPressure(new Temperature(demandTemperatureThresholdLow, Temperature.Unit.CELSIUS)).pa());
                heatEnergyUsed += energyNeeded;
            }

        //if inside hotter than threshold
        }else if(i.getTemperature().celsius() > demandTemperatureThresholdHigh - 1){

            //if outside cooler than inside
            if(o.getTemperature().celsius() < i.getTemperature().celsius()){
                ventInAir(volumeInput);
            }
            else{ //else cool some other way
                System.out.println("cool down");
            }
        }

        double rh = building.getAir().getRelativeHumidity();

        //if inside rh lower than threshold
        if(rh < demandHumidityThresholdLow) {
            Air mixedAir = o.mix(i);
            //if resultant air is within tolerable temperature and new mix has higher rh than air inside
            if(mixedAir.getTemperature().celsius() < demandTemperatureThresholdHigh - 1
                    && mixedAir.getTemperature().celsius() > demandTemperatureThresholdLow +1
                    && mixedAir.getRelativeHumidity() > i.getRelativeHumidity()){
                ventInAir(volumeInput);
            }
            else if(mixedAir.getRelativeHumidity() > i.getRelativeHumidity()){ //else dehumidify somehow
                ventInAir(volumeInput);
            }else{
                System.out.println("humidify");
            }

        //if inside rh greater than threshold
        }else if(rh > demandHumidityThresholdHigh){

            Air mixedAir = o.mix(i);
            //if resultant air is within tolerable temperature and new mix has lower rh than air inside
            if(mixedAir.getTemperature().celsius() < demandTemperatureThresholdHigh - 1
                    && mixedAir.getTemperature().celsius() > demandTemperatureThresholdLow + 1
                    && mixedAir.getRelativeHumidity() < i.getRelativeHumidity()){
                ventInAir(volumeInput);
            }
            else if(mixedAir.getRelativeHumidity() < i.getRelativeHumidity()){ //if mixed air has less humidity, just do it
                timeSpentForcingHumidityReduction += 1;
                ventInAir(volumeInput);
            }else{
                System.out.println("dehumidify");
            }

        }
        if(i.getRelativeHumidity() < demandHumidityThresholdLow
                || i.getRelativeHumidity() > demandHumidityThresholdHigh){
            timeSpentOutisdeThresholdHumidity += 1;
        }
        if(i.getTemperature().celsius() < demandTemperatureThresholdLow
                || i.getTemperature().celsius() > demandTemperatureThresholdHigh){
            timeSpentOutsideThresholdTemperature += 1;
        }

    }

    public void ventInAir(double amount){
        Air outsideAir = outside.getAir(amount);
        Air newAir = building.getAir().mix(outsideAir);
        newAir.setVolume(building.getVolume());
        building.setAir(newAir);

        volumeOfAirVentedIn += amount;
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
