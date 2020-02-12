package timer;

public enum Verse {
    /**
     * Increases the Timer value by 1 step each time.
     */
    UP(1),

    /**
     * Decreases the Timer value by 1 step each time.
     */
    DOWN(-1);
	
	private final int verseIncrementValue;
	
	Verse(final int incrementValue){
		verseIncrementValue = incrementValue;
	}
	
	/**
	 * 
	 * @return
	 * returns the increment or decrement value
	 */
	public int getVerseIncrementValue() {
		return verseIncrementValue;
	}
	
}
