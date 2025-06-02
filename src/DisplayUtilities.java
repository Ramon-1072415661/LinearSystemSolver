import java.text.DecimalFormat;

/**
 * Singleton class to handle all display utilities for linear system solver
 * Manages variable names, matrix formatting, and solution presentation
 */
public class DisplayUtilities {
    private static DisplayUtilities instance;

    // Array of variable names to use instead of X1, X2, etc.
    private static final String[] VARIABLE_NAMES = {"X", "Y", "Z", "A", "B", "C", "D", "E", "F", "G"};
    private final DecimalFormat df = new DecimalFormat("0.##");

    // EPSILON defines a small tolerance for floating-point comparisons to account for rounding errors.
    private static final double EPSILON = 1e-10;  // 0.0000000001

    // Private constructor to prevent instantiation
    private DisplayUtilities() {
    }

    /**
     * Gets the singleton instance
     */
    public static DisplayUtilities getInstance() {
        if (instance == null) {
            instance = new DisplayUtilities();
        }
        return instance;
    }

    /**
     * Returns the appropriate variable name for the given index
     */
    public String getVariableName(int index) {
        if (index < VARIABLE_NAMES.length) {
            return VARIABLE_NAMES[index];
        } else {
            // If we run out of predefined names, fall back to X1, X2, etc.
            return "X" + (index + 1);
        }
    }

    /**
     * Formats a number to show decimals limited to 2 positions
     */
    public String formatNumber(double number) {
        if (Math.abs(number - Math.round(number)) < EPSILON) {
            return Integer.toString((int) number);
        }

        // Format with 2 decimal places maximum
        String formatted = String.format("%.2f", number);

        // Remove trailing zeros after decimal point
        if (formatted.contains(".")) {
            while (formatted.endsWith("0")) {
                formatted = formatted.substring(0, formatted.length() - 1);
            }
            // Remove decimal point if it's the last character
            if (formatted.endsWith(".")) {
                formatted = formatted.substring(0, formatted.length() - 1);
            }
        }

        return formatted;
    }

    /**
     * Displays the matrix in augmented format
     */
    public void displayMatrix(double[][] matrix, int rows, int cols) {
        System.out.println();

        // Display in matrix format
        for (int i = 0; i < rows; i++) {
            System.out.print("[ ");

            // Display all columns
            for (int j = 0; j < cols; j++) {
                // Add separator before the last column (constant terms)
                if (j == cols - 1) {
                    System.out.print("  | ");
                }

                System.out.printf("%10s", formatNumber(matrix[i][j]));

                // Add spacing between elements (except after the last one)
                if (j < cols - 1) {
                    System.out.print("  ");
                }
            }

            System.out.println(" ]");
        }
        System.out.println();
    }

    /**
     * Appends matrix to a StringBuilder in augmented format
     */
    public void appendMatrix(StringBuilder steps, double[][] matrix, int rows, int cols) {
        steps.append("\n");
        for (int i = 0; i < rows; i++) {
            steps.append("[ ");
            for (int j = 0; j < cols; j++) {
                if (j == cols - 1) {
                    steps.append("  | ");
                }
                steps.append(String.format("%10s", formatNumber(matrix[i][j])));
                if (j < cols - 1) {
                    steps.append("  ");
                }
            }
            steps.append(" ]\n");
        }
        steps.append("\n");
    }

    /**
     * Displays the solution summary
     */
    public void displaySolutionSummary(double[] solution) {
        System.out.println("=== Solution Summary ===");
        if (solution != null && solution.length > 0) {
            System.out.println("The system has a unique solution:");
            for (int i = 0; i < solution.length; i++) {
                System.out.printf("%s = %s%n", getVariableName(i), formatNumber(solution[i]));
            }
        } else if (solution == null) {
            System.out.println("The system has infinitely many solutions.");
        } else {
            System.out.println("The system is inconsistent and has no solution.");
        }
    }

    /**
     * Appends final solution to steps
     */
    public void appendFinalSolution(StringBuilder steps, double[] solution) {
        steps.append("\nFinal Solution:\n");
        for (int i = 0; i < solution.length; i++) {
            steps.append(getVariableName(i)).append(" = ").append(formatNumber(solution[i])).append("\n");
        }
    }

    /**
     * Appends back substitution step to steps
     */
    public void appendBackSubstitutionStep(StringBuilder steps, int variableIndex,
                                           double constantTerm, double sum, double coefficient, double result) {
        steps.append(getVariableName(variableIndex)).append(" = (")
                .append(formatNumber(constantTerm)).append(" - ")
                .append(formatNumber(sum)).append(") / ")
                .append(formatNumber(coefficient)).append(" = ")
                .append(formatNumber(result)).append("\n");
    }
}