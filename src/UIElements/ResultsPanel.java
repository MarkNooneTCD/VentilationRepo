package UIElements;

import javax.swing.*;
import metrics.Results;


/**
 * Created by marcus on 04/04/2017.
 */
public class ResultsPanel extends JPanel {
    String[] headings = {"Result", "Value (DCV)", "Value (SCV)"};
    Results resultsDCV, resultsSCV;
    Object[][] data;

    public ResultsPanel(Results resultsDCV, Results resultsSCV){
        this.resultsDCV = resultsDCV;
        this.resultsSCV = resultsSCV;
        Object[][] data = getUpdatedTable();
        this.data = data;
    }

    private Object[][] getUpdatedTable(){
        Object[][] data = {
                {"Volume of air vented in", new Double(resultsDCV.getVolumeOfAirVentedIn()), new Double(resultsSCV.getVolumeOfAirVentedIn())},
                {"Lowest temperature reached", new Double(resultsDCV.getLowestTemperatureReached()), new Double(resultsSCV.getLowestTemperatureReached())},
                {"Time spent outside threshold temperature", new Double(resultsDCV.getTimeSpentOutsideThresholdTemperature()), new Double(resultsSCV.getTimeSpentOutsideThresholdTemperature())},
                {"Heat energy used", new Double(resultsDCV.getHeatEnergyUsed()), new Double(resultsSCV.getHeatEnergyUsed())},
                {"Heater run time", new Double(resultsDCV.getHeaterRunTime()), new Double(resultsSCV.getHeaterRunTime())},
                {"Cooling energy used", new Double(resultsDCV.getCoolingEnergyUsed()), new Double(resultsSCV.getCoolingEnergyUsed())},
                {"Cooling run time", new Double(resultsDCV.getCoolingRunTime()), new Double(resultsSCV.getCoolingRunTime())},
                {"Time spent above CO threshold", new Double(resultsDCV.getTimeSpentAboveCOThreshold()), new Double(resultsSCV.getTimeSpentAboveCOThreshold())},
                {"Time spent above CO2 threshold", new Double(resultsDCV.getTimeSpentAboveCO2Threshold()), new Double(resultsSCV.getTimeSpentAboveCO2Threshold())},
                {"Time spent above VOC threshold", new Double(resultsDCV.getTimeSpentAboveVOCThreshold()), new Double(resultsSCV.getTimeSpentAboveVOCThreshold())},
                {"Highest CO level", new Double(resultsDCV.getHighestCOLevel()), new Double(resultsSCV.getHighestCOLevel())},
                {"Highest CO2 level", new Double(resultsDCV.getHighestCO2Level()), new Double(resultsSCV.getHighestCO2Level())},
                {"Highest VOC level", new Double(resultsDCV.getHighestVOCLevel()), new Double(resultsSCV.getHighestVOCLevel())},
                {"CO vented", new Double(resultsDCV.getCOvented()), new Double(resultsSCV.getCOvented())},
                {"CO2 vented", new Double(resultsDCV.getCO2vented()), new Double(resultsSCV.getCO2vented())},
                {"VOC vented", new Double(resultsDCV.getVOCvented()), new Double(resultsSCV.getVOCvented())},
                {"Dehumidifier run time", new Double(resultsDCV.getDehumidifierRunTime()), new Double(resultsSCV.getDehumidifierRunTime())},
                {"Dehumidifier energy used", new Double(resultsDCV.getDehumidifierEnergyUsed()), new Double(resultsSCV.getDehumidifierEnergyUsed())},
                {"Total water removed", new Double(resultsDCV.getTotalWaterRemoved()), new Double(resultsSCV.getTotalWaterRemoved())},
                {"Lowest relative humidity level", new Double(resultsDCV.getLowestRelativeHumidityLevel()), new Double(resultsSCV.getLowestRelativeHumidityLevel())},
                {"Highest relative humidity level", new Double(resultsDCV.getHighestRelativeHumidityLevel()), new Double(resultsSCV.getHighestRelativeHumidityLevel())},
                {"Time spent outside threshold humidity", new Double(resultsDCV.getTimeSpentOutisdeThresholdHumidity()), new Double(resultsSCV.getTimeSpentOutisdeThresholdHumidity())},
                {"Humidifier energy used", new Double(resultsDCV.getHumidifierEnergyUsed()), new Double(resultsSCV.getHumidifierEnergyUsed())},
                {"Humidifier run time", new Double(resultsDCV.getHumidifierRunTime()), new Double(resultsSCV.getHumidifierRunTime())},
                {"Water added", new Double(resultsDCV.getWaterAdded()), new Double(resultsSCV.getWaterAdded())},
                {"Total energy used", new Double(resultsDCV.getTotalEnergyUsed()), new Double(resultsSCV.getTotalEnergyUsed())}
        };
        return data;
    }
}
