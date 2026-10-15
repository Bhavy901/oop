# Practical 10: Thread Pools, Inter-Thread Coordination & Deadlock Prevention

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Thread Pool Runner (`ThreadPoolRunner.java`)
- **Objective**: Manage concurrency using `ExecutorService` (`Executors.newFixedThreadPool(3)`).
- **Observation**: Submits 10 tasks, observing worker threads being reused efficiently, followed by graceful `shutdown()` and `awaitTermination()`.

### 2. Producer-Consumer Coordination (`ProducerConsumerDemo.java`)
- **Objective**: Implement thread-safe bounded buffer using intrinsic monitors: `wait()` and `notifyAll()`.
- **Guarantee**: Producers wait when full; consumers wait when empty; guarantees zero message loss.

### 3. Deadlock Simulation & Consistent Lock Ordering (`DeadlockDemo.java`) - For Advanced Learners
- **Objective**: Demonstrate circular wait deadlock between two accounts during opposite transfers.
- **Resolution**: Eliminates deadlock completely by establishing global consistent lock ordering based on account ID comparison.

---

## How to Compile & Run
```bash
cd practical-10
javac *.java
java ThreadPoolRunner
java ProducerConsumerDemo
java DeadlockDemo
```
