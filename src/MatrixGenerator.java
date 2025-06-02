import java.util.Random;

/**
 * Matrix generator for linear systems
 */
public class MatrixGenerator {
    private static final Random random = new Random();
    private static final int RANDOM_RANGE_MIN = -100;
    private static final int RANDOM_RANGE_MAX = 100;
    private static final DisplayUtilities display = DisplayUtilities.getInstance();

    /**
     * Generates a basic random matrix (may not have unique solution)
     */
    public static void generateBasicRandom(double[][] matrix, int numEquations, int numVariables) {
        System.out.println("\nGenerating basic random matrix with coefficients between " + RANDOM_RANGE_MIN + " and " + RANDOM_RANGE_MAX + "...");

        System.out.println("Warning: This may not have a unique solution!");

        for (int i = 0; i < numEquations; i++) {
            // Generate random coefficients (excluding zero to avoid trivial cases)
            for (int j = 0; j < numVariables; j++) {
                int coefficient;
                do {
                    coefficient = random.nextInt(RANDOM_RANGE_MAX - RANDOM_RANGE_MIN + 1) + RANDOM_RANGE_MIN;
                } while (coefficient == 0); // Avoid zero coefficients for cleaner equations

                matrix[i][j] = coefficient;
            }

            // Generate random constant term (can be zero)
            int constantTerm = random.nextInt(RANDOM_RANGE_MAX - RANDOM_RANGE_MIN + 1) + RANDOM_RANGE_MIN;
            matrix[i][numVariables] = constantTerm;
        }

        System.out.println("Basic random matrix generated successfully!");
    }
}