import metrics.Temperature;

public class Outside {

    private Temperature t;
    private double relativeHumidity;

    public Outside(Temperature t, double relativeHumidity) {

    }

    public Air getAir(double volumeAmountToGet){
        return new Air(t, relativeHumidity, volumeAmountToGet);
    }

    public void update(Data currentData) {
        //TODO update the current Air based on the passed in data
    }
}
