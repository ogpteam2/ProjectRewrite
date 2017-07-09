package rpg.inventory;

/**
 * Created by elias on 08/07/17.
 */
public class Weapon extends Item{

    public Weapon(int value, long identifier) {
        super(value, identifier);
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public boolean canHaveAsValue() {
        return false;
    }
}
