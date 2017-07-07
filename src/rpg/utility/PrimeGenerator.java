package rpg.utility;

import java.util.ArrayList;

public class PrimeGenerator implements IDGenerator {
	
	private int position;
	
	private ArrayList<Long> primeList = new ArrayList<Long>();
	
	/**
	 * Iterator that generates consecutive primes.
	 */
	
	public PrimeGenerator(){
		position = 0;
		primeList.add(2L);
	}
	
	@Override
	public long generateID(){
		if(!hasNextID()){
			reset();
		}
		return nextID();
	}
	
	/**
	 * There are an infinite amount of prime numbers, so in theory this method should always be true.
	 * Java's long primitive does have a finite size though, so 
	 */
	
	@Override
	public boolean hasNextID() {
		return true;
	}
	
	
	@Override
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
	 * Resets the position to the first known prime.
	 * 
	 * @effect Resets the position
	 * 		   position = 0
	 */
	public void reset(){
		this.position = 0;
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
