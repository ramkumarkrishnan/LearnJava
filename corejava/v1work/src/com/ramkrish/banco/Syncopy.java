/**
 * Demonstrates synchronized access to Bank accounts using BancoDeSynco
 */
package com.ramkrish.banco;

public class Syncopy {
    public static final int NACCOUNTS = 100;
    public static final double INITIAL_BALANCE = 1000;
    public static final double MAX_AMOUNT = 1000;
    public static final int DELAY = 10;

    public static void main(String[] args)  {
        var bank = new BancoDeSynco(NACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i++)  {
            int fromAccount = i;
            Runnable r = () ->  {
                try  {
                    while (true)  {
                        int toAccount = (int) (bank.size() * Math.random());
                        double amount = MAX_AMOUNT * Math.random();

                        // each Bank object has its own ReentrantLock object.
                        // If two threads try to access the same Bank object,
                        // then the lock serves to serialize the access. However,
                        // if two threads access different Bank objects, each
                        // thread acquires a different lock and neither thread
                        // is blocked. This is as it should be, because the
                        // threads cannot interfere with one another when they
                        // manipulate different Bank instances.
                        bank.transfer(fromAccount, toAccount, amount);
                        Thread.sleep((int) (DELAY * Math.random()));
                    }
                }
                catch (InterruptedException e)
                {
                }
            };
            var t = new Thread(r);
            t.start();
        }
    }
}
