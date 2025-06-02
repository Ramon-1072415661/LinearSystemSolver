# Linear System Solver

A Java implementation of Gaussian Elimination for solving systems of linear equations with a command-line interface.

## Overview

This project provides a complete solution for solving linear systems using Gaussian elimination. It can handle systems with:
- **✅ Unique Solution** - One exact answer
- **❌ No solutions** - Inconsistent systems
- **♾️ Infinite solutions** - Underdetermined systems

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

## How to Run the Project in IntelliJ IDEA Community Edition

### ✅ Step 1: Download and Install IntelliJ IDEA Community Edition

1. Go to the official IntelliJ IDEA download page:
   [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)

2. Download the **Community Edition** (free).

<p align="center">
  <img src="https://github.com/user-attachments/assets/d7f57bcf-8e0f-4f88-ad43-ec35d01ccb8d" alt="image" />
</p>

4. Install IntelliJ following the installer instructions for your operating system.

---

### ✅ Step 2: Clone the Repository Using IntelliJ

1. Open IntelliJ IDEA.
2. On the **Welcome screen**, click on **"Clone Repository"**.
   
<p align="center">
  <img src="https://github.com/user-attachments/assets/4344bcd1-547a-4ace-be1e-208eda7b4cea" alt="image" />
</p>

4. In the **URL** field, enter this repository link

   ```
   https://github.com/Ramon-1072415661/LinearSystemSolver.git
   ```

<p align="center">
  <img src="https://github.com/user-attachments/assets/6bc62102-b57b-49be-9bb8-7e668fee6df6" alt="image" />
</p>

6. Choose a **local directory** where IntelliJ will save the project.
7. Click **"Clone"**.

---

### ✅ Step 3: Open and Configure the Project

1. Once cloning is complete, IntelliJ will automatically open the project.
2. If prompted, choose to **trust the project**.
3. Ensure your **Project SDK** is set to Java 23 or higher:

   * Go to **File** → **Project Structure** → **Project**.
   * Set **Project SDK** to the appropriate Java version.
   * If no SDK is available, click **Add SDK** → **Download JDK** → choose version 23+.

<p align="center">
  <img src="https://github.com/user-attachments/assets/fafec615-a253-4622-82af-46d914be7eb3" alt="image" />
</p>

---

### ✅ Step 4: Run the Application

1. In the **Project view** (on the left), navigate to `LinearSystemSolverCLI.java`.
2. Right-click the file and select **"Run 'LinearSystemSolverCLI.main()'"**.
3. The application will start in the **Run** window at the bottom.
4. Follow the interactive prompts:

   * Choose matrix generation method (manual input or random)
   * Enter number of equations and variables
   * Input coefficients (if manual mode)
   * View the solution process and results

<p align="center">
  <img src="https://github.com/user-attachments/assets/65895b39-0ba0-4096-93e7-2c4a308b6a6c" alt="image" />
</p>

---

### ✅ Step 5: Run Unit Tests

1. Navigate to `GaussianEliminationTest.java` under **test** folder in the **Project view**.
2. Right-click the file and select **"Run 'GaussianEliminationTest'"**.
3. The test results will appear in the **Run** window.

> ⚠️ Make sure JUnit 5 is properly configured:
>
> * IntelliJ may prompt to add JUnit automatically. Accept this prompt.
> * Alternatively, add it manually via **File** → **Project Structure** → **Libraries**.

<p align="center">
  <img src="https://github.com/user-attachments/assets/2c7a6d16-7c2d-4365-b872-c8ce408b192b" alt="image" />
</p>

---

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
