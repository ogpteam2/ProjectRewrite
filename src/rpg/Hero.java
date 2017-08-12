package rpg;

import rpg.inventory.AnchorType;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Parent;
import rpg.inventory.Weapon;
import rpg.value.Strength;
import rpg.value.Weight;
import sun.plugin.javascript.navig.Anchor;
import sun.plugin.javascript.navig.Array;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by elias on 15/07/2017.
 */
public class Hero extends Mobile {

    /*****************************
     * Constants
     *****************************/

    /**
     * Constant specifying what protection factor a hero should have.
     */
    private static final int PROTECTION = 10;

    /**
     * Enumset specifying what types of Anchorpoints a hero should have.
     */
    private static final EnumSet<AnchorType> ANCHORTYPES_HERO = EnumSet.of(
            AnchorType.back,
            AnchorType.belt,
            AnchorType.body,
            AnchorType.lhand,
            AnchorType.rhand
    );

    /*****************************
     * 1.10: Constructors
     *****************************/

    public Hero(String name){
        super(name, , ANCHORTYPES_HERO);
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
     * Calculates the carrying capacity this mobile has.
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

    /*****************************
     * 1.5: Protection
     *****************************/

    /**
     * Gets the protection factor for this hero.
     * @return the constant specifying the amount of protection a hero has.
     */
    @Override
    public int getProtection(){
        return PROTECTION;
    }

    /*****************************
     * 1.7: Hit
     *****************************/

    /**
     * Checks whether the given mobile can be attacked.
     * @param target
     *        Target mobile to be checked.
     * @return True if the given mobile is an instance of monster.
     * | return target intanceof Monster
     */
    @Override
    public boolean canAttack(Mobile target){
        return target instanceof Monster;
    }

    /**
     * Generates a pseudorandom number based on which hit success is
     * determined.
     * @return Pseudorandom integer between 0 and 20, generated by built in function nextInt
     * from the Random class. Given bound value is 21 as that bound is exclusive.
     * | return nextInt(21)
     */
    @Override
    public int generateAttackSeed() {
        Random ranGen = new Random();
        return ranGen.nextInt(21);
    }

    /**
     * Calculates the amount of damage this hero does when it gets a successfull hit.
     * @return Let dmg be the strength of the hero plus the damage of all weapons held,
     * subtracted by 10 and divided by two.
     * | let:
     * |    weaponDmg = weapon1.getDamage + weapon2.getDamage + ...
     * |    dmg = ((getStrength() + weaponDmg) - 10)/2
     * @return If dmg is larger than 0, return dmg. Else return 0.
     * | if(dmg > 0) return dmg
     * | else return 0
     */
    @Override
    public int calculateDamage() {
        int totalWeaponDmg = 0;
        //Adds all damage values of the weapons held in anchorpoints together.
        for (Anchorpoint a : this.getAnchorpoints()){
            totalWeaponDmg +=
                    //if the anchorpoint contains a weapon, add to total, else add 0.
                    a.containsWeapon() ? ((Weapon) a.getContent()).getDamage() : 0;
        }
        Strength attackDamage = getStrength().add(totalWeaponDmg);
        attackDamage = attackDamage.add(-10);
        if (attackDamage.getNumeral().signum() == -1){
            return 0;
        } else {
            attackDamage = attackDamage.divide(2);
            //rounding mode discarding everything after the decimal point.
            MathContext floor = new MathContext(0, RoundingMode.FLOOR);
            //rounds numeral using floor, converts to int, returns result.
            return attackDamage.getNumeral().round(floor).intValue();
        }
    }

    @Override
    public void onOpponentDeath() {
        heal();

    }

    /*****************************
     * 1.8: Heal
     *****************************/

    /**
     * Heals the hero by a random amount.
     * @effect Calculates the difference between the maximum hitpoints and the current hitpoints,
     * multiplies that by a random percentage. Rounds that amount, adds it to the current hitpoints.
     * The current hitpoints are then set to the closest prime to that value.
     * | let:
     * todo specify this better.
     */
    private void heal(){
        int hpDiff = getMaximumHitpoints() - getCurrentHitpoints();
        Random random = new Random();
        float percentage = random.nextFloat();
        float healAmount = hpDiff * percentage;
        int roundedHealAmount = Math.round(healAmount);
        int nextHP = primeUtil.closestPrime(roundedHealAmount + getCurrentHitpoints());
        setCurrentHitpoints(nextHP);
    }

    /*****************************
     * 1.9: Collect treasures
     *****************************/

    public void collectAllTreasures(){
        int len = getItemList().size();
        boolean[] itemSelection = new boolean[len];
        Arrays.fill(itemSelection, true);

    }

    /**
     * Collect
     * @param itemSelection
     */
    private void collectTreasures(boolean[] itemSelection, Parent[] destinations){

    }
}
