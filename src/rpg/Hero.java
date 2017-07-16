package rpg;

import rpg.inventory.AnchorType;
import rpg.value.Weight;

import java.util.EnumSet;

/**
 * Created by elias on 15/07/2017.
 */
public class Hero extends Mobile {

    public static EnumSet<AnchorType> ANCHORTYPES_HERO = EnumSet.of(
            AnchorType.back,
            AnchorType.belt,
            AnchorType.body,
            AnchorType.lhand,
            AnchorType.rhand
    );

    public Hero(String name){
        super(name)
    }

    /*****************************
     * 1.1: Name
     *****************************/

    /**
     * Checks whether the given string is an effective name for a hero.
     * @param name
     *        String to be checked.
     * @return False if there is a : without a space after it.
     *       | if
     * todo finish this shite
     */
    @Override
    public boolean isValidName(String name) {
        String paddedName = name + "_"; //padding string so no try catch has to be used in the for loop later
        char[] nameCharArray = paddedName.toCharArray();
        for (int i = 0; i < nameCharArray.length - 1; i++) {
            if(nameCharArray[i] == ':'){
                if(nameCharArray[i+1] != ' ') return false;
            }
        }
        int count = 0;
        for(char c : nameCharArray){
            if(c == '\'') count++;
        }
        if (count > 2) return false;
        name.replace("(: )", "");
        name.replace("'", "");
        return name.matches("^[A-Z][A-Za-z ]+");
    }

    /*****************************
     * 1.4: Capacity
     *****************************/

    public Weight getCapacity(){

    }
}
