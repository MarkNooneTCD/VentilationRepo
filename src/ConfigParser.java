import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by marcus on 04/03/2017.
 */
public class ConfigParser {

    private double ElectricityCostPerKilowatt;
    private double DCVTemperatureThresholdHigh;
    private double DCVTemperatureThresholdLow;
    private double DCVHumidityThresholdHigh;
    private double DCVHumidityThresholdLow;
    private double DCVAirQualityThresholdLow;
    private double SCVTemperatureThresholdHigh;
    private double SCVTemperatureThresholdLow;
    private double SCVHumidityThresholdHigh;
    private double SCVHumidityThresholdLow;
    private double SCVAirQualityThresholdLow;
    private double VentMaxAirIntake;
    private double VentMaxAirOutake;
    private double VentCostPerTemperatureChange;
    private double VentCostPerHumidityChange;
    private double VentCostPerQualityChange;
    private double BuildingUValue;
    private double BuildingAirVolume;
    private LocalTime startTime;
    private LocalDate startDate;

    private JSONObject configObject;
    private String baseFilePath;
    private boolean isParsed = false;
    private boolean printStats = false;

    ConfigParser(String baseFilePath, boolean printStats){
        this.baseFilePath = baseFilePath;
        try {

            this.printStats = printStats;
            System.out.println("Loading Configuration Data .......");
            FileReader reader = new FileReader(baseFilePath);
            JSONParser jsonParser = new JSONParser();

            // Validates the data assuring that all necessary data is present
            // This method will throw an exception if data fails any test.
            configObject = (JSONObject) jsonParser.parse(reader);
            parse();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch(ConfigParserException ex) {
            ex.printStackTrace();
        }

        System.out.println("Configuration data loaded!");
    }

    // This will set up all configuration information within the parser object.
    // The programmer can then reference the parser for the most up to date
    // information regarding configuration. EXAMPLE: configParser.BuildingVolume
    // will hold the volume of the building, same for configParser.SCVHumidity etc..
    // This will hold true to all variable listed in the documentation

    private void parse() throws ConfigParserException {
        //TODO Make the JSON keys a constant for easier changing.

        JSONObject building;
        JSONObject thresholds;
        JSONObject ventilationSystem;
        if(configObject.containsKey("Thresholds") && configObject.containsKey("VentilationSystem") && configObject.containsKey("Building")){
            building = (JSONObject) configObject.get("Building");
            thresholds = (JSONObject) configObject.get("Thresholds");
            ventilationSystem = (JSONObject) configObject.get("VentilationSystem");
        } else {
            throw new ConfigParserException("Building, Threshold or Ventilation System objects not found. \n " +
                    "Please check config and any spelling.");
        }

        if(building == null || !(building.containsKey("AirVolume") && building.containsKey("U-Value"))){
            throw new ConfigParserException("Building Object does not meet the requirements.\n Please read documentation fo rmore information.");
        } else {
            String s = (String) building.get("AirVolume");
            BuildingAirVolume = Double.parseDouble(s);
            s = (String) building.get("U-Value");
            BuildingUValue = Double.parseDouble(s);

            if(printStats) {
                System.out.println("Building Air Volume is " + BuildingAirVolume + " meters cubed.");
                System.out.println("Building U-Value is " + BuildingUValue + " watts per meters squared.");
            }
        }



        if(thresholds == null || !(thresholds.containsKey("DCVHumidityHigh") && thresholds.containsKey("DCVHumidityLow") &&
                thresholds.containsKey("DCVTemperatureHigh") && thresholds.containsKey("DCVTemperatureLow") && thresholds.containsKey("DCVQualityLow") &&
                thresholds.containsKey("SCVHumidityHigh") && thresholds.containsKey("SCVHumidityLow") && thresholds.containsKey("SCVTemperatureLow") &&
                thresholds.containsKey("SCVTemperatureHigh") && thresholds.containsKey("SCVQualityLow"))){
            throw new ConfigParserException("Threshold Object does not meet the requirements.\n Please read documentation for more information.");
        } else {
            String s = (String) thresholds.get("DCVHumidityHigh");
            DCVHumidityThresholdHigh = Double.parseDouble(s);
            s = (String) thresholds.get("DCVHumidityLow");
            DCVHumidityThresholdLow = Double.parseDouble(s);
            s = (String) thresholds.get("DCVTemperatureHigh");
            DCVTemperatureThresholdHigh = Double.parseDouble(s);
            s = (String) thresholds.get("DCVTemperatureLow");
            DCVTemperatureThresholdLow = Double.parseDouble(s);
            s = (String) thresholds.get("DCVQualityLow");
            DCVAirQualityThresholdLow = Double.parseDouble(s);
            s = (String) thresholds.get("SCVHumidityHigh");
            SCVHumidityThresholdHigh = Double.parseDouble(s);
            s = (String) thresholds.get("SCVHumidityLow");
            SCVHumidityThresholdLow = Double.parseDouble(s);
            s = (String) thresholds.get("SCVTemperatureHigh");
            SCVTemperatureThresholdHigh = Double.parseDouble(s);
            s = (String) thresholds.get("SCVTemperatureLow");
            SCVTemperatureThresholdLow = Double.parseDouble(s);
            s = (String) thresholds.get("SCVHumidityLow");
            SCVAirQualityThresholdLow = Double.parseDouble(s);

            if(printStats) {
                System.out.println("DCV Humidity High Threshold is " + DCVHumidityThresholdHigh + " percent.");
                System.out.println("DCV Humidity Low Threshold is " + DCVHumidityThresholdLow + " percent.");
                System.out.println("DCV Temperature High Threshold is " + DCVTemperatureThresholdHigh + " celcius.");
                System.out.println("DCV Temperature Low Threshold is " + DCVTemperatureThresholdLow + " celcius.");
                System.out.println("DCV Air Quality Threshold is " + DCVAirQualityThresholdLow + " percent.");

                System.out.println("SCV Humidity High Threshold is " + SCVHumidityThresholdHigh + " percent.");
                System.out.println("SCV Humidity Low Threshold is " + SCVHumidityThresholdLow + " percent.");
                System.out.println("SCV Temperature High Threshold is " + SCVTemperatureThresholdHigh + " celcius.");
                System.out.println("SCV Temperature Low Threshold is " + SCVTemperatureThresholdLow + " celcius.");
                System.out.println("SCV Air Quality Threshold is " + SCVAirQualityThresholdLow + " percent.");
            }
        }

        if(ventilationSystem == null || !(ventilationSystem.containsKey("MaxAirIntake") && ventilationSystem.containsKey("MaxAirOutake") &&
                ventilationSystem.containsKey("CostPerTemperatureChange") && ventilationSystem.containsKey("CostPerHumidityChange") &&
                ventilationSystem.containsKey("CostPerQualityChange"))){
            throw new ConfigParserException("Ventilation Object does not meet the requirements. \n Please read documentation fo rmore information.");
        } else {
            String s = (String) ventilationSystem.get("MaxAirIntake");
            VentMaxAirIntake = Double.parseDouble(s);
            s = (String) ventilationSystem.get("MaxAirOutake");
            VentMaxAirOutake = Double.parseDouble(s);
            s = (String) ventilationSystem.get("CostPerTemperatureChange");
            VentCostPerTemperatureChange = Double.parseDouble(s);
            s = (String) ventilationSystem.get("CostPerHumidityChange");
            VentCostPerHumidityChange = Double.parseDouble(s);
            s = (String) ventilationSystem.get("CostPerQualityChange");
            VentCostPerQualityChange = Double.parseDouble(s);

            if(printStats) {
                System.out.println("Ventilation Max Intake is " + VentMaxAirIntake + " meters cubed per second.");
                System.out.println("Ventilation Max Outake is " + VentMaxAirOutake + " meters cubed per second.");
                System.out.println("Ventilation Cost per temperature change is " + VentCostPerTemperatureChange + " watts.");
                System.out.println("Ventilation Cost per humidity change is " + VentCostPerHumidityChange + " watts.");
                System.out.println("Ventilation Cost per quality change is " + VentCostPerQualityChange + " watts.");
            }
        }

        if(!configObject.containsKey("ElectricityCostPerKilowatt")){
            throw new ConfigParserException("Electricity cost cannot be found.\n Please check configuration for value and spelling. \n " +
                    "Looking for: 'ElectricityCostPerKilowatt'.");
        } else {
            ElectricityCostPerKilowatt = Double.parseDouble((String) configObject.get("ElectricityCostPerKilowatt"));
            if(printStats)
            System.out.println("Electricity Cost is " + ElectricityCostPerKilowatt + " euros per kilowatt.");
        }

        if(!configObject.containsKey("DataStartDateAndTime")){
            throw new ConfigParserException("Data start time cannot be found.\n Please check configuration for value and spelling. \n " +
                    "Looking for: 'DataStartDateAndTime'.");
        } else {
            String s = (String) configObject.get("DataStartDateAndTime");
            startTime = Data.getTimeFromDateTime(s);
            startDate = Data.getDateFromDateTime(s);
            if(printStats)
                System.out.println("Start Date is " + startDate + ".");
                System.out.println("Start Time is " + startTime + ".");
        }

        isParsed = true;
    }

    //************************** CONFIG GETTERS ************************/

    public double getElectricityCostPerKilowatt() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return ElectricityCostPerKilowatt;
    }

    public double getDCVTemperatureThresholdHigh() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return DCVTemperatureThresholdHigh;
    }

    public double getDCVTemperatureThresholdLow() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return DCVTemperatureThresholdLow;
    }

    public double getDCVHumidityThresholdHigh() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return DCVHumidityThresholdHigh;
    }

    public double getDCVHumidityThresholdLow() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return DCVHumidityThresholdLow;
    }

    public double getDCVAirQualityThresholdLow() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return DCVAirQualityThresholdLow;
    }

    public double getSCVTemperatureThresholdHigh() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return SCVTemperatureThresholdHigh;
    }

    public double getSCVTemperatureThresholdLow() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return SCVTemperatureThresholdLow;
    }

    public double getSCVHumidityThresholdHigh() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return SCVHumidityThresholdHigh;
    }

    public double getSCVHumidityThresholdLow() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return SCVHumidityThresholdLow;
    }

    public double getSCVAirQualityThresholdLow() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return SCVAirQualityThresholdLow;
    }

    public double getVentMaxAirIntake() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return VentMaxAirIntake;
    }

    public double getVentMaxAirOutake() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return VentMaxAirOutake;
    }

    public double getVentCostPerTemperatureChange() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return VentCostPerTemperatureChange;
    }

    public double getVentCostPerHumidityChange() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return VentCostPerHumidityChange;
    }

    public double getVentCostPerQualityChange() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return VentCostPerQualityChange;
    }

    public double getBuildingUValue() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return BuildingUValue;
    }

    public double getBuildingAirVolume() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return BuildingAirVolume;
    }

    public LocalTime getStartTime() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return startTime;
    }

    public LocalDate getStartDate() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return startDate;
    }
}
