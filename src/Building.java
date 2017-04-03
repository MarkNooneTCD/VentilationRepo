import metrics.Temperature;

public class Building{

    public static double PSEUDO_PPM_VOLUME = 0.01;

    private Air air;
    private double volume;
    private double uvalue;
    private double exposedBuilding = 5;
    private double eneryLossToInsulation = 0;

    private double carbonMonoxidePpm = 0;
    private double carbonDioxidePpm = 0;
    private double vocPpm = 0;

    public Building(double volume, Temperature temperature, double relativeHumidity, double uvalue){
        this.air = new Air(temperature, relativeHumidity, volume);
        this.volume = volume;
        this.uvalue = uvalue;
    }

    public void setAir(Air newAir){
        this.air = newAir;
    }

    public Air getAir(){
        return air;
    }

    public double getVolume(){
        return volume;
    }

    public void setCarbonMonoxide(double val){
        this.carbonMonoxidePpm = val;
    }

    public void setVOC(double val){
        this.vocPpm = val;
    }

    public void setCarbonDioxide(double val) { this.carbonDioxidePpm = val;}

    public double getCarbonMonoxidePpm() {
        return carbonMonoxidePpm;
    }

    public double getCarbonDioxidePpm(){
        return carbonDioxidePpm;
    }

    public double getVocPpm(){
        return vocPpm;
    }


    public void update(Data currentData) {
        //heat loss due to convection
        double energyLost = 60 * uvalue* exposedBuilding
                * Math.abs(Outside.getTemperature().celsius() - air.getTemperature().celsius());
        double newTemp = air.getTemperature().celsius() - energyLost/1000.0/air.getMassOfAir() * 1.01;

        eneryLossToInsulation += energyLost;
        air.setTemperature(newTemp);


        for(Event event : currentData.getEvents()){
            switch (event.getType()){

                case TEMPERATURE:
                    handleTemperatureChange(event.getRate());
                    break;
                case WATER_VAPOUR:
                    handleWaterVapourChange(event.getRate());
                    break;
                case VOC:
                    handleVOCChange(event.getRate());
                    break;
                case CARBON_MONOXIDE:
                    handleCarbonMonoxideChange(event.getRate());
                    break;
                case CARBON_DIOXIDE:
                    handleCarbonDioxideChange(event.getRate());
                    break;
            }
        }
    }

    private void handleTemperatureChange(double rate){
        double energy = rate / 1000.0;

        //temp change
        double t = energy / air.getMassOfAir() * 1.01 + air.getTemperature().celsius();
        Temperature newTemp = new Temperature(t, Temperature.Unit.CELSIUS);
        air.setTemperature(newTemp.celsius());

        //humidity change cause of temp change
        double newRh = air.getWaterVapourPressure() / Air.getSaturationPressure(newTemp).pa();
        air.setRelativeHumidity(newRh);


    }


    public void handleWaterVapourChange(double rate){
        double waterVapour = rate;
        //humidity change

        double currentDensity = air.getWaterVapourDensity();
        double newDensity = (waterVapour/1000.0)/(air.getVolume()) + currentDensity;
        double newVapourPressure = newDensity*air.getTemperature().kelvin() / 0.0022;
        double newRH = newVapourPressure / Air.getSaturationPressure(air.getTemperature()).pa();
        air.setRelativeHumidity(newRH);

        double newTemp = 0.0022*newVapourPressure / newDensity;  //innacuracy here
        Temperature t = new Temperature(newTemp, Temperature.Unit.KELVIN);
        air.setTemperature(t.celsius());
    }

    public void handleCarbonDioxideChange(double rate){
        double co2 = rate;
        double current = carbonDioxidePpm;

        //PPM[pt] = PPM[NTP]*(760 / P)*(T / 273.15)    // P (mmHG) , T (K)
//        double addedCO2 = co2 * (760 / 760) * (air.getTemperature().kelvin() / 273.15);

        //(Mg/M^3) = PPM[pt] * (22.4/ MW)*(760 / P)* (T / 273.15)
        double currentMg = current * (22.4/ 44.01)*(760/760)*(air.getTemperature().kelvin() / 273.15);
        double addedMg = co2 * (22.4/ 44.01)*(760/760)*(air.getTemperature().kelvin() / 273.15);

        double totalMg = (currentMg * volume + addedMg * (PSEUDO_PPM_VOLUME)) / (volume + PSEUDO_PPM_VOLUME);

        carbonDioxidePpm = totalMg*(22.4/ 44.01)*(273.15 / air.getTemperature().kelvin()    ); //convert to stp
    }

    private void handleCarbonMonoxideChange(double rate){
        double co = rate;
        double current = getCarbonMonoxidePpm();

        //PPM[pt] = PPM[NTP]*(760 / P)*(T / 273.15)    // P (mmHG) , T (K)
//        double addedCO = co2 * (760 / 760) * (air.getTemperature().kelvin() / 273.15);

        //(Mg/M^3) = PPM[pt] * (22.4/ MW)*(760 / P)* (T / 273.15)
        double currentMg = current * (22.4/ 28.01)*(760/760)*(air.getTemperature().kelvin() / 273.15);
        double addedMg = co* (22.4/ 28.01)*(760/760)*(air.getTemperature().kelvin() / 273.15);

        double totalMg = (currentMg * volume + addedMg * (PSEUDO_PPM_VOLUME)) / (volume + PSEUDO_PPM_VOLUME);

        carbonMonoxidePpm = totalMg*(22.4/ 28.01)*(273.15 / air.getTemperature().kelvin()); //convert to stp

    }

    private void handleVOCChange(double rate){
        double voc = rate;
        double current = vocPpm;

        //PPM[pt] = PPM[NTP]*(760 / P)*(T / 273.15)    // P (mmHG) , T (K)
//        double addedVOC = co2 * (air.getTemperature().kelvin() / 273.15);

        //(Mg/M^3) = PPM[pt] * (22.4/ MW)*(760 / P)* (T / 273.15)
        double currentMg = current * (22.4/ 44)*(air.getTemperature().kelvin() / 273.15);
        double addedMg = voc * (22.4/ 44)*(air.getTemperature().kelvin() / 273.15);

        double totalMg = (currentMg * volume + addedMg * (PSEUDO_PPM_VOLUME)) / (volume + PSEUDO_PPM_VOLUME);

        vocPpm = totalMg*(22.4/ 28.01)*(273.15 / air.getTemperature().kelvin()); //convert to stp
    }

}
