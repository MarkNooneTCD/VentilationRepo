package metrics;

//A results class with only simple getters and setters
//used to store the results of a simulation
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

    public void updateResults(Results r){
        setVolumeOfAirVentedIn(r.getVolumeOfAirVentedIn());
    }

    public double getVolumeOfAirVentedIn() {
        return volumeOfAirVentedIn;
    }

    public void setVolumeOfAirVentedIn(double volumeOfAirVentedIn) {
        this.volumeOfAirVentedIn = volumeOfAirVentedIn;
    }

    public double getLowestTemperatureReached() {
        return lowestTemperatureReached;
    }

    public void setLowestTemperatureReached(double lowestTemperatureReached) {
        this.lowestTemperatureReached = lowestTemperatureReached;
    }

    public double getHighestTemperatureReached() {
        return highestTemperatureReached;
    }

    public void setHighestTemperatureReached(double highestTemperatureReached) {
        this.highestTemperatureReached = highestTemperatureReached;
    }

    public double getTimeSpentOutsideThresholdTemperature() {
        return timeSpentOutsideThresholdTemperature;
    }

    public void setTimeSpentOutsideThresholdTemperature(double timeSpentOutsideThresholdTemperature) {
        this.timeSpentOutsideThresholdTemperature = timeSpentOutsideThresholdTemperature;
    }

    public double getHeatEnergyUsed() {
        return heatEnergyUsed;
    }

    public void setHeatEnergyUsed(double heatEnergyUsed) {
        this.heatEnergyUsed = heatEnergyUsed;
    }

    public double getHeaterRunTime() {
        return heaterRunTime;
    }

    public void setHeaterRunTime(double heaterRunTime) {
        this.heaterRunTime = heaterRunTime;
    }

    public double getCoolingEnergyUsed() {
        return coolingEnergyUsed;
    }

    public void setCoolingEnergyUsed(double coolingEnergyUsed) {
        this.coolingEnergyUsed = coolingEnergyUsed;
    }

    public double getCoolingRunTime() {
        return coolingRunTime;
    }

    public void setCoolingRunTime(double coolingRunTime) {
        this.coolingRunTime = coolingRunTime;
    }

    public double getTimeSpentAboveCOThreshold() {
        return timeSpentAboveCOThreshold;
    }

    public void setTimeSpentAboveCOThreshold(double timeSpentAboveCOThreshold) {
        this.timeSpentAboveCOThreshold = timeSpentAboveCOThreshold;
    }

    public double getTimeSpentAboveVOCThreshold() {
        return timeSpentAboveVOCThreshold;
    }

    public void setTimeSpentAboveVOCThreshold(double timeSpentAboveVOCThreshold) {
        this.timeSpentAboveVOCThreshold = timeSpentAboveVOCThreshold;
    }

    public double getTimeSpentAboveCO2Threshold() {
        return timeSpentAboveCO2Threshold;
    }

    public void setTimeSpentAboveCO2Threshold(double timeSpentAboveCO2Threshold) {
        this.timeSpentAboveCO2Threshold = timeSpentAboveCO2Threshold;
    }

    public double getHighestCOLevel() {
        return highestCOLevel;
    }

    public void setHighestCOLevel(double highestCOLevel) {
        this.highestCOLevel = highestCOLevel;
    }

    public double getHighestCO2Level() {
        return highestCO2Level;
    }

    public void setHighestCO2Level(double highestCO2Level) {
        this.highestCO2Level = highestCO2Level;
    }

    public double getHighestVOCLevel() {
        return highestVOCLevel;
    }

    public void setHighestVOCLevel(double highestVOCLevel) {
        this.highestVOCLevel = highestVOCLevel;
    }

    public double getCOvented() {
        return COvented;
    }

    public void setCOvented(double COvented) {
        this.COvented = COvented;
    }

    public double getCO2vented() {
        return CO2vented;
    }

    public void setCO2vented(double CO2vented) {
        this.CO2vented = CO2vented;
    }

    public double getVOCvented() {
        return VOCvented;
    }

    public void setVOCvented(double VOCvented) {
        this.VOCvented = VOCvented;
    }

    public double getDehumidifierRunTime() {
        return dehumidifierRunTime;
    }

    public void setDehumidifierRunTime(double dehumidifierRunTime) {
        this.dehumidifierRunTime = dehumidifierRunTime;
    }

    public double getDehumidifierEnergyUsed() {
        return dehumidifierEnergyUsed;
    }

    public void setDehumidifierEnergyUsed(double dehumidifierEnergyUsed) {
        this.dehumidifierEnergyUsed = dehumidifierEnergyUsed;
    }

    public double getTotalWaterRemoved() {
        return totalWaterRemoved;
    }

    public void setTotalWaterRemoved(double totalWaterRemoved) {
        this.totalWaterRemoved = totalWaterRemoved;
    }

    public double getLowestRelativeHumidityLevel() {
        return lowestRelativeHumidityLevel;
    }

    public void setLowestRelativeHumidityLevel(double lowestRelativeHumidityLevel) {
        this.lowestRelativeHumidityLevel = lowestRelativeHumidityLevel;
    }

    public double getHighestRelativeHumidityLevel() {
        return highestRelativeHumidityLevel;
    }

    public void setHighestRelativeHumidityLevel(double highestRelativeHumidityLevel) {
        this.highestRelativeHumidityLevel = highestRelativeHumidityLevel;
    }

    public double getTimeSpentOutisdeThresholdHumidity() {
        return timeSpentOutisdeThresholdHumidity;
    }

    public void setTimeSpentOutisdeThresholdHumidity(double timeSpentOutisdeThresholdHumidity) {
        this.timeSpentOutisdeThresholdHumidity = timeSpentOutisdeThresholdHumidity;
    }

    public double getHumidifierEnergyUsed() {
        return humidifierEnergyUsed;
    }

    public void setHumidifierEnergyUsed(double humidifierEnergyUsed) {
        this.humidifierEnergyUsed = humidifierEnergyUsed;
    }

    public double getHumidifierRunTime() {
        return humidifierRunTime;
    }

    public void setHumidifierRunTime(double humidifierRunTime) {
        this.humidifierRunTime = humidifierRunTime;
    }

    public double getWaterAdded() {
        return waterAdded;
    }

    public void setWaterAdded(double waterAdded) {
        this.waterAdded = waterAdded;
    }

    public double getTotalEnergyUsed() {
        return totalEnergyUsed;
    }

    public void setTotalEnergyUsed(double totalEnergyUsed) {
        this.totalEnergyUsed = totalEnergyUsed;
    }
}
