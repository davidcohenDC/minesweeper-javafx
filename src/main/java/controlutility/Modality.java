package controlutility;

/**
 * Type of modality.
 */
public enum Modality {

    /**
     * Standard mode.
     */
    STANDARD("STD"),

    /**
     * One versus one.
     * 
     * Play against another player.
     */
    ONE_VS_ONE("OVO"),

    /**
     * Beat the Timer.
     * 
     * Finish the game before a certain time expires.
     */
    BTT("BTT");

    private final String directoryName;

    /**
     * Creates a new {@link Modality}.
     * 
     * @param name
     *                 The name to associate to the modality.
     */
    Modality(final String name) {
        this.directoryName = name;
    }

    /**
     * @return Returns the directory inside <i>.minesweeper/score_files/</i> where
     *         the files for the modality's files are stored.
     */
    public String getDirectoryName() {
        return this.directoryName;
    }
}
