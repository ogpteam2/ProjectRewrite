package rpg.inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class Backpack extends Container {

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
	
	/**
	 * Data structure for storing references to the items in this container.
	 * 
	 * @note Specification dictates that retrieval of all items with the same identifier
	 * 		 has to happen in constant time. 
	 * 		 Retrieval of items with the same id must happen in linear time.
	 * 		 Thus a hashmap (search O(1)) maps an identifier to an arraylist (search O(n)) 
	 * 		 with items with the same identifier 
	 */
	private HashMap<Long, ArrayList<Item>> contents;
	
}
