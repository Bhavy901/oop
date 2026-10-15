package com.minibank.interfaces;

import com.minibank.exception.InsufficientFundsException;
import com.minibank.exception.InvalidAmountException;

/**
 * Practical 6 (Part B)
 * Core contract for transactional entities that support deposit and withdrawal.
 */
public interface Transactable {
    void deposit(long amount) throws InvalidAmountException;
    boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException;
}
