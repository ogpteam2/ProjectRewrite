package rpg;

import rpg.exception.InvalidNameException;
import rpg.inventory.AnchorType;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Item;
import rpg.value.Weight;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.regex.Pattern;

public abstract class Mobile {

    /**
     * @param anchorTypes
     * @post The mobile has an anchorpoint corresponding to each of the
     * types specified in the set anchorTypes.
     * | for each type in anchorTypes:
     * |    anchorpoints.get(type) != null
     * @note All
     */
    public Mobile(String name, EnumSet<AnchorType> anchorTypes) {
        //name
        setName(name);
        //anchorpoints
        anchorpoints = new EnumMap<AnchorType, Anchorpoint>(AnchorType.class);
        for (AnchorType type : anchorTypes) {
            anchorpoints.put(type, new Anchorpoint(this, type));
        }
    }

    /*****************************
     * Name
     *****************************/

    /**
     * Setter for the name of this mobile.
     * @param name
     *        String the name is to be set to.
     * @throws InvalidNameException
     *         If the name is not effective, exception is thrown.
     *       | if !isValidName(name) throw InvalidNameException
     */
    public void setName(String name) throws InvalidNameException{
        if(!isValidName(name)) throw new InvalidNameException("Name is not allowed!");
        this.name = name;
    }

    /**
     * Getter for the name of this mobile.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Checks whether the given string is an effective name for a mobile.
     * @param name
     *        String to be checked.
     * @return Whether the given string is an effective name.
     *         Conditions to be defined on subclass level.
     */
    public abstract boolean isValidName(String name);

    /**
     * Variable for storing the name of this mobile.
     */
    private String name = null;

    /*****************************
     * Hitpoints - nominal
     *****************************/


    /**
     *
     */

    /*****************************
     * Anchorpoints
     *****************************/

    /**
     * Retrieves the
     *
     * @param type
     * @return
     */
    public Anchorpoint getAnchorpoint(AnchorType type) {
        return this.anchorpoints.get(type);
    }

    /**
     * Adds the item to the anchorpoint of the given type.
     *
     * @param type What anchorpoint to add the item to.
     * @param item The item to add to the anchorpoint.
     * @pre The anchorpoint may not have an item in it as dictated by
     * it's precondition.
     */
    public void addItemToAnchorpoint(AnchorType type, Item item) {
        getAnchorpoint(type).addItem(item);
    }

    /**
     * Retrieves all empty anchorpoints this mobile has.
     *
     * @return An arraylist with all anchorpoints that are empty.
     * | ArrayList&#60Anchorpoint&#62 empty
     */
    public EnumMap<AnchorType, Anchorpoint> getEmptyAnchorpoints() {
        EnumMap<AnchorType, Anchorpoint> emptyAnchorPoints = new EnumMap<AnchorType, Anchorpoint>(AnchorType.class);
        for (AnchorType type : anchorpoints.keySet()) {
            if (anchorpoints.get(type).containsItem()) {
                emptyAnchorPoints.put(type, anchorpoints.get(type));
            }
        }
        return emptyAnchorPoints;
    }

    /**
     * Calculates the weight of all items held by this mobile.
     *
     * @return Sum of the total weights of the items held in the anchorpoints.
     * |sum = 0
     * |for each anchorpoint in anchorpoints:
     * |     sum += anchorpoint.getContent().getWeight()
     */
    public Weight getCurrentCarriedWeight() {
        return new Weight(BigDecimal.ZERO);
    }

    /**
     * Checks if the given weight would exceed this mobile's carrying capacity.
     *
     * @param weight Given weight to check against capacity.
     * @return If the given weight is larger than the mobile's carrying capacity.
     * | getCapacity().compareTo(weight) == -1
     */
    public boolean exceedsCapacity(Weight weight) {
        return getCapacity().compareTo(weight) == -1;
    }

    private final EnumMap<AnchorType, Anchorpoint> anchorpoints;

    /*****************************
     * Capacity
     *****************************/

    public abstract Weight getCapacity();
}
