import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for GaussianElimination class
 * Tests various matrix configurations, edge cases, and solution types
 */
public class GaussianEliminationTest {

    // EPSILON defines a small tolerance for floating-point comparisons to account for rounding errors.
    private static final double EPSILON = 1e-10;  // 0.0000000001
    
    @BeforeEach
    void setUp() {
        // Any setup needed before each test
    }
    
    // ========== UNIQUE SOLUTION TESTS ==========
    
    @Test
    @DisplayName("2x2 System with Unique Solution - Simple Case")
    void testSimple2x2UniqueSystem() {
        // System: x + y = 3, 2x - y = 0
        // Solution: x = 1, y = 2
        double[][] matrix = {
            {1, 1, 3},   // x + y = 3
            {2, -1, 0}   // 2x - y = 0
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(2, solution.length);
        assertEquals(1.0, solution[0], EPSILON, "X should be 1.0");
        assertEquals(2.0, solution[1], EPSILON, "Y should be 2.0");
    }
    
    @Test
    @DisplayName("3x3 System with Unique Solution")
    void testSimple3x3UniqueSystem() {
        // System: x + y + z = 6, 2x + y - z = 1, x - y + z = 2
        // Solution: x = 1, y = 2, z = 3
        double[][] matrix = {
            {1, 1, 1, 6},    // x + y + z = 6
            {2, 1, -1, 1},   // 2x + y - z = 1
            {1, -1, 1, 2}    // x - y + z = 2
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(3, solution.length);
        assertEquals(1.0, solution[0], EPSILON, "X should be 1.0");
        assertEquals(2.0, solution[1], EPSILON, "Y should be 2.0");
        assertEquals(3.0, solution[2], EPSILON, "Z should be 3.0");
    }

    @Test
    @DisplayName("System with Decimal Coefficients")
    void testDecimalCoefficients() {
        // System: 0.5x + 0.25y = 1.5, 1.5x - 0.75y = 0.75
        // Solution: x = 1.75, y = 2.5
        double[][] matrix = {
                {0.5, 0.25, 1.5},
                {1.5, -0.75, 0.75}
        };

        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();

        assertNotNull(solution);
        assertEquals(2, solution.length);
        assertEquals(1.75, solution[0], EPSILON, "X should be 1.75");
        assertEquals(2.5, solution[1], EPSILON, "Y should be 2.5");
    }

    @Test
    @DisplayName("System with Negative Coefficients")
    void testNegativeCoefficients() {
        // System: -2x - 3y = -5, -4x - y = -7
        // Solution: x = 1.6, y = 0.6
        double[][] matrix = {
                {-2, -3, -5},
                {-4, -1, -7}
        };

        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();

        assertNotNull(solution);
        assertEquals(2, solution.length);
        assertEquals(1.6, solution[0], EPSILON, "X should be 1.6");
        assertEquals(0.6, solution[1], EPSILON, "Y should be 0.6");
    }

    @Test
    @DisplayName("4x4 System with Unique Solution")
    void testLarger4x4System() {
        // System with known solution: x=1, y=2, z=3, a=4
        double[][] matrix = {
                {1, 1, 1, 1, 10},   // x + y + z + a = 10
                {2, 1, 0, 1, 8},    // 2x + y + a = 8
                {1, 0, 1, 2, 12},   // x + z + 2a = 12
                {0, 1, 1, 1, 9}     // y + z + a = 9
        };

        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();

        assertNotNull(solution);
        assertEquals(4, solution.length);
        assertEquals(1.0, solution[0], EPSILON, "X should be 1.0");
        assertEquals(2.0, solution[1], EPSILON, "Y should be 2.0");
        assertEquals(3.0, solution[2], EPSILON, "Z should be 3.0");
        assertEquals(4.0, solution[3], EPSILON, "A should be 4.0");
    }

    // ========== INCONSISTENT SYSTEM TESTS ==========
    
    @Test
    @DisplayName("Inconsistent 2x2 System")
    void testInconsistent2x2System() {
        // System: x + y = 1, x + y = 2
        double[][] matrix = {
            {1, 1, 1},
            {1, 1, 2}
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(0, solution.length, "Inconsistent system should return empty array");
    }
    
    @Test
    @DisplayName("Inconsistent 3x3 System")
    void testInconsistent3x3System() {
        // System: x + y + z = 3, 2x + 2y + 2z = 6, x + y + z = 5
        double[][] matrix = {
            {1, 1, 1, 3},
            {2, 2, 2, 6},
            {1, 1, 1, 5}  // This makes it inconsistent
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(0, solution.length, "Inconsistent system should return empty array");
    }
    
    @Test
    @DisplayName("Inconsistent System with Zero Row")
    void testInconsistentZeroRow() {
        // System that reduces to 0 = 5 in one row
        double[][] matrix = {
            {1, 2, 3},
            {2, 4, 11}  // 2*(first row) would give 2x + 4y = 6, but we have 11
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(0, solution.length, "Inconsistent system should return empty array");
    }
    
    // ========== INFINITE SOLUTIONS TESTS ==========
    
    @Test
    @DisplayName("System with Infinite Solutions - Dependent Equations")
    void testInfiniteSolutions2x2() {
        // System: x + y = 3, 2x + 2y = 6 (same line)
        double[][] matrix = {
            {1, 1, 3},
            {2, 2, 6}
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNull(solution, "System with infinite solutions should return null");
    }
    
    @Test
    @DisplayName("Underdetermined System - More Variables than Equations")
    void testUnderdeterminedSystem() {
        // 2 equations, 3 variables: x + y + z = 5, 2x - y + z = 3
        double[][] matrix = {
            {1, 1, 1, 5},
            {2, -1, 1, 3}
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNull(solution, "Underdetermined system should return null");
    }
    
    @Test
    @DisplayName("3x3 System with Infinite Solutions")
    void testInfiniteSolutions3x3() {
        // System where one equation is a linear combination of others
        double[][] matrix = {
            {1, 2, 3, 14},
            {2, 1, 1, 11},
            {3, 3, 4, 25}  // This is sum of first two equations
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNull(solution, "System with infinite solutions should return null");
    }
    
    // ========== EDGE CASES AND SPECIAL MATRICES ==========
    
    @Test
    @DisplayName("Single Variable System")
    void testSingleVariable() {
        // System: 5x = 15
        double[][] matrix = {
            {5, 15}
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(1, solution.length);
        assertEquals(3.0, solution[0], EPSILON, "X should be 3.0");
    }
    
    @Test
    @DisplayName("Identity Matrix System")
    void testIdentityMatrix() {
        // System representing identity matrix
        double[][] matrix = {
            {1, 0, 0, 5},
            {0, 1, 0, 3},
            {0, 0, 1, 2}
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(3, solution.length);
        assertEquals(5.0, solution[0], EPSILON, "X should be 5.0");
        assertEquals(3.0, solution[1], EPSILON, "Y should be 3.0");
        assertEquals(2.0, solution[2], EPSILON, "Z should be 2.0");
    }
    
    @Test
    @DisplayName("System with Zero Solutions")
    void testZeroSolution() {
        // System: x + y = 0, 2x - y = 0
        // Solution: x = 0, y = 0
        double[][] matrix = {
            {1, 1, 0},
            {2, -1, 0}
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(2, solution.length);
        assertEquals(0.0, solution[0], EPSILON, "X should be 0.0");
        assertEquals(0.0, solution[1], EPSILON, "Y should be 0.0");
    }
    
    @Test
    @DisplayName("System Requiring Row Swapping")
    void testRowSwapping() {
        // System where pivot selection requires row swapping
        double[][] matrix = {
            {0, 1, 2},    // First element is 0, needs row swap
            {1, 1, 3}
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(2, solution.length);
        assertEquals(1.0, solution[0], EPSILON, "X should be 1.0");
        assertEquals(2.0, solution[1], EPSILON, "Y should be 2.0");
    }
    
    @Test
    @DisplayName("System with Very Small Coefficients")
    void testSmallCoefficients() {
        // System with coefficients close to zero
        double[][] matrix = {
            {0.000001, 1, 1.000001},
            {1, 0.000001, 1.000001}
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(2, solution.length);
        // Due to precision, we'll just check that we get a solution
        assertTrue(Math.abs(solution[0]) < 1e6, "X should be reasonable");
        assertTrue(Math.abs(solution[1]) < 1e6, "Y should be reasonable");
    }
    
    @Test
    @DisplayName("System with Large Coefficients")
    void testLargeCoefficients() {
        // System with large coefficients
        double[][] matrix = {
            {1000, 2000, 5000},
            {3000, 1000, 7000}
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(2, solution.length);
        // Verify the solution satisfies the original equations
        double eq1 = 1000 * solution[0] + 2000 * solution[1];
        double eq2 = 3000 * solution[0] + 1000 * solution[1];
        assertEquals(5000, eq1, 0.01, "First equation should be satisfied");
        assertEquals(7000, eq2, 0.01, "Second equation should be satisfied");
    }
    
    // ========== RECTANGULAR MATRIX TESTS ==========
    
    @Test
    @DisplayName("Overdetermined System - More Equations than Variables")
    void testOverdeterminedConsistent() {
        // 3 equations, 2 variables - consistent system
        double[][] matrix = {
            {1, 1, 3},    // x + y = 3
            {2, -1, 0},   // 2x - y = 0  
            {1, 2, 5}     // x + 2y = 5 (consistent with above)
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(2, solution.length);
        assertEquals(1.0, solution[0], EPSILON, "X should be 1.0");
        assertEquals(2.0, solution[1], EPSILON, "Y should be 2.0");
    }
    
    @Test
    @DisplayName("Overdetermined System - Inconsistent")
    void testOverdeterminedInconsistent() {
        // 3 equations, 2 variables - inconsistent system
        double[][] matrix = {
            {1, 1, 3},    // x + y = 3
            {2, -1, 0},   // 2x - y = 0
            {1, 2, 6}     // x + 2y = 6 (inconsistent with solution x=1, y=2)
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(0, solution.length, "Inconsistent overdetermined system should return empty array");
    }
    
    // ========== NUMERICAL STABILITY TESTS ==========
    
    @Test
    @DisplayName("System Testing Numerical Stability")
    void testNumericalStability() {
        // Hilbert-like matrix (known to be ill-conditioned)
        double[][] matrix = {
            {1.0, 0.5, 0.33333333, 1.833333},
            {0.5, 0.33333333, 0.25, 1.083333},
            {0.33333333, 0.25, 0.2, 0.783333}
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(3, solution.length);
        // For ill-conditioned matrices, we just verify we get some solution
        assertTrue(solution[0] > -1000 && solution[0] < 1000, "X should be reasonable");
        assertTrue(solution[1] > -1000 && solution[1] < 1000, "Y should be reasonable");
        assertTrue(solution[2] > -1000 && solution[2] < 1000, "Z should be reasonable");
    }

    @Test
    @DisplayName("System with Mixed Integer and Decimal Results")
    void testMixedResults() {
        // System: 2x + 4y = 10, 3x + 2y = 11
        // Solution: x = 3.0, y = 1.0
        double[][] matrix = {
                {2, 4, 10},
                {3, 2, 11}
        };

        GaussianElimination solver = new GaussianElimination(matrix);
        double[] solution = solver.solve();

        assertNotNull(solution);
        assertEquals(2, solution.length);
        assertEquals(3.0, solution[0], EPSILON, "X should be 3.0");
        assertEquals(1.0, solution[1], EPSILON, "Y should be 1.0");
    }

    // ========== VERIFICATION HELPER TESTS ==========
    
    @Test
    @DisplayName("Verify Solution Correctness for Complex System")
    void testSolutionVerification() {
        // Complex system to verify our solutions are correct
        double[][] originalMatrix = {
            {3, -2, 1, 1},
            {1, 1, -1, 3},
            {2, -1, 1, 2}
        };
        
        GaussianElimination solver = new GaussianElimination(originalMatrix);
        double[] solution = solver.solve();
        
        assertNotNull(solution);
        assertEquals(3, solution.length);
        
        // Verify solution by substituting back into original equations
        double eq1 = 3 * solution[0] - 2 * solution[1] + solution[2];
        double eq2 = solution[0] + solution[1] - solution[2];
        double eq3 = 2 * solution[0] - solution[1] + solution[2];
        
        assertEquals(1.0, eq1, EPSILON, "First equation should equal 1");
        assertEquals(3.0, eq2, EPSILON, "Second equation should equal 3");
        assertEquals(2.0, eq3, EPSILON, "Third equation should equal 2");
    }
    
    // ========== ERROR HANDLING TESTS ==========
    
    @Test
    @DisplayName("Test Steps String Generation")
    void testStepsGeneration() {
        double[][] matrix = {
            {1, 1, 3},
            {2, -1, 0}
        };
        
        GaussianElimination solver = new GaussianElimination(matrix);
        solver.solve();
        String steps = solver.getSteps();
        
        assertNotNull(steps);
        assertFalse(steps.isEmpty(), "Steps should not be empty");
        assertTrue(steps.contains("Gaussian Elimination"), "Steps should contain process description");
        assertTrue(steps.contains("Forward Elimination"), "Steps should contain forward elimination");
        assertTrue(steps.contains("Back Substitution"), "Steps should contain back substitution");
    }
}