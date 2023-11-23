package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import data_structures.ArrayList;
import data_structures.BasicHashFunction;
import data_structures.HashTableSC;
import data_structures.LinkedStack;
import interfaces.List;
import interfaces.Map;
import interfaces.Stack;

/**
 * Represents a factory for producing car parts, managing orders, and tracking inventory.
 * 
 * @author Eliel Cruz Felix
 */
public class CarPartFactory {
	
	private List<PartMachine> machines = new ArrayList<>();
    private Stack<CarPart> productionBin = new LinkedStack<>();
    private Map<Integer, CarPart> partCatalog = new HashTableSC<>(10, new BasicHashFunction());
    private Map<Integer, List<CarPart>> inventory = new HashTableSC<>(10, new BasicHashFunction());
    private Map<Integer, Integer> defectives = new HashTableSC<>(10, new BasicHashFunction());
    private List<Order> orders = new ArrayList<>();
        
    /**
     * Constructs a new CarPartFactory by setting up machines, orders, catalog, and inventory.
     *
     * @param orderPath The path to the file containing order information.
     * @param partsPath The path to the file containing machine and part information.
     * @throws IOException If there is an issue reading the files.
     */
    public CarPartFactory(String orderPath, String partsPath) throws IOException {
	    	validatePath(orderPath);
	        validatePath(partsPath);
	        setupMachines(partsPath);
	        setupOrders(orderPath);
	        setupCatalog();
	        setupInventory();    
    }
    
    /**
     * Retrieves the list of machines in the factory.
     *
     * @return The list of machines.
     */
    public List<PartMachine> getMachines() {
    	return machines;
    }
    
    /**
     * Retrieves the list of machines in the factory.
     *
     * @return The list of machines.
     */
    public void setMachines(List<PartMachine> machines) {
        this.machines = machines;
    }
    
    /**
     * Retrieves the production bin stack.
     *
     * @return The production bin stack.
     */
    public Stack<CarPart> getProductionBin() {
    	return productionBin;
    }
    
    /**
     * Sets the production bin stack.
     *
     * @param production The new production bin stack.
     */
    public void setProductionBin(Stack<CarPart> production) {
    	this.productionBin = production;
    }
    
    /**
     * Retrieves the part catalog map.
     *
     * @return The part catalog map.
     */
    public Map<Integer, CarPart> getPartCatalog() {
        return partCatalog;
    }
    
    /**
     * Sets the part catalog map.
     *
     * @param partCatalog The new part catalog map.
     */
    public void setPartCatalog(Map<Integer, CarPart> partCatalog) {
        this.partCatalog = partCatalog;
    }
    
    /**
     * Retrieves the inventory map.
     *
     * @return The inventory map.
     */
    public Map<Integer, List<CarPart>> getInventory() {
    	return inventory;
    }
    
    /**
     * Sets the inventory map.
     *
     * @param inventory The new inventory map.
     */
    public void setInventory(Map<Integer, List<CarPart>> inventory) {
        this.inventory = inventory;
    }
    
    /**
     * Retrieves the list of orders.
     *
     * @return The list of orders.
     */
    public List<Order> getOrders() {
        return orders;
    }
    
    /**
     * Sets the list of orders.
     *
     * @param orders The new list of orders.
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
    /**
     * Retrieves the map of defective parts.
     *
     * @return The map of defective parts.
     */
    public Map<Integer, Integer> getDefectives() {
        return defectives;
    }
    
    /**
     * Sets the map of defective parts.
     *
     * @param defectives The new map of defective parts.
     */
    public void setDefectives(Map<Integer, Integer> defectives) {
        this.defectives = defectives;
    }
    
    /**
     * Reads order information from a file and sets up the list of orders.
     *
     * @param path The path to the file containing order information.
     * @throws IOException If there is an issue reading the file.
     */
    public void setupOrders(String path) throws IOException {
    	validateNotNullOrEmpty(path, "Path cannot be null");
    	
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            br.readLine(); 
            String line;
            
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                
            	String[] values = line.split(",");
                int id = Integer.parseInt(values[0]);
                String customerName = values[1];
                Map<Integer, Integer> requestedParts = createRequestedPartsMap(values[2]);
                
                try {
                    this.getOrders().add(new Order(id, customerName, requestedParts, false));
                } catch (NullPointerException e) {
                    System.out.println("Unable to add order: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("File cannot be read");
            e.printStackTrace();
        }
    }
    
    /**
     * Reads machine and part information from a file and sets up the list of machines.
     *
     * @param path The path to the file containing machine and part information.
     * @throws IOException If there is an issue reading the file.
     */
    public void setupMachines(String path) throws IOException {
    	validateNotNullOrEmpty(path, "Path cannot be null");
    	
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // Skip header
            String line;
            
            while ((line = br.readLine()) != null) {
                
            	String[] values = line.split(",");
                try {
                    CarPart carPart = new CarPart(Integer.parseInt(values[0]), values[1], Double.parseDouble(values[2]), false);
                    PartMachine partMachine = new PartMachine(Integer.parseInt(values[0]), carPart, Integer.parseInt(values[4]), Double.parseDouble(values[3]), Integer.parseInt(values[5]));
                    this.getMachines().add(partMachine);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing ID: " + e.getMessage());
                } catch (NullPointerException e) {
                    System.out.println("Unable to add machine: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(path + " not found");
            e.printStackTrace();
        }
    }
    
    /**
     * Sets up the part catalog map based on the machines.
     */
    public void setupCatalog() {
    	 for (PartMachine machine : machines) {
             partCatalog.put(machine.getPart().getId(), machine.getPart());
         }
    }
    
    /**
     * Sets up the inventory map and the defectives map based on the machines.
     */
    public void setupInventory() {
    	 for (PartMachine machine : machines) {
             inventory.put(machine.getPart().getId(), new ArrayList<>());
             defectives.put(machine.getPart().getId(), 0);
         }
    }
    
    /**
     * Transfers parts from the production bin to inventory, updating defectives count.
     */
    public void storeInInventory() {
    	while (!productionBin.isEmpty()) {
            CarPart part = productionBin.pop();
            if (part.isDetective()) {
                int currentCount = defectives.get(part.getId());
                defectives.put(part.getId(), currentCount + 1);
            } else {
                ArrayList<CarPart> currentInventory = (ArrayList<CarPart>) inventory.get(part.getId());
                currentInventory.add(part);
            }
        }
    }
    
    /**
     * Runs the factory for a specified number of days and minutes.
     *
     * @param days    The number of days to run the factory.
     * @param minutes The number of minutes to run the factory each day.
     */
    public void runFactory(int days, int minutes) {
    	validatePositiveValue(days, "Days");
	    validatePositiveValue(minutes, "Minutes");

	    List<PartMachine> machines = getMachines();

	    for (int i = 0; i < days; i++) {
	        for (int j = 0; j < minutes; j++) {
	            for (PartMachine machine : machines) {
	                CarPart part = machine.produceCarPart();

	                if (part != null) {
	                    getProductionBin().push(part);
	                }
	            }
	        }

	        for (PartMachine machine : machines) {
	            while (!machine.getConveyorBelt().isEmpty()) {
	                CarPart part = machine.getConveyorBelt().dequeue();

	                if (part != null) {
	                    getProductionBin().push(part);
	                }
	            }
	        }

	        storeInInventory();
	    }

	    processOrders();
    }

    /**
     * Processes orders, updating inventory and setting orders as fulfilled if possible.
     */
    public void processOrders() {
    	for (Order order : this.getOrders()) {
            if (isOrderFulfilled(order)) {
                updateInventory(order);
                order.setFulfilled(true);
            }
        }
    }
    
    /**
     * Checks if an order can be fulfilled based on the available inventory.
     *
     * @param order The order to check for fulfillment.
     * @return True if the order can be fulfilled, false otherwise.
     */
    private boolean isOrderFulfilled(Order order) {
        List<Integer> orderKeys = order.getRequestedParts().getKeys();
        for (int key : orderKeys) {
            int requestedQuantity = order.getRequestedParts().get(key);
            int availableQuantity = this.getInventory().get(key).size();
            if (availableQuantity < requestedQuantity) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Updates the inventory based on the fulfilled order.
     *
     * @param order The fulfilled order.
     */
    private void updateInventory(Order order) {
        List<Integer> orderKeys = order.getRequestedParts().getKeys();
        for (int key : orderKeys) {
            int requestedQuantity = order.getRequestedParts().get(key);
            for (int i = 0; i < requestedQuantity; i++) {
                this.getInventory().get(key).remove(0);
            }
        }
    }
    
    /**
     * Validates that the given path is not null or empty.
     *
     * @param path      The path to validate.
     * @param paramName The name of the parameter being validated.
     */
    private void validatePath(String path) {
    	validateNotNullOrEmpty(path, "Path");
    }
    
    /**
     * Validates that the given object is not null or empty.
     *
     * @param object    The object to validate.
     * @param paramName The name of the parameter being validated.
     */
    private void validateNotNullOrEmpty(Object object, String paramName) {
    	if (object == null) {
            throw new IllegalArgumentException(paramName + " cannot be null");
        }
        if (object.getClass().isArray() && java.lang.reflect.Array.getLength(object) == 0) {
            throw new IllegalArgumentException(paramName + " cannot be empty");
        }
        if (object instanceof Iterable && !((Iterable<?>) object).iterator().hasNext()) {
            throw new IllegalArgumentException(paramName + " cannot be empty");
        }
    }
    
    /**
     * Validates that the given value is positive.
     *
     * @param value     The value to validate.
     * @param paramName The name of the parameter being validated.
     */
    private void validatePositiveValue(int value, String paramName) {
    	if (value <= 0) {
            throw new IllegalArgumentException(paramName + " must be greater than 0");
        }
    }
    
    /**
     * Creates a map of requested parts from the given string.
     *
     * @param partsString The string containing part information.
     * @return The map of requested parts.
     */
    private Map<Integer, Integer> createRequestedPartsMap(String partsString) {
    	 Map<Integer, Integer> requestedParts = new HashTableSC<>(10, new BasicHashFunction());
    	    String[] partQuantities = partsString.split("-");
    	    for (String partQuantity : partQuantities) {
    	        String[] pair = partQuantity.replaceAll("[()]", "").split(" ");
    	        int partId = Integer.parseInt(pair[0]);
    	        int quantity = Integer.parseInt(pair[1]);

    	        if (requestedParts.containsKey(partId)) {
    	            requestedParts.put(partId, requestedParts.get(partId) + quantity);
    	        } else {
    	            requestedParts.put(partId, quantity);
    	        }
    	    }
    	    return requestedParts;
    }
    
    /**
     * Generates a report indicating how many parts were produced per machine,
     * how many of those were defective and are still in inventory. Additionally, 
     * it also shows how many orders were successfully fulfilled. 
     */
    public void generateReport() {
        String report = "\t\t\tREPORT\n\n";
        report += "Parts Produced per Machine\n";
        for (PartMachine machine : this.getMachines()) {
            report += machine + "\t(" + 
            this.getDefectives().get(machine.getPart().getId()) +" defective)\t(" + 
            this.getInventory().get(machine.getPart().getId()).size() + " in inventory)\n";
        }
       
        report += "\nORDERS\n\n";
        for (Order transaction : this.getOrders()) {
            report += transaction + "\n";
        }
        System.out.println(report);
    }

   

}
