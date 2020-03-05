package controlutility;
/**
 * Type of difficulty.
 **/
public enum Difficulty {

    /**
<<<<<<< HEAD
     * Easy level: 9 x 9 with 10 mines.
     */
    EASY(1), 

    /**
     * Medium level: 16 x 16 with 40 mines.
     * 
     * Score will be doubled.
     */
    MEDIUM(2),

    /**
     * Hard level: 30 x 16 with 90 mines.
     * 
     * Score will be multiplied by 4.
     */
    HARD(4), 

    /**
     * Personalized level: the field can have the preferred dimension between 9 x 9 and 30 x 24 with a number of mines between 10 and 667. 
     * 
     * Score will not be calculated with this setting.
     */
    PERSONALIZED(0);

    private final int scoreMultiplier;

    Difficulty(final int multiplier) {
        this.scoreMultiplier = multiplier;
    }

    public int getScoreMultiplier() {
        return this.scoreMultiplier;
    }

}
