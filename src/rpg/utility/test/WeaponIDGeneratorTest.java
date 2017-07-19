package rpg.utility.test;

import org.junit.Before;
import org.junit.Test;
import rpg.utility.WeaponIDGenerator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class WeaponIDGeneratorTest {

    WeaponIDGenerator idGen;

    @Before
    public void setUp() {
        idGen = new WeaponIDGenerator();
    }

    @Test
    public void correctnessTest() {
        long[] expected = new long[50];
        long[] generated = new long[50];
        for (int i = 0; i < 50; i++) {
            expected[i] = (i+1)*6;
            generated[i] = idGen.generateID();
        }
        assertArrayEquals(expected,generated);
    }

    @Test
    public void stressTest() {
        for (int i = 0; i < 1E+9; i++) {
            idGen.generateID();
        }
        assertTrue(idGen.generateID()>= 0);
    }
}
