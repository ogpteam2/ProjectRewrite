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
    public Mobile getHolder({
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

    public boolean canHaveAsItem(Item item){
        if(item instanceof Weapon || item instanceof Backpack){
            return true;
        } else {
            switch(getType()){
                case back:

            }
        }

    }

    /**
     * Variable that determines what type this anchorpoint is.
     *
     * @note The type of anchorpoint determines what
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
     * @pre  The anchorpoint must have
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
     * @return If the
     */
    @Raw
    public boolean containsItem(){
        return getContent() != null;
    }

    private Item content = null;

}
