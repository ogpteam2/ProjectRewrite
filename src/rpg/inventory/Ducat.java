package rpg.inventory;

import rpg.utility.IDGenerator;
import rpg.value.Unit;
import rpg.value.Weight;

import java.util.Stack;

/**
 * A class of ducats to be used as currency in the game.
 *
 * @invar The value of a ducat shall always be equal to one, as it is
 * to be used as a monetary unit.
 * | getOwnValue() == 1
 */
public class Ducat extends Item {

    /**
     * Creates a new ducat.
     *
     * @effect Uses the superclass constructor to set value and weight
     * to the predefined constants for a ducat.
     * |
     */
    public Ducat() {
        super(1, DUCAT_WEIGHT);
    }

    /**
     * Creates a stack containing the given amount of ducats.
     * @param amount
     *        Amount of ducats the stack has to contain.
     * @return A stack of length amount containing ducats
     * |
     */
    public Stack<Ducat> Ducat(int amount){
        Stack<Ducat> ducats = new Stack<>();
        for (int i = 0; i < amount; i++) {
            ducats.add(new Ducat());
        }
        return ducats;
    }

    /*****************************
     * Value
     *****************************/

    /**
     * The value of items uses a ducat as the unit value. The value of a ducat
     * must as such be fixed at 1.
     *
     * @return The value of a ducat: one.
     * | return 1
     */
    public int getOwnValue() {
        return this.value;
    }

    /*****************************
     * Identification
     *****************************/

    /**
     * Getter for the ducat's implementation of the IDGenerator interface.
     */
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

    /*****************************
     * Weight
     *****************************/

    /**
     * Constant declaring the weight of one ducat.
     */
    public static Weight DUCAT_WEIGHT = new Weight(50, Unit.g);
}
