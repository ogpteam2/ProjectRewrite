package rpg.inventory;

import be.kuleuven.cs.som.annotate.Raw;
import org.omg.CORBA.DynAnyPackage.Invalid;
import rpg.utility.BinomialGenerator;
import rpg.utility.IDGenerator;
import rpg.exception.InvalidItemException;
import rpg.value.Weight;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Backpack extends Container implements Parent {

    public Backpack(int ownValue, Weight weight, Weight capacity) {
        super(weight, capacity);
        setOwnValue(ownValue);
    }

    /*****************************
     * Identification
     *****************************/

    @Override
    IDGenerator getIDGenerator() {
        return idGen;
    }

    private static BinomialGenerator idGen = new BinomialGenerator();

    /**********************************
     * 4.2: Content - Inspectors
     **********************************/

    /**
     * Checks if the given item can be added to the content of the backpack.
     * @param item
     *        Item to be added
     * @return False if the item is null, will exceed the capacity or is already
     *         in the backpack.
     */
    public boolean canHaveAsContent(Item item){
        return item!=null && !exceedsCapacity(item) && !contains(item);
    }

    /**
     * Checks if adding the item to the content of this backpack would exceed
     * the capacity of something in the chain of parents holding it.
     * @param item
     *        Item to check.
     * @return True if
     * todo spec
     */
    @Override
    public boolean exceedsCapacity(Item item){
        boolean exceedsThis = false;
        if (item == null) {
            exceedsThis = false;
        } else {
            Weight itemWeight = item.getWeight();
            Weight current = getWeightOfContents();
            exceedsThis = exceedsCapacity(current.add(itemWeight));
        }
        return exceedsThis || getParent().exceedsCapacity(item);
    }

    /**
     * Counts all the items contained in the backpack.
     * @return Takes the sum of the sizes of all the ArrayList in the content
     * HashMap
     * | let: sum = 0
     * | for each list in content:
     * |    sum = sum + list.size
     * | return sum
     */
    @Override
    public int getNbOfItems() {
        int count = 0;
        for (ArrayList<Item> e:
                getContent().values()) {
            count += e.size();
        }
        return count;
    }

    /**
     * Checks if the given item exists in the backpack.
     * @param item
     *        Item to look for.
     * @return If no entry with the id of the given item exists, return false.
     * | if content.get(item.id) == null return false
     *         Else, if the item exists in the ArrayList associated with
     *         it's id, return true.
     * | return content.get(item.id).contains(item)
     */
    public boolean contains(Item item){
        ArrayList<Item> list = getContent().get(item.getIdentifier());
        if(list == null){
            return false;
        } else {
            return list.contains(item);
        }
    }

    /**********************************
     * 4.2: Content - Mutators
     **********************************/

    /**
     * Transfers the given item
     * @param item
     *        Item to be transferred
     * @param destination
     *        Destination of the item.
     * @effect The item is removed from the contents of this backpack.
     *       | removeItem(item)
     * @effect The item is added to the destination parent.
     *       | destination.addItem(item)
     * @throws InvalidItemException
     *         If the backpack does not contain this item.
     *       | !containsItem(item)
     * @throws NullPointerException
     *         If the given destination or item is a null pointer.
     *       | destination == null || item == null
     * @throws InvalidItemException
     *         The method addItem can throw an InvalidItemException, these are not caught to give
     *         more debugging information as the error message will be more specific.
     *         Refer to the specification of these methods within implementations of Parent for
     *         conditions under which this error is thrown.
     *         @see Backpack#addItem(Item) for backpacks
     *         @see Anchorpoint#addItem(Item) for anchorpoints
     */
    public void transferItemTo(Item item, Parent destination)
            throws InvalidItemException, NullPointerException{
        if (!contains(item)) {
            throw new InvalidItemException("This backpack does not contain the given item!");
        } else if (destination == null) {
            throw new NullPointerException("Destination is a null reference!");
        } else if (item == null){
            throw new NullPointerException("Item is a null reference!");
        } else {
            removeItem(item);
            destination.addItem(item);
        }
    }

    /**
     * Adds the given item to the content of this backpack.
     * @param item
     *        Item to be added.
     * @effect The item is added to the content of this backpack.
     *       | putItem(item)
     * @effect If the item implements hasParent, the item receives a reference to this backpack as
     *         it's parent.
     *       | if (item instanceof hasParent)
     *       |      item.setParent(this)
     * @throws InvalidItemException
     *         If the given item is already in this backpack.
     *       | contains(item)
     *         If adding the given item would exceed one of the capacities of the parents it would be
     *         (indirectly) held in.
     *       | exceedsCapacity(item)
     * @throws NullPointerException
     *         If the item is null.
     *       | item == null
     *
     */
    public void addItem(Item item) throws InvalidItemException, NullPointerException{
        if (item == null) {
            throw new NullPointerException("Item contains null reference!");
        } else if(contains(item)) {
            throw new InvalidItemException("Backpack already contains this item!");
        } else if(exceedsCapacity(item)){
            throw new InvalidItemException("Adding this item would exceed the capacity of this Backpack.");
        } else {
            putItem(item);
            if(item instanceof hasParent){
                ((hasParent) item).setParent(this);
            }
        }
    }

    /**
     * Causes the given item to be dropped to the ground.
     * @param item
     *        Item to be dropped.
     * @throws InvalidItemException
     *         If the item given is not in this backpack.
     *       | !this.contains(item)
     * @throws NullPointerException
     *         If the given item is null.
     *       | item == null
     */
    public void dropItem(Item item) throws InvalidItemException, NullPointerException{
        if (item == null) {
            throw new NullPointerException("Item contains null reference!");
        } else if(!contains(item)){
            throw new InvalidItemException("Item does not exist in this backpack!");
        } else {
            removeItem(item);
            item.drop();
        }
    }

    /**********************************
     * 4.2: Content - Value
     **********************************/

    /**
     * Calculates the total value of all the items currently being stored in
     * this backpack.
     * @return The sum of the values of the items currently being stored.
     * | let: sum = 0
     * | then:
     * |    for each item in content:
     * |        sum = sum + item.getValue()
     * |    return sum
     */
    public int getValueOfContent(){
        BackpackIterator it = this.iterator(true);
        int sum = 0;
        while(it.hasMoreElements()){
            sum += it.nextElement().getValue();
        }
        return sum;
    }

    /**********************************
     * 4.2: Content - Iterator
     **********************************/

    /**
     * Creates an instance of BackpackIterator that iterates over the current
     * content of the backpack.
     * @return new BackpackIterator(content)
     * @note When the content of the backpack is modified, this will not modify
     * the content of iterators that have already been created.
     */
    public BackpackIterator iterator(){
        return new BackpackIterator(getContent());
    }

    /**
     * Creates an instance of BackpackIterator that iterates over the current
     * content of the backpack.
     * @param skipNested
     *        Whether to skip items contained in
     *        //TODO specify this
     * @return new BackpackIterator(content)
     * @note When the content of the backpack is modified, this will not modify
     * the content of iterators that have already been created.
     */
    private BackpackIterator iterator(boolean skipNested) {
        return new BackpackIterator(getContent(), skipNested);
    }

    /**********************************
     * 4.2: Content - Auxiliary methods
     **********************************/

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
     * @pre The referenced item must be in the backpack.
     * @param item
     *        Item to remove.
     * @effect If there is no other item with the same id in the backpack, the
     * ArrayList for that identifier is removed.
     * | if content.get(item.id).size == 1
     * |    content.remove(item.id)
     * @effect Else remove the item from the ArrayList
     * | content.get(item.id).remove(item)
     */
    @Raw
    private void removeItem(Item item) {
        ArrayList<Item> idArray = getContent().get(item.getIdentifier());
        if (idArray.size() == 1){
            content.remove(item.getIdentifier());
        } else {
            content.get(item.getIdentifier()).remove(item);
        }
    }

    /**********************************
     * 4.2: Content - storage
     **********************************/

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
     * 4.4: Weight - total
     *****************************/

    /**
     * Calculates the weight of the content of this backpack.
     * @return Adds the weights of all the items together, returns that sum.
     *         If the item is an instance of container, the method to get the weight
     *         is getTotalWeight(), otherwise it is getWeight().
     * | let: sum = 0
     * | then:
     * |    for each item in content:
     * |        if item instanceof Container:
     * |            sum = sum + getTotalWeight()
     * |        else: sum = sum + getWeight()
     * |    return sum
     */
    @Override
    public Weight getWeightOfContents() {
        BackpackIterator it = this.iterator(true);
        Weight sum = Weight.kg_0;
        while(it.hasMoreElements()){
            Item next = it.nextElement();
            if(next instanceof Container){
                sum = sum.add(((Container) next).getTotalWeight());
            } else {
                sum = sum.add(next.getWeight());
            }
        }
        return sum;
    }

    /*****************************
     * 4.5: Value - total
     *****************************/

    /**
     * Gets the total value of the backpack and its content.
     * @return The sum of the value of the backpack itself and the value of its content.
     * | return getOwnValue + getValueOfContent
     */
    @Override
    public int getValue() {
        return getOwnValue() + getValueOfContent();
    }

    /**
     * Sets the ownValue for this backpack.
     * @param value
     *        Value to be set.
     * @effect If the given ownValue is effective, the ownValue attribute is set to it.
     *      | if isValidOwnValue(ownValue) this.ownValue = ownValue
     */
    public void setOwnValue(int value) {
        if(isValidOwnValue(value)){
            this.ownValue = value;
        }
    }

    /**
     * Getter for the inherent value of the backpack.
     */
    public int getOwnValue() {
        return ownValue;
    }

    /**
     * Checks if the given value is effective for a backpack.
     * @param value
     *        Value to be checked.
     * @return True if the given value is between 0 and 500.
     */
    public boolean isValidOwnValue(int value){
        return value >= 0 && value <= 500;
    }

    /**
     * Variable storing the inherent value of the backpack.
     */
    private int ownValue = 0;


}