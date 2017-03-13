import java.time.*;
import java.util.ArrayList;

public class Data {
    private LocalDate date;
    private LocalTime time;
    private String dateInString;
    private double rainInMilimeters;
    private double temperatureCelcius;
    private double wetBulbTemperatureCelcius;
    private double dewPointTemperatureCelcius;
    private double relativeHumidityPercentage;
    private double vapourPressureHPA;
    private double windSpeedKnots;
    private ArrayList<Pollutant> pollutants;

    Data(String date, String rain, String temperature, String wetTemp, String dewTemp, String
         relative, String vapour, String wind, ArrayList<Pollutant> p){
        pollutants = p;
        this.date = getDateFromDateTime(date);
        this.time = getTimeFromDateTime(date);
        this.dateInString = date;
        rainInMilimeters = Double.parseDouble(rain);
        temperatureCelcius = Double.parseDouble(temperature);
        wetBulbTemperatureCelcius = Double.parseDouble(wetTemp);
        dewPointTemperatureCelcius = Double.parseDouble(dewTemp);
        relativeHumidityPercentage = Double.parseDouble(relative);
        vapourPressureHPA = Double.parseDouble(vapour);
        windSpeedKnots = Double.parseDouble(wind);

    }

    public static LocalDate getDateFromDateTime(String date){
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

    public static LocalTime getTimeFromDateTime(String date){
        String[] s = date.split(" ");
        return LocalTime.parse(s[1]);
    }

    //**************** GETTERS ***********************//

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDateInString() {
        return dateInString;
    }

    public double getRainInMilimeters() {
        return rainInMilimeters;
    }

    public double getTemperatureCelcius() {
        return temperatureCelcius;
    }

    public double getWetBulbTemperatureCelcius() {
        return wetBulbTemperatureCelcius;
    }

    public double getDewPointTemperatureCelcius() {
        return dewPointTemperatureCelcius;
    }

    public double getRelativeHumidityPercentage() {
        return relativeHumidityPercentage;
    }

    public double getVapourPressureHPA() {
        return vapourPressureHPA;
    }

    public double getWindSpeedKnots() {
        return windSpeedKnots;
    }

    public ArrayList<Pollutant> getPollutants() {
        return pollutants;
    }
}
