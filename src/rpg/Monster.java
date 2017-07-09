package rpg;

import rpg.inventory.AnchorType;
import rpg.value.Weight;

import java.util.EnumSet;

/**
 * Created by elias on 08/07/17.
 */
public class Monster extends Mobile {

    /**
     *
     * @param anchorPoints
     *        An enumset of the AnchorType enum specifying which
     *        types of anchorpoints this monster has.
     *
     * @post
     *
     */
    public Monster(EnumSet<AnchorType> anchorPoints) {
        super(anchorPoints);

    }

    @Override
    public Weight getCapacity() {
        return null;
    }


}
