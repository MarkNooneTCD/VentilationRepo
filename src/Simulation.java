import metrics.Temperature;

public class Simulation {


    public static final String CONFIG_FILE_NAME = "config.json";
    public static final String ENVIRONMENT_CONFIG_FILE_NAME = "dataDec2016.csv";
    public static final String SCENARIO_CONFIG_FILE_NAME = "scenarios.json";

    private static Building building;
    private static Outside outside;
    private static VentilationSystem dcv;
    private static EnvironmentParser environmentParser;
    private static ConfigParser config;
    private static ScenarioParser scenarioParser;

    public static void main(String args[]){

        //static files
        DataList dataList = new DataList();
        config = new ConfigParser(CONFIG_FILE_NAME, false);
        environmentParser = new EnvironmentParser(ENVIRONMENT_CONFIG_FILE_NAME, dataList, false);
        scenarioParser = new ScenarioParser(SCENARIO_CONFIG_FILE_NAME, dataList);

        //Setup the building
        Temperature internalTemperature = new Temperature(config.getBuildingTemperature(), Temperature.Unit.CELSIUS);
        double internalVolume = config.getBuildingAirVolume();
        double internalHumidityRatio = config.getBuildingHumidityRatio();
        double uvalue = config.getBuildingUValue();
        building = new Building(internalVolume, internalTemperature, internalHumidityRatio, uvalue);

        //Setup outside
        Data currentData = dataList.getNext();
        Temperature outsideTemperature = currentData.getTemperature();
        double outsideHumidityRatio = currentData.getRelativeHumidity(); //given as %, we need decimal
        outside = new Outside(outsideTemperature, outsideHumidityRatio);

        //Setup Ventilation System
        dcv =  DCV.createDCV()
                .demandHumidityThreshold(config.getSCVHumidityThresholdLow(), config.getDCVHumidityThresholdHigh())
                .demandTemperatureThreshold(config.getDCVTemperatureThresholdLow(), config.getDCVTemperatureThresholdHigh())
                .building(building)
                .outside(outside)
                .volumeInput(config.getVentMaxAirIntake())
                .volumeOutput(config.getVentMaxAirOutake())
                .build();


        while(dataList.hasNext()){
            currentData = dataList.getNext();
            outside.update(currentData);
            building.update(currentData);
            dcv.simulate();

        }
        System.out.println("Start date: " + dataList.getDataByIndex(0).getDateTime().toString());
        System.out.println("End date  : " + currentData.getDateTime().toString());
        System.out.println("Heating energy used : " + dcv.heatEnergyUsed);
        System.out.println("Minutes outside temperature : " + dcv.timeSpentOutsideThresholdTemperature); //will basically never happen
        System.out.println("Minutes outside humidity : " + dcv.timeSpentOutisdeThresholdHumidity);

        //Output results here to buffer
        ResultsWriter resultsWriter = new ResultsWriter();

    }

}
