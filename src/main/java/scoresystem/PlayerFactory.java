package scoresystem;

import controlutility.Difficulty;

/**
 * A Factory to create a {@link Player} for each game mode.
 */
public interface PlayerFactory {

    /**
     * Creates a Player for Standard Mode.
     * 
     * @param name
     *                       The Player's name.
     * @param difficulty
     *                       The selected {@link Difficulty}.
     * @return A {@link Player} with no adversary.
     */
    Player createPlayerForStandardMode(String name, Difficulty difficulty);

    /**
     * Creates a Player for Beat The Timer Mode.
     * 
     * @param name
     *                       The Player's name.
     * @param difficulty
     *                       The selected {@link Difficulty}.
     * @return A {@link Player} with no adversary.
     */
    Player createPlayerForBeatTheTimerMode(String name, Difficulty difficulty);

    /**
     * Creates a Player for 1 versus 1 Mode.
     * 
     * @param name
     *                          The Player's name.
     * @param difficulty
     *                          The selected {@link Difficulty}.
     * @param adversaryName
     *                          The current adversary's name.
     * @return A {@link Player} with adversary.
     */
    Player createPlayerFor1vs1Mode(String name, Difficulty difficulty, String adversaryName);

}
