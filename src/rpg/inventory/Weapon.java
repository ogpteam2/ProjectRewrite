package rpg.inventory;

import rpg.IDGeneration.IDGenerator;
import rpg.IDGeneration.WeaponIDGenerator;
import rpg.Mobile;
import rpg.value.Weight;

/**
 * Created by elias on 08/07/17.
 */
public class Weapon extends Item {

    public Weapon(int value, long identifier) {
        super(value, Weight.kg_0);
    }

    public int getValue() {
        return 0;
    }

    public boolean canHaveAsValue(int value) {
        return false;
    }

    /*****************************
     * Identification
     *****************************/

    @Override
    IDGenerator getIDGenerator() {
        return idGen;
    }

    private static WeaponIDGenerator idGen = new WeaponIDGenerator();

    /*****************************
     * Holder
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
    public Mobile getHolder() {
        if (parent == null) {
            return null;
        } else {
            return parent.getHolder();
        }
    }

    private Parent parent;

}
