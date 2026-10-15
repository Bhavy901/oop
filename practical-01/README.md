# Practical 01: Java Fundamentals, Enums, Records & Switch Expressions

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Vending Machine (`VendingMachine.java`)
- **Objective**: Use enum constants (`ONE`, `TWO`, `FIVE`, `TEN`) and a modern switch expression to convert coins into integer values.
- **Process**: Loops reading coin inputs, accumulates the running total until reaching the snack price of 15, and returns the exact change (`total - 15`).
- **Expected Output**:
  - `TEN` then `FIVE` -> `Paid. Change: 0`
  - `TEN` then `TEN` -> `Paid. Change: 5`

### 2. Toll Booth (`TollBooth.java`)
- **Objective**: Leverage Java `record Vehicle(String number, String type)` to model read-only vehicles.
- **Process**: Keeps running toll revenue and counts for `bike` (20 Rs), `car` (50 Rs), and `truck` (150 Rs) using switch expressions until `"done"` is entered.
- **Expected Output**: `two cars + one bike` -> `Total toll: 120`, `Most frequent: car`.

### 3. Rock-Paper-Scissors-Lizard-Spock (`RPSLS.java`) - For Advanced Learners
- **Objective**: Implement extended 5-choice game with enum `Move` { `ROCK`, `PAPER`, `SCISSORS`, `LIZARD`, `SPOCK` }.
- **Process**: `winner(Move a, Move b)` uses a switch expression mapping all winning and tie interactions. Plays 5 rounds against computer and computes final score.
- **Expected Output**: Prints round outcomes and ends with a `"You win 3–2"` summary.

---

## How to Compile & Run
```bash
cd practical-01
javac *.java
java VendingMachine
java TollBooth
java RPSLS
```
