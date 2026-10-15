# Practical 02: Classes, Encapsulation, Static Members & Constructors

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Smart Thermostat (`Thermostat.java`)
- **Objective**: Demonstrate encapsulation with private fields (`location`, `temperature`, `MIN=16`, `MAX=30`, `activeCount`).
- **Features**: Constructor chaining via `this(location, 22)`, boundary checking in `raise()` and `lower()`, and static `getActiveCount()`.
- **Expected Output**: Temperature raises up to 30 with `"Already at maximum (30)"`, lowers down to 16 with `"Already at minimum (16)"`, and prints active thermostat count.

### 2. Cinema Show (`CinemaShow.java`)
- **Objective**: Manage movie ticket bookings with private `seatsAvailable`, `capacity`, and static `totalBooked`.
- **Features**: `book(int n)` reduces available seats if capacity permits, `cancel(int n)` restores seats up to capacity limit.
- **Expected Output**: Rejects overbooking requests (`book` returns `false`), preserves accurate seat counts, and tallies total bookings made.

### 3. Parking Lot (`ParkingLot.java`) - For Advanced Learners
- **Objective**: Simulate two-wheeler (Rs. 20) and four-wheeler (Rs. 40) parking with strict capacity limits and static revenue tracking.
- **Features**: `park(String type)` and `leave(String type)` enforce boundaries and update shared revenue.
- **Expected Output**: Full parking areas reject entry with `"Full"`, leaves decrement counts never below 0, and final revenue reflects parked counts.

---

## How to Compile & Run
```bash
cd practical-02
javac *.java
java Thermostat
java CinemaShow
java ParkingLot
```
