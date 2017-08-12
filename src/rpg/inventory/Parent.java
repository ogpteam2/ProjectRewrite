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
     * Adds the given item to the content of this parent.
     * @param item
     *        Item to be added.
     * @throws InvalidItemException
     *         If the item for some reason cannot be added.
     *         Circumstances under which items can be added to be determined in implementation.
     * @throws NullPointerException
     *         If the item is a null reference.
     *       | item == null
     */
    void addItem(Item item) throws InvalidItemException, NullPointerException;

    /**
     * Drops the given item to the ground.
     * @param item
     *        Item to be dropped.
     * @throws InvalidItemException
     *         If the item does not contain the given item.
     *       | !contains(item)
     * @throws NullPointerException
     *         If the item is a null reference.
     *       | item == null
     */
    void dropItem(Item item) throws InvalidItemException, NullPointerException;

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
