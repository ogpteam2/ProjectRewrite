package rpg;

import rpg.exception.InvalidNameException;
import rpg.inventory.AnchorType;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Item;
import rpg.value.Strength;
import rpg.value.Weight;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.EnumSet;

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
     * Strength
     *****************************/

    /**
     * Getter for strength.
     */
    public Strength getStrength() {
        return strength;
    }

    /**
     * Multiplies the current strength with the given factor.
     * @param factor
     *        Multiplication factor.
     * @effect Sets the strength to the current strength multiplied by the given factor.
     * | setStrength(getStrength().multiply(factor))
     */
    public void multiplyStrength(int factor){
        setStrength(getStrength().multiply(factor));
    }

    /**
     * Divides the strength by the given divisor.
     * @param divisor
     *        Value to divide by.
     * @effect Sets the strength to the current strength divided by the given divisor.
     * | setStrength(getStrength().divide(divisor))
     */
    public void divideStrength(int divisor){
        setStrength(getStrength().divide(divisor));
    }

    /**
     * Setter for strength.
     * @param strength
     *        Value the attribute strength is to be set to.
     * @effect If the given instance of Strength is effective, the attribute is set.
     * | if isValidStrength(strength)
     * |    this.strength = strength
     * @effect Else the attribute is set to no strength at all.
     * | this.strength = Strength.none
     * @note Private because strength should only be multiplied or divided by integers,
     * not directly set.
     */
    private void setStrength(Strength strength) {
        if (isValidStrength(strength)){
            this.strength = strength;
        } else {
            this.strength = Strength.none;
        }
    }

    /**
     * Checks if the given instance of Strength is effective for a Mobile.
     * @param strength
     *        Strength instance to be checked.
     * @return If the given Strength is not a null reference, return true.
     * | return strength != null
     */
    public boolean isValidStrength(Strength strength){
        return strength != null;
    }

    private Strength strength = null;

    /*****************************
     * Capacity
     *****************************/

    public abstract Weight getCapacity();
}
