package rpg.value.test;

import org.junit.Before;
import org.junit.Test;
import rpg.value.Unit;
import rpg.value.Weight;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by elias on 11/07/2017.
 */
public class WeightTest {

    Weight testWeight;

    //define shorthands for frequently used units
    static Unit kg = Unit.kg;
    static BigDecimal one = BigDecimal.ONE;

    static BigDecimal decimalNumeral = new BigDecimal(1.23456789);

    static Weight standardWeight = new Weight(one, kg);
    static Weight nonStandardUnitWeight = new Weight(1000, Unit.g);
    static Weight poundWeight = new Weight(1, Unit.lbs);

    @Before
    public void setUp() throws Exception {
    }


    //Constructor with valid nonstandard values.
    @Test
    public void constructorValid() {
        testWeight = new Weight(one, Unit.g);
        assertTrue(
                testWeight.getNumeral().equals(one)
                        && testWeight.getUnit().equals(Unit.g)
        );
    }

    @Test
    public void constructorInvalidUnit() {
        testWeight = new Weight(one, null);
        assertEquals(testWeight.getUnit(), kg);
    }

    @Test
    public void constructorNullNumeral() {
        testWeight = new Weight(null);
        assertEquals(testWeight.getNumeral(), BigDecimal.ZERO);
    }

    @Test
    public void constructorNegativeNumeral() {
        testWeight = new Weight(new BigDecimal(-1));
        assertEquals(testWeight.getNumeral(), BigDecimal.ZERO);
    }

    @Test
    public void constructorKilogramRounding() {
        testWeight = new Weight(decimalNumeral);
        MathContext c = testWeight.getContext();
        assertEquals(testWeight.getNumeral().round(c), decimalNumeral.round(c));
    }

    @Test
    public void constructorGramRounding() {
        testWeight = new Weight(decimalNumeral, Unit.g);
        MathContext c = testWeight.getContext();
        assertEquals(testWeight.getNumeral().round(c), decimalNumeral.round(c));
    }

    @Test
    public void constructorPoundRounding() {
        testWeight = new Weight(decimalNumeral, Unit.lbs);
        MathContext c = testWeight.getContext();
        assertEquals(testWeight.getNumeral().round(c), decimalNumeral.round(c));
    }

    @Test
    public void toUnitSameUnit() {
        Weight convertedWeight = nonStandardUnitWeight.toUnit(Unit.g);
        assertEquals(convertedWeight, nonStandardUnitWeight);
    }

    @Test
    public void toUnitInvalidUnit() {
        Weight convertedWeight = nonStandardUnitWeight.toUnit(null);
        assertEquals(standardWeight, convertedWeight);
    }

    @Test
    public void toUnitKilogramToGram() {
        Weight convertedWeight = standardWeight.toUnit(Unit.g);
        assertEquals(new Weight(1000, Unit.g), convertedWeight);
    }

    @Test
    public void toUnitKilogramToPound() {
        Weight convertedWeight = standardWeight.toUnit(Unit.lbs);
        assertEquals(new Weight(new BigDecimal(2.20462262185), Unit.lbs), convertedWeight);
    }

    @Test
    public void toUnitGramToKilogram() {
        Weight convertedWeight = nonStandardUnitWeight.toUnit(Unit.kg);
        assertEquals(new Weight(1, Unit.kg), convertedWeight);
    }

    @Test
    public void toUnitGramToPound() {
        Weight convertedWeight = nonStandardUnitWeight.toUnit(Unit.kg);
        assertEquals(new Weight(1, Unit.kg), convertedWeight);
    }

    @Test
    public void toUnitPoundToKilogram() {
        Weight convertedWeight = poundWeight.toUnit(Unit.kg);
        assertEquals(new Weight(new BigDecimal(0.45359237), Unit.kg), convertedWeight);
    }

    @Test
    public void toUnitPoundToGram() {
        Weight convertedWeight = poundWeight.toUnit(Unit.g);
        assertEquals(new Weight(new BigDecimal(453.59237), Unit.g), convertedWeight);
    }

    @Test
    public void compareToInvalid() {
        assertEquals(0, standardWeight.compareTo(null));
    }

    @Test
    public void compareToSameUnitsLarger() {
        Weight other = new Weight(new BigDecimal(.5), Unit.kg);
        assertEquals(1, standardWeight.compareTo(other));
    }

    @Test
    public void compareToSameUnitsSmaller() {
        Weight other = new Weight(2, Unit.kg);
        assertEquals(-1, standardWeight.compareTo(other));
    }

    @Test
    public void compareToSameUnitsEqual() {
        assertEquals(0, standardWeight.compareTo(standardWeight));
    }

    @Test
    public void compareToOtherUnits() {
        assertEquals(0, standardWeight.compareTo(nonStandardUnitWeight));
    }

    @Test
    public void addNullReference() {
        assertEquals(standardWeight, standardWeight.add(null));
    }

    @Test
    public void addSameUnits() {
        Weight sum = new Weight(2, kg);
        assertEquals(sum, standardWeight.add(standardWeight));
    }

    @Test
    public void addDifferentUnits() {
        Weight sum = new Weight(2000, Unit.g);
        assertEquals(sum, nonStandardUnitWeight.add(standardWeight));
    }

    @Test
    public void multiplyNull() {
        assertEquals(Weight.kg_0, standardWeight.multiply(null));
    }

    @Test
    public void multiplyNegativeFactor() {
        assertEquals(standardWeight.multiply(2),
                standardWeight.multiply(-2));
    }

    @Test
    public void multiplyWithInteger() {
        int factor = 2;
        assertEquals(standardWeight.multiply(new BigDecimal(factor)),
                standardWeight.multiply(factor));
    }

    @Test
    public void multiplyValid() {
        Weight expectedMultiple = new Weight(2, kg);
        assertEquals(expectedMultiple,
                standardWeight.multiply(new BigDecimal(2)));
    }
}