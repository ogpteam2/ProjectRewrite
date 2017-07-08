package rpg.inventory;

import java.util.ArrayList;
import java.util.HashMap;

import rpg.InvalidItemException;

/**
 * A superclass of containers to contain a set of items.
 * @author elias
 *
 */
public abstract class Container extends Item implements Holder{

	public Container(int value, long identifier) {
		super(value, identifier);

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
	 * Adds the given item to the prime object.
	 * @param item
	 * 		  Item to be added.
	 * @effect Item gets a reference 
	 * @effect Prime object gets reference to item
	 * @throws InvalidItemException
	 * 		   If the container cannot contain this item an InvalidItemException is thrown
	 * 		 | !canHaveAsContent(Item item)
	 */
	public void addToContent(Item item) throws InvalidItemException{
		
	}
	
	/**
	 * 
	 * @param item
	 * @throws InvalidItemException
	 */
	public void removeFromContent(Item item) throws InvalidItemException{
		
	}
	
	/*****************************
	 * Capacity
	 *****************************/
	
	/**
	 * Checks whether adding the 
	 * @return
	 */
	protected boolean exceedsCapacity(Item item){
		return false;
	}
	
}
