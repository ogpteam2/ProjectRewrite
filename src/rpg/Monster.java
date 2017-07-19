package rpg;

import rpg.inventory.AnchorType;
import rpg.value.Weight;

import java.util.EnumSet;

/**
 * Created by elias on 08/07/17.
 */
public class Monster extends Mobile {

    /*****************************
     * Constants
     *****************************/

    /**
     * Constant specifying what protection factor a hero should have.
     */
    private static final int PROTECTION = 10;

    /*****************************
     * 2.0: Constructors
     *****************************/

    /**
     * @param anchorPoints An enumset of the AnchorType enum specifying which
     *                     types of anchorpoints this monster has.
     * @post
     */
    public Monster(String name, EnumSet<AnchorType> anchorPoints) {
        super(name, , anchorPoints);

    }

    /*****************************
     * 2.1: Name
     *****************************/

    /**
     * Checks whether the given string is an effective name for a monster.
     * @param name
     *        String to be checked.
     * @return If the string does not start with a capital letter, return false.
     *       | if !name.matches("^[A-Z]") return false
     *         If the rest of the string consists of characters other than letters,
     *         spaces and apostrophes, return false.
     *       | if !name.matches("^[A-Z][A-Za-z ']+") return false
     *         Else return true
     */
    public boolean isValidName(String name){
        return name.matches("^[A-Z][A-Za-z ']+");
    }

    /*****************************
     * 2.6: Strength - Capacity
     *****************************/

    /**
     * Calculates the carrying capacity this mobile has.
     * @return The carrying capacity in kilograms is equal to the strength
     * multiplied by 9.
     * | return new Weight(strength * 9)
     */
    @Override
    public Weight getCapacity() {
        Weight base = new Weight(9);
        return base.multiply(getStrength());
    }
}
