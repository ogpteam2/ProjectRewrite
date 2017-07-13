package rpg.IDGeneration;

/**
 * An implementation of the IDGenerator interface generating numbers
 * from Fibonacci's sequence.
 *
 * @invar Every number generated is the sum of the two previous ones.
 * $$F_n = F_{n-1} + F_{n-2}$$
 * @invar The first and second numbers are 1.
 * $$F_1 = F_2 = 1$$
 *
 * @author Elias Storme
 * @version 1.1
 */
public class FibonacciGenerator implements IDGenerator {

    /*****************************
     * Constructor
     *****************************/

    /**
     * Creates a new Fibonacci generator instance.
     * @effect Uses the reset method to initialise both state variables.
     * | reset()
     */
    public FibonacciGenerator() {
        reset();
    }

    /*****************************
     * Interface method
     *****************************/

    /**
     * Generates and returns a number from Fibonacci's sequence.
     * @return A number from Fibonacci's sequence.
     * @effect If the generator cannot generate another sequential number, resets
     *         the generator to it's initial state.
     *       | if(!hasNextID()) reset()
     * @effect Advances the generator one step.
     *       | nextID()
     */
    @Override
    public long generateID() {
        if (!hasNextID()) {
            reset();
        }
        return nextID();
    }

    /*****************************
     * Calculation
     *****************************/

    /**
     * Calculates the next sequential Fibonacci number from the two
     * previous ones.
     * @return The next Fibonacci number.
     * @effect secondNumber is assigned the value of the sum of itself
     * and firstNumber.
     * @effect The number firstNumber is assigned the value of secondNumber.
     */
    public long nextID() {
        long nextFirst = 0;
        long nextSecond = 0;
        if(secondNumber == 0){
            nextSecond = 1;
        } else {
            nextFirst = secondNumber;
            nextSecond = firstNumber + secondNumber;
        }
        firstNumber = nextFirst;
        secondNumber = nextSecond;
        return nextSecond;
    }

    /**
     * Checks if a next ID can be generated.
     *
     * @return False if the next id would overflow the long variable it is contained in.
     * This is done by checking if the next calculated id is negative, as over-
     * flowing a long in java causes it to flip the sign bit.
     * | return (firstNumber + secondNumber) >= 0
     */
    public boolean hasNextID() {
        return (firstNumber + secondNumber) >= 0;
    }

    /*****************************
     * State variables and mutator
     *****************************/

    /**
     * Resets the generator.
     * @effect Reinitialises both state variables to their default values of zero.
     * | firstNumber = 0
     * | secondNumber = 0
     */
    public void reset() {
        firstNumber = 0;
        secondNumber = 0;
    }

    /**
     * State variables for the generator.
     * They represent two sequential numbers in Fibonacci's sequence.
     */
    private long firstNumber;
    private long secondNumber;

}
