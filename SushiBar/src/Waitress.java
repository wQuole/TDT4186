/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable{

    private WaitingArea waitingArea;
    /**
     * Creates a new waitress. Make sure to save the parameter in the class
     *
     * @param waitingArea The waiting area for customers
     */
    Waitress(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        while(SushiBar.isOpen || waitingArea.line.size() > 0){
            try {
                Customer customer = waitingArea.next();
                Thread.sleep(SushiBar.waitressWait);
                SushiBar.write(Thread.currentThread().getName() + ":\t Customer #" + customer.getCustomerID() + " is now ordering.");
                customer.order();
                SushiBar.write(Thread.currentThread().getName() + ":\t Customer #" + customer.getCustomerID() + " is now eating.");
                Thread.sleep(SushiBar.customerWait);
                SushiBar.write(Thread.currentThread().getName() + ":\t Customer #" + customer.getCustomerID() + " is now leaving.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
