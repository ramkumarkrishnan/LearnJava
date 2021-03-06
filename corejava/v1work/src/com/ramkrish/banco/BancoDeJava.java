/**
 * Demo Threads using concurrent Bank transfers
 */
package com.ramkrish.banco;
import java.util.*;

public class BancoDeJava {
    private final double[] accounts;

    /**
     * Constructs the bank.
     * @param n the number of accounts
     * @param initialBalance the initial balance for each account
     */
    public BancoDeJava(int numAccounts, double initialBalance)  {
        accounts = new double[numAccounts];
        Arrays.fill(accounts, initialBalance);
    }

    /**
     * Transfers money from one account to another.
     * @param from the account to transfer from
     * @param to the account to transfer to
     * @param amount the amount to transfer
     */
    public void transfer(int from, int to, double amount)  {
        if (accounts[from] < amount)
            // without locks, this check will be incorrect because the
            // thread might be deactivated at this point.  In BancoDeSynco,
            // we show how to ensure that no other thread can modify the
            // balance between the test and the transfer action. We do so
            // by protecting both the test and the transfer action with a lock.
            return;

        // the output of the threads is interleaved, showing that they run concurrently.
        System.out.print(Thread.currentThread() + ":" + Thread.currentThread().getState());
        System.out.printf(" %d -> %d: %10.2f Balances:", from, to, amount);
        accounts[from] -= amount;
        accounts[to] += amount;
        double sum = 0;
        for (int i = 0; i < accounts.length; i++) {
            System.out.printf(" %d:%10.2f ", i, accounts[i]);
            sum += accounts[i];
        }
        System.out.printf(" Total: %10.2f%n", getTotalBalance());
    }

    /**
     * Gets total balance in account balances.
     * @return the total balance
     */
    public double getTotalBalance()  {
        double sum = 0;
        for (double a : accounts) sum += a;
        return sum;
    }

    /**
     * Gets the number of accounts in the bank.
     * @return the number of accounts
     */
    public int size() {
        return accounts.length;
    }
}