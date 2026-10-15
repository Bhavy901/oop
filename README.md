# Object-Oriented Programming (CEUE203)

### Student Profile & Credits
- **Student Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Degree**: B.Tech Computer Engineering / Information Technology
- **Department**: Faculty of Technology and Engineering (FTE)
- **University**: Charotar University of Science and Technology (CHARUSAT)
- **Academic Term**: 3rd Semester, 2026-27 (ODD)
- **GitHub Repository**: [https://github.com/Bhavy901/oop](https://github.com/Bhavy901/oop)

---

## Repository Structure & Overview

This repository contains the complete, verified, and structured laboratory curriculum for CEUE203, covering all **14 Practical Laboratories** (Part A practice programs including **For Advanced Learners – home**) alongside the comprehensive **MiniBank Capstone Mini-Project** (integrating all Part B milestones across the semester).

```
oop/
├── .gitignore                    # Production gitignore for Java & OS files
├── README.md                     # Root comprehensive guide & student credits
├── practical-01/                 # Practical 1: Enums, Records, Switch Expressions
│   ├── VendingMachine.java
│   ├── TollBooth.java
│   ├── RPSLS.java                # For Advanced Learners – home
│   └── README.md
├── practical-02/                 # Practical 2: Encapsulation, Static State, Chaining
│   ├── Thermostat.java
│   ├── CinemaShow.java
│   ├── ParkingLot.java           # For Advanced Learners – home
│   └── README.md
├── practical-03/                 # Practical 3: Object Methods, Nested Classes, GCD
│   ├── Point.java
│   ├── Card.java
│   ├── Fraction.java             # For Advanced Learners – home
│   └── README.md
├── practical-04/                 # Practical 4: Strings, Regular Expressions, Tokenizers
│   ├── PasswordChecker.java
│   ├── ChatFilter.java
│   ├── TemplateFiller.java       # For Advanced Learners – home
│   └── README.md
├── practical-05/                 # Practical 5: Polymorphism, Abstract Classes, Super
│   ├── ShapeAreas.java
│   ├── PayrollSystem.java
│   ├── MediaLateFee.java         # For Advanced Learners – home
│   └── README.md
├── practical-06/                 # Practical 6: Interfaces, Lambdas, Marker Interfaces
│   ├── RemoteControl.java
│   ├── NotificationSenders.java
│   ├── DiscountEngine.java       # For Advanced Learners – home
│   └── README.md
├── practical-07/                 # Practical 7: Custom Annotations & Java Reflection
│   ├── FormValidator.java
│   ├── MiniTestRunner.java
│   ├── ColumnMapper.java         # For Advanced Learners – home
│   └── README.md
├── practical-08/                 # Practical 8: Checked Exceptions & AutoCloseable
│   ├── GuardedCalculator.java
│   ├── StockWarehouse.java
│   ├── AutoCloseableResourceDemo.java # For Advanced Learners – home
│   └── README.md
├── practical-09/                 # Practical 9: Concurrency, Race Conditions & Locks
│   ├── CounterRace.java
│   ├── SeatBookingRace.java
│   ├── ArraySumRace.java         # For Advanced Learners – home
│   └── README.md
├── practical-10/                 # Practical 10: Thread Pools & Deadlock Prevention
│   ├── ThreadPoolRunner.java
│   ├── ProducerConsumerDemo.java
│   ├── DeadlockDemo.java         # For Advanced Learners – home
│   └── README.md
├── practical-11/                 # Practical 11: Object Serialization & Java NIO
│   ├── ObjectSerializationDemo.java
│   ├── LogAnalyzer.java
│   ├── DirectoryTreeReporter.java # For Advanced Learners – home
│   └── README.md
├── practical-12/                 # Practical 12: Java Collections & Comparators
│   ├── WordFrequency.java
│   ├── UniqueVisitors.java
│   ├── Leaderboard.java          # For Advanced Learners – home
│   └── README.md
├── practical-13/                 # Practical 13: Generic Classes & Bounded Types
│   ├── GenericContainers.java
│   ├── BoundedStats.java
│   ├── GenericStack.java         # For Advanced Learners – home
│   └── README.md
├── practical-14/                 # Practical 14: Layered Architecture & Automated Tests
│   ├── LayeredAppDemo.java
│   ├── LayeredAppPackagedDemo.java # For Advanced Learners – home
│   └── README.md
└── mini-project/                 # Capstone: Full MiniBank Application (Part B Integration)
    ├── build_and_run.sh          # One-click automated build, test & packaging script
    ├── README.md                 # In-depth architectural documentation
    └── src/
        ├── main/java/com/minibank/
        │   ├── MiniBank.java             # Main menu & console interface
        │   ├── model/                    # BankInfo, Customer, Account, Subclasses
        │   ├── model/annotation/         # @Id, @Positive, @MaxLength, @Audit
        │   ├── interfaces/               # Transactable, InterestBearing, WithdrawRule
        │   ├── exception/                # BankException, InsufficientFundsException
        │   ├── util/                     # Validator, CommandParser, Formatter, Session
        │   ├── repository/               # Generic Repository<T>, AccountStore
        │   ├── concurrency/              # TransactionProcessor, AccountWorker, Buffer
        │   ├── persistence/              # StatePersister, TransactionLog, ReportGenerator
        │   └── service/                  # BankService layered business orchestrator
        └── test/java/com/minibank/
            └── MiniBankTest.java         # Automated verification test suite
```

---

## Practicals Summary Table (Part A)

| Practical | Topics / Key Skills | Practice Programs (Part A) |
|---|---|---|
| **01** | Enums, records, switch expressions, Scanner | `VendingMachine`, `TollBooth`, `RPSLS` |
| **02** | Encapsulation, static state, constructor chaining | `Thermostat`, `CinemaShow`, `ParkingLot` |
| **03** | `toString`, `equals`, `hashCode`, nested classes, GCD | `Point`, `Card`, `Fraction` |
| **04** | Strings, regex patterns, Matcher, StringBuilder | `PasswordChecker`, `ChatFilter`, `TemplateFiller` |
| **05** | Inheritance, abstract classes, run-time polymorphism | `ShapeAreas`, `PayrollSystem`, `MediaLateFee` |
| **06** | Interfaces, default methods, lambdas, marker interfaces | `RemoteControl`, `NotificationSenders`, `DiscountEngine` |
| **07** | Custom annotations (`@Retention`, `@Target`), Reflection | `FormValidator`, `MiniTestRunner`, `ColumnMapper` |
| **08** | Checked exceptions, exception hierarchies, `AutoCloseable` | `GuardedCalculator`, `StockWarehouse`, `AutoCloseableResourceDemo` |
| **09** | Threads, Runnable, race conditions, synchronization | `CounterRace`, `SeatBookingRace`, `ArraySumRace` |
| **10** | `ExecutorService`, thread pool, `wait`/`notify`, deadlock fix | `ThreadPoolRunner`, `ProducerConsumerDemo`, `DeadlockDemo` |
| **11** | Object serialization, `transient`, NIO `Path`, `Files` | `ObjectSerializationDemo`, `LogAnalyzer`, `DirectoryTreeReporter` |
| **12** | Collections (`List`, `Set`, `Map`), `Comparator`, `Comparable` | `WordFrequency`, `UniqueVisitors`, `Leaderboard` |
| **13** | Generic classes (`Box<T>`, `Pair`), bounded types `<N extends Number>` | `GenericContainers`, `BoundedStats`, `GenericStack` |
| **14** | Layered design (Model-Service-UI), automated testing | `LayeredAppDemo`, `LayeredAppPackagedDemo` |

---

## MiniBank Capstone Mini-Project (Part B Combined)

The `mini-project` integrates all Part B project requirements across the semester into a single, cohesive, enterprise-ready banking application:
- **Clean Layered Architecture**: Clear separation of UI (`MiniBank.java`), Service (`BankService.java`), Repository (`Repository<T>`, `AccountStore.java`), and Domain Models (`Account`, `Customer`).
- **Encapsulated Polymorphic Accounts**: Abstract `Account` base class extended by `SavingsAccount` (4.0% interest), `CurrentAccount` (overdraft protection), and `FixedDepositAccount` (7.0% locked interest).
- **Validation & Custom Metadata**: Regular expression validation for Mobile, Email, PAN, IFSC, and amounts, combined with reflective validation via `@Id`, `@Positive`, `@MaxLength`, and `@Audit`.
- **Concurrency & Deadlock Prevention**: Batch transactions processed over a managed thread pool (`ExecutorService`), thread-safe updates via `synchronized` methods, and circular deadlock prevention via deterministic account number lock ordering.
- **Persistence & Audit Reporting**: Binary state persistence across sessions using Java Object Serialization, alongside append-only audit logging and daily financial reconciliation reports powered by Java NIO.
- **Automated Test Suite**: Comprehensive self-validating test runner (`MiniBankTest.java`) ensuring 100% correctness on deposits, overdraft rejections, multi-threaded concurrency, and persistence round-trips.

---

## Quick Start & Execution

### 1. Running Individual Practicals (e.g. Practical 1)
```bash
cd practical-01
javac *.java
java VendingMachine
java TollBooth
java RPSLS
```

### 2. Building and Running the MiniBank Capstone Project
```bash
cd mini-project
chmod +x build_and_run.sh
./build_and_run.sh

# Run the generated executable JAR:
java -jar target/minibank.jar
```

---
*Created by Bhavy Gol (ID: 25CS016) | FTE, CHARUSAT University*
