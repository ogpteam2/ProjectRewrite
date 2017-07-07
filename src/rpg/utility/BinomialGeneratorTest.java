package rpg.utility;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BinomialGeneratorTest {

	BinomialGenerator it;
	
	@Before
	public void setUp(){
		it = new BinomialGenerator();
	}

	@Test
	public void binomialCorrectnessTest() {
		int length = 20;
		long [] pyramidBinomial = new long[length];
		long [] factorialBinomial = new long[length];
		for(int i = 0; i < length; i++){
			pyramidBinomial[i] = it.nextID();
			factorialBinomial[i] = calculateBinomial(i+1);
		}
		assertArrayEquals(factorialBinomial,pyramidBinomial);
	}
	
	@Test
	public void test(){
		assertEquals(calculateBinomial(2),4L);
	}
	
	@Test
	public void testReset(){
		it.reset();
		assertEquals(2L,it.nextID());
		assertEquals(4L,it.nextID());
	}
	
	@Test
	public void pushMethod(){
		while(it.nextID() > 0);
		System.out.println(it.getCurrentRowNum());
	}
	
	public long calculateBinomial(int n){
		long sum = 0;
		for(int i = 0; i<=n; i++){
			sum += nChooseK(n,i);
		}
		return sum;
	}
	
	public long nChooseK(int n, int k){
		return (factorial(n)/(factorial(k)*factorial(n-k)));
	}
	
	public long factorial(long l){
		if(l == 0) return 1;
		long result = 0;
		result += l;
		for(long i = 1; i < l; i++){
			result *= i;
		}
		return result;
	}
}
