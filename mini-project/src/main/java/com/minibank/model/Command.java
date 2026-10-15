package com.minibank.model;

/**
 * Practical 4 (Part B)
 * Record representing a parsed banking command.
 */
public record Command(TransactionType type, String accountNumber, long amount) {
    @Override
    public String toString() {
        return "Command[type=" + type + ", accountNumber=" + accountNumber + ", amount=" + amount + "]";
    }
}
