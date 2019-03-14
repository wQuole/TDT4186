import java.util.concurrent.ThreadLocalRandom;


/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */
public class Customer {
    private int id;
    private int NumberOfOrders;
    private int EatenOrders;
    private int TakeAwayOrders;

    /**
     *  Creates a new Customer.
     *  Each customer should be given a unique ID
     */
    public Customer(int customerID) {
        setCustomerID(customerID);
    }


    /**
     * Here you should implement the functionality for ordering food as described in the assignment.
     */
    public synchronized void order(){
        this.setNumberOfOrders(ThreadLocalRandom.current().nextInt(0, SushiBar.maxOrder + 1));
        this.setEatenOrders(ThreadLocalRandom.current().nextInt(0, getNumberOfOrders() + 1));
        this.setTakeAwayOrders(this.getNumberOfOrders() - this.getEatenOrders());

        // Update the SushiBar
        SushiBar.totalOrders.add(this.getNumberOfOrders());
        SushiBar.servedOrders.add(this.getEatenOrders());
        SushiBar.takeawayOrders.add(this.getTakeAwayOrders());
    }


    /**
     *
     * @return Should return the customerID
     */
    public int getCustomerID() {
        return this.id;
    }


    /**
     *
     *  Getters and Setters below
     */
    public void setCustomerID(int customerID){
        this.id = customerID;
    }

    public int getNumberOfOrders() {
        return NumberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        NumberOfOrders = numberOfOrders;
    }

    public int getEatenOrders() {
        return EatenOrders;
    }

    public void setEatenOrders(int eatenOrders) {
        EatenOrders = eatenOrders;
    }

    public int getTakeAwayOrders() {
        return TakeAwayOrders;
    }

    public void setTakeAwayOrders(int takeAwayOrders) {
        TakeAwayOrders = takeAwayOrders;
    }
}