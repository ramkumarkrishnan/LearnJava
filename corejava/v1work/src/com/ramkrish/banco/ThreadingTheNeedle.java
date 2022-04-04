package com.ramkrish.banco; /**
 * Demo Java Threads
 * Two threads transfer money between two accounts - the first between 0 and 1
 * accounts and the second between 2 and 3.  More interesting to emulate will
 * be between 0 and 1 and 1 and 2 - with protection in transfer() for overdrafts
 *
 * Great candidate to extend to Debit-Credit benchmark
 */
import com.ramkrish.banco.BancoDeJava;
import java.util.*;

public class ThreadingTheNeedle  {
    public static final int DELAY = 10;
    public static final int TRANSFERS = 100;
    public static final double MAX_AMOUNT = 1000;

    public static void main(String[] args) {
        var bank = new BancoDeJava(4, 100000);

        Runnable transferAccountZerotoOne = () -> {
            try {
                for (int i = 0; i < TRANSFERS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(0, 1, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                ;
            }
        };

        Runnable transferAccountTwotoThree = () -> {
            try {
                for (int i = 0; i < TRANSFERS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(2, 3, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
            }
        };

        new Thread(transferAccountZerotoOne).start();
        new Thread(transferAccountTwotoThree).start();
    }
}
