import metrics.Pressure;
import metrics.Temperature;

public class Psychometrics {

    private static final double MOLAR_MASS_DRY_AIR = 0.028964;
    private static final double SPECIFIC_GAS_DRY_AIR = 287.058;

    private static final double MOLAR_MASS_WATER_VAPOUR = 0.018016;
    private static final double SPECIFIC_GAS_WATER_VAPOUR = 461.495;

    private static final double UNIVERSAL_GAS_CONSTANT = 8.314;

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


    Temperature dryBulb;
    Temperature wetBulb;
    Pressure pressure;


    public Psychometrics(Builder b){
        this.dryBulb = b.dryBulb;
        this.wetBulb = b.wetBulb;
        this.pressure = b.pressure;
    }

    public Pressure getSaturationPressure(Temperature temp){
        double t = temp.kelvin();
        double val;
        if(temp.celsius() > 0) {
            val = Math.pow(Math.E, C8/t + C9 + C10*t + C11*t*t + C12*t*t*t+ C13*Math.log(t));
        }else{
            val= Math.pow(Math.E, C1*t + C2 + C3*t + C4*Math.pow(t,2) + C5*Math.pow(t,3)+ C6*Math.pow(t,4) + C7*Math.log(t));
        }
        return new Pressure(val, Pressure.Unit.PA);
    }

    public double getHumidityRatioAtSaturation(Temperature temp){
        return 0.622 * getSaturationPressure(temp).kPA() / (pressure.kPA() - getSaturationPressure(temp).kPA());
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

    public static void main(String[] args){
        Psychometrics p = Psychometrics.create()
                .dryBulb(new Temperature(40, Temperature.Unit.CELSIUS))
                .wetBulb(new Temperature(30, Temperature.Unit.CELSIUS))
                .totalPressure(new Pressure(101, Pressure.Unit.KPA))
                .build();
        System.out.println((p.getHumidityRatioAtSaturation(p.wetBulb)));
    }
}
