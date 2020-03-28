package scoresystem;

import controlutility.Modality;

public interface StatistcsWriter extends Writer {

    /**
     * Gets the wins of a certain player in a certain game mode.
     * @param playerName
     * the name of the player to know the wins of
     * @param gameMode
     * the game mode from which to get the statistics
     * @return
     * returns the number of wins
     */
    int getWins(String playerName, Modality gameMode);

    /**
     * Gets the losses of a certain player in a certain game mode.
     * @param playerName
     * the name of the player to know the losses of
     * @param gameMode
     * the game mode from which to get the statistics
     * @return
     * returns the number of losses
     */
    int getLosses(String playerName, Modality gameMode);
}
