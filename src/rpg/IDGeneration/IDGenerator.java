package rpg.IDGeneration;

/**
 * An interface implementing IDGenerators for the items.
 *
 * @author Robbe, Elias
 */
public interface IDGenerator {

    /**
     * Generates an ID in sequence.
     *
     * @return
     */
    long generateID();
}
