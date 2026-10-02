class Confirmation implements Runnable {
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()
                    + " Restaurant Confirmation - Started");

            Thread.sleep(500);

            System.out.println(Thread.currentThread().getName()
                    + " Restaurant Confirmation - Completed");

        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }
}


class Verification implements Runnable {
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()
                    + " Payment Verification - Started");

            Thread.sleep(300);

            System.out.println(Thread.currentThread().getName()
                    + " Payment Verification - Completed");

        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }
}


class Assignment implements Runnable {
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()
                    + " Delivery Assignment - Started");

            Thread.sleep(700);

            System.out.println(Thread.currentThread().getName()
                    + " Delivery Assignment - Completed");

        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }
}


public class Main {
    public static void main(String[] args) {

        Thread t1 = new Thread(new Confirmation());
        Thread t2 = new Thread(new Verification());
        Thread t3 = new Thread(new Assignment());

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }

        System.out.println("Order Processing Completed");
    }
}