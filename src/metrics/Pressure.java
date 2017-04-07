package metrics;
/*
 Works by storing all pressure objects in SI_UNIT form and
 converting to the necessary unit as needed
 */
public class Pressure {

    //Pressure units
    public enum Unit{
        PA, KPA, HPA, MILLI_BAR
    }

    public static final Unit SI_UNIT = Unit.PA;
    public static final double STANDARD_ATMOSPHERIC_PRESSURE = 101325;

    private double value;

    /**
     * Create a Pressure object
     * @param value the value
     * @param unit the unit which the value is in
     */
    public Pressure(double value, Unit unit){
        this.value = asSIunit(value, unit);
    }

    public double pa(){
        return asSpecifiedUnit(value, Unit.PA);
    }

    public double hPA(){
        return asSpecifiedUnit(value, Unit.HPA);
    }

    public double kPA(){
        return asSpecifiedUnit(value, Unit.KPA);
    }

    public double milliBar(){ return asSpecifiedUnit(value, Unit.MILLI_BAR);
    }

    //converts value from one unit to another
    public static double convert(double value, Unit from, Unit to){
        return asSpecifiedUnit(asSIunit(value,from), to);
    }

    //converts a value and its Unit to its value at SI
    private static double asSIunit(double value, Unit unit){
        switch (unit){
            case PA: return value;
            case KPA: return value*1000.0;
            case HPA: return value*100.0;
            case MILLI_BAR: return value*100.0;
        }
        throw new IllegalArgumentException("Invalid unit type for Pressure");
    }

    //Returns a value at SI to a specified unit
    private static double asSpecifiedUnit(double value, Unit unit){
        switch (unit){
            case PA: return value;
            case KPA: return value/1000.0;
            case HPA: return value/100.0;
            case MILLI_BAR: return value/100.0;
        }
        throw new IllegalArgumentException("Invalid unit type for Pressure");
    }

}
