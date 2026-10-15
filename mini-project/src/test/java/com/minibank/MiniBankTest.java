package com.minibank;

import com.minibank.exception.InsufficientFundsException;
import com.minibank.model.Account;
import com.minibank.model.CurrentAccount;
import com.minibank.model.SavingsAccount;
import com.minibank.service.BankService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MiniBankTest {

    public static void main(String[] args) throws Exception {
        System.out.println("==========================================================");
        System.out.println("               MINIBANK TEST SUITE (PRACTICAL 14)         ");
        System.out.println("==========================================================");
        System.out.println("Student: Bhavy Gol | ID: 25CS016\n");

        int passed = 0;
        int failed = 0;

        try {
            testDepositIncreasesBalance();
            passed++;
            System.out.println("  [PASS] testDepositIncreasesBalance");
        } catch (Throwable t) {
            failed++;
            System.err.println("  [FAIL] testDepositIncreasesBalance: " + t.getMessage());
        }

        try {
            testWithdrawalOverLimitThrowsException();
            passed++;
            System.out.println("  [PASS] testWithdrawalOverLimitThrowsException");
        } catch (Throwable t) {
            failed++;
            System.err.println("  [FAIL] testWithdrawalOverLimitThrowsException: " + t.getMessage());
        }

        try {
            testConcurrentDepositsKeepBalanceExact();
            passed++;
            System.out.println("  [PASS] testConcurrentDepositsKeepBalanceExact");
        } catch (Throwable t) {
            failed++;
            System.err.println("  [FAIL] testConcurrentDepositsKeepBalanceExact: " + t.getMessage());
        }

        try {
            testPersistenceIntegrity();
            passed++;
            System.out.println("  [PASS] testPersistenceIntegrity");
        } catch (Throwable t) {
            failed++;
            System.err.println("  [FAIL] testPersistenceIntegrity: " + t.getMessage());
        }

        System.out.println("\n----------------------------------------------------------");
        System.out.printf("Test Execution Completed: Total: %d, Passed: %d, Failed: %d%n", (passed + failed), passed, failed);
        System.out.println("----------------------------------------------------------");

        if (failed > 0) {
            System.exit(1);
        }
    }

    public static void testDepositIncreasesBalance() throws Exception {
        SavingsAccount acc = new SavingsAccount("Test User", 5000, 1000);
        long initial = acc.getBalance();
        acc.deposit(2500);
        if (acc.getBalance() != initial + 2500) {
            throw new AssertionError("Balance was not increased correctly. Expected: 7500, Actual: " + acc.getBalance());
        }
    }

    public static void testWithdrawalOverLimitThrowsException() throws Exception {
        SavingsAccount acc = new SavingsAccount("Overdraft Tester", 2000, 1000);
        boolean caught = false;
        long expectedShortfall = 0;

        try {

            acc.withdraw(3000);
        } catch (InsufficientFundsException ife) {
            caught = true;
            expectedShortfall = ife.getShortfall();
        }

        if (!caught) {
            throw new AssertionError("Expected InsufficientFundsException was not thrown!");
        }
        if (expectedShortfall != 2000) {
            throw new AssertionError("Expected shortfall of 2000, but got: " + expectedShortfall);
        }
    }

    public static void testConcurrentDepositsKeepBalanceExact() throws Exception {
        SavingsAccount acc = new SavingsAccount("Concurrent User", 0, 0);
        int numThreads = 10;
        int depositsPerThread = 1000;
        long expectedFinalBalance = (long) numThreads * depositsPerThread;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < depositsPerThread; j++) {
                        acc.deposit(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        if (acc.getBalance() != expectedFinalBalance) {
            throw new AssertionError("Race condition detected! Expected balance: " + expectedFinalBalance + ", but got: " + acc.getBalance());
        }
    }

    public static void testPersistenceIntegrity() throws Exception {
        Path tempDir = Files.createTempDirectory("minibank_test_");
        Path testStore = tempDir.resolve("test_accounts.dat");
        Path testLogs = tempDir.resolve("logs");

        BankService service = new BankService(testStore, testLogs);
        Account created = service.openAccount("SAVINGS", "Persisted User", 8000, 1000L);
        service.savePersistedState();

        BankService serviceReloaded = new BankService(testStore, testLogs);
        Account reloaded = serviceReloaded.getAccount(created.getAccountNumber());

        if (reloaded == null) {
            throw new AssertionError("Reloaded account was not found in persisted storage!");
        }
        if (reloaded.getBalance() != 8000) {
            throw new AssertionError("Reloaded balance mismatch! Expected 8000, got: " + reloaded.getBalance());
        }

        Files.deleteIfExists(testStore);
        Files.deleteIfExists(testLogs.resolve("transactions.log"));
        Files.deleteIfExists(testLogs);
        Files.deleteIfExists(tempDir);
    }
}
