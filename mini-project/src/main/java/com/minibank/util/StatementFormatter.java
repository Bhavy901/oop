package com.minibank.util;

import com.minibank.model.Account;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Practical 4 (Part B)
 * Generates formatted multi-line account statement using StringBuilder.
 */
public class StatementFormatter {

    public static String buildStatement(Account account) {
        if (account == null) {
            return "[Error: Account is null]";
        }

        StringBuilder sb = new StringBuilder();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        sb.append("------------------------------------------------------------------\n");
        sb.append("                     MINIBANK ACCOUNT STATEMENT                   \n");
        sb.append("------------------------------------------------------------------\n");
        sb.append(String.format("Generated On   : %s%n", timestamp));
        sb.append(String.format("Account Number : %s%n", account.getAccountNumber()));
        sb.append(String.format("Account Holder : %s%n", account.getOwnerName()));
        sb.append(String.format("Account Type   : %s%n", account.getAccountType()));
        sb.append(String.format("Interest Rate  : %.2f%%%n", account.interestRate()));
        sb.append(String.format("Annual Yield   : Rs. %.2f%n", account.yearlyInterest()));
        sb.append(String.format("Current Balance: Rs. %d%n", account.getBalance()));
        sb.append(String.format("Status         : %s%n", account.isActive() ? "ACTIVE" : "INACTIVE / FROZEN"));
        sb.append("------------------------------------------------------------------\n");

        return sb.toString();
    }
}
