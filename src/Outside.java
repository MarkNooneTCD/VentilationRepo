public class Outside {

    private Air air;

    public Outside(Air outsideAir){
        this.air = outsideAir;
    }

    public Air getAir(){
        return air;
    }

    /**
     * Updates the component based on the currentData being passed in
     *
     * @param currentData the current data to update from
     */
    public void update(Data currentData) {
        //TODO update the current Air based on the passed in data
    }
}
