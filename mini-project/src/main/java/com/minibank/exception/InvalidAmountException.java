package com.minibank.exception;

/**
 * Practical 8 (Part B)
 * Thrown when an invalid transaction amount (<= 0 or malformed) is entered.
 */
public class InvalidAmountException extends BankException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
