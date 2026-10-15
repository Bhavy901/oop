# Practical 14: Layered Architecture, Modular Integration & Automated Testing

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Tiny Layered Application (`LayeredAppDemo.java`)
- **Objective**: Implement clean 3-tier architecture: Model (`Task`) -> Service (`TaskService`) -> UI / Automated Test Suite.
- **Self-Testing**: Unit test runner asserting that adding tasks increases count, invalid task titles throw exceptions, and non-existent IDs fail gracefully.

### 2. Packaged App with State Persistence (`LayeredAppPackagedDemo.java`) - For Advanced Learners
- **Objective**: Modular layered architecture with persistent serialization to disk.
- **Verification**: Executes automated assertions proving that saved entities match reloaded entities with 100% fidelity.

---

## How to Compile & Run
```bash
cd practical-14
javac *.java
java LayeredAppDemo
java LayeredAppPackagedDemo
```
