package rpg.utility.test;

import org.junit.Before;
import org.junit.Test;
import rpg.utility.FibonacciGenerator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class FibonacciGeneratorTest {

    public long[] correctFibonacci = {1, 1, 2, 3, 5, 8, 13, 21};
    public FibonacciGenerator gen;

    @Before
    public void setUp() {
        gen = new FibonacciGenerator();
    }

    @Test
    public void correctnessTest() {
        long[] generatedFibonacci = new long[8];
        for (int i = 0; i < 8; i++) {
            generatedFibonacci[i] = gen.nextID();
        }
        assertArrayEquals(correctFibonacci, generatedFibonacci);
    }

    @Test
    public void stressTest() {
        for (int i = 0; i < 1E+9; i++) {
            gen.generateID();
        }
        assertTrue(gen.generateID()>= 0);
        System.out.println(gen.generateID());
    }

}
