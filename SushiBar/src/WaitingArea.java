import java.util.LinkedList;
import java.util.Queue;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {


    public Queue<Customer> line;


    private int waitingCap;

    /**
     * Creates a new waiting area.
     *
     * @param size The maximum number of Customers that can be waiting.
     */
    public WaitingArea(int size) {
        this.waitingCap = size;

        this.line = new LinkedList<>();
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) throws InterruptedException {
        while (this.getWaitingCap() <= this.getCustomers().size()){
            wait();
        }

        // This condition avoids the situation when a customer is added, after the shop is closed
        if (SushiBar.isOpen){
            addCustomer(customer);
            SushiBar.write(Thread.currentThread().getName() + ":\t\t Customer" + customer.getCustomerID() + " is now waiting.");
        }

        // Notifying waitresses about customers in waitingArea
        notify();


    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() throws InterruptedException {
        while (this.line.isEmpty()){
            wait();
        }

        // First in line
        Customer customer = line.poll();
        SushiBar.write(Thread.currentThread().getName() + ":\t Customer #" + customer.getCustomerID() + " is now fetched.");

        // notifying door
        notify();
        return customer;
    }


    /**
     * @param customer A customer you want to put in to the waitingArea
     */
    public void addCustomer(Customer customer){
        if (!line.contains(customer)) {
            line.add(customer);
        }
    }

    public int getWaitingCap(){
        return this.waitingCap;
    }

    public Queue<Customer> getCustomers(){
        return this.line;
    }
}