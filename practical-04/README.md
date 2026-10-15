# Practical 04: Strings, Regular Expressions & Token Parsing

### Student Details
- **Name**: Bhavy Gol
- **Student ID**: 25CS016
- **Subject**: Object-Oriented Programming (CEUE203)
- **Institution**: Faculty of Technology and Engineering (FTE), CHARUSAT

---

## Part A - Practice Programs

### 1. Password Strength Evaluator (`PasswordChecker.java`)
- **Objective**: Validate passwords against 4 security rules: length >= 8, uppercase character, digit, and special symbol using regex.
- **Classification**: Returns `"Weak"` (0-1 rules passed), `"Medium"` (2-3 rules passed), or `"Strong"` (all 4 rules passed).
- **Expected Output**: `"abc"` -> Weak; `"Abcd1234!"` -> Strong.

### 2. Chat Log Filter (`ChatFilter.java`)
- **Objective**: Parse raw log lines with `split(" ", 3)` into timestamp, user, and message tokens, safely skipping malformed lines.
- **Reporting**: Searches for keyword case-insensitively and accumulates matches using `StringBuilder`.
- **Expected Output**: Keyword `"hello"` over 3 lines (1 malformed) -> `"Matches: 1"` with matching lines.

### 3. Template Filler (`TemplateFiller.java`) - For Advanced Learners
- **Objective**: Dynamic placeholder substitution using regex `Pattern` (`\{(\w+)\}`) and `Matcher`.
- **Logic**: Matches tokens against parallel arrays and replaces unknown keys with `"[?]"`.
- **Expected Output**: `"Dear {name}, order {id} ships {date}."` -> `"Dear Riya, order A07 ships [?]."`.

---

## How to Compile & Run
```bash
cd practical-04
javac *.java
java PasswordChecker
java ChatFilter
java TemplateFiller
```
