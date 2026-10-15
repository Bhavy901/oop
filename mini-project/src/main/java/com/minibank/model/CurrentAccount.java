package com.minibank.model;

public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;

    private final long overdraftLimit;

    public CurrentAccount(String ownerName, long openingBalance, long overdraftLimit) {
        super(ownerName, openingBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public CurrentAccount(String ownerName, long openingBalance) {
        this(ownerName, openingBalance, 10000L);
    }

    public long getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public synchronized boolean canWithdraw(long amount) {

        return (this.balance - amount) >= -overdraftLimit;
    }

    @Override
    public synchronized long getMaxWithdrawable() {
        return this.balance + overdraftLimit;
    }

    @Override
    public String getAccountType() {
        return "CURRENT";
    }

    @Override
    public String toString() {
        return super.toString() + " (Overdraft: Rs. " + overdraftLimit + ", Rate: 0.0%)";
    }
}
