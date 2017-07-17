package rpg;

import rpg.inventory.AnchorType;
import rpg.value.Strength;
import rpg.value.Unit;
import rpg.value.Weight;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.EnumSet;
import java.util.HashMap;

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
        super(name, ANCHORTYPES_HERO);
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

    private static MathContext ceiling = new MathContext(0,RoundingMode.FLOOR);

    /**
     * HashMap mapping the ceiling value of strength to the maximum carrying capacity for
     * strength values between [ceiling - .99, ceiling]. See table below for exact values.
     * _______________________
     * |   Range    | Weigth |
     * ----------------------|
     * | 10.01 - 11 |  115   |
     * | 11.01 - 12 |  130   |
     * | 12.01 - 13 |  150   |
     * | 13.01 - 14 |  175   |
     * | 14.01 - 15 |  200   |
     * | 15.01 - 16 |  230   |
     * | 16.01 - 17 |  260   |
     * | 17.01 - 18 |  300   |
     * | 18.01 - 19 |  350   |
     * | 19.01 - 20 |  400   |
     * -----------------------
     */
    private static HashMap<Integer, Integer> tenToTwentyMap = new HashMap<>(10);

    /**
     * Fills the HashMap according to table above.
     */
    static {
        int[] values = {115, 130, 150, 175, 200, 230, 260, 300, 350, 400};
        for (int i = 11; i <= 20; i++) {
            tenToTwentyMap.put(i,values[i - 11]);
        }
    }

    /**
     * Calculates the capacity this mobile has.
     * @return The capacity calculated based on the mobile's strength.
     * | return getCapacity(getStrength())
     */
    public Weight getCapacity(){
        return getCapacity(getStrength());
    }

    /**
     * Calculates the carrying capacity for a given amount of strength.
     * @param strength
     *        Strength value on which the carrying capacity will be based.
     * @return If the strength value of the mobile is smaller than 1 it cannot carry anything.
     * | if strength <= 1
     * |    return Weight.kg_0
     * @return If the strength value of the mobile is larger or equal to 1 and smaller or equal to 10,
     * the mobile can carry a weight in kg 10 times larger than it's strength.
     * | else if strength <= 10
     * |    return new Weight(strength * 10)
     * @return If the strength value of the mobile is larger than 10 and smaller or equal to 20, the
     * mobile can carry a weight in kg defined by the tenToTwentyMap. It maps the ceiling of the strength
     * value to the maximum carrying capacity, so that a value in ]ceiling - 1, ceiling] gets mapped to it.
     * | else if strength <= 20
     * |    let:
     * |        celing = strength.ceiling()
     * |        numeral = tenToTwentyMap.get(ceiling)
     * |    then:
     * |        return new Weight(numeral)
     * @return If the strength value lies above 20, the carrying capacity is defined by multiplying the
     * carrying capacity for the strength value subtracted by 10, multiplied by 4.
     * | return getCapacity(strength - 10) * 4
     *
     * @note A method with an explicit value for strength has been created in order to
     * be able to use recursion for values of strength larger than 20.
     */
    private Weight getCapacity(Strength strength){
        double strengthAsDouble = strength.getNumeral().doubleValue();
        if (strengthAsDouble < 1){
            return Weight.kg_0;
        } else if (strengthAsDouble <= 10) {
            Weight base = new Weight(10);
            return base.multiply(strength);
        } else if (strengthAsDouble <= 20) {
            int floorNumeral = strength.getNumeral().round(ceiling).intValue();
            return new Weight(tenToTwentyMap.get(floorNumeral));
        } else {
            BigDecimal newNumeral =
                    strength.getNumeral()
                            .subtract(new BigDecimal(10));
            Strength newStrength = new Strength(newNumeral);
            return getCapacity(newStrength).multiply(4);
        }
    }
}
