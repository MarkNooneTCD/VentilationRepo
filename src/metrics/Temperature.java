package metrics;

public class Temperature {

    public enum Unit{
        KELVIN,CELSIUS, FAHRENHEIT
    }

    public static final Unit SI_UNIT = Unit.KELVIN;

    public static double convert(double value, Unit from ,Unit to){
        return asSpecifiedunit(asSIunit(value,from), to);
    }

    private static double asSIunit(double value, Unit unit){
        switch (unit){
            case KELVIN: return value;
            case CELSIUS: return value + 273.15;
            case FAHRENHEIT: return (value + 459.67)* 5.0/9.0;
        }
        throw new IllegalArgumentException("Invalid unit type for Temperature");
    }

    private static double asSpecifiedunit(double value,Unit unit){
        switch (unit){
            case KELVIN: return value;
            case CELSIUS: return value - 273.15;
            case FAHRENHEIT: return value * 9.0/5.0 - 459.67;
        }
        throw new IllegalArgumentException("Invalid unit type for Temperature");
    }
}