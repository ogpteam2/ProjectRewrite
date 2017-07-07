package rpg.value;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.*;

public class UnitTest {
	
	static Unit kilogram, gram, pound;
	static BigDecimal kgTokg, kgTog, kgTolbs, gTog, gTolbs, lbsTolbs,gTokg,lbsTokg,lbsTog;
	
	
	@BeforeClass
	public static void setUpBeforeClass() {
		kilogram = Unit.kg; 
		gram = Unit.g;
		pound = Unit.lbs;
		kgTokg = BigDecimal.ONE;
		gTog = BigDecimal.ONE;
		lbsTolbs = BigDecimal.ONE;
		kgTog = new BigDecimal(BigInteger.valueOf(1000),0);
		kgTolbs = new BigDecimal("220462262185").movePointLeft(11);
		gTolbs = new BigDecimal("220462262").movePointLeft(11);	
		gTokg = BigDecimal.ONE.divide(kgTog,Unit.unitContext);
		lbsTokg = BigDecimal.ONE.divide(kgTolbs,Unit.unitContext);
		lbsTog = BigDecimal.ONE.divide(gTolbs,Unit.unitContext);
	}
	
	
	@Test
	public void toUnitTestOnes(){
		assertEquals(kilogram.toUnit(kilogram),kgTokg);
		assertEquals(gram.toUnit(gram),gTog);
		assertEquals(pound.toUnit(pound),lbsTolbs);
	}
	
	
	@Test
	public void toUnitTestOthers(){
		assertEquals(kilogram.toUnit(gram),kgTog);
		assertEquals(kilogram.toUnit(pound),kgTolbs);
		assertEquals(gram.toUnit(pound),gTolbs);
	}
	
	@Test 
	public void toUnitTestInversion(){
		assertEquals(gram.toUnit(kilogram),gTokg);
		assertEquals(pound.toUnit(kilogram),lbsTokg);
		assertEquals(pound.toUnit(gram),lbsTog);
	}
	
	
}
