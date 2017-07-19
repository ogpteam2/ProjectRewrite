package rpg.utility;

/**
 * An implementation of the IDGenerator interface that generates binomial
 * coëfficients according to the following formula:
 * $$\sum^{n}_{i=0}{\binom{n}{i}}$$
 * The numbers that come out of this formula also happen to be powers of two.
 * For the sake of simplicity, this is the way this generator calculates
 * the numbers it generates.
 *
 * @invar Every number this generator generates adheres to the formula above.
 *        @see <a href="https://en.wikipedia.org/wiki/Pascal%27s_triangle#Rows">Informal proof</a>
 *
 * @author Elias Storme
 * @version 1.1
 */
public class BinomialGenerator implements IDGenerator {

    /*****************************
     * Constructor
     *****************************/

    /**
     * Creates a new Binomialgenerator.
     *
     * @effect Uses the reset method to initialise all values to their
     * initial state.
     * | reset()
     */
    public BinomialGenerator() {
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
     * @effect If there is no next sequential ID, reset the generator.
     * | if !hasNextID()
     * |      reset()
     */
    @Override
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
     * Resets the generator to it's initial state
     * @effect The state variable current is set to 1.
     */
    public void reset(){
        current = 1L;
    }

    long current;

    /*****************************
     * Calculation
     *****************************/

    /**
     * Calculates the sequentially next ID, advances the generator.
     *
     * @return The next ID. This is just the state variable.
     * | return current
     * @effect The state variable is multiplied by two.
     * | current = current * 2
     */
    private long nextID() {
        current *= 2;
        return current;
    }

    /**
     * Checks if a next ID can be generated.
     *
     * @return False if the next id would overflow the long variable it is contained in.
     * This is done by checking if the next calculated id is negative, as over-
     * flowing a long in java causes it to flip the sign bit.
     * | return rowSum(calculateNextRow()) &gt;= 0
     */
    public boolean hasNextID() {
        return current * 2 > 0;
    }
}
