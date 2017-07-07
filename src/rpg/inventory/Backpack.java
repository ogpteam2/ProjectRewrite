package rpg.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;
import rpg.utility.BinomialGenerator;

public class Backpack extends Container {

	private static BinomialGenerator idGen = new BinomialGenerator();
	
	public Backpack(int value, long identifier) {
		super(value, idGen.nextID());
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canHaveAsContent(Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canHaveAsValue() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*****************************
	 * Content
	 *****************************/
	
	/**
	 * Data structure for storing references to the items in this container.
	 * 
	 * @note Specification dictates that retrieval of all items with the same identifier
	 * 		 has to happen in constant time. 
	 * 		 Retrieval of items with the same id must happen in linear time.
	 * 		 Thus a hashmap (search O(1)) maps an identifier to an arraylist (search O(n)) 
	 * 		 with items with the same identifier 
	 */
	private HashMap<Long, ArrayList<Item>> content;
	
	/**
	 * Adds reference to the content datastructure for this item.
	 * @param item
	 * 		  Item to be added.
	 */
	@Raw
	private void putItem(Item item){
		
	}
	
	/**
	 * Removes the reference to this item from the content data structure.
	 * @param item
	 */
	@Raw
	private void removeItem(Item item){
		
	}
	
	/*****************************
	 * Value
	 *****************************/
	
	/**
	 * Sets the value for this backpack.
	 * @pre Value has to be larger than zero and smaller than 500.
	 */
	public void setValue(){
		
	}
	
}
