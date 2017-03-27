import java.time.LocalTime;

public class Event {

    public enum Type {
        CARBON_MONOXIDE,
        CARBON_DIOXIDE,
        WATER_VAPOUR,
        VOC,
        TEMPERATURE
    }

    private Type type;
    private String source;
    private LocalTime startTime;
    private LocalTime endTime;
    private double rate;

    Event(String pollutant, String source, String time, String rate){
        this.type = parseEventType(pollutant);
        this.source = source;
        this.rate = Double.parseDouble(rate);
        String[] s = time.split("-");
        startTime = LocalTime.of((int) Double.parseDouble((s[0].split(":"))[0]), (int) Double.parseDouble((s[0].split(":"))[1]));
        endTime = LocalTime.of((int) Double.parseDouble((s[1].split(":"))[0]), (int) Double.parseDouble((s[1].split(":"))[1]));
    }

    private Type parseEventType(String s){
        if(s.equals("Carbon Monoxide")){
            return Type.CARBON_MONOXIDE;
        }else if(s.equals("Carbon Dioxide")){
            return Type.CARBON_DIOXIDE;
        }else if(s.equals("Water Vapour")){
            return Type.WATER_VAPOUR;
        }else if(s.equals("VOC")){
            return Type.VOC;
        }else if(s.equals("Temperature")){
            return Type.TEMPERATURE;
        }
        throw new IllegalArgumentException(s + " is not a supported scenario event");
    }
    // --------------------------- GETTERS ----------------------------


    public Type getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public double getRate() {
        return rate;
    }
}
