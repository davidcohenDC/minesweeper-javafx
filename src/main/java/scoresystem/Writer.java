package scoresystem;

public interface Writer {

    /**
     * Writes the player's score into its designated file.
     * @param p
     * the player to write the score about
     */
    void write(Player p);
}
