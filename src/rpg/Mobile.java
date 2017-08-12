package rpg;

import rpg.exception.InvalidNameException;
import rpg.inventory.AnchorType;
import rpg.inventory.Anchorpoint;
import rpg.inventory.Item;
import rpg.utility.PrimeUtility;
import rpg.value.Strength;
import rpg.value.Weight;

import java.lang.reflect.Array;
import java.util.*;

public abstract class Mobile {

    /*****************************
     * Constructors
     *****************************/

    /**
     *
     * @param maximumHitpoints
     *        The maximum hitpoints this mobile can have.
     * @pre The given maximum hitpoints must be effective.
     * | canHaveAsHitpoints(maximumHitpoints)
     * @effect The maximum hitpoints are set to the given value.
     * @effect The current hitpoints are set to the maximum hitpoints.
     * @param anchorTypes
     * @post The mobile has an anchorpoint corresponding to each of the
     * types specified in the set anchorTypes.
     * | for each type in anchorTypes:
     * |    anchorpoints.get(type) != null
     * @note All
     */
    public Mobile(String name, int maximumHitpoints, EnumSet<AnchorType> anchorTypes) {
        //name
        setName(name);
        //hitpoints
        assert canHaveAsHitpoints(maximumHitpoints);
        this.maximumHitpoints = maximumHitpoints;
        setCurrentHitpoints(maximumHitpoints);
        //anchorpoints
        anchorpoints = new EnumMap<AnchorType, Anchorpoint>(AnchorType.class);
        for (AnchorType type : anchorTypes) {
            anchorpoints.put(type, new Anchorpoint(this, type));
        }
    }

    /*****************************
     * Name
     *****************************/

    /**
     * Setter for the name of this mobile.
     * @param name
     *        String the name is to be set to.
     * @throws InvalidNameException
     *         If the name is not effective, exception is thrown.
     *       | if !isValidName(name) throw InvalidNameException
     */
    public void setName(String name) throws InvalidNameException{
        if(!isValidName(name)) throw new InvalidNameException("Name is not allowed!");
        this.name = name;
    }

    /**
     * Getter for the name of this mobile.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Checks whether the given string is an effective name for a mobile.
     * @param name
     *        String to be checked.
     * @return Whether the given string is an effective name.
     *         Conditions to be defined on subclass level.
     */
    public abstract boolean isValidName(String name);

    /**
     * Variable for storing the name of this mobile.
     */
    private String name = null;

    /*****************************
     * Hitpoints - nominal
     *****************************/

    protected void die(){

    }

    public boolean isDead(){
        return this.dead;
    }

    private boolean dead = false;

    /**
     * Checks if the given value is effective for hitpoints.
     * @param hitpoints
     *        Value to be checked.
     * @return True if the given value is prime.
     * | return isPrime(hitpoints)
     */
    public boolean canHaveAsHitpoints(int hitpoints){
        return primeUtil.isPrime(hitpoints);
    }

    /**
     * Getter for the current hitpoints.
     */
    public int getCurrentHitpoints() {
        return currentHitpoints;
    }

    /**
     * Setter for the current hitpoints.
     * @param currentHitpoints
     *        Value the current hitpoints are to be set to.
     * @pre The given value must be effective for hitpoints.
     * | canHaveAsHitpoints(currentHitpoints)
     * @effect If the given value is effective, the current hitpoints are set.
     * | this.currentHitpoints = currentHitpoints
     */
    public void setCurrentHitpoints(int currentHitpoints) {
        assert canHaveAsHitpoints(currentHitpoints);
        this.currentHitpoints = currentHitpoints;
    }

    /**
     * Variable storing the current hitpoints of this mobile. The value stored here
     * should lie between 0 and maximumHitpoints.
     */
    private int currentHitpoints;

    /**
     * Getter for the maximum hitpoints.
     */
    public int getMaximumHitpoints() {
        return maximumHitpoints;
    }

    /**
     * Variable storing the maximum value for the hitpoints of this mobile. The hitpoints
     * of the mobile may never exceed this value.
     */
    private final int maximumHitpoints;

    /**
     * Utility for finding primes. It builds up an internal list of primes so calculating if a number is prime
     * only has to be done once. Making this object static ensures the list is shared among all instances of mobile.
     */
    protected static PrimeUtility primeUtil = new PrimeUtility();

    /*****************************
     * Anchorpoints
     *****************************/

    public ArrayList<Item> getItemList(){
        ArrayList<Item> itemList = new ArrayList<>();
        for (Anchorpoint a : getAnchorpoints()){
            if(a.containsItem())
                itemList.add(a.getContent());
        }
        return itemList;
    }

    /**
     * Retrieves the
     *
     * @param type
     * @return
     */
    public Anchorpoint getAnchorpoint(AnchorType type) {
        return this.anchorpoints.get(type);
    }

    /**
     * Adds the item to the anchorpoint of the given type.
     *
     * @param type What anchorpoint to add the item to.
     * @param item The item to add to the anchorpoint.
     * @pre The anchorpoint may not have an item in it as dictated by
     * it's precondition.
     */
    public void addItemToAnchorpoint(AnchorType type, Item item)
            throws IllegalArgumentException{
        getAnchorpoint(type).addItem(item);
    }

    /**
     * Retrieves all empty anchorpoints this mobile has.
     *
     * @return An arraylist with all anchorpoints that are empty.
     * | ArrayList&#60Anchorpoint&#62 empty
     */
    public EnumMap<AnchorType, Anchorpoint> getEmptyAnchorpoints() {
        EnumMap<AnchorType, Anchorpoint> emptyAnchorPoints = new EnumMap<AnchorType, Anchorpoint>(AnchorType.class);
        for (AnchorType type : anchorpoints.keySet()) {
            if (anchorpoints.get(type).containsItem()) {
                emptyAnchorPoints.put(type, anchorpoints.get(type));
            }
        }
        return emptyAnchorPoints;
    }

    /**
     * Calculates the weight of all items held by this mobile.
     *
     * @return Sum of the total weights of the items held in the anchorpoints.
     * |sum = 0
     * |for each anchorpoint in anchorpoints:
     * |     sum += anchorpoint.getContent().getWeight()
     */
    public Weight getCurrentCarriedWeight() {
        Weight sum = Weight.kg_0;
        for (Anchorpoint point:
             anchorpoints.values()) {
            sum = sum.add(point.getWeightOfContent());
        }
        return sum;
    }

    /**
     * Checks if adding the given item to the current set of carried items will
     * exceed the carrying capacity of this mobile.
     * @param item
     *        Item to check.
     * @return False if the given item is a null reference.
     * | if item == null return false
     * @return Else adds the weight of the item and the current carried weight
     * together and checks if that exceeds the capacity.
     * | return exceedsCapacity(item.getWeight + getCurrentCarriedWeight)
     */
    public boolean exceedsCapacity(Item item){
        if (item == null) {
            return false;
        } else {
            Weight itemWeight = item.getWeight();
            Weight current = getCurrentCarriedWeight();
            return exceedsCapacity(current.add(itemWeight));
        }
    }

    /**
     * Checks if the given weight would exceed this mobile's carrying capacity.
     *
     * @param weight Given weight to check against capacity.
     * @return If the given weight is larger than the mobile's carrying capacity.
     * | getCapacity().compareTo(weight) == -1
     */
    public boolean exceedsCapacity(Weight weight) {
        return getCapacity().isSmallerThan(weight);
    }

    protected final Collection<Anchorpoint> getAnchorpoints(){
        return this.anchorpoints.values();
    }

    private final EnumMap<AnchorType, Anchorpoint> anchorpoints;

    /*****************************
     * Strength
     *****************************/

    /**
     * Getter for strength.
     */
    public Strength getStrength() {
        return strength;
    }

    /**
     * Sets the strength to a multiple of the current strength.
     * @param factor
     *        Multiplication factor.
     * @effect Sets the strength to the current strength multiplied by the given factor.
     * | setStrength(getStrength().multiply(factor))
     */
    public void multiplyStrength(int factor){
        setStrength(getStrength().multiply(factor));
    }

    /**
     * Sets the strength to a fraction of the current strength.
     * @param divisor
     *        Value to divide by.
     * @effect Sets the strength to the current strength divided by the given divisor.
     * | setStrength(getStrength().divide(divisor))
     */
    public void divideStrength(int divisor){
        setStrength(getStrength().divide(divisor));
    }

    /**
     * Setter for strength.
     * @param strength
     *        Value the attribute strength is to be set to.
     * @effect If the given instance of Strength is effective, the attribute is set.
     * | if isValidStrength(strength)
     * |    this.strength = strength
     * @effect Else the attribute is set to no strength at all.
     * | this.strength = Strength.none
     * @note Private because strength should only be multiplied or divided by integers,
     * not directly set.
     */
    private void setStrength(Strength strength) {
        if (isValidStrength(strength)){
            this.strength = strength;
        } else {
            this.strength = Strength.none;
        }
    }

    /**
     * Checks if the given instance of Strength is effective for a Mobile.
     * @param strength
     *        Strength instance to be checked.
     * @return If the given Strength is not a null reference, return true.
     * | return strength != null
     */
    public boolean isValidStrength(Strength strength){
        return strength != null;
    }

    //todo specify
    private Strength strength = null;

    /*****************************
     * Hit
     *****************************/

    /**
     * Checks whether the given mobile can be attacked.
     * @param target
     *        Target mobile to be checked.
     * @return True if the given mobile can be attacked by this mobile.
     */
    public abstract boolean canAttack(Mobile target);

    /**
     * Generates a pseudo random number based on which hit success is
     * determined.
     * @return A pseudo random integer, of which specification is to be
     * determined in subclass.
     */
    public abstract int generateAttackSeed();

    /**
     * Calculates the amount of damage this mobile does when it gets a successfull hit.
     */
    public abstract int calculateDamage();

    /**
     * Specifies behavior upon death of the opponent when performing a hit.
     * To be executed after damage has been applied during hit algorithm.
     */
    public abstract void onOpponentDeath();

    /**
     * Applies the given damage to the
     * @param dmg
     */
    protected void applyDamage(int dmg){

    }

    public void hit(Mobile target) throws IllegalArgumentException{
        if(!canAttack(target))
            throw new IllegalArgumentException("Mobile cannot attack this target.");
        if(generateAttackSeed() >= target.getProtection()){
            applyDamage(calculateDamage());
            if(target.isDead()){
                onOpponentDeath();
            }
        }
        //else do nothing, hit did not succeed
    }

    /*****************************
     * Capacity
     *****************************/

    public abstract Weight getCapacity();

    /*****************************
     * Protection
     *****************************/

    public abstract int getProtection();
}
