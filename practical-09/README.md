# Practical 09: Multithreading, Race Conditions & Synchronization

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Counter Race (`CounterRace.java`)
- **Objective**: Demonstrate lost updates in multithreaded environments.
- **Comparison**: Contrasts an unsynchronized counter (resulting in corrupted, lower counts) with a `synchronized` counter (yielding 100% accurate totals).

### 2. Seat Booking Race (`SeatBookingRace.java`)
- **Objective**: Model overselling race condition where 10 concurrent threads compete for 5 available seats.
- **Resolution**: Shows how lack of synchronization allows overselling, whereas a `synchronized book()` method strictly limits bookings to 5.

### 3. Array Sum Race & Fixes (`ArraySumRace.java`) - For Advanced Learners
- **Objective**: Divide a 1,000,000 element array across 4 threads adding to a shared accumulator.
- **Analysis**: Demonstrates the race condition error, fixes it using (1) `synchronized` blocks and (2) `AtomicLong`, and compares execution performance and lock contention.

---

## How to Compile & Run
```bash
cd practical-09
javac *.java
java CounterRace
java SeatBookingRace
java ArraySumRace
```
