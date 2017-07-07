package rpg.value;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of weights with a given numeral and a unit.
 * 
 * @invar The numeral of each weight must be valid.
 * 		  | isValidNumeral(getNumeral())
 * @invar The unit of each weight must be a valid unit.
 * 		  | isValidUnit(getUnit())
 * @author Robbe, Elias
 * @version 1.0
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
	 * @post  The numeral of this new capacity amount is equal to 
	 * 		  the given numeral rounded (using half down) to a decimal 
	 * 		  number with 1 fractional digits.
	 * 		  | let roundedNumeral = numeral.roud(getContextForScale1(numeral))
	 * 		  | in new.getNumeral().equals(numeral))
	 * @post The unit for this new weight is the same as the given unit.
	 * 		 | new.getUnit() == unit
	 * @throws IllegalArgumentException
	 *   	   The given numeral is not effective.
	 *   	   | numeral == null
	 * @throws IllegalArgumentException
	 *    	   The given unit is not a valid unit for any weight.
	 *         | ! isValidUnit()
	 */
	@Raw
	public Weight(BigDecimal numeral, Unit unit)
		throws IllegalArgumentException
	{
		if (numeral == null)
			throw new IllegalArgumentException("Non-effective numeral");
		if (! isValidUnit(unit))
			throw new IllegalArgumentException("Invalid unit");
		if (numeral.scale() != 1)
			numeral = numeral.round(getContextForScale2(numeral));
		this.numeral = numeral;
		this.unit = unit;
	}
	
	/**
	 * Initialize this new weight with given numeral and unit "kg".
	 * 
	 * @param numeral
	 * 		  The numeral for this new weight.
	 * @effect The new weight is initialized with the given numeral
	 *         and the unit "kg".
	 *         | this(numeral, Unit.kg)
	 */
	@Raw
	public Weight(BigDecimal numeral)
			throws IllegalArgumentException
	{
		this(numeral,Unit.kg);
	}
	
	public Weight(int weight, Unit unit){
		this(new BigDecimal(weight), unit);
	}
	
	/**
	 * Initialize a new weight with value zero and unit kg
	 */
	public Weight(){
		this(BigDecimal.ZERO, Unit.kg);
	}
	
	/************************************************
	 * Value
	 ************************************************/
	
	/**
	 * Variable referencing a weight of 0.0 kg
	 */
	public final static Weight kg_0 = 
			new Weight(BigDecimal.ZERO,Unit.kg);
	
	/**
	 * Return the numeral of this capacity amount.
	 */
	@Basic @Raw @Immutable
	public BigDecimal getNumeral(){
		return this.numeral;
	}
	
	/**
	 * Checks whether the given numeral is a valid numeral for any weight.
	 * 
	 * @param numeral
	 * 		  The numeral to check.
	 * @return True iff the given numeral is effective and if it has a scale of 2.
	 * 		   | result ==
	 * 		   | 	( (numeral != null)
	 * 		   |    && (numeral.scale() ==2) )
	 */
	public static boolean isValidNumeral(BigDecimal numeral){
		return (numeral != null) && (numeral.scale() == 2); 
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
	 * @return The resulting weight has the given unit as its unit.
	 *         | result.getUnit() == unit
	 * @return The numeral of the resulting weight is equal to the numeral
	 * 		   of this weight multiplied with the conversion rate from the 
	 *         unit of this weight to the given unit rounded half down to a
	 *         scale of 2.
	 *         | let conversionRate = this.getUnit().toUnit(unit),
	 *         |     numeralInCurreny = this.getNumeral().multiply(conversionRate)
	 *         | in result.getNumeral().equals
	 *         |      (numeralInUnit.round(getContextForScale2(numeralInUnit))
	 * @throws IllegalArgumentException
	 * 		   The given unit is not effective
	 *  	   | unit == null
	 */       
	public Weight toUnit(Unit unit)
		throws IllegalArgumentException
	{
		if (unit == null)
			throw new IllegalArgumentException
						("Non-effective unit");
		if (this.getUnit() == unit)
			return this;
		BigDecimal conversionRate = 
				this.getUnit().toUnit(unit);
		BigDecimal numeralInUnit =
				getNumeral().multiply(conversionRate);
		numeralInUnit =
				numeralInUnit.round(getContextForScale2(numeralInUnit));
		return new Weight(numeralInUnit,unit);
	}
	
	/**
	 * Variable referencing the unit of this weight.
	 */
	private final Unit unit;
	
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
	 * 	       |result.equals("[" + getNumeral().toString() +
	 * 		   |		 " " + getUnit().toString() + "]")
	 */
	@Override
	public String toString(){
		return "[" + getNumeral().toString() + " " + getUnit().toString() + "]";
	}
	
	/**
	 * Return a mathematical context to round the given big decimal to 2 
	 * fractional digits.
	 * 
	 * @param value
	 * 		  The value to compute a mathematical context for.
	 * @pre The given value must be effective.
	 * 		| value != null
	 * @return The precision of the resulting mathematical context is 
	 * 		   equal to the precision of the given value diminished with its
	 * 		   scale and incremented by 2.
	 *        | result.getPrecision() == value.precision()-value.scale()+2
	 * @return The resulting mathematical context uses HALF_DOWN as its rounding mode.
	 *         | result.getRoudingMode == RoundingMode.HALF_DOWN
	 */
	@Model
	static MathContext getContextForScale2(BigDecimal value) {
		assert value != null;
		return new MathContext(value.precision()-value.scale()+2,
				RoundingMode.HALF_DOWN);
	}
	
	/************************************************
	 * Logic
	 ************************************************/

	/**
	 * Compare this capacity amount with another weight.
	 * 
	 * @param other
	 *        The other weight to compare with this one.
	 * @return The result is equal to the comparison between this numeral
	 * 		   and the other numeral.
	 *         | result == getNumeral().compareTo(other.getNumeral())
	 * @throws  ClassCastException
	 *         the other weight is not effective or this weight
	 *         and the other use different units.
	 *		   | ( (other == null) || (this.getUnit() != other.getUnit()) )
	 */
	@Override
	public int compareTo(Weight other)
		throws ClassCastException
	{
		if (other == null)
			throw new ClassCastException("Non-effective weight");
		if (getUnit() != other.getUnit())
			throw new ClassCastException("Incompatible units");
		return getNumeral().compareTo(other.getNumeral());
	}
	
	/**
	 * Checks whether this weight has the same value as the other one.
	 * 
	 * @param other
	 * 		  The other weight to compare this with.
	 * @return True iff this weight is equal to the other one
	 *         expressed in the unit of this weight.
	 *        | result == this.equals(other.toUnit(getUnit()) 
	 * @throws IllegalArgumentException
	 * 		   The other capacity amount is not effective.
	 * 		   | other == null
	 */
	public boolean hasSameValue(Weight other)
		throws IllegalArgumentException
	{
		if (other == null)
			throw new IllegalArgumentException("Non-effective capacity amount");
		return this.equals(other.toUnit(this.getUnit()));
	}
	
	/**
	 * Checks whether this weight is equal to the given object.
	 * 
	 * @return True iff the given object is effective, if this weight
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
		Weight otherAmount = (Weight) other;
		return (this.getNumeral().equals(otherAmount.getNumeral()) &&
				( this.getUnit() == otherAmount.getUnit()) );
	}
	
	/************************************************
	 * Arithmetic
	 ************************************************/
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public Weight add(Weight other){
		BigDecimal total = BigDecimal.ZERO;
		total.add(this.getNumeral());
		total.add(other.toUnit(this.getUnit()).getNumeral());
		return new Weight(total);
	}
	
	public Weight multiply(BigDecimal factor){
		BigDecimal result = this.getNumeral();
		result = result.multiply(factor);
		return new Weight(result, this.getUnit());
	}
	
	public Weight multiply(double factor){
		return multiply(new BigDecimal(factor));
	}
	
}
