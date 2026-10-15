package com.minibank.model;

import com.minibank.exception.BankException;
import com.minibank.exception.InsufficientFundsException;
import com.minibank.exception.InvalidAmountException;
import com.minibank.interfaces.InterestBearing;
import com.minibank.interfaces.Transactable;
import com.minibank.model.annotation.Id;
import com.minibank.model.annotation.Positive;

import java.io.Serializable;
import java.util.Objects;

public abstract class Account implements Transactable, InterestBearing, Comparable<Account>, Serializable {
    private static final long serialVersionUID = 1L;

    private static long accountCounter = 0;

    @Id
    private final String accountNumber;

    private String ownerName;

    @Positive(message = "Account balance must be positive or zero")
    protected long balance;

    private boolean active;

    private static synchronized String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0L);
    }

    @Override
    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    public abstract String getAccountType();

    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be strictly positive: Rs. " + amount);
        }
        this.balance += amount;
    }

    @Override
    public synchronized boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be strictly positive: Rs. " + amount);
        }
        if (!canWithdraw(amount)) {
            long maxPermissible = getMaxWithdrawable();
            long shortfall = amount - maxPermissible;
            throw new InsufficientFundsException(shortfall);
        }
        this.balance -= amount;
        return true;
    }

    public void transfer(Account to, long amount) throws BankException {
        if (to == null) {
            throw new BankException("Recipient account cannot be null");
        }
        if (this.accountNumber.equals(to.accountNumber)) {
            throw new BankException("Cannot transfer to the same account");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Transfer amount must be strictly positive");
        }

        Account firstLock = this.accountNumber.compareTo(to.accountNumber) < 0 ? this : to;
        Account secondLock = this.accountNumber.compareTo(to.accountNumber) < 0 ? to : this;

        boolean success = false;
        synchronized (firstLock) {
            synchronized (secondLock) {
                try {
                    this.withdraw(amount);
                    to.deposit(amount);
                    success = true;
                } catch (BankException be) {
                    throw be;
                } finally {

                }
            }
        }
    }

    public long getMaxWithdrawable() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public synchronized long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public String toString() {
        return String.format("%s | %-12s | %-14s | Balance: Rs. %8d | Active: %s",
                accountNumber, ownerName, getAccountType(), balance, active);
    }
}
