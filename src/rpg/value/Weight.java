package rpg.value;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.EnumMap;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of weights with a given numeral and a unit.
 * 
 * @invar The numeral of each weight must be valid.
 * 		  | isValidNumeral(getNumeral())
 * @invar The numeral must be rounded to the following decimal places
 *        in accordance with it's unit:
 *        kg: 5, g: 2, lbs: 4
 *        | isValidRounding(getNumeral(), getUnit())
 * @note This invariant is enforced by rounding at initialisation as each mutator
 *       returns a newly constructed object with modified values.
 * @invar The unit of each weight must be a valid unit.
 * 		  | isValidUnit(getUnit())
 * @author Robbe, Elias (verion 1.0)
 * @author Elias Storme (rework to total programming)
 * @version 2.0
 */
@Value
public class Weight implements Comparable<Weight> {
	
	/************************************************
	 * Constructors
	 ************************************************/
	
	/**
	 * Initialize the new weight with given numeral and given unit.
	 * 
	 * @param numeral
	 * 		  The numeral for this new weight.
	 * @param unit
	 * 		  The unit for this new weight.
     * @post The unit may not contain a null reference. If this is the case, kg
     *       is set as the unit.
     *     | if(!isValidUnit(unit)) this.unit = Unit.kg
     * @post The numeral may not contain a null reference and must be positive. If
     *       this is not the case, zero is set as the numeral.
     *     | if(!isValidNumeral(numeral) this.numeral = Bigdecimal.ZERO
     * @post The numeral has to be rounded to the correct number of decimal places
     *     | isValidRounding(this.numeral)
	 */
	@Raw
	public Weight(BigDecimal numeral, Unit unit)
	{
	    //set unit
	    if(isValidUnit(unit)) this.unit = unit;
	    else this.unit = Unit.kg;

	    BigDecimal provisionalNumeral;
	    //check numeral validity
		if(isValidNumeral(numeral)) provisionalNumeral = numeral;
		else provisionalNumeral = BigDecimal.ZERO;
		//check rounding
		if(!isValidRounding(provisionalNumeral,getUnit())) this.numeral =
                provisionalNumeral.round(getContext()).stripTrailingZeros();
		else this.numeral = provisionalNumeral;
	}
	
	/**
	 * Initialize this new weight with given numeral and unit "kg".
	 * 
	 * @param numeral
	 * 		  The numeral for this new weight.
	 * @effect The new weight is initialized with the given numeral
	 *         and the unit "kg".
	 *       | this(numeral, Unit.kg)
	 */
	@Raw
	public Weight(BigDecimal numeral)
	{
		this(numeral,Unit.kg);
	}

    /**
     * Initialise a new weight with given numeral in integer form and the given unit.
     * @param numeral
     *        Numeral in integer form.
     * @param unit
     *        Unit for this weight.
     * @effect A new weight is initialised with as numeral the Bigdecimal equivalent of the
     *         given integer value.
     *      | this(new Bigdecimal(numeral), unit)
     */
	public Weight(int numeral, Unit unit){
		this(new BigDecimal(numeral), unit);
	}

    /**
     * Variable referencing a weight of 0.0 kg
     */
    public final static Weight kg_0 =
            new Weight(BigDecimal.ZERO,Unit.kg);

	/************************************************
	 * Value
	 ************************************************/
	
	/**
	 * Return the numeral of this capacity amount.
	 */
	@Basic @Raw @Immutable
	public BigDecimal getNumeral(){
		return this.numeral;
	}

    /**
     * Checks if the numeral value of this weight is valid.
     * @param numeral
     *        Bigdecimal numeral to be checked.
     * @return If the numeral is positive and not a null reference, return true
     *         return numeral != null && numeral >= 0
     */
	private static boolean isValidNumeral(BigDecimal numeral){
	    return numeral != null && numeral.signum() != -1;
    }

    /**
     * Checks if the given numeral has the correct rounding in accordance
     * with the unit.
     * @param numeral
     *        Numeral of which the rounding is to be checked.
     * @param unit
     *        Unit to check the rounding against.
     * @return Return true if the number of decimal places in the numeral matches the
     *         rounding specified by the unit.
     *       | numeral.scale() == unit.getPrecision()
     *
     */
    private static boolean isValidRounding(BigDecimal numeral, Unit unit){
        return numeral.scale() == unit.getPrecision();
    }

    /**
	 * A variable that references the numeral of this weight.
	 */
	private final BigDecimal numeral;
	
	/************************************************
	 * Unit
	 ************************************************/
	
	/**
	 * Returns the unit of this weight.
	 */
	@Basic @Raw @Immutable
	public Unit getUnit(){
		return this.unit;
	}
	
	/**
	 * Checks whether a given unit is valid for any weight.
	 * 
	 * @param unit
	 *        The unit to check
	 * @return True if the given unit is effective.
	 *         | result == (! unit == null)
	 */
	public static boolean isValidUnit(Unit unit) {
		return (unit != null);
	}

	/**
	 * Return a capacity amount that has the same value as this weight
	 * exxpressed in the given unit.
	 * 
	 * @param unit
	 *        The unit in which to current weight will be converted.
	 * @return If the given unit is not effective, convert to kg.
	 *       | if(!isValidUnit(unit)) return toUnit(Unit.kg)
	 * @return If the given unit is the same as the unit of the prime object,
     *         do nothing and just return the prime object.
     *       | if(unit = getUnit()) return this
     * @return Else convert the weight to the given unit by multiplying it
     *         by the conversionrate dictated by the unit enum, then rounding
     *         for satisfaction of the class invariant.
     *       | let
     *       |     conversionRate = this.getUnit().toUnit(unit)
     *       |     convertedNumeral = this.getNumeral().multiply(conversionRate)
     *       | in
     *       |     return new Weight(convertedNumeral, unit)
     *
	 */
	public Weight toUnit(Unit unit)
	{
		if(!isValidUnit(unit))
		    return this.toUnit(Unit.kg);
		if (this.getUnit() == unit)
			return this;
		BigDecimal conversionRate = 
				this.getUnit().toUnit(unit);
		BigDecimal convertedNumeral =
				getNumeral().multiply(conversionRate);
		return new Weight(convertedNumeral,unit);
	}
	
	/**
	 * Variable referencing the unit of this weight.
	 */
	private final Unit unit;

    /************************************************
     * Rounding & precision
     ************************************************/

    /**
     * Retrieves the mathcontext to be used when rounding the numeral.
     *
     * @return Mathcontext object determining the way the numeral of the weight class is
     * rounded and what precision it has.
     *
     * The precision is set according to the unit that is being used. As the gram unit is
     * 1000 times smaller than the kg unit it's rounding has to be done to 3 decimal places less.
     *
     * The rounding is done using the half even method, dictating that rounding is
     * done to the nearest neighbour and the even neighbour if equidistant.
     */
    @Immutable
    protected MathContext getContext(){
        return new MathContext(
                getUnit().getPrecision(),
                RoundingMode.HALF_EVEN
        );
    }

	/************************************************
	 * Utility
	 ************************************************/
	
	/**
	 * Return the hash code for this weight.
	 */
	@Override
	public int hashCode(){
		return getNumeral().hashCode() + getUnit().hashCode();
	}
	
	/**
	 * Return a textual representation of this weight.
	 * 
	 * @return A string consisting of the textual representation
	 * 		   of the numeral of this weight, followed by
	 * 		   the textual representation of its unit, separated by a space
	 * 		   and enclosed in square brackets.
	 * 	     | result.equals("[" + getNumeral().toString() +
	 * 		 |      " " + getUnit().toString() + "]")
	 */
	@Override
	public String toString(){
		return "[" + getNumeral().toString() + " " + getUnit().toString() + "]";
	}

	/************************************************
	 * Logical operations
	 ************************************************/

	/**
	 * Compare this weight to another.
	 * 
	 * @param other
	 *        The other weight to compare with this one.
     * @return Zero if other contains a null reference.
     *       | if other == null return 0
	 * @return If the units are the same, the result is equal to the
     *         comparison between this numeral and the other numeral.
	 *       | if getUnit() == other.getUnit()
     *       |    return getNumeral().compareTo(other.getNumeral())
     * @return If the units are not the same, convert the other weight
     *         to the unit of the prime object and compare.
     *       | else
     *       |    return compareTo(other.toUnit(this.getUnit()))
     */
	@Override
	public int compareTo(Weight other)
	{
		if(other == null) return 0;
		if(getUnit() == other.getUnit())
		    return getNumeral().compareTo(other.getNumeral());
		else
		    return this.compareTo(other.toUnit(this.getUnit()));
	}
	
	/**
	 * Checks whether this weight and the other represent the same physical weights.
	 * 
	 * @param other
	 * 		  The other weight to compare this with.
	 * @return If other contains a null reference, return false.
     *       | if other == null return false
     * @return If the units of both weights are the same, compare the numerals.
     *       | if this.getUnit() == other.getUnit()
     *       |    return this.hasSameNumeral(other)
     * @return Else convert other to the same unit as this, then compare numerals.
     *       | else
     *       |    return this.hasSameNumeral(other.toUnit(this.getUnit()))
	 */
	public boolean hasSameValue(Weight other)
	{
        if(other == null) return false;
        if(this.getUnit() == other.getUnit())
            return this.hasSameNumeral(other);
        else
            return this.hasSameNumeral(other.toUnit(this.getUnit()));
	}

    /**
     * Checks whether the numerals of this and other are the same.
     * @note This operation only compares the numerical values of the weights,
     *       not the weight it represents.
     *
     * @param other
     *        Other weight to be compared
     * @pre other may not be a null reference
     *    | other != null
     * @pre Both weights have to have the same unit.
     *    | this.getUnit() == other.getUnit()
     * @return The comparison between the numerals.
     *       | return this.getNumeral() == other.getNumeral()
     */
	private boolean hasSameNumeral(Weight other){
        return this.getNumeral().equals(other.getNumeral());
    }

	/**
	 * Checks whether this weight is equal to the given object.
	 * 
	 * @return True if the given object is effective, if this weight
	 * 		   and the given object belong to the same class. and if this 
	 * 		   weight and the other object interpreted as a 
	 * 		   weight have equal numerals and equal units.
	 * 		   | result == ( (other != null) && (this.getClass() == other.getClass() 
	 * 		   |              && (this.getNumeral().equals((capacityAmount other)
	 * 								.getNumeral())) && (this.getUnit() == 
	 * 									((capacityAmount other).getUnit()) )
	 */
	@Override
	public boolean equals(Object other){
		if (other == null)
			return false;
		if (this.getClass() != other.getClass())
			return false;
		Weight otherWeight = (Weight) other;
		return (hasSameNumeral(otherWeight) &&
				( this.getUnit() == otherWeight.getUnit()) );
	}
	
	/************************************************
	 * Arithmetic operations
	 ************************************************/
	
	/**
	 * Adds this weight to the given other weight.
     *
	 * @param other
     *        The weight to be added to the prime weight.
     * @return The prime weight if the other weight is a null reference
     *       | if other == null return this
	 * @return If both units are the same, a new weight is returned with as numeral
     *         the sum of the numerals of the prime weight and the given weight.
     *       | if this.getUnit() == other.getUnit()
     *       |    return.numeral = this.numeral + other.numeral
     * @return Else convert other to the unit of this weight and add them together.
     *       | return this.add(other.toUnit(this.getUnit()))
	 */
	public Weight add(Weight other){
		if(other == null) return this;
		if(this.getUnit() == other.getUnit())
		    return this.addNumerals(other);
		else
		    return this.addNumerals(other.toUnit(this.getUnit()));
	}

    /**
     * Adds the numerals of the prime weight and the given other together.
     * @param other
     *        Other weight to be added.
     * @pre other may not be a null reference
     * @pre both weights must have the same unit.
     * @return New weight with as numeral the sum of this and other's numerals.
     */
	private Weight addNumerals(Weight other){
        BigDecimal total = BigDecimal.ZERO;
        total = total.add(this.getNumeral());
        total = total.add(other.getNumeral());
        return new Weight(total, this.getUnit());
    }

    /**
     * Multiplies this weight by a bigdecimal factor.
     * @param factor
     *        Bigdecimal factor this weight's numeral is to be multiplied by.
     * @return If the given factor is a null reference, return a weight of zero with
     *         the same unit as the prime weight.
     *       | if factor == null
     *       |     return new Weight(0, this.getUnit())
     * @return If the factor is negative, multiply by the absolute value of the factor.
     *       | if factor < 0
     *       |     return this.multiply(abs(factor))
     * @return Else return a new weight with the numeral of the old weight multiplied by
     *         the given factor, keep the unit as it was.
     *       | let
     *       |     newNumeral = this.getNumeral().multiply(factor)
     *       | then
     *       |     return new Weight(newNumeral, getUnit())
     */
	public Weight multiply(BigDecimal factor){
		if(factor == null) return new Weight(0,this.getUnit());
		if(factor.signum() != -1){
            BigDecimal newNumeral = this.getNumeral().multiply(factor);
            return new Weight(newNumeral, this.getUnit());
        } else {
		    return this.multiply(factor.abs());
        }
	}

    /**
     * Multiplies this weight by an integer factor.
      * @param factor
     *         Integer to multiply the weight by.
     * @return Converts the integer to a BigDecimal, then calculates the
     *         multiple with method multiply(BigDecimal)
     *       | return multiply(new BigDecimal(factor))
     */
    public Weight multiply(int factor){
        return multiply(new BigDecimal(factor));
    }
	
}
