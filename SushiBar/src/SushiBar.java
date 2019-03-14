import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SushiBar {

    //SushiBar settings
    private static List<Thread> waitressList = new ArrayList<>();
    private static int waitingAreaCapacity = 15;
    private static int waitressCount = 8;
    private static int duration = 4;
    public static int maxOrder = 10;
    public static int waitressWait = 50;    // Used to calculate the time the waitress spends before taking the order
    public static int customerWait = 2000; // Used to calculate the time the customer spends eating
    public static int doorWait = 100; // Used to calculate the interval at which the door tries to create a customer
    public static boolean isOpen = true;

    //Creating log file
    private static File log;
    private static String path = "./";

    //Variables related to statistics
    public static SynchronizedInteger customerCounter;
    public static SynchronizedInteger servedOrders;
    public static SynchronizedInteger takeawayOrders;
    public static SynchronizedInteger totalOrders;


    public static void main(String[] args) {
        log = new File(path + "log.txt");

        //Initializing shared variables for counting number of orders
        customerCounter = new SynchronizedInteger(0);
        totalOrders = new SynchronizedInteger(0);
        servedOrders = new SynchronizedInteger(0);
        takeawayOrders = new SynchronizedInteger(0);


        // Initialize Door-Thread and Clock
        new Clock(duration);
        WaitingArea waitingArea = new WaitingArea(waitingAreaCapacity);
        Thread door = new Thread(new Door(waitingArea), "Door");
        door.start();

        // Initialize Waitress-Threads
        for (int i = 0; i < waitressCount ; i++) {
            Thread waitress = new Thread (new Waitress(waitingArea), "Waitress"+(i+1));
            waitress.start();
            waitressList.add(waitress);
        }

        // joining the door-Thread
        try {
            door.join();
            write("\t ...joined door...");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // joining the waitress-Threads
        for (Thread waitress : waitressList) {
            try {
                write("\t ...joining... " + waitress.getName() + "...");
                waitress.join();
                write("\t ...joined... " + waitress.getName() + "...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Waitresses done
        SushiBar.write("\n\n***** NO MORE CUSTOMERS - THE SHOP IS CLOSED NOW. *****\n");


        // Printing Statistics
        System.out.println("\n");
        String outPut = "Statistics\t\t\t | amount\n"+ "--------------------------------------------\n"
                + "Total number of orders:\t\t\t\t | \t" + SushiBar.totalOrders.get() + "\n"
                + "Total number of takeaway orders:\t | \t" + SushiBar.takeawayOrders.get() + "\n"
                + "Total number of orders eaten:\t\t | \t" + SushiBar.servedOrders.get();
        write(outPut);

    }

    //Writes actions in the log file and console
    public static void write(String str) {
        try {
            FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("["+ Clock.getTime() + "]\t " + str + "\n");
            bw.close();
            System.out.println("["+ Clock.getTime() + "]\t" + str);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
