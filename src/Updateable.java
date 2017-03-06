/**
 Components of the simulation that will need updating on any changes that occur
 */
public interface Updateable{

    /**
     * Updates the component based on the currentData being passed in
     * @param currentData
     */
    void update(Data currentData);
}
