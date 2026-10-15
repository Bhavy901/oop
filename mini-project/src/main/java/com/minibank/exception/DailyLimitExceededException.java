package com.minibank.exception;

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
