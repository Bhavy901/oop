package com.minibank.model;

public record Command(TransactionType type, String accountNumber, long amount) {
    @Override
    public String toString() {
        return "Command[type=" + type + ", accountNumber=" + accountNumber + ", amount=" + amount + "]";
    }
}
