package timer;

/**
 * The direction of an increment.
 */
public enum Verse {

    /**
     * Increases a value by 1 step each time.
     */
    UP(1),

    /**
     * Decreases a value by 1 step each time.
     */
    DOWN(-1);

    private final int verseIncrementValue;

    /**
     * Creates a new Verse.
     * @param incrementValue
     * the step which will increment a value
     */
    Verse(final int incrementValue) {
        verseIncrementValue = incrementValue;
    }

    /**
     * @return
     * returns the step's value
     */
    public int getVerseIncrementValue() {
        return verseIncrementValue;
    }
}
