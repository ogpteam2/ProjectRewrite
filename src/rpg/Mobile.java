package rpg;

import rpg.inventory.AnchorType;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Item;

import java.util.EnumMap;
import java.util.EnumSet;

public class Mobile {

    /**
     *
     * @param anchorTypes
     *
     * @post The mobile has an anchorpoint corresponding to each of the
     *       types specified in the set anchorTypes.
     *     | for each type in anchorTypes:
     *     |    anchorpoints.get(type) != null
     *
     */
    public Mobile(EnumSet<AnchorType> anchorTypes){
        anchorpoints = new EnumMap<AnchorType, Anchorpoint>(AnchorType.class);
        for(AnchorType type: anchorTypes){
            anchorpoints.put(type, new Anchorpoint(this, type));
        }
    }

    /*****************************
     * Anchorpoints
     *****************************/

    /**
     * Retrieves the
     * @param type
     * @return
     */
    public Anchorpoint getAnchorpoint(AnchorType type){
        return this.anchorpoints.get(type);
    }

    /**
     * Adds the item to the anchorpoint of the given type.
     *
     * @param type
     *        What anchorpoint to add the item to.
     * @param item
     *        The item to add to the anchorpoint.
     *
     *
     * @pre The anchorpoint may not have an item in it as dictated by
     *      it's precondition.
     */
    public void addItemToAnchorpoint(AnchorType type, Item item){
        getAnchorpoint(type).addItem(item);
    }

    /**
     * Retrieves all empty anchorpoints this
     * @return
     */
    public EnumMap<AnchorType, Anchorpoint> getEmptyAnchorpoints(){
        EnumMap<AnchorType, Anchorpoint> emptyAnchorPoints = new EnumMap<AnchorType, Anchorpoint>(AnchorType.class);
        for(AnchorType type : anchorpoints.keySet()){
            if(anchorpoints.get(type).containsItem()){
                emptyAnchorPoints.put(type,anchorpoints.get(type));
            }
        }
    }

    private final EnumMap<AnchorType, Anchorpoint> anchorpoints;
}
