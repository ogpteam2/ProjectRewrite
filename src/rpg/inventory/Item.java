package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.IDGeneration.IDGenerator;
import rpg.value.Weight;

/**
 * A superclass of items for all the items in the game.
 *
 * @invar An item's weight must be a valid instance of the weight class.
 *      | weight != null
 *
 * @author Elias Storme
 * @version 1.0
 *
 */
public abstract class Item {

    /**
     * Creates a new item with the given value, weight and identifier.
     * @param value
     * @param weight
     *        The weight of this item.
     *
     * @post The weight of this item must be a valid instance of the
     *       Weight value class.
     * @note The value class Weight does all the heavy lifting for the requirements
	 * 	     of the weight attribute. It is not possible to create an instance of weight
     * 	     with a negative numeral.
     *       Checking for a null reference is the only needed check here.
     */
	public Item(int value, Weight weight){
		if(weight != null) this.weight = weight;
		else this.weight = Weight.kg_0;
		this.identifier = getIDGenerator().generateID();
	}

	/*****************************
	 * Value
	 *****************************/

	/**
	 * Variable storing the value of this item. The value is expressed as
	 * an integer amount of ducats.
	 */
	private int value = 0;

	/**
	 * Calculates or returns the total value of this item.
	 * @return Total value of this item.
	 * @note The way this value is calculated is subclass specific and thus the method
	 * is abstract and spec remains open.
	 */
	public abstract int getValue();

	/**
	 *
	 * @return
	 */
	public abstract boolean canHaveAsValue(int value);


	/*****************************
	 * Weight - total
	 *****************************/

    /**
     * Retrieves the weight object representing this object's own weight.
     */
    @Basic @Raw @Immutable
	public Weight getWeight(){
		return this.weight;
	}

	/**
	 * Variable for storing the weight of this item.
	 */
	private final Weight weight;

	/*****************************
	 * Identifier
	 *****************************/

	private final long identifier;

    /**
     * Retrieves the identifier for this object.
     */
	@Basic @Raw @Immutable
	public long getIdentifier(){
		return this.identifier;
	}

    /**
     * Retrieves the IDGenerator implementation for this object.
     * Subclasses will each have their specific implementation in accordance
     * with what requirements there are for their respective identifiers.
     */
	abstract IDGenerator getIDGenerator();
}
