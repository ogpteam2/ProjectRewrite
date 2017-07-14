package rpg.IDGeneration;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * An implementation of the IDGenerator interface that generates primes.
 *
 * @invar Every number generated shall only have one and itself as its
 * positive integer divisors.
 *
 * @author Elias Storme
 * @version 1.1
 */
public class PrimeGenerator implements IDGenerator {

	/*****************************
	 * Constructor
	 *****************************/

	/**
	 * Creates a new PrimeGenerator.
	 *
	 * @effect Uses the reset method to initialise the position
     * state variable to it's initial state.
	 * | reset()
     * @effect Adds 2 to the ArrayList in order to have a reference
     * to calculates other primes off.
	 */
	public PrimeGenerator(){
		primeSet = new TreeSet<>();
		reset();
		primeSet.add(2L);
	}

    /*****************************
     * Interface method
     *****************************/

    //TODO finish reimplementation with treeset

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
	public long generateID(){
		if(!hasNextID()){
			reset();
		}
		return nextID();
	}

    /*****************************
     * State variable and mutator
     *****************************/

    private void reset(){
        IDIterator = primeSet.iterator();
    }

    private Iterator<Long> IDIterator;

    private TreeSet<Long> primeSet;

    /*****************************
     * Calculation
     *****************************/

    /**
     * Checks if a next ID can be generated.
     *
     * @return False if the next id would overflow the long variable it is contained in.
     * This is done by checking if the next calculated id is negative, as over-
     * flowing a long in java causes it to flip the sign bit.
     * | return generateNextPrime(primeList) &gt;= 0
     */
	public boolean hasNextID() {
		return generateNextPrime(primeSet) > 0;
	}

    /**
     * Returns the sequentially next ID.
     * @effect Advances the position by one.
     * @return The next ID.
     * | return getPrime(position - 1)
     */
	public long nextID() {
		if(IDIterator.hasNext()) return IDIterator.next();
		else{
		    primeSet.add(generateNextPrime(primeSet));
		    IDIterator = primeSet.iterator();
            for (int i = 0; i < primeSet.size() - 1; i++) {
                IDIterator.next();
            }
            return IDIterator.next();
        }
	}
	
	/**
	 * Generates the smallest prime larger than all primes in the given array.
	 * @param previousPrimes
	 * 			TreeSet with previous primes.
	 * @pre previousPrimes has to be a complete list of primes.
	 * @return The next prime, larger than all ones in the given array.
     * | let p:
     * |    for each q in previousPrimes:
     * |        p > q
     * |        p mod q != 0
     * | then:
     * |    return p
	 */
	private long generateNextPrime(TreeSet<Long> previousPrimes){
		long current = previousPrimes.last();
		SEARCH:
		while(true){
			current++;
			for(long p : previousPrimes){
				if (current % p == 0) continue SEARCH;
			}
			return current;
		}
	}

    /**
     * Checks if the given number is a prime.
     * @param number
     *        The long to be checked.
     * @effect If the largest prime in the primeList is smaller
     * than the given number, generate numbers until the largest is
     * larger or equal to the given number.
     * | while(
     * @return Whether number exists in the list of primes.
     * | return primeList.contains(number)
     */
	public boolean isPrime(long number){
		while(number > primeSet.last()){
		    primeSet.add(generateNextPrime(primeSet));
        }
        return primeSet.contains(number);
	}
	
	public long closestPrime(long number){
		if(isPrime(number)) return number;
		else{
			long ceiling = primeSet.ceiling(number);
			long floor = primeSet.floor(number);
			if((number - floor) > (ceiling - number)){
			    return ceiling;
            } else {
			    return floor;
            }
		}
	}
}
