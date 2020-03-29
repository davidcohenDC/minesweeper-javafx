package scoresystem;

import controlutility.Difficulty;

/**
 * A Factory to create a player for each game mode.
 */
public interface PlayerFactory {

    /**
     * Creates a Player for Standard Mode.
     * @param name
     * The Players' name 
     * @param difficulty
     * The selected Difficulty
     * @return
     * A Player with no adversary
     */
    Player createPlayerForStandardMode(String name, Difficulty difficulty);

    /**
     * Creates a Player for Beat The Timer Mode.
     * @param name
     * The Players' name 
     * @param difficulty
     * The selected Difficulty
     * @return
     * A Player with no adversary
     */
    Player createPlayerForBeatTheTimerMode(String name, Difficulty difficulty);

    /**
     * Creates a Player for 1 versus 1 Mode.
     * @param name
     * The Players' name 
     * @param difficulty
     * The selected Difficulty
     * @param adversaryName
     * The current adversary
     * @return
     * A Player with adversary
     */
    Player createPlayerFor1vs1Mode(String name, Difficulty difficulty, String adversaryName);

}
