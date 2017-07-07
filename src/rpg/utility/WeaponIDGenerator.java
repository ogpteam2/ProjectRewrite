package rpg.utility;

public class WeaponIDGenerator implements IDGenerator {

	private long counter;
	
	/**
	 * An IDGenerator that generates multiples of six to be used as id for the weapon class.
	 * With the ID being a multiple of 6 we are certain it is even, aka a multiple of 2, and a multiple of three.
	 * @invar the generated ID will always be even and a multiple of three.
	 * 		| (ID%6 == 0)
	 */
	
	public WeaponIDGenerator() {
		reset();
	}
	
	@Override
	public long generateID(){
		if(!hasNextID()){
			reset();
		}
		return nextID();
	}
	
	/**
	 * Calculates an ID that is a multiple of six.
	 * @return next ID in the sequence.
	 */
	
	@Override
	public long nextID() {
		counter++;
		return counter*6;
	}

	
	/**
	 * Indicates if the generator can generate another ID.
	 * 
	 * The list of multiples of threes is infinite, so there should always be a next triplicate.
	 * However, the dimensions of the java long variable are finite, so when there is an overflow there
	 * are no next ID's to be generated.
	 * 
	 * @return true if a new ID can be generated.
	 */
	
	@Override
	public boolean hasNextID() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Resets the generator to it's initial state. nextID() will generate
	 * an ID as if the generator was just initialised.
	 * @effect counter is reset to 0.
	 */
	@Override
	public void reset() {
		this.counter = 0;
	}

}
