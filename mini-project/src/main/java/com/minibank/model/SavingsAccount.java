package com.minibank.model;

/**
 * Practical 5 (Part B)
 * Savings Account with minimum balance requirement and 4.0% interest rate.
 */
public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;

    private final long minBalance;

    public SavingsAccount(String ownerName, long openingBalance, long minBalance) {
        super(ownerName, openingBalance);
        this.minBalance = minBalance;
    }

    public SavingsAccount(String ownerName, long openingBalance) {
        this(ownerName, openingBalance, 1000L); // Default min balance Rs. 1000
    }

    public long getMinBalance() {
        return minBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public synchronized boolean canWithdraw(long amount) {
        return (this.balance - amount) >= minBalance;
    }

    @Override
    public synchronized long getMaxWithdrawable() {
        return Math.max(0, this.balance - minBalance);
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }

    @Override
    public String toString() {
        return super.toString() + " (MinBal: Rs. " + minBalance + ", Rate: 4.0%)";
    }
}
