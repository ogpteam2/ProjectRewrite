package rpg.inventory;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import rpg.utility.IDGenerator;
import rpg.utility.WeaponIDGenerator;
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
    private final static int MAX_DMG = 100;

    /**
     * Constant specifying how many ducats a unit of damage is worth.
     */
    private final static int VALUE_PER_DMG = 2;

    /*****************************
     * 3.0: Constructors
     *****************************/

    /**
     * Creates a new weapon with the given
     *
     * @param weight
     *        Weight of the weapon.
     * @param dmg
     *        Damage value for this weapon.
     *
     * @pre The given damage must be an effective value.
     * | canHaveAsDamage(dmg)
     */
    public Weapon(Weight weight, int dmg) {
        super(weight);
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
     * @return If the weapon is destroyed, return 0
     * | if isDestroyed() return 0
     * @return Else act as getter for dmg
     */
    @Basic @Raw
    public int getDamage(){
        if(isDestroyed()) return 0;
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
     * @return If the weapon is destroyed, return 0.
     * | if isDestroyed return 0
     * @return Else returns the product of the damage this weapon can do
     * and the value per damage.
     * | return getDamage * getValuePerDamage
     */
    @Override
    public int getValue() {
        if(isDestroyed()) return 0;
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

    public boolean hasParent(){
        return getParent() != null;
    }

    /**
     * Sets the parent for this weapon.
     * @pre The parent has to contain a reference to this weapon.
     * @param parent
     *        Reference to set the parent attribute to.
     * @effect Sets the attribute parent to the given instance of Parent.
     */
    @Override @Basic @Raw
    public void setParent(Parent parent){
        this.parent = parent;
    }

    /**
     * Getter for the parent of this weapon.
     */
    @Override @Basic @Raw
    public Parent getParent(){
        return this.parent;
    }

    /**
     * Specifies what a weapon should do when being dropped by a parent.
     * @pre The weapon should have a parent.
     * | hasParent()
     * @effect The weapon's parent is set to null, effectively dropping it to the ground.
     * | setParent(null)
     * @effect The weapon destroys itself as it can't exist on the ground after being held
     * inside a container.
     * | destroy()
     */
    public void drop(){
        assert hasParent();
        setParent(null);
        destroy();
    }

    /**
     * Variable storing a reference to the parent of this weapon. Null if the weapon is lying
     * on the ground.
     */
    private Parent parent;

    /*****************************
     * Destructor
     *****************************/

    /**
     * Destroys the weapon rendering it unusable.
     * @effect The attribute destroyed is set to true.
     * | this.destroyed = true
     * @effect The weapon has no damage anymore.
     * | getDamage() == 0
     * @effect The weapon has no value anymore.
     * | getValue() == 0
     */
    @Raw
    private void destroy(){
        this.destroyed = true;
    }

    /**
     * Getter for the destroyed attribute.
     */
    @Raw
    @Basic
    public boolean isDestroyed(){
        return destroyed;
    }

    /**
     * Boolean storing whether the weapon has been destroyed.
     */
    private boolean destroyed = false;

}
