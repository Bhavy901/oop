package com.minibank.interfaces;

import com.minibank.model.Account;

/**
 * Practical 6 & 7 (Part B)
 * Functional interface specifying policy evaluation for withdrawals.
 */
@FunctionalInterface
public interface WithdrawRule {
    boolean allow(Account account, long amount);
}
