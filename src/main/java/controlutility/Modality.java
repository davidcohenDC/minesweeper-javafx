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

    Modality(final String name) {
        this.directoryName = name;
    }

    public String getDirectoryName() {
        return this.directoryName;
    }
}
