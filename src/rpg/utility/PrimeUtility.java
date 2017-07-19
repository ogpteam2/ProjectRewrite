package rpg.utility;

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
public class PrimeUtility {

	/*****************************
	 * Constructor
	 *****************************/

	/**
	 * Creates a new PrimeUtility.
	 *
	 * @effect Uses the reset method to initialise the position
     * state variable to it's initial state.
	 * | reset()
     * @effect Adds 2 to the ArrayList in order to have a reference
     * to calculates other primes off.
	 */
	public PrimeUtility(){
		primeSet = new TreeSet<>();
		primeSet.add(2L);
	}

	/*****************************
	 * Storage
	 *****************************/

	/**
	 * Sorted set storing all the primes this utility has generated.
     * Set is sorted according to natural order.
	 */
    private TreeSet<Long> primeSet;

    /*****************************
     * Calculation
     *****************************/
	
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
