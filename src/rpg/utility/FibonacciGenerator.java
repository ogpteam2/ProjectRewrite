package rpg.utility;

public class FibonacciGenerator implements IDGenerator {

	private long firstNumber;
	private long secondNumber;
	
	public FibonacciGenerator(){
		reset();
	}
	
	@Override
	public long nextID() {
		if(firstNumber == 0){
			firstNumber = 1;
			return firstNumber;
		}
		if(secondNumber == 0){
			secondNumber = 1;
			return secondNumber;
		}
		secondNumber += firstNumber;
		firstNumber = secondNumber - firstNumber;
		return secondNumber;
	}

	@Override
	public boolean hasNextID() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		firstNumber = 0;
		secondNumber = 0;
	}

	@Override
	public long generateID(){
		if(!hasNextID()){
			reset();
		}
		return nextID();
	}

}
