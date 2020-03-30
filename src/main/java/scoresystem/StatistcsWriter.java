package scoresystem;

import controlutility.Modality;

/**
 * A {@link Writer} designated to handle Players's statistics.
 */
public interface StatistcsWriter extends Writer {

    /**
     * Gets the wins of a certain player in a certain game mode.
     * 
     * @param playerName
     *                       The name of the player to know the wins of.
     * @param gameMode
     *                       The {@link Modality} from which to get the statistics.
     * @return Returns the number of wins.
     */
    int getWins(String playerName, Modality gameMode);

    /**
     * Gets the losses of a certain player in a certain game mode.
     * 
     * @param playerName
     *                       The name of the player to know the losses of.
     * @param gameMode
     *                       The {@link Modality} from which to get the statistics.
     * @return Returns the number of losses.
     */
    int getLosses(String playerName, Modality gameMode);

    /**
     * Get an overview on all the games won in a certain game mode.
     * 
     * @param gameMode
     *                     The {@link Modality} from which to get the statistics.
     * @return Returns the total wins from all players in a game mode.
     */
    int getAllWins(Modality gameMode);

    /**
     * Get an overview on all the games lost in a certain game mode.
     * 
     * @param gameMode
     *                     The {@link Modality} from which to get the statistics.
     * @return Returns the total losses from all players in a game mode.
     */
    int getAllLosses(Modality gameMode);
}
