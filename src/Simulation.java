import metrics.Temperature;

public class Simulation {


    public static final String CONFIG_FILE_NAME = "config.json";
    public static final String ENVIRONMENT_CONFIG_FILE_NAME = "data2016.csv";
    public static final String SCENARIO_CONFIG_FILE_NAME = "scenarios.json";
    public static final String MOSIER_CONFIG_FILE_NAME = "mosierTable.csv";

    private static Building building;
    private static Outside outside;
    private static DCV dcv;
    private static EnvironmentParser environmentParser;
    private static ConfigParser config;
    private static ScenarioParser scenarioParser;
    private static Mosierr mosier;

    public static void main(String args[]){

        test(); //run any testing in this function

        //static files
        config = new ConfigParser(CONFIG_FILE_NAME, false);
        scenarioParser = new ScenarioParser(SCENARIO_CONFIG_FILE_NAME);
        environmentParser = new EnvironmentParser(ENVIRONMENT_CONFIG_FILE_NAME, false, scenarioParser, config);

        //Setup the building
        Temperature internalTemperature = new Temperature(config.getBuildingTemperature(), Temperature.Unit.CELSIUS);
        double internalVolume = config.getBuildingAirVolume();
        double internalHumidityRatio = config.getBuildingHumidityRatio();
        building = new Building(internalVolume, internalTemperature, internalHumidityRatio);

        //Setup outside
        Data currentData = environmentParser.getData();
        Temperature outsideTemperature = new Temperature(currentData.getTemperatureCelcius(), Temperature.Unit.CELSIUS);
        double outsideHumidityRatio = currentData.getRelativeHumidityPercentage()/100; //given as %, we need decimal
        outside = new Outside(outsideTemperature, outsideHumidityRatio);

        //Setup Ventilation System
        dcv = VentilationSystem.createDCV()
                .demandHumidtyThreshold();

        while(environmentParser.hasData()){
            currentData = environmentParser.getData();


        }
    }

    public static void test(){

    }

}
