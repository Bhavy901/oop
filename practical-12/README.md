# Practical 12: Java Collections Framework & Comparators

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Word Frequency Counter (`WordFrequency.java`)
- **Objective**: Parse text paragraphs, map word frequencies in a `HashMap<String, Integer>`, and sort descending by count with a custom `Comparator`.
- **Tie-breaker**: Resolves frequency ties alphabetically by word.

### 2. Unique Visitors (`UniqueVisitors.java`)
- **Objective**: Contrast `HashSet` and `LinkedHashSet` over duplicate login streams.
- **Features**: `HashSet` computes unique count; `LinkedHashSet` preserves first-seen arrival order; prints first 3 distinct visitors.

### 3. Gaming Leaderboard (`Leaderboard.java`) - For Advanced Learners
- **Objective**: Rank player scores held in a `Map` using custom sorting logic.
- **Rules**: Displays top-N players sorted descending by score with alphabetical tie-breaking on usernames.

---

## How to Compile & Run
```bash
cd practical-12
javac *.java
java WordFrequency
java UniqueVisitors
java Leaderboard
```
