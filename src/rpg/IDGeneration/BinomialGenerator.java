package rpg.IDGeneration;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;

/**
 * A class that generates binomial numbers.
 *
 * @author Robbe, Elias
 */
public class BinomialGenerator implements IDGenerator {

    //TODO rework method names and documentation

    /**
     * Arraylist storing a row of Pascal's pyramid.
     * On the one hand this row is used to calculate a binomial coëfficient
     * by summing the row.
     * On the other hand it is used to calculate the next row in the pyramid.
     */
    private ArrayList<Long> pyramidRow;

    /**
     * The current value of the triangle of Pascal.
     */
    private long currentValue;
    /**
     * the current row of the triangle of Pascal.
     */
    private int currentRowNum;

    /**
     * Creates a new Binomialgenerator.
     *
     * @effect Uses the reset method to initialise all values to their
     * initial state.
     * | reset()
     */
    public BinomialGenerator() {
        reset();
    }

    /**
     * Generates an ID.
     *
     * @return The next sequential ID
     * | return nextID()
     * @effect If there is no sequential next ID, reset the generator.
     * | if !hasNextID()
     * |      reset()
     */
    public long generateID() {
        if (!hasNextID()) {
            reset();
        }
        return nextID();
    }

    /**
     * Checks if a next ID can be generated.
     *
     * @return True if the next id would overflow the long variable it is contained in.
     * This is done by checking if the next calculated id is negative, as over-
     * flowing a long in java causes it to flip the sign bit.
     * | return calculateRowSum(pyramidRow) >= 0
     */
    public boolean hasNextID() {
        return calculateRowSum(pyramidRow) >= 0;
    }

    /**
     * Calculates the next sequential ID.
     *
     * @effect pyramidrow is replaced by the next row.
     * | setPyramidRow(calculateNextRow())
     * @effect The rownumber is advan
     */
    private void nextID() {
        setPyramidRow(calculateNextRow());
        advanceCurrentRowNum();
        long nextLong = calculateRowSum(pyramidRow);
        setCurrentValue(nextLong);
        return nextLong;
    }

    /**
     * Resets the iterator to the first binomial coefficient.
     *
     * @effect the pyramidrow is re-initialised to an empty arraylist
     * @effect the pyramid row number is set to 1
     */
    public void reset() {
        setPyramidRow(new ArrayList<Long>());
        setCurrentRowNum(1);
    }

    private ArrayList<Long> calculateNextRow() {
        ArrayList<Long> nextRow = new ArrayList<Long>();
        nextRow.add(1L);
        for (int i = 0; i < pyramidRow.size() - 1; i++) {
            nextRow.add(pyramidRow.get(i) + pyramidRow.get(i + 1));
        }
        nextRow.add(1L);
        return nextRow;
    }

    /**
     * Replaces the current pyramidRow with another.
     *
     * @param nextRow The pyramidrow the current pyramidrow will be replaced with.
     */
    @Raw
    private void setPyramidRow(ArrayList<Long> nextRow) {
        this.pyramidRow = nextRow;
    }

    private long calculateRowSum(ArrayList<Long> row) {
        long rowSum = 0L;
        for (long p : row) {
            rowSum += p;
        }
        return rowSum;
    }

    @Basic
    @Raw
    public long getCurrentValue() {
        return currentValue;
    }


    private void setCurrentValue(long currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * Returns the current row number.
     *
     * @return the current row number.
     */
    @Raw
    @Basic
    public int getCurrentRowNum() {
        return currentRowNum;
    }


    /**
     * The value of the current row number is directly set
     *
     * @param currentRowNum value to set the row number to
     */
    private void setCurrentRowNum(int currentRowNum) {
        this.currentRowNum = currentRowNum;
    }

    /**
     * Advances the row counter by one
     *
     * @Post one is added to the currentRowNum
     */
    private void advanceCurrentRowNum() {
        this.currentRowNum++;
    }
}
