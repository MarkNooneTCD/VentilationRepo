import metrics.Temperature;

public class Building{

    private double volume;
    private double carbonMonoxide = 0;
    private double voc = 0;
    private Air air;
    private double uvalue;
    private double exposedBuilding = 5;

    private double eneryLossToInsulation = 0;

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

    public void setCarbonMonoxideLevels(double val){
        this.carbonMonoxide = val;
    }

    public void setVOC(double val){
        this.voc = val;
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
                    handleTemperatureChange(event);
                    break;
                case WATER_VAPOUR:
                    handleWaterVapourChange(event);
                    break;
                case VOC:
                    handleVOCChange(event);
                    break;
                case CARBON_MONOXIDE:
                    handleCarbonMonoxideChange(event);
                    break;
                case CARBON_DIOXIDE:
                    handleCarbonDioxideChange(event);
                    break;
            }
        }
    }

    private void handleTemperatureChange(Event e){
        double energy = e.getRate() / 1000.0;

        //temp change
        double t = energy / air.getMassOfAir() * 1.01 + air.getTemperature().celsius();
        Temperature newTemp = new Temperature(t, Temperature.Unit.CELSIUS);
        air.setTemperature(newTemp.celsius());

        //humidity change cause of temp change
        double newRh = air.getWaterVapourPressure() / Air.getSaturationPressure(newTemp).pa();
        air.setRelativeHumidity(newRh);


    }


    private void handleWaterVapourChange(Event e){
        double waterVapour = e.getRate();
        //humidity change

        double currentDensity = air.getWaterVapourDensity();
        double newDensity = (waterVapour/1000.0)/air.getVolume() + currentDensity;
        double newVapourPressure = newDensity*air.getTemperature().kelvin() / 0.0022;
        double newRH = newVapourPressure / Air.getSaturationPressure(air.getTemperature()).pa();
        air.setRelativeHumidity(newRH);

        double newTemp = 0.0022*newVapourPressure / newDensity;  //innacuracy here
        Temperature t = new Temperature(newTemp, Temperature.Unit.KELVIN);
        air.setTemperature(t.celsius());

    }

    private void handleCarbonDioxideChange(Event e){

    }

    private void handleCarbonMonoxideChange(Event e){

    }

    private void handleVOCChange(Event e){

    }





}
