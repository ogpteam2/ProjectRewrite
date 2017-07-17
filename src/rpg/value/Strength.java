package rpg.value;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by elias on 16/07/2017.
 */
public class Strength implements Comparable<Strength>{

    /*****************************
     * Constants
     *****************************/

    /**
     * Constant specifying the precision to be used when rounding strength values.
     * Determines the number of decimal places.
     */
    public static int PRECISION = 2;

    /*****************************
     * Constructors
     *****************************/

    /**
     * Initialises a new Strength with the given numeric value.
     * @param numeral
     *        Numeral for the new instance of Strength
     * @effect If the given numeral is effective, the attribute numeral is set
     * to it's value.
     * | if isValidNumeral(numeral)
     * |    this.numeral = numeral
     * @effect Else the numeral is set to zero.
     * | else this.numeral = 0
     * @effect The numeral will be rounded to the precision specified by the
     * MathContext constant.
     */
    public Strength(BigDecimal numeral){
        if(!isValidNumeral(numeral)){
            this.numeral = BigDecimal.ZERO;
        } else {
            this.numeral = numeral.round(getContext());
        }
    }

    /**
     * Constantly defined instance of Strength depicting no strength at all.
     */
    public final static Strength none =
            new Strength(BigDecimal.ZERO);

    /*****************************
     * Hashcode
     *****************************/

    @Override
    public int hashCode(){
        return getNumeral().hashCode() + "strength".hashCode();
    }

    /*****************************
     * Numeral
     *****************************/

    /**
     * Checks if the given numeral is effective for strength.
     * @param numeral
     *        Numeral to be checked.
     * @return True if the numeral is not a null reference and positive
     * or equal to zero.
     * | return numeral != null && numeral >= 0
     */
    public boolean isValidNumeral(BigDecimal numeral){
        if (numeral == null)
            return false;
        else if (numeral.signum() == -1)
            return false;
        else
            return true;
    }

    /**
     * Getter for the numeral.
     */
    public BigDecimal getNumeral() {
        return numeral;
    }

    /**
     * Variable storing the numerical value of this instance of Strength.
     */
    private final BigDecimal numeral;

    /*****************************
     * Rounding & precision
     *****************************/

    /**
     * Retrieves the MathContext to be used when rounding the numeral.
     *
     * @return MathContext object determining the way the numeral of the Strength class is
     * rounded and what precision it has.
     * <p>
     * The precision is set according to the precision constant, specifying how many decimal
     * places rounding is to be done to.
     * <p>
     * The rounding is done using the half even method, dictating that rounding is
     * done to the nearest neighbour and the even neighbour if equidistant.
     */
    @Immutable
    private static MathContext getContext(){
        return new MathContext(
                PRECISION,
                RoundingMode.HALF_EVEN
        );
    }

    /*****************************
     * Logical operations
     *****************************/

    /**
     * Compares this Strength instance to the given other.
     * @param other
     *        Strength instance to compare to.
     * @return The comparison between the numerals.
     * | let:
     * |    thisNumeral = getNumeral()
     * |    otherNumeral = other.getNumeral()
     * | then:
     * |    return thisNumeral.compareTo(otherNumeral)
     */
    @Override
    public int compareTo(Strength other) {
        return getNumeral().compareTo(other.getNumeral());
    }

    /*****************************
     * Arithmetic operations
     *****************************/

    /**
     * Multiplies the numeral of this strength with the given double factor.
     * @param factor
     *        Multiplication factor.
     * @return If the given factor is equal to zero, returns the strength constant none.
     *       | if factor == 0 return Strength.none
     *         Else if the factor is negative, multiply by absolute value.
     *       | else if factor < 0 return multiply(abs(factor))
     *         Else return new instance of strength with as numeral the product of
     *         the old numeral and the given factor.
     *       | else return new Strength(numeral * factor)
     * @note Private because specification dictates strength should only be multiplied or
     * divided by whole numbers.
     */
    private Strength multiply(double factor){
        if (factor == 0){
            return Strength.none;
        } else if (factor < 0){
            return this.multiply(Math.abs(factor));
        } else {
            BigDecimal newNumeral =
                    getNumeral().multiply(new BigDecimal(factor));
            return new Strength(newNumeral);
        }
    }

    /**
     * Multiplies the numeral of this strength with the given integer factor.
     * @param factor
     *        Multiplication factor.
     * @return Casts integer to double and multiplies.
     *       | return multiply((double) factor)
     */
    public Strength multiply(int factor){
        return multiply((double) factor);
    }

    /**
     * Divides the numeral of this strength by the given integer divisor.
     * @param divisor
     *        Value to divide with.
     * @return Multiplies by one over the given divisor.
     *       | return multiply(1/divisor)
     */
    public Strength divide(int divisor){
        return multiply(1/divisor);
    }
}
