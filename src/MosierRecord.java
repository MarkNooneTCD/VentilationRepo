/**
 * Created by marcus on 13/03/2017.
 */
public class MosierRecord {
    private double relativeHumidity;
    private double dryBulbTemperature;
    private double altitude;
    private double saturatedVapourPressure;
    private double partialVapourPressure;
    private double humidityRatio;
    private double enthalpy;
    private double dewPoint;
    private double wetBulbTemperature;
    private double specificVolume;

    private boolean isCreated = false;

    MosierRecord(String rh, String dbt, String a, String svp, String pvp, String hr, String e, String dp, String wbt, String sv){
        relativeHumidity = Double.parseDouble(rh);
        dryBulbTemperature = Double.parseDouble(dbt);
        altitude = Double.parseDouble(a);
        saturatedVapourPressure = Double.parseDouble(svp);
        partialVapourPressure = Double.parseDouble(pvp);
        humidityRatio = Double.parseDouble(hr);
        enthalpy = Double.parseDouble(e);
        dewPoint = Double.parseDouble(dp);
        wetBulbTemperature = Double.parseDouble(wbt);
        specificVolume = Double.parseDouble(sv);

        isCreated = true;
    }

    //***************** GETTERS *****************//


    public double getRelativeHumidity() {
        if(!isCreated){
            throw new NullPointerException("Mosier Records not yet set.");
        }
        return relativeHumidity;
    }

    public double getDryBulbTemperature() {
        if(!isCreated){
            throw new NullPointerException("Mosier Records not yet set.");
        }
        return dryBulbTemperature;
    }

    public double getAltitude() {
        if(!isCreated){
            throw new NullPointerException("Mosier Records not yet set.");
        }
        return altitude;
    }

    public double getSaturatedVapourPressure() {
        if(!isCreated){
            throw new NullPointerException("Mosier Records not yet set.");
        }
        return saturatedVapourPressure;
    }

    public double getPartialVapourPressure() {
        if(!isCreated){
            throw new NullPointerException("Mosier Records not yet set.");
        }
        return partialVapourPressure;
    }

    public double getHumidityRatio() {
        if(!isCreated){
            throw new NullPointerException("Mosier Records not yet set.");
        }
        return humidityRatio;
    }

    public double getEnthalpy() {
        if(!isCreated){
            throw new NullPointerException("Mosier Records not yet set.");
        }
        return enthalpy;
    }

    public double getDewPoint() {
        if(!isCreated){
            throw new NullPointerException("Mosier Records not yet set.");
        }
        return dewPoint;
    }

    public double getWetBulbTemperature() {
        if(!isCreated){
            throw new NullPointerException("Mosier Records not yet set.");
        }
        return wetBulbTemperature;
    }

    public double getSpecificVolume() {
        if(!isCreated){
            throw new NullPointerException("Mosier Records not yet set.");
        }
        return specificVolume;
    }
}
