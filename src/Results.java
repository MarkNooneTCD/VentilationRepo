public class Results {

    public double volumeOfAirVentedIn = 0;

    public double lowestTemperatureReached = 0;
    public double highestTemperatureReached = 0;
    public double timeSpentOutsideThresholdTemperature = 0;
    public double heatEnergyUsed = 0;
    public double heaterRunTime = 0;
    public double coolingEnergyUsed = 0;
    public double coolingRunTime = 0;

    public double timeSpentAboveCOThreshold = 0;
    public double timeSpentAboveVOCThreshold = 0;
    public double timeSpentAboveCO2Threshold = 0;
    public double highestCOLevel = 0;
    public double highestCO2Level = 0;
    public double highestVOCLevel = 0;
    public double COvented = 0;
    public double CO2vented = 0;
    public double VOCvented = 0;

    public double dehumidifierRunTime = 0;
    public double dehumidifierEnergyUsed = 0;
    public double totalWaterRemoved = 0;
    public double lowestRelativeHumidityLevel = 0;
    public double highestRelativeHumidityLevel = 0;
    public double timeSpentOutisdeThresholdHumidity = 0;
    public double humidifierEnergyUsed = 0;
    public double humidifierRunTime = 0;
    public double waterAdded = 0;

    public double totalEnergyUsed = 0;

    public String toString(){
        return String.format(
                "Temperature\n" +
                        "\tlowest : %f\n" +
                        "\thighest : %f\n" +
                        "\tTime outside Threshold : %f\n" +
                "Relative Humidity\n" +
                        "\tlowest : %f\n" +
                        "\thighest : %f\n" +
                        "\tTime Outside Threshold : %f\n"+
                "Heating\n" +
                        "\tEnergy Used : %f\n" +
                        "\tRun time : %f\n" +
                "Cooling\n" +
                        "\tEnergy Used : %f\n" +
                        "\tRun time : %f\n" +
                "Dehumidifier\n" +
                        "\tEnergy Used : %f\n" +
                        "\tRun time : %f\n" +
                        "\tWater removed : %f\n" +
                "Humidifier\n" +
                        "\tEnergy Used : %f\n" +
                        "\tRun time : %f\n" +
                        "\tWater added : %f\n" +
                "Pollutants\n" +
                    "\tCO2\n" +
                        "\t\thighest : %f\n" +
                        "\t\ttime above threshold : %f\n" +
                        "\t\tamount vented : %f\n" +
                    "\tCO\n" +
                        "\t\thighest : %f\n" +
                        "\t\ttime above threshold : %f\n" +
                        "\t\tamount vented : %f\n" +
                    "\tVOC\n" +
                        "\t\thighest : %f\n" +
                        "\t\ttime above threshold : %f\n" +
                        "\t\tamount vented : %f\n" +
                "Total Energy Used : %f\n" +
                "Volume Vented In : %f\n",
                lowestTemperatureReached, highestTemperatureReached, timeSpentOutsideThresholdTemperature,
                lowestRelativeHumidityLevel, highestRelativeHumidityLevel, timeSpentOutisdeThresholdHumidity,
                heatEnergyUsed, heaterRunTime,
                coolingEnergyUsed, coolingRunTime,
                dehumidifierEnergyUsed, dehumidifierRunTime, totalWaterRemoved,
                humidifierEnergyUsed, humidifierRunTime , waterAdded,
                highestCO2Level, timeSpentAboveCO2Threshold, CO2vented,
                highestCOLevel, timeSpentAboveCOThreshold, COvented,
                highestVOCLevel, timeSpentAboveVOCThreshold, VOCvented,
                totalEnergyUsed, volumeOfAirVentedIn
        );
    }
}
