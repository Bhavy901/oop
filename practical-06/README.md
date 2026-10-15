# Practical 06: Interfaces, Lambdas & Modular Packaging

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Remote Control (`RemoteControl.java`)
- **Objective**: Define interface `Switchable` with `on()`, `off()` and default `toggle()` implemented by `Light` and `Fan`.
- **Functional Interface**: Implements `SchedulePolicy` supplied once via an Anonymous Class and once via a Lambda Expression.

### 2. Notification Senders (`NotificationSenders.java`)
- **Objective**: Functional interface `Notifier` with `send(String)` defined via lambdas for Email, SMS, and Push.
- **Marker Interface**: Uses marker interface `Urgent` to detect critical senders and trigger duplicate broadcasts.

### 3. Discount Engine (`DiscountEngine.java`) - For Advanced Learners
- **Objective**: Dynamic retail discount processor with `@FunctionalInterface DiscountRule`.
- **Runtime Selection**: Lets user pick percentage off, flat discount, or clearance flash sale lambdas at runtime and calculates savings.

---

## How to Compile & Run
```bash
cd practical-06
javac *.java
java RemoteControl
java NotificationSenders
java DiscountEngine
```
