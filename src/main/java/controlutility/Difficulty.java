package controlutility;

/**
 * Difficulty levels of the game.
 */
public enum Difficulty {

    /**
     * Easy level: 9 x 9 with 10 mines.
     */
    EASY("Easy"),

    /**
     * Medium level: 16 x 16 with 40 mines.
     */
    MEDIUM("Medium"),

    /**
     * Hard level: 30 x 16 with 90 mines.
     */
    HARD("Hard"),

    /**
     * Personalized level: the field can have the preferred dimension between 9 x 9
     * and 30 x 24 with a number of mines between 10 and 667. <br>
     * <strong>Score will not be saved with this setting.</strong>
     */
    PERSONALIZED("Personalized");

    private final String name;

    /**
     * Creates new {@link Difficulty}.
     * 
     * @param name
     *                 The Name of the Difficulty.
     */
    Difficulty(final String name) {
        this.name = name;
    }

    /**
     * @return Returns the name of the Difficlty.
     */
    public String getName() {
        return this.name;
    }
}
