import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class ConfigParser {

    private double ElectricityCostPerKilowatt;
    private double VOCThreshold;
    private double CarbonMonoxideThreshold;
    private double CarbonDioxideThreshold;
    private double TemperatureThresholdHigh;
    private double TemperatureThresholdLow;
    private double HumidityThresholdHigh;
    private double HumidityThresholdLow;
    private double VentMaxAirIntake;
    private double BuildingUValue;
    private double BuildingAirVolume;
    private double BuildingTemperature;
    private double BuildingRelativeHumidity;
    private double dehumidifierLitresRemovedPerDay;
    private double dehumidifierPower;
    private double humidifierLitresAddedPerDay;
    private double humidifierPower;
    private double heaterPower;
    private double coolerPower;
    private LocalDateTime startDateTime;

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

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ConfigParserException | ParseException ex){
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
        JSONObject dehumidifier;
        JSONObject humidifier;
        JSONObject heater;
        JSONObject cooler;
        if(objectHasKeys(configObject, "Thresholds", "VentilationSystem", "Building", "Dehumidifier",
                "Humidifier","Heater","Cooler")){
            building = (JSONObject) configObject.get("Building");
            thresholds = (JSONObject) configObject.get("Thresholds");
            ventilationSystem = (JSONObject) configObject.get("VentilationSystem");
            dehumidifier = (JSONObject) configObject.get("Dehumidifier");
            humidifier = (JSONObject) configObject.get("Humidifier");
            heater = (JSONObject) configObject.get("Heater");
            cooler = (JSONObject) configObject.get("Cooler");
        } else {
            throw new ConfigParserException("Config main objects");
        }


        /*
         * Building
         */
        if(building == null || !objectHasKeys(building, "AirVolume", "U-Value", "RelativeHumidity", "Temperature")){
            throw new ConfigParserException("Building Object");
        } else {
            String s = (String) building.get("AirVolume");
            BuildingAirVolume = Double.parseDouble(s);
            s = (String) building.get("U-Value");
            BuildingUValue = Double.parseDouble(s);
            s = (String) building.get("Temperature");
            BuildingTemperature = Double.parseDouble(s);
            s = (String) building.get("RelativeHumidity");
            BuildingRelativeHumidity = Double.parseDouble(s);
        }

        /*
         * Dehumidifier
         */
        if(dehumidifier == null || !objectHasKeys(dehumidifier, "Power", "LitresRemovedPerDay")){
            throw new ConfigParserException("Dehumidifier object");
        }else {
            dehumidifierLitresRemovedPerDay = Double.parseDouble((String) dehumidifier.get("LitresRemovedPerDay"));
            dehumidifierPower = Double.parseDouble((String) dehumidifier.get("Power"));
        }

        /*
         * Thresholds
         */
        if(thresholds == null || !objectHasKeys(thresholds, "HumidityHigh", "HumidityLow",
                "TemperatureHigh", "TemperatureLow", "CarbonMonoxideThreshold",
                "CarbonDioxideThreshold" ,"VOCThreshold")){
            throw new ConfigParserException("Threshold Object");
        } else {
            HumidityThresholdHigh=Double.parseDouble((String) thresholds.get("HumidityHigh"));
            HumidityThresholdLow=Double.parseDouble((String) thresholds.get("HumidityLow"));
            TemperatureThresholdHigh=Double.parseDouble((String) thresholds.get("TemperatureHigh"));
            TemperatureThresholdLow=Double.parseDouble((String) thresholds.get("TemperatureLow"));
            CarbonMonoxideThreshold=Double.parseDouble((String) thresholds.get("CarbonMonoxideThreshold"));
            CarbonDioxideThreshold=Double.parseDouble((String) thresholds.get("CarbonDioxideThreshold"));
            VOCThreshold=Double.parseDouble((String) thresholds.get("VOCThreshold"));
        }


        /*
         * Ventilation
         */
        if(ventilationSystem == null || !objectHasKeys(ventilationSystem, "MaxAirIntake")){
            throw new ConfigParserException("Ventilation Object");
        } else {
            String s = (String) ventilationSystem.get("MaxAirIntake");
            VentMaxAirIntake = Double.parseDouble(s);
        }

        /*
         * Humidifier
         */
        if(humidifier == null || !objectHasKeys(humidifier, "LitresAddedPerDay", "Power")){
            throw new ConfigParserException("Humidifier Object");
        } else {
            humidifierLitresAddedPerDay = Double.parseDouble((String) humidifier.get("LitresAddedPerDay"));
            humidifierPower = Double.parseDouble((String) humidifier.get("Power"));
        }

        /*
         * Heater
         */
        if(heater == null || !objectHasKeys(heater, "Power")){
            throw new ConfigParserException("Heater");
        } else {
            String s = (String) heater.get("Power");
            heaterPower = Double.parseDouble(s);
        }

        /*
         * Cooler
         */
        if(cooler == null || !objectHasKeys(cooler, "Power")){
            throw new ConfigParserException("Cooler");
        } else {
            String s = (String) cooler.get("Power");
            coolerPower = Double.parseDouble(s);
        }

        if(!configObject.containsKey("ElectricityCostPerKilowatt")){
            throw new ConfigParserException("Electricity cost cannot be found.\n Please check configuration for value and spelling. \n " +
                    "Looking for: 'ElectricityCostPerKilowatt'.");
        } else {
            ElectricityCostPerKilowatt = Double.parseDouble((String) configObject.get("ElectricityCostPerKilowatt"));
            if(printStats)
            System.out.println("Electricity Cost is " + getElectricityCostPerKilowatt() + " euros per kilowatt.");
        }

        if(!configObject.containsKey("DataStartDateAndTime")){
            throw new ConfigParserException("Data start time cannot be found.\n Please check configuration for value and spelling. \n " +
                    "Looking for: 'DataStartDateAndTime'.");
        } else {
            String s = (String) configObject.get("DataStartDateAndTime");
            startDateTime = Data.getDateTimeFromString(s);
            if(printStats)
                System.out.println("Start DateTime is " + startDateTime + ".");
        }

        isParsed = true;
    }

    public static boolean objectHasKeys(JSONObject object, String... keys){
        for(String key : keys)
            if(!object.containsKey(key))
                return false;
        return true;
    }

    public double getElectricityCostPerKilowatt() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return ElectricityCostPerKilowatt;
    }

    public double getVOCThreshold() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return VOCThreshold;
    }

    public double getCarbonMonoxideThreshold() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return CarbonMonoxideThreshold;
    }

    public double getCarbonDioxideThreshold() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return CarbonDioxideThreshold;
    }


    public double getTemperatureThresholdHigh() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return TemperatureThresholdHigh;
    }

    public double getTemperatureThresholdLow() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return TemperatureThresholdLow;
    }

    public double getHumidityThresholdHigh() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return HumidityThresholdHigh;
    }

    public double getHumidityThresholdLow() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return HumidityThresholdLow;
    }

    public double getVentMaxAirIntake() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return VentMaxAirIntake;
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

    public double getBuildingTemperature() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return BuildingTemperature;
    }

    public double getBuildingRelativeHumidity() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return BuildingRelativeHumidity;
    }

    public double getDehumidifierLitresRemovedPerDay() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return dehumidifierLitresRemovedPerDay;
    }

    public double getDehumidifierPower() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return dehumidifierPower;
    }

    public double getHumidifierLitresAddedPerDay() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return humidifierLitresAddedPerDay;
    }

    public double getHumidifierPower() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return humidifierPower;
    }

    public double getHeaterPower() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return heaterPower;
    }

    public double getCoolerPower() {
        if(!isParsed){
            throw new NullPointerException("Config Parameters not yet set");
        }
        return coolerPower;
    }

}
