import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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
    private int dataArrayIndex = 0;

    private ArrayList<Data> environmentData = new ArrayList<Data>();
    private boolean printCSV = false;

    EnvironmentParser(String baseFilePath, boolean printEntries, ScenarioParser scenarioParser, ConfigParser configParser){
        this.baseFilePath = baseFilePath;
        printCSV = printEntries;

        try (CSVReader reader = new CSVReader(new FileReader(baseFilePath))){
            System.out.println("Reading CSV ...");
            List<String[]> csv = reader.readAll();
            System.out.println("Beginning environment parse...");
            LocalTime time = configParser.getStartTime();
            LocalDate date = configParser.getStartDate();
            for(int i =0; i < csv.size(); i++) {

                String[] line = interpolateLine(csv, i);
                if(printCSV) {
                    System.out.println("Date: " + line[dateColumn] + ", Rain (mm): " + line[rainInMilimetersColumn] + ", Temperature was " + line[temperatureCelciusColumn] +
                            " celcius, Wetbulb Temperature was " + line[wetBulbTemperatureCelciusColumn] + " celcius, Dew Point Temperature was " + line[dewPointTemperatureCelciusColumn] +
                            " celcius, Relative Humidity was " + line[relativeHumidityPercentageColumn] + " percent, Vapour Pressure was " + line[vapourPressureHPAColumn] +
                            " hector pascals, Average Wind Speed was " + line[windSpeedKnotsColumn] + " knots.");
                }
                for(int n =0; n<60; n++){
                    ArrayList<Pollutant> tmp = null;//scenarioParser.hasPollutantsAtTime(time);
                    Data d = new Data(line[dateColumn], line[rainInMilimetersColumn], line[temperatureCelciusColumn], line[wetBulbTemperatureCelciusColumn],
                            line[dewPointTemperatureCelciusColumn], line[relativeHumidityPercentageColumn], line[vapourPressureHPAColumn], line[windSpeedKnotsColumn], tmp);
                    environmentData.add(d);
                    time = time.plusMinutes(1);
                }
                time = time.plusMinutes(1);
                if(time.compareTo(LocalTime.of(00, 00)) == 0){
                    date = date.plusDays(1);
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
                System.out.println("Interpolated");
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

    public Data getData(){
        return hasData()? environmentData.get(dataArrayIndex++) : null;
    }

    public Data getDataAt(int i){
        return environmentData.get(i);
    }

    public boolean hasDataAt(int i){ return environmentData.get(i) != null;}

    public boolean hasData(){
        return dataArrayIndex < environmentData.size();
    }


}
