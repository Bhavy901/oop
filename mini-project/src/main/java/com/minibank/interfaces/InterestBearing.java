package com.minibank.interfaces;

/**
 * Practical 6 (Part B)
 * Interface for interest bearing accounts with default calculation methods.
 */
public interface InterestBearing {
    double interestRate();
    long getBalance();

    /**
     * Default method computing annual interest based on interestRate() and balance.
     */
    default double yearlyInterest() {
        return (getBalance() * interestRate()) / 100.0;
    }

    /**
     * Supplementary Practical 6: Second default method calculating monthly interest.
     */
    default double monthlyInterest() {
        return yearlyInterest() / 12.0;
    }
}
