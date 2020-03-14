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

    private static final String FILE_EXTENSION = ".txt";
    private final String fileName;

    Modality(final String name) {
        this.fileName = name + FILE_EXTENSION;
    }

    public String getFileName() {
        return this.fileName;
    }
}
