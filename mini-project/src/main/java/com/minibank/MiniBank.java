package com.minibank;

import com.minibank.exception.BankException;
import com.minibank.exception.InsufficientFundsException;
import com.minibank.model.Account;
import com.minibank.model.BankInfo;
import com.minibank.model.Command;
import com.minibank.model.TransactionType;
import com.minibank.service.BankService;
import com.minibank.util.BankSession;
import com.minibank.util.Validator;

import java.util.List;
import java.util.Scanner;

/**
 * MiniBank - Object-Oriented Banking Capstone Application
 *
 * Developed as part of Object-Oriented Programming (CEUE203)
 * Student: Bhavy Gol (ID: 25CS016)
 * Institution: FTE, CHARUSAT
 */
public class MiniBank {

    public enum MenuOption {
        OPEN_ACCOUNT(1, "Open New Account"),
        DEPOSIT(2, "Deposit Funds"),
        WITHDRAW(3, "Withdraw Funds"),
        TRANSFER(4, "Transfer Funds"),
        VIEW_STATEMENT(5, "Account Statement"),
        LIST_ACCOUNTS(6, "List All Accounts (Sorted by Balance)"),
        RUN_BATCH_TRANSACTIONS(7, "Run Concurrent Batch Transactions"),
        GENERATE_AUDIT_REPORT(8, "Generate Daily Audit Report (NIO)"),
        BANK_WORKING_HOURS(9, "Bank Working Hours & Contact"),
        EXIT(10, "Exit & Persist State");

        private final int code;
        private final String description;

        MenuOption(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static MenuOption fromCode(int code) {
            for (MenuOption opt : values()) {
                if (opt.code == code) {
                    return opt;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        // Practical 1: Display BankInfo header
        BankInfo bankInfo = new BankInfo("MiniBank India", "CHARUSAT University Campus");
        System.out.println(bankInfo);

        System.out.println("Developed by: Bhavy Gol | Student ID: 25CS016 | Subject: CEUE203 (OOP)");
        System.out.println("--------------------------------------------------------\n");

        // Practical 8: Try-with-resources with AutoCloseable session
        try (BankSession session = new BankSession("BranchManager-BhavyGol")) {
            BankService service = new BankService();
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            // Seed sample accounts if store is empty for instant demo readiness
            if (service.listAccounts().isEmpty()) {
                try {
                    service.openAccount("SAVINGS", "Riya Patel", 15000, 1000L);
                    service.openAccount("CURRENT", "Aarav Shah", 25000, 5000L);
                    service.openAccount("FIXED_DEPOSIT", "Kavya Dave", 50000, 24L);
                } catch (Exception ignored) {}
            }

            while (running) {
                printMenu();
                System.out.print("Enter your choice (1-10): ");

                if (!scanner.hasNext()) {
                    System.out.println("\nNo further input detected. Exiting gracefully.");
                    break;
                }

                String input = scanner.next().trim();
                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number between 1 and 10.\n");
                    continue;
                }

                MenuOption option = MenuOption.fromCode(choice);
                if (option == null) {
                    System.out.println("Invalid choice [" + choice + "]. Please select from the menu options.\n");
                    continue;
                }

                // Switch expression routing menu selections
                switch (option) {
                    case OPEN_ACCOUNT -> handleOpenAccount(service, scanner);
                    case DEPOSIT -> handleDeposit(service, scanner);
                    case WITHDRAW -> handleWithdraw(service, scanner);
                    case TRANSFER -> handleTransfer(service, scanner);
                    case VIEW_STATEMENT -> handleStatement(service, scanner);
                    case LIST_ACCOUNTS -> handleListAccounts(service);
                    case RUN_BATCH_TRANSACTIONS -> handleBatch(service);
                    case GENERATE_AUDIT_REPORT -> handleReport(service);
                    case BANK_WORKING_HOURS -> printWorkingHours();
                    case EXIT -> {
                        running = false;
                        System.out.println("\nSaving application state and closing secure connection...");
                        service.shutdown();
                        System.out.println("Thank you for using MiniBank! Goodbye.");
                    }
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n==================== MAIN MENU ====================");
        for (MenuOption opt : MenuOption.values()) {
            System.out.printf("  [%2d] %-40s%n", opt.getCode(), opt.getDescription());
        }
        System.out.println("===================================================");
    }

    private static void handleOpenAccount(BankService service, Scanner scanner) {
        try {
            System.out.println("\n--- Open New Account ---");
            System.out.print("Select Account Type (1: SAVINGS, 2: CURRENT, 3: FIXED_DEPOSIT): ");
            int typeChoice = scanner.nextInt();
            String type = switch (typeChoice) {
                case 1 -> "SAVINGS";
                case 2 -> "CURRENT";
                case 3 -> "FIXED_DEPOSIT";
                default -> "SAVINGS";
            };

            scanner.nextLine(); // Consume newline
            System.out.print("Enter Account Holder Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Initial Deposit (Rs): ");
            long deposit = scanner.nextLong();

            Long param = null;
            if (type.equals("SAVINGS")) {
                System.out.print("Enter Minimum Balance requirement [default 1000]: ");
                param = scanner.nextLong();
            } else if (type.equals("CURRENT")) {
                System.out.print("Enter Overdraft Limit [default 10000]: ");
                param = scanner.nextLong();
            } else if (type.equals("FIXED_DEPOSIT")) {
                System.out.print("Enter Lock-in Tenure in Months [default 12]: ");
                param = scanner.nextLong();
            }

            Account account = service.openAccount(type, name, deposit, param);
            System.out.println("SUCCESS! Account created: " + account.getAccountNumber());
            System.out.println("Details: " + account);

        } catch (BankException be) {
            System.out.println("Account opening failed: " + be.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    private static void handleDeposit(BankService service, Scanner scanner) {
        try {
            System.out.print("Enter Account Number (e.g. AC0001): ");
            String acc = scanner.next().trim();
            System.out.print("Enter Deposit Amount (Rs): ");
            long amt = scanner.nextLong();

            service.deposit(acc, amt);
            Account updated = service.getAccount(acc);
            System.out.println("SUCCESS! Deposit of Rs. " + amt + " processed. New Balance: Rs. " + updated.getBalance());
        } catch (BankException be) {
            System.out.println("Deposit failed: " + be.getMessage());
        }
    }

    private static void handleWithdraw(BankService service, Scanner scanner) {
        try {
            System.out.print("Enter Account Number (e.g. AC0001): ");
            String acc = scanner.next().trim();
            System.out.print("Enter Withdrawal Amount (Rs): ");
            long amt = scanner.nextLong();

            service.withdraw(acc, amt);
            Account updated = service.getAccount(acc);
            System.out.println("SUCCESS! Withdrawal of Rs. " + amt + " processed. New Balance: Rs. " + updated.getBalance());
        } catch (InsufficientFundsException ife) {
            System.out.println("Withdrawal failed: " + ife.getMessage() + " (Shortfall: Rs. " + ife.getShortfall() + ")");
        } catch (BankException be) {
            System.out.println("Withdrawal failed: " + be.getMessage());
        }
    }

    private static void handleTransfer(BankService service, Scanner scanner) {
        try {
            System.out.print("Enter Sender Account Number (From): ");
            String from = scanner.next().trim();
            System.out.print("Enter Recipient Account Number (To): ");
            String to = scanner.next().trim();
            System.out.print("Enter Transfer Amount (Rs): ");
            long amt = scanner.nextLong();

            service.transfer(from, to, amt);
            System.out.println("SUCCESS! Transferred Rs. " + amt + " from " + from + " to " + to);
            System.out.println("Sender Balance: Rs. " + service.getAccount(from).getBalance());
            System.out.println("Recipient Balance: Rs. " + service.getAccount(to).getBalance());
        } catch (BankException be) {
            System.out.println("Transfer failed: " + be.getMessage());
        }
    }

    private static void handleStatement(BankService service, Scanner scanner) {
        try {
            System.out.print("Enter Account Number: ");
            String acc = scanner.next().trim();
            String stmt = service.getStatement(acc);
            System.out.println("\n" + stmt);
        } catch (BankException be) {
            System.out.println("Could not generate statement: " + be.getMessage());
        }
    }

    private static void handleListAccounts(BankService service) {
        List<Account> accounts = service.getAccountsSortedByBalance();
        System.out.println("\n=== Active Bank Accounts (Sorted by Balance Descending) ===");
        if (accounts.isEmpty()) {
            System.out.println("No accounts currently registered.");
            return;
        }
        for (Account a : accounts) {
            System.out.println("  " + a);
        }
    }

    private static void handleBatch(BankService service) {
        System.out.println("\n--- Executing Concurrent Batch Transactions ---");
        List<Account> accs = service.listAccounts();
        if (accs.size() < 2) {
            System.out.println("Need at least 2 accounts for batch test.");
            return;
        }

        String a1 = accs.get(0).getAccountNumber();
        String a2 = accs.get(1).getAccountNumber();

        List<Command> batch = List.of(
            new Command(TransactionType.DEPOSIT, a1, 500),
            new Command(TransactionType.DEPOSIT, a2, 1000),
            new Command(TransactionType.WITHDRAW, a1, 200),
            new Command(TransactionType.DEPOSIT, a1, 300)
        );

        service.processBatch(batch);
        System.out.println("Submitted " + batch.size() + " concurrent transactions to thread pool successfully!");
    }

    private static void handleReport(BankService service) {
        try {
            System.out.println("\nGenerating Java NIO transaction audit report...");
            String report = service.generateAuditReport();
            System.out.println(report);
        } catch (Exception e) {
            System.err.println("Report generation error: " + e.getMessage());
        }
    }

    private static void printWorkingHours() {
        System.out.println("\n---------------------------------------------------");
        System.out.println("           MINIBANK OPERATIONAL SCHEDULE           ");
        System.out.println("---------------------------------------------------");
        System.out.println("  Working Days : Monday to Friday");
        System.out.println("  Banking Hours: 09:30 AM to 04:30 PM IST");
        System.out.println("  Digital/ATM  : 24x7 Available");
        System.out.println("  Customer Care: 1800-OOP-JAVA (Toll Free)");
        System.out.println("  Support Email: support@minibank.charusat.edu.in");
        System.out.println("---------------------------------------------------");
    }
}
