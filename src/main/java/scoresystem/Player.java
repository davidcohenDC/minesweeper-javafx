package scoresystem;

import controlutility.Modality;

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

    /**
     * @return
     * Returns the player's modality.
     */
    Modality getModality();
}
