# Practical 11: Object Serialization & Java NIO Files API

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Object Serialization (`ObjectSerializationDemo.java`)
- **Objective**: Serialize and deserialize object arrays using `ObjectOutputStream` and `ObjectInputStream`.
- **Transient Field**: Marks sensitive temporary session token as `transient`, proving it is omitted during serialization and restored as null.

### 2. Java NIO Log Analyzer (`LogAnalyzer.java`)
- **Objective**: Inspect text log files using `Path`, `Files`, and `BasicFileAttributes`.
- **Analysis**: Reads lines with `BufferedReader`, counts lines and keyword matches (`"ERROR"`), and displays file creation timestamps and byte sizes.

### 3. Directory Tree Reporter (`DirectoryTreeReporter.java`) - For Advanced Learners
- **Objective**: Recursive directory walk using `Files.walkFileTree` and custom `SimpleFileVisitor<Path>`.
- **Output**: Generates formatted audit summary of all files, byte sizes, and directory totals, outputting to a report file.

---

## How to Compile & Run
```bash
cd practical-11
javac *.java
java ObjectSerializationDemo
java LogAnalyzer
java DirectoryTreeReporter
```
