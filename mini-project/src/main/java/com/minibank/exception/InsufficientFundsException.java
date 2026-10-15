package com.minibank.exception;

/**
 * Practical 8 (Part B)
 * Thrown when an account withdrawal exceeds available balance and overdraft parameters.
 * Carries the exact shortfall amount.
 */
public class InsufficientFundsException extends BankException {
    private final long shortfall;

    public InsufficientFundsException(long shortfall) {
        super("Insufficient funds in account: short by Rs. " + shortfall);
        this.shortfall = shortfall;
    }

    public InsufficientFundsException(String message, long shortfall) {
        super(message);
        this.shortfall = shortfall;
    }

    public long getShortfall() {
        return shortfall;
    }
}
