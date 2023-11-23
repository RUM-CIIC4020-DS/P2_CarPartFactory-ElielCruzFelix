package main;

import interfaces.Map;

/**
 * Represents an order with specific attributes.
 * 
 * @author Eliel Cruz Felix
 */
public class Order {
	
	private int id;
	private String customerName;
	private Map<Integer, Integer> requestedParts;
	private boolean fulfilled;
	    
	/**
     * Constructs a new Order object with the given parameters.
     *
     * @param id             The unique identifier of the order.
     * @param customerName   The name of the customer placing the order.
     * @param requestedParts The map of requested parts with their IDs and quantities.
     * @param fulfilled      Indicates whether the order has been fulfilled or not.
     */
    public Order(int id, String customerName, Map<Integer, Integer> requestedParts, boolean fulfilled) {
    	 this.id = id;
         this.customerName = customerName;
         this.requestedParts = requestedParts;
         this.fulfilled = fulfilled;
    }
    
    /**
     * Retrieves the unique identifier of the order.
     *
     * @return The identifier of the order.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the unique identifier of the order.
     *
     * @param id The new identifier for the order.
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Checks if the order has been fulfilled.
     *
     * @return True if the order has been fulfilled, false otherwise.
     */
    public boolean isFulfilled() {
    	return fulfilled;
    }
    
    /**
     * Sets whether the order has been fulfilled or not.
     *
     * @param fulfilled The new status indicating if the order has been fulfilled.
     */
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }
    
    /**
     * Retrieves the map of requested parts with their IDs and quantities.
     *
     * @return The map of requested parts.
     */
    public Map<Integer, Integer> getRequestedParts() {
    	return requestedParts;
    }
    
    /**
     * Sets the map of requested parts with their IDs and quantities.
     *
     * @param requestedParts The new map of requested parts.
     */
    public void setRequestedParts(Map<Integer, Integer> requestedParts) {
    	this.requestedParts = requestedParts;
    }
    
    /**
     * Retrieves the name of the customer placing the order.
     *
     * @return The name of the customer.
     */
    public String getCustomerName() {
    	return customerName;
    }
    
    /**
     * Sets the name of the customer placing the order.
     *
     * @param customerName The new name for the customer.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    /**
     * Returns the order's information in the following format: {id} {customer name} {number of parts requested} {isFulfilled}
     */
    @Override
    public String toString() {
        return String.format("%d %s %d %s", this.getId(), this.getCustomerName(), this.getRequestedParts().size(), (this.isFulfilled())? "FULFILLED": "PENDING");
    }

    
    
}
