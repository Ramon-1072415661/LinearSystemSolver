# Linear System Solver

A Java implementation of Gaussian Elimination for solving systems of linear equations with a command-line interface.

## Overview

This project provides a complete solution for solving linear systems using Gaussian elimination. It can handle systems with:
- **Unique solutions** - One exact answer
- **No solutions** - Inconsistent systems
- **Infinite solutions** - Underdetermined systems

## Features

- Interactive command-line interface
- Manual matrix input or random matrix generation
- Step-by-step solution process display
- Support for decimal coefficients
- Clean, formatted output with variable names (X, Y, Z, etc.)

## Project Structure

```
├── LinearSystemSolverCLI.java    # Main application and user interface
├── GaussianElimination.java      # Core algorithm implementation
├── DisplayUtilities.java         # Formatting and display utilities
├── MatrixGenerator.java          # Random matrix generation
└── GaussianEliminationTest.java  # Unit tests
```

## How to Run

### Prerequisites
- Java 23 or higher
- JUnit 5 (for running tests)

### Running the Application

1. **Compile all Java files:**
   ```bash
   javac *.java
   ```

2. **Run the main application:**
   ```bash
   java LinearSystemSolverCLI
   ```

3. **Follow the interactive prompts:**
    - Choose matrix generation method (manual input or random)
    - Enter number of equations and variables
    - Input coefficients (if manual mode)
    - View the solution process and results

### Running Tests

1. **Compile with JUnit:**
   ```bash
   javac -cp ".:junit-platform-console-standalone-1.9.3.jar" *.java
   ```

2. **Run the tests:**
   ```bash
   java -cp ".:junit-platform-console-standalone-1.9.3.jar" org.junit.platform.console.ConsoleLauncher --scan-classpath
   ```

## Example Usage

### Input Example
```
=== Linear System Solver ===
Enter the number of equations (1-10): 2
Enter the number of variables (1-10): 2

--- Equation 1 ---
Coefficient for X: 1
Coefficient for Y: 1
Constant term: 3

--- Equation 2 ---
Coefficient for X: 2
Coefficient for Y: -1
Constant term: 0
```

### Output Example
```
=== System of Equations ===
[         1          1  |         3 ]
[         2         -1  |         0 ]

=== Solution Process ===
Starting Gaussian Elimination
----------------------------

Initial augmented matrix:
[         1          1  |         3 ]
[         2         -1  |         0 ]

Forward Elimination:
--------------------

Eliminate in row 2 using row 1:
R2 = R2 - 2 * R1
[         1          1  |         3 ]
[         0         -3  |        -6 ]

Back Substitution:
-----------------
Y = (-6 - 0) / -3 = 2
X = (3 - 2) / 1 = 1

Final Solution:
X = 1
Y = 2

=== Solution Summary ===
The system has a unique solution:
X = 1
Y = 2
```

## Supported System Types

### 1. Unique Solution
System has exactly one solution:
```
x + y = 3
2x - y = 0
→ Solution: x = 1, y = 2
```

### 2. No Solution (Inconsistent)
System has contradictory equations:
```
x + y = 1
x + y = 2
→ No solution exists
```

### 3. Infinite Solutions
System has dependent equations:
```
x + y = 3
2x + 2y = 6
→ Infinitely many solutions
```

## Technical Details

- **Algorithm**: Gaussian elimination with partial pivoting
- **Precision**: Uses epsilon tolerance (1e-10) for floating-point comparisons
- **Matrix Size**: Supports up to 10x10 systems
- **Number Format**: Supports decimals 
