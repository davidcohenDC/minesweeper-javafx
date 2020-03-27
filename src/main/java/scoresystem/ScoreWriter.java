package scoresystem;

import java.util.Map;

import controlutility.Difficulty;
import controlutility.Modality;

public interface ScoreWriter extends Writer {
 
    /**
     * Returns the score board of a specified game mode in a specified difficulty.
     * @param gameMode
     * the game mode of the desired score board
     * @param difficulty
     * the difficulty of the desired score board
     * @return
     * returns a Map<String, Integer> where players' names are the keys associates with their best score
     */
    Map<String, Integer> getScoreBoard(Modality gameMode, Difficulty difficulty);
}
