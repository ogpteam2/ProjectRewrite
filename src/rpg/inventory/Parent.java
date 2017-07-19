package rpg.inventory;

import rpg.exception.InvalidItemException;
import rpg.Mobile;

/**
 * Created by elias on 08/07/17.
 */
public interface Parent {

    /**
     * Retrieves the holder of this parent item.
     *
     * @return The mobile that is holding this item, directly
     * or indirectly.
     */
    Mobile getHolder();

    /**
     *
     * @param item
     * @throws InvalidItemException
     */
    void addItem(Item item) throws InvalidItemException;

    /**
     * Drops the given item to the ground.
     * @param item
     *        Item to be dropped.
     * @throws InvalidItemException
     *         If the item does not contain the given item, exception is thrown.
     *       | if !contains(item) throw InvalidItemException
     */
    void dropItem(Item item) throws InvalidItemException;

    /**
     * Checks whether the parent contains the given item.
     * @param item
     *        Item to look for.
     */
    boolean contains(Item item);

    /**
     * Checks if adding the given item will exceed the capacity of the holder or
     * any of the parents.
     * @param item
     *        Item to check.
     */
    boolean exceedsCapacity(Item item);
}
