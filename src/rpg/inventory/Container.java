package rpg.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import rpg.InvalidItemException;

/**
 * A superclass of containers to contain a set of items.
 * @author elias
 *
 */
public abstract class Container extends Item{

	public Container(int value) {
		super(value);
		
	}
	
	/*****************************
	 * Content - defensive
	 *****************************/
	
	
	
	/**
	 * Checks whether the prime object can contain the given item
	 * @param item
	 * 		  Item to check.
	 * @return Conditions to be specified in subclasses.
	 */
	public abstract boolean canHaveAsContent(Item item);
	
	
	/**
	 * Adds the given item to the prime object
	 * @param item
	 * 		  Item to be added
	 * @throws InvalidItemException
	 * 		   If the item will cause the 
	 */
	public void addToContent(Item item) throws InvalidItemException{
		
	}
	
	/*****************************
	 * Capacity
	 *****************************/
	
	/**
	 * Checks whether adding the 
	 * @return
	 */
	protected boolean exceedsCapacity(){
		return false;
	}
	
}
