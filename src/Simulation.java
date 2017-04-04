import metrics.Temperature;

public class Simulation {


    public static final String CONFIG_FILE_NAME = "config.json";
    public static final String ENVIRONMENT_CONFIG_FILE_NAME = "data2016.csv";
    public static final String SCENARIO_CONFIG_FILE_NAME = "scenarios.json";

    private Building dcvBuilding;
    private Building scvBuilding;
    private Outside outside;
    private VentilationSystem dcv;
    private VentilationSystem scv;
    private EnvironmentParser environmentParser;
    private ConfigParser config;
    private ScenarioParser scenarioParser;

    public Simulation(){

        //static files
        DataList dataList = new DataList();
        config = new ConfigParser(CONFIG_FILE_NAME, false);
        environmentParser = new EnvironmentParser(ENVIRONMENT_CONFIG_FILE_NAME, dataList, false);
        scenarioParser = new ScenarioParser(SCENARIO_CONFIG_FILE_NAME, dataList);

        //Setup the building
        Temperature internalTemperature = new Temperature(config.getBuildingTemperature(), Temperature.Unit.CELSIUS);
        double internalVolume = config.getBuildingAirVolume();
        double internalHumidityRatio = config.getBuildingRelativeHumidity();
        double uvalue = config.getBuildingUValue();
        dcvBuilding = new Building(internalVolume, internalTemperature, internalHumidityRatio, uvalue);
        scvBuilding = new Building(internalVolume, internalTemperature, internalHumidityRatio, uvalue);

        //Setup outside
        Data currentData = dataList.getNext();
        Temperature outsideTemperature = currentData.getTemperature();
        double outsideHumidityRatio = currentData.getRelativeHumidity(); //given as %, we need decimal
        outside = new Outside(outsideTemperature, outsideHumidityRatio);

        //Setup Ventilation System
        dcv =  DCV.createDCV()
                .demandHumidityThreshold(config.getHumidityThresholdLow(), config.getHumidityThresholdHigh())
                .demandTemperatureThreshold(config.getTemperatureThresholdLow(), config.getTemperatureThresholdHigh())
                .building(dcvBuilding)
                .outside(outside)
                .volumeInput(config.getVentMaxAirIntake())
                .carbonDioxideThreshold(config.getCarbonDioxideThreshold())
                .carbonMonoxideThreshold(config.getCarbonMonoxideThreshold())
                .vocThreshold(config.getVOCThreshold())
                .dehumidifierLitresRemovedPerDay(config.getDehumidifierLitresRemovedPerDay())
                .dehumidifierPower(config.getDehumidifierPower())
                .humidifierLitresAddedPerDay(config.getHumidifierLitresAddedPerDay())
                .humidifierPower(config.getHumidifierPower())
                .coolerPower(config.getCoolerPower())
                .heaterPower(config.getHeaterPower())
                .build();

        scv = SCV.createSCV()
                .demandHumidityThreshold(config.getHumidityThresholdLow(), config.getHumidityThresholdHigh())
                .demandTemperatureThreshold(config.getTemperatureThresholdLow(), config.getTemperatureThresholdHigh())
                .building(scvBuilding)
                .outside(outside)
                .volumeInput(config.getVentMaxAirIntake())
                .carbonDioxideThreshold(config.getCarbonDioxideThreshold())
                .carbonMonoxideThreshold(config.getCarbonMonoxideThreshold())
                .vocThreshold(config.getVOCThreshold())
                .dehumidifierLitresRemovedPerDay(config.getDehumidifierLitresRemovedPerDay())
                .dehumidifierPower(config.getDehumidifierPower())
                .humidifierLitresAddedPerDay(config.getHumidifierLitresAddedPerDay())
                .humidifierPower(config.getHumidifierPower())
                .coolerPower(config.getCoolerPower())
                .heaterPower(config.getHeaterPower())
                .build();

        while(dataList.hasNext()){
            currentData = dataList.getNext();
            outside.update(currentData);
            dcvBuilding.update(currentData);
            dcv.simulate();
            scvBuilding.update(currentData);
            scv.simulate();
        }
        dcv.results.totalEnergyUsed = dcv.results.dehumidifierEnergyUsed + dcv.results.heatEnergyUsed;
        scv.results.totalEnergyUsed = scv.results.dehumidifierEnergyUsed + scv.results.heatEnergyUsed;
        System.out.println(dcv.results.toString());
        System.out.println(scv.results.toString());
        //Output results here to buffer
        ResultsWriter resultsWriter = new ResultsWriter();

    }

}
