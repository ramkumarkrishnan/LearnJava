package com.ramkrish.banco;

import java.util.*;

public class BancoUnSynco  {
    private final double[] accounts;

    /**
     * Constructs the bank.
     * @param n the number of accounts
     * @param initialBalance the initial balance for each account
     */
    public BancoUnSynco(int n, double initialBalance)  {
        accounts = new double[n];
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
        System.out.print(Thread.currentThread().getName() + Thread.currentThread().getState());
        System.out.printf(" %d -> %d: %10.2f Bal:", from, to, amount);
        accounts[from] -= amount;
        accounts[to] += amount;
        for (int i = 0; i < accounts.length; i++)  {
            System.out.printf(" %d:%10.2f ", i, accounts[i]);
        }
        System.out.printf(" Total: %10.2f%n", getTotalBalance());
    }

    /**
     * Gets the sum of all account balances.
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
    public int size()
    {
        return accounts.length;
    }
}
