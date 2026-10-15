package com.minibank.exception;

/**
 * Practical 8 (Part B)
 * Thrown when an account number cannot be located in the repository.
 */
public class AccountNotFoundException extends BankException {
    public AccountNotFoundException(String accountNumber) {
        super("Account not found with ID: " + accountNumber);
    }
}
