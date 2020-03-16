package gameLogics;

import java.util.HashMap;

/**
 * Interface for game managing
 * */
public interface GameEngine {

    /**
     * set as hit the box in position coord
     * @param coord represent the coord of the box
     */
    void hit(Pair<Integer, Integer> coord);

    /**
     * set as flagged the box in position coord
     * @param coord represent the coord of the box
     */
    void setFlag(Pair<Integer, Integer> coord);

    /**
     * @return the status of the game
     */
    GameStatus getGameStatus();

    /**
     * @return a data structure that describe the board status
     */
    HashMap<Pair<Integer, Integer>, Integer> getBoardStatus();
}
