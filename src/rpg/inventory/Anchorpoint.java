package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.exception.InvalidItemException;
import rpg.Mobile;
import rpg.value.Weight;

/**
 * A class of Anchorpoints to which mobiles can attach Items.
 *
 * @invar The Item currently being stored in the anchorpoint
 * has to be of the right type as dictated by the
 * anchorpoint type.
 * | canHaveAsItem(this.content)
 */
public class Anchorpoint implements Parent {


    /**
     * Creates a new anchorpoint of given type with given holder.
     *
     * @param holder
     */
    public Anchorpoint(Mobile holder, AnchorType type) {
        this.holder = holder;
        this.type = type;
    }

    /*****************************
     * Holder
     *****************************/

    @Basic
    public Mobile getHolder() {
        return this.holder;
    }

    /**
     * Variable that stores a reference to the holder of this anchorpoint.
     *
     * @note An anchorpoint is a container inherent to the holder so it cannot
     * be transferred between mobiles. As a result the holder is set at
     * initialisation.
     */
    private final Mobile holder;

    /*****************************
     * Type
     *****************************/

    /**
     * Getter for the type of this anchorpoint.
     * @return An enum type depicting what type this anchorpoint is.
     */
    @Basic
    public AnchorType getType() {
        return type;
    }

    /**
     * Variable that determines what type this anchorpoint is.
     *
     * @note Using an enum type for the anchorpoint is an easy way to both reference
     * the anchorpoint in a memory efficient way (using the enum instead of the
     * object) and ensure a mobile can only have one of each (using enummap).
     */
    private final AnchorType type;

    /*****************************
     * Content - checks
     *****************************/

    /**
     * Checks if the anchorpoint can contain this item.
     *
     * @param item Item to be checked.
     * @return An anchorpoint can contain an item if carrying the item will not
     * cause the holding mobile to exceed it's carrying capacity.
     * | return !exceedsCapacity(item)
     */
    public boolean canHaveAsItem(Item item) {
        return !exceedsCapacity(item); //add weight carrying the item would add
    }

    /*****************************
     * Content - manipulation
     *****************************/

    /**
     * Transfers the content of this anchorpoint to the target parent.
     * @param target
     *        Parent instance to which the item is to be transferred.
     * @throws InvalidItemException
     *         If the target is null.
     *       | target == null
     *         If the anchorpoint does not contain an item.
     *       | !this.containsItem()
     *         If the target cannot accept the item.
     *       | !target.canHaveAsItem(content)
     * @effect Adds the item to the target parent, then removes it from the anchorpoint.
     *       | target.addItem(content)
     *       | dropItem()
     */
    public void transferContentTo(Parent target) throws InvalidItemException{
        if (target == null) {
            throw new InvalidItemException("Target is null!");
        } else if (!containsItem()) {
            throw new InvalidItemException("Anchorpoint contains no item!");
        } else {
            target.addItem(getContent());
            dropItem();
        }
    }

    /**
     * Checks if the anchorpoint contains a weapon.
     * @return True if the contained item is an instance of Weapon.
     */
    public boolean containsWeapon(){
        return getContent() instanceof Weapon;
    }

    /**
     * Checks if adding the given item would exceed the holder's capacity.
     * @param item
     *        Item to be checked.
     * @return If the given item is a null reference, return false.
     *       | if item == null return false
     * @return Else add the weight of the item to the current carried weight and check
     *         if that weight exceeds the capacity of the holder.
     */
    @Override
    public boolean exceedsCapacity(Item item) {
        if (item == null) {
            return false;
        } else {
            Weight newWeight = getHolder().getCurrentCarriedWeight()
                    .add(item.getWeight());
            return getHolder().exceedsCapacity(newWeight);
        }

    }

    @Raw
    @Basic
    public Item getContent() {
        return content;
    }

    /**
     * Calculates the weight of the item currently being held.
     *
     * @return If an item is being held, the weight of that item is returned.
     * | if containsItem() return item.getWeight()
     * @return 0 kg if
     */
    public Weight getWeightOfContent(){
        if(containsItem()){
            return getContent().getWeight();
        } else {
            return Weight.kg_0;
        }
    }

    @Raw
    @Basic
    private void setContent(Item content) {
        this.content = content;
    }

    /**
     * Drops the item currently being held in this anchorpoint.
     *
     * @throws InvalidItemException If the anchorpoint does not contain an item, exception is thrown.
     *                              | if(!containsItem()) throw InvalidItemException
     * @post The item's parent has to be a null reference.
     * | item.getParent() == null
     * @post The anchorpoint has a null reference as its content.
     */
    //todo this
    public void dropItem(Item item) throws InvalidItemException, NullPointerException {
        if (item == null) throw new NullPointerException("Item is null reference!");
        else if (item.equals(getContent())){
            throw new InvalidItemException("Anchorpoint does not contain given item!");
        } else {
            if (item instanceof hasParent){
                ((hasParent) item).drop();
            }
            setContent(null);
        }
    }

    //todo specify this
    public void dropItem() throws InvalidItemException {
        try{
            dropItem(getContent());
        } catch (NullPointerException e){
            throw new InvalidItemException("Anchorpoint does not contain an item!");
        }
    }

    /**
     * Puts the item into the anchorpoint if there is room.
     *
     * @throws NullPointerException If the passed object contains a null reference, exception is thrown.
     *                              | if(item == null) throw NullPointerException
     * @throws InvalidItemException If the anchorpoint already contains an item, exception is thrown.
     *                              | if(containsItem()) throw InvalidItemException
     *                              If the anchorpoint cannot accept the item, exception is thrown.
     *                              | if(!canHaveAsItem(item)) throw InvalidItemException
     * @post The parent of the item must be set to this anchorpoint.
     * | item.getParent() == this
     * @post The content of this anchorpoint must be the given item.
     * | getContent() == item
     */
    public void addItem(Item item) throws NullPointerException, InvalidItemException {
        if (containsItem()) {
            throw new InvalidItemException("Anchorpoint already contains an item!");
        } else if (item == null) {
            throw new NullPointerException("Item is a null reference!");
        } else if (!canHaveAsItem(item)) {
            throw new InvalidItemException("Anchorpoint cannot accept this item!");
        } else {
            setContent(item);
            if(item instanceof hasParent){
                ((hasParent) item).setParent(this);
            }
        }
    }

    /**
     * Checks if the anchorpoint contains an item.
     *
     * @return If the variable content does not contain a null reference, return true.
     * | return !contains(null)
     */
    @Raw
    public boolean containsItem() {
        return !contains(null);
    }

    /**
     * Checks whether the parent contains the given item.
     * @param item
     *        Item to look for.
     * @return Whether the content variable contains a reference to the given item.
     *       | return content == item
     */
    public boolean contains(Item item){
        return getContent() == item;
    }

    /**
     * Variable containing a reference to the item currently held within the anchorpoint.
     */
    private Item content = null;
}
