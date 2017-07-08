package rpg.inventory;

import rpg.Mobile;

/**
 * An interface specifying how an item should handle its holder.
 * @author elias
 *
 */
public interface Holder {

	/**
	 * Fetches the Mobile which is holding this item.
	 *
	 * @return Mobile holder
	 * 		  The holder of this item.
	 */
	public Mobile getHolder();
}
