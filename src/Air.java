import metrics.Pressure;
import metrics.Temperature;

/**
 * Air consists of Dry Air and Water Vapour
 */
public class Air {

    private static final double MOLAR_MASS_DRY_AIR = 0.028964;
    private static final double SPECIFIC_GAS_DRY_AIR = 287.058;

    private static final double MOLAR_MASS_WATER_VAPOUR = 0.018016;
    private static final double SPECIFIC_GAS_WATER_VAPOUR = 461.495;

    private static final double UNIVERSAL_GAS_CONSTANT = 8.314;

    //Must be set by constructor
    private Temperature temperature;
    private Pressure absolutePressure; //usually atmospheric pressure


    private double relativeHumidity = -1;
    private double partialVapourPressure = -1;



    public Air(double temperature, double pressure){
        this.temperature = temperature;
        this.absolutePressure = pressure;
    }

}
