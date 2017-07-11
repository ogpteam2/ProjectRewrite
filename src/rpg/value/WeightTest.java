package rpg.value;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.*;

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
    public void constructorValid(){
        testWeight = new Weight(one,Unit.g);
        assertTrue(
                testWeight.getNumeral().equals(one)
                && testWeight.getUnit().equals(Unit.g)
                );
    }

    @Test
    public void constructorInvalidUnit(){
        testWeight = new Weight(one,null);
        assertEquals(testWeight.getUnit(),kg);
    }

    @Test
    public void constructorNullNumeral(){
        testWeight = new Weight(null);
        assertEquals(testWeight.getNumeral(), BigDecimal.ZERO);
    }

    @Test
    public void constructorNegativeNumeral(){
        testWeight = new Weight(new BigDecimal(-1));
        assertEquals(testWeight.getNumeral(), BigDecimal.ZERO);
    }

    @Test
    public void constructorKilogramRounding(){
        testWeight = new Weight(decimalNumeral);
        MathContext c = testWeight.getContext();
        assertEquals(testWeight.getNumeral().round(c), decimalNumeral.round(c));
    }

    @Test
    public void constructorGramRounding(){
        testWeight = new Weight(decimalNumeral, Unit.g);
        MathContext c = testWeight.getContext();
        assertEquals(testWeight.getNumeral().round(c), decimalNumeral.round(c));
    }

    @Test
    public void constructorPoundRounding(){
        testWeight = new Weight(decimalNumeral, Unit.lbs);
        MathContext c = testWeight.getContext();
        assertEquals(testWeight.getNumeral().round(c), decimalNumeral.round(c));
    }

    @Test
    public void toUnitSameUnit() {
        Weight convertedWeight = nonStandardUnitWeight.toUnit(Unit.g);
        assertEquals(convertedWeight,nonStandardUnitWeight);
    }

    @Test
    public void toUnitInvalidUnit() {
        Weight convertedWeight = nonStandardUnitWeight.toUnit(null);
        assertEquals(standardWeight,convertedWeight);
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
    public void add() {
    }

    @Test
    public void multiply() {
    }

}