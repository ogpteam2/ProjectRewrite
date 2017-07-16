package rpg.value;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by elias on 16/07/2017.
 */
public class Strength {

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

    public Strength(BigDecimal numeral){
        if(!isValidNumeral(numeral)){
            this.numeral = BigDecimal.ZERO;
        } else {
            this.numeral = numeral.round(getContext());
        }

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



    private final BigDecimal numeral;

    /*****************************
     * Rounding & precision
     *****************************/

    public static MathContext getContext(){
        return new MathContext(
                PRECISION,
                RoundingMode.HALF_EVEN
        );
    }

    /*****************************
     * Logical operations
     *****************************/


    /*****************************
     * Arithmetic operations
     *****************************/

    public Strength add(int amount){

    }

    public Strength multiply(int factor){

    }
}
