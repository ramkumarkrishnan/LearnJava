/**
 * Demos use of default locking using synchronized keyword to
 * for a thread must acquire the intrinsic object lock while calling a method.
 * The intrinsic object lock has a single associated condition.
 * The wait method adds a thread to the wait set, and the notifyAll/notify
 * methods unblock waiting threads. In other words, calling wait or notifyAll
 * is the equivalent of
 * intrinsicCondition.await();
 * intrinsicCondition.signalAll();
 *
 * It is also legal to declare static methods as synchronized. If such a
 * method is called, it acquires the intrinsic lock of the associated class
 * object. For example, if the Bank class has a static synchronized method,
 * then the lock of the Bank.class object is locked when it is called.
 * As a result, no other thread can call this or any other synchronized
 * static method of the same class.
 *
 * The intrinsic locks and conditions have some limitations. Among them:
 * You cannot interrupt a thread that is trying to acquire a lock.
 * You cannot specify a timeout when trying to acquire a lock.
 * Having a single condition per lock can be inefficient.
 */
package com.ramkrish.banco;

import java.util.Arrays;
import static java.util.Arrays.*;

public class BancoDefaultSynco {
    private final double[] accounts;

    /**
     * Constructs the bank.
     * @param n the number of accounts
     * @param initialBalance the initial balance for each account
     */
    public BancoDefaultSynco(int n, double initialBalance)  {
        accounts = new double[n];
        fill(accounts, initialBalance);
    }

    /**
     * Transfers money from one account to another.
     * @param from the account to transfer from
     * @param to the account to transfer to
     * @param amount the amount to transfer
     */
    public synchronized void transfer(int from, int to, double amount)
            throws InterruptedException  {
        while (accounts[from] < amount)
            wait();

        System.out.print(Thread.currentThread());

        System.out.printf(" %d -> %d: %10.2f: ", from, to, amount);
        accounts[from] -= amount;
        accounts[to] += amount;
        System.out.printf(" Bal: %d:%10.2f, %d:%10.2f ",
                from, accounts[from], to, accounts[to]);
        System.out.printf(" Total(%d): %10.2f%n",
                size(), getTotalBalance());

        notifyAll();
    }

    /**
     * Gets the sum of all account balances.
     * @return the total balance
     */
    public synchronized double getTotalBalance()  {
        double sum = 0;
        for (double a : accounts)
            sum += a;
        return sum;
    }

    /**
     * Gets the number of accounts in the bank.
     * @return the number of accounts
     */
    public int size()  {
        return accounts.length;
    }
}