package rpg.inventory;

import rpg.value.Unit;
import rpg.value.Weight;

import java.math.BigDecimal;

public class Ducat extends Item{

    public static Weight DUCAT_WEIGHT = new Weight(50, Unit.g);

	public Ducat(){
		super(1, DUCAT_WEIGHT);
		// TODO Auto-generated constructor stub
	}

    /**
     * The value of items uses a ducat as the unit value. The value of a ducat
     * must as such be fixed at 1.
     * @return The value of a ducat: one.
     *       | return 1
     */
	public int getValue(){
	    return this.value;
    }
}
