package com.minibank.concurrency;

import com.minibank.exception.InvalidAmountException;
import com.minibank.model.Account;

public class AccountWorker implements Runnable {

    private final Account account;
    private final int times;
    private final long amount;

    public AccountWorker(Account account, int times, long amount) {
        this.account = account;
        this.times = times;
        this.amount = amount;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("  [WORKER START] " + threadName + " beginning " + times + " deposits of Rs. " + amount);

        for (int i = 0; i < times; i++) {
            try {
                account.deposit(amount);
            } catch (InvalidAmountException e) {
                System.err.println("Worker deposit error: " + e.getMessage());
            }
        }

        System.out.println("  [WORKER FINISH] " + threadName + " completed all operations.");
    }
}
