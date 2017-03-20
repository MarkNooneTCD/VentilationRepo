import metrics.Temperature;

public class Building{

    private double volume;
    private Air air;

    public Building(double volume, Temperature temperature, double relativeHumidity){
        this.air = new Air(temperature, relativeHumidity, volume);
        this.volume = volume;
    }

    public void setAir(Air newAir){
        this.air = newAir;
    }

    public Air getAir(){
        return air;
    }

    public double getVolume(){
        return volume;
    }

    public void update(Data currentData) {
        //TODO pollutant updating
    }
}
