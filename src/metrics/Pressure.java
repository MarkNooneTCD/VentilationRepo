package metrics;

public class Pressure {

    public enum Unit{
        PA, KPA, HPA
    }

    public static final Unit SI_UNIT = Unit.PA;

    private double value;

    public Pressure(double value, Unit unit){
        setPressure(value,unit);
    }

    public double paValue(){
        return value;
    }

    public double kPaValue(){
        return value*1000;
    }

    public double hPaValue(){
        return value*100;
    }

    public void setPressure(double value, Unit unit){
        this.value = asSIunit(value,unit);
    }

    public static double asSIunit(double value, Unit unit){
        switch (unit){
            case PA: return value;
            case KPA: return value/1000;
            case HPA: return value/100;
        }
        throw new IllegalArgumentException("Invalid unit type for Pressure");
    }

}
