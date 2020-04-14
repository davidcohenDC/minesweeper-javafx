package scoresystem;

import java.util.Map;

import controlutility.Difficulty;
import controlutility.Modality;

/**
 * A {@link Writer} designated to handle Players' scores.
 */
public interface ScoreWriter extends Writer {

    /**
     * Returns the unordered score board of a specified game mode in a specified
     * difficulty.
     * 
     * @param gameMode
     *                       The {@link Modality} of the desired score board.
     * @param difficulty
     *                       The {@link Difficulty} of the desired score board.
     * @return Returns a Map<String, Long> where players' names are the keys
     *         associated with their best score.
     */
    Map<String, Long> getScoreBoard(Modality gameMode, Difficulty difficulty);
}
