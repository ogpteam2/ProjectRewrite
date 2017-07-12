package rpg.inventory;

import rpg.IDGeneration.IDGenerator;
import rpg.Mobile;
import rpg.IDGeneration.FibonacciGenerator;
import rpg.value.Weight;

import java.util.Arrays;
import java.util.Stack;

/**
 *
 */
public class Purse extends Container {

    /*****************************
     * 6.0: Constructors
     *****************************/

    /**
     * Initialises a new purse with the given weight and capacity, then
     * adds the given ducats to contents.
     *
     * @param weight   Weight of this purse itself.
     * @param capacity Capacity of this purse expressed in terms of weight.
     * @param ducats   Series of ducat objects to be added to the purse
     *                 after initialisation.
     * @effect An empty purse is initialised using the less extended constructor.
     * @effect All given ducats are added to the contents of the purse.
     * | for each ducat in ducats:
     * |    addToContent(ducat)
     * @see Purse#Purse(Weight, Weight)
     * | this(weight, capacity)
     */
    public Purse(Weight weight, Weight capacity, Ducat... ducats) {
        this(weight, capacity);
        content.addAll(Arrays.asList(ducats));
    }

    /**
     * Initialises a new, empty purse with the given value, weight and capacity.
     *
     * @param weight   Weight of this purse itself.
     * @param capacity Capacity of this purse expressed in terms of weight.
     * @effect Uses the superclass constructor to set value, weight and capacity.
     * As the purse has no value itself, value is set to zero.
     * @see Container#Container(int, Weight, Weight)
     * | super(0, weight, capacity)
     */
    public Purse(Weight weight, Weight capacity) {
        super(0, weight, capacity);
        this.content = new Stack<>();
    }

    /*****************************
     * 6.1: Identifier
     *****************************/

    private static FibonacciGenerator idGen = new FibonacciGenerator();

    @Override
    IDGenerator getIDGenerator() {
        return idGen;
    }

    /*****************************
     * 6.2: Content
     *****************************/

    /**
     * Drops all the ducats held within this purse.
     *
     * @effect Every ducat in the content of this purse are dropped.
     * As ducats have no knowledge of their parent, this is equivalent
     * to reinitialising the content Stack.
     * | content = new Stack&lt;Ducat&gt;
     */
    private void dropAllContent() {
        content = new Stack<>();
    }

    /**
     * Drops one ducat on the ground.
     *
     * @effect One ducat is removed from the contents of this purse.
     * | content.pop()
     */
    private void drop() {
        content.pop();
    }

    /**
     * Retrieves the content stack for this purse. Private because content should not
     * be manipulated directly.
     * @return
     */
    private Stack<Ducat> getContent(){
        return this.content;
    }

    /**
     * Data structure for storing the contents of this purse.
     *
     * @note The stack data structure was chosen for ease of use. As all ducats
     * are identical, there is no need to get to a specific one. However, removing
     * one ducat gets significantly easier when using a stack.
     */
    private Stack<Ducat> content = null;

    /*****************************
     * 6.3: Weight
     *****************************/

    /**
     * Calculates the weight of all the items this container contains.
     * @return All ducats have the same weight so the total weight is equal
     *         to the weight of one ducat multiplied by the amount of ducats.
     */
    public Weight getWeightOfContents(){
        return Ducat.DUCAT_WEIGHT.multiply(getContent().size());
    }

    /*****************************
     * 6.4: Value
     *****************************/

    /**
     * Calculates or returns the total value of this item.
     *
     * @return The value of the contents of this item.
     * Every ducat has the same value of one, so the value is equal to the
     * amount of ducats being stored.
     * | return content.size()
     */
    @Override
    public int getValue() {
        return content.size();
    }


    @Override
    public Mobile getHolder() {
        return null;
    }


    /*****************************
     * 6.5: Capacity
     *****************************/

    /**
     * Tears the purse. Drops all the contents into the parent of the purse and
     * renders the purse itself unusable.
     *
     * @effect If the purse is laying on the ground, all ducats contained within
     * are dropped on the ground.
     * | if parent == null
     * |     dropAllContent()
     * @effect If the purse's parent is an anchorpoint, all ducats contained within
     * are dropped on the ground. The purse is also dropped on the ground.
     * | if parent instanceof Anchorpoint
     * |     dropAllContent()
     * |     parent.drop(this)
     * @effect If the purse's parent is a Backpack, try to add all the ducats to the
     * contents of the backpack. If there is no room in the backpack, drop the
     * ducat on the ground. The purse is also dropped on the ground.
     * | if parent instanceof Backpack
     * |     for each ducat in content
     * |         drop(ducat)
     * |         try parent.addToContent(ducat)
     * |     parent.drop(this)
     */
    private void tear() {

    }

    /**
     * Sets the capacity for this purse to the given value.
     *
     * @param capacity Capacity weight the attribute has to be set to.
     * @effect If the given capacity is a null reference, nothing changes.
     * | if capacity == null break
     * @effect If the given capacity is a valid instance of Weight, sets the capactity
     * to the given value.
     * | this.capacity = capacity
     * @effect If the given capacity is smaller than the current capacity, check if
     * the weight of the contents exceeds the given capacity. If this is the case,
     * tear the purse.
     * | if (capacity.compareTo(getCapacity()) == -1)
     * |     if (exceedsCapacity(getWeightOfContents()))
     * |         tear()
     */
    public void setCapacity(Weight capacity) {
        if (capacity == null) return;
        boolean smallerThanCurrent = false;
        if (capacity.compareTo(getCapacity()) == -1) {
            smallerThanCurrent = true;
        }
        this.capacity = capacity;
        if (smallerThanCurrent) {
            if (exceedsCapacity(getWeightOfContents())) {
                tear();
            }
        }
    }

}
