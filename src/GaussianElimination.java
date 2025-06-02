import java.util.Arrays;

/**
 * Class to perform Gaussian elimination on an augmented matrix
 */
public class GaussianElimination {
    private final double[][] matrix;
    private final int rows;
    private final int cols;
    private final StringBuilder steps;
    private final DisplayUtilities display;

    // EPSILON defines a small tolerance for floating-point comparisons to account for rounding errors.
    private static final double EPSILON = 1e-10;  // 0.0000000001

    public GaussianElimination(double[][] matrix) {
        this.matrix = deepCopy(matrix);
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.steps = new StringBuilder();
        this.display = DisplayUtilities.getInstance();
    }

    private double[][] deepCopy(double[][] original) {
        double[][] copy = new double[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    public double[] solve() {
        steps.append("Starting Gaussian Elimination\n");
        steps.append("----------------------------\n\n");
        steps.append("Initial augmented matrix:\n");
        appendMatrix();

        // Forward elimination
        forwardElimination();

        // Check if the system is inconsistent
        if (isInconsistent()) {
            steps.append("\nThe system is inconsistent and has no solution.\n");
            return new double[0];
        }

        // Check if the system has infinite solutions
        if (hasInfiniteSolutions()) {
            steps.append("\nThe system has infinitely many solutions.\n");
            return null;
        }

        // Back substitution
        return backSubstitution();
    }

    private void forwardElimination() {
        steps.append("\nForward Elimination:\n");
        steps.append("--------------------\n");

        for (int pivot = 0; pivot < Math.min(rows, cols - 1); pivot++) {
            // Find the pivot row
            int maxRow = findPivotRow(pivot);

            // If the pivot element is zero, skip this column
            if (Math.abs(matrix[maxRow][pivot]) < EPSILON) {
                steps.append("\nSkipping column ").append(pivot + 1).append(" (pivot element is zero).\n");
                continue;
            }

            // Swap rows if necessary
            if (maxRow != pivot) {
                swapRows(pivot, maxRow);
                steps.append("\nSwap row ").append(pivot + 1).append(" with row ").append(maxRow + 1).append(":\n");
                appendMatrix();
            }

            // Eliminate below
            for (int i = pivot + 1; i < rows; i++) {
                double factor = matrix[i][pivot] / matrix[pivot][pivot];
                if (Math.abs(factor) < EPSILON) continue;

                steps.append("\nEliminate in row ").append(i + 1).append(" using row ").append(pivot + 1).append(":\n");
                steps.append("R").append(i + 1).append(" = R").append(i + 1).append(" - ")
                        .append(display.formatNumber(factor)).append(" * R").append(pivot + 1).append("\n");

                for (int j = pivot; j < cols; j++) {
                    matrix[i][j] -= factor * matrix[pivot][j];
                    if (Math.abs(matrix[i][j]) < EPSILON) {
                        matrix[i][j] = 0;
                    }
                }

                appendMatrix();
            }
        }

        steps.append("\nRow Echelon Form:\n");
        appendMatrix();
    }

    private int findPivotRow(int col) {
        int maxRow = col;
        double maxVal = Math.abs(matrix[col][col]);

        for (int i = col + 1; i < rows; i++) {
            if (Math.abs(matrix[i][col]) > maxVal) {
                maxVal = Math.abs(matrix[i][col]);
                maxRow = i;
            }
        }

        return maxRow;
    }

    private void swapRows(int row1, int row2) {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    private boolean isInconsistent() {
        for (int i = 0; i < rows; i++) {
            boolean allZeros = true;
            for (int j = 0; j < cols - 1; j++) {
                if (Math.abs(matrix[i][j]) >= EPSILON) {
                    allZeros = false;
                    break;
                }
            }
            if (allZeros && Math.abs(matrix[i][cols - 1]) >= EPSILON) {
                return true;
            }
        }
        return false;
    }

    private boolean hasInfiniteSolutions() {
        int rank = 0;
        for (int i = 0; i < rows; i++) {
            boolean nonZeroRow = false;
            for (int j = 0; j < cols - 1; j++) {
                if (Math.abs(matrix[i][j]) >= EPSILON) {
                    nonZeroRow = true;
                    break;
                }
            }
            if (nonZeroRow) {
                rank++;
            }
        }

        return rank < cols - 1;
    }

    private double[] backSubstitution() {
        steps.append("\nBack Substitution:\n");
        steps.append("-----------------\n");

        int variables = cols - 1;
        double[] solution = new double[variables];

        for (int i = Math.min(rows, variables) - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < variables; j++) {
                sum += matrix[i][j] * solution[j];
            }

            solution[i] = (matrix[i][variables] - sum) / matrix[i][i];

            display.appendBackSubstitutionStep(steps, i, matrix[i][variables], sum, matrix[i][i], solution[i]);
        }

        display.appendFinalSolution(steps, solution);

        return solution;
    }

    private void appendMatrix() {
        display.appendMatrix(steps, matrix, rows, cols);
    }

    public String getSteps() {
        return steps.toString();
    }
}