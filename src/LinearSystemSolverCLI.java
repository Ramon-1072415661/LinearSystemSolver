import java.util.Scanner;

/**
 * Command Line Interface for solving linear systems using Gaussian Elimination
 */
public class LinearSystemSolverCLI {
    private static final int MAX_VARIABLES = 10;
    private static final int MAX_EQUATIONS = 10;
    private static final DisplayUtilities display = DisplayUtilities.getInstance();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Linear System Solver ===");
        System.out.println("This program solves systems of linear equations using Gaussian Elimination.");

        // Enhanced menu for matrix generation options
        int generationChoice = getGenerationChoice(scanner);

        // Get the number of equations and variables with improved validation
        int numEquations = readIntegerInRange(scanner, "Enter the number of equations (1-" + MAX_EQUATIONS + "): ", 1, MAX_EQUATIONS);
        int numVariables = readIntegerInRange(scanner, "Enter the number of variables (1-" + MAX_VARIABLES + "): ", 1, MAX_VARIABLES);

        // Create the augmented matrix
        double[][] augmentedMatrix = new double[numEquations][numVariables + 1];

        switch (generationChoice) {
            case 1:
                inputMatrixManually(scanner, augmentedMatrix, numEquations, numVariables);
                break;
            case 2:
                MatrixGenerator.generateBasicRandom(augmentedMatrix, numEquations, numVariables);
                break;
        }

        // Display the generated/entered matrix
        System.out.println("\n=== System of Equations ===");
        display.displayMatrix(augmentedMatrix, numEquations, numVariables + 1);

        // Solve the system directly
        GaussianElimination solver = new GaussianElimination(augmentedMatrix);
        double[] solution = solver.solve();

        // Display the solution
        System.out.println("\n=== Solution Process ===");
        System.out.println(solver.getSteps());

        // Display solution summary using singleton
        display.displaySolutionSummary(solution);

        scanner.close();
    }

    /**
     * Gets user choice for matrix generation method
     */
    private static int getGenerationChoice(Scanner scanner) {
        System.out.println("\nChoose matrix generation method:");
        System.out.println("1. Enter matrix manually");
        System.out.println("2. Generate basic random matrix");

        return readIntegerInRange(scanner, "Enter your choice (1-2): ", 1, 2);
    }

    /**
     * Handles manual input of matrix coefficients
     */
    private static void inputMatrixManually(Scanner scanner, double[][] matrix, int numEquations, int numVariables) {
        System.out.println("\nEnter the coefficients of the augmented matrix:");
        System.out.println("Format: [coefficients... | constant]");
        System.out.println("You can use decimals (e.g., 2.5) or fractions will be converted to decimals.");

        for (int i = 0; i < numEquations; i++) {
            System.out.println("\n--- Equation " + (i + 1) + " ---");

            // Input coefficients with better prompts
            for (int j = 0; j < numVariables; j++) {
                String varName = display.getVariableName(j);
                String prompt = String.format("Coefficient for %s: ", varName);
                matrix[i][j] = readValidDouble(scanner, prompt);
            }

            // Input constant term
            matrix[i][numVariables] = readValidDouble(scanner, "Constant term: ");
        }
    }

    /**
     * Reads an integer within a specified range with comprehensive validation
     */
    private static int readIntegerInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number between " + min + " and " + max + ".");
                continue;
            }

            try {
                int value = Integer.parseInt(input);
                if (value < min) {
                    System.out.println("Value too small. Please enter a number greater than or equal to " + min + ".");
                } else if (value > max) {
                    System.out.println("Value too large. Please enter a number less than or equal to " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input '" + input + "'. Please enter a valid integer between " + min + " and " + max + ".");
            }
        }
    }

    /**
     * Reads a valid double from the user with enhanced validation and user-friendly prompts
     */
    private static double readValidDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number.");
                continue;
            }

            // Replace comma with period for international number formats
            input = input.replace(',', '.');

            try {
                double value = Double.parseDouble(input);

                // Check for reasonable bounds to prevent extreme values
                if (Double.isInfinite(value) || Double.isNaN(value)) {
                    System.out.println("Invalid number. Please enter a finite number.");
                    continue;
                }

                if (Math.abs(value) > 1e12) {
                    System.out.println("Number too large. Please enter a smaller number (absolute value < 1e12).");
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input '" + input + "'. Please enter a valid number (e.g., 2.5, -3, 0.75).");
            }
        }
    }
}