package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
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
     * @return An anchorpoint can contain an item if:
     *         - the given object is not a null reference
     *         - the given object is an instance of the class item or ducat, thus
     *           classifying it as a game item.
     *         - carrying the item will not cause the holding mobile to exceed it's
     *           carrying capacity.
     */
    public boolean canHaveAsItem(Item item){
        return false;
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
    *****************************/


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
     * @pre  The anchorpoint must contain an item.
     *      | containsItem()
     * @post The item's parent has to be a null reference.
     *       | item.getParent() == null
     * @post The anchorpoint has a null refrence
     */
    public void dropItem(){

    }

    /**
     * Puts the item into the anchorpoint if there is room.
     *
     * @pre The anchorpoint may not contain an item.
     *    | !containsItem()
     * @post The parent of the item must be set to this anchorpoint.
     *     | item.getParent() == this
     * @post The content of this anchorpoint must be the given item.
     *     | getContent() == item
     */
    public void addItem(Item item){

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
