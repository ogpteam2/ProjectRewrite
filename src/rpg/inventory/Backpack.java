package rpg.inventory;

import be.kuleuven.cs.som.annotate.Raw;
import rpg.IDGeneration.BinomialGenerator;
import rpg.IDGeneration.IDGenerator;
import rpg.exception.InvalidItemException;
import rpg.value.Weight;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class Backpack extends Container implements Parent {

    public Backpack(int value, Weight weight, Weight capacity) {
        super(value, weight, capacity);
        // TODO Auto-generated constructor stub
    }


    @Override
    public int getValue() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*****************************
     * Identification
     *****************************/

    @Override
    IDGenerator getIDGenerator() {
        return idGen;
    }

    private static BinomialGenerator idGen = new BinomialGenerator();

    /*****************************
     * Content
     *****************************/


    public boolean contains(Item item){

    }

    public void addItem(Item item) throws InvalidItemException{

    }

    public void dropItem(Item item) throws InvalidItemException{

    }

    public Enumeration<Item> iterator(){
        return new BackpackIterator(getContent());
    }

    /**
     * Adds reference to the content datastructure for this item.
     * @pre The backpack can accept this item into it's contents.
     * | canHaveAsContent(item)
     * @param item Item to be added.
     */
    @Raw
    private void putItem(Item item) {
        if(!content.containsKey(item.getIdentifier())) {
            content.put(item.getIdentifier(),
                    new ArrayList<>());
        }
        content.get(item.getIdentifier()).add(item);
    }

    /**
     * Removes the reference to this item from the content data structure.
     *
     * @param item
     */
    @Raw
    private void removeItem(Item item) {

    }

    /**
     * Makes a shallow copy of the content datastructure of this backpack and returns it.
     * Shallow copying ensures the content of the backpack cannot be modified outside of
     * the backpack class, but the references to the items stay consistent.
     * @return
     */
    private HashMap<Long, ArrayList<Item>> getContent(){
        return (HashMap<Long, ArrayList<Item>>) this.content.clone();
    }

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

}
