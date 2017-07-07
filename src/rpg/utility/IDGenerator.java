package rpg.utility;

/**
 * An interface implementing IDGenerators for the items.
 * 
 * @author Robbe, Elias
 */
public interface IDGenerator {
	
	/**
	 * Calculates an ID based on the specification.
	 * 
	 * @return next ID in the sequence.
	 */
	public long nextID();
	
	/**
	 * Indicates if the generator can generate another ID.
	 *
	 * @return true if a new ID can be generated.
	 */
	public boolean hasNextID();
	
	/**
	 * Resets the generator to it's initial state. nextID() will generate
	 * an ID as if the generator was just initialised.
	 * @post The IdGenerates is reset.
	 */
	public void reset();
	
	/**
	 * Checks if there is an next ID, if not reset.
	 * 
	 * @return The next ID if there is one.
	 * @return The first one if there isn't one.
	 */
	public long generateID();
}
