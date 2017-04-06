import com.opencsv.CSVReader;
import metrics.Temperature;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class EnvironmentParser {

    // These are the columns in which the relevant data is in the csv file.
    // It is used in order to get the correct value from the line. It is
    // using zero-based numbering similar to arrays.
    private final int dateColumn = 0;
    private final int rainInMilimetersColumn = 2;
    private final int temperatureCelciusColumn = 4;
    private final int wetBulbTemperatureCelciusColumn = 6;
    private final int dewPointTemperatureCelciusColumn = 7;
    private final int relativeHumidityPercentageColumn = 9;
    private final int vapourPressureHPAColumn = 8;
    private final int windSpeedKnotsColumn = 12;

    private String baseFilePath;

    EnvironmentParser(String baseFilePath,DataList dataList ,boolean printEntries){
        this.baseFilePath = baseFilePath;

        try (CSVReader reader = new CSVReader(new FileReader(baseFilePath))){
            System.out.println("Reading CSV ...");
            List<String[]> csv = reader.readAll();
            System.out.println("Beginning environment parse...");
            for(int i = 0; i < csv.size(); i++) {

                //interpolate any bad data
                String[] line = interpolateLine(csv, i);

                //parse the data from strings
                LocalDateTime dateTime = Data.getDateTimeFromString(line[dateColumn]);
                double temperatureCelcius = Double.parseDouble(line[temperatureCelciusColumn]);
                Temperature temperature = new Temperature(temperatureCelcius, Temperature.Unit.CELSIUS);
                double relativeHumidity = Double.parseDouble(line[relativeHumidityPercentageColumn]) / 100;// to decimal

                //Generate data for the full 60 minutes of the hour
                for(int n = 0; n < 60; n++){
                    Data d = new Data(dateTime.plusMinutes(n),temperature,relativeHumidity);
                    dataList.append(d);
                }

                if(printEntries) {
                    System.out.println("Date: " + dateTime.toString() + ", Temperature was " + temperature.celsius() +
                            " celcius, Relative Humidity was " + line[relativeHumidityPercentageColumn] + " percent");
                }
            }
            System.out.println("Finished environment parse.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] interpolateLine(List<String[]> l, int index){
        String[] goodLine = l.get(index);
        for(int i=0; i<goodLine.length; i++){
            if(goodLine[i].equals(" ") || goodLine[i].equals("") || goodLine[i] == null){
                System.out.println("WARNING: Interpolating line");
                Double x, y;
                try{
                    x = Double.parseDouble(l.get(index-1)[i]);
                } catch(NullPointerException | NumberFormatException ex) {
                    x=0.0;
                }
                try{
                    y = Double.parseDouble(l.get(index+1)[i]);
                } catch(NullPointerException | NumberFormatException ex) {
                    y=0.0;
                }
                goodLine[i] = "" + (x+y/2);
            }
        }
        return goodLine;
    }


}
