package rpg.inventory.test;

import org.junit.Before;
import org.junit.Test;
import rpg.inventory.Weapon;
import rpg.value.Weight;

import static org.junit.Assert.*;

/**
 * Created by elias on 15/07/2017.
 */
public class WeaponTest {

    static Weapon one = null;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testConstructor() throws AssertionError{
        one = new Weapon(100, Weight.kg_0, 6);
        assertNull(one);
    }

}