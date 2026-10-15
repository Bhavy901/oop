# Practical 13: Generics, Bounded Types & Type Safety

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Generic Containers (`GenericContainers.java`)
- **Objective**: Implement generic classes `Box<T>` and `Pair<A, B>` ensuring compile-time type safety without manual casting.
- **Generic Method**: Implements `<T> T getFirstElement(T[] array)`.

### 2. Bounded Statistics (`BoundedStats.java`)
- **Objective**: Bounded generic method `<N extends Number> double average(N[] values)`.
- **Type Bounds**: Computes average across `Integer[]`, `Double[]`, `Long[]`, and `Float[]`, while refusing non-Number types at compile time.

### 3. Generic Array-backed Stack (`GenericStack.java`) - For Advanced Learners
- **Objective**: Generic `CustomStack<T>` backed by dynamic resizing array.
- **Robustness**: Supports `push`, `pop`, `peek`, `isEmpty`, and handles empty pop calls cleanly with `EmptyStackException`.

---

## How to Compile & Run
```bash
cd practical-13
javac *.java
java GenericContainers
java BoundedStats
java GenericStack
```
