package rpg.inventory;

import rpg.utility.FibonacciGenerator;
import rpg.utility.IDGenerator;
import rpg.exception.InvalidItemException;
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
        addDucats(ducats);
    }

    /**
     * Initialises a new, empty purse with the given value, weight and capacity.
     *
     * @param weight   Weight of this purse itself.
     * @param capacity Capacity of this purse expressed in terms of weight.
     * @effect Uses the superclass constructor to set value, weight and capacity.
     * As the purse has no value itself, value is set to zero.
     * @see Container#Container(Weight, Weight)
     * | super(0, weight, capacity)
     */
    public Purse(Weight weight, Weight capacity) {
        super(weight, capacity);
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
     * Counts how many ducats are in the purse
     * @return The size of the content stack.
     * | content.size()
     */
    public int getNbOfItems(){
        return content.size();
    }

    /**
     * Adds the given ducat to the contents of this purse.
     *
     * @param ducat Ducat to be added.
     * @throws NullPointerException If the given ducat contains a null pointer, exception is thrown.
     * @throws InvalidItemException If the purse is torn, exception is thrown.
     * @effect If the ducat is effective, it is added to the purse.
     * | if ducat != null:
     * |      content.push(ducat)
     * @effect If adding the ducat has exceeded the capacity of this purse, tear it.
     * | if exceedsCapacity(getWeightOfContents()):
     * |      tear()
     */
    public void addDucat(Ducat ducat)
            throws NullPointerException, InvalidItemException {
        if (isTorn()) throw new InvalidItemException("Purse is torn!");
        if (ducat == null) {
            throw new NullPointerException("Given ducat contains null pointer");
        } else {
            content.push(ducat);
            if (exceedsCapacity(getWeightOfContents())) {
                tear();
            }
        }
    }

    public void addDucats(Ducat... ducats)
            throws NullPointerException {
        for (Ducat ducat : ducats) {
            if (ducat == null)
                throw new NullPointerException("Given ducat contains null pointer");
        }
        Stack<Ducat> ducatStack = new Stack<>();
        ducatStack.addAll(Arrays.asList(ducats));
    }

    /**
     * Adds all the given ducats to this purse.
     *
     * @param ducats Stack of ducats to be added
     * @effect Tries to add all the ducats in the stack to this purse one by one.
     * | for each ducat in ducats:
     * |    try addDucat(ducat)
     * @effect If the purse tears and not all ducats have been added, the remaining
     * ducats are added to the parent of the purse (with the rest of the contents of
     * the purse, as it has been torn)
     * | if !ducats.empty():
     * |    for each ducat in ducats:
     * |        try getParent.addItem(ducat)
     * @effect If that fails, the remaining ducats are dropped to the ground by just
     * letting the method terminating without adding the ducats to an inventory.
     */
    public void addDucats(Stack<Ducat> ducats) throws NullPointerException {
        while (!ducats.isEmpty()) {
            try {
                addDucat(ducats.pop());
            } catch (InvalidItemException e) {
                break;
            }
        }
        while (!ducats.isEmpty()) {
            try {
                getParent().addItem(ducats.pop());
            } catch (InvalidItemException e) {
            }
        }
    }

    /**
     * Transfers the contents of this purse to the given other.
     *
     * @param purse Purse the contents are to be added to.
     * @effect All of this purse's contents are removed.
     * | dropAllContent()
     * @effect This purse is dropped to the ground.
     * | getParent().drop(this)
     * @effect All ducats that were once in this purse are added to
     * the given other purse.
     * | purse.addDucats(this.content)
     */
    public void transferContentTo(Purse purse) {
        Stack<Ducat> ducatsToTransfer = this.getContent();
        this.dropAllContent();
        this.getParent().dropItem(this);

        purse.addDucats(ducatsToTransfer);
    }

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
    private Ducat drop() {
        return content.pop();
    }

    /**
     * Retrieves the content stack for this purse. Private because content should not
     * be manipulated directly.
     *
     * @return
     */
    private Stack<Ducat> getContent() {
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
     *
     * @return All ducats have the same weight so the total weight is equal
     * to the weight of one ducat multiplied by the amount of ducats.
     */
    public Weight getWeightOfContents() {
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
     * |     parent.dropItem(this)
     * @effect If the purse's parent is a Backpack, try to add all the ducats to the
     * contents of the backpack. If there is no room in the backpack, drop the
     * ducat on the ground. The purse is also dropped on the ground.
     * | if parent instanceof Backpack
     * |     for i < content.size
     * |         try parent.addItem(drop())
     * |     parent.drop(this)
     * @note If trying to add a ducat to the backpack fails, this automatically drops
     * it to the ground because the reference to it has already been removed from
     * the content stack of the purse.
     */
    private void tear() {
        if (getParent() == null) dropAllContent();
        if (getParent() instanceof Anchorpoint) {
            dropAllContent();
            getParent().dropItem(this);
        } else {
            for (int i = 0; i < getContent().size(); i++) {
                try {
                    getParent().addItem(this.drop());
                } catch (InvalidItemException e) {
                    //nothing has to be done, reference to ducat already removed.
                }
            }
        }
        torn = true;
    }

    /**
     * Checks whether the purse is torn.
     */
    public boolean isTorn() {
        return this.torn;
    }

    /**
     * Boolean storing whether the purse is torn.
     * If the purse is torn it is effectively useless as it won't contain
     * any items, and no items can be added anymore.
     */
    private boolean torn = false;

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
