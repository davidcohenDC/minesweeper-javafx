package scoresystem;

import controlutility.Difficulty;
import controlutility.Modality;

public interface ScoreCalculator {

    /**
     * Calculates the score of a player.
     * 
     * @param difficuly
     * the difficulty chosen by the player
     * @param modality
     * the game mode chosen by the player
     * @param time
     * the amount of time used to finish the game successfully
     */
    void calculate(Difficulty difficuly, Modality modality, int time);
}
