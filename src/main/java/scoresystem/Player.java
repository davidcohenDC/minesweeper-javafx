package scoresystem;
/**
 * A Player.
 */
public interface Player {

    /**
     * The Player has won the game.
     */
    void won();

    /**
     * The Player has lost the game.
     */
    void lost();

    /**
     * @return
     * Returns the player's score
     */
    int getScore();
}
