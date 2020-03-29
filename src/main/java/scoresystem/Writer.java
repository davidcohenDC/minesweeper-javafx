package scoresystem;

/**
 * A Writer to save certain data in a file.
 */
public interface Writer {

    /**
     * Writes the player's desired data into its designated file.
     * 
     * @param p
     *              the player to write the score about
     */
    void write(Player p);
}
