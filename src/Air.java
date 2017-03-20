import metrics.*;

/**
 * Air consists of Dry Air and Water Vapour
 */
public class Air {

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


    private static final double MOLAR_MASS_DRY_AIR = 0.028964;
    private static final double SPECIFIC_GAS_DRY_AIR = 287.058;

    private static final double MOLAR_MASS_WATER_VAPOUR = 0.018016;
    private static final double SPECIFIC_GAS_WATER_VAPOUR = 461.495;

    private static final double UNIVERSAL_GAS_CONSTANT = 8.314;

    private static final double ATMOSPHERIC_PRESSURE = Pressure.STANDARD_ATMOSPHERIC_PRESSURE;
    //Must be set by constructor
    private Temperature temperature;
    private double volume;
    private double relativeHumidity;


    public Air(Temperature temperature, double relativeHumidity, double volume){
        this.temperature = temperature;
        this.volume = volume;
        this.relativeHumidity = relativeHumidity;
    }

    public Temperature getTemperature(){
        return temperature;
    }

    public double getRelativeHumidity(){
        return  relativeHumidity;
    }

    public double getHumidityRatio(){
        double vapourPressure = relativeHumidity*getSaturationPressure(temperature).pa();
        return (0.62198*vapourPressure)/ (ATMOSPHERIC_PRESSURE -vapourPressure);
    }

    public double getEnthalpy(){
        return 1.006*temperature.celsius() + getHumidityRatio() * (1.006*temperature.celsius() + 2501);
    }

    public double getSpecificVolume(){
        return (temperature.kelvin() * SPECIFIC_GAS_DRY_AIR * (1 + 1.6078 * getHumidityRatio()) ) / ATMOSPHERIC_PRESSURE;
    }

    public static Pressure getSaturationPressure(Temperature temp){
        double t = temp.kelvin();
        double val;
        if(temp.celsius() > 0) {
            val = Math.pow(Math.E, C8/t + C9 + C10*t + C11*t*t + C12*t*t*t+ C13*Math.log(t));
        }else{
            val= Math.pow(Math.E, C1*t + C2 + C3*t + C4*Math.pow(t,2) + C5*Math.pow(t,3)+ C6*Math.pow(t,4) + C7*Math.log(t));
        }
        return new Pressure(val, Pressure.Unit.PA);
    }

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

    public static void main(String[] args){
        Air a = new Air(new Temperature(32, Temperature.Unit.CELSIUS), .40, 20);
        Air b = new Air(new Temperature(12, Temperature.Unit.CELSIUS), .9, 25);
        a.mix(b);
        System.out.println(a.getSpecificVolume());
    }


}
