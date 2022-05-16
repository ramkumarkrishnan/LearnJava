/**
 * Demo concurrency with locking.
 * The real problem with BancoDeJava is that the work of the transfer method
 * can be interrupted in the middle - and another thread to do a transfer that
 * renders the account overdrafted or corrupted. If we could ensure that the
 * method runs to completion before the thread loses control, the state of the
 * bank account object would never be corrupted.
 * The Java language provides a synchronized keyword for this purpose, and
 * Java 5 introduced the ReentrantLock class. The synchronized keyword
 * automatically provides a lock as well as an associated “condition,” which
 * makes it powerful and convenient for most cases that require explicit
 * locking. We will see standlone use of ReEntrantLock first, and the use
 * of synchronized in BancoDeDosSynco.
 *
 */
package com.ramkrish.banco;

import java.util.*;
import java.util.concurrent.locks.*;

public class BancoDeSynco {
    private final double[] accounts;
    private Lock bankLock;
    private Condition sufficientFunds;

    /**
     * Constructs the bank with lock on the accounts for synchronized access
     * and conditions for a thread to release the lock.
     * @param n the number of accounts
     * @param initialBalance the initial balance for each account
     */
    public BancoDeSynco(int numAccounts, double initialBalance)  {
        accounts = new double[numAccounts];
        Arrays.fill(accounts, initialBalance);

        // lock to protect critical area
        bankLock = new ReentrantLock();

        // Condition object to signal/track funds availability to relinquish lock
        sufficientFunds = bankLock.newCondition();
    }

    /**
     * Transfers money from one account to another.
     * @param from the account to transfer from
     * @param to the account to transfer to
     * @param amount the amount to transfer
     */
    public void transfer(int from, int to, double amount) throws InterruptedException  {
        // This lock-try-finally construct is called the critical section.
        // It guarantees that only one thread at a
        // time can enter the critical section. As soon as one thread locks the
        // lock object, no other thread can get past the lock statement. When
        // other threads call lock, they are deactivated until the first thread
        // unlocks the lock object.
        //
        // The lock is called reentrant because a thread can repeatedly acquire
        // a lock that it already owns. The lock has a hold count that keeps
        // track of the nested calls to the lock method. The thread has to call
        // unlock for every call to lock in order to relinquish the lock. Because
        // of this feature, code protected by a lock can call another method
        // that uses the same locks.
        //
        // For example, the transfer method calls the getTotalBalance method,
        // which also locks the bankLock object, which now has a hold count
        // of 2. When the getTotalBalance method exits, the hold count is back
        // to 1. When the transfer method exits, the hold count is 0, and the
        // thread relinquishes the lock.
        bankLock.lock();

        // When you use locks, you cannot use the try-with-resources statement.
        // First off, the unlock method isn’t called close. But even if it was
        // renamed, the try-with-resources statement wouldn’t work. Its header
        // expects the declaration of a new variable. But when you use a lock,
        // you want to keep using the same variable that is shared among threads.
        //
        // the code in a critical section MUST NOT be bypassed by throwing an
        // exception. If an exception is thrown before the end of the section,
        // the finally clause will relinquish the lock, but the object may be
        // in a damaged state.
        try  {
            while (accounts[from] < amount)
                // Deactivate current thread and give up the lock; allowing
                // another thread that can, we hope, increase the balance.
                //
                // The essential difference between a thread that is waiting
                // to acquire a lock and a thread that has called await: Once
                // a thread calls the await method, it enters a wait set for
                // that condition. The thread is not made runnable when the
                // lock is available. Instead, it stays deactivated until
                // another thread has called the signalAll method on the same condition.
                sufficientFunds.await();

            System.out.print(Thread.currentThread().getName() + ":" + Thread.currentThread().getState());
            System.out.printf(" %d -> %d: %10.2f Bal:", from, to, amount);
            accounts[from] -= amount;
            accounts[to] += amount;
            for (int i = 0; i < accounts.length; i++) {
                System.out.printf(" %d:%10.2f ", i, accounts[i]);
            }
            System.out.printf(" Total: %10.2f%n", getTotalBalance());

            // This call reactivates all threads waiting for the condition. When
            // the threads are removed from the wait set, they are again runnable
            // and the scheduler will eventually activate them again. At that
            // time, they will attempt to reenter the object. As soon as the
            // lock is available, one of them will acquire the lock and continue
            // where it left off, returning from the call to await. At that time,
            // the thread should test the condition again. There is no guarantee
            // that the condition is now fulfilled—the signalAll method merely
            // signals to the waiting threads that it may be fulfilled at this
            // time and that it is worth checking for the condition again.
            sufficientFunds.signalAll();
        }
        finally  {
            // It is critically important that the unlock operation is enclosed
            // in a finally clause. If the code in the critical section throws
            // an exception, the lock must be unlocked. Otherwise, the other
            // threads will be blocked forever.
            bankLock.unlock();
        }
    }

    /**
     * Gets total balance in account balances.
     * @return the total balance
     */
    public double getTotalBalance()  {
        bankLock.lock();
        try  {
            double sum = 0;
            for (double a : accounts) sum += a;
            return sum;
        }
        finally {
            bankLock.unlock();
        }
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
