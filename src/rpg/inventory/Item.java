package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.value.Unit;
import rpg.value.Weight;

/**
 * A superclass of items for all the items in the game.
 *
 * @invar An item's weight must be a valid instance of the weight class.
 *      | weight != null
 *
 * @author elias
 *
 */
public abstract class Item {

    /**
     * Creates a new item with the given value, weight and identifier.
     * @param value
     * @param weight
     *        The weight of this item.
     * @param identifier
     *
     * @post The weight of this item must be a valid instance of the
     *       Weight value class.
     * @note The value class Weight does all the heavy lifting for this
     *       postcondition. It is not possible to create a negative weight.
     *       Checking for a null reference is the only needed check here.
     */
	public Item(int value, Weight weight, long identifier){
		if(weight != null) this.weight = weight;
		else this.weight = Weight.kg_0;
		this.identifier = identifier;
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

	@Basic
	@Raw
	public long getIdentifier(){
		return this.identifier;
	}
}
