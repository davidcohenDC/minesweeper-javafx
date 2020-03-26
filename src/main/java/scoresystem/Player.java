package scoresystem;

import java.util.Optional;

import controlutility.Difficulty;
import controlutility.Modality;
import gameLogics.GameStatus;

/**
 * A Player.
 */
public interface Player {

    /**
     * The Player has won the game.
     * @param score
     * The score with which the player has won 
     * 
     */
    void won(int score);

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

    /**
     * @return
     * Returns the player's difficulty.
     */
    Difficulty getDifficuly();

    /**
     * @return
     * Returns the player's name.
     */
    String getName();

    /**
     * @return
     * Returns the player's result.
     */
    GameStatus getResult();

}
