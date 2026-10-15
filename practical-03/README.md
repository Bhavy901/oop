# Practical 03: Object Methods (toString, equals, hashCode) & Nested Classes

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Distinct Points (`Point.java`)
- **Objective**: Override `toString()`, `equals(Object)`, and `hashCode()` contract for 2D points `(x, y)`.
- **Driver**: Evaluates an array of `Point` objects containing duplicates and counts unique coordinates using `equals()`.
- **Expected Output**: 5 points with 2 duplicates -> `"Distinct: 3"`.

### 2. Duplicate Card Check (`Card.java`)
- **Objective**: Create `Card` with rank and suit, overriding `toString()` as `"Rank of Suit"` and `equals()`/`hashCode()` by rank and suit.
- **Driver**: Progressively adds cards to storage and detects repeats on entry.
- **Expected Output**: `"Duplicate found: Ace of Spades"`.

### 3. Fractions (`Fraction.java`) - For Advanced Learners
- **Objective**: Implement rational fraction reduction to lowest terms using GCD.
- **Features**: Reduces numerator and denominator in constructor and checks equality based on reduced terms.
- **Expected Output**: `new Fraction(2, 4)` prints `"1/2"` and equals `new Fraction(1, 2)`.

---

## How to Compile & Run
```bash
cd practical-03
javac *.java
java Point
java Card
java Fraction
```
