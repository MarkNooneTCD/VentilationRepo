import metrics.Temperature;

public class Air {

    private static final double MOLAR_MASS_DRY_AIR = 0.028964;
    private static final double SPECIFIC_GAS_DRY_AIR = 287.058;

    private static final double MOLAR_MASS_WATER_VAPOUR = 0.018016;
    private static final double SPECIFIC_GAS_WATER_VAPOUR = 461.495;

    private static final double UNIVERSAL_GAS_CONSTANT = 8.314;

    //Must be set by constructor
    private double temperature;
    private double absolutePressure; //usually atmospheric pressure


    private double relativeHumidity = -1;
    private double partialVapourPressure = -1;



    public Air(double temperature, double pressure){
        this.temperature = temperature;
        this.absolutePressure = pressure;
    }


    /**
     * Set the partial vapour Pressure of this Air (Pa)
     * @param value value of partial vapour pressure
     * @return this
     */
    public Air partialVapourPressure(double value){
        this.partialVapourPressure = value;
        return this;
    }

    /**
     * Set the relative Humidity of this Air ( 0 - 1) where 1 is 100%
     * @param value value of relative humidity
     * @return this
     */
    public Air relativeHumidity(double value){
        this.relativeHumidity = value;
        return this;
    }

    /**
     * Converts a volume of this air into a mass (Kg) using
     * @param volume the volume of air (m^3)
     * @return the mass of a specific volume of air (Kg)
     */
    public double volumeToMass(double volume){
        return getAirDensity()*volume;
    }

    /**
     * Gets the density of this air
     * @return the density of this air in (kg/m^3)
     */
    public double getAirDensity(){

        boolean solvable = true;
        if(partialVapourPressure == -1){ //if no partialVapourPressure
            //get from mollier table instead or try calculate from available items
            solvable = false; //temp
        }
        if(!solvable)
            throw new IllegalArgumentException("Can't get mass with current information provided");

        return (absolutePressure*MOLAR_MASS_DRY_AIR
                + partialVapourPressure*(MOLAR_MASS_WATER_VAPOUR - MOLAR_MASS_DRY_AIR))
                / (UNIVERSAL_GAS_CONSTANT * temperature);
    }

    /**
     * Get the humidity ratio of this air
     * @return the humidity ratio of this air
     */
    public double getHumidityRatio(){

        boolean solvable = true;
        if(partialVapourPressure == -1){ //if no partialVapourPressure
            //get from mollier table or try calculate
            solvable = false;
        }
        if(!solvable)
            throw  new IllegalArgumentException("Can't get Humidity Ratio with current information");
        return 0.62198 * partialVapourPressure / (absolutePressure - partialVapourPressure);

    }

    public double saturationVapourPressure(){
        return Air.saturationVapourPressure(this.temperature);
    }


    public static double saturationVapourPressure(double temperature){
        double kelvin = Temperature.convert(temperature, Temperature.Unit.CELSIUS, Temperature.Unit.KELVIN);
        return Math.pow(Math.E , (77.3450 + 0.0057*kelvin - 7235.0 / kelvin)) / Math.pow(kelvin, 8.2);
    }

}
