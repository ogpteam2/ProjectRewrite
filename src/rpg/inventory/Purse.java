package rpg.inventory;

import rpg.IDGeneration.IDGenerator;
import rpg.Mobile;
import rpg.IDGeneration.FibonacciGenerator;
import rpg.value.Weight;
import be.kuleuven.cs.som.taglet.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class Purse extends Container {

    /**
     * Initialises a new purse with the given value, weight and capacity, then
     * adds the given ducats to contents.
     * @param value
     *        Value of the purse itself.
     * @param weight
     *        Weight of this purse itself.
     * @param capacity
     *        Capacity of this purse expressed in terms of weight.
     * @param ducats
     *        Series of ducat objects to be added to the purse
     *        after initialisation.
     * @effect An empty purse is initialised using the less extended constructor.
     *         @see Purse#Purse(int, Weight, Weight)
     *       | this(value, weight, capacity)
     * @effect All given ducats are added to the contents of the purse.
     *       | for each ducat in ducats:
     *       |    addToContent(ducat)
     */
    public Purse(int value, Weight weight, Weight capacity, Ducat... ducats){
        this(value, weight, capacity);
        content.addAll(Arrays.asList(ducats));
    }

    /**
     * Initialises a new, empty purse with the given value, weight and capacity.
     *
     * @param value
     *        Value of the purse itself.
     * @param weight
     *        Weight of this purse itself.
     * @param capacity
     *        Capacity of this purse expressed in terms of weight.
     * @effect Uses the superclass constructor to set value, weight and capacity.
     *         @see Container#Container(int, Weight, Weight)
     *       | super(value, weight, capacity)
     *
     */
    public Purse(int value, Weight weight, Weight capacity) {
        super(value, weight, capacity);
        this.content = new Stack<>();
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
    public boolean canHaveAsValue(int value) {
        return false;
    }

    @Override
    public Mobile getHolder() {
        return null;
    }

    /*****************************
     * Content
     *****************************/

    /**
     * Drops all the ducats held within this purse.
     * @effect Every ducat in the content of this purse are dropped.
     *         As ducats have no knowledge of their parent, this is equivalent
     *         to reinitialising the content Stack.
     *       | content = new Stack&lt;Ducat&gt;
     */
    private void dropAllContent(){
        content = new Stack<>();
    }

    /**
     * Drops one ducat on the ground.
     * @effect One ducat is removed from the contents of this purse.
     *       | content.pop()
     */
    private void drop(){
        content.pop();
    }

    /**
     * Data structure for storing the contents of this purse.
     * @note The stack data structure was chosen for ease of use. As all ducats
     * are identical, there is no need to get to a specific one. However, removing
     * one ducat gets significantly easier when using a stack.
     */
    private Stack<Ducat> content = null;

    /*****************************
     * Identifier
     *****************************/

    private static FibonacciGenerator idGen = new FibonacciGenerator();

    @Override
    IDGenerator getIDGenerator() {
        return idGen;
    }

    /*****************************
     * Capacity
     *****************************/

    /**
     * Tears the purse. Drops all the contents into the parent of the purse and
     * renders the purse itself unusable.
     * @effect If the purse is laying on the ground, all ducats contained within
     *         are dropped on the ground.
     *       | if parent == null
     *       |     dropAllContent()
     * @effect If the purse's parent is an anchorpoint, all ducats contained within
     *         are dropped on the ground. The purse is also dropped on the ground.
     *       | if parent instanceof Anchorpoint
     *       |     dropAllContent()
     *       |     parent.drop(this)
     * @effect If the purse's parent is a Backpack, try to add all the ducats to the
     *         contents of the backpack. If there is no room in the backpack, drop the
     *         ducat on the ground. The purse is also dropped on the ground.
     *       | if parent instanceof Backpack
     *       |     for each ducat in content
     *       |         drop(ducat)
     *       |         try parent.addToContent(ducat)
     *       |     parent.drop(this)
     *
     */
    private void tear(){

    }

    /**
     * Sets the capacity for this purse to the given value.
     *
     * @param capacity Capacity weight the attribute has to be set to.
     * @effect If the given capacity is a null reference, nothing changes.
     *       | if capacity == null break
     * @effect If the given capacity is a valid instance of Weight, sets the capactity
     *         to the given value.
     *       | this.capacity = capacity
     * @effect If the given capacity is smaller than the current capacity, check if
     *         the weight of the contents exceeds the given capacity. If this is the case,
     *         tear the purse.
     *       | if (capacity.compareTo(getCapacity()) == -1)
     *       |     if (exceedsCapacity(getWeightOfContents()))
     *       |         tear()
     */
    public void setCapacity(Weight capacity) {
        if (capacity == null) return;
        boolean smallerThanCurrent = false;
        if (capacity.compareTo(getCapacity()) == -1){
            smallerThanCurrent = true;
        }
        this.capacity = capacity;
        if (smallerThanCurrent){
            if (exceedsCapacity(getWeightOfContents())){
                tear();
            }
        }
    }
}
