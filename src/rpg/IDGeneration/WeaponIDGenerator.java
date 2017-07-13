package rpg.IDGeneration;

/**
 * An implementation of the IDGenerator interface that generates positive
 * multiples of 6.
 *
 * @invar Every number generated shall be positive.
 * @invar Every number generated shall be divisible by 2.
 * @invar Every number generated shall be divisible by 3.
 *
 * @author Elias Storme
 * @version 1.1
 */
public class WeaponIDGenerator implements IDGenerator {

    /*****************************
     * Constructor
     *****************************/

    /**
     * Creates a new WeaponIDGenerator.
     *
     * @effect Uses the reset method to initialise all values to their
     * initial state.
     * | reset()
     */
    public WeaponIDGenerator() {
        reset();
    }

    /*****************************
     * Interface method
     *****************************/

    /**
     * Generates an ID.
     *
     * @return The next sequential ID
     * | return nextID()
     * @effect Advances the generator one step.
     * @effect If there is no sequential next ID, reset the generator.
     * | if !hasNextID()
     * |      reset()
     */
    public long generateID() {
        if (!hasNextID()) {
            reset();
        }
        return nextID();
    }

    /*****************************
     * State variable and mutator
     *****************************/

    /**
     * Resets the generator to it's initial state. nextID() will generate
     * an ID as if the generator was just initialised.
     *
     * @effect counter is reset to 0.
     */
    public void reset() {
        this.counter = 0;
    }

    /**
     * Counter that is to be multiplied by six in order to calculate an ID.
     * Incrementing this counter each time an ID is calculated ensures every
     * multiple of 6 can be reached.
     */
    private long counter;

    /*****************************
     * Calculation
     *****************************/

    /**
     * Calculates the sequentially next ID, advances the generator.
     *
     * @effect Advances the counter
     * | counter = counter + 1
     * @return The next ID. This is the counter multiplied by 6.
     * | return counter * 6
     */
    public long nextID() {
        counter++;
        return counter * 6;
    }


    /**
     * Checks if a next ID can be generated.
     *
     * @return False if the next id would overflow the long variable it is contained in.
     * This is done by checking if the next calculated id is negative, as over-
     * flowing a long in java causes it to flip the sign bit.
     * | return counter * 6 &gt;= 0
     */
    public boolean hasNextID() {
        return (counter * 6) >= 0;
    }

}
