# Practical 08: Exception Handling & AutoCloseable Resources

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Guarded Calculator (`GuardedCalculator.java`)
- **Objective**: Robust arithmetic processor throwing custom checked `DivideByZeroException`.
- **Handling**: Handles invalid numbers, illegal operators, and arithmetic errors distinctly, looping until valid input succeeds, with `finally` auditing each attempt.

### 2. Stock Warehouse (`StockWarehouse.java`)
- **Objective**: Warehouse inventory system raising checked exceptions `OutOfStockException` (carrying shortfall) and `InvalidQuantityException`.
- **Fault Tolerance**: Batches requests and reports errors without terminating program flow.

### 3. AutoCloseable Resource Demo (`AutoCloseableResourceDemo.java`) - For Advanced Learners
- **Objective**: Custom resource implementing `AutoCloseable` demonstrating deterministic cleanup with `try-with-resources`.
- **Behavior**: Proves that `close()` is always invoked even when runtime exceptions occur inside the block, preserving the original root exception.

---

## How to Compile & Run
```bash
cd practical-08
javac *.java
java GuardedCalculator
java StockWarehouse
java AutoCloseableResourceDemo
```
