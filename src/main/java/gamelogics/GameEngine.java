package gamelogics;

/**
 * Interface for manage the entire game.
 */
public interface GameEngine {

    /**
     * Set as hit the {@link Box} in that coordinates.
     * 
     * @param coord
     *                  The coordinates of the {@link Box}
     */
    void hit(Pair<Integer, Integer> coord);

    /**
     * Set as flagged the {@link Box} in that coordinates.
     * 
     * @param coord
     *                  The coordinates of the {@link Box}
     */
    void setFlag(Pair<Integer, Integer> coord);

    /**
     * @return The status of the game
     */
    GameStatus getGameStatus();

    /**
     * @return A data structure that describe the {@link Board} status
     */
    Board getBoard();
}
