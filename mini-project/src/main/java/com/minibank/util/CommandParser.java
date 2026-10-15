package com.minibank.util;

import com.minibank.model.Command;
import com.minibank.model.TransactionType;

public class CommandParser {

    public static Command parse(String line) throws IllegalArgumentException {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Command line cannot be empty");
        }

        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid command format. Expected: <TYPE> <ACCOUNT_NUMBER> <AMOUNT>. Received: " + line);
        }

        TransactionType type;
        try {
            type = TransactionType.valueOf(parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown transaction type '" + parts[0] + "'. Must be DEPOSIT, WITHDRAW, or TRANSFER.");
        }

        String accountNumber = parts[1].toUpperCase();

        long amount;
        try {
            amount = Long.parseLong(parts[2]);
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be a positive whole number.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric amount: " + parts[2]);
        }

        return new Command(type, accountNumber, amount);
    }
}
