package metrics;

public class Temperature {

    public enum Unit{
        KELVIN,CELSIUS, FAHRENHEIT
    }

    public static final Unit SI_UNIT = Unit.KELVIN;

    private double value;

    public Temperature(double value, Unit unit){
        setTemperature(value,unit);
    }

    public double kelvinValue(){
        return value;
    }

    public double celsiusValue(){
        return value - 273.15;
    }

    public double fahrenheitValue(){
        return value * 9.0/5.0 - 459.67;
    }

    public void setTemperature(double value, Unit unit){
        this.value = asSIunit(value, unit);
    }cd

    public static double asSIunit(double value, Unit unit){
        switch (unit){
            case KELVIN: return value;
            case CELSIUS: return value + 273.15;
            case FAHRENHEIT: return (value + 459.67)* 5.0/9.0;
        }
        throw new IllegalArgumentException("Invalid unit type for Temperature");
    }
}
