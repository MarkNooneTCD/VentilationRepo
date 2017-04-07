package metrics;

/*
 Works by storing all temperature objects in SI_UNIT form and
 converting to the necessary unit as needed
 */
public class Temperature {

    //The units available to use
    public enum Unit{
        KELVIN,CELSIUS, FAHRENHEIT
    }

    public static final Unit SI_UNIT = Unit.KELVIN;

    private double value;

    /**
     * Create a temperature object
     * @param value the value to use
     * @param unit the unit the value is in
     */
    public Temperature(double value, Unit unit){
        this.value = asSIunit(value, unit);
    }

    public double kelvin(){
        return asSpecifiedunit(this.value, Unit.KELVIN);
    }

    public double celsius(){
        return asSpecifiedunit(this.value, Unit.CELSIUS);
    }

    public double fahrenheit(){
        return asSpecifiedunit(this.value, Unit.FAHRENHEIT);
    }

    //converts value from one unit to another
    public static double convert(double value, Unit from ,Unit to){
        return asSpecifiedunit(asSIunit(value,from), to);
    }

    //converts a value and its Unit to its value at SI
    private static double asSIunit(double value, Unit unit){
        switch (unit){
            case KELVIN: return value;
            case CELSIUS: return value + 273.15;
            case FAHRENHEIT: return (value + 459.67)* 5.0/9.0;
        }
        throw new IllegalArgumentException("Invalid unit type for Temperature");
    }

    //Returns a value at SI to a specified unit
    private static double asSpecifiedunit(double value,Unit unit){
        switch (unit){
            case KELVIN: return value;
            case CELSIUS: return value - 273.15;
            case FAHRENHEIT: return value * 9.0/5.0 - 459.67;
        }
        throw new IllegalArgumentException("Invalid unit type for Temperature");
    }
}
