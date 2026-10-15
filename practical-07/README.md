# Practical 07: Custom Annotations & Java Reflection

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Form Validator (`FormValidator.java`)
- **Objective**: Define custom annotations `@NotBlank` and `@MaxLength(int)` with `@Retention(RUNTIME)` and `@Target(FIELD)`.
- **Reflection**: Inspects annotated `SignupForm` using `getDeclaredFields()`, `isAnnotationPresent()`, `setAccessible(true)` and returns field validation errors.

### 2. Mini Test Runner (`MiniTestRunner.java`)
- **Objective**: Implement JUnit-style test execution using custom method annotation `@Run`.
- **Reflection**: Inspects methods via `getDeclaredMethods()`, filtering and invoking only marked test cases, reporting passed/failed tallies.

### 3. Column Mapper (`ColumnMapper.java`) - For Advanced Learners
- **Objective**: Custom `@Column(name, required)` annotation to map tabular rows to Java object fields dynamically.
- **Features**: Performs type conversion (String, int, double, long) and handles missing columns gracefully.

---

## How to Compile & Run
```bash
cd practical-07
javac *.java
java FormValidator
java MiniTestRunner
java ColumnMapper
```
