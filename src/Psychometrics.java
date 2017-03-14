import metrics.Pressure;
import metrics.Temperature;

public class Psychometrics {

    private static final double MOLAR_MASS_DRY_AIR = 0.028964;
    private static final double SPECIFIC_GAS_DRY_AIR = 287.058;

    private static final double MOLAR_MASS_WATER_VAPOUR = 0.018016;
    private static final double SPECIFIC_GAS_WATER_VAPOUR = 461.495;

    private static final double UNIVERSAL_GAS_CONSTANT = 8.314;

    Temperature dryBulb;
    Temperature wetBulb;
    Pressure pressure;


    public Psychometrics(Builder b){
        this.dryBulb = b.dryBulb;
        this.wetBulb = b.wetBulb;
        this.pressure = b.pressure;
    }



    public double getHumidityRatioAtSaturation(Temperature temp){
//        return 0.622 * getSaturationPressure(temp).kPA() / (pressure.kPA() - getSaturationPressure(temp).kPA());
        return -1;
    }

    public double getHumidityRatio(){
        double twb = wetBulb.celsius();
        double tdb = dryBulb.celsius();
        double wsat = getHumidityRatioAtSaturation(wetBulb);

        double top = wsat * (2501.0 - 2.381*twb) - 1.006 * (tdb - twb);
        double bot = 2501.0 + 1.805*tdb - 4.186 * twb;

        return top/bot;
    }

    public double getRelativeHumidity(){
        double w = getHumidityRatio();
        double wsat = getHumidityRatioAtSaturation(wetBulb);
        double top = w * (0.622 + wsat);
        double bot = wsat * (0.622 * w);
        return top/bot;
    }


    public static Builder create(){
        return new Builder();
    }

    public static class Builder{

        private Temperature wetBulb;
        private Temperature dryBulb;
        private Pressure pressure;

        private Builder(){}

        public Builder dryBulb(Temperature t){
            this.dryBulb = t;
            return this;
        }

        public Builder wetBulb(Temperature t){
            this.wetBulb = t;
            return this;
        }

        public Builder totalPressure(Pressure p){
            this.pressure = p;
            return this;
        }

        public Psychometrics build(){
            return new Psychometrics(this);
        }

    }
}
