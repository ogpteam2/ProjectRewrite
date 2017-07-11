package rpg.inventory;

import rpg.value.Weight;

/**
 * Created by elias on 08/07/17.
 */
public class Weapon extends Item{

    public Weapon(int value, long identifier) {
        super(value, Weight.kg_0, identifier);
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public boolean canHaveAsValue(int value) {
        return false;
    }

}
