package rpg.inventory;

import rpg.InvalidItemException;
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

    void addToContent(Item item) throws InvalidItemException;

    void removeFromContent(Item item) throws InvalidItemException;
}
