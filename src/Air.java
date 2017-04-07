import metrics.*;

/**
 * Air consists of Dry Air and Water Vapour
 */
public class Air {

    //Constants needed for air saturation pressure calculations
    private static final double C1 = -5.6745354 * Math.pow(10,3);
    private static final double C2 = 6.3425247;
    private static final double C3 = -9.6778430 * Math.pow(10,-3);
    private static final double C4 = 6.2215701 * Math.pow(10,-7);
    private static final double C5 = 2.0747825 * Math.pow(10, -9);
    private static final double C6 = -9.4840240 * Math.pow(10, -13);
    private static final double C7 = 4.1635019;
    private static final double C8 = -5.8002206 * Math.pow(10, 3);
    private static final double C9 = 1.3914993;
    private static final double C10 = -4.8640239 * Math.pow(10, -2);
    private static final double C11 = 4.1764768 * Math.pow(10, -5);
    private static final double C12= -1.4452093 * Math.pow(10, -8);
    private static final double C13 = 6.5459673;
    private static final double SPECIFIC_GAS_DRY_AIR = 287.058;

    //The air pressure that is assumed throughout all calcs
    private static final double ATMOSPHERIC_PRESSURE = Pressure.STANDARD_ATMOSPHERIC_PRESSURE;

    private Temperature temperature;
    private double volume;
    private double relativeHumidity;

    /**
     * @param temperature temperature of the air
     * @param relativeHumidity the relativity humidity in % (0 - 1)
     * @param volume the volume of air being considered
     */
    public Air(Temperature temperature, double relativeHumidity, double volume){
        this.temperature = temperature;
        this.volume = volume;
        this.relativeHumidity = relativeHumidity;
    }

    public void setVolume(double volume){
        this.volume = volume;
    }

    public void setTemperature(double t){
        this.temperature = new Temperature(t, Temperature.Unit.CELSIUS);
    }

    public void setRelativeHumidity(double rh){
        this.relativeHumidity = rh;
    }

    public Temperature getTemperature(){
        return temperature;
    }

    public double getVolume(){
        return volume;
    }

    public double getRelativeHumidity(){
        return  relativeHumidity;
    }

    //Returns the humidity ratio , the ratio of the mass of water vapour to the mass of dry air
    public double getHumidityRatio(){
        double vapourPressure = relativeHumidity*getSaturationPressure(temperature).pa();
        return (0.62198*vapourPressure)/ (ATMOSPHERIC_PRESSURE - vapourPressure);
    }

    //A measure of the enthalpy in the air (how much energy is essentially in this system of air)
    public double getEnthalpy(){
        return 1.006*temperature.celsius() + getHumidityRatio() * (1.006*temperature.celsius() + 2501);
    }

    //Returns the specific volume of the air
    public double getSpecificVolume(){
        return (temperature.kelvin() * SPECIFIC_GAS_DRY_AIR * (1 + 1.6078 * getHumidityRatio()) ) / ATMOSPHERIC_PRESSURE;
    }

    //Gets the saturation pressure of air at a given temperature as approximated by the formula
    public static Pressure getSaturationPressure(Temperature temp){
        double t = temp.kelvin();
        double val;
        if(temp.celsius() > 0) {
            val = Math.pow(Math.E, C8/t + C9 + C10*t + C11*t*t + C12*t*t*t+ C13*Math.log(t));
        }else if(temp.celsius() <= 0){
            if(t == 0) t = -0.0000001;
            val= Math.pow(Math.E, C1/t + C2 + C3*t + C4*Math.pow(t,2) + C5*Math.pow(t,3)+ C6*Math.pow(t,4) + C7*Math.log(t));
        }else{
            throw new NumberFormatException("Temperature was NaN");
        }
        return new Pressure(val, Pressure.Unit.PA);
    }

    //Returns the amount of pressure the water vapour in the air is exerting
    public double getWaterVapourPressure(){
        return relativeHumidity * getSaturationPressure(temperature).pa();
    }

    //Returns the density of water vapour in the air
    public double getWaterVapourDensity(){
        return 0.0022*getWaterVapourPressure() / temperature.kelvin();
    }

    //Returns the total density of the air
    public double getAirDensity(){
        double top = (ATMOSPHERIC_PRESSURE-getWaterVapourPressure())*0.028964 + getWaterVapourPressure()*0.018016;
        double bot = 8.314 * temperature.kelvin();
        return top/bot;
    }

    //Returns the mass of the air
    public double getMassOfAir(){
        return getAirDensity()*volume;
    }


    //Mixes two sets of air and returns the resultant air
    public Air mix(Air other){
        double h1 = this.getEnthalpy();
        double h2 = other.getEnthalpy();
        double w1 = this.getHumidityRatio();
        double w2 = other.getHumidityRatio();
        double sv1 = this.getSpecificVolume();
        double sv2 = other.getSpecificVolume();

        double m1 = this.volume / sv1;
        double m2 = other.volume / sv2;
        double m3 = m1 + m2;

        double w3 = (m2*w2 + m1*w1) / m3;
        double h3 = (m2*h2 + m1*h1) / m3;

        Temperature t3 = new Temperature((h3 - 2501*w3)/(1.006*(1+w3)), Temperature.Unit.CELSIUS);
        double rh3 = (w3*ATMOSPHERIC_PRESSURE)/ ( getSaturationPressure(t3).pa()*(0.62198 + w3));
        double sv3 = (t3.kelvin()*SPECIFIC_GAS_DRY_AIR * (1 + 1.6078*w3)) / ATMOSPHERIC_PRESSURE;
        double vol3 = m3*sv3;

        return new Air(t3, rh3, vol3);
    }

}
