package com.minibank.exception;

/**
 * Practical 8 (Part B - Supplementary)
 * Thrown when an account withdrawal exceeds the maximum permissible daily limit.
 */
public class DailyLimitExceededException extends BankException {
    private final long requested;
    private final long limit;

    public DailyLimitExceededException(long requested, long limit) {
        super("Daily withdrawal limit of Rs. " + limit + " exceeded by requested amount: Rs. " + requested);
        this.requested = requested;
        this.limit = limit;
    }

    public long getRequested() {
        return requested;
    }

    public long getLimit() {
        return limit;
    }
}
