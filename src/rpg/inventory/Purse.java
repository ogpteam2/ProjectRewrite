package rpg.inventory;

import rpg.Mobile;
import rpg.value.Weight;

public class Purse extends Container {

	public Purse(int value, Weight weight) {
		super(value, weight, 0L);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canHaveAsContent(Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canHaveAsValue(int value) {
		return false;
	}

	@Override
	public Mobile getHolder() {
		return null;
	}
}
