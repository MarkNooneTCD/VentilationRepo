import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcus on 13/03/2017.
 */
public class Mosierr {
    String baseFilePath;
    ArrayList<MosierRecord> records;


    Mosierr(String baseFilePath){
        this.baseFilePath = baseFilePath;
        records = new ArrayList<MosierRecord>();
        CSVReader reader = null;
        try {
            System.out.println("Reading CSV ...");
            reader = new CSVReader(new FileReader(baseFilePath));
            List<String[]> csv = reader.readAll();

            for(int i =0; i < csv.size(); i++){
                String[] s = csv.get(i);
                MosierRecord m = new MosierRecord(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9]);
//                System.out.println("Relative humidity is " + m.getRelativeHumidity() + ", Dry Bulb Temperature is " + m.getDryBulbTemperature()
//                                    + "Altitude is " + m.getAltitude() + ", SVP is " + m.getSaturatedVapourPressure() + ", PVP is " + m.getPartialVapourPressure() + ", Humidity Ratio is "
//                                    + m.getHumidityRatio() + ", Enthalpy is " + m.getEnthalpy() + ", Dew Point is " + m.getDewPoint() + ", Wet Bulb Temperature is "
//                                    + m.getWetBulbTemperature() + ", Specific Volume is " + m.getSpecificVolume());
                records.add(m);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MosierRecord mosierLookup(double rh, double dbt){
        for(MosierRecord m: records){
            if(m.getRelativeHumidity() == rh){
                if(m.getDryBulbTemperature() == dbt){
                    return m;
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        return null;
    }
}
