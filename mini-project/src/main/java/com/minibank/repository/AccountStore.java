package com.minibank.repository;

import com.minibank.model.Account;
import java.util.*;

public class AccountStore {

    private final Map<String, Account> accountMap = new HashMap<>();
    private final List<String> transactionHistory = new ArrayList<>();

    public void add(Account account) {
        if (account != null) {
            accountMap.put(account.getAccountNumber(), account);
            transactionHistory.add("Account Added: " + account.getAccountNumber() + " (" + account.getOwnerName() + ")");
        }
    }

    public Account get(String accountNumber) {
        return accountMap.get(accountNumber);
    }

    public List<Account> getAll() {
        return new ArrayList<>(accountMap.values());
    }

    public void recordTransaction(String transactionDetail) {
        transactionHistory.add(transactionDetail);
    }

    public List<String> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    public ArrayList<Account> sortedByBalance() {
        ArrayList<Account> list = new ArrayList<>(accountMap.values());
        list.sort((a1, a2) -> Long.compare(a2.getBalance(), a1.getBalance()));
        return list;
    }

    public List<Account> getAccountsWithBalanceAbove(long threshold) {
        List<Account> result = new ArrayList<>();
        for (Account a : accountMap.values()) {
            if (a.getBalance() > threshold) {
                result.add(a);
            }
        }
        return result;
    }
}
