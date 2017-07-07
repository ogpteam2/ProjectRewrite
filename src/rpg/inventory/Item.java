package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.value.Weight;

/**
 * A superclass of items for all the items in the game.
 * @author elias
 *
 */
public abstract class Item {
	
	
	public Item(int value, long identifier){
		this.identifier = identifier;
	}
	
	/*****************************
	 * Value
	 *****************************/
	
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
	public abstract boolean canHaveAsValue();
	
	
	/*****************************
	 * Weight
	 *****************************/
	
	private Weight weight;
	
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
