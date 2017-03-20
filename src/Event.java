import java.time.LocalTime;

public class Event {
    private String event;
    private String source;
    private LocalTime startTime;
    private LocalTime endTime;
    private double rate;

    Event(String pollutant, String source, String time, String rate){
        this.event = pollutant;
        this.source = source;
        this.rate = Double.parseDouble(rate);
        String[] s = time.split("-");
        startTime = LocalTime.of((int) Double.parseDouble((s[0].split(":"))[0]), (int) Double.parseDouble((s[0].split(":"))[1]));
        endTime = LocalTime.of((int) Double.parseDouble((s[1].split(":"))[0]), (int) Double.parseDouble((s[1].split(":"))[1]));
    }


    // --------------------------- GETTERS ----------------------------


    public String getEvent() {
        return event;
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
