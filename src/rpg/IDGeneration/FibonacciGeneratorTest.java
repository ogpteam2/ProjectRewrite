package rpg.IDGeneration;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

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
        gen.reset();
        for (int i = 0; i < 1000; i++) {
            gen.nextID();
        }
    }

}
