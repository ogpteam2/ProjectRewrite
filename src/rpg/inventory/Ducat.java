package rpg.inventory;

import rpg.value.Weight;

public class Ducat extends Item{

    public static Weight DUCAT_WEIGHT = new Weight()

	public Ducat(){
		super(1, -1L);
		// TODO Auto-generated constructor stub
	}


	/**
	 * The value of items uses a ducat as the unit value. The value of a ducat
	 * must as such be fixed at 1.
	 * @param value
	 * 		  Value to be checked.
	 * @return The value must be one.
	 *       | return value == 1
	 */
	public boolean canHaveAsValue(int value){
		return value == 1;
	}

    /**
     * The value of items uses a ducat as the unit value. The value of a ducat
     * must as such be fixed at 1.
     * @return The value of a ducat: one.
     *       | return 1
     */
	public int getValue(){
	    return 1;
    }
}
