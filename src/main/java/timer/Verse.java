package timer;

/**
 * The direction of an increment.
 */
public enum Verse {

    /**
     * Increases a value by 1 step each time.
     */
    UP(1, 999),

    /**
     * Decreases a value by 1 step each time.
     */
    DOWN(-1, 0);

    private final int verseValue;
    private final int limit;

    /**
     * Creates a new Verse.
     * 
     * @param incrementValue
     *                           the step which will increment a value
     */
    Verse(final int verseValue, final int incrementLimit) {
        this.verseValue = verseValue;
        this.limit = incrementLimit;
    }

    /**
     * @return Returns the step's value.
     */
    public int getVerseIncrementValue() {
        return this.verseValue;
    }

    /**
     * @return Returns the limit value.
     */
    public int getLimit() {
        return this.limit;
    }
}
