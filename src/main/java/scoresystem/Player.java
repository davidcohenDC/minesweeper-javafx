package scoresystem;

import java.util.Optional;

import controlutility.Difficulty;
import controlutility.Modality;
import gamelogics.GameStatus;

/**
 * A Player.
 * <p>
 * This class describes all the attributes that should be known about a
 * Player.<br>
 * Every Player will be uniquely identified with its name.
 */
public interface Player {

    /**
     * The Player has won the game.
     * 
     * @param score
     *                  The score with which the player has won.
     */
    void won(long score);

    /**
     * The Player has lost the game.
     */
    void lost();

    /**
     * @return Returns the player's score.
     */
    long getScore();

    /**
     * @return Returns the player's modality.
     */
    Modality getModality();

    /**
     * @return Returns the player's difficulty.
     */
    Difficulty getDifficuly();

    /**
     * @return Returns the player's name.
     */
    String getName();

    /**
     * @return Returns the player's result.
     */
    GameStatus getResult();

    /**
     * @return Returns the name of the Players adversary.
     * 
     *         if a Player did not have an adversary it will return an empty
     *         Optional.
     */
    Optional<String> getAdversary();

}
