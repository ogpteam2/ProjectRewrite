package rpg.inventory;

import rpg.exception.InvalidItemException;
import rpg.Mobile;
import rpg.value.Weight;

/**
 * A superclass of containers to contain a set of items.
 *
 * @author elias
 * @invar The weight of the contents must be smaller or equal to the
 * capacity of the container.
 * | getWeightOfContents().compareTo(
 */
public abstract class Container extends Item implements hasParent {

    public Container(Weight weight, Weight capacity) {
        super(weight);

    }

    /*****************************
     * Content - defensive
     *****************************/

    public abstract int getNbOfItems();

    /**
     * Adds the given item to the prime object.
     *
     * @param item Item to be added.
     * @throws InvalidItemException If the container cannot contain this item an InvalidItemException is thrown
     *                              | !canHaveAsContent(Item item)
     * @effect Item gets a reference
     * @effect Prime object gets reference to item
     */
    public void addToContent(Item item) throws InvalidItemException {

    }

    /**
     * @param item
     * @throws InvalidItemException
     */
    public void removeFromContent(Item item) throws InvalidItemException {

    }

    /*****************************
     * Weight
     *****************************/

    /**
     * Calculates the weight of all the items this container contains.
     *
     * @return A summation of the weights of the items contained.
     */
    public abstract Weight getWeightOfContents();

    /**
     * Calculates the total weight of the container.
     *
     * @return The weight of the container plus the weight of the contents.
     * | return getWeight + getWeightOfContents()
     */
    public Weight getTotalWeight() {
        return getWeight().add(getWeightOfContents());
    }

    /*****************************
     * Capacity
     *****************************/

    /**
     * Checks whether adding the given item will cause the total weight of
     * items contained to exceed the capacity of the container.
     *
     * @return Else return whether the current weight of the contents plus the
     * weight of the item exceeds the capacity.
     * | return exceedsCapacity(
     * |      getWeightOfContents().add(item.getWeight()))
     */
    protected boolean exceedsCapacity(Item item) {
        return false;
    }


    /**
     * Checks whether the given weight exceeds the capacity of this container.
     *
     * @param weight Weight to be checked against the capacity.
     * @return Else return whether the given weight is larger than the
     * capacity of the container.
     */
    protected boolean exceedsCapacity(Weight weight) {
        if (weight == null)
            return false;
        else
            return getCapacity().compareTo(weight) == -1;
    }

    /**
     * Returns the ca
     *
     * @return
     */
    public Weight getCapacity() {
        return this.capacity;
    }

    /**
     * Weight variable storing the capacity of this container.
     * The capacity of a container dictates how much weight the
     * items contained collectively may weigh.
     */
    protected Weight capacity = null;

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
    public Mobile getHolder() {
        if (parent == null) {
            return null;
        } else {
            return parent.getHolder();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Parent getParent() {
        return this.parent;
    }

    private Parent parent;

    @Override
    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
