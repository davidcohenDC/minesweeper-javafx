package controlutility;
/**
 * Type of difficulty.
 **/
public enum Difficulty {

    /**
     * Easy level: 9 x 9 with 10 mines.
     */
    EASY, 

    /**
     * Medium level: 16 x 16 with 40 mines.
     */
    MEDIUM,

    /**
     * Hard level: 30 x 16 with 90 mines.
     */
    HARD, 

    /**
     * Personalized level: the field can have the preferred dimension between 9 x 9 and 30 x 24 with a number of mines between 10 and 667. 
     * 
     * Score will not be calculated with this setting.
     */
    PERSONALIZED;
}
