package rpg.value;

import be.kuleuven.cs.som.annotate.*;
import java.math.*;
import java.util.EnumMap;

/**
 * An enumeration introducing different units of mass used to express
 * amounts of mass.
 * @note In its current form, the class only supports the kilogram,
 * the gram and the pound(lbs).
 * 
 * @version 1.0
 * @author Robbe, Elias
 *
 */
@Value
public enum Unit {
	
	kg("kilogram"), g("gram"),lbs("pound");    
	
	/**
	 * Initialize this unit with the given unit.
	 *
	 * @param unit
	 * 		  The unit for this new unit.
	 * @post The unit for this new unit is equal to the given unit.
	 * 		 | new.getUnit() == unit	
	 */
	@Raw
	private Unit(String unit){
		this.unit = unit;
	}
	
	/**
	 * Return the unit for this unit.
	 */
	@Basic @Raw @Immutable
	public String getUnit(){
		return this.unit;
	}
	
	/**
	 * Return the value of 1 unit to the other unit.
	 * 
	 * @param other
	 * 		  The unit to convert to.
     * @pre The given unit other must be effective.
     *    | other != null
	 * @return The resulting conversion rate is positive.
	 * 		   | result.signum == 1 
	 * @return If the unit is the same as the other
	 * 		   BigDecimal.ONE is returned.
	 * 		   | if (this == other)
	 *         | then (result == BigDecimal.ONE)
	 * @return If the unit is not the same as the other the 
	 * 		   resulting conversion rate has the precision as 
	 * 		   established by the unit context.
	 * 	       | if (this != other)
	 * 		   | then (result.precision() ==
	 * 		   | 			unitContext.getPrecision())
	 * @return The resulting conversion rate is the inverse of the unit
	 * 		   conversion rate from the other unit to this unit.
	 * 		   | result.equals
	 * 		   |	(BigDecimal.ONE.divide(other.toUnit(this).unitContext))
	 */
	public BigDecimal toUnit(Unit other)
	{
	    assert other != null;
		return conversionRates[this.ordinal()][other.ordinal()];
	}
	

	/**
	 * Variable referencing a two-dimensional array registering
	 * conversion rates between units. The first level is indexed
	 * by the ordinal number of the unit of the unit to convert from; 
	 * the second number to convert to is used to index the second level.
	 */
	private static BigDecimal[][] conversionRates = 
			new BigDecimal[3][3];
	
	static {
		// Initialization of the upper part of the conversion table.
		// Other conversions are computed and registered the first time 
		// they are queried.
		conversionRates[kg.ordinal()][kg.ordinal()]=
				BigDecimal.ONE;
		conversionRates[kg.ordinal()][g.ordinal()]=
				new BigDecimal(BigInteger.valueOf(1000),0);
        conversionRates[g.ordinal()][kg.ordinal()] =
                BigDecimal.ONE.divide(
                        conversionRates[kg.ordinal()][g.ordinal()]
                        ,12, RoundingMode.HALF_EVEN);
		conversionRates[kg.ordinal()][lbs.ordinal()]=
				new BigDecimal("220462262185").movePointLeft(11);
        conversionRates[lbs.ordinal()][kg.ordinal()] =
                BigDecimal.ONE.divide(
                        conversionRates[kg.ordinal()][lbs.ordinal()]
                        ,12, RoundingMode.HALF_EVEN);
		conversionRates[g.ordinal()][g.ordinal()]=
				BigDecimal.ONE;
		conversionRates[g.ordinal()][lbs.ordinal()]=
				new BigDecimal("220462262").movePointLeft(11);
		conversionRates[lbs.ordinal()][lbs.ordinal()]=
				BigDecimal.ONE;
        conversionRates[lbs.ordinal()][g.ordinal()] =
                BigDecimal.ONE.divide(
                        conversionRates[g.ordinal()][lbs.ordinal()]
                        ,12, RoundingMode.HALF_EVEN);
    }
	
	/**
	 * A variable storing the unit of this unit.
	 */
	private final String unit;

    /************************************************
     * Precision
     ************************************************/

    /**
     * Gets the decimal precision to round a weight with this unit with.
     */
    public int getPrecision(){
        return unitPrecisionMap.get(this);
    }

    /**
     * Map storing what precisions to use per unit.
     * The unit enum is mapped to an integer indicating what precision to use.
     */
    private static final EnumMap<Unit,Integer> unitPrecisionMap = new EnumMap<Unit,Integer>(Unit.class);
    static{
        unitPrecisionMap.put(Unit.kg,6);
        unitPrecisionMap.put(Unit.g,3);
        unitPrecisionMap.put(Unit.lbs,5);
    }
}
