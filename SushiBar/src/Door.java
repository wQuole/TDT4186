
/**
 * This class implements the Door component of the sushi bar assignment
 * The Door corresponds to the Producer in the producer/consumer problem
 */

public class Door implements Runnable {


    private WaitingArea waitingArea;
    /**
     * Creates a new Door. Make sure to save the
     * @param waitingArea   The customer queue waiting for a seat
     */
    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This method will run when the door thread is created (and started)
     * The method should create customers at random intervals and try to put them in the waiting area
     */
    @Override
    public void run() {
        while (SushiBar.isOpen){
            try {
                Customer customer = new Customer(SushiBar.customerCounter.get());
                SushiBar.write(Thread.currentThread().getName() + ":\t\t Customer #" + customer.getCustomerID() + " is now created.");
                SushiBar.customerCounter.increment();
                waitingArea.enter(customer);
                Thread.currentThread().sleep(SushiBar.doorWait);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    // Add more methods as you see fit
}