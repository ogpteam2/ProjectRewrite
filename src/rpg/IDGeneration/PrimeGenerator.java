package rpg.IDGeneration;

import java.util.ArrayList;

/**
 * An implementation of the IDGenerator interface that generates primes.
 *
 *
 * @author Elias Storme
 * @version 1.1
 */
public class PrimeGenerator implements IDGenerator {

	/*****************************
	 * Constructor
	 *****************************/

	/**
	 * Creates a new Primegenerator.
	 *
	 * @effect Uses the reset method to initialise the position
     * state variable to it's initial state.
	 * | reset()
     * @effect Adds 2 to the arraylist in order to have a reference
     * to calculates other primes off.
	 */
	public PrimeGenerator(){
		reset();
		primeList.add(2L);
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

    /**
     * Resets the position to the first known prime.
     *
     * @effect Resets the position
     * 		 | position = 0
     */
    public void reset(){
        this.position = 0;
    }

    /**
     * State variable defining the position in the arraylist of primes.
     */
    private int position;

    /**
     * Arraylist storing all primes that have once been generated by this
     * primegenerator.
     */
    private ArrayList<Long> primeList = new ArrayList<>();

    /*****************************
     * Calculation
     *****************************/

	/**
	 * There are an infinite amount of prime numbers, so in theory this method should always be true.
	 * Java's long primitive does have a finite size though, so 
	 */

	public boolean hasNextID() {
		return true;
	}

	public long nextID() {
		position++;
		return getPrime(position - 1);
	}
	
	public long getPrime(int position){
		while(position > primeList.size() - 1){
			primeList.add(generateNextPrime(primeList));
		}
		return primeList.get(position);
	}
	
	/**
	 * Generates the smallest prime larger than all primes in the given array.
	 * @param previousPrimes
	 * 			Array with a list of primes to check candidate primes against.
	 * @pre previousPrimes has to be a complete list of primes. 
	 * @return the next prime
	 */
	private long generateNextPrime(ArrayList<Long> previousPrimes){
		long current = previousPrimes.get(previousPrimes.size()-1);
		SEARCH:
		while(true){
			current++;
			for(long p : previousPrimes){
				if (current % p == 0) continue SEARCH;
			}
			return current;
		}
	}
	
	public boolean isPrime(long number){
		System.out.println(number > primeList.get(position));
		System.out.println(nextID());
		while(number > getPrime(position)){
			nextID();
		}
		return primeList.contains(number);
	}
	
	public long closestPrime(long number){
		if(isPrime(number)) return number;
		else{
			int i = 0;
			while(getPrime(i) < number){
				i++;
			}
			long lower = getPrime(i - 1);
			long higher = getPrime(i);
			return ((number - lower) > (higher - number)) ? higher : lower;
		}
	}
}
