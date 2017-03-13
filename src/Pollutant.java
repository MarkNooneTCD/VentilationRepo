import java.time.LocalTime;

/**
 * Created by marcus on 13/03/2017.
 */
public class Pollutant {
    private String pollutant;
    private String source;
    private LocalTime startTime;
    private LocalTime endTime;
    private double rate;

    Pollutant(String pollutant, String source, String time, String rate){
        this.pollutant = pollutant;
        this.source = source;
        this.rate = Double.parseDouble(rate);
        String[] s = time.split("-");
        startTime = LocalTime.of((int) Double.parseDouble((s[0].split(":"))[0]), (int) Double.parseDouble((s[0].split(":"))[1]));
        endTime = LocalTime.of((int) Double.parseDouble((s[1].split(":"))[0]), (int) Double.parseDouble((s[1].split(":"))[1]));
        System.out.println(startTime + ", " + endTime);
    }

    public boolean inTime(LocalTime t){
        return t.compareTo(startTime) >= 0 && t.compareTo(endTime) <= 0;
    }

    // --------------------------- GETTERS ----------------------------


    public String getPollutant() {
        return pollutant;
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
