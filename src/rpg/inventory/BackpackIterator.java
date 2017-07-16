package rpg.inventory;

import java.util.*;

//todo spec for class
/**
 * An implementation of the Enumeration interface for traversing the
 * items held within a Backpack.
 *
 * @author Elias Storme
 * @version 1.0
 */
public class BackpackIterator implements Enumeration<Item> {

    /*****************************
     * Constructor
     *****************************/
    //TODO formal spec for constructor
    /**
     * Creates a new BackpackIterator which will iterate over the given
     * content data structure.
     * @param content
     *        Content data structure of the backpack constructing this
     *        BackpackIterator.
     * @effect Sets the internal content attribute to the given one.
     * @effect The HashMap iterator is constructed for the content.
     * @effect The ArrayList iterator is constructed for the first element in
     * the HashMap iterator.
     */
    public BackpackIterator(HashMap<Long, ArrayList<Item>> content){
        this.content = content;
        hashMapIterator = content.values().iterator();
        arrayListIterator = hashMapIterator.next().iterator();
    }

    /*****************************
     * Content
     *****************************/
    //todo spec for content
    private HashMap<Long, ArrayList<Item>> content = null;

    /*****************************
     * Subiterators
     *****************************/

    /**
     * The Backpack class uses an ArrayList nested within a HashMap to store it's contents.
     * Each ArrayList is mapped to an identifier and contains the items with said identifier.
     * As such, the iterators of both of those classes can be used to iterate over all of
     * the contents of the backpack.
     */

    /**
     * Iterator for the HashMap.
     */
    private Iterator<ArrayList<Item>> hashMapIterator = null;

    /**
     * Iterator for the nested ArrayList.
     */
    private Iterator<Item> arrayListIterator = null;

    /*****************************
     * Interface methods
     *****************************/

    /**
     * Checks whether the iterator has a next item.
     * @return If the current arrayListIterator has a next item, return true.
     *         Else return whether the hashMapIterator has a next item (and as
     *         such can generate a new arrayListIterator containing at least one
     *         item.
     *       | if arrayListIterator.hasNext() return true
     *       | else return hashMapIterator.hasNext()
     */
    @Override
    public boolean hasMoreElements() {
        if(arrayListIterator.hasNext()){
            return true;
        } else {
            return hashMapIterator.hasNext();
        }
    }

    /**
     * Gets the next element in the iteration.
     * @return The next element.
     * @throws NoSuchElementException
     *         If the iterator has no more elements, throw exception.
     *       | if !hasMoreElements throw NoSuchElementException
     */
    @Override
    public Item nextElement() throws NoSuchElementException {
        if(!arrayListIterator.hasNext()){
            arrayListIterator = hashMapIterator.next().iterator();
        }
        return arrayListIterator.next();
    }
}
