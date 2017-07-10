package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import org.omg.CORBA.DynAnyPackage.Invalid;
import rpg.InvalidItemException;
import rpg.Mobile;
import rpg.inventory.Container;

/**
 * A class of Anchorpoints to which mobiles can attach Items.
 *
 * @invar The Item currently being stored in the anchorpoint
 *        has to be of the right type as dictated by the
 *        anchorpoint type.
 *      | canHaveAsItem(this.content)
 */
public class Anchorpoint implements Holder {


    /**
     * Creates a new anchorpoint of given type with given holder.
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
    public Mobile getHolder(){
        return this.holder;
    }

    /**
     * Variable that stores a reference to the holder of this anchorpoint.
     *
     * @note An anchorpoint is a container inherent to the holder so it cannot
     *       be transferred between mobiles. As a result the holder is set at
     *       initialisation.
     */
    private final Mobile holder;

    /*****************************
     * Type
     *****************************/

    @Basic
    public AnchorType getType() {
        return type;
    }

    /**
     * Checks if the anchorpoint contain this item.
     * @param item
     *        Item to be checked.
     * @return An anchorpoint can contain an item if carrying the item will not
     *         cause the holding mobile to exceed it's carrying capacity.
     *       | return !holder.exceedsCapacity(holder.getCurrentCarriedWeight.add(item.getWeight))
     */
    public boolean canHaveAsItem(Item item){
        return !getHolder().exceedsCapacity(
                getHolder().getCurrentCarriedWeight() //get weight currently carried by mobile
                        .add(item.getWeight())); //add weight carrying the item would add
    }

    /**
     * Variable that determines what type this anchorpoint is.
     *
     * @note Using an enum type for the anchorpoint is an easy way to both reference
     *       the anchorpoint in a memory efficient way (using the enum instead of the
     *       object) and ensure a mobile can only have one of each (using enummap).
     */
    private final AnchorType type;

    /*****************************
    * Content
    ******************************/

    @Raw @Basic
    public Item getContent() {
        return content;
    }

    @Raw @Basic
    private void setContent(Item content) {
        this.content = content;
    }

    /**
     * Drops the item currently being held in this anchorpoint.
     *
     * @throws InvalidItemException
     *         If the anchorpoint does not contain an item, exception is thrown.
     *       | if(!containsItem()) throw InvalidItemException
     * @post The item's parent has to be a null reference.
     *       | item.getParent() == null
     * @post The anchorpoint has a null refrence
     */
    public void dropItem() throws InvalidItemException{
        if(!containsItem()) throw new InvalidItemException("Anchorpoint does not contain an item!");
        else {

        }
    }

    /**
     * Puts the item into the anchorpoint if there is room.

     * @throws NullPointerException
     *         If the passed object contains a null reference, exception is thrown.
     *       | if(item == null) throw NullPointerException
     * @throws InvalidItemException
     *         If the anchorpoint already contains an item, exception is thrown.
     *       | if(containsItem()) throw InvalidItemException
     *         If the anchorpoint cannot accept the item, exception is thrown.
     *       | if(!canHaveAsItem(item)) throw InvalidItemException
     *
     * @post The parent of the item must be set to this anchorpoint.
     *     | item.getParent() == this
     * @post The content of this anchorpoint must be the given item.
     *     | getContent() == item
     */
    public void addItem(Item item) throws NullPointerException, InvalidItemException {
        if(containsItem()){
            throw new InvalidItemException("Anchorpoint already contains an item!");
        } else if(item == null){
            throw new NullPointerException();
        } else if (!canHaveAsItem(item)){
            throw new InvalidItemException("Anchorpoint cannot accept this item!");
        } else {

        }
    }

    /**
     * Checks if the anchorpoint contains an item.
     *
     * @return If the variable content contains a null reference, return false.
     *       | return content != null
     */
    @Raw
    public boolean containsItem(){
        return getContent() != null;
    }

    private Item content = null;

}
