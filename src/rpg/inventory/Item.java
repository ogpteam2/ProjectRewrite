package rpg.inventory;


/**
 * A superclass of items for all the items in the game.
 * @author elias
 *
 */
public abstract class Item {
	
	
	public Item(int value){
		
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
}
