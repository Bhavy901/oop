# MiniBank - Core Banking Capstone System

### Student Information
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Course**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT
- **Semester**: 3rd Semester, 2026-27 (ODD)

---

## Project Overview
**MiniBank** is a comprehensive, production-grade, multi-threaded console banking application engineered in Java. It integrates all 14 laboratory milestones from the Object-Oriented Programming curriculum into a unified, extensible system following clean architecture and object-oriented design patterns.

### Key Architectural Layers & Milestones
1. **Model & State Representation (Practicals 1, 2, 3, 5, 11)**:
   - `BankInfo`: Application metadata record.
   - `Customer`: Encapsulated customer state with static ID generator (`CUST101...`), nested static `Address`, and deep `clone()` support.
   - `Account` (Abstract): Common banking base implementing `Transactable`, `InterestBearing`, `Comparable<Account>`, and `Serializable`.
   - Concrete Polymorphic Account Subclasses:
     - `SavingsAccount`: Minimum balance protection, 4.0% interest.
     - `CurrentAccount`: Overdraft facility, 0.0% interest.
     - `FixedDepositAccount`: Locked deposit tenure, 7.0% interest yield.
2. **Validation & Command Interpretation (Practical 4)**:
   - `Validator`: High-performance compiled regex patterns for Mobile (`[6-9][0-9]{9}`), Email, PAN, IFSC, and positive whole amounts.
   - `CommandParser`: Parses tokenized string inputs (`DEPOSIT AC0001 500`) into immutable `Command` records.
   - `StatementFormatter`: Generates formatted multi-line account statements via `StringBuilder`.
3. **Custom Annotation & Reflection Framework (Practicals 7 & 14)**:
   - `@Id`: Marks the primary key field for generic data persistence.
   - `@Positive`: Enforces numeric field values are strictly positive.
   - `@MaxLength`: Constrains string field length.
   - `@Audit`: Method-level annotation enabling reflective audit logging without modifying service logic.
   - `AnnotationValidator`: Reflection engine inspecting field metadata dynamically at runtime.
4. **Robust Exception Hierarchy (Practical 8)**:
   - Checked `BankException` base class.
   - `InsufficientFundsException` carrying structured `shortfall` details.
   - `AccountNotFoundException`, `InvalidAmountException`, `DailyLimitExceededException`.
   - Transactional safety with `try-catch-finally` and deterministic `BankSession` resource cleanup using `try-with-resources` (`AutoCloseable`).
5. **Concurrency & Thread Safety (Practicals 9, 10, 12)**:
   - Synchronized thread-safe deposit and withdrawal operations preventing race conditions.
   - Deadlock-free funds transfer employing global lock ordering based on account numbers.
   - `TransactionProcessor`: Fixed thread pool (`ExecutorService`) handling high-throughput concurrent batch requests.
   - `TransactionBuffer`: Producer-consumer queue synchronized with `wait()` and `notify()`.
   - Thread-safe `ConcurrentHashMap` for high-concurrency access.
6. **Persistence & NIO Reporting (Practical 11)**:
   - `StatePersister`: High-fidelity object serialization (`ObjectOutputStream` / `ObjectInputStream`) saving and restoring account state across application restarts.
   - `TransactionLog`: High-performance append-only audit logging using Java NIO `Files.write`.
   - `ReportGenerator`: Directory walker using `Files.newDirectoryStream` and `BufferedReader` computing end-of-day transaction reconciliations.
7. **Generic Repository & Sorting (Practicals 12 & 13)**:
   - `Repository<T>`: Generic type-safe CRUD repository resolving primary keys reflectively via `@Id`.
   - `@SafeVarargs saveAll(T... items)`.
   - Bounded generic statistical utility `<N extends Number> double average(N[] values)`.
   - `sortedByBalance()` utilizing `Comparator` to rank accounts.
8. **Automated Unit Testing (Practical 14)**:
   - Dedicated `MiniBankTest` asserting deposit correctness, shortfall exception handling, multi-threaded race condition freedom, and persistence round-trip fidelity.

---

## Directory Structure
```
mini-project/
├── build_and_run.sh          # Automated compilation, packaging & test script
├── README.md                 # Project documentation
└── src/
    ├── main/java/com/minibank/
    │   ├── MiniBank.java             # Main application entry point & menu loop
    │   ├── model/
    │   │   ├── BankInfo.java
    │   │   ├── Customer.java
    │   │   ├── Account.java
    │   │   ├── SavingsAccount.java
    │   │   ├── CurrentAccount.java
    │   │   ├── FixedDepositAccount.java
    │   │   ├── TransactionType.java
    │   │   └── Command.java
    │   ├── model/annotation/
    │   │   ├── Id.java
    │   │   ├── Positive.java
    │   │   ├── MaxLength.java
    │   │   └── Audit.java
    │   ├── interfaces/
    │   │   ├── Transactable.java
    │   │   ├── InterestBearing.java
    │   │   ├── WithdrawRule.java
    │   │   └── Premium.java
    │   ├── exception/
    │   │   ├── BankException.java
    │   │   ├── InsufficientFundsException.java
    │   │   ├── AccountNotFoundException.java
    │   │   ├── InvalidAmountException.java
    │   │   └── DailyLimitExceededException.java
    │   ├── util/
    │   │   ├── Validator.java
    │   │   ├── CommandParser.java
    │   │   ├── StatementFormatter.java
    │   │   ├── AnnotationValidator.java
    │   │   ├── AuditLogger.java
    │   │   └── BankSession.java
    │   ├── repository/
    │   │   ├── Repository.java
    │   │   └── AccountStore.java
    │   ├── concurrency/
    │   │   ├── AccountWorker.java
    │   │   ├── TransactionProcessor.java
    │   │   └── TransactionBuffer.java
    │   ├── persistence/
    │   │   ├── StatePersister.java
    │   │   ├── TransactionLog.java
    │   │   └── ReportGenerator.java
    │   └── service/
    │       └── BankService.java
    └── test/java/com/minibank/
        └── MiniBankTest.java
```

---

## How to Build and Run

### 1. Automated Script (Build, Package JAR & Test):
```bash
cd mini-project
chmod +x build_and_run.sh
./build_and_run.sh
```

### 2. Run the Packaged Runnable JAR:
```bash
java -jar target/minibank.jar
```

### 3. Run Directly with Classpath:
```bash
javac -d bin $(find src/main/java -name "*.java") $(find src/test/java -name "*.java")
java -cp bin com.minibank.MiniBank
```

### 4. Run Automated Test Suite:
```bash
java -cp bin com.minibank.MiniBankTest
```
