package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.IDGeneration.IDGenerator;
import rpg.IDGeneration.WeaponIDGenerator;
import rpg.Mobile;
import rpg.value.Weight;

/**
 * A class of weapons usable by mobiles.
 *
 * @invar The identifier for an instance of Weapon is an even, positive integer
 * divisible by three.
 * | identifier mod 2 == 0 && identifier mod 3 == 0 && identifier > 0
 * @invar The damage of the weapon must be effective.
 * | canHaveAsDamage(dmg)
 *
 * @author Elias Storme
 * @version 2.0
 */
public class Weapon extends Item implements hasParent{


     //TODO make it so weapons destroy themselves when trying to add to backpack but cannot carry under any circumstance, see 3.5


    /*****************************
     * Constants
     *****************************/

    /**
     * Constant specifying the maximum value the damage of a weapon may be.
     */
    private static int MAX_DMG = 100;

    /**
     * Constant specifying how many ducats a unit of damage is worth.
     */
    private static int VALUE_PER_DMG = 2;

    /*****************************
     * 3.0: Constructors
     *****************************/

    /**
     * Creates a new weapon with the given
     *
     * @param value
     *        Value of the weapon.
     * @param weight
     *        Weight of the weapon.
     * @param dmg
     *        Damage value for this weapon.
     *
     * @pre The given damage must be an effective value.
     * | canHaveAsDamage(dmg)
     */
    public Weapon(int value, Weight weight, int dmg) {
        super(value, weight);
        setDamage(dmg);
    }

    /*****************************
     * 3.1: Identification
     *****************************/

    /**
     * Getter for the IDGenerator for the weapon class.
     */
    @Override @Basic @Raw
    IDGenerator getIDGenerator() {
        return idGen;
    }

    /**
     * IDGenerator for the weapon class.
     * Generates multiples of six so the ID of the weapon is always an even, positive
     * integer divisible by three.
     */
    private static WeaponIDGenerator idGen = new WeaponIDGenerator();

    /*****************************
     * 3.3: Damage
     *****************************/

    /**
     * Checks if the given value is a valid damage value.
     * @param dmg
     *        Value to be checked.
     * @return True if the given value lies within the range [1,100]
     * and it is divisible by 7
     * | return dmg > 0 && dmg <= MAX_DMG && (dmg mod 7) == 0
     */
    @Immutable
    public boolean canHaveAsDamage(int dmg){
        return dmg > 0
                && dmg <= getMaxDamage()
                && (dmg % 7) == 0;
    }

    /**
     * Sets the damage of this weapon to the given value.
     * @param dmg
     *        Value to be set.
     * @pre The given damage must be an effective value.
     * | canHaveAsDamage(dmg)
     */
    @Basic @Raw
    public void setDamage(int dmg){
        this.dmg = dmg;
    }

    /**
     * Getter for the damage of this weapon.
     */
    @Basic @Raw
    public int getDamage(){
        return this.dmg;
    }

    /**
     * Getter for the maximum damage.
     * May be modified later to return a dynamic result.
     */
    @Basic @Raw @Immutable
    private int getMaxDamage(){
        return MAX_DMG;
    }

    /**
     * Variable for storing the damage this weapon can do.
     */
    private int dmg = 0;

    /*****************************
     * 3.4: Value
     *****************************/

    /**
     * Calculates the value of this weapon.
     *
     * @return Returns the product of the damage this weapon can do
     * and the value per damage.
     * | return getDamage * getValuePerDamage
     */
    @Override
    public int getValue() {
        return getDamage() * getValuePerDamage();
    }

    /**
     * Gets the amount of ducats a unit of damage is worth.
     * @return The constant specifying how much a unit of damage is worth.
     * | return VALUE_PER_DMG
     */
    @Basic @Raw @Immutable
    private int getValuePerDamage(){
        return VALUE_PER_DMG;
    }

    public boolean canHaveAsValue(int value){
        return value > 0 && value >= 200;
    }

    /*****************************
     * 3.5: Holder
     *****************************/

    /**
     * Retrieves the holder of this weapon.
     *
     * @return If the parent of this container is null, it means the container
     * is lying on the ground. As a result it cannot have a holder so
     * a null reference must be returned.
     * | if parent == null return null
     * If the container does have a parent, the parent is queried for it's
     * holder.
     * | else return parent.getHolder()
     */
    @Raw
    public Mobile getHolder() {
        if (parent == null) {
            return null;
        } else {
            return parent.getHolder();
        }
    }

    @Override
    public void setParent(Parent parent){

    }

    @Override
    public Parent getParent(){
        return this.parent;
    }

    private Parent parent;

}
