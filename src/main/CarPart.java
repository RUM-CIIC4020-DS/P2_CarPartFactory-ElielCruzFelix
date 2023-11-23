package main;

/**
 * Represents a car part with specific attributes.
 * 
 * @author Eliel Cruz Felix
 */
public class CarPart {
	
	private int id;
	private String name;
	private double weight;
	private boolean isDetective;
    
	/**
     * Constructs a new CarPart object with the given parameters.
     *
     * @param id          The unique identifier of the car part.
     * @param name        The name of the car part.
     * @param weight      The weight of the car part.
     * @param isDetective Indicates whether the car part is defective or not.
     */
    public CarPart(int id, String name, double weight, boolean isDetective) {
    	this.id = id;
        this.name = name;
        this.weight = weight;
        this.isDetective = isDetective;
    }
    
    /**
     * Retrieves the unique identifier of the car part.
     *
     * @return (int) The identifier of the car part.
     */
    public int getId() {
    	return id;	
    }
    
    /**
     * Sets the unique identifier of the car part.
     *
     * @param id The new identifier for the car part.
     */
    public void setId(int id) {
    	this.id = id;   
    }
    
    /**
     * Retrieves the name of the car part.
     *
     * @return (String) The name of the car part.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the car part.
     *
     * @param name The new name for the car part.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Retrieves the weight of the car part.
     *
     * @return (double) The weight of the car part.
     */
    public double getWeight() {
    	return weight;
    }
    
    /**
     * Sets the weight of the car part.
     *
     * @param weight The new weight for the car part.
     */
    public void setWeight(double weight) {
    	this.weight = weight;
    }

    /**
     * Checks if the car part is defective.
     *
     * @return (boolean) True if the car part is defective, false otherwise.
     */
    public boolean isDetective() {
    	return isDetective;
    }
    
    /**
     * Sets whether the car part is defective or not.
     *
     * @param isDetective The new status indicating if the car part is defective.
     */
    public void setDetective(boolean isDetective) {
        this.isDetective = isDetective;
    }
    
    /**
     * Returns the parts name as its string representation
     * @return (String) The part name
     */
    public String toString() {
        return this.getName();
    }
}