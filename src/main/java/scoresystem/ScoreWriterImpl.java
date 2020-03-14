package scoresystem;

public class ScoreWriterImpl implements ScoreWriter {
    //separates the player id from his score
    private static final String PLAYER_SEPARATOR = ":";
    //separates each score form each other based on the difficulty 
    private static final String DIFFICULTY_SEPARATOR = "-"; 
    private static final String SEPARATOR = System.getProperty("file.separator");
    private final Player player;

    /**
     * Sets up the score writing process.
     * @param player
     * The player to register the score
     */
    protected ScoreWriterImpl(final Player player) {
        this.player = player;
    }
}
