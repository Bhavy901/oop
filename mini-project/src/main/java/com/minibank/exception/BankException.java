package com.minibank.exception;

/**
 * Practical 8 (Part B)
 * Checked base exception for all domain-specific banking failures.
 */
public class BankException extends Exception {
    public BankException(String message) {
        super(message);
    }

    public BankException(String message, Throwable cause) {
        super(message, cause);
    }
}
