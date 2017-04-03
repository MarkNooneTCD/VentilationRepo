import metrics.Temperature;

public class DCV extends VentilationSystem {

    double demandHumidityThresholdLow;
    double demandHumidityThresholdHigh;
    double demandTemperatureThresholdHigh;
    double demandTemperatureThresholdLow;

    DCV(Builder b){
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
        boolean vented = false;
        double rh = building.getAir().getRelativeHumidity();

        //if inside lower than threshold
        if(i.getTemperature().celsius() < demandTemperatureThresholdLow+1){

            //if outside hotter than inside
            if(o.getTemperature().celsius() > i.getTemperature().celsius()){
                ventInAir(o);
                vented = true;
            }else{ //heat up some other way
                double heatedAirTemp = (( heaterPower*60/1000) + o.getMassOfAir()*1.005*o.getTemperature().celsius())
                        / o.getMassOfAir() * 1.005;
                o.setTemperature(heatedAirTemp);
                Air newInside = i.mix(o);
                if(newInside.getTemperature().celsius() > i.getTemperature().celsius()) {
                    i.setTemperature(newInside.getTemperature().celsius());
                    i.setRelativeHumidity(newInside.getRelativeHumidity());
                    results.heatEnergyUsed += heaterPower;
                    results.heaterRunTime += 1;
                }
            }

        //if inside hotter than threshold
        }else if(i.getTemperature().celsius() > demandTemperatureThresholdHigh - 1) {

            //if outside cooler than inside
            if (o.getTemperature().celsius() < i.getTemperature().celsius()) {
                ventInAir(o);
                vented = true;
            } else { //else cool some other way
                double cooledAirTemp = (( -coolerPower*60/1000) + o.getMassOfAir()*1.005*o.getTemperature().celsius())
                        / o.getMassOfAir() * 1.005;
                i.setTemperature(cooledAirTemp);
                Air newInside = i.mix(o);
                i.setTemperature(newInside.getTemperature().celsius());
                i.setRelativeHumidity(newInside.getRelativeHumidity());
                results.coolingEnergyUsed += coolerPower;
                results.coolingRunTime += 1;
            }
        }

        //if inside rh lower than threshold
        if(rh < demandHumidityThresholdLow) {
            Air mixedAir = o.mix(i);
            //if resultant air is within tolerable temperature and new mix has higher rh than air inside
            if(mixedAir.getTemperature().celsius() < demandTemperatureThresholdHigh - 1
                    && mixedAir.getTemperature().celsius() > demandTemperatureThresholdLow +1
                    && mixedAir.getRelativeHumidity() > i.getRelativeHumidity()
                    && !vented){
                ventInAir(o);
                vented = true;
            }
            else if(mixedAir.getRelativeHumidity() > i.getRelativeHumidity()){ //else dehumidify somehow
                ventInAir(o);
                vented = true;
            }else{
                double waterAdded = (humidifierLitresAdderPerDay * 1000)/ (24.0 * 60.0);
                results.waterAdded += waterAdded;
                results.humidifierEnergyUsed += humidifierPower*60;
                results.humidifierRunTime += 1;
                building.handleWaterVapourChange(waterAdded);
            }

        //if inside rh greater than threshold
        }else if(rh > demandHumidityThresholdHigh) {

            Air mixedAir = o.mix(i);
            //if resultant air is within tolerable temperature and new mix has lower rh than air inside
            if (mixedAir.getTemperature().celsius() < demandTemperatureThresholdHigh - 1
                    && mixedAir.getTemperature().celsius() > demandTemperatureThresholdLow + 1
                    && mixedAir.getRelativeHumidity() < i.getRelativeHumidity()
                    && !vented) {
                ventInAir(o);
                vented = true;
            } else if (mixedAir.getRelativeHumidity() < i.getRelativeHumidity()) { //if mixed air has less humidity, just do it
                ventInAir(o);
                vented = true;
            } else {
                double waterRemoved = (dehumidifierLitresRemovePerDay * 1000)/ (24.0 * 60.0);
                results.totalWaterRemoved += waterRemoved;
                results.dehumidifierEnergyUsed += dehumidifierPower*60;
                results.dehumidifierRunTime += 1;
                building.handleWaterVapourChange(-waterRemoved);
            }
        }

        if(building.getVocPpm() > vocThreshold && !vented) {
            ventInAir(o);
            vented = true;
        }
        if(building.getCarbonDioxidePpm() > carbonDioxideThreshold && !vented) {
            ventInAir(o);
            vented = true;
        }
        if(building.getCarbonMonoxidePpm() > carbonMonoxideThreshold && !vented){
            ventInAir(o);
            vented = true;
        }
        if(i.getRelativeHumidity() < demandHumidityThresholdLow
                || i.getRelativeHumidity() > demandHumidityThresholdHigh){
            results.timeSpentOutisdeThresholdHumidity += 1;
        }
        if(i.getTemperature().celsius() < demandTemperatureThresholdLow
                || i.getTemperature().celsius() > demandTemperatureThresholdHigh){
            results.timeSpentOutsideThresholdTemperature += 1;
        }
        //check for higher or lowest values for results
        resultsUpdate();
    }

    private void ventInAir(Air outsideAir){
        Air newAir = building.getAir().mix(outsideAir);
        newAir.setVolume(building.getVolume());
        building.setAir(newAir);
        double factorReduction = (building.getVolume() - volumeInput)/building.getVolume();

        results.CO2vented += (building.getCarbonDioxidePpm() - building.getCarbonDioxidePpm()*factorReduction);
        results.COvented += (building.getCarbonMonoxidePpm() - building.getCarbonMonoxidePpm()*factorReduction);
        results.VOCvented += (building.getVocPpm() - building.getVocPpm()*factorReduction);

        building.setCarbonDioxide(building.getCarbonDioxidePpm() * factorReduction);
        building.setCarbonMonoxide(building.getCarbonMonoxidePpm() * factorReduction);
        building.setVOC(building.getVocPpm() * factorReduction);
        results.volumeOfAirVentedIn += volumeInput;
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
