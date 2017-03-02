public class Outside implements Component{

    private Air air;

    public Outside(Air outsideAir){
        this.air = outsideAir;
    }

    public void update(Data currentData){
        //TODO modify outside are here
    }

    public Air getAir(){
        return air;
    }

}
