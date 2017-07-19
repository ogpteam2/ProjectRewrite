package rpg.utility.test;

import org.junit.Before;
import org.junit.Test;
import rpg.utility.BinomialGenerator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class BinomialGeneratorTest {

    BinomialGenerator idGen;

    @Before
    public void setUp() {
        idGen = new BinomialGenerator();
    }

    @Test
    public void correctnessTest() {
        long[] expected = new long[50];
        long[] generated = new long[50];
        for (int i = 0; i < 50; i++) {
            expected[i] = (long) Math.pow(2, i+1);
            generated[i] = idGen.generateID();
        }
        assertArrayEquals(expected, generated);
    }

    @Test
    public void stressTest() {
        for (int i = 0; i < 1E+9; i++) {
            idGen.generateID();
        }
        assertTrue(idGen.generateID() >= 0);
    }
}
