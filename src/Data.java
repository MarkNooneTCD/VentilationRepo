import metrics.Temperature;

import java.time.*;
import java.util.ArrayList;

//

public class Data {
    private LocalDateTime dateTime;
    private Temperature temperature;
    private double relativeHumidity;
    private ArrayList<Event> events;

    public Data(LocalDateTime date, Temperature temp, double relativeHumidity){
        this.dateTime = date;
        this.temperature = temp;
        this.relativeHumidity = relativeHumidity;
        this.events = new ArrayList<>();
    }

    public void setDateTime(LocalDateTime newDateTime){
        this.dateTime = newDateTime;
    }

    private static LocalDate getDateFromDateTime(String date){
        int day= -1, month= -1, year= -1;
        String[] s = date.split(" ");
        String[] splitDate = s[0].split("-");
        day = Integer.parseInt(splitDate[0]);
        switch(splitDate[1]){
            case "jan":
                month = 1;
                break;
            case "feb":
                month = 2;
                break;
            case "mar":
                month = 3;
                break;
            case "apr":
                month = 4;
                break;
            case "may":
                month = 5;
                break;
            case "jun":
                month = 6;
                break;
            case "jul":
                month = 7;
                break;
            case "aug":
                month = 8;
                break;
            case "sep":
                month = 9;
                break;
            case "oct":
                month = 10;
                break;
            case "nov":
                month = 11;
                break;
            case "dec":
                month = 12;
                break;
        }
        year = Integer.parseInt(splitDate[2]);
        return LocalDate.of(year, month, day);
    }

    private static LocalTime getTimeFromDateTime(String date){
        String[] s = date.split(" ");
        return LocalTime.parse(s[1]);
    }

    public static LocalDateTime getDateTimeFromString(String s){
        return LocalDateTime.of(getDateFromDateTime(s), getTimeFromDateTime(s));
    }

    //**************** GETTERS ***********************//

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public double getRelativeHumidity() {
        return relativeHumidity;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}
