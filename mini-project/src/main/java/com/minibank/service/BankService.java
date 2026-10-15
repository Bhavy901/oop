package com.minibank.service;

import com.minibank.concurrency.TransactionProcessor;
import com.minibank.exception.AccountNotFoundException;
import com.minibank.exception.BankException;
import com.minibank.exception.InsufficientFundsException;
import com.minibank.exception.InvalidAmountException;
import com.minibank.model.*;
import com.minibank.model.annotation.Audit;
import com.minibank.persistence.ReportGenerator;
import com.minibank.persistence.StatePersister;
import com.minibank.persistence.TransactionLog;
import com.minibank.repository.Repository;
import com.minibank.util.AnnotationValidator;
import com.minibank.util.AuditLogger;
import com.minibank.util.StatementFormatter;
import com.minibank.util.Validator;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BankService {

    private final Repository<Account> accountRepository;
    private final TransactionProcessor transactionProcessor;
    private final Path storageFile;
    private final Path logFile;
    private final Path logsDir;

    public BankService(Path storageFile, Path logsDir) {
        this.accountRepository = new Repository<>();
        this.transactionProcessor = new TransactionProcessor(4);
        this.storageFile = storageFile;
        this.logsDir = logsDir;
        this.logFile = logsDir.resolve("transactions.log");
        loadPersistedState();
    }

    public BankService() {
        this(Paths.get("data", "accounts.dat"), Paths.get("data", "logs"));
    }

    public void loadPersistedState() {
        try {
            List<Account> loaded = StatePersister.loadList(storageFile);
            for (Account acc : loaded) {
                accountRepository.save(acc);
                transactionProcessor.registerAccount(acc);
            }
            if (!loaded.isEmpty()) {
                System.out.println("[PERSISTENCE] Successfully restored " + loaded.size() + " accounts from storage.");
            }
        } catch (Exception e) {
            System.out.println("[PERSISTENCE] Starting with fresh account store (No existing file or read error).");
        }
    }

    public void savePersistedState() {
        try {
            List<Account> accounts = accountRepository.findAll();
            StatePersister.save(accounts, storageFile);
            System.out.println("[PERSISTENCE] Successfully saved " + accounts.size() + " accounts to " + storageFile);
        } catch (IOException e) {
            System.err.println("[PERSISTENCE ERROR] Failed to save accounts: " + e.getMessage());
        }
    }

    @Audit(action = "OPEN_ACCOUNT")
    public Account openAccount(String type, String ownerName, long initialDeposit, Long customParameter) throws BankException {
        AuditLogger.logServiceCall(getClass(), "openAccount", new Object[]{type, ownerName, initialDeposit});

        if (ownerName == null || ownerName.trim().isEmpty()) {
            throw new BankException("Account holder name cannot be empty");
        }
        if (initialDeposit < 0) {
            throw new InvalidAmountException("Initial deposit cannot be negative: Rs. " + initialDeposit);
        }

        Account account = switch (type.toUpperCase()) {
            case "SAVINGS" -> {
                long minBal = (customParameter != null) ? customParameter : 1000L;
                yield new SavingsAccount(ownerName.trim(), initialDeposit, minBal);
            }
            case "CURRENT" -> {
                long overdraft = (customParameter != null) ? customParameter : 10000L;
                yield new CurrentAccount(ownerName.trim(), initialDeposit, overdraft);
            }
            case "FIXED_DEPOSIT", "FD" -> {
                int tenure = (customParameter != null) ? customParameter.intValue() : 12;
                yield new FixedDepositAccount(ownerName.trim(), initialDeposit, tenure);
            }
            default -> throw new BankException("Unsupported account type: " + type + ". Choose SAVINGS, CURRENT, or FIXED_DEPOSIT.");
        };

        String[] validationErrors = AnnotationValidator.validate(account);
        if (validationErrors.length > 0) {
            throw new BankException("Validation failed: " + String.join(", ", validationErrors));
        }

        accountRepository.save(account);
        transactionProcessor.registerAccount(account);

        logAction("OPEN_ACCOUNT " + account.getAccountNumber() + " " + initialDeposit + " [" + account.getAccountType() + "]");
        return account;
    }

    @Audit(action = "DEPOSIT")
    public void deposit(String accountNumber, long amount) throws BankException {
        AuditLogger.logServiceCall(getClass(), "deposit", new Object[]{accountNumber, amount});

        Account account = getAccountOrThrow(accountNumber);
        account.deposit(amount);
        logAction("DEPOSIT " + accountNumber + " " + amount);
    }

    @Audit(action = "WITHDRAW")
    public void withdraw(String accountNumber, long amount) throws BankException {
        AuditLogger.logServiceCall(getClass(), "withdraw", new Object[]{accountNumber, amount});

        Account account = getAccountOrThrow(accountNumber);
        account.withdraw(amount);
        logAction("WITHDRAW " + accountNumber + " " + amount);
    }

    @Audit(action = "TRANSFER")
    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws BankException {
        AuditLogger.logServiceCall(getClass(), "transfer", new Object[]{fromAccountNum, toAccountNum, amount});

        Account from = getAccountOrThrow(fromAccountNum);
        Account to = getAccountOrThrow(toAccountNum);

        from.transfer(to, amount);
        logAction("TRANSFER " + fromAccountNum + "->" + toAccountNum + " " + amount);
    }

    @Audit(action = "STATEMENT")
    public String getStatement(String accountNumber) throws BankException {
        AuditLogger.logServiceCall(getClass(), "getStatement", new Object[]{accountNumber});
        Account account = getAccountOrThrow(accountNumber);
        return StatementFormatter.buildStatement(account);
    }

    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAccountsSortedByBalance() {
        ArrayList<Account> list = accountRepository.findAll();
        list.sort((a1, a2) -> Long.compare(a2.getBalance(), a1.getBalance()));
        return list;
    }

    public Account getAccount(String accountNumber) {
        return accountRepository.findById(accountNumber);
    }

    public Account getAccountOrThrow(String accountNumber) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }

    public String generateAuditReport() throws IOException {
        Path reportOut = logsDir.resolve("daily_reconciliation_report.txt");
        return ReportGenerator.generateDailyReport(logsDir, reportOut);
    }

    public void processBatch(List<Command> commands) {
        for (Command cmd : commands) {
            transactionProcessor.submit(() -> {
                try {
                    if (cmd.type() == TransactionType.DEPOSIT) {
                        deposit(cmd.accountNumber(), cmd.amount());
                    } else if (cmd.type() == TransactionType.WITHDRAW) {
                        withdraw(cmd.accountNumber(), cmd.amount());
                    }
                } catch (Exception e) {
                    System.err.println("Batch error on " + cmd + ": " + e.getMessage());
                }
            });
        }
    }

    public void shutdown() {
        savePersistedState();
        transactionProcessor.stop();
    }

    private void logAction(String logLine) {
        try {
            TransactionLog.append(logFile, logLine);
        } catch (IOException e) {
            System.err.println("Failed to write to transaction log: " + e.getMessage());
        }
    }
}
