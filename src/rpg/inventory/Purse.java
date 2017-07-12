package rpg.inventory;

import rpg.Mobile;
import rpg.utility.FibonacciGenerator;
import rpg.value.Weight;

public class Purse extends Container {

    public Purse(int value, Weight weight) {
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
    public boolean canHaveAsValue(int value) {
        return false;
    }

    @Override
    public Mobile getHolder() {
        return null;
    }

    /**
     * Drops all the ducats held within this purse.
     * @effect Every ducat in the content of this purse are dropped.
     *       | for each ducat in content
     *       |      drop(ducat)
     */
    private void dropAllContent(){

    }

    /*****************************
     * Identifier
     *****************************/

    private static FibonacciGenerator idGen = new FibonacciGenerator();

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
