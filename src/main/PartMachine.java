package main;

import data_structures.ListQueue;
import interfaces.Queue;
import java.util.Random;

/**
 * Represents a machine responsible for producing car parts.
 * 
 * @author Eliel Cruz Felix
 */
public class PartMachine {
    
	 private int id;
	 private CarPart part;
	 private int period;
	 private double weightError;
	 private int chanceOfDefective;
	 private Queue<Integer> timer;
	 private Queue<CarPart> conveyorBelt;
	 private int totalPartsProduced;
	 
	 /**
	     * Constructs a new PartMachine object with the given parameters.
	     *
	     * @param id                The unique identifier of the machine.
	     * @param p1                The initial car part configuration for the machine.
	     * @param period            The production period of the machine.
	     * @param weightError       The potential weight error of the produced parts.
	     * @param chanceOfDefective The chance of a part being defective.
	     */
    public PartMachine(int id, CarPart p1, int period, double weightError, int chanceOfDefective) {
    	this.id = id;
    	this.part = p1;
        this.period = period;
        this.weightError = weightError;
        this.chanceOfDefective = chanceOfDefective;
        this.timer = initializeTimer();
        this.conveyorBelt = new ListQueue<>();
        this.initializeConveyorBelt();
        this.totalPartsProduced = 0;
    }
    
    /**
     * Retrieves the unique identifier of the machine.
     *
     * @return The identifier of the machine.
     */
    public int getId() {
       return id;
    }
    
    /**
     * Sets the unique identifier of the machine.
     *
     * @param id The new identifier for the machine.
     */
    public void setId(int id) {
       this.id = id;
    }
    
    /**
     * Retrieves the timer queue used for production timing.
     *
     * @return The timer queue.
     */
    public Queue<Integer> getTimer() {
       return timer;
    }
    
    /**
     * Sets the timer queue used for production timing.
     *
     * @param timer The new timer queue.
     */
    public void setTimer(Queue<Integer> timer) {
       this.timer = timer;
    }
    
    /**
     * Retrieves the current car part configuration of the machine.
     *
     * @return The car part configuration.
     */
    public CarPart getPart() {
       return part;
    }
    
    /**
     * Sets the current car part configuration of the machine.
     *
     * @param part1 The new car part configuration.
     */
    public void setPart(CarPart part1) {
        this.part = part1;
    }
    
    /**
     * Retrieves the conveyor belt queue used for part transportation.
     *
     * @return The conveyor belt queue.
     */
    public Queue<CarPart> getConveyorBelt() {
        return conveyorBelt;
    }
    
    /**
     * Sets the conveyor belt queue used for part transportation.
     *
     * @param conveyorBelt The new conveyor belt queue.
     */
    public void setConveyorBelt(Queue<CarPart> conveyorBelt) {
    	this.conveyorBelt = conveyorBelt;
    }
    
    /**
     * Retrieves the total number of parts produced by the machine.
     *
     * @return The total number of parts produced.
     */
    public int getTotalPartsProduced() {
        return totalPartsProduced;
    }
    
    /**
     * Sets the total number of parts produced by the machine.
     *
     * @param count The new total number of parts produced.
     */
    public void setTotalPartsProduced(int count) {
    	this.totalPartsProduced = count;
    }
    
    /**
     * Retrieves the potential weight error of the produced parts.
     *
     * @return The weight error.
     */
    public double getPartWeightError() {
        return weightError;
    }
    
    /**
     * Sets the potential weight error of the produced parts.
     *
     * @param partWeightError The new weight error.
     */
    public void setPartWeightError(double partWeightError) {
        this.weightError = partWeightError;
    }
    
    /**
     * Retrieves the chance of a part being defective.
     *
     * @return The chance of a defective part.
     */
    public int getChanceOfDefective() {
        return chanceOfDefective;
    }
    
    /**
     * Sets the chance of a part being defective.
     *
     * @param chanceOfDefective The new chance of a defective part.
     */
    public void setChanceOfDefective(int chanceOfDefective) {
        this.chanceOfDefective = chanceOfDefective;
    }
    
    /**
     * Initializes the conveyor belt queue with null values.
     */
    private void initializeConveyorBelt() {
         for (int i = 0; i < 10; i++) {
             this.conveyorBelt.enqueue(null);
         }
    }
    
    /**
     * Resets the conveyor belt queue by clearing its content.
     */
    public void resetConveyorBelt() {
       this.conveyorBelt.clear(); 
    }
    
    /**
     * Initializes the timer queue with countdown values based on the machine's period.
     *
     * @return The initialized timer queue.
     */
    private Queue<Integer> initializeTimer() {
        Queue<Integer> timer = new ListQueue<>();
        for (int i = this.period - 1; i >= 0; i--) {
            timer.enqueue(i);
        }
        return timer;
    }
    
    /**
     * Simulates the passage of time by ticking the timer queue.
     *
     * @return The value at the front of the timer queue.
     */
    public int tickTimer() {
    	if(this.getTimer().isEmpty()) {
            this.initializeTimer();
        }
    	
        int thefront = this.getTimer().front();
        this.getTimer().enqueue(this.getTimer().dequeue());
        
        return thefront;
    }
    
    /**
     * Produces a car part based on the machine's configuration and timing.
     *
     * @return The produced car part.
     */
    public CarPart produceCarPart() {
    	
        if(this.getConveyorBelt().isEmpty()){
            this.resetConveyorBelt();
        }
        
        int time = tickTimer();
        CarPart priorPart = this.getConveyorBelt().dequeue();
        
        if(time != 0){
            conveyorBelt.enqueue(null);
        }else{
            Random random = new Random();
            CarPart newPart = new CarPart(this.getPart().getId(), this.getPart().getName(), (this.part.getWeight() - weightError + 2 * weightError * random.nextDouble()), (this.getTotalPartsProduced() % this.getChanceOfDefective() == 0));
            this.setTotalPartsProduced(this.getTotalPartsProduced() + 1);
            conveyorBelt.enqueue(newPart);
        }
        
        return priorPart;

    }

    /**
     * Returns string representation of a Part Machine in the following format:
     * Machine {id} Produced: {part name} {total parts produced}
     */
    @Override
    public String toString() {
        return "Machine " + this.getId() + " Produced: " + this.getPart().getName() + " " + this.getTotalPartsProduced();
    }
    /**
     * Prints the content of the conveyor belt. 
     * The machine is shown as |Machine {id}|.
     * If the is a part it is presented as |P| and an empty space as _.
     */
    public void printConveyorBelt() {
        // String we will print
        String str = "";
        // Iterate through the conveyor belt
        for(int i = 0; i < this.getConveyorBelt().size(); i++){
            // When the current position is empty
            if (this.getConveyorBelt().front() == null) {
                str = "_" + str;
            }
            // When there is a CarPart
            else {
                str = "|P|" + str;
            }
            // Rotate the values
            this.getConveyorBelt().enqueue(this.getConveyorBelt().dequeue());
        }
        System.out.println("|Machine " + this.getId() + "|" + str);
    }
}
