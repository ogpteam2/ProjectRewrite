package rpg.inventory;

import rpg.inventory.Container;

public class Anchorpoint extends Container {

	public Anchorpoint(int value) {
		super(value);
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
	public boolean canHaveAsValue() {
		// TODO Auto-generated method stub
		return false;
	}

}
