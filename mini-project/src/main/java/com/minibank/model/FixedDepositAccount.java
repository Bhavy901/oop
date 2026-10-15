package com.minibank.model;

/**
 * Practical 5 (Part B)
 * Fixed Deposit Account offering higher yield (7.0%) but locked against premature withdrawal.
 */
public class FixedDepositAccount extends Account {
    private static final long serialVersionUID = 1L;

    private final int tenureMonths;

    public FixedDepositAccount(String ownerName, long principalAmount, int tenureMonths) {
        super(ownerName, principalAmount);
        this.tenureMonths = tenureMonths;
    }

    public FixedDepositAccount(String ownerName, long principalAmount) {
        this(ownerName, principalAmount, 12); // Default 1 year tenure
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    @Override
    public double interestRate() {
        return 7.0; // 7.0% FD rate
    }

    @Override
    public boolean canWithdraw(long amount) {
        // Locked: premature withdrawal is prohibited
        return false;
    }

    @Override
    public long getMaxWithdrawable() {
        return 0L;
    }

    @Override
    public String getAccountType() {
        return "FIXED_DEPOSIT";
    }

    @Override
    public String toString() {
        return super.toString() + " (Locked Tenure: " + tenureMonths + " months, Rate: 7.0%)";
    }
}
