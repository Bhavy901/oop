package com.minibank.concurrency;

import com.minibank.model.Account;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionProcessor {

    private final ExecutorService executor;
    private final ConcurrentHashMap<String, Account> sharedAccounts = new ConcurrentHashMap<>();

    public TransactionProcessor(int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    public TransactionProcessor() {
        this(4);
    }

    public void registerAccount(Account account) {
        if (account != null) {
            sharedAccounts.put(account.getAccountNumber(), account);
        }
    }

    public Account getAccount(String accountNumber) {
        return sharedAccounts.get(accountNumber);
    }

    public ConcurrentHashMap<String, Account> getSharedAccounts() {
        return sharedAccounts;
    }

    public void submit(Runnable task) {
        executor.execute(task);
    }

    public void stop() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public boolean transferSafely(String fromAccNum, String toAccNum, long amount) {
        Account from = sharedAccounts.get(fromAccNum);
        Account to = sharedAccounts.get(toAccNum);

        if (from == null || to == null) return false;

        Account firstLock = fromAccNum.compareTo(toAccNum) < 0 ? from : to;
        Account secondLock = fromAccNum.compareTo(toAccNum) < 0 ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                try {
                    from.withdraw(amount);
                    to.deposit(amount);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
    }
}
