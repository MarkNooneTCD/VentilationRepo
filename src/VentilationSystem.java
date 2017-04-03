public abstract class VentilationSystem{

    //numerical values

    Results results;
    double volumeInput;

    double vocThreshold;
    double carbonMonoxideThreshold;
    double carbonDioxideThreshold;

    double dehumidifierLitresRemovePerDay = 0;
    double dehumidifierPower = 0;

    double humidifierLitresAdderPerDay = 0;
    double humidifierPower = 0;

    double heaterPower = 0;
    double coolerPower = 0;



    //Other parts of the system all Ventilation Systems need
    Outside outside;
    Building building;

    //Constructor
    public VentilationSystem(Builder b){
        results = new Results();
        results.lowestTemperatureReached = b.building.getAir().getTemperature().celsius();
        results.highestTemperatureReached = b.building.getAir().getTemperature().celsius();
        results.lowestRelativeHumidityLevel = b.building.getAir().getRelativeHumidity();
        results.highestRelativeHumidityLevel = b.building.getAir().getRelativeHumidity();

        this.volumeInput= b.volumeInput;
        this.outside = b.outside;
        this.building = b.building;
        this.vocThreshold = b.vocThreshold;
        this.carbonMonoxideThreshold = b.carbonMonoxideThreshold;
        this.carbonDioxideThreshold = b.carbonDioxideThreshold;
        this.dehumidifierLitresRemovePerDay = b.dehumidifierLitresRemovePerDay;
        this.dehumidifierPower = b.dehumidifierPower;
        this.humidifierPower = b.humidifierPower;
        this.coolerPower = b.coolerPower;
        this.heaterPower = b.heaterPower;
        this.humidifierLitresAdderPerDay = b.humidifierLitresAdderPerDay;
    }

    public void resultsUpdate(){
        Air i = building.getAir();
        if(building.getVocPpm() > vocThreshold){
            results.timeSpentAboveVOCThreshold += 1;
        }
        if(building.getCarbonMonoxidePpm() > carbonMonoxideThreshold){
            results.timeSpentAboveCOThreshold += 1;
        }
        if(building.getCarbonDioxidePpm() > carbonDioxideThreshold){
            results.timeSpentAboveCO2Threshold += 1;
        }
        if(i.getTemperature().celsius() > results.highestTemperatureReached){
            results.highestTemperatureReached = i.getTemperature().celsius();
        }
        if(i.getTemperature().celsius() < results.lowestTemperatureReached){
            results.lowestTemperatureReached = i.getTemperature().celsius();
        }
        if(building.getVocPpm() > results.highestVOCLevel){
            results.highestVOCLevel = building.getVocPpm();
        }
        if(building.getCarbonDioxidePpm() > results.highestCO2Level){
            results.highestCO2Level = building.getCarbonDioxidePpm();
        }
        if(building.getCarbonMonoxidePpm() > results.highestCOLevel){
            results.highestCOLevel = building.getCarbonMonoxidePpm();
        }
        if(i.getRelativeHumidity() < results.lowestRelativeHumidityLevel){
            results.lowestRelativeHumidityLevel = i.getRelativeHumidity();
        }
        if(i.getRelativeHumidity() > results.highestRelativeHumidityLevel){
            results.highestRelativeHumidityLevel = i.getRelativeHumidity();
        }
    }

    public abstract void simulate();


    public static abstract class Builder{

        private double volumeInput;
        private Outside outside;
        private Building building;

        private double vocThreshold;
        private double carbonMonoxideThreshold;
        private double carbonDioxideThreshold;

        double dehumidifierLitresRemovePerDay = 0;
        double dehumidifierPower = 0;

        double humidifierLitresAdderPerDay = 0;
        double humidifierPower = 0;

        double heaterPower = 0;
        double coolerPower = 0;

        public Builder volumeInput(double volumeInput){
            this.volumeInput = volumeInput;
            return this;
        }

        public Builder outside(Outside outside){
            this.outside = outside;
            return this;
        }

        public Builder building(Building building){
            this.building = building;
            return this;
        }

        public Builder vocThreshold(double threshold){
            this.vocThreshold = threshold;
            return this;
        }

        public Builder carbonMonoxideThreshold(double threshold){
            this.carbonMonoxideThreshold = threshold;
            return this;
        }

        public Builder carbonDioxideThreshold(double threshold){
            this.carbonDioxideThreshold = threshold;
            return this;
        }

        public Builder dehumidifierLitresRemovedPerDay(double amount){
            this.dehumidifierLitresRemovePerDay = amount;
            return this;
        }

        public Builder humidifierLitresAddedPerDay(double amount){
            this.humidifierLitresAdderPerDay = amount;
            return this;
        }

        public Builder dehumidifierPower(double amount){
            this.dehumidifierPower = amount;
            return this;
        }

        public Builder humidifierPower(double amount){
            this.humidifierPower = amount;
            return this;
        }

        public Builder heaterPower(double amount){
            this.heaterPower = amount;
            return this;
        }

        public Builder coolerPower(double amount){
            this.coolerPower = amount;
            return this;
        }


        public abstract VentilationSystem build();

    }






}
