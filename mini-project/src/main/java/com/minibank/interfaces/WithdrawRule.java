package com.minibank.interfaces;

import com.minibank.model.Account;

@FunctionalInterface
public interface WithdrawRule {
    boolean allow(Account account, long amount);
}
