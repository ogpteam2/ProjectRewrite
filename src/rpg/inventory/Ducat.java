package rpg.inventory;

import rpg.IDGeneration.IDGenerator;
import rpg.value.Unit;
import rpg.value.Weight;

public class Ducat extends Item {

    public static Weight DUCAT_WEIGHT = new Weight(50, Unit.g);

    public Ducat() {
        super(1, DUCAT_WEIGHT);
        // TODO Auto-generated constructor stub
    }

    /**
     * The value of items uses a ducat as the unit value. The value of a ducat
     * must as such be fixed at 1.
     *
     * @return The value of a ducat: one.
     * | return 1
     */
    public int getValue() {
        return this.value;
    }

    /*****************************
     * Identification
     *****************************/

    @Override
    IDGenerator getIDGenerator() {
        return idGen;
    }

    /**
     * IDGenerator for the ducat class.
     *
     * This is not a requirement of the specification. My choice to give ducat an ID is for
     * ease of use within the backpack content datastructure.
     *
     * Giving Ducat the ID -1, which no other item will ever have as it doesn't meet their
     * specification, makes it easy to retrieve all ducats within the backpack in an easy way.
     * Additionally, giving Ducat an object that implements the IDGenerator interface makes
     * for an elegant way to implement id generation at the highest level of abstraction, the
     * item class.
     *
     * @note The expression below realises an implementation of the IDGenerator interface using
     * a lambda expression. I used this because the implementation is so incredibly simple.
     */
    private static IDGenerator idGen = () -> -1;
}
