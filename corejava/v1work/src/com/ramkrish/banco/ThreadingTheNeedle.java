package com.ramkrish.banco; /**
 * Demo Java Threads
 * Two threads transfer money between two accounts - the first between 0 and 1
 * accounts and the second between 2 and 3.
 */
import com.ramkrish.banco.BancoDeJava;
import java.util.*;

public class ThreadingTheNeedle  {
    public static final int DELAY = 10;
    public static final int TRANSFERS = 5000;
    public static final double MAX_AMOUNT = 25000;

    public static void main(String[] args)  {
        var bank = new BancoDeJava(4, 25000);

        Runnable transfer01 = () -> {
            try {
                for (int i = 0; i < TRANSFERS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(0, 1, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                // We need to catch an InterruptedException that the sleep
                // method threatens to throw. Interruption is used to request
                // that a thread terminates. Accordingly, our run method
                // exits when an InterruptedException occurs.
                // The following is the recommended method if you do not have
                // anything to state in the catch block.
                Thread.currentThread().interrupt();
            }
        };

        Runnable transfer23 = () -> {
            try {
                for (int i = 0; i < TRANSFERS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(2, 3, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                // We need to catch an InterruptedException that the sleep
                // method threatens to throw. Interruption is used to request
                // that a thread terminates. Accordingly, our run method
                // exits when an InterruptedException occurs.
                // The following is the recommended method if you do not have
                // anything to state in the catch block.
                Thread.currentThread().interrupt();
            }
        };

        Runnable transfer10 = () -> {
            try {
                for (int i = 0; i < TRANSFERS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(1, 0, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                // We need to catch an InterruptedException that the sleep
                // method threatens to throw. Interruption is used to request
                // that a thread terminates. Accordingly, our run method
                // exits when an InterruptedException occurs.
                // The following is the recommended method if you do not have
                // anything to state in the catch block.
                Thread.currentThread().interrupt();
            }
        };

        Runnable transfer32 = () -> {
            try {
                for (int i = 0; i < TRANSFERS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(3, 2, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                // We need to catch an InterruptedException that the sleep
                // method threatens to throw. Interruption is used to request
                // that a thread terminates. Accordingly, our run method
                // exits when an InterruptedException occurs.
                // The following is the recommended method if you do not have
                // anything to state in the catch block.
                Thread.currentThread().interrupt();
            }
        };

        int Tn = 0;
        for (Runnable runnable : Arrays.asList(transfer01, transfer23, transfer10, transfer32)) {
            var t = new Thread(runnable);
            t.setName(new StringBuilder().append("T-0").append(String.valueOf(Tn++)).toString());
            t.start();
        }
    }
}

/**
 * Do not call the run method of the Thread class or the Runnable object.
 * Calling the run method directly merely executes the task in the same
 * thread—no new thread is started. Instead, call the Thread.start method.
 * It creates a new thread that executes the run method.
 *
 * Do NOT DO THIS:
 * You can also define a thread by forming a subclass of the Thread class, like this:
 * class MyThread extends Thread
 * {
 *    public void run()
 *    {
 *       task code
 *    }
 * }
 * Then you construct an object of the subclass and call its start method. However,
 * this approach is no longer recommended. You should decouple the task that
 * is to be run in parallel from the mechanism of running it. If you have many
 * tasks, it is too expensive to create a separate thread for each of them.
 * Instead, you can use a thread pool.
 */
