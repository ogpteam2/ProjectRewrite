package rpg.inventory;

import be.kuleuven.cs.som.annotate.Raw;
import rpg.IDGeneration.BinomialGenerator;
import rpg.IDGeneration.IDGenerator;
import rpg.value.Weight;

import java.util.ArrayList;
import java.util.HashMap;

public class Backpack extends Container implements Parent {

    private static BinomialGenerator idGen = new BinomialGenerator();

    public Backpack(int value, Weight weight) {
        super(value, weight, idGen.nextID());
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
    IDGenerator getIDGenerator() {
        return null;
    }


    /*****************************
     * Content
     *****************************/

    /**
     * Data structure for storing references to the items in this container.
     *
     * @note Specification dictates that retrieval of all items with the same identifier
     * has to happen in constant time.
     * Retrieval of items with the same id must happen in linear time.
     * Thus a hashmap (search O(1)) maps an identifier to an arraylist (search O(n))
     * with items with the same identifier
     */
    private HashMap<Long, ArrayList<Item>> content;

    /**
     * Adds reference to the content datastructure for this item.
     *
     * @param item Item to be added.
     */
    @Raw
    private void putItem(Item item) {

    }

    /**
     * Removes the reference to this item from the content data structure.
     *
     * @param item
     */
    @Raw
    private void removeItem(Item item) {

    }

    /*****************************
     * Value
     *****************************/

    /**
     * Sets the value for this backpack.
     *
     * @pre Value has to be larger than zero and smaller than 500.
     */
    public void setValue() {

    }

    /*****************************
     * Weight - total
     *****************************/

    public Weight getDefaultWeight() {
        return null;
    }

    public boolean isValidWeight(Weight weight) {
        return false;
    }

    @Override
    public Weight getWeightOfContents() {
        return null;
    }

    /*****************************
     * Holder
     *****************************/

    /**
     * Retrieves the holder of this container.
     * @return If the parent of this container is null, it means the container
     *         is lying on the ground. As a result it cannot have a holder so
     *         a null reference must be returned.
     *       | if parent == null return null
     *         If the container does have a parent, the parent is queried for it's
     *         holder.
     *       | else return parent.getHolder()
     */

}
