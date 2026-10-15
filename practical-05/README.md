# Practical 05: Inheritance, Abstract Classes & Run-Time Polymorphism

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Shape Areas (`ShapeAreas.java`)
- **Objective**: Design abstract `Shape` with abstract `area()` implemented by `Circle`, `Rectangle`, and `Triangle`.
- **Polymorphism**: Iterates over a heterogeneous `Shape[]` array in a single loop, calculating individual areas, running total, and identifying the largest shape.

### 2. Employee Payroll (`PayrollSystem.java`)
- **Objective**: Abstract `Employee` with `monthlySalary()` extended by `FullTime` (fixed), `PartTime` (hours * rate), and `Intern` (stipend).
- **Features**: Subclasses invoke `super(id, name)` constructor and use `instanceof` pattern matching to highlight interns.

### 3. Media Late Fee System (`MediaLateFee.java`) - For Advanced Learners
- **Objective**: Abstract `Media` hierarchy (`Book`, `DVD`, `VideoGame`) computing overdue return fees with distinct policy rules.
- **Rules**: Books charged Rs. 2/day, DVDs charged flat penalty after 3 days, and VideoGames capped at Rs. 200.

---

## How to Compile & Run
```bash
cd practical-05
javac *.java
java ShapeAreas
java PayrollSystem
java MediaLateFee
```
