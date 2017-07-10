package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.value.Unit;
import rpg.value.Weight;

/**
 * A superclass of items for all the items in the game.
 * @author elias
 *
 */
public abstract class Item {

	public Item(int value, Weight weight, long identifier){
		this.weight = weight;
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
	 * Gets the default weight for the item.
	 * @note To be used when the given weight is not effective.
	 * @return A default value for the weight of this item. The concrete value
	 *         is to be determined on the subclass level.
	 */
	public abstract Weight getDefaultWeight();

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
